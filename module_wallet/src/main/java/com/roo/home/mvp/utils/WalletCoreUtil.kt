package com.roo.home.mvp.utils

import android.text.TextUtils
import com.core.domain.mine.BlockHeader
import com.google.protobuf.ByteString
import com.roo.core.app.constants.Constants
import com.roo.core.utils.LogManage
import org.web3j.abi.datatypes.generated.Int64
import org.web3j.utils.Numeric
import wallet.core.java.AnySigner
import wallet.core.jni.CoinType
import wallet.core.jni.proto.Tron
import java.math.BigInteger

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
 * Created on : 2021/8/12
 * PackageName : com.roo.home.mvp.utils
 * Description :
 */
class WalletCoreUtil {
    fun getSignTransferTrc20Contract(
        ownerAddress: String,
        contractAddress: String,
        toAddress: String,
        amount: BigInteger,
        timestamp: Long,
        txTrieRoot: String,
        parentHash: String,
        number: Long,
        witnessAddress: String,
        version: Int,
        privateKey: String,
        token: String,
        gasLimit: Long
    ): String? {


        var hexStrin = ""

        if (TextUtils.isEmpty(token)) {//主网币 trx  transfer
            val transient = Tron.TransferContract.newBuilder()
                .setOwnerAddress(ownerAddress)
                .setToAddress(toAddress)
                // 1000
                .setAmount(Int64(amount.toLong()).value.toLong())

            val blockHeader = Tron.BlockHeader.newBuilder()
                .setTimestamp(timestamp)
                .setTxTrieRoot(ByteString.copyFrom((Constants.PREFIX_16 + txTrieRoot).toHexByteArray()))
                .setParentHash(ByteString.copyFrom((Constants.PREFIX_16 + parentHash).toHexByteArray()))
                .setNumber(Int64(number).value.toLong())
                .setWitnessAddress(ByteString.copyFrom((Constants.PREFIX_16 + witnessAddress).toHexByteArray()))
                .setVersion(version)
                .build()


            val transaction = Tron.Transaction.newBuilder()
                .setTransfer(transient)
                .setTimestamp(timestamp)
//                    .setExpiration(BigInteger.valueOf(10 * 60 * 60 * 1000).toLong() + BigInteger.valueOf(timestamp).toLong())
                .setBlockHeader(blockHeader)
                .build()
            val signingInput = Tron.SigningInput.newBuilder()
                .setTransaction(transaction)
                .setPrivateKey(ByteString.copyFrom(privateKey.toHexByteArray()))
            val output =
                AnySigner.sign(signingInput.build(), CoinType.TRON, Tron.SigningOutput.parser())
            println("*****signing_output${output.json}")
//            hexStrin = output.signature.toByteArray().toHexString(false);
            hexStrin = output.json
        } else {
            val amoutHexStr: String = stringTo64b(amount.toString(16));
            val trc20Contract = Tron.TransferTRC20Contract.newBuilder()
                .setOwnerAddress(ownerAddress)
                .setContractAddress(token)
                .setToAddress(toAddress)
                .setAmount(ByteString.copyFrom(amoutHexStr.toHexByteArray()))

            val blockHeader = Tron.BlockHeader.newBuilder()
                .setTimestamp(timestamp)
                .setTxTrieRoot(ByteString.copyFrom((Constants.PREFIX_16 + txTrieRoot).toHexByteArray()))
                .setParentHash(ByteString.copyFrom((Constants.PREFIX_16 + parentHash).toHexByteArray()))
                .setNumber(Int64(number).value.toLong())
                .setWitnessAddress(ByteString.copyFrom((Constants.PREFIX_16 + witnessAddress).toHexByteArray()))
                .setVersion(version)
                .build()

            val transaction = Tron.Transaction.newBuilder()
                .setTransferTrc20Contract(trc20Contract)
                .setTimestamp(timestamp)
                .setFeeLimit(Int64(gasLimit).value.toLong())
                .setBlockHeader(blockHeader)
                .build()
            val signingInput = Tron.SigningInput.newBuilder()
                .setTransaction(transaction)
                .setPrivateKey(ByteString.copyFrom(privateKey.toHexByteArray()))

            val output =
                AnySigner.sign(signingInput.build(), CoinType.TRON, Tron.SigningOutput.parser())
            hexStrin = output.json
            println("*****signing_output${output.json}")
        }
        return hexStrin
    }


    fun testSignTransferTrc20Contract(
        t: BlockHeader,
        from: String,
        to: String,
        contractAddress: String,
        amount:BigInteger,
        privateKey: String
    ): String? {
//        LogManage.e("blockID---->"+t.blockID)
        val amoutHexStr: String = stringTo64b(amount.toString(16));

        LogManage.e("amoutHexStr---->"+amoutHexStr)
        val trc20Contract = Tron.TransferTRC20Contract.newBuilder()
            .setOwnerAddress(from)
            .setContractAddress(contractAddress)
            .setToAddress(to)
            // 1000
            .setAmount(ByteString.copyFrom((Constants.PREFIX_16 + amoutHexStr).toHexByteArray()))

        val blockHeader = Tron.BlockHeader.newBuilder()
            .setTimestamp(t.block_header.raw_data.timestamp)
            .setTxTrieRoot(ByteString.copyFrom((Constants.PREFIX_16 + t.block_header.raw_data.txTrieRoot).toHexByteArray()))
            .setParentHash(ByteString.copyFrom((Constants.PREFIX_16 + t.block_header.raw_data.parentHash).toHexByteArray()))
            .setNumber(t.block_header.raw_data.number)
            .setWitnessAddress(ByteString.copyFrom((Constants.PREFIX_16 + t.block_header.raw_data.witness_address).toHexByteArray()))
            .setVersion(t.block_header.raw_data.version)
            .build()

        val transaction = Tron.Transaction.newBuilder()
            .setTransferTrc20Contract(trc20Contract)
            .setTimestamp(t.block_header.raw_data.timestamp)
            .setBlockHeader(blockHeader)
            .build()

        val signingInput = Tron.SigningInput.newBuilder()
            .setTransaction(transaction)
            .setPrivateKey(ByteString.copyFrom(privateKey.toHexByteArray()))

        val output =
            AnySigner.sign(signingInput.build(), CoinType.TRON, Tron.SigningOutput.parser())
        LogManage.e("Numeric.toHexString(output.id.toByteArray()---" + Numeric.toHexString(output.id.toByteArray()))
        println("*****signing_output${output.json}")
        return output.json

    }

    /**
     * 十六进制的去掉前面的0x,补0，凑够64个字符长度
     */
     fun stringTo64b(str: String): String {
        var strNew = str;
        var new000 = ""
        val str0000 = "0000000000000000000000000000000000000000000000000000000000000000";
        if (str.startsWith(Constants.PREFIX_16)) {
            strNew = str.replace(Constants.PREFIX_16, "")

        }

        val len = strNew.length

        if (len < 64) {

            val s2 = str0000.removeRange(str0000.length - len, str0000.length)
            println("s2=${s2} ;" + s2.length)
            new000 = s2.plus(strNew)

        }
        return new000;
    }
}