package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;

import java.util.List;

public interface IProfessionalDao extends IBaseDao<ProfessionalEntity, Integer> {
    List<ProfessionalEntity> findByCareSession(int idCareSession);
}
