package com.roo.core.utils.utils;

import com.google.common.collect.ImmutableList;
import com.roo.core.utils.LogManage;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/9 17:38
 *     desc        : 描述--//WordUtils 创建钱包
 * </pre>
 */
public class WordUtils {
    private static final String TAG = WordUtils.class.getSimpleName();

    //生成助记词到的过程,即头部注释第1到4步
    private List<String> generateWordList() throws Exception {
        //使用随机数,生成助记词
        SecureRandom secureRandom = new SecureRandom();

        //生成随机的128位, 也就是16字节的数组
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        //给byte数组赋给随机值
        secureRandom.nextBytes(entropy);

        //使用生成的随机字节数组生成助记词
        List<String> words = MnemonicCode.INSTANCE.toMnemonic(entropy);
        LogManage.d(TAG, "生成的12个助记词是: \n");
        for (String word : words) {
            LogManage.d(TAG, word);
        }
        return words;
    }

    /**
     * 使用助记词, 生成确定性钱包
     *
     * @param password 安全密码
     * @param words    助记词
     */
    private WalletFile createWallet(String password, List<String> words) throws Exception {
        byte[] seeds = MnemonicCode.INSTANCE.toEntropy(words);
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seeds);
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
        DeterministicKey deterministicKey = deterministicHierarchy
                .deriveChild(DeterministicKeyChain.BIP44_ACCOUNT_ZERO_PATH,
                        true, true, ChildNumber.ZERO);
        ECKeyPair ecKeyPair = ECKeyPair.create(deterministicKey.getPrivKeyBytes());
        return Wallet.createLight(password, ecKeyPair);
    }

    /**
     * 派生出子节点公私钥
     *
     * @param password 安全密码
     * @param coinType 币种类型
     * @param words    助记词
     */
    private WalletFile createWalletByMnemonic(String password, int coinType, List<String> words)
            throws Exception {
        //使用助记词生成种子
        byte[] seeds = MnemonicCode.INSTANCE.toEntropy(words);

        //使用种子计算出根节点密钥对
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seeds);

        //根节点派生出子节点的公私钥
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);

        // m/44'/xx'/xx'/xx
        ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
                ImmutableList.of(new ChildNumber(44, true),
                        new ChildNumber(coinType, true),
                        ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);
        //使用BIP44协议,派生成层级确定性结构的子节点的私钥
        //createParent=true, 生成父路径,  relative=false, 相对于根路径
        DeterministicKey deterministicKey = deterministicHierarchy
                .deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH,
                        false, true,
                        ChildNumber.ZERO);

        //获取子节点的字节数组
        byte[] privKeyBytes = deterministicKey.getPrivKeyBytes();

        //根据子节点的字节数组创建密钥对
        ECKeyPair ecKeyPair = ECKeyPair.create(privKeyBytes);
        //根据密码和密钥对创建钱包,将创建出来的钱包对象赋值给本地变量
        return Wallet.createLight(password, ecKeyPair);
    }

    /**
     * 导出私钥
     *
     * @param password 安全密码
     */
    public String exportPrivateKey(String password, WalletFile walletFile) throws Exception {
        ECKeyPair ecKeyPair = Wallet.decrypt(password, walletFile);
        BigInteger privateKey = ecKeyPair.getPrivateKey();
        return Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);
    }
}
