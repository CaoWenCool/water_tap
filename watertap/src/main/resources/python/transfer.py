#!/usr/bin/env python3

import json
from flask import Flask, request, jsonify
from flask import Flask
from web3 import Web3, HTTPProvider
from eth_account import Account

app = Flask(__name__)
app.debug = True


@app.route('/transfer', methods=['post'])
def post_http():
    params = request.data.decode('utf-8')
    # 获取到POST过来的数据，因为我这里传过来的数据需要转换一下编码。根据晶具体情况而定
    params = json.loads(params)
    network = params["network"]
    token_address = params["token_address"]
    abi = params["abi"]
    private_key = params["private_key"]
    to_address = params["to_address"]
    trans_value = params["trans_value"]

    w3 = Web3(HTTPProvider(network))  # 网络地址
    token_contract = Web3.toChecksumAddress(token_address)  # 合约地址
    abi_json = json.loads(abi)
    token_contract = w3.eth.contract(address=token_contract, abi=abi_json)
    print(w3.isConnected())
    acc = Account.from_key(private_key=private_key)
    from_address = Web3.toChecksumAddress(acc.address)
    balance = token_contract.functions.balanceOf(from_address).call()
    print(Web3.toWei(balance, 'ether'))
    # TODO 添加余额的判断
    to_address = Web3.toChecksumAddress(to_address)
    nonce = w3.eth.getTransactionCount(from_address)
    gas_price = w3.eth.gasPrice
    trans_value = float(trans_value)
    value = Web3.toWei(trans_value, 'ether')
    balance = Web3.toWei(balance, 'ether')
    if balance < value:
        return -1
    gas = token_contract.functions.transfer(to_address, int(value)).estimateGas({'from': from_address})
    transaction_contract = token_contract.functions.transfer(to_address, int(value)).buildTransaction(
        {'gasPrice': gas_price, 'gas': gas, 'nonce': nonce})
    txn_signed_usdt = w3.eth.account.signTransaction(transaction_contract, private_key)
    txn_hash = w3.eth.sendRawTransaction(txn_signed_usdt.rawTransaction)
    txn_hash = Web3.toHex(txn_hash)

    result = {
        "txn_hash": txn_hash
    }
    print(result)
    # 把区获取到的数据转为JSON格式。
    return jsonify(result)


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=9090)
    # 这里指定了地址和端口号。也可以不指定地址填0.0.0.0那么就会使用本机地址ip
