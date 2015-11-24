package cl.usach.ingesoft.agendator.business.framework;

import cl.usach.ingesoft.agendator.entity.AdministratorEntity;
import cl.usach.ingesoft.agendator.entity.PatientEntity;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(ApplicationListenerBean.class);

    //@Autowired private IUserDAO userDAO;

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
/*
        if (userDAO.findAll().size() == 0) {
            logger.info("inserting users ...");
            userDAO.save(makeAdministrator(1, "foo1", "bar1", "f1", "l1", 1000, "root"));
            userDAO.save(makeAdministrator(2, "foo2", "bar2", "f2", "l2", 2000, "general"));
            userDAO.save(makeAdministrator(3, "foo3", "bar3", "f3", "l3", 2009, "general"));

            userDAO.save(makeAdministrator(3, "foo3", "bar3", "f3", "l3", 2009, "general"));
            userDAO.save(makeAdministrator(3, "foo3", "bar3", "f3", "l3", 2009, "general"));
            userDAO.save(makeAdministrator(3, "foo3", "bar3", "f3", "l3", 2009, "general"));
        } else {
            logger.info("omitting insertion of users");
        }
*/
        logger.info("database updated");
    }

    private AdministratorEntity makeAdministrator(int id, String email, String password, String firstName, String lastName, int run, String type) {
        AdministratorEntity a = new AdministratorEntity();

        // UserEntity attributes
        a.setId(id);
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setRun(run);
        a.setEmail(email);
        a.setHashedPassword(password);

        // Administrator attributes
        a.setType("root");
        return a;
    }

    private PatientEntity makePatient(int id, String email, String password, String firstName, String lastName, int run, String type) {
        PatientEntity a = new PatientEntity();

        // UserEntity attributes
        a.setId(id);
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setRun(run);
        a.setEmail(email);
        a.setHashedPassword(password);

        return a;
    }

}
