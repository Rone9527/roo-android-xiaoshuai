以太坊基本概念
钱包地址

以0x开头的42位的哈希值 (16进制) 字符串

keystore

明文私钥通过加密算法加密过后的 JSON 格式的字符串, 一般以文件形式存储

助记词

12 (或者 15、18、21) 单词构成, 用户可以通过助记词导入钱包, 但反过来讲, 如果他人得到了你的助记词,
不需要任何密码就可以轻而易举的转移你的资产, 所以要妥善保管自己的助记词

明文私钥

64位的16进制哈希值字符串, 用一句话阐述明文私钥的重要性 “谁掌握了私钥, 谁就掌握了该钱包的使用权!”
同样, 如果他人得到了你的明文私钥, 不需要任何密码就可以轻而易举的转移你的资产


通俗地去解释,以银行账户为类比,这些名词分别对应内容如下:

钱包地址 = 银行卡号
密码 = 银行卡密码
私钥 = 银行卡号 + 银行卡密码
助记词 = 银行卡号 + 银行卡密码
Keystore + 密码 = 银行卡号 + 银行卡密码
Keystore ≠ 银行卡号


BIP32,BIP39,BIP44
在创建钱包前,得先说下BIP32,BIP39,BIP44

BIP 全名是 Bitcoin Improvement Proposals，是提出 Bitcoin 的新功能或改进措施的文件。
可由任何人提出，经过审核后公布在 bitcoin/bips 上。BIP 和 Bitcoin 的关系，就像是 RFC 之于 Internet。
而其中的 BIP32, BIP39, BIP44 共同定义了目前被广泛使用的 HD Wallet，包含其设计动机和理念、实作方式、实例等。

BIP32
定义 Hierarchical Deterministic wallet (简称 “HD Wallet”)，
是一个系统可以从单一个 seed 产生一树状结构储存多组 keypairs（私钥和公钥）。
好处是可以方便的备份、转移到其他相容装置（因为都只需要 seed），以及分层的权限控制等


BIP39
将 seed 用方便记忆和书写的单字表示。一般由 12 个单字组成，称为 mnemonic code(phrase)，中文称为助记词或助记码。例如
rose rocket invest real refuse margin festival danger anger border idle brown

BIP44
基于 BIP32 的系统，赋予树状结构中的各层特殊的意义。让同一个 seed 可以支援多币种、多帐户等。各层定义如下:
m / purpose' / coin_type' / account' / change / address_index
purpose'一般为「44」，代表遵循 BIP44 方案，但 BTC 隔离见证地址为「49」
coin_type'代表币种类型，其中 0 为 BTC，1 为 BTC 测试网，2 为 LTC，60 为 ETH 等
account'代表此币种的账户索引，从 0 开始
change 数值 0 用于外部链，1 用于内部链。一个用于创建接收地址，另一个用来创造找零地址
address_index 地址索引，从 0 开始


Ethereum HD Wallet
Ethereum 的钱包目前均采用以上 Bitcoin HD Wallet 的架构，并订 coin_type' 为 60'，
可以在 ethereum/EIPs/issues 中看到相关的讨论。举例来说，在一个 Ethereum HD Wallet 中，
第一个帐户（这里的帐户指 BIP44 中定义的 account'）的第一组 keypair，其路径会是 m/44'/60'/0'/0/0

创建钱包
在了解BIP 后,我们开始以太坊钱包开发,创建的钱包的流程为:
随机生成一组助记词
生成 seed
生成 master key
生成 child key
我们取第一组child key即m/44'/60'/0'/0/0 得到私钥,keystore及地址



Q. 怎么导出助记词啊 , imToken 有导出/备份助记词的功能 .

A. 很好的问题. 其实就是创建/用助记词解锁钱包时,app本地保存了助记词.导出只是将存储数据读取出来而已.
可以尝试在imToken上通过导入Keystore或者私钥解锁钱包,就会发现没有备份助记词的入口.

Q. app本地需要保存钱包什么信息

A. 理论上说只需要保存钱包的Keystore.助记词,私钥最好别存,因为app一旦被破解,用户的钱包就能被直接获取到.
如若有出于用户体验等原因保存这些敏感信息,最好结合用户输入的密码做对称加密保存.

Q: 为什么调用sendRawTransaction接口后,转账记录在以太坊浏览器或者其他钱包查询不到?

A: 接口调用后会返回一个字符串hash,我们通常称为txHash.此时是不知道结果的,
因为拥堵或者其他原因以太坊不一定确认,因此查不到是正常的.在app中,一种做法是在发送请求后,
主动通知自己的中继relay,在中继relay中维护这个txHash状态,以处理中的状态呈现给用户.

Q:sendRawTransaction后接口报错’invalid sender’

A: 这个报错是以太坊环境错误导致的,代码上表现为chainId没对应.
```
private String signData(byte chainId,
                        RawTransaction rawTransaction,
                        HDWallet wallet,
                        String password) throws Exception {
        Credentials credentials;
        credentials = Credentials.create(Wallet.decrypt(password, wallet.getWalletFile()));
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        return Numeric.toHexString(signMessage);
}
```
签名需要制定环境chainId,若使用不带chainId的方法,则默认是主网.

创建钱包输入的密码，并不是用于生成种子，而是用来做keystore 加密的密码，这是业内的一个常规做法。
尽管这个做法会降低一些安全性，但是不遵循行规，会导致和其他的钱包不兼容，及在其他钱包的助记词不能导入到我们钱包。
反之，keystore 文件应该存储在内部存储，即应用程序自身目录内，保证其他程序无法读取内容，万万不可存取在外部存储中，如SD卡。
商业产品，应该检查手机时候root，如果root，则第2点的安全性无法保证。

测试水龙头
以太坊
http://www.itheima.com/tools/fancet/
火币
https://scan-testnet.hecochain.com/faucet
币安
https://testnet.binance.org/faucet-smart

以太坊交易协议
1   0x协议-转账---------transfer
2   0x095ea7b3---------approve
3   0x0863b7ac---------swapOnUniswapFork
4   0xa9059cbb---------transfer