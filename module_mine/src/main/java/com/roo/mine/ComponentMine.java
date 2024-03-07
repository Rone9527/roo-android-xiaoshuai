package com.roo.mine;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.roo.core.app.component.Mine;
import com.roo.mine.mvp.ui.activity.AddressBookActivity;
import com.roo.mine.mvp.ui.activity.MineActivity;
import com.roo.mine.mvp.ui.activity.NoticeDetailActivity;
import com.roo.mine.mvp.ui.fragment.MineFragment;

public class ComponentMine implements IComponent {

    @Override
    public String getName() {
        return Mine.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case Mine.Action.AddressBookActivity:
                AddressBookActivity.start(cc.getContext(), true, cc.getCallId(), cc.getParamItem(Mine.Param.chainCode));
                return true;
            case Mine.Action.NoticeDetailActivity:
                NoticeDetailActivity.start(cc.getContext(), cc.getParamItem(Mine.Param.MessageSystemDataBean));
                return true;
            case Mine.Action.MineActivity:
                MineActivity.start(cc.getContext());
                return false;
            case Mine.Action.MineFragment:
                MineFragment mineFragment = MineFragment.newInstance();
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(mineFragment));
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error("没有找到对应的Action"));
                return false;
        }
    }

}
