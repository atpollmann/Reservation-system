package cl.usach.ingesoft.agendator.util.dao;

import cl.usach.ingesoft.agendator.entity.NationalityEntity;

public interface INationalityDAO extends IBaseDAO<NationalityEntity, Integer> {
    NationalityEntity findByName(String name);
}
