package com.roo.webview;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.roo.core.app.component.WebView;
import com.roo.core.app.constants.Constants;
import com.roo.webview.activity.WebViewActivity;
import com.roo.webview.fragment.WebFragment;

import java.util.HashMap;

public class ComponentWebView implements IComponent {

    @Override
    public String getName() {
        return WebView.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case WebView.Action.WebFragment:
                HashMap<String, Object> result = new HashMap<>();
                WebFragment webFragment = WebFragment.newInstance(cc.getParamItem(Constants.KEY_URL));
                result.put(WebView.Result.RESULT, webFragment);
                CC.sendCCResult(cc.getCallId(), CCResult.success(result));
                return false;
            case WebView.Action.WebActivity:
                HashMap<String, Object> params = (HashMap<String, Object>) cc.getParams();
                WebViewActivity.start(cc.getContext(), params);
                return false;

            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error("没有找到对应的Action"));
                return false;
        }
    }

}
