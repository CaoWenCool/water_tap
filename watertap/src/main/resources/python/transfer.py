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
    print(to_address)
    return token_address