package com.blockchain.watertap.mapper.opensea.mapper;

import com.blockchain.watertap.mapper.opensea.model.TransferPO;
import com.blockchain.watertap.model.request.ListRequest;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferMapper {

    void insert(TransferPO transferPO);

    void update(TransferPO transferPO);

    List<TransferPO> getByState(@Param("state") Integer state, @Param("toAddress") String toAddress);

    List<TransferPO> getByTransferTime(@Param("transferTime") LocalDateTime localDateTime, @Param("toAddress") String toAddress);

    List<TransferPO> listByPage(@Param("request") ListRequest listRequest);
}
