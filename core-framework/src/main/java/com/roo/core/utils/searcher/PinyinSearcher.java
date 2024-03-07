package com.roo.core.utils.searcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PinyinSearcher<T> {

    private List<T> queriedBeanList; // 待查实体列表
    private List<String> regex; // 待查实体指定关键字列构成的正则式列表
    /**
     * 将字符串拆分成单个汉字与非字符串
     */
    private Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]|[^\\u4e00-\\u9fa5]+");

    @Inject
    public PinyinSearcher() {
    }

    /**
     * 匹配与整理bean到最终返回集合中
     * term:要搜索的关键词
     */
    public List<T> startMatching(String term) {

        List<T> resultList = new ArrayList<T>();
        for (int i = 0; i < regex.size(); i++) {
            if (term.matches(regex.get(i))) {
                resultList.add(queriedBeanList.get(i));
            }
        }

        return resultList;
    }

    /**
     * 预处理被搜索的关键词 将其拆解为正则表达式
     */
    public void preProcessKeywords(Method getValueMethod) throws Exception {
        if (queriedBeanList == null || queriedBeanList.isEmpty()) {
            return;
        }

        String chineseReg = "[\\u4e00-\\u9fa5]";
        // 初始化正则式
        for (Object bean : queriedBeanList) {

            // 拆分汉字与非汉字字符串
            // 如 姚sunny陈ken堃 {"姚","sunny","陈","ken","堃"}
            String queryString = (String) getValueMethod.invoke(bean);
            if (queryString == null || "".equals(queryString)) {// 若为空regax置为""
                regex.add("");
                continue;
            }

            Word[] words = splitStrIntoHanziAndOthers(queryString);
            int length = words.length;

            // 若为汉字转成拼音及相应正则，若为非汉字串转成前缀、全排列、后缀
            for (Word word : words) {
                if (word.getContent().matches(chineseReg)) {// 汉字
                    word.formHanziReg(); // 生成拼音正则
                } else {// 非汉字串
                    word.formFixReg(); // 生成非汉字串前缀、后缀、全排列
                }
            }

            StringBuilder mRegex = new StringBuilder();
            boolean addSubregFlag; // 是否输出子正则表达式（如 姚|yao sunny）
            for (int i = 0; i < length; i++) {
                for (int j = i; j < length; j++) {
                    addSubregFlag = false;
                    StringBuilder subRegex = new StringBuilder();
                    for (int k = j; k >= i; k--) {
                        String hanziReg = words[k].getHanziReg();
                        if ("".equals(hanziReg)) {// 非汉字串
                            // 判断该非汉字串所在位置
                            if (j > i) {// 中间
                                if (k < j && k > i) {// 中间 取子正则式
                                    subRegex.insert(0, words[k].getCompleteReg());
                                    addSubregFlag = true;
                                } else if (k >= j) { // 最后一个 取前缀
                                    subRegex.insert(0, words[k].getPrefixReg());
                                    addSubregFlag = true;
                                } else { // 第一个k <= i 取后缀
                                    subRegex.insert(0, words[k].getSuffixReg());
                                }
                            } else {// 第一个且最后一个 取全排列
                                subRegex.insert(0, words[k].getFullfixReg());
                            }
                        } else { // 汉字
                            if (addSubregFlag) {// 输出子正则式
                                subRegex.insert(0, words[k].getCompleteReg());
                            } else {
                                subRegex.insert(0, hanziReg);
                            }
                        }
                    }
                    mRegex.append(subRegex).append("|");
                }
            }

            mRegex = new StringBuilder(mRegex.substring(0, mRegex.length() - 1));
            mRegex = new StringBuilder(mRegex.toString().replaceAll("\\.", "#")); // 屏蔽掉.号，防止其加入正则表达式
            regex.add(mRegex.toString());
        }
    }

    public Word[] splitStrIntoHanziAndOthers(String str) {
        Matcher m = p.matcher(str.replaceAll("\\(", "")
                .replaceAll("\\)", ""));
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group());
        }
        Word[] res = new Word[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = new Word(list.get(i).toLowerCase());
        }
        return res;

    }

    /**
     * keyword:输入的关键字 kws:被查关键字集 ids:被查关键字所在索引集
     */
    public List<T> match(String keyword, List<T> queriedBeanList, String domainName) throws Exception {
        if (keyword == null || queriedBeanList == null || queriedBeanList.isEmpty()) {
            return Collections.emptyList();
        }

        this.queriedBeanList = queriedBeanList;
        this.regex = new ArrayList<>();

        // 利用反射获取方法
        Class beanClass = queriedBeanList.get(0).getClass();
        String methodName = convertToMethodName(domainName);
        Method getValueMethod = beanClass.getDeclaredMethod(methodName);

        // 预处理待搜索的关键字
        preProcessKeywords(getValueMethod);

        // 返回被匹配上的关键词对象
        return startMatching(keyword.toLowerCase());
    }

    // 将domainName转换成getDomainName
    private String convertToMethodName(String domainName) {
        char firstCh = domainName.charAt(0);
        if (firstCh >= 'a' && firstCh <= 'z') {
            firstCh = (char) (firstCh - 'a' + 'A');
        }

        return "get" + firstCh + domainName.substring(1);
    }

}
