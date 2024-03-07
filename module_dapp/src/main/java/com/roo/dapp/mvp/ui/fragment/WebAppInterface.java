package com.roo.dapp.mvp.ui.fragment;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.utils.Hex;
import com.roo.core.utils.LogManage;
import com.roo.dapp.mvp.beans.Address;
import com.roo.dapp.mvp.beans.EthereumMessage;
import com.roo.dapp.mvp.beans.EthereumTypedMessage;
import com.roo.dapp.mvp.beans.SignMessageType;
import com.roo.dapp.mvp.beans.WalletAddEthereumChainObject;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.interfaces.OnEthCallListener;
import com.roo.dapp.mvp.interfaces.OnRequestAccountsListener;
import com.roo.dapp.mvp.interfaces.OnSignMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignPersonalMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignTransactionListener;
import com.roo.dapp.mvp.interfaces.OnSignTypedMessageListener;
import com.roo.dapp.mvp.interfaces.OnSwitchEthChainListener;
import com.roo.dapp.mvp.interfaces.OnWalletAddEthereumChainObjectListener;
import com.roo.dapp.mvp.utils.CryptoFunctions;
import com.roo.dapp.mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

class WebAppInterface {
    private WebView context;
    private String address;
    @NonNull
    private final OnRequestAccountsListener onRequestAccountsListener;

    @NonNull
    private final OnSignTransactionListener onSignTransactionListener;
    @NonNull
    private final OnSignMessageListener onSignMessageListener;
    @NonNull
    private final OnSignPersonalMessageListener onSignPersonalMessageListener;
    @NonNull
    private final OnSignTypedMessageListener onSignTypedMessageListener;
    @NonNull
    private final OnEthCallListener onEthCallListener;
    @NonNull
    private final OnWalletAddEthereumChainObjectListener onWalletAddEthereumChainObjectListener;

    @NonNull
    private final OnSwitchEthChainListener onSwitchEthChainListener;


    public WebAppInterface(WebView webView, String address,
                           @NonNull OnRequestAccountsListener onRequestAccountsListener,
                           @NonNull OnSignTransactionListener onSignTransactionListener,
                           @NonNull OnSignMessageListener onSignMessageListener,
                           @NonNull OnSignPersonalMessageListener onSignPersonalMessageListener,
                           OnSignTypedMessageListener onSignTypedMessageListener,
                           @NotNull OnEthCallListener onEthCallListener,
                           @NonNull OnWalletAddEthereumChainObjectListener onWalletAddEthereumChainObjectListener,
                           @NonNull OnSwitchEthChainListener onSwitchEthChainListener) {
        this.context = webView;
        this.address = address;
        this.onRequestAccountsListener = onRequestAccountsListener;
        this.onSignTransactionListener = onSignTransactionListener;
        this.onSignMessageListener = onSignMessageListener;
        this.onSignPersonalMessageListener = onSignPersonalMessageListener;
        this.onSignTypedMessageListener = onSignTypedMessageListener;
        this.onEthCallListener = onEthCallListener;
        this.onWalletAddEthereumChainObjectListener = onWalletAddEthereumChainObjectListener;
        this.onSwitchEthChainListener = onSwitchEthChainListener;
    }

