
const __addressHex = "%1$s";
const __rpcURL = "%2$s";
const __chainID = "%3$s";

function executeCallback (id, error, value) {
  RooWallet.executeCallback(id, error, value)
}

window.RooWallet.init(__rpcURL, {
  getAccounts: function (cb) { cb(null, [__addressHex]) },
  processTransaction: function (tx, cb){
    console.log('signing a transaction', tx)
    const { id = 8888 } = tx
    RooWallet.addCallback(id, cb)

    var gasLimit = tx.gasLimit || tx.gas || null;
    var gasPrice = tx.gasPrice || null;
    var data = tx.data || null;
    var nonce = tx.nonce || -1;
    roo.signTransaction(id, tx.to || null, tx.value, nonce, gasLimit, gasPrice, data);
  },
  signMessage: function (msgParams, cb) {
      console.log('signMessage', msgParams)
      const { data, chainType } = msgParams
      const { id = 8888 } = msgParams
    RooWallet.addCallback(id, cb)
    roo.signMessage(id, data);
  },
  signPersonalMessage: function (msgParams, cb) {
      console.log('signPersonalMessage', msgParams)
      const { data, chainType } = msgParams
      const { id = 8888 } = msgParams
    RooWallet.addCallback(id, cb)
    roo.signPersonalMessage(id, data);
  },
  signTypedMessage: function (msgParams, cb) {
    console.log('signTypedMessage ', msgParams)
    const { data } = msgParams
    const { id = 8888 } = msgParams
    RooWallet.addCallback(id, cb)
    roo.signTypedMessage(id, JSON.stringify(msgParams))
  },
  ethCall: function (msgParams, cb) {
    console.log("eth_call", msgParams)
    const data = msgParams
    const { id = Math.floor((Math.random() * 100000) + 1) } = msgParams
    RooWallet.addCallback(id, cb)
    //alpha.ethCall(id, JSON.stringify(msgParams));
    roo.ethCall(id, msgParams.to, msgParams.data);
  },
  walletAddEthereumChain: function (msgParams, cb) {
    alert("walletAddEthereumChain")
    const data = msgParams
    const { id = Math.floor((Math.random() * 100000) + 1) } = msgParams
    console.log("walletAddEthereumChain", msgParams)
    RooWallet.addCallback(id, cb)
    roo.walletAddEthereumChain(id, JSON.stringify(msgParams));
    //webkit.messageHandlers.walletAddEthereumChain.postMessage({"name": "walletAddEthereumChain", "object": data, id: id})
  },
   walletAddEthereumChain: function (msgParams, cb) {
      const data = msgParams
      const { id = Math.floor((Math.random() * 100000) + 1) } = msgParams
      console.log("walletAddEthereumChain", msgParams)
      RooWallet.addCallback(id, cb)
      roo.walletAddEthereumChain(id, JSON.stringify(msgParams));
      //webkit.messageHandlers.walletAddEthereumChain.postMessage({"name": "walletAddEthereumChain", "object": data, id: id})
    },
  enable: function() {
      return new Promise(function(resolve, reject) {
          //send back the coinbase account as an array of one
          resolve([__addressHex])
      })
  }
}, {
    address: __addressHex,
    networkVersion: __chainID
})

window.web3.setProvider = function () {
  console.debug('Roo Wallet - overrode web3.setProvider')
}

window.web3.version.getNetwork = function(cb) {
    cb(null, __chainID)
}
window.web3.eth.getCoinbase = function(cb) {
    return cb(null, __addressHex)
}
window.web3.eth.defaultAccount = __addressHex

window.ethereum = web3.currentProvider
