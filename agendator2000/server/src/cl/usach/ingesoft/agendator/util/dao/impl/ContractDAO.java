package cl.usach.ingesoft.agendator.util.dao.impl;

import cl.usach.ingesoft.agendator.util.dao.IContractDAO;
import cl.usach.ingesoft.agendator.entity.ContractEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractDAO extends BaseDAO<ContractEntity, Integer> implements IContractDAO {
    public ContractDAO() {
        super(ContractEntity.class);
    }

    @Override
    public List<ContractEntity> findAllAvailableContracts() {
        // retorna los contratos que no estan relacionados, ni a socio ni a personal
        return findByStatement("from ContractEntity ce");
    }
}
