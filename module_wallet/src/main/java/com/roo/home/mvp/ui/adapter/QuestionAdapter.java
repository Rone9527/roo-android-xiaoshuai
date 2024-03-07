package com.roo.home.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.core.domain.wallet.QuestionBean;
import com.roo.home.R;

import java.util.List;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //    ┃　　　┃   神兽保佑
 * //    ┃　　　┃   代码无BUG！
 * //    ┃　　　┗━━━┓
 * //    ┃　　　　　　　┣┓
 * //    ┃　　　　　　　┏┛
 * //    ┗┓┓┏━┳┓┏┛
 * //      ┃┫┫　┃┫┫
 * //      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/9/23
 * PackageName : com.roo.home.mvp.ui.adapter
 * Description :
 */
public class QuestionAdapter extends BaseQuickAdapter<QuestionBean.OptionBean, BaseViewHolder> {
    //显示答案
    private boolean showAnswer = false;
    private List<String> answer;

    public QuestionAdapter(List<QuestionBean.OptionBean> data, List<String> answer, boolean showAnswer) {
        super(R.layout.item_question_adapter, data);
        this.showAnswer = showAnswer;
        this.answer = answer;
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionBean.OptionBean item) {
        ImageView imageView = helper.getView(R.id.iv_check);
        helper.setText(R.id.tv_question, item.option);
        imageView.setImageResource(item.checked ? R.drawable.ic_checked : R.drawable.ic_un_check);

        if (showAnswer) {//如果是展示答案的模式
            if (answer.contains(String.valueOf(helper.getAdapterPosition()))) {//显示正确答案
                if (item.checked){
                    imageView.setImageResource(item.checked ? R.drawable.ic_checked : R.drawable.ic_un_check);
                }else {
                    imageView.setImageResource(R.drawable.ic_uncheck_blue);
                }
                helper.setTextColor(R.id.tv_question, mContext.getColor(R.color.blue_color));
            } else {//如果选中了错误答案
                if (item.checked){
                    helper.setTextColor(R.id.tv_question, mContext.getColor(R.color.color_deep_orange));
                    imageView.setImageResource( R.drawable.ic_checked_yellow );
                }else {
                    helper.setTextColor(R.id.tv_question, mContext.getColor(R.color.text_color_dark_normal));
                }

            }
        }
//        if (showAnswer && item.checked) {//如果选中判断答案是否正确
//            if (!answer.contains(String.valueOf(helper.getAdapterPosition()))) {
//
//            }
//            helper.setTextColor(R.id.tv_question, mContext.getColor(R.color.color_deep_orange));
//        }
    }
}
