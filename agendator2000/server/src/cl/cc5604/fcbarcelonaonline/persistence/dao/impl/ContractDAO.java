package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IContractDAO;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
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
        return findByStatement("from ContractEntity ce where (ce.hooked2associate = false and ce.hooked2staff = false)");
    }
}
