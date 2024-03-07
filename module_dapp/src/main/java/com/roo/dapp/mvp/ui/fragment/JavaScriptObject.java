package com.roo.dapp.mvp.ui.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.dapp.R;
import com.roo.dapp.mvp.beans.EthereumMessage;
import com.roo.dapp.mvp.beans.SignMessageType;
import com.roo.dapp.mvp.interfaces.OnRequestAccountsListener;
import com.roo.dapp.mvp.interfaces.OnSignTronListener;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

public class JavaScriptObject {
    public static final String obj = "iTron";   //统一的类名
    private WebView context;
    @NonNull
    private final OnSignTronListener onSignTronListener;
    private String address;
    public JavaScriptObject(WebView context, String address,
                            @NonNull OnSignTronListener onSignTronListener) {
        this.context = context;
        this.address = address;
        this.onSignTronListener = onSignTronListener;
    }

    @JavascriptInterface
    public void postMessage(String json) {
        LogManage.e("json---->"+json);
        context.post(() -> onSignTronListener.onSignTronMessage(json));
    }
}