    @JavascriptInterface
    public void postMessage(String json) {
        LogManage.e("postMessage", json);
        try {
            JSONObject obj = new JSONObject(json);
            String name = (String) obj.get("name");
            //这里的id 可能是 Integer 和 Long 所以不转型
            long id = obj.getLong("id");
            switch (name) {
                case "requestAccounts":
                    //window.ethereum.sendResponse(1629466641901, ["0x8AC761bCFF9220A7a3786C52366127cba292aaE8"])
                    String callback = "window.ethereum.sendResponse(" + id + ", [\"" + address + "\"])";
                    onRequestAccountsListener.onRequestAccounts(callback);
//                    context.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            context.evaluateJavascript(callback, value -> println("requestAccount" + value));
//                        }
//                    });
                    break;
                case "signTransaction":
                    //{"id":1629464076583,"name":"signTransaction","object":{"gas":"0xbece","gasPrice":"0x12a05f200","from":"0x8ac761bcff9220a7a3786c52366127cba292aae8","to":"0x55d398326f99059ff775485246999027b3197955","data":"0x095ea7b300000000000000000000000010ed43c718714eb63d5aa57b78b54704e256024effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"}}

                    JSONObject object = obj.optJSONObject("object");
                    if (object != null) {

                        String from = object.optString("from");
                        String payload = object.optString("data");
                        String to = object.optString("to");
                        String nonce = object.optString("nonce");
                        String value = "0";//默认0
                        String gasPrice = "0";//默认0
                        String gasLimit = "0";//默认0
                        if (object.has("value")) {
                            value = object.optString("value");
                            if (value.equals("undefined")) {
                                value = "60000";
                            }
                        }
                        if (object.has("gasPrice")) {
                            gasPrice = object.optString("gasPrice");
                        }
                        if (object.has("gas")) {
                            gasLimit = object.optString("gas");
                        } else {
                            gasLimit = "100000";
                        }


                        Web3Transaction transaction = new Web3Transaction(
                                TextUtils.isEmpty(to) ? Address.EMPTY : new Address(to),
                                null,
                                Hex.hexToBigInteger(value),
                                Hex.hexToBigInteger(gasPrice, new BigInteger("10")),
                                Hex.hexToBigInteger(gasLimit, new BigInteger("21000")),
                                payload,
                                id);
                        LogManage.e(DappBrowserFragmentNew.TAG, new Gson().toJson(transaction));
                        context.post(() -> {
                            onSignTransactionListener.onSignTransaction(transaction, getUrl());
                        });
                    }
                    break;

                case "signPersonalMessage"://签名数据及其验证
                    JSONObject signPersonalMessage = obj.optJSONObject("object");
                    String data = signPersonalMessage.optString("data");
                    context.post(() -> {
                                onSignPersonalMessageListener.onSignPersonalMessage(new EthereumMessage("0x54416e73703347454d7757443464703477486d4173637a78737a755743446362726f20436f6e73656e7420746f20726567697374726174696f6e20626162616f736875203631323134383339363435346435336564366463623733653133373636613361303638666465356666363931666137343139363638653862623231613237356237383538386633613961326361326366653765393663343463666233646138663632353664316565653263633937643238376536663863633264666266353235", getUrl(), id
                                        , SignMessageType.SIGN_PERSONAL_MESSAGE));

                            }
                    );

                    break;
                case "signMessage"://调用使用指定地址的私钥签名消息。该调用需要节点 启用钱包功能
                    JSONObject signMessage = obj.optJSONObject("object");
                    String signMessageData = signMessage.optString("data");
                    context.post(() -> onSignMessageListener.onSignMessage(new EthereumMessage(signMessageData, getUrl(), id, SignMessageType.SIGN_MESSAGE)));
                    break;

                case "signTypedMessage"://调用使用指定地址的私钥签名消息。该调用需要节点 启用钱包功能

                    context.post(() -> {
                        try {
                            JSONObject signTypedMessage = obj.optJSONObject("object");

                            String signTypedMessageData = signTypedMessage.getString("raw");
                            CryptoFunctions cryptoFunctions = new CryptoFunctions();
                            context.post(new Runnable() {
                                @Override
                                public void run() {
                                    EthereumTypedMessage message = new EthereumTypedMessage(signTypedMessageData, getDomainName(), id, cryptoFunctions);
                                    onSignTypedMessageListener.onSignTypedMessage(message);
                                }
                            });
                        } catch (Exception e) {
                            EthereumTypedMessage message = new EthereumTypedMessage(null, "", getDomainName(), id);
                            onSignTypedMessageListener.onSignTypedMessage(message);
                            e.printStackTrace();
                        }
                    });
                case "addEthereumChain":
                    //First draft: attempt to match this chain with known chains; switch to known chain if we match
                    try {
                        JSONObject msgParams = obj.optJSONObject("object");
                        WalletAddEthereumChainObject chainObj = new Gson().fromJson(msgParams.toString(), WalletAddEthereumChainObject.class);
                        if (!TextUtils.isEmpty(chainObj.chainId)) {
                            context.post(() -> onWalletAddEthereumChainObjectListener.OnWalletAddEthereumChainObject(chainObj));
                        }
                    } catch (JsonSyntaxException e) {
                        if (GlobalConstant.DEBUG_MODEL)
                            e.printStackTrace();
                    }
                    break;
                case "switchEthereumChain":
                    JSONObject msgParams = obj.optJSONObject("object");
                    String chainId = msgParams.getString("chainId");
                    LogManage.e("chainId---->" + chainId);
                    context.post(() -> onSwitchEthChainListener.onSwitchEthereumChain(chainId));
                    break;
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDomainName() {
        return context == null ? "" : Utils.getDomainName(context.getUrl());
    }

    private String getUrl() {
        return context == null ? "" : context.getUrl();
    }
}