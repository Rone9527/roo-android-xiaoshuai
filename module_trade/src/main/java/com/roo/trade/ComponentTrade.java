package com.roo.trade;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.roo.core.app.component.Trade;
import com.roo.trade.mvp.ui.activity.TradeActivity;
import com.roo.trade.mvp.ui.fragment.MarketFragment;

public class ComponentTrade implements IComponent {

    @Override
    public String getName() {
        return Trade.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case Trade.Action.TradeActivity:
                TradeActivity.start(cc.getContext());
                return false;
            case Trade.Action.MarketFragment:
                MarketFragment marketFragment = MarketFragment.newInstance();
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(marketFragment));
                return false;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error("没有找到对应的Action"));
                return false;
        }
    }

}
