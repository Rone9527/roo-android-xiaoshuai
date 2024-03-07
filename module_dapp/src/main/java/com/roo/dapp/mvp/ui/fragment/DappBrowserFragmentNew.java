package com.roo.dapp.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.aries.ui.view.title.TitleBarView;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.EthereumNetworkBase;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.GasBean;
import com.core.domain.trade.SignatureFromKey;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.ChooseGasDialog;
import com.roo.core.ui.dialog.TransferDappConfirmDialog;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.Web3jUtil;
import com.roo.dapp.R;
import com.roo.dapp.di.component.DaggerDappBrowserComponent;
import com.roo.dapp.mvp.beans.EthereumMessage;
import com.roo.dapp.mvp.beans.EthereumTypedMessage;
import com.roo.dapp.mvp.beans.SignMessageType;
import com.roo.dapp.mvp.beans.WalletAddEthereumChainObject;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.contract.DappBrowserContract;
import com.roo.dapp.mvp.interfaces.DAppFunction;
import com.roo.dapp.mvp.interfaces.OnEthCallListener;
import com.roo.dapp.mvp.interfaces.OnRequestAccountsListener;
import com.roo.dapp.mvp.interfaces.OnSignMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignPersonalMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignTransactionListener;
import com.roo.dapp.mvp.interfaces.OnSignTypedMessageListener;
import com.roo.dapp.mvp.interfaces.OnSwitchEthChainListener;
import com.roo.dapp.mvp.interfaces.OnWalletAddEthereumChainObjectListener;
import com.roo.dapp.mvp.interfaces.SendTransactionInterface;
import com.roo.dapp.mvp.interfaces.Signable;
import com.roo.dapp.mvp.presenter.DappBrowserPresenter;
import com.roo.dapp.mvp.ui.dialog.DappChooseLinkDialog;
import com.roo.dapp.mvp.ui.dialog.DappMoreDialog;
import com.roo.dapp.mvp.ui.dialog.DialogAuthorizationDetail;
import com.roo.dapp.mvp.ui.dialog.GrantCountDialog;
import com.roo.dapp.mvp.ui.dialog.GrantDialog;
import com.roo.dapp.mvp.utils.Numeric;
import com.roo.dapp.mvp.utils.Utils;
import com.roo.dapp.mvp.utils.autoWeb3.Web3Call;
import com.roo.dapp.mvp.utils.autoWeb3.Web3View;
import com.roo.walletconnect.WalletConnectManager;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.jetbrains.annotations.NotNull;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //    ┃　　　┃   神兽保佑
 * //    ┃　　　┃   代码无BUG！
 * //    ┃　　　┗━━━┓
 * //    ┃　　　　　　　┣┓
 * //    ┃　　　　　　　┏┛
 * //    ┗┓┓┏━┳┓┏┛
 * //      ┃┫┫　┃┫┫
 * //      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/8/20
 * PackageName : com.roo.dapp.mvp.ui.fragment
 * Description :
 */

public class DappBrowserFragmentNew extends BaseFragment<DappBrowserPresenter> implements DappBrowserContract.View {

    public static final String TAG = DappBrowserFragmentNew.class.getSimpleName();

    public static final int UPLOAD_FILE = 1;
    public static final int REQUEST_CAMERA_ACCESS = 111;

    private String address;
    private String url;
    private int chainId;
    private String rpcUrl;
    private LinkTokenBean.TokensBean tokensBean;

    private WebChromeClient.FileChooserParams fileChooserParams;
    private Intent picker;

    private ProgressBar progressBar;
    private Signable messageTBS;
    private DAppFunction dAppFunction;
    private GasBean gasBean;
    private BigInteger gasPrice;
    private BigInteger gasLimit = new BigInteger("21000");//默认给个21000上线
    private BigInteger nonce;
    private UserWallet.ChainWallet chainWallet;
    private DappBean dappBean;
    private final String MOST_APPROVE = "987545688286543768315432397631987760547";
    private String approveAmount;
    private MyWebView web3View;

    public static DappBrowserFragmentNew newInstance(LinkTokenBean.TokensBean tokensBean,
                                                     DappBean dappBean, boolean titileBarVisible) {
        Bundle args = new Bundle();
        DappBrowserFragmentNew fragment = new DappBrowserFragmentNew();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putParcelable(Constants.KEY_MSG, dappBean);
        args.putBoolean(Constants.KEY_TITLE, titileBarVisible);
        fragment.setArguments(args);
        return fragment;
    }

