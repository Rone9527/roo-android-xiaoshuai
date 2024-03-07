package com.roo.home.mvp.ui.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.core.domain.wallet.QuestionBean;
import com.roo.core.app.constants.EventBusTag;
import com.roo.home.R;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

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
 * Created on : 2021/9/22
 * PackageName : com.roo.home.mvp.ui.adapter
 * Description : 问答adapter
 */
public class QAAdapter extends BaseQuickAdapter<QuestionBean, BaseViewHolder> {
    //显示答案
    private boolean showAnswer = false;

    @Inject
    public QAAdapter() {
        super(R.layout.item_qa_adapter);
    }


    @Override
    protected void convert(BaseViewHolder helper, QuestionBean item) {
        String type = item.type == 1 ? "(单选)" : "(多选)";
        helper.setText(R.id.tv_title, (helper.getAdapterPosition() + 1) + "." + item.question + type);
        RecyclerView recyclerView = helper.getView(R.id.recycler_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        QuestionAdapter questionAdapter = new QuestionAdapter(item.options, item.answer, showAnswer);
        recyclerView.setAdapter(questionAdapter);


        questionAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (item.type == 1) {

                        List<QuestionBean.OptionBean> options = item.options;
                        for (int i = 0; i < options.size(); i++) {
                            options.get(i).checked = false;
                        }
                        questionAdapter.getData().get(position).checked = true;
                        questionAdapter.notifyDataSetChanged();
                    } else {
                        questionAdapter.getData().get(position).checked = !questionAdapter.getData().get(position).checked;
                        questionAdapter.notifyItemChanged(position);
                    }

                    item.isFinish = true;
                    HashMap<String, Object> param = new HashMap<>();
                    EventBus.getDefault().post(param, EventBusTag.CHECK_OPTION);
                }
        );
    }

    public void showAnswer(boolean b) {
        this.showAnswer = b;
        notifyDataSetChanged();
    }
}
