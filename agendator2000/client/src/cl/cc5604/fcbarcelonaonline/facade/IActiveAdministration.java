package cl.cc5604.fcbarcelonaonline.facade;

import cl.cc5604.fcbarcelonaonline.entity.ActiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.ActiveTypeEntity;

import java.util.List;

public interface IActiveAdministration {

    void createActive(ActiveEntity active);

    List<ActiveEntity> findAllActives();

    ActiveEntity findById(int idActive);

    void updateActive(ActiveEntity active);

    void deleteActive(int idActive);

    // active types

    List<ActiveTypeEntity> findAllActiveTypes();

}
