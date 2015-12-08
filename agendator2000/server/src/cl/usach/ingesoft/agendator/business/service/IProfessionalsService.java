package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import cl.usach.ingesoft.agendator.entity.SpecialityEntity;

import java.util.List;

public interface IProfessionalsService {

    /**
     * Gets the list of professionals which have scheduled work for the provided CareSession.
     * @param idCareSession Id of the CareSession for which the professionals are to be retrieved.
     * @return List of the professionals which will work in the given CareSession.
     */
    List<ProfessionalEntity> findProfessionalsByCareSession(int idCareSession);

    /**
     * Operation 11 (this implicitely yields the Operation 12).
     *
     * @param idProfessional Id of the Professional for which the ProfessionalCalendar object is to be retrieved.
     * @param idCareSession Id of the CareSession for which the ProfessionalCalendar object is to be retrieved (which
     *          specifies the schedule for the professional chosen)
     * @return non-null ProfessionalCalendar for the parameters provided.
     */
    ProfessionalCalendarBO getProfessionalCalendar(int idProfessional, int idCareSession);
    ProfessionalCalendarBO getProfessionalCalendar(int idProfessional);

    /**
     * Operation 13.
     *
     * @param idProfessional Id of the Professional for which the schedule is to be created.
     * @param idCareSession Id of the CareSession for which the schedule is to be created.
     * @param schedules Time allocations for which the professional will work on the specified CareSession.
     */
    void saveScheduleForProfessional(int idProfessional, int idCareSession, List<ScheduleEntity> schedules);

    /**
     * Gets the list of specialities for this application.
     * @return List of specialities to be associated to any Professional.
     */
    List<SpecialityEntity> findSpecialities();

    /**
     * Gets a speciality by its known id.
     * @param id Id of the speciality to find.
     * @return Non-null speciality if exists, or null if none was found.
     */
    SpecialityEntity findSpecialityById(int id);

    void parseAndSaveSchedules(int idCareSession, int idProfessional, String allSchedules);
}
