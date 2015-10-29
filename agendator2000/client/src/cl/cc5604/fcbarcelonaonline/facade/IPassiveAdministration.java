package cl.cc5604.fcbarcelonaonline.facade;

import cl.cc5604.fcbarcelonaonline.entity.PassiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;

import java.util.List;

public interface IPassiveAdministration {

    void createPassive(PassiveEntity passive);

    List<PassiveEntity> findAllPassives();

    PassiveEntity findById(int idPassive);

    void updatePassive(PassiveEntity passive);

    void deletePassive(int idPassive);

    // passive status

    List<PassiveStatusEntity> findAllPassiveStatus();

    PassiveStatusEntity findPassiveStatusByStatus(String type);
}
