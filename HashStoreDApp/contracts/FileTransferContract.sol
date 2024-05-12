pragma solidity >=0.4.22 <0.7.0;

contract FileTransfer {

    address payable owner;
    mapping ( string => string ) hash_values;

    event NewHashAdded(address from, string itemId, bool success);

    constructor() public {
        owner = msg.sender;
    }

    function killBlockAuth() public {
        if(msg.sender == owner){
            selfdestruct(owner);
        }
    }

    function addNewHash(string memory itemId, string memory hashValue) public returns (bool) {
        hash_values[itemId] = hashValue;
        emit NewHashAdded(msg.sender, itemId, true);
        return true;
    }

    function getHashValue(string memory itemId) public view returns (string memory) {
        return hash_values[itemId];
    }
}