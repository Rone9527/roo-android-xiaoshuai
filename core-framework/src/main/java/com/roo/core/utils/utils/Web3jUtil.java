package com.roo.core.utils.utils;

import android.text.TextUtils;

import com.core.domain.manager.ChainCode;
import com.roo.core.app.constants.GlobalConstant;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * web3j构建主网rpc
 */
public class Web3jUtil {
    private static Web3jUtil web3jUtil = null;

    public static Web3jUtil getInstance() {
        if (web3jUtil == null) {
            web3jUtil = new Web3jUtil();
        }
        return web3jUtil;
    }

    public Web3j getBlockConnect(String chainCode) {
        return Web3j.build(new HttpService(getRpcUrl(chainCode)));
    }

    public String getRpcUrl(String chainCode) {
        String rpc = "";
        if (GlobalConstant.RPC_TYPE_MAIN) {
            switch (chainCode) {
                case ChainCode.ETH:
                    rpc = "https://eth-mainnet.roo.top";
                    break;
                case ChainCode.BSC:
                    rpc = "https://bsc-dataseed1.ninicoin.io/";
                    break;
                case ChainCode.HECO:
                    rpc = "https://http-mainnet.hecochain.com";
                    break;
                case ChainCode.POLYGON:
                    rpc = "https://polygon-rpc.com/";
                    break;
                case ChainCode.OEC:
                    rpc = "https://exchainrpc.okex.org";
                    break;
                case ChainCode.TRON:
                    rpc = "https://trongrid.io";
                    break;
                case ChainCode.FANTOM:
                    rpc = "https://rpc.fantom.network";
                    break;
            }
        } else {
            switch (chainCode) {
                case ChainCode.ETH:
                    rpc = "https://rinkeby.infura.io/v3/b4e3d3879051411c9b4a198bcd6f08ff";
                    break;
                case ChainCode.BSC:
                    rpc = "https://data-seed-prebsc-1-s1.binance.org:8545/";
                    break;
                case ChainCode.HECO:
                    rpc = "https://http-testnet.huobichain.com/";
                    break;
                case ChainCode.POLYGON:
                    rpc = "https://polygon-mumbai.infura.io/v3/c8c3b9caad3b428eb4846ecac4df86e1";
                    break;
                case ChainCode.OEC:
                    rpc = "https://exchaintestrpc.okex.org";
                    break;
                case ChainCode.TRON:
                    rpc = "https://nile.trongrid.io";
                case ChainCode.FANTOM:
                    rpc = "https://rpc.fantom.network ";
                    break;
            }
        }
        return rpc;
    }

    //获取代币精度
    public String getDecimal(String address, String chainCode, String contractId) throws IOException {
        Function function = new Function("decimals",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        String data = FunctionEncoder.encode(function);
        String addr = TextUtils.isEmpty(address) ? "0x0000000000000000000000000000000000000000" : address;
        Transaction transaction = Transaction.createEthCallTransaction(addr, contractId, data);
        EthCall ethCall = getBlockConnect(chainCode)
                .ethCall(transaction, DefaultBlockParameterName.LATEST)
                .send();
        List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        return results.size() == 0 ? "0" : results.get(0).getValue().toString();
    }

    public String getSymbol(String chainCode, String contractId) throws IOException {
        Function function = new Function(WalletEthFunction.symbol.function,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        Object symbol = executeFunction(chainCode, contractId, function);
        return (String) symbol;
    }

    public String getName(String chainCode, String contractId) throws IOException {
        Function function = new Function(WalletEthFunction.name.function,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        Object symbol = executeFunction(chainCode, contractId, function);
        return (String) symbol;
    }

    private Object executeFunction(String chainCode, String contractId, Function function) throws IOException {
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(
                "0x0000000000000000000000000000000000000000", contractId, data);
        EthCall ethCall = getBlockConnect(chainCode).ethCall(transaction, DefaultBlockParameterName.LATEST).send();
        List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        return results.get(0).getValue();
    }

    public enum WalletEthFunction {

        decimals("decimals", "精度"),
        name("name", "代币名称"),
        symbol("symbol", "代币符号"),
        totalSupply("totalSupply", "代币发行总量"),
        ;
        public final String function;

        public final String desc;

        WalletEthFunction(String function, String desc) {
            this.function = function;
            this.desc = desc;
        }
    }

}
