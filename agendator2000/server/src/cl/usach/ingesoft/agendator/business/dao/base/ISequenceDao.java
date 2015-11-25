package cl.usach.ingesoft.agendator.business.dao.base;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.base.SequenceEntity;

public interface ISequenceDao extends IBaseDao<SequenceEntity, Integer> {
    SequenceEntity generate();
}
