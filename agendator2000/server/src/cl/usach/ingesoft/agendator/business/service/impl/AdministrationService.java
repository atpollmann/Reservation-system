package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.dao.impl.CareSessionDao;
import cl.usach.ingesoft.agendator.business.dao.impl.OngDao;
import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdministrationService implements IAdministrationService {
    @Autowired private CareSessionDao careSessionDao;
    @Autowired private OngDao ongDao;

    @Transactional
    @Override
    public CareSessionEntity createCareSession(CareSessionEntity careSession) {
        careSessionDao.save(careSession);
        careSessionDao.flush();
        return careSession;
    }

    @Transactional
    @Override
    public boolean cancelCareSession(int idCareSession) {
        CareSessionEntity cse = careSessionDao.findById(idCareSession);
        if (cse != null && cse.getValid()) {
            cse.setValid(false);
            careSessionDao.save(cse);
            careSessionDao.flush();
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public CareSessionEntity updateCareSession(CareSessionEntity careSession) {
        careSessionDao.update(careSession);
        careSessionDao.flush();
        return careSession;
    }

    @Transactional
    @Override
    public OngEntity findCurrentOng(int ongId) {
        return ongDao.findById(ongId);
    }

    @Transactional
    @Override
    public OngEntity findCurrentOng() {
        return ongDao.findFirst();
    }

    @Transactional
    @Override
    public CareSessionEntity findCurrentCareSession(OngEntity ong, Date currentTime) {
        return careSessionDao.findByDate(ong.getId(), currentTime);
    }

    @Transactional
    @Override
    public List<CareSessionEntity> findAllCareSessions(int ongId) {
        return careSessionDao.findByOng(ongId);
    }

    @Transactional
    @Override
    public List<CareSessionEntity> findPendingCareSessions(int ongId, Date currentDate) {
        return careSessionDao.findPending(ongId, currentDate);
    }

    @Transactional
    @Override
    public CareSessionEntity findCareSessionById(int idCareSession) {
        return careSessionDao.findById(idCareSession);
    }
}
