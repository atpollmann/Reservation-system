package cl.usach.ingesoft.agendator.business.application;

import cl.usach.ingesoft.agendator.business.dao.IUserDAO;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(ApplicationListenerBean.class);

    @Autowired private IUserDAO userDAO;

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
            logger.info("Exception:"+e.getMessage());
        }
    }

    private void populateDatabase(){
        logger.info("populating database with initial data...");

        if (userDAO.findAll().size() == 0) {
            logger.info("inserting users ...");
            userDAO.save(makeUser(1, "foo", "bar"));
            userDAO.save(makeUser(2, "admin", "admin"));
            userDAO.save(makeUser(3, "root", "12345"));
        } else {
            logger.info("omitting insertion of users");
        }

        logger.info("database updated");
    }

    private UserEntity makeUser(int id, String username, String password) {
        UserEntity ue = new UserEntity();
        ue.setId(id);
        ue.setUsername(username);
        ue.setPassword(password);
        return ue;
    }

}
