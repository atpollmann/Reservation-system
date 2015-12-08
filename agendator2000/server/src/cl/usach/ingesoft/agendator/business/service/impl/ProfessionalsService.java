package cl.usach.ingesoft.agendator.business.service.impl;

import static cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO.Pair;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.dao.impl.*;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.entity.*;
import cl.usach.ingesoft.agendator.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
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

    private ProfessionalCalendarBO buildProfessionalCalendar(int idProfessional, Integer idCareSession) {
        ProfessionalEntity professional = professionalDao.findById(idProfessional);
        Validator.shouldBeFound(professional);

        CareSessionEntity careSession = null;

        if (idCareSession != null) {
            careSession = careSessionDao.findById(idCareSession);
            Validator.shouldBeFound(careSession);
        }

        List<ScheduleEntity> frees = new ArrayList();
        List<Pair<ScheduleEntity, AppointmentEntity>> taken = new ArrayList();

        List<ScheduleEntity> allSchedules = (idCareSession == null ?
                scheduleDao.findByProfessional(idProfessional) :
                scheduleDao.findByProfessionalByCareSession(idProfessional, idCareSession)
        );
        List<AppointmentEntity> allAppointments = (idCareSession == null ?
                appointmentDao.findByProfessional(idProfessional) :
                appointmentDao.findByProfessionalByCareSession(idProfessional, idCareSession)
        );

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
    public ProfessionalCalendarBO getProfessionalCalendar(int idProfessional, int idCareSession) {
        return buildProfessionalCalendar(idProfessional, idCareSession);
    }

    @Transactional
    @Override
    public ProfessionalCalendarBO getProfessionalCalendar(int idProfessional) {
        return buildProfessionalCalendar(idProfessional, null);
    }

    @Transactional
    @Override
    public void saveScheduleForProfessional(int idProfessional, int idCareSession, List<ScheduleEntity> schedules) {
        CareSessionEntity careSession = careSessionDao.findById(idCareSession);
        ProfessionalEntity professional = professionalDao.findById(idProfessional);

        Validator.shouldBeFound(careSession);
        Validator.shouldBeFound(professional);

        List<ScheduleEntity> alreadyInDB = scheduleDao.findByProfessionalByCareSession(idProfessional, idCareSession);

        // Remove the schedules (with its appointments) no longer present
        List<Integer> idsToRemove = getIdsFromAnotContainedInB(alreadyInDB, schedules);
        if (!idsToRemove.isEmpty()) {
            scheduleDao.deleteSchedulesWithAppointments(idsToRemove);
            scheduleDao.flush();
        }

        // get list of IDs on alreadyInDB but schedules, to erase them
        for(ScheduleEntity se : schedules) {
            se.setProfessional(professional);
            se.setCareSession(careSession);
        }
        // create or update
        scheduleDao.saveAll(schedules);
        scheduleDao.flush();
    }

    private List<Integer> getIdsFromAnotContainedInB(List<ScheduleEntity> listA, List<ScheduleEntity> listB) {
        // To quickly know whether it exists an id in B
        HashMap<Integer, Boolean> bMap = new HashMap();
        for (ScheduleEntity se : listB) {
            if (se.getId() != null) {
                bMap.put(se.getId(), true);
            }
        }
        // only let pass the ids of A that are not in B (result = A\B)
        List<Integer> result = new ArrayList();
        for (ScheduleEntity se : listA) {
            if (se.getId() != null) {
                Boolean isInB = bMap.get(se.getId());
                if (isInB == null || !isInB.booleanValue()) {
                    result.add(se.getId());
                }
            }
        }
        return result;
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

    @Transactional
    @Override
    public void parseAndSaveSchedules(int idCareSession, int idProfessional, String allSchedules) {
        List<ScheduleEntity> schedules = makeList(allSchedules);
        saveScheduleForProfessional(idProfessional, idCareSession, schedules);
    }

    private List<ScheduleEntity> makeList(String allSchedules) {
        List<ScheduleEntity> schedules = new ArrayList<ScheduleEntity>();
        String[] parts = allSchedules.split(";");
        for (String p : parts) {
            p = p.trim();
            if (!p.isEmpty()) {
                String[] tokens = p.split(",");
                if (tokens.length == 3) {
                    try {
                        ScheduleEntity se = new ScheduleEntity();
                        String strId = tokens[0].trim();
                        if (!strId.isEmpty()) {
                            se.setId(Integer.parseInt(strId));
                        }
                        se.setStartDate(DateUtils.tryParse(tokens[1]));
                        se.setEndDate(DateUtils.tryParse(tokens[2]));
                        schedules.add(se);
                    } catch (ParseException e) {
                        // Omit malformed schedule dates
                    } catch (NumberFormatException nfe) {
                        // Omit malformed schedule id
                    }
                }
            }
        }
        return schedules;
    }
}
