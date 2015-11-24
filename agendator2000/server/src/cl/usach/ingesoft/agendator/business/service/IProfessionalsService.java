package cl.usach.ingesoft.agendator.business.service;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;

import java.util.List;

public interface IProfessionalsService {

    /**
     * Gets the list of professionals which have scheduled work for the provided CareSession.
     * @param careSession CareSession for which the professionals are to be retrieved.
     * @return List of the professionals which will work in the given CareSession.
     */
    List<ProfessionalEntity> findProfessionalsByCareSession(CareSessionEntity careSession);

    /**
     * Operation 11 (this implicitely yields the Operation 12).
     *
     * @param professional Professional for which the ProfessionalCalendar object is to be retrieved.
     * @param careSession CareSession for which the ProfessionalCalendar object is to be retrieved (which specifies the
     *          schedule for the professional chosen)
     * @return non-null ProfessionalCalendar for the parameters provided.
     */
    ProfessionalCalendarBO getProfessionalCalendar(ProfessionalEntity professional, CareSessionEntity careSession);

    /**
     * Operation 13.
     *
     * @param careSession CareSession for which the schedule is to be created.
     * @param professional Professional for which the schedule is to be created.
     * @param schedules Time allocations for which the professional will work on the specified CareSession.
     * @return non-null ProfessionalCalendar with the just added information.
     */
    ProfessionalCalendarBO setScheduleForProfessional(CareSessionEntity careSession, ProfessionalEntity professional,
            List<ScheduleEntity> schedules);
}
