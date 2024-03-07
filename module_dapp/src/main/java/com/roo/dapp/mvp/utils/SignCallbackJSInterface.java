package com.roo.dapp.mvp.utils;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.utils.LogManage;
import com.roo.dapp.mvp.beans.Address;
import com.roo.dapp.mvp.beans.EthereumMessage;
import com.roo.dapp.mvp.beans.EthereumTypedMessage;
import com.roo.dapp.mvp.beans.SignMessageType;
import com.roo.dapp.mvp.beans.WalletAddEthereumChainObject;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.interfaces.OnEthCallListener;
import com.roo.dapp.mvp.interfaces.OnSignMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignPersonalMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignTransactionListener;
import com.roo.dapp.mvp.interfaces.OnSignTypedMessageListener;
import com.roo.dapp.mvp.interfaces.OnWalletAddEthereumChainObjectListener;
import com.roo.dapp.mvp.ui.fragment.DappBrowserFragmentNew;
import com.roo.dapp.mvp.utils.autoWeb3.Web3Call;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;

public class SignCallbackJSInterface {
    private final WebView webView;
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

    public SignCallbackJSInterface(WebView webView,
                                   @NonNull OnSignTransactionListener onSignTransactionListener,
                                   @NonNull OnSignMessageListener onSignMessageListener,
                                   @NonNull OnSignPersonalMessageListener onSignPersonalMessageListener,
                                   OnSignTypedMessageListener onSignTypedMessageListener,
                                   @NotNull OnEthCallListener onEthCallListener,
                                   @NonNull OnWalletAddEthereumChainObjectListener onWalletAddEthereumChainObjectListener) {
        this.webView = webView;
        this.onSignTransactionListener = onSignTransactionListener;
        this.onSignMessageListener = onSignMessageListener;
        this.onSignPersonalMessageListener = onSignPersonalMessageListener;
        this.onSignTypedMessageListener = onSignTypedMessageListener;
        this.onEthCallListener = onEthCallListener;
        this.onWalletAddEthereumChainObjectListener = onWalletAddEthereumChainObjectListener;
    }

    /*交易*/
    @JavascriptInterface
    public void signTransaction(
            int callbackId,
            String recipient,
            String value,
            String nonce,
            String gasLimit,
            String gasPrice,
            String payload) {
        LogManage.i(DappBrowserFragmentNew.TAG, ">>signTransaction()>>" + "callbackId = [" + callbackId + "], recipient = [" + recipient + "], value = [" + value + "], nonce = [" + nonce + "], gasLimit = [" + gasLimit + "], gasPrice = [" + gasPrice + "], payload = [" + payload + "]");
        if (value == null || value.equals("undefined")) value = "0";
        if (gasPrice == null) gasPrice = "1";
        if (gasLimit == null) gasLimit = "1";
        Web3Transaction transaction = new Web3Transaction(
                TextUtils.isEmpty(recipient) ? Address.EMPTY : new Address(recipient),
                null,
                Hex.hexToBigInteger(value),
                Hex.hexToBigInteger(gasPrice, new BigInteger("10")),
                Hex.hexToBigInteger(gasLimit, new BigInteger("21000")),
                payload,
                callbackId);

        webView.post(() -> {
            onSignTransactionListener.onSignTransaction(transaction, getUrl());
        });
    }

    /*签名*/
    @JavascriptInterface
    public void signMessage(int callbackId, String data) {
        LogManage.i(DappBrowserFragmentNew.TAG, ">>signMessage()>>" + "callbackId = [" + callbackId + "], data = [" + data + "]");

        webView.post(() -> onSignMessageListener.onSignMessage(new EthereumMessage(data, getUrl(), callbackId, SignMessageType.SIGN_MESSAGE)));
    }

    @JavascriptInterface
    public void signPersonalMessage(int callbackId, String data) {
        LogManage.i(DappBrowserFragmentNew.TAG, ">>signPersonalMessage()>>" + "callbackId = [" + callbackId + "], data = [" + data + "]");

        webView.post(() -> onSignPersonalMessageListener.onSignPersonalMessage(new EthereumMessage(data, getUrl(), callbackId, SignMessageType.SIGN_PERSONAL_MESSAGE)));
    }

    @JavascriptInterface
    public void signTypedMessage(int callbackId, String data) {
        LogManage.e(Constants.LOG_STRING, ">>signTypedMessage()>>" + "callbackId = [" + callbackId + "], data = [" + data + "]");
        try {
            JSONObject obj = new JSONObject(data);
            String address = obj.getString("from");
            String messageData = obj.getString("data");
            CryptoFunctions cryptoFunctions = new CryptoFunctions();
            EthereumTypedMessage message = new EthereumTypedMessage(messageData, getDomainName(), callbackId, cryptoFunctions);
            onSignTypedMessageListener.onSignTypedMessage(message);
        } catch (Exception e) {
            EthereumTypedMessage message = new EthereumTypedMessage(null, "", getDomainName(), callbackId);
            onSignTypedMessageListener.onSignTypedMessage(message);
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void ethCall(int callbackId, String recipient, String payload) {
        //LogManage.i(DappBrowserActivity.TAG, ">>ethCall()>>" + "callbackId = [" + callbackId + "], recipient = [" + recipient + "], payload = [" + payload + "]");

        DefaultBlockParameter defaultBlockParameter;
        if (payload.equals("undefined")) payload = "0x";
        defaultBlockParameter = DefaultBlockParameterName.LATEST;

        Web3Call call = new Web3Call(
                new Address(recipient),
                defaultBlockParameter,
                payload,
                callbackId);

        webView.post(() -> onEthCallListener.onEthCall(call));
    }


    @JavascriptInterface
    public void walletAddEthereumChain(int callbackId, String msgParams) {
        //TODO: Implement custom chains from dapp browser: see OnWalletAddEthereumChainObject in class DappBrowserFragment
        //First draft: attempt to match this chain with known chains; switch to known chain if we match
        try {
            WalletAddEthereumChainObject chainObj = new Gson().fromJson(msgParams, WalletAddEthereumChainObject.class);
            if (!TextUtils.isEmpty(chainObj.chainId)) {
                webView.post(() -> onWalletAddEthereumChainObjectListener.OnWalletAddEthereumChainObject(chainObj));
            }
        } catch (JsonSyntaxException e) {
            if (GlobalConstant.DEBUG_MODEL)
                e.printStackTrace();
        }
    }

    private String getUrl() {
        return webView == null ? "" : webView.getUrl();
    }

    private String getDomainName() {
        return webView == null ? "" : Utils.getDomainName(webView.getUrl());
    }
}
