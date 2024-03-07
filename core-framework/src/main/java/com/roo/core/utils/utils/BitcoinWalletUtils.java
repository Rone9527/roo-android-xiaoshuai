package com.roo.core.utils.utils;

import android.content.Context;
import android.text.TextUtils;

import com.roo.core.utils.LogManage;
import com.roo.core.utils.ToastUtils;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.CheckpointManager;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.listeners.PeerConnectedEventListener;
import org.bitcoinj.core.listeners.PeerDisconnectedEventListener;
import org.bitcoinj.core.listeners.PeerDiscoveredEventListener;
import org.bitcoinj.net.discovery.MultiplexingDiscovery;
import org.bitcoinj.net.discovery.PeerDiscovery;
import org.bitcoinj.net.discovery.PeerDiscoveryException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.uri.BitcoinURI;
import org.bitcoinj.utils.MonetaryFormat;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletFiles;
import org.bitcoinj.wallet.WalletProtobufSerializer;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/*
 * 钱包分为钱包程序和钱包文件两个部分
 *
 * 层级确定性钱包（hierarchical deterministic wallets）
 * 使用BIP44协议  m/0'/0'/0'/0'
 *       '代表加固  加固后仅拿到当前节点的公私钥无法派生出子节点公私钥
 *       以保证安全. 不加固则存在安全隐患
 *           m --->  master  seed
 *           第一层 使用的是什么协议
 *           第二层  是哪个币种
 *           第三层  使用的是那个账号
 *           第四层  0 外部链接收转账     1 内部链接收找零
 *           第五层  钱包的索引编号
 * BIP44协议是一个系统可以从单一个 seed 产生一树状结构储存多组 keypairs（私钥和公钥）。
 * 好处是可以方便的备份、转移到其他相容装置（因为都只需要 seed），
 * 及分层的权限控制等。
 *
 * Simplified Payment Verification (SPV):
 *     节点无需下载所有的区块数据, 而只需要加载所有区块头数据
 *     （block header的大小为80B），
 *     即可验证这笔交易是否曾经被比特币网络认证过
 *
 *  PeerGroup  节点探索器
 *
 *  Unspent Transaction Output    交易输出中没有被花费的部分
 *  比特币钱包余额需要统计所有钱包地址对应的UTXO
 *
 * 1. 使用随机数,生成助记词
 * 2. 使用助记词生成种子(seed)
 * 3. 使用种子计算出根节点的公私钥 master
 * 4. 根节点的公私钥派生出子节点公私钥
 * */
public class BitcoinWalletUtils {
    private static final boolean SERVER_TYPE_COIN = false;

    private static final NetworkParameters NETWORK_PARAMETERS_BTC =
            SERVER_TYPE_COIN ? MainNetParams.get() : TestNet3Params.get();
    private static final String TAG = BitcoinWalletUtils.class.getSimpleName();

    private Wallet wallet;
    private PeerGroup peerGroup;

    //生成确定性公私钥
    //generateDeterministicKey();
    //创建钱包，涉及到文件操作，并且属于耗时操作， 开启异步线程执行
    //AsyncTask.execute(mLoadWalletTask);

    public void loadWallet(Context context) throws Exception {
        //加载钱包，
        //1.本地没有钱包文件， 则创建钱包文件
        File file = context.getFileStreamPath("wallet-protobuf");
        if (!file.exists()) {
            //创建钱包文件
            //比特币主网， 会消耗真实比特币 MainNetParams mainNetParams = MainNetParams.get();
            //比特币测试网络, 创建钱包：TestNet3Params.get()
            //创建钱包的过程就是依据BIP协议创建层级确定性钱包
            //也就是generateDeterministicKey方法中的步骤
            wallet = new Wallet(NETWORK_PARAMETERS_BTC);
        } else {
            //2.本地有钱包文件，加载钱包文件
            //钱包文件已经存在，直接加载读取
            //将protobuf文件转换成java对象，反序列化，
            //将file转换成输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            wallet = new WalletProtobufSerializer().readWallet(fileInputStream);
            //清理钱包
            wallet.cleanup();
        }
        //将钱包以文件形式保存起来（对象序列化), 并设置保存周期
        //第4个参数为null, 表示不监听钱包文件的变化事件
        WalletFiles walletFiles = wallet.autosaveToFile(file, 3 * 1000, TimeUnit.MILLISECONDS, null);
        //保存钱包文件
        walletFiles.saveNow();

        //给钱包设置监听器，监听余额变化
        wallet.addCoinsReceivedEventListener(mWalletCoinsReceivedEventListener);
        //同步区块数据
        startAsyncBlockChain(context);
    }

