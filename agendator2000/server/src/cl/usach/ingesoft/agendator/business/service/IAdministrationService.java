package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;

import java.util.Date;
import java.util.List;

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
     * @param idCareSession Id for the CareSession to be canceled.
     * @return whether the CareSession could be canceled or not (true means canceled, otherwise false).
     */
    boolean cancelCareSession(int idCareSession);

    /**
     * Operation 7.
     *
     * @param careSession CareSession to be updated.
     * @return CareSession just updated.
     */
    CareSessionEntity updateCareSession(CareSessionEntity careSession);

    /**
     *
     * @param ongId Id for the Ong to retrieve.
     * @return Ong for the id provided, or null if none was found.
     */
    OngEntity findCurrentOng(int ongId);
    OngEntity findCurrentOng();

    /**
     *
     * @param ong Ong for which the CareSession is to be retrieved.
     * @param currentTime Date and time for which the CareSession is to be retrieved.
     * @return CareSession for the supplied parameters (or null if none was found).
     */
    CareSessionEntity findCurrentCareSession(OngEntity ong, Date currentTime);

    List<CareSessionEntity> findAllCareSessions(int ongId);

    List<CareSessionEntity> findPendingCareSessions(int ongId, Date currentDate);

    /**
     *
     * @param idCareSession If for the CareSession to be retrieved.
     * @return CareSession for the provided id, or null if none was found.
     */
    CareSessionEntity findCareSessionById(int idCareSession);
}
