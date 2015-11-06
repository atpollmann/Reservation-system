package cl.usach.ingesoft.agendator.facade;

import cl.usach.ingesoft.agendator.entity.ContractEntity;

import java.util.List;

public interface IContractAdministration {
    void createContract(ContractEntity newContract);

    List<ContractEntity> findAllContracts();

    List<ContractEntity> findAllAvailableContracts();

    ContractEntity findById(int idContract);

    void updateContract(ContractEntity contract);

    void deleteContract(int idContract);
}
