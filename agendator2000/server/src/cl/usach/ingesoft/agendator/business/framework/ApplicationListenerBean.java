package cl.usach.ingesoft.agendator.business.framework;

import cl.usach.ingesoft.agendator.business.dao.*;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.entity.*;
import cl.usach.ingesoft.agendator.util.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(ApplicationListenerBean.class);

    @Autowired private IUsersService usersService;

    @Autowired private ISpecialityDao specialityDao;
    @Autowired private IOngDao ongDao;
    @Autowired private ICareSessionDao careSessionDao;
    @Autowired private IProfessionalDao professionalDao;
    @Autowired private IScheduleDao scheduleDao;

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

        if (usersService.findAllUsers().size() == 0) {

            if (specialityDao.findAll().isEmpty()) {
                specialityDao.save(makeSpeciality(1, "sicologo"));
                specialityDao.save(makeSpeciality(2, "doctor"));
                specialityDao.save(makeSpeciality(3, "pediatra"));
                specialityDao.flush();
            }

            usersService.createUser(makeAdministrator(12000000, "juan", "perez", "j@p.cl", "abc123", "root"));
            usersService.createUser(makePatient(14000000, "rosendo", "morgado", "r@m.cl", "1234"));
            usersService.createUser(makeProfessional(16000000, "belfor", "cruz", "b@c.cl", "a4"));
            usersService.createUser(makeProfessional(404, "chilly", "willy", "penguin@p.cl", "890"));
        }

        if (ongDao.findAll().isEmpty()) {
            OngEntity e = new OngEntity();
            e.setId(OngEntity.ONLY_ONE_ONG);
            e.setName("Da ONG");
            ongDao.save(e);
            ongDao.flush();
        }

        if (careSessionDao.findAll().isEmpty()) {
            CareSessionEntity c = new CareSessionEntity();
            c.setLocation("LasCondes");
            c.setOng(ongDao.findById(1));
            c.setStartDate(DateUtils.makeDate(2015, 1, 10, 8, 0, 0));
            c.setEndDate(DateUtils.makeDate(2015, 1, 20, 18, 0, 0));
            c.setValid(true);
            careSessionDao.save(c);
            careSessionDao.flush();
        }

        if (scheduleDao.findAll().isEmpty()) {
            CareSessionEntity cse = careSessionDao.findAll().get(0); // Enero10 a Enero20 de 2015

            List<ProfessionalEntity> professionals = professionalDao.findAll();
            ProfessionalEntity profA = professionals.get(0);
            ScheduleEntity []schedA = {
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 11, 8, 0, 0), 30),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 11, 8, 30, 0), 30),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 11, 9, 0, 0), 30),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 11, 9, 30, 0), 15),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 11, 9, 45, 0), 15),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 11, 10, 0, 0), 15),

                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 12, 14, 0, 0), 30),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 12, 14, 30, 0), 15),
                    makeSched(profA, cse, DateUtils.makeDate(2015, 1, 12, 14, 45, 0), 15),
            };

            scheduleDao.saveAll(Arrays.asList(schedA));
            scheduleDao.flush();

            ProfessionalEntity profB = professionals.get(1);
            ScheduleEntity []schedB = {
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 10, 0, 0), 30),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 10, 30, 0), 30),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 11, 0, 0), 30),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 11, 30, 0), 30),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 15, 0, 0), 30),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 15, 30, 0), 30),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 11, 16, 0, 0), 30),

                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 12, 14, 0, 0), 15),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 12, 14, 15, 0), 15),
                    makeSched(profB, cse, DateUtils.makeDate(2015, 1, 12, 14, 30, 0), 15),
            };
            scheduleDao.saveAll(Arrays.asList(schedB));
            scheduleDao.flush();

        }

        logger.info("database updated");
    }

    private AdministratorEntity makeAdministrator(
            int run,
            String firstName,
            String lastName,
            String email,
            String hashedPassword,
            String type
    ) {
        AdministratorEntity ae = new AdministratorEntity();
        ae.setRun(run);
        ae.setFirstName(firstName);
        ae.setLastName(lastName);
        ae.setEmail(email);
        ae.setHashedPassword(hashedPassword);
        ae.setType(type);
        return ae;
    }

    private PatientEntity makePatient(
            int run,
            String firstName,
            String lastName,
            String email,
            String hashedPassword
    ) {
        PatientEntity pe = new PatientEntity();
        pe.setRun(run);
        pe.setFirstName(firstName);
        pe.setLastName(lastName);
        pe.setEmail(email);
        pe.setHashedPassword(hashedPassword);
        return pe;
    }

    private ProfessionalEntity makeProfessional(
            int run,
            String firstName,
            String lastName,
            String email,
            String hashedPassword
    ) {
        ProfessionalEntity pe = new ProfessionalEntity();
        pe.setRun(run);
        pe.setFirstName(firstName);
        pe.setLastName(lastName);
        pe.setEmail(email);
        pe.setHashedPassword(hashedPassword);
        pe.setSpeciality(specialityDao.findById(1));
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

    private ScheduleEntity makeSched(ProfessionalEntity p, CareSessionEntity cse, Date startDate, int durationMin) {
        ScheduleEntity s = new ScheduleEntity();
        s.setProfessional(p);
        s.setCareSession(cse);
        s.setStartDate(startDate);
        s.setEndDate(new Date(startDate.getTime() + durationMin * 60000));
        return s;
    }
}
