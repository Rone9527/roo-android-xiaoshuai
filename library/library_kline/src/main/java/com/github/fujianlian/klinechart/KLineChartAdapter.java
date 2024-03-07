package com.github.fujianlian.klinechart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据适配器
 * Created by tifezh on 2016/6/18.
 */
public class KLineChartAdapter extends BaseKLineChartAdapter {

    private List<KLineEntity> dataSource = new ArrayList<>();

    public KLineChartAdapter() {

    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        try{
            return dataSource.get(position);
        }catch (IndexOutOfBoundsException ignore){
            return null;
        }
    }

    @Override
    public String getDate(int position) {
        return dataSource.get(position).Date;
    }

    /**
     * 向头部添加数据
     */
    public void addHeaderData(KLineEntity addDatas) {
        dataSource.add(0, addDatas);
    }


    /**
     * 向头部添加数据
     */
    public void addHeaderData(ArrayList<KLineEntity> addDatas) {
        dataSource.addAll(0, addDatas);
    }

    /**
     * 获取最后一条数据
     *
     * @return
     */
    public KLineEntity getLastData() {
        return dataSource.get(getCount() - 1);
    }


    /**
     * 获取第一条数据
     *
     * @return
     */
    public KLineEntity getFirstData() {
        if (dataSource == null || dataSource.size() == 0) return null;
        return dataSource.get(0);
    }


    /**
     * 获取最后一条数据
     *
     * @return
     */
    public List<KLineEntity> getData() {
        return dataSource;
    }

    /**
     * 向尾部添加数据
     */
    public void addFooterData(KLineEntity addDatas) {
        dataSource.add(addDatas);
    }

    /**
     * 重新设置新的数据源
     */
    public void setNewData(Collection<KLineEntity> addDatas) {
        dataSource.clear();
        dataSource.addAll(addDatas);
    }

    /**
     * 改变某个点的值
     *
     * @param position 索引值
     */
    public void changeItem(int position, KLineEntity data) {
        dataSource.set(position, data);
    }

    /**
     * 改变最后一个点的值
     */
    public void changeLastItem(KLineEntity data) {
        if (getCount() != 0) {
            dataSource.set(getCount() - 1, data);
        } else {
            dataSource.set(0, data);
        }
    }

    /**
     * 数据清除
     */
    public void clearData() {
        dataSource.clear();
        notifyDataSetChanged();
    }

}
