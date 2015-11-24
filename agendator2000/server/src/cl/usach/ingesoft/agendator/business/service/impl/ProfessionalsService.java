package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalsService implements IProfessionalsService {

    @Override
    public List<ProfessionalEntity> findProfessionalsByCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public ProfessionalCalendarBO getProfessionalCalendar(ProfessionalEntity professional,
            CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public ProfessionalCalendarBO setScheduleForProfessional(CareSessionEntity careSession,
            ProfessionalEntity professional, List<ScheduleEntity> schedules) {
        throw new RuntimeException("Not yet implemented.");
    }
}
