package cl.usach.ingesoft.agendator.util.dao;

import cl.usach.ingesoft.agendator.entity.ContractEntity;

import java.util.List;

public interface IContractDAO extends IBaseDAO<ContractEntity, Integer> {
    List<ContractEntity> findAllAvailableContracts();
}