    public static DappBrowserFragmentNew newInstance(LinkTokenBean.TokensBean tokensBean,
                                                     String url, boolean titileBarVisible) {
        Bundle args = new Bundle();
        DappBrowserFragmentNew fragment = new DappBrowserFragmentNew();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putString(Constants.KEY_URL, url);
        args.putBoolean(Constants.KEY_TITLE, titileBarVisible);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerDappBrowserComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_webview_new, container, false);

        approveAmount = MOST_APPROVE;

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
//        rpcUrl="https://bsc-dataseed.binance.org/";
//        rpcUrl="https://binance.ankr.com/";
        web3View = inflate.findViewById(R.id.web3view);
        progressBar = inflate.findViewById(R.id.progressBar);
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(requireActivity());
        chainWallet = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, tokensBean.getChainCode());
        address = Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress();
        if (tokensBean.getChainCode().equals(ChainCode.TRON)) {
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics().toString().trim(), "");
            address = hdWallet.getAddressForCoin(CoinType.TRON);
        } else {
            address = Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress();
        }
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(dappBean == null ? "" : dappBean.getName(),
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

//                            web3View.evaluateJavascript("window.ethereum.isConnected()", new ValueCallback<String>() {
//                                @Override
//                                public void onReceiveValue(String s) {
//                                    LogManage.e("window.ethereum--->"+s);
//                                }
//                            });

//                            web3View.evaluateJavascript("const yourAddress = '0x0c54FcCd2e384b4BB6f2E405Bf5Cbc15a017AaFb'\n" +
//                                    "const value = '0xde0b6b3a7640000'\n" +
//                                    "const desiredNetwork = 56\n" +
//                                    "\n" +
//                                    "if (typeof window.ethereum === 'undefined') {\n" +
//                                    "    alert('Looks like you need a Dapp browser to get started.')\n" +
//                                    "    alert('Consider installing MetaMask!')\n" +
//                                    "\n" +
//                                    "} else {\n" +
//                                    "\n" +
//                                    "    ethereum.enable()\n" +
//                                    "\n" +
//                                    "        .catch(function (reason) {\n" +
//                                    "            if (reason === 'User rejected provider access') {\n" +
//                                    "            } else {\n" +
//                                    "                alert('There was an issue signing you in.')\n" +
//                                    "            }\n" +
//                                    "        })\n" +
//                                    "\n" +
//                                    "        .then(function (accounts) {\n" +
//                                    "            if (ethereum.networkVersion !== desiredNetwork) {\n" +
//                                    "                alert('This application requires the main network, please switch it in your MetaMask UI.'+ethereum.networkVersion+desiredNetwork)\n" +
//                                    "            }\n" +
//                                    "            const account = accounts[0]\n" +" console.log('account---'+account)"+
//
//                                    "\n" +
//                                    "        })\n" +
//                                    "}", new ValueCallback<String>() {
//                                @Override
//                                public void onReceiveValue(String s) {
//                                    LogManage.e("window.ethereum--->"+s);
//                                }
//                            });
//
//                            web3View.evaluateJavascript("window.ethereum.enable()", new ValueCallback<String>() {
//                                @Override
//                                public void onReceiveValue(String s) {
//                                    LogManage.e(".ethereum.enable--->"+s);
//                                }
//                            });
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
                            onLinkChangeDialog();
                        }

                        @Override
                        public void onDismiss() {
                        }
                    }).show(getChildFragmentManager(), getClass().getSimpleName());
                }));
        LogManage.i(DappBrowserFragmentNew.TAG, ">>initView()>>" + "chainId = [" + chainId + "], rpcUrl = [" + rpcUrl + "], address = [" + address + "]");
        initWeb(web3View, url, address);

        mPresenter.getGasDataSet(tokensBean.getChainCode());
        return inflate;
    }

    private void onLinkChangeDialog() {
        DappChooseLinkDialog.newInstance().setOnClickListener((item) -> {
            for (LinkTokenBean.TokensBean tokensBean : item.getTokens()) {
                if (tokensBean.getChainCode().equalsIgnoreCase(item.getCode())) {
                    onLinkChanged(tokensBean);
                    break;
                }
            }
        }).show(getChildFragmentManager(), getClass().getSimpleName());
    }

    private void onLinkChanged(LinkTokenBean.TokensBean tokensBean) {
        DappBrowserFragmentNew.this.tokensBean = tokensBean;
        chainId = EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, this.tokensBean.getChainCode());
        rpcUrl = Web3jUtil.getInstance().getRpcUrl(this.tokensBean.getChainCode());
        web3View.reload();
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

    private String loadProviderJs() {

        String initSrc = Utils.loadFile(getContext(), R.raw.trust_min);
        return initSrc;
    }

    String connectWallet() {
        String source = "(function() {\n" +
                "    var config = {\n" +
                "        chainId:" + chainId + "  ,\n" +
                "        rpcUrl: \"" + rpcUrl + "\",\n" +
                "        address: \"" + address + "\",\n" +
                "        isDebug: true\n" +
                "    };\n" +
                "    window.ethereum = new trustwallet.Provider(config);\n" +

                "    window.web3 = new trustwallet.Web3(window.ethereum);\n" +
                "    trustwallet.postMessage = (json) => {\n" +
                "    window._tw_.postMessage(JSON.stringify(json));\n" +
                "    }\n" +
                "})();";
        return source;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @SuppressLint("SetJavaScriptEnabled")
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
        webview.addJavascriptInterface(new WebAppInterface(
                webview, address,
                onRequestAccountsListener,
                innerOnSignTransactionListener,
                innerOnSignMessageListener,
                innerOnSignPersonalMessageListener,
                innerOnSignTypedMessageListener,
                innerOnEthCallListener,
                innerAddChainListener,
                innerSwitchChainListener), "_tw_");

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
                view.evaluateJavascript(injectVConsole(), null);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                if (handler != null) {
                    handler.proceed();//忽略证书的错误继续加载页面内容，不会变成空白页面
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] prefixCheck = url.split(":");
                if (prefixCheck.length > 1) {
                    Intent intent;
                    switch (prefixCheck[0]) {
                        case Constants.DAPP_PREFIX_TELEPHONE: {
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(url));
                            startActivity(Intent.createChooser(intent, "Call " + prefixCheck[1]));
                        }
                        return true;
                        case Constants.DAPP_PREFIX_MAILTO: {
                            intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse(url));
                            startActivity(Intent.createChooser(intent, "Email: " + prefixCheck[1]));
                        }
                        return true;
                        case Constants.DAPP_PREFIX_WALLETCONNECT: {
                            //start walletconnect
                            WalletConnectManager.getInstance().startConnect(getActivity(), url);
                        }
                        return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        webview.loadUrl(url);


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
    public void getGasDataSet(GasBean data) {
        this.gasBean = data;
        gasLimit = BigInteger.valueOf(data.getGasLimit().intValue());
        gasPrice = Convert.toWei(gasBean.getProposeGasPrice().toPlainString(), Convert.Unit.GWEI).toBigInteger();
    }

    @Override
    public Web3View getWeb3View() {
        return null;
    }


    @Override
    public void showMessage(@NonNull String message) {

    }


    /**
     * 计算gasFee
     */
    private BigDecimal calculateGasFee(Web3Transaction web3Transaction) {
        BigDecimal gasPriceWei = new BigDecimal(web3Transaction.gasPrice);
        String gasFee = gasPriceWei.multiply(new BigDecimal(web3Transaction.gasLimit)).toPlainString();
        return Convert.fromWei(gasFee, Convert.Unit.ETHER);
    }

    private final OnSignTransactionListener innerOnSignTransactionListener = new OnSignTransactionListener() {
        @Override
        public void onSignTransaction(Web3Transaction transaction, String url) {
            if (transaction.gasLimit.intValue() == 0) {//如果dapp 没有该值 用 接口的
                transaction.gasLimit = gasLimit;
            } else {
                gasLimit = transaction.gasLimit;
                gasBean.setGasLimit(BigDecimal.valueOf(gasLimit.longValue()));
            }
            if (transaction.gasPrice.intValue() == 0) {
                transaction.gasPrice = gasPrice;
            } else {
                gasPrice = transaction.gasPrice;
            }

            LogManage.e(DappBrowserFragment.TAG, "transaction---" + JSON.toJSONString(transaction));
            BigDecimal gasFeeNum = calculateGasFee(transaction);
            if (transaction.payload.endsWith(Constants.DAPP_APPROVE_ALL)) {
                GrantDialog grantDialog = GrantDialog.newInstance(transaction, dappBean, tokensBean, gasFeeNum.toPlainString());
                grantDialog.setOnClickedListener(new GrantDialog.OnClickedListener() {
                    @Override
                    public void onConfirm() {
                        KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(requireActivity());
                        keyboardDialog.setCanceledOnTouchOutside(false);
                        //取消授权
                        keyboardDialog.setOnCancelClickListener(() ->

                        {
                            String Canceled = "window.ethereum.sendError(" + transaction.leafPosition + ", [\"Canceled\"])";
                            web3View.evaluateJavascript(Canceled, null);
                        });
                        keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                            @Override
                            public void onPasswordComplete(String passWord) {
                                if (SafePassword.isEquals(passWord)) {
                                    if (!approveAmount.equals(MOST_APPROVE)) {
                                        Function function = new Function(
                                                "approve",//参数1：  智能合约里面的函数名称
                                                //参数2： 智能合约要执行的这个函数要传入的参数列表
                                                Arrays.asList(new org.web3j.abi.datatypes.Address(transaction.recipient.toString()),
                                                        new Uint256(Convert.toWei(approveAmount, Convert.Unit.ETHER).toBigInteger())),
                                                //参数3： 智能合约要执行的这个函数的返回值的类型
                                                Collections.singletonList(new TypeReference<Type>() {
                                                }));
                                        //解析function，以便于调用
                                        String encode = FunctionEncoder.encode(function);
                                        transaction.payload = transaction.payload.replace(Constants.DAPP_APPROVE_ALL, encode.substring(74));
                                    }

                                    LogManage.e(DappBrowserFragment.TAG, "transaction---" + JSON.toJSONString(transaction));
                                    mPresenter.sendTransaction(tokensBean, transaction, chainId, new SendTransactionInterface() {
                                        @Override
                                        public void transactionSuccess(Web3Transaction web3Tx, String hashData) {
                                            LogManage.e("transactionSuccess----->" + hashData);
                                            approveAmount = MOST_APPROVE;
                                            String sendResponse = "window.ethereum.sendResponse(" + transaction.leafPosition + ",\"" + hashData + "\")";
                                            web3View.evaluateJavascript(sendResponse, null);
                                        }

                                        @Override
                                        public void transactionError(long callbackId, Throwable error) {
                                            LogManage.e("transactionError---->" + error.getMessage());
                                            String Canceled = "window.ethereum.sendError(" + transaction.leafPosition + ", [\"Canceled\"])";
                                            web3View.evaluateJavascript(Canceled, null);
                                        }
                                    });
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
                    public void onDetail(Web3Transaction transaction) {
                        DialogAuthorizationDetail dialogAuthorizationDetail = DialogAuthorizationDetail
                                .newInstance(approveAmount, transaction.recipient.toString(), tokensBean.getChainCode());
                        dialogAuthorizationDetail.setOnClickedListener(new DialogAuthorizationDetail.OnClickedListener() {
                            @Override
                            public void onCancel(String newAmount) {
                                approveAmount = newAmount;
                            }

                            @Override
                            public void onChangeAmount(String amountChange) {
                                GrantCountDialog.newInstance(amountChange)
                                        .setOnClickListener(newAmount -> {
                                            if (!TextUtils.isEmpty(newAmount)) {
                                                dialogAuthorizationDetail.setAuthAmount(newAmount);
                                            }
                                        })
                                        .show(getChildFragmentManager(), getClass().getSimpleName());
                            }
                        });
                        dialogAuthorizationDetail.show(getChildFragmentManager(), getClass().getSimpleName());

                    }

                    @Override
                    public void onGasFee() {
                        ChooseGasDialog chooseGasDialog = ChooseGasDialog.newInstance(tokensBean, gasBean, transaction.gasLimit.toString(), address, gasFeeNum.toPlainString());
                        chooseGasDialog.setOnClickedListener((gasPrice, gasLimit) -> {
                            if (grantDialog.isVisible()) {
                                transaction.gasPrice = Convert.toWei(gasPrice, Convert.Unit.GWEI).toBigInteger();
                                transaction.gasLimit = gasLimit.toBigInteger();
                                grantDialog.setNewGasFee(calculateGasFee(transaction));
                            }
                        });
                        chooseGasDialog.show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                });
                //取消授权
                grantDialog.setOnCancelClickListener(() ->

                {
                    String Canceled = "window.ethereum.sendError(" + transaction.leafPosition + ", [\"Canceled\"])";
                    web3View.evaluateJavascript(Canceled, null);
                });
                grantDialog.show(

                        getChildFragmentManager(), getClass().

                                getSimpleName());
            } else {
                TransferDappConfirmDialog transferDappConfirmDialog = TransferDappConfirmDialog.newInstance
                        (address, transaction.recipient.toString(), Convert.fromWei(transaction.value.toString(), Convert.Unit.ETHER).toPlainString(), tokensBean, gasFeeNum);

                transferDappConfirmDialog.setOnEnsureClickedListener(new TransferDappConfirmDialog.OnEnsureClickedListener() {
                    @Override
                    public void onClick() {
                        KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(requireActivity());
                        keyboardDialog.setKeyboardLintener(new SimpleKeyboardListener() {
                            @Override
                            public void onPasswordComplete(String passWord) {
                                if (SafePassword.isEquals(passWord)) {
                                    mPresenter.sendTransaction(tokensBean, transaction, chainId, new SendTransactionInterface() {
                                        @Override
                                        public void transactionSuccess(Web3Transaction web3Tx, String hashData) {
                                            String sendResponse = "window.ethereum.sendResponse(" + transaction.leafPosition + ",\"" + hashData + "\")";
                                            web3View.evaluateJavascript(sendResponse, null);
                                        }

                                        @Override
                                        public void transactionError(long callbackId, Throwable error) {
                                            String Canceled = "window.ethereum.sendError(" + transaction.leafPosition + ", [\"Canceled\"])";
                                            web3View.evaluateJavascript(Canceled, null);
                                        }
                                    });
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

                    @Override
                    public void setGas() {
                        LogManage.e("gasFeeNumxxxxx" + gasFeeNum.toPlainString());
                        ChooseGasDialog.newInstance(tokensBean, gasBean, transaction.gasLimit.toString(), address, gasFeeNum.toPlainString())
                                .setOnClickedListener((gasPrice, gasLimit) -> {
                                    if (transferDappConfirmDialog.isVisible()) {
                                        transaction.gasPrice = Convert.toWei(gasPrice, Convert.Unit.GWEI).toBigInteger();
                                        transaction.gasLimit = gasLimit.toBigInteger();
                                        transferDappConfirmDialog.setNewGasFee(calculateGasFee(transaction));
                                    }
                                }).show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                });
                transferDappConfirmDialog.setOnCancelClickListener(() -> {
                    String Canceled = "window.ethereum.sendError(" + transaction.leafPosition + ", [\"Canceled\"])";
                    web3View.evaluateJavascript(Canceled, null);
                });
                transferDappConfirmDialog.show(getChildFragmentManager(), getClass().getSimpleName());
            }

        }
    };

    private String injectVConsole() {
        String VConsole = "  var oScript = document.createElement(\"script\");\n" +
                "            oScript.src = \"https://cdn.bootcdn.net/ajax/libs/vConsole/3.9.0/vconsole.min.js\";\n" +
                "            document.body.appendChild(oScript);\n" +
                "\n" +
                "            window.onload = function () {\n" +
                "                var vConsole = new VConsole();\n" +
                "            }";
        return VConsole;
    }

    private final OnSignMessageListener innerOnSignMessageListener = new OnSignMessageListener() {
        @Override
        public void onSignMessage(EthereumMessage message) {
            handleSignMessage(message);
        }
    };

    private final OnSignPersonalMessageListener innerOnSignPersonalMessageListener = new OnSignPersonalMessageListener() {
        @Override
        public void onSignPersonalMessage(EthereumMessage message) {
            handleSignMessage(message);
        }
    };
    private final OnSignTypedMessageListener innerOnSignTypedMessageListener = new OnSignTypedMessageListener() {
        @Override
        public void onSignTypedMessage(EthereumTypedMessage message) {
            if (message.getPrehash() == null || message.getMessageType() == SignMessageType.SIGN_ERROR) {
                onSignCancel(message.getCallbackId());
            } else {
                handleSignMessage(message);
            }
        }
    };
    private final OnEthCallListener innerOnEthCallListener = new OnEthCallListener() {
        @Override
        public void onEthCall(Web3Call txData) {

        }
    };
    private final OnWalletAddEthereumChainObjectListener innerAddChainListener = new OnWalletAddEthereumChainObjectListener() {
        @Override
        public void OnWalletAddEthereumChainObject(WalletAddEthereumChainObject chainObject) {
            chainId = chainObject.getChainId();
            if (chainId != 0) {
                rpcUrl = chainObject.getRpcUrl();
//                web3View.evaluateJavascript(loadProviderJs(), null);
//                web3View.evaluateJavascript(connectWallet(), null);
                web3View.reload();
            } else {
                ToastUtils.show(R.string.toast_not_support_this_chain);
            }
        }
    };

    private final OnSwitchEthChainListener innerSwitchChainListener = new OnSwitchEthChainListener() {

        @Override
        public void onSwitchEthereumChain(String callback) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogManage.e("xxxxxxxxxxxxxxxxxxxxx");

//                    web3View.evaluateJavascript("try {\n" +
//                            "    await window.ethereum.request({\n" +
//                            "        method: 'wallet_switchEthereumChain',\n" +
//                            "        params: [{chainId: 56, networkVersion: 56}],\n" +
//                            "    });\n" +
//                            "} catch (switchError) {\n" +
//                            "    console.log(\"switchError--->\" + switchError)\n" +
//                            "    // This error code indicates that the chain has not been added to MetaMask.\n" +
//                            "    if (switchError.code === 4902) {\n" +
//                            "        try {\n" +
//                            "            await window.ethereum.request({\n" +
//                            "                method: 'wallet_addEthereumChain',\n" +
//                            "                params: [{chainId: 56, rpcUrl: 'https://bsc-dataseed.binance.org/'}],\n" +
//                            "            }).then(() => {\n" +
//                            "                console.log('网络切换成功')\n" +
//                            "            });\n" +
//                            "        } catch (addError) {\n" +
//                            "            console.log(addError)\n" +
//                            "        }\n" +
//                            "    }\n" +
//                            "    // handle other \"switch\" errors\n" +
//                            "}", new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String s) {
//
//                        }
//                    });
                }
            });

        }
    };
    private final OnRequestAccountsListener onRequestAccountsListener = new OnRequestAccountsListener() {

        @Override
        public void onRequestAccounts(String callback) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    web3View.evaluateJavascript("window.ethereum.chainId", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

//                            switch (s) {
//                                case "56"://BSC
//                                    if (chainId != 56) {//不同链，才切换
//                                        web3View.evaluateJavascript();
//                                        chainId = 56;
//                                        rpcUrl = Web3jUtil.getInstance().getRpcUrl(ChainCode.BSC);
//                                        web3View.reload();
//                                    }
//
//                                    break;
//                                case "128"://HECO
//                                    if (chainId != 128) {
//                                        chainId = 128;
//                                        rpcUrl = Web3jUtil.getInstance().getRpcUrl(ChainCode.HECO);
//                                        web3View.reload();
//                                    }
//
//                                    break;
//                            }
                        }
                    });
                    web3View.evaluateJavascript(callback, null);
                }
            });

        }
    };

    private void handleSignMessage(Signable message) {
        LogManage.i(TAG, ">>handleSignMessage()>>" + "message = [" + message + "]");
        messageTBS = message;
        dAppFunction = new DAppFunction() {
            @Override
            public void DAppError(Throwable error, Signable message) {
                String Canceled = "window.ethereum.sendError(" + message.getCallbackId() + ", [\"Canceled\"])";
                web3View.evaluateJavascript(Canceled, null);
            }

            @Override
            public void DAppReturn(byte[] data, Signable message) {
                LogManage.i(TAG, "Initial Msg: " + message.getMessage());
                String signHex = Numeric.toHexString(data);
                String sendResponse = "window.ethereum.sendResponse(" + message.getCallbackId() + ",\"" + signHex + "\")";
                web3View.evaluateJavascript(sendResponse, null);
            }
        };

        RxUtils.makeObservable(new Callable<SignatureFromKey>() {
            @Override
            public SignatureFromKey call() {
                return mPresenter.signMessage(messageTBS, tokensBean);
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> dAppFunction.DAppReturn(t.signature, messageTBS),
                        e -> dAppFunction.DAppError(e, messageTBS));


    }

    public void onSignCancel(long callbackId) {
        String Canceled = "window.ethereum.sendError(" + callbackId + ", [\"Canceled\"])";
        web3View.evaluateJavascript(Canceled, null);
    }

    @Override
    public void onDestroy() {
        if (web3View != null) {
            web3View.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web3View.clearHistory();
            ((ViewGroup) web3View.getParent()).removeView(web3View);
            web3View.destroy();
            web3View = null;
        }
        super.onDestroy();
    }
}

