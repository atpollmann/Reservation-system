package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdministrationService implements IAdministrationService {
    @Override
    public CareSessionEntity createCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public boolean cancelCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public CareSessionEntity updateCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public OngEntity findCurrentOng(Date currentTime) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public CareSessionEntity findCurrentCareSession(OngEntity ong, Date currentTime) {
        throw new RuntimeException("Not yet implemented.");
    }
}
