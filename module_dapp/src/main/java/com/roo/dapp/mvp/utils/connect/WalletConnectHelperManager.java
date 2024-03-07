package com.roo.dapp.mvp.utils.connect;

import android.content.Context;
import android.util.Log;

import com.mykey.id.walletconnect.Session;
import com.roo.walletconnect.WallConnectInfoConfig;
import com.roo.walletconnect.WalletConnectCallBack;
import com.roo.walletconnect.WalletConnectManager;
import com.roo.walletconnect.log.LogCallBack;


public class WalletConnectHelperManager {
    private static final String TAG = "WalletConnectHelper";
    private static final WalletConnectHelperManager walletConnectHelper = new WalletConnectHelperManager();

    private static final String CUSTOM_METHOD_PERSONAL_SIGN = "personal_sign";
    private Context context;
    private int mainChainId;
    private String address;

    private WalletConnectHelperManager() {
    }

    public static WalletConnectHelperManager getInstance() {
        return walletConnectHelper;
    }

    public void initWalletConnect(Context context, int mainChainId, String address) {
        this.context = context;
        this.mainChainId = mainChainId;
        this.address = address;
        WalletConnectManager.getInstance().init(context, new WallConnectInfoConfig()
                .setClientId(UserManager.getRandomUUID(context))
                .setName(CommonInfo.APP_NAME)
                .setUrl(CommonInfo.APP_URL)
                .setIcon(CommonInfo.APP_ICON_URL)
                .setDescription(CommonInfo.APP_DESCRIPTION)
                .setLogCallBack(logCallBack)
                .setWalletConnectCallBack(walletConnectCallBack));
    }

    private void savePeerMeta(Session.PeerMeta peerMeta, Context context) {
        if (null == peerMeta) {
            return;
        }
        SharedPreferenceManager.setWalletConnectPeerMeta(JsonUtil.gsonToJson(peerMeta), context);
    }

    private Session.PeerMeta getPeerMeta() {
        Session.PeerMeta peerMeta = JsonUtil.gsonParse(SharedPreferenceManager.getWalletConnectPeerMeta(context), Session.PeerMeta.class);
        if (null == peerMeta) {
            peerMeta = new Session.PeerMeta();
        }
        return peerMeta;
    }

    private LogCallBack logCallBack = new LogCallBack() {
        @Override
        public void e(String tag, String log) {
            Log.e(tag, log);
        }

        @Override
        public void i(String tag, String log) {
            Log.i(tag, log);
        }
    };

    private WalletConnectCallBack walletConnectCallBack = new WalletConnectCallBack() {
        @Override
        public void onSessionRequest(Session.MethodCall.SessionRequest sessionRequest) {
            Log.e(TAG, "in onSessionRequest");
            savePeerMeta(sessionRequest.getPeer().getMeta(), context);
            WalletConnectManager.getInstance().approveSession(address, (long) mainChainId);
        }

        @Override
        public void onSessionUpdate(Session.MethodCall.SessionUpdate sessionUpdate) {

        }

        @Override
        public void onSendTransaction(Session.MethodCall.SendTransaction sendTransaction) {
            Log.e(TAG, "in onSendTransaction");
        }

        @Override
        public void onSignMessage(Session.MethodCall.SignMessage signMessage) {
            Log.e(TAG, "in onSignMessage");
        }

        @Override
        public void onCustom(Session.MethodCall.Custom custom) {
            Log.e(TAG, "in onCustom");
        }

        @Override
        public void onResponse(Session.MethodCall.Response response) {

        }
    };
}
