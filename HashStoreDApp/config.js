module.exports.ETHEREUM_NODE_ADDRESS = "http://127.0.0.1:7545"
module.exports.CONTRACT_ADDRESS = "0x1686ABe8F928A1c3f371203A7A80F94041Ed4dB3";
module.exports.ETHEREUM_ACCOUNT_ADDRESS = "0x4A9ae99FD75f46c75e08088E1c8f34f315a7624F";
module.exports.PRIVATE_KEY = "12d897625d5009894a581c23d2b059de556c683300e3812bd0f6f319127e37ac"
module.exports.ABI = [
  {
    "inputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "constructor"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "address",
        "name": "from",
        "type": "address"
      },
      {
        "indexed": false,
        "internalType": "string",
        "name": "itemId",
        "type": "string"
      },
      {
        "indexed": false,
        "internalType": "bool",
        "name": "success",
        "type": "bool"
      }
    ],
    "name": "NewHashAdded",
    "type": "event"
  },
  {
    "constant": false,
    "inputs": [],
    "name": "killBlockAuth",
    "outputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "constant": false,
    "inputs": [
      {
        "internalType": "string",
        "name": "itemId",
        "type": "string"
      },
      {
        "internalType": "string",
        "name": "hashValue",
        "type": "string"
      }
    ],
    "name": "addNewHash",
    "outputs": [
      {
        "internalType": "bool",
        "name": "",
        "type": "bool"
      }
    ],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [
      {
        "internalType": "string",
        "name": "itemId",
        "type": "string"
      }
    ],
    "name": "getHashValue",
    "outputs": [
      {
        "internalType": "string",
        "name": "",
        "type": "string"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  }
]