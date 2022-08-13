import json

from web3 import Web3, HTTPProvider
from eth_account import Account

def transfer(network, token_address, abi, private_key, to_address, trans_value):
    """
    Web3 进行转账
    :param network: 网络
    :param token_address: 智能合约的地址
    :param abi: 智能合约得ABI
    :param private_key: 私钥
    :param to_address: 转账地址
    :param trans_value: 交易金额
    :return:
    """
    w3 = Web3(HTTPProvider(network))  # 网络地址
    token_contract = Web3.toChecksumAddress(token_address)  #合约地址
    abi_json = json.loads(abi)
    token_contract = w3.eth.contract(address=token_contract, abi=abi_json)
    print(w3.isConnected())
    acc = Account.from_key(private_key=private_key)
    from_address = Web3.toChecksumAddress(acc.address)
    balance = token_contract.functions.balanceOf(from_address).call()
    # TODO 添加余额的判断
    to_address = Web3.toChecksumAddress(to_address)
    nonce = w3.eth.getTransactionCount(from_address)
    gas_price = w3.eth.gasPrice
    trans_value = float(trans_value)
    value = Web3.toWei(trans_value, 'ether')
    gas = token_contract.functions.transfer(to_address, int(value)).estimateGas({'from': from_address})
    transaction_contract = token_contract.functions.transfer(to_address, int(value)).buildTransaction(
        {'gasPrice': gas_price, 'gas': gas, 'nonce': nonce})
    txn_signed_usdt = w3.eth.account.signTransaction(transaction_contract,private_key)
    txn_hash = w3.eth.sendRawTransaction(txn_signed_usdt.rawTransaction)
    return txn_hash