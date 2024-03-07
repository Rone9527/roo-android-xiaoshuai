package com.roo.upgrade;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.roo.core.app.component.Upgrade;

public class ComponentUpgrade implements IComponent {
    @Override
    public String getName() {
        return Upgrade.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        switch (cc.getActionName()) {
            case Upgrade.Action.check:
                if (cc.getParamItem(Upgrade.Param.DOWNLOAD, false)) {//作为apk文件下载模块使用
                    UpgradeManger.newInstance(cc)
                            .downloadDirectly(cc.getParamItem(Upgrade.Param.DOWNLOAD_URL));
                } else {
                    UpgradeManger.newInstance(cc).requestCheckUpdate(Upgrade.Action.check);
                }
                return true;
            case Upgrade.Action.versionNew:
                UpgradeManger.newInstance(cc).requestCheckUpdate(Upgrade.Action.versionNew);
                return true;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error("没有找到对应的Action"));
                return false;
        }
    }
}