    public void startAsyncBlockChain(Context context) throws Exception {
        File dir = context.getDir("blockstore", Context.MODE_PRIVATE);
        //创建区块链文件
        File blockchainFile = new File(dir, "blockchain");

        //创建SPVBlockStore管理区块数据  spv简化的付款验证
        //即值加载区块头部数据,即可校验这笔交易是否被比特币网络验证过
        SPVBlockStore spvBlockStore = new SPVBlockStore(NETWORK_PARAMETERS_BTC, blockchainFile);
        //加载检查点,预加载一段数据
        //读取检查点文件, 转换成InputStream (输入流)
        InputStream checkpoints = context.getAssets().open("checkpoints.txt");
        CheckpointManager.checkpoint(NETWORK_PARAMETERS_BTC, checkpoints, spvBlockStore, wallet.getEarliestKeyCreationTime());

        //创建BlockChain对象, 包含SPVBlockStore 和 Wallet
        BlockChain blockChain = new BlockChain(NETWORK_PARAMETERS_BTC, wallet, spvBlockStore);

        //创建PeerGroup对象   节点探索器
        peerGroup = new PeerGroup(NETWORK_PARAMETERS_BTC, blockChain);
        //配置钱包
        peerGroup.addWallet(wallet);
        //设置节点探索器最大连接数
        peerGroup.setMaxConnections(8);

        //监听节点连接事件
        peerGroup.addConnectedEventListener(mPeerConnectedEventListener);
        //监听节点连接中断事件
        peerGroup.addDisconnectedEventListener(mPeerDisconnectedEventListener);
        //监听节点发现新的连接事件
        peerGroup.addDiscoveredEventListener(mPeerDiscoveredEventListener);

        //配置节点探索器
        peerGroup.addPeerDiscovery(new PeerDiscovery() {
            private final PeerDiscovery nomarPeerDiscovery = MultiplexingDiscovery
                    .forServices(NETWORK_PARAMETERS_BTC, 0);

            @Override
            public InetSocketAddress[] getPeers(long services, long timeoutValue, TimeUnit timeoutUnit) throws PeerDiscoveryException {
                return nomarPeerDiscovery.getPeers(services, timeoutValue, timeoutUnit);
            }

            @Override
            public void shutdown() {
                nomarPeerDiscovery.shutdown();
            }
        });

        //开始同步,探索节点并连接
        //peerGroup.start();//同步,耗时操作,线程会卡住

        peerGroup.startAsync();//异步

        //同步下载区块链数据
        peerGroup.startBlockChainDownload(null);
    }

    private final WalletCoinsReceivedEventListener mWalletCoinsReceivedEventListener = new WalletCoinsReceivedEventListener() {
        @Override
        public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        }
    };

    private final PeerConnectedEventListener mPeerConnectedEventListener = new PeerConnectedEventListener() {
        @Override
        public void onPeerConnected(Peer peer, int peerCount) {
            //打印节点名称
            LogManage.d(TAG, "onPeerConnected:  是哪个节点连接上了 -->  " + peer.toString());
        }
    };

    private final PeerDisconnectedEventListener mPeerDisconnectedEventListener = new PeerDisconnectedEventListener() {
        @Override
        public void onPeerDisconnected(Peer peer, int peerCount) {
            LogManage.d(TAG, "onPeerDisconnected:  是哪个节点的连接中断了 -->  " + peer.toString());
        }
    };

    private final PeerDiscoveredEventListener mPeerDiscoveredEventListener = new PeerDiscoveredEventListener() {
        @Override
        public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {
            LogManage.d(TAG, "onPeersDiscovered:  发现了几个新的节点  -->  " + peerAddresses.size());
        }
    };

    /**
     * 确认交易并全网广播
     *
     * @param toAddressString 用户输入的地址
     * @param amountString    用户输入的金额-以mBTC为单位
     */
    public void sendTransaction(String toAddressString, String amountString) throws Exception {
        if (TextUtils.isEmpty(toAddressString) || TextUtils.isEmpty(toAddressString.trim())) {
            ToastUtils.show("对不起，转账地址不能为空，请重新输入");
        } else if (TextUtils.isEmpty(amountString) || TextUtils.isEmpty(amountString.trim())) {
            ToastUtils.show("对不起，转账金额不能为空，请重新输入");
        } else {
            //将地址字符串和金额字符串转换成地址对象和比特币对象
            Address address = Address.fromBase58(NETWORK_PARAMETERS_BTC, toAddressString);
            //代码中都是以satoshi为单位
            Coin coin = MonetaryFormat.MBTC.parse(amountString); //以satoshi为单位

            //生成交易请求
            SendRequest sendRequest = SendRequest.to(address, coin);

            //使用交易请求并以线下交易的方式, 生成一个交易对象
            Transaction transaction = wallet.sendCoinsOffline(sendRequest);

            //将这笔交易在全网广播出去
            peerGroup.broadcastTransaction(transaction);
        }
    }

    //需要在主线程执行，更新UI界面
    public void updateUI() {
        //获取钱包地址
        Address address = wallet.currentAddress(KeyChain.KeyPurpose.RECEIVE_FUNDS);


        //更新钱包余额
        Coin balance = wallet.getBalance(Wallet.BalanceType.ESTIMATED);

        //long value = balance.getValue(); //返回值是以satoshi为单位， 即'聪'：代码中都是以satoshi为单位
        long balanceMBTC = balance.getValue() / 10000; //转换为mBTC
        //钱包余额
        String balanceString = balanceMBTC + "mBTC";
        //钱包地址的二维码内容
        String addressBitmapStr = BitcoinURI.convertToBitcoinURI(address, null, null, null);
        //将钱包地址
        String addressText = MessageFormat.format("0x{0}", address.toString());
    }

}

