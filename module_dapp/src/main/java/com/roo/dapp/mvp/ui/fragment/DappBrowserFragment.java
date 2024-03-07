package com.roo.dapp.mvp.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
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
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.ChooseGasDialog;
import com.roo.core.ui.dialog.TransferDappConfirmDialog;
import com.roo.core.daoManagers.DappDaoManager;
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
import com.roo.dapp.mvp.beans.Address;
import com.roo.dapp.mvp.beans.EthereumMessage;
import com.roo.dapp.mvp.beans.EthereumTypedMessage;
import com.roo.dapp.mvp.beans.WalletAddEthereumChainObject;
import com.roo.dapp.mvp.beans.Web3Transaction;
import com.roo.dapp.mvp.contract.DappBrowserContract;
import com.roo.dapp.mvp.interfaces.DAppFunction;
import com.roo.dapp.mvp.interfaces.OnEthCallListener;
import com.roo.dapp.mvp.interfaces.OnSignMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignPersonalMessageListener;
import com.roo.dapp.mvp.interfaces.OnSignTransactionListener;
import com.roo.dapp.mvp.interfaces.OnSignTypedMessageListener;
import com.roo.dapp.mvp.interfaces.OnWalletAddEthereumChainObjectListener;
import com.roo.dapp.mvp.interfaces.SendTransactionInterface;
import com.roo.dapp.mvp.interfaces.Signable;
import com.roo.dapp.mvp.interfaces.URLLoadInterface;
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
import com.roo.dapp.mvp.utils.connect.WalletConnectHelperManager;
import com.roo.walletconnect.WalletConnectManager;
import com.xyzlf.custom.keyboardlib.KeyboardDialog;
import com.xyzlf.custom.keyboardlib.SimpleKeyboardListener;

import org.jetbrains.annotations.NotNull;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction;

