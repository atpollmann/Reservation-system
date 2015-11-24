package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;

import java.util.Date;

public interface IAdministrationService {
    /**
     * Operation 5.
     *
     * @param careSession Creates a new CareSession for the given parameters.
     * @return just created CareSession.
     */
    CareSessionEntity createCareSession(CareSessionEntity careSession);

    /**
     * Operation 6.
     *
     * Cancels a CareSession (this does not deletes it).
     *
     * @param careSession CareSession to be canceled.
     * @return whether the CareSession could be canceled or not (true means canceled, otherwise false).
     */
    boolean cancelCareSession(CareSessionEntity careSession);

    /**
     * Operation 7.
     *
     * @param careSession CareSession to be updated.
     * @return CareSession just updated.
     */
    CareSessionEntity updateCareSession(CareSessionEntity careSession);

    /**
     *
     * @param currentTime Date and time for which the Ong is to be found.
     * @return Ong for the supplied time. If no Ong is found, null is returned instead.
     */
    OngEntity findCurrentOng(Date currentTime);

    /**
     *
     * @param ong Ong for which the CareSession is to be retrieved.
     * @param currentTime Date and time for which the CareSession is to be retrieved.
     * @return CareSession for the supplied parameters (or null if none was found).
     */
    CareSessionEntity findCurrentCareSession(OngEntity ong, Date currentTime);
}
