DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer` (
                            `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                            `to_address` varchar(50) NOT NULL COMMENT '发送地址',
                            `transfer_val` int(5) NOT NULL COMMENT '交易值',
                            `transfer_time` datetime DEFAULT NULL COMMENT '交易时间',
                            `tx_hash` varchar(255) DEFAULT NULL COMMENT '交易hash',
                            `state` tinyint(4) NOT NULL COMMENT '交易状态',
                            `network` varchar(50) NOT NULL COMMENT '网络',
                            `update_time` datetime NOT NULL COMMENT '修改时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;