public class DappBrowserFragment extends
        BaseFragment<DappBrowserPresenter> implements DappBrowserContract.View,
        OnSignTransactionListener, OnSignPersonalMessageListener,
        OnSignTypedMessageListener, OnSignMessageListener, OnWalletAddEthereumChainObjectListener,
        OnEthCallListener, URLLoadInterface {

    public static final String TAG = DappBrowserFragment.class.getSimpleName();

    public static final int UPLOAD_FILE = 1;
    public static final int REQUEST_CAMERA_ACCESS = 111;

    private String address;
    private String url;
    private int chainId;
    private String rpcUrl;
    private LinkTokenBean.TokensBean tokensBean;

    private WebChromeClient.FileChooserParams fileChooserParams;
    private Intent picker;
    private Web3View web3View;
    private ProgressBar progressBar;
    private Signable messageTBS;
    private DAppFunction dAppFunction;
    private GasBean gasBean;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private DappBean dappBean;
    private final String MOST_APPROVE = "987545688286543768315432397631987760547";
    private String approveAmount;

    public static DappBrowserFragment newInstance(LinkTokenBean.TokensBean tokensBean,
                                                  DappBean dappBean, boolean titileBarVisible) {
        Bundle args = new Bundle();
        DappBrowserFragment fragment = new DappBrowserFragment();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putParcelable(Constants.KEY_MSG, dappBean);
        args.putBoolean(Constants.KEY_TITLE, titileBarVisible);
        fragment.setArguments(args);
        return fragment;
    }

    public static DappBrowserFragment newInstance(LinkTokenBean.TokensBean tokensBean,
                                                  String url, boolean titileBarVisible) {
        Bundle args = new Bundle();
        DappBrowserFragment fragment = new DappBrowserFragment();
        args.putParcelable(Constants.KEY_DEFAULT, tokensBean);
        args.putString(Constants.KEY_URL, url);
        args.putBoolean(Constants.KEY_TITLE, titileBarVisible);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
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
        View inflate = inflater.inflate(R.layout.fragment_webview, container, false);

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
        web3View = inflate.findViewById(R.id.web3view);
        progressBar = inflate.findViewById(R.id.progressBar);
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(requireActivity());
        UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance()
                .getWalletByChainCode(userWallet, tokensBean.getChainCode());
        address = Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress();
        if (tokensBean.getChainCode().equals(ChainCode.TRON)){
            HDWallet hdWallet = new HDWallet(userWallet.getMnemonics().toString().trim(), "");
            address = hdWallet.getAddressForCoin(CoinType.TRON);
        }else {
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

        web3View.setWebLoadCallback(this);
        web3View.setActivity(requireActivity());

        web3View.setChainId(chainId);

        web3View.setRpcUrl(rpcUrl);
        web3View.setWalletAddress(new Address(address));
        LogManage.i(DappBrowserFragment.TAG, ">>initView()>>" + "chainId = [" + chainId + "], rpcUrl = [" + rpcUrl + "], address = [" + address + "]");

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
                if (dappBean == null) {
                    mTitleBarView.setTitleMainText(title);
                    mTitleBarView.setTitleMainTextMarquee(true);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fCParams) {
                if (filePathCallback == null) return true;
                fileChooserParams = fCParams;
                picker = fileChooserParams.createIntent();

                return requestUpload();
            }
        });

        web3View.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

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
        web3View.setOnSignMessageListener(this);
        web3View.setOnSignPersonalMessageListener(this);
        web3View.setOnSignTransactionListener(this);
        web3View.setOnSignTypedMessageListener(this);
        web3View.setOnEthCallListener(this);
        web3View.setOnWalletAddEthereumChainObjectListener(this);

        loadUrl(url);
        mPresenter. getGasDataSet(tokensBean.getChainCode());
        WalletConnectHelperManager.getInstance().initWalletConnect(getActivity(), chainId, address);
        return inflate;
    }

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

    private boolean checkWalletConnect(String result) {
        // 没有ETH地址，或者协议不对均不需要start wallet connect
//        if (TextUtils.isEmpty(ETHAddress) || !WalletConnectUtil.isWalletConnectProtocol(result)) {
//            return false;
//        }
        WalletConnectManager.getInstance().startConnect(getActivity(), result);
//        mvpView.doClickBackAction();
        return true;
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
        DappBrowserFragment.this.tokensBean = tokensBean;
        chainId = EthereumNetworkBase.getChainIdByChainCode(GlobalConstant.RPC_TYPE_MAIN, this.tokensBean.getChainCode());
        rpcUrl = Web3jUtil.getInstance().getRpcUrl(this.tokensBean.getChainCode());
        web3View.setChainId(chainId);
        web3View.setRpcUrl(rpcUrl);
        web3View.reload();
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable @org.jetbrains.annotations.Nullable Object data) {
        onLinkChanged((LinkTokenBean.TokensBean) data);
    }

    protected boolean requestUpload() {
        try {
            startActivityForResult(picker, UPLOAD_FILE);
        } catch (ActivityNotFoundException e) {
            ToastUtils.show("Cannot Open File Chooser");
            return false;
        }
        return true;
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
    public void onSignMessage(EthereumMessage message) {
        handleSignMessage(message);
    }

    @Override
    public void onSignPersonalMessage(EthereumMessage message) {
        handleSignMessage(message);
    }

    @Override
    public void onEthCall(Web3Call call) {
        Single.fromCallable(() -> {
            Web3j web3j = Web3jUtil.getInstance().getBlockConnect(tokensBean.getChainCode());
            Transaction transaction
                    = createEthCallTransaction(address, call.to.toString(), call.payload);
            return web3j.ethCall(transaction, call.blockParam).send();
        }).map(EthCall::getValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> web3View.onCallFunctionSuccessful(call.leafPosition, result),
                        error -> web3View.onCallFunctionError(call.leafPosition, error.getMessage()))
                .isDisposed();
    }

    private void handleSignMessage(Signable message) {
        LogManage.i(TAG, ">>handleSignMessage()>>" + "message = [" + message + "]");
        messageTBS = message;
        dAppFunction = new DAppFunction() {
            @Override
            public void DAppError(Throwable error, Signable message) {
                web3View.onSignCancel(message.getCallbackId());
            }

            @Override
            public void DAppReturn(byte[] data, Signable message) {
                LogManage.i(TAG, "Initial Msg: " + message.getMessage());
                String signHex = Numeric.toHexString(data);
                web3View.onSignMessageSuccessful(message, signHex);
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

    @Override
    public void onSignTransaction(Web3Transaction transaction, String url) {
        gasLimit = transaction.gasLimit;
        transaction.gasPrice = gasPrice;//赋值
        LogManage.e(DappBrowserFragment.TAG, "transaction---" + JSON.toJSONString(transaction));
        BigDecimal gasFeeNum = calculateGasFee(transaction);
        if (transaction.payload.endsWith(Constants.DAPP_APPROVE_ALL)) {
            GrantDialog grantDialog = GrantDialog.newInstance(transaction, dappBean, tokensBean, gasFeeNum.toPlainString());
            grantDialog.setOnClickedListener(new GrantDialog.OnClickedListener() {
                @Override
                public void onConfirm() {
                    KeyboardDialog keyboardDialog = KeyboardDialog.newInstance(requireActivity());
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
                                mPresenter.sendTransaction(tokensBean, transaction, chainId, new SendTransactionInterface() {
                                    @Override
                                    public void transactionSuccess(Web3Transaction web3Tx, String hashData) {
                                        approveAmount = MOST_APPROVE;
                                        web3View.onSignTransactionSuccessful(web3Tx, hashData);
                                    }

                                    @Override
                                    public void transactionError(long callbackId, Throwable error) {
                                        web3View.onSignCancel(transaction.leafPosition);
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
                    keyboardDialog.getTitle().setText(getString(R.string.keyboard_title_input));
                    keyboardDialog.getTipsLayout().setVisibility(View.GONE);
                }

                @Override
                public void onDetail(Web3Transaction transaction) {
                    DialogAuthorizationDetail dialogAuthorizationDetail = DialogAuthorizationDetail.newInstance(approveAmount, transaction.recipient.toString(),tokensBean.getChainCode());
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
            grantDialog.setOnCancelClickListener(() -> {
                web3View.onSignCancel(transaction.leafPosition);
            });
            grantDialog.show(getChildFragmentManager(), getClass().getSimpleName());
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
                                        web3View.onSignTransactionSuccessful(web3Tx, hashData);
                                    }

                                    @Override
                                    public void transactionError(long callbackId, Throwable error) {
                                        web3View.onSignCancel(transaction.leafPosition);
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
                web3View.onSignCancel(transaction.leafPosition);
            });
            transferDappConfirmDialog.show(getChildFragmentManager(), getClass().getSimpleName());
        }
    }

    /**
     * 计算gasFee
     */
    private BigDecimal calculateGasFee(Web3Transaction web3Transaction) {
        BigDecimal gasPriceWei = new BigDecimal(web3Transaction.gasPrice);
        String gasFee = gasPriceWei.multiply(new BigDecimal(web3Transaction.gasLimit)).toPlainString();
        return Convert.fromWei(gasFee, Convert.Unit.ETHER);
    }

    /**
     * Browse to relative entry with sanity check on value
     *
     * @param relative relative addition or subtraction of browsing index
     */
    private void loadSessionUrl(int relative) {
        WebBackForwardList sessionHistory = web3View.copyBackForwardList();
        int newIndex = sessionHistory.getCurrentIndex() + relative;
        if (newIndex < sessionHistory.getSize()) {
            WebHistoryItem newItem = sessionHistory.getItemAtIndex(newIndex);
            if (newItem != null) {
                setUrlText(newItem.getUrl());
            }
        }
    }

    @Override
    public void onWebpageLoaded(String url, String title) {
        LogManage.i(DappBrowserFragment.TAG, "url = [" + url + "], title = [" + title + "]");
        onWebpageLoadComplete();
    }

    @Override
    public void onWebpageLoadComplete() {
        LogManage.i(DappBrowserFragment.TAG, "onWebpageLoadComplete");
    }

    private boolean loadUrl(String urlText) {
        web3View.loadUrl(Utils.formatUrl(urlText), getWeb3Headers());
        setUrlText(Utils.formatUrl(urlText));
        return true;
    }

    private void setUrlText(String newUrl) {
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

    @Override
    public void onSignTypedMessage(EthereumTypedMessage message) {
        LogManage.e(Constants.LOG_STRING, ">>onSignTypedMessage()>>" + "message = [" + message + "]");
    }

    @Override
    public void OnWalletAddEthereumChainObject(WalletAddEthereumChainObject chainObject) {
        chainId = chainObject.getChainId();
        if (chainId != 0) {
            rpcUrl = chainObject.getRpcUrl();
            web3View.setChainId(chainId);
            web3View.setRpcUrl(rpcUrl);
            web3View.reload();
        } else {
            ToastUtils.show(R.string.toast_not_support_this_chain);
        }
    }

    @Override
    public void getGasDataSet(GasBean data) {
        this.gasBean = data;
        gasPrice = Convert.toWei(gasBean.getProposeGasPrice().toPlainString(), Convert.Unit.GWEI).toBigInteger();
    }

    @Override
    public Web3View getWeb3View() {
        return web3View;
    }


}
