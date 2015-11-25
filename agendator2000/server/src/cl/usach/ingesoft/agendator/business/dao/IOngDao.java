package cl.usach.ingesoft.agendator.business.dao;

import cl.usach.ingesoft.agendator.business.dao.base.IBaseDao;
import cl.usach.ingesoft.agendator.entity.OngEntity;

import java.util.Date;

public interface IOngDao extends IBaseDao<OngEntity, Integer> {
    /**
     *
     * @param date date which will be contained  the startDate and endDate of the Ong.
     * @return the Ong which will contain the date Date, or null if none was found.
     */
    OngEntity findByEnclosingDate(Date date);
}
