package com.roo.dapp.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.title.TitleBarView;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.EthereumNetworkBase;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.TransactionResult;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.TronTransferDappConfirmDialog;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.Web3jUtil;
import com.roo.dapp.R;
import com.roo.dapp.di.component.DaggerDappBrowserTronComponent;
import com.roo.dapp.mvp.beans.TronTransaction;
import com.roo.dapp.mvp.contract.DappBrowserTronContract;
import com.roo.dapp.mvp.interfaces.OnSignTronListener;
import com.roo.dapp.mvp.presenter.DappBrowserTronPresenter;
import com.roo.dapp.mvp.ui.dialog.DappMoreDialog;
import com.roo.dapp.mvp.ui.dialog.DialogAuthorizationDetail;
import com.roo.dapp.mvp.ui.dialog.DialogDescribe;
import com.roo.dapp.mvp.ui.dialog.GrantTronDialog;
import com.roo.dapp.mvp.utils.HexUtil;
import com.roo.dapp.mvp.utils.Sign;
import com.roo.dapp.mvp.utils.Utils;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DappBrowserTronFragment extends BaseFragment<DappBrowserTronPresenter> implements DappBrowserTronContract.View {

    public static final String TAG = DappBrowserTronFragment.class.getSimpleName();
    private LinkTokenBean.TokensBean tokensBean;
    private String address;
    private String url;
    private int chainId;
    private String rpcUrl;
    private DappBean dappBean;
    private MyWebView web3View;
    private ProgressBar progressBar;
    private UserWallet.ChainWallet chainWallet;
    private TitleBarView mTitleBarView;
    private boolean modeStandard = true;
    private long promiseId;

    public static DappBrowserTronFragment newInstance(LinkTokenBean.TokensBean tokensBean,
                                                      String url, boolean titileBarVisible) {
        Bundle args = new Bundle();
        DappBrowserTronFragment fragment = new DappBrowserTronFragment();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putString(Constants.KEY_URL, url);
        args.putBoolean(Constants.KEY_TITLE, titileBarVisible);
        fragment.setArguments(args);
        return fragment;
    }

    public static DappBrowserTronFragment newInstance(LinkTokenBean.TokensBean tokensBean,
                                                      DappBean dappBean, boolean titileBarVisible) {
        Bundle args = new Bundle();
        DappBrowserTronFragment fragment = new DappBrowserTronFragment();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putParcelable(Constants.KEY_MSG, dappBean);
        args.putBoolean(Constants.KEY_TITLE, titileBarVisible);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDappBrowserTronComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_webview_new, container, false);


        tokensBean = getArguments().getParcelable(Constants.KEY_DEFAULT);
        dappBean = getArguments().getParcelable(Constants.KEY_MSG);
        if (dappBean == null) {
            url = getArguments().getString(Constants.KEY_URL);
        } else {
            url = dappBean.getLinks();
            DappDaoManager.insertRecent(dappBean);
        }

        chainId = EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, tokensBean.getChainCode());
        rpcUrl = Web3jUtil.getInstance().getRpcUrl(tokensBean.getChainCode());
        web3View = inflate.findViewById(R.id.web3view);
        progressBar = inflate.findViewById(R.id.progressBar);
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(requireActivity());

        TitleBarView mTitleBarView = ViewHelper.initTitleBar(dappBean == null ? "" : dappBean.getName(),
                inflate, R.drawable.ic_common_back_black, this);
        mTitleBarView.getLinearLayout(Gravity.START).removeAllViews();
        chainWallet = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, tokensBean.getChainCode());
        address = Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress();

        if (userWallet.getPrivateKey() != null) {
            address = getPrivateKeyToAddress(userWallet.getPrivateKey());
        } else {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics(), "");
            address = hdWallet.getAddressForCoin(CoinType.TRON);
        }

        mTitleBarView = ViewHelper.initTitleBar(dappBean == null ? "" : dappBean.getName(),
                inflate, R.drawable.ic_common_back_black, this);
        mTitleBarView.getLinearLayout(Gravity.START).removeAllViews();

        boolean titileBarVisible = getArguments().getBoolean(Constants.KEY_TITLE, true);
        mTitleBarView.setVisibility(titileBarVisible ? View.VISIBLE : View.GONE);


        mTitleBarView.addLeftAction(mTitleBarView.new ImageAction(R.drawable.ic_common_back_black,
                v -> onKeyDown(KeyEvent.KEYCODE_BACK)));

        mTitleBarView.addLeftAction(mTitleBarView.new ImageAction(R.drawable.ic_fragment_close,
                v -> requireActivity().finish()));

        mTitleBarView.addRightAction(mTitleBarView.new ImageAction(R.drawable.icon_more,
                v -> {
                    DappMoreDialog.newInstance(dappBean).setOnClickedListener(new DappMoreDialog.OnClickedListener() {
                        @Override
                        public void onRefresh() {
                            web3View.loadUrl(Utils.formatUrl(url), getWeb3Headers());
                        }

                        @Override
                        public void onCopyLink() {
                            GlobalUtils.copyToClipboard(url, requireActivity());
                            ToastUtils.show(getString(R.string.copy_success));
                        }

                        @Override
                        public void onShare() {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, url);
                            intent.setType("text/plain");
                            //设置分享列表的标题，并且每次都显示分享列表
                            startActivity(Intent.createChooser(intent, getString(R.string.string_share_to)));
                        }

                        @Override
                        public void onSave() {
                            if (dappBean != null) {
                                if (DappDaoManager.isExist(dappBean.getName(), Constants.FAVORITE)) {
                                    DappDaoManager.delete(dappBean.getName(), Constants.FAVORITE);
                                    ToastUtils.show(getString(R.string.toast_cancel_favorite_dapp));
                                } else {
                                    if (DappDaoManager.insertFavorite(dappBean)) {
                                        ToastUtils.show(getString(R.string.saved));
                                    }
                                }
                            }
                        }

                        @Override
                        public void onChangeChain() {

                        }

                        @Override
                        public void onDismiss() {
                        }
                    }).show(getChildFragmentManager(), getClass().getSimpleName());
                }));
        LogManage.i(DappBrowserTronFragment.TAG, ">>initView()>>" + "chainId = [" + chainId + "], rpcUrl = [" + rpcUrl + "], address = [" + address + "], url = [" + url + "]");
        initWeb(web3View, url, address);

        return inflate;
    }

    private String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58.encode(decode);
    }

    private void initWeb(WebView webview, String url, String address) {
        WebView.setWebContentsDebuggingEnabled(true);
        WebSettings localWebSettings = webview.getSettings();
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        localWebSettings.setMediaPlaybackRequiresUserGesture(false);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setLoadWithOverviewMode(true);
        localWebSettings.setBlockNetworkImage(false);
        localWebSettings.setUseWideViewPort(true);
        web3View.addJavascriptInterface(new JavaScriptObject(webview, address, onSignTronListener), "roowallet");
        web3View.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webview, int newProgress) {
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
//                if (dappBean == null) {
//                    mTitleBarView.setTitleMainText(title);
//                    mTitleBarView.setTitleMainTextMarquee(true);
//                }
            }

        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.evaluateJavascript(loadProviderJs(), null);
                view.evaluateJavascript(connectWallet(), null);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (handler != null) {
                    handler.proceed();//忽略证书的错误继续加载页面内容，不会变成空白页面
                }
            }
        });
        webview.loadUrl(url);

