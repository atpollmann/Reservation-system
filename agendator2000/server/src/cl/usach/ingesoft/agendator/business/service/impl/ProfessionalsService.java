package cl.usach.ingesoft.agendator.business.service.impl;

import static cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO.Pair;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.dao.impl.*;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
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
    @Autowired private CareSessionDao careSessionDao;

    @Transactional
    @Override
    public List<ProfessionalEntity> findProfessionalsByCareSession(int idCareSession) {
        return professionalDao.findByCareSession(idCareSession);
    }

    @Transactional
    @Override
    public ProfessionalCalendarBO getProfessionalCalendar(int idProfessional, int idCareSession) {
        ProfessionalEntity professional = professionalDao.findById(idProfessional);
        CareSessionEntity careSession = careSessionDao.findById(idCareSession);

        Validator.shouldBeFound(professional);
        Validator.shouldBeFound(careSession);

        List<ScheduleEntity> frees = new ArrayList();
        List<Pair<ScheduleEntity, AppointmentEntity>> taken = new ArrayList();

        List<ScheduleEntity> allSchedules = scheduleDao.findByProfessionalByCareSession(idProfessional, idCareSession);
        List<AppointmentEntity> allAppointments = appointmentDao.findByProfessionalByCareSession(idProfessional,
                idCareSession);

        // allAppointments should be a subset of allSchedules

        // put all the schedules into a hashmap (by id)
        HashMap<Integer, ScheduleEntity> sch = new HashMap();
        for (ScheduleEntity se : allSchedules) {
            sch.put(se.getId(), se);
        }

        // get the schedules related to appointments and put them in taken
        for (AppointmentEntity ae : allAppointments) {
            ScheduleEntity se = sch.remove(ae.getSchedule().getId());
            Pair<ScheduleEntity, AppointmentEntity> pair = new Pair(se, ae);
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
    public ProfessionalCalendarBO setScheduleForProfessional(int idProfessional, int idCareSession,
            List<ScheduleEntity> schedules) {
        CareSessionEntity careSession = careSessionDao.findById(idCareSession);
        ProfessionalEntity professional = professionalDao.findById(idProfessional);

        Validator.shouldBeFound(careSession);
        Validator.shouldBeFound(professional);

        for(ScheduleEntity se : schedules) {
            se.setId(null);
            se.setProfessional(professional);
            se.setCareSession(careSession);
        }
        scheduleDao.saveAll(schedules);
        scheduleDao.flush();
        return getProfessionalCalendar(idProfessional, idCareSession);
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
