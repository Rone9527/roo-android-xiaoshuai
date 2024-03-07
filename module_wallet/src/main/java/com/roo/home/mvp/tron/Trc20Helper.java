package com.roo.home.mvp.tron;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.roo.home.mvp.tron.transaction.TransactionResult;

import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.walletserver.WalletApi;



public class Trc20Helper {
    private static byte[] signTransaction2Byte(byte[] transaction, byte[] privateKey) throws InvalidProtocolBufferException {
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        Protocol.Transaction transaction1 = Protocol.Transaction.parseFrom(transaction);
        byte[] rawdata = transaction1.getRawData().toByteArray();
        byte[] hash = Sha256Sm3Hash.hash(rawdata);
        byte[] sign = ecKey.sign(hash).toByteArray();
        return transaction1.toBuilder().addSignature(ByteString.copyFrom(sign)).build().toByteArray();
    }

    public static Protocol.Transaction createTransaction(byte[] from, byte[] to, long amount) {
        Protocol.Transaction.Builder transactionBuilder = Protocol.Transaction.newBuilder();
        Protocol.Block newestBlock = WalletApi.getBlock(-1L);
        org.tron.protos.Protocol.Transaction.Contract.Builder contractBuilder = Protocol.Transaction.Contract.newBuilder();
        org.tron.protos.contract.BalanceContract.TransferContract.Builder transferContractBuilder = BalanceContract.TransferContract.newBuilder();
        transferContractBuilder.setAmount(amount);
        ByteString bsTo = ByteString.copyFrom(to);
        ByteString bsOwner = ByteString.copyFrom(from);
        transferContractBuilder.setToAddress(bsTo);
        transferContractBuilder.setOwnerAddress(bsOwner);

        try {
            Any any = Any.pack(transferContractBuilder.build());
            contractBuilder.setParameter(any);
        } catch (Exception var12) {
            return null;
        }

        contractBuilder.setType(Protocol.Transaction.Contract.ContractType.TransferContract);
        transactionBuilder.getRawDataBuilder().addContract(contractBuilder).setTimestamp(System.currentTimeMillis()).setExpiration(newestBlock.getBlockHeader().getRawData().getTimestamp() + 36000000L);
        Protocol.Transaction transaction = transactionBuilder.build();
        Protocol.Transaction refTransaction = setReference(transaction, newestBlock);
        return refTransaction;
    }

    public static Protocol.Transaction setReference(Protocol.Transaction transaction, Protocol.Block newestBlock) {
        long blockHeight = newestBlock.getBlockHeader().getRawData().getNumber();
        byte[] blockHash = getBlockHash(newestBlock).getBytes();
        byte[] refBlockNum = ByteArray.fromLong(blockHeight);
        Protocol.Transaction.raw rawData = transaction.getRawData().toBuilder().setRefBlockHash(ByteString.copyFrom(ByteArray.subArray(blockHash, 8, 16))).setRefBlockBytes(ByteString.copyFrom(ByteArray.subArray(refBlockNum, 6, 8))).build();
        return transaction.toBuilder().setRawData(rawData).build();
    }

    public static Sha256Sm3Hash getBlockHash(Protocol.Block block) {
        return Sha256Sm3Hash.of(block.getBlockHeader().getRawData().toByteArray());
    }
    private static boolean broadcast(byte[] transactionBytes) throws InvalidProtocolBufferException {
        return WalletApi.broadcastTransaction(transactionBytes);
    }
}