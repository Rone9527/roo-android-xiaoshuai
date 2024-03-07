package com.roo.wallet;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;
import com.roo.core.app.component.Main;
import com.roo.wallet.mvp.ui.activity.MainActivity;

public class ComponentMain implements IComponent {

    @Override
    public String getName() {
        return Main.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case Main.Action.MainActivity:
                Boolean exit = cc.getParamItem(Main.Param.EXIT, false);
                MainActivity.start(cc.getContext(), exit);
                return false;
        }
        return false;
    }

}
