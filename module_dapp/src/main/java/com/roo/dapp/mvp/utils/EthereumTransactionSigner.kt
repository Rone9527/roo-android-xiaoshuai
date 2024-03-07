package com.roo.dapp.mvp.utils

import com.google.protobuf.ByteString
import com.roo.core.utils.LogManage
import org.web3j.utils.Numeric
import wallet.core.java.AnySigner
import wallet.core.jni.CoinType
import wallet.core.jni.PrivateKey
import wallet.core.jni.proto.Ethereum
import kotlin.experimental.and

/**
//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/9/4
 * PackageName : com.roo.dapp.mvp.utils
 * Description :
 */
class EthereumTransactionSigner {

    fun ethereumTransactionSigning() {
        val signingInput = Ethereum.SigningInput.newBuilder()
        signingInput.apply {
            privateKey =
                ByteString.copyFrom(PrivateKey("0x131018eb864e22757ca084af98426cc75f81b41f4dc55f20a8e492e41c417914".toHexBytes()).data())
            toAddress = "0x2206ceabceb3c66a0f008595dd67763114c94007"
            chainId = ByteString.copyFrom("0x80".toHexBytes())
            nonce = ByteString.copyFrom(Numeric.hexStringToByteArray("0xa1"))
            gasPrice = ByteString.copyFrom("0x861c4680".toHexBytes())
            gasLimit = ByteString.copyFrom("0x5208".toHexBytes())
            transaction = Ethereum.Transaction.newBuilder().apply {
                transfer = Ethereum.Transaction.Transfer.newBuilder().apply {
                    amount = ByteString.copyFrom("0x0".toHexBytes())
                    data = ByteString.copyFrom("0x095ea7b3000000000000000000000000794a204eeab3b5d33618495013ba5da5c0b9775bffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toHexBytes())
                }.build()
            }.build()
        }
        val output =
            AnySigner.sign(signingInput.build(), CoinType.ETHEREUM, Ethereum.SigningOutput.parser())

        val hexString = output.encoded.toByteArray().toHexString(false)
        LogManage.e("hexString--->" + hexString)
    }


    fun ByteArray.toHexString(withPrefix: Boolean = true): String {
        val stringBuilder = StringBuilder()
        if (withPrefix) {
            stringBuilder.append("0x")
        }
        for (element in this) {
            stringBuilder.append(String.format("%02x", element and 0xFF.toByte()))
        }
        return stringBuilder.toString()
    }
}