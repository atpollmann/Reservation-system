package cl.usach.ingesoft.agendator.business.framework;

import cl.usach.ingesoft.agendator.business.dao.IAdministratorDao;
import cl.usach.ingesoft.agendator.business.dao.IPatientDao;
import cl.usach.ingesoft.agendator.business.dao.IProfessionalDao;
import cl.usach.ingesoft.agendator.business.dao.ISpecialityDao;
import cl.usach.ingesoft.agendator.entity.AdministratorEntity;
import cl.usach.ingesoft.agendator.entity.PatientEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import cl.usach.ingesoft.agendator.entity.SpecialityEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(ApplicationListenerBean.class);

    @Autowired private IAdministratorDao administratorDao;
    @Autowired private IPatientDao patientDao;
    @Autowired private IProfessionalDao professionalDao;
    @Autowired private ISpecialityDao specialityDao;

    private static final String APPLICATION_PROPERTIES_FILENAME = "/db.properties";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            // hack para pasar una propiedad desde el build.properties que indica si se debe llenar la BD
            InputStream inputStream = ApplicationListenerBean.class.getResourceAsStream(APPLICATION_PROPERTIES_FILENAME);

            if(inputStream == null){
                logger.info(APPLICATION_PROPERTIES_FILENAME+" not found, omitting database insertion");
            }else{
                logger.info(APPLICATION_PROPERTIES_FILENAME+" found, parsing");

                Properties properties = new Properties();
                properties.load(inputStream);

                boolean shouldBePopulated = "true".equals(properties.getProperty("database.populate", "false"));

                logger.info("Doing database insertion: "+shouldBePopulated);

                if(shouldBePopulated){
                    populateDatabase();
                }
            }

        }catch (Exception e){
            logger.info("Exception:" + e.getMessage());
        }
    }

    private void populateDatabase(){
        logger.info("populating database with initial data...");

        if (administratorDao.findAll().isEmpty()) {
            administratorDao.save(makeAdministrator(1, 12000000, "juan", "perez", "j@p.cl", "abc123", "root"));
            administratorDao.flush();
        }

        if (patientDao.findAll().isEmpty()) {
            patientDao.save(makePatient(2, 14000000, "rosendo", "morgado", "r@m.cl", "1234"));
            patientDao.flush();
        }

        if (specialityDao.findAll().isEmpty()) {
            specialityDao.save(makeSpeciality(1, "sicologo"));
            specialityDao.flush();
        }

        if (professionalDao.findAll().isEmpty()) {
            professionalDao.save(makeProfessional(3, 16000000, "belfor", "cruz", "b@c.cl", "a4", 1));
            professionalDao.flush();
        }

        logger.info("database updated");
    }

    private AdministratorEntity makeAdministrator(
            int id,
            int run,
            String firstName,
            String lastName,
            String email,
            String hashedPassword,
            String type
    ) {
        AdministratorEntity ae = new AdministratorEntity();
        ae.setId(id);
        ae.setRun(run);
        ae.setFirstName(firstName);
        ae.setLastName(lastName);
        ae.setEmail(email);
        ae.setHashedPassword(hashedPassword);
        ae.setType(type);
        return ae;
    }

    private PatientEntity makePatient(
            int id,
            int run,
            String firstName,
            String lastName,
            String email,
            String hashedPassword
    ) {
        PatientEntity pe = new PatientEntity();
        pe.setId(id);
        pe.setRun(run);
        pe.setFirstName(firstName);
        pe.setLastName(lastName);
        pe.setEmail(email);
        pe.setHashedPassword(hashedPassword);
        return pe;
    }

    private ProfessionalEntity makeProfessional(
            int id,
            int run,
            String firstName,
            String lastName,
            String email,
            String hashedPassword,
            int idSpeciality
    ) {
        ProfessionalEntity pe = new ProfessionalEntity();
        pe.setId(id);
        pe.setRun(run);
        pe.setFirstName(firstName);
        pe.setLastName(lastName);
        pe.setEmail(email);
        pe.setHashedPassword(hashedPassword);
        pe.setSpeciality(specialityDao.findById(idSpeciality));
        return pe;
    }

    private SpecialityEntity makeSpeciality(
            int id,
            String name
    ) {
        SpecialityEntity se = new SpecialityEntity();
        se.setId(id);
        se.setName(name);
        return se;
    }
}
