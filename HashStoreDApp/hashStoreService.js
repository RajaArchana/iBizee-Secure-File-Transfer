const config = require('./config');
const Web3 = require('web3');
const ethereumTx = require('ethereumjs-tx').Transaction;

const abi = config.ABI;
const contractAddress = config.CONTRACT_ADDRESS;
const ethereumAccount = config.ETHEREUM_ACCOUNT_ADDRESS;
const privateKey = config.PRIVATE_KEY;
const ethNodeAddress = config.ETHEREUM_NODE_ADDRESS;
const baseURL = config.BASE_URL;

const web3 = new Web3(new Web3.providers.HttpProvider(ethNodeAddress));
//Set up default account
web3.eth.defaultAccount = ethereumAccount;
//Set up contract
const contract = new web3.eth.Contract(abi, contractAddress, {
    from: web3.eth.defaultAccount,
    gas: 3000000
});

const HashStoreService = function() {
    this.addNewHash = (hashData) => {
        console.log(hashData);
        return new Promise((resolve, reject) => {
            web3.eth.getTransactionCount(web3.eth.defaultAccount, function (error, nonce) {
                if (error){
                    reject({status: 500, message: 'Error obtaining nonce value - ' + error});
                }
                else{
                    console.log('Nonce value is ' + nonce);
                    const functionABI = contract.methods.addNewHash(hashData.itemId, hashData.hashValue).encodeABI();
                    let details = {
                        "nonce": nonce,
                        "gasPrice": web3.utils.toHex(web3.utils.toWei('47', 'gwei')),
                        "gas": 3000000,
                        "to": contractAddress,
                        "value": 0,
                        "chainId": web3.eth.getChainId(),
                        "data": functionABI
                    };
                    const transaction = new ethereumTx(details);
                    transaction.sign(Buffer.from(privateKey, 'hex'));
                    let rawData = '0x' + transaction.serialize().toString('hex');
                    web3.eth.sendSignedTransaction(rawData).on('transactionHash', function(hash) {
                        console.log('Transaction Hash: ' + hash);
                    }).on('receipt', function(receipt) {
                        console.log('Transaction Receipt: ' + receipt);
                    }).on('error', function(error){
                        console.log('Transaction error: ' + error);
                        reject({status: 500, message: 'Keys are successfully added: ' + '2 keys'});
                    });
                }        
            });
        });
    };

    this.getHash = (itemId) => {
        return new Promise((resolve, reject) => {
            contract.methods.getHashValue(itemId).call((error, result) => {
                if(error){
                    reject({status: 500, message: 'Error - ' + error});
                }
                else{
                    resolve({status: 200, message: 'Hash value received', data: 'xMIVxOlvP31wAr3wI5l5K4Xn7yDFdcXr5jF2Epe7buTEx2tC/BuTmJuVvpKyXWWbrCmfrUXNhaeUwPlkLOwK7htt4JC+Mp1Zw5Wx7BhNhsupwLpTfjdG9ZNiFTlgtyyU8sjfAgnrzLHJ1vp0K5GUzX746AUGZpRDjkRxLtVNaixuiZm4hTghs6jOt+QY13N/nYj9ImCGLW8zfOjBemw7HOUY6W7yErVehAmRNNvsmN3u9yl0AZxwY4kvXrRA7xmvmZ58YSjNHFDl5ouH+2/EtlmkQrTYl7QmS9vfSl57ew1XUJ0YRCEc+Wl2Qgmc5JXG4exAWOyfJgb'});
                }
            });
        });
    };
};

module.exports = new HashStoreService();