//        webview.loadUrl("https://js-eth-sign.surge.sh/");


    }

    private String loadProviderJs() {

        String initSrc = Utils.loadFile(getContext(), R.raw.tronweb_new);
        return initSrc;
    }

    /* Required for CORS requests */
    private Map<String, String> getWeb3Headers() {
        //headers
        return new HashMap<String, String>() {{
            put("Connection", "close");
            put("Content-Type", "text/plain");
            put("Access-Control-Allow-Origin", "*");
            put("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            put("Access-Control-Max-Age", "600");
            put("Access-Control-Allow-Credentials", "true");
            put("Access-Control-Allow-Headers", "accept, authorization, Content-Type");
        }};
    }

    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web3View.canGoBack()) {
                web3View.goBack();
                return true;
            } else {
                requireActivity().finishAfterTransition();
            }
        }
        return false;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

//    private String injectVConsole() {
//        String VConsole = "  var oScript = document.createElement(\"script\");\n" +
//                "            oScript.src = \"https://cdn.bootcdn.net/ajax/libs/vConsole/3.9.0/vconsole.min.js\";\n" +
//                "            document.body.appendChild(oScript);\n" +
//                "\n" +
//                "            window.onload = function () {\n" +
//                "                var vConsole = new VConsole();\n" +
//                "            }";
//        return VConsole;
//    }

    private void injectScriptFile(WebView view, String scriptFile) {
        InputStream input;
        try {
            input = getActivity().getAssets().open(scriptFile);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();

            // String-ify the script byte-array using BASE64 encoding !!!
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script)" +
                    "})()");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadJsAdvanced() {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getContext());
        String privateKeyHex;
        byte[] privateKey;
        if (userWallet.getMnemonics() != null) {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics(), "");
            privateKey = hdWallet.getKeyForCoin(CoinType.TRON).data();
            privateKeyHex = HexUtil.byte2Hex(privateKey, false, false);
        } else {
            privateKeyHex = userWallet.getPrivateKey();
        }
        injectScriptFile(web3View, "TronWeb.js");
        web3View.loadUrl("javascript:  try{function myFunction()" +
                        "{" +
                        "if(!window.tronWeb){" +
                        " var HttpProvider = TronWeb.providers.HttpProvider;" +
                        " var fullNode = new HttpProvider('https://api.trongrid.io');" +
                        " var solidityNode = new HttpProvider('https://api.trongrid.io');" +
                        "var eventServer = 'https://api.trongrid.io/';" +
                        " var tronWeb = new TronWeb(" +
                        " fullNode," +
                        " solidityNode," +
                        " eventServer," +
                        "'" + privateKeyHex + "'" +
                        " );" +
//                    "tronWeb.setAddress(TronWeb.address.fromPrivateKey(\""+privateKey+"\"));"+
//                    "tronWeb.setPrivateKey('"+privateKey+"');"+
//                    "tronWeb.defaultPrivateKey=false;"+
//                    "tronWeb.setDefaultBlock('latest');"+
//                    "tronWeb.contract().address='TX9WvWkhCEF3J35HnvcenK3C5XrABCipfF';"+
//                    "tronWeb.isConnected();"+
                        "tronWeb.ready=true;" +
                        "window.tronWeb = tronWeb;" +
                        "console.log(window.tronWeb.defaultAddress.base58+'----'+tronWeb.ready);" +
                        "console.log(window.tronWeb.defaultPrivateKey+'----'+tronWeb.ready);" +
                        "console.log(window.tronWeb.trx.getBalance(window.tronWeb.defaultAddress.base58)+'----'+tronWeb.ready);" +
                        "};" +
                        "};" +
                        "myFunction();" +
                        "}catch(err){" +
                        "console.log('插入方法报错啦+'+err);" +
                        "};"
        );
    }

    private static String signTransaction2Byte(byte[] transactionBytes, byte[] privateKey) throws InvalidProtocolBufferException {

        ECKey ecKey = ECKey.fromPrivate(privateKey);
        byte[] hash = Sha256Hash.hash(transactionBytes);
        ECKey.ECDSASignature sign1 = ecKey.sign(hash);
        String s = sign1.toHex();
        return s;
    }

    private String createTransaction(String json) {
        String hexString = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            boolean transaction = jsonObject.has("transaction");
            if (transaction) {
                JSONObject transaction1 = jsonObject.optJSONObject("transaction");
                String txID = transaction1.optString("txID");
                String raw_data = transaction1.optString("raw_data");
                String raw_data_hex = transaction1.optString("raw_data_hex");

                String privateKeyHex;
                byte[] privateKey;
                UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getContext());
                if (userWallet.getMnemonics() != null) {
                    HDWallet hdWallet = new HDWallet(userWallet.getMnemonics(), "");
                    privateKey = hdWallet.getKeyForCoin(CoinType.TRON).data();
                    privateKeyHex = HexUtil.byte2Hex(privateKey, false, false);
                } else {
                    privateKeyHex = userWallet.getPrivateKey();
                }

                byte[] rawData = Numeric.hexStringToByteArray(raw_data_hex);
                String bytes = signTransaction2Byte(rawData, Numeric.hexStringToByteArray(privateKeyHex));
                ArrayList<String> signature = new ArrayList<>();
                signature.add(bytes);

                Map map = new HashMap();
                map.put("txID", txID);
                map.put("raw_data", raw_data);
                map.put("raw_data_hex", raw_data_hex);
                map.put("signature", signature);

                hexString = new Gson().toJson(map);

                LogManage.e("hexStrin---->" + hexString);
                return hexString;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return hexString;

    }

    private void showApproveDialog(TronTransaction transaction, long promiseId, String json) {
        GrantTronDialog grantDialog = GrantTronDialog.newInstance(transaction, dappBean, tokensBean);
        grantDialog.setOnClickedListener(new GrantTronDialog.OnClickedListener() {
            @Override
            public void onConfirm() {//确认
                KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(requireActivity());
                keyboardDialog.setCanceledOnTouchOutside(false);
                //取消授权
                keyboardDialog.setOnCancelClickListener(() -> canceledSign(promiseId));

                keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                    @Override
                    public void onPasswordComplete(String passWord) {
                        if (SafePassword.isEquals(passWord)) {
                            String hexString = createTransaction(json);
                            SignSuccess(promiseId, hexString);
                            keyboardDialog.dismiss();
                        } else {
                            ToastUtils.show(getString(R.string.false_safe_pass));
                            keyboardDialog.clearPassword();
                        }
                    }
                });
                keyboardDialog.show();
                keyboardDialog.getTitle().

                        setText(getString(R.string.keyboard_title_input));
                keyboardDialog.getTipsLayout().

                        setVisibility(View.GONE);
            }



            @Override
            public void onDetail() {
                DialogDescribe dialogAuthorizationDetail = DialogDescribe.newInstance();
                dialogAuthorizationDetail.show(getChildFragmentManager(), getClass().getSimpleName());
            }

        });
        //取消授权
        grantDialog.setOnCancelClickListener(() -> canceledSign(promiseId));
        grantDialog.show(

                getChildFragmentManager(), getClass().

                        getSimpleName());
    }

    private void showTransferDialog(TronTransaction transaction, long promiseId, String json) {

        long call_value = transaction.raw_data.contract.get(0).parameter.value.call_value;
        byte[] decode = Hex.decode(transaction.raw_data.contract.get(0).parameter.value.contract_address);
        String contract_address = Base58.encode(decode);
        TronTransferDappConfirmDialog transferDappConfirmDialog = TronTransferDappConfirmDialog.newInstance
                (address, contract_address,
                        Convert.fromWei(String.valueOf(call_value), Convert.Unit.MWEI).toPlainString(), tokensBean);

        transferDappConfirmDialog.setOnCancelClickListener(() -> {
            canceledSign(promiseId);
        });
        transferDappConfirmDialog.setOnEnsureClickedListener(new TronTransferDappConfirmDialog.OnEnsureClickedListener() {
            @Override
            public void onClick() {
                KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(requireActivity());
                keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                    @Override
                    public void onPasswordComplete(String passWord) {
                        if (SafePassword.isEquals(passWord)) {
                            String hexString = createTransaction(json);
                            SignSuccess(promiseId, hexString);
                            keyboardDialog.dismiss();
                            transferDappConfirmDialog.dismiss();
                        } else {
                            ToastUtils.show(getString(R.string.false_safe_pass));
                            keyboardDialog.clearPassword();
                        }
                    }
                });
                keyboardDialog.show();
                keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
                keyboardDialog.getTipsLayout().setVisibility(View.GONE);
            }

        });
        transferDappConfirmDialog.show(getChildFragmentManager(), getClass().getSimpleName());
    }


    private final OnSignTronListener onSignTronListener = new OnSignTronListener() {


        @Override
        public void onSignTronMessage(String message) {
            LogManage.e("message---->" + message);
            try {
                JSONObject jsonObject = new JSONObject(message);
                String s = jsonObject.optString("__roo__type__");
                promiseId = jsonObject.optLong("promiseId");
                LogManage.e("promiseId---->" + promiseId);
                switch (s) {
                    case "init wallet...":
                        LogManage.e("初始化js");
                        break;
                    case "init wallet successed":
                        LogManage.e("钱包链接成功");
                        break;

                    case "sign"://签名
                        Object transaction1 = jsonObject.get("transaction");

                        if (transaction1 instanceof String) {
                            String transaction = jsonObject.getString("transaction");
                            byte[] privateKeyByte;
                            UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getContext());
                            if (userWallet.getMnemonics() != null) {
                                HDWallet hdWallet = new HDWallet(userWallet.getMnemonics(), "");
                                privateKeyByte = hdWallet.getKeyForCoin(CoinType.TRON).data();
                            } else {
                                String privateKeyHex = userWallet.getPrivateKey();
                                privateKeyByte = ByteArray.fromHexString(privateKeyHex);
                            }
                            String s1 = Sign.signMessage(ByteArray.fromHexString(transaction), privateKeyByte);
                            SignMessage(promiseId, s1);

                        } else {
                            JSONObject transactionData = jsonObject.getJSONObject("transaction");
                            TronTransaction transaction = new Gson().fromJson(transactionData.toString(), TronTransaction.class);
                            if (transaction.raw_data.contract.size() > 0) {
                                String data = transaction.raw_data.contract.get(0).parameter.value.data;
                                if (data.endsWith("0000000000000000000000000000000000000000000000000000000000000000")
                                        || data.endsWith("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")) {//授权模式
                                    showApproveDialog(transaction, promiseId, message);
                                } else {//转账模式
                                    showTransferDialog(transaction, promiseId, message);
                                }
                            }
                        }

                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    };

    private void canceledSign(long id) {
        String Canceled = String.format(
                "window.resolvePromise(%s,null,\"cancle\")",
                id);
        LogManage.e("Canceled---->" + Canceled);
        web3View.evaluateJavascript(Canceled, null);
    }

    private void SignSuccess(long id, String data) {
        String success = String.format(
                "window.resolvePromise(%s," + data + ",null)",
                id);
        LogManage.e("success---->" + success);
        web3View.evaluateJavascript(success, null);
    }

    private void SignMessage(long id, String data) {
        String success = String.format(
                "window.resolvePromise(%s, \"" + data + "\" ,null)",
                id);
        LogManage.e("success---->" + success);
        web3View.evaluateJavascript(success, null);
    }

    private String fullNode = "https://api.trongrid.io/";
    private String solidityNode = "https://api.trongrid.io/";
    private String eventServer = "https://api.trongrid.io/";

    String connectWallet() {
        String source = String.format(
                "(function(){var config = {address: '%s', fullNode:'%s', solidityNode: '%s', eventServer: '%s'};var provider = new window.Roo(config); window.tronLink = provider;})();",
                address, fullNode, solidityNode, eventServer);

        return source;
    }
}