package cl.usach.ingesoft.agendator.util.business.application;

import cl.usach.ingesoft.agendator.entity.*;
import cl.usach.ingesoft.agendator.util.dao.*;
import cl.usach.ingesoft.agendator.util.FormatHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = Logger.getLogger(ApplicationListenerBean.class);

    @Autowired private IContactTypeDAO contactTypeDAO;
    @Autowired private INationalityDAO nationalityDAO;
    @Autowired private IContractDAO contractDAO;
    @Autowired private IUserDAO userDAO;
    @Autowired private IAssociateDAO associateDAO;
    @Autowired private IContactDataDAO contactDataDAO;

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

        if (nationalityDAO.findAll().size() == 0) {
            logger.info("inserting nationalities ...");
            nationalityDAO.save(makeNationality(1, "Chile"));
            nationalityDAO.save(makeNationality(2, "Argentina"));
            nationalityDAO.save(makeNationality(3, "Brasil"));
            nationalityDAO.save(makeNationality(4, "Colombia"));
            nationalityDAO.save(makeNationality(5, "Venezuela"));
            nationalityDAO.save(makeNationality(6, "Ecuador"));
            nationalityDAO.flush();
        } else {
            logger.info("omitting insertion of nationalities");
        }

        if (contactTypeDAO.findAll().size() == 0) {
            logger.info("inserting contactTypes ...");
            contactTypeDAO.save(makeContactType(1, "Direccion", "Dirección"));
            contactTypeDAO.save(makeContactType(2, "Telefono", "Teléfono particular"));
            contactTypeDAO.save(makeContactType(3, "Email", "Correo electrónico"));
            contactTypeDAO.flush();
        } else {
            logger.info("omitting insertion of contactTypes");
        }

        if (contractDAO.findAll().size() == 0) {
            logger.info("inserting contracts ...");
            contractDAO.save(makeContract(1, "01/07/2013", "30/08/2013", 0));
            contractDAO.save(makeContract(2, "01/09/2013", "15/09/2013", 1));
            contractDAO.save(makeContract(3, "01/10/2013", "30/10/2013", 10));
            contractDAO.save(makeContract(4, "01/10/2013", "30/10/2013", 950));
            contractDAO.save(makeContract(5, "01/10/2013", "30/10/2013", 9500));
            contractDAO.save(makeContract(6, "01/10/2013", "30/10/2013", 125000));
            contractDAO.save(makeContract(7, "01/10/2013", "30/10/2013", 1650250));
        } else {
            logger.info("omitting insertion of contracts");
        }

        if (userDAO.findAll().size() == 0) {
            logger.info("inserting users ...");
            userDAO.save(makeUser(1, "foo", "bar"));
            userDAO.save(makeUser(2, "admin", "admin"));
            userDAO.save(makeUser(3, "root", "12345"));
        } else {
            logger.info("omitting insertion of users");
        }

        if (associateDAO.findAll().size() == 0) {
            logger.info("inserting associates ...");
            associateDAO.save(makeAssociate(1, "nombre1", "apellido1", "01/10/2013", false, 1, 1));
            associateDAO.save(makeAssociate(2, "nombre2", "apellido2", "01/10/2013", false, 2, 2));
            associateDAO.save(makeAssociate(3, "nombre3", "apellido3", "01/10/2013", false, 3, 3));
        } else {
            logger.info("omitting insertion of associates");
        }

        if(contactDataDAO.findAll().size() == 0){
            logger.info("inserting contactData ...");
            contactDataDAO.save(makeContactData(1, 1, 1, "address #1"));
            contactDataDAO.save(makeContactData(2, 1, 2, "5621234567"));
            contactDataDAO.save(makeContactData(3, 1, 3, "foo@bar.com"));
            contactDataDAO.save(makeContactData(4, 2, 1, "address #2"));
            contactDataDAO.save(makeContactData(5, 2, 2, "5629876543"));
            contactDataDAO.save(makeContactData(6, 3, 1, "address #3"));
        } else {
            logger.info("omitting insertion of contactData");
        }

        logger.info("database updated");
    }

    private NationalityEntity makeNationality(int id, String country) {
        NationalityEntity n = new NationalityEntity();
        n.setId(id);
        n.setCountry(country);
        return n;
    }

    private ContactTypeEntity makeContactType(int id, String type, String description) {
        ContactTypeEntity cte = new ContactTypeEntity();
        cte.setId(id);
        cte.setType(type);
        cte.setDescription(description);
        return cte;
    }

    private ContractEntity makeContract(int id, String initDate, String expirationDate, int monthlyPayment) {
        ContractEntity ce = new ContractEntity();
        ce.setId(id);
        ce.setInitDate(FormatHelper.parseDate(initDate));
        ce.setExpirationDate(FormatHelper.parseDate(expirationDate));
        ce.setMonthlyPayment(monthlyPayment);
        return ce;
    }

    private UserEntity makeUser(int id, String username, String password) {
        UserEntity ue = new UserEntity();
        ue.setId(id);
        ue.setUsername(username);
        ue.setPassword(password);
        return ue;
    }

    private AssociateEntity makeAssociate(
            int id,
            String firstName,
            String lastName,
            String birthDate,
            boolean seatRight,
            int idNationality,
            int idContract
    ) {
        AssociateEntity associate = new AssociateEntity();
        associate.setId(id);
        associate.setFirstName(firstName);
        associate.setLastName(lastName);
        associate.setBirthDate(FormatHelper.parseDate(birthDate));
        associate.setSeatRight(seatRight);
        associate.setNationality(nationalityDAO.findById(idNationality));
        associate.setContract(contractDAO.findById(idContract));
        return associate;
    }

    private ContactDataEntity makeContactData(int id, int associateId, int contactTypeId, Object valueData){
        ContactDataEntity contactData = new ContactDataEntity();
        contactData.setId(id);
        contactData.setAssociate(associateDAO.findById(associateId));
        contactData.setContactType(contactTypeDAO.findById(contactTypeId));
        contactData.setValueData(String.valueOf(valueData));
        return contactData;
    }

}
