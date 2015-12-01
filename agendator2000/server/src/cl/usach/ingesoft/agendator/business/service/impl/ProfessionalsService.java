package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.dao.impl.AppointmentDao;
import cl.usach.ingesoft.agendator.business.dao.impl.ProfessionalDao;
import cl.usach.ingesoft.agendator.business.dao.impl.ScheduleDao;
import cl.usach.ingesoft.agendator.business.dao.impl.SpecialityDao;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfessionalsService implements IProfessionalsService {

    @Autowired private ProfessionalDao professionalDao;
    @Autowired private SpecialityDao specialityDao;
    @Autowired private ScheduleDao scheduleDao;
    @Autowired private AppointmentDao appointmentDao;

    @Transactional
    @Override
    public List<ProfessionalEntity> findProfessionalsByCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Transactional
    @Override
    public ProfessionalCalendarBO getProfessionalCalendar(ProfessionalEntity professional,
            CareSessionEntity careSession) {
        List<ScheduleEntity> frees = new ArrayList<ScheduleEntity>();
        List<ProfessionalCalendarBO.Pair<ScheduleEntity, AppointmentEntity>> taken
                = new ArrayList<ProfessionalCalendarBO.Pair<ScheduleEntity, AppointmentEntity>>();

        List<ScheduleEntity> allSchedules
                = scheduleDao.findByProfessionalByCareSession(professional.getId(), careSession.getId());
        List<AppointmentEntity> allAppointments
                = appointmentDao.findByProfessionalByCareSession(professional.getId(), careSession.getId());

        // allAppointments should be a subset of allSchedules

        // put all the schedules into a hashmap (by id)
        HashMap<Integer, ScheduleEntity> sch = new HashMap<Integer, ScheduleEntity>();
        for (ScheduleEntity se : allSchedules) {
            sch.put(se.getId(), se);
        }

        // get the schedules related to appointments and put them in taken
        for (AppointmentEntity ae : allAppointments) {
            ScheduleEntity se = sch.remove(ae.getSchedule().getId());
            ProfessionalCalendarBO.Pair<ScheduleEntity, AppointmentEntity> pair
                    = new ProfessionalCalendarBO.Pair<ScheduleEntity, AppointmentEntity>(se, ae);
            taken.add(pair);
        }

        // put the remaining schedules from the hash into frees
        for(Map.Entry<Integer,ScheduleEntity> entry : sch.entrySet()) {
            frees.add(entry.getValue());
        }

        return new ProfessionalCalendarBO(careSession, professional, frees, taken);
    }

    @Transactional
    @Override
    public ProfessionalCalendarBO setScheduleForProfessional(CareSessionEntity careSession,
            ProfessionalEntity professional, List<ScheduleEntity> schedules) {
        for(ScheduleEntity se : schedules) {
            se.setId(null);
            se.setProfessional(professional);
            se.setCareSession(careSession);
        }
        scheduleDao.saveAll(schedules);
        scheduleDao.flush();
        return getProfessionalCalendar(professional, careSession);
    }

    @Transactional
    @Override
    public List<SpecialityEntity> findSpecialities() {
        return specialityDao.findAll();
    }

    @Transactional
    @Override
    public SpecialityEntity findSpecialityById(int id) {
        return specialityDao.findById(id);
    }
}
