package cl.cc5604.fcbarcelonaonline.business.application;

import cl.cc5604.fcbarcelonaonline.entity.*;
import cl.cc5604.fcbarcelonaonline.persistence.dao.*;
import cl.cc5604.fcbarcelonaonline.util.FormatHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 27-06-13
 * Time: 01:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = Logger.getLogger(ApplicationListenerBean.class);

    @Autowired private IActiveTypeDAO activeTypeDAO;
    @Autowired private IContactTypeDAO contactTypeDAO;
    @Autowired private INationalityDAO nationalityDAO;
    @Autowired private IPassiveStatusDAO passiveStatusDAO;
    @Autowired private IStaffTypeDAO staffTypeDAO;
    @Autowired private IContractDAO contractDAO;
    @Autowired private IUserDAO userDAO;
    @Autowired private IAssociateDAO associateDAO;
    @Autowired private IActiveDAO activeDAO;
    @Autowired private IPassiveDAO passiveDAO;
    @Autowired private IStaffDAO staffDAO;
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

        if (passiveStatusDAO.findAll().size() == 0) {
            logger.info("inserting passiveStatuses ...");
            passiveStatusDAO.save(makePassiveStatus(1, "Pendiente", "Aún no se paga la totalidad"));
            passiveStatusDAO.save(makePassiveStatus(2, "Pagado", "Totalmente pagado"));
            passiveStatusDAO.flush();
        } else {
            logger.info("omitting insertion of passiveStatus");
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

        if (activeTypeDAO.findAll().size() == 0) {
            logger.info("inserting activeTypes ...");
            activeTypeDAO.save(makeActiveType(1, "Inmueble", "Bien raíz"));
            activeTypeDAO.save(makeActiveType(2, "Mueble", "Bien mueble"));
            activeTypeDAO.save(makeActiveType(3, "Otro", "Otro bien"));
            activeTypeDAO.flush();
        } else {
            logger.info("omitting insertion of activeTypes");
        }

        if (staffTypeDAO.findAll().size() == 0) {
            logger.info("inserting staffTypes ...");
            staffTypeDAO.save(makeStaffType(1, "Directivo", "Toma decisiones"));
            staffTypeDAO.save(makeStaffType(2, "Tecnico", "Entrena equipo"));
            staffTypeDAO.save(makeStaffType(3, "Doctor", "Cura enfermedades"));
            staffTypeDAO.save(makeStaffType(4, "Fisioterapeuta", "Rehabilita lesionados"));
            staffTypeDAO.save(makeStaffType(5, "Arquero", "Guarda metas"));
            staffTypeDAO.save(makeStaffType(6, "Defensa", "Defiende el arco"));
            staffTypeDAO.save(makeStaffType(7, "Mediocampista", "Juega al medio del campo"));
            staffTypeDAO.save(makeStaffType(8, "Delantero", "Mete goles"));
            staffTypeDAO.flush();
        } else {
            logger.info("omitting insertion of staffTypes");
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

        if(staffDAO.findAll().size() == 0){
            logger.info("inserting staffs ...");
            staffDAO.save(makeStaff(1, "nombre1", "apellido1", "12/07/1991", true, 0,     1, 1, 4));
            staffDAO.save(makeStaff(2, "nombre2", "apellido2", "11/10/1992", true, 0,     2, 1, 5));
            staffDAO.save(makeStaff(3, "nombre3", "apellido3", "07/08/1993", true, 12000, 5, 5, 6));
            staffDAO.save(makeStaff(4, "nombre4", "apellido4", "04/04/1991", true, 10000, 6, 6, 7));
        }else{
            logger.info("omitting insertion of staffs");
        }

        if (activeDAO.findAll().size() == 0) {
            logger.info("inserting actives ...");
            activeDAO.save(makeActive(1, 1, 15000));
            activeDAO.save(makeActive(2, 1, 12000));
            activeDAO.save(makeActive(3, 2, 4000));
            activeDAO.save(makeActive(4, 2, 12000));
            activeDAO.save(makeActive(5, 3, 4000));
            activeDAO.save(makeActive(6, 3, 7000));
            activeDAO.save(makeActive(7, 3, 8000));
        } else {
            logger.info("omitting insertion of actives");
        }

        if (passiveDAO.findAll().size() == 0) {
            logger.info("inserting passives ...");
            passiveDAO.save(makePassive(1, 1, 8000));
            passiveDAO.save(makePassive(2, 1, 10000));
            passiveDAO.save(makePassive(3, 1, 12000));
            passiveDAO.save(makePassive(4, 2, 2000));
            passiveDAO.save(makePassive(5, 2, 3000));
        } else {
            logger.info("omitting insertion of passives");
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

    private PassiveStatusEntity makePassiveStatus(int id, String status, String description) {
        PassiveStatusEntity passiveStatus = new PassiveStatusEntity();
        passiveStatus.setId(id);
        passiveStatus.setStatus(status);
        passiveStatus.setDescription(description);
        return passiveStatus;
    }

    private ContactTypeEntity makeContactType(int id, String type, String description) {
        ContactTypeEntity cte = new ContactTypeEntity();
        cte.setId(id);
        cte.setType(type);
        cte.setDescription(description);
        return cte;
    }

    private ActiveTypeEntity makeActiveType(int id, String type, String description) {
        ActiveTypeEntity ate = new ActiveTypeEntity();
        ate.setId(id);
        ate.setType(type);
        ate.setDescription(description);
        return ate;
    }

    private StaffTypeEntity makeStaffType(int id, String type, String description) {
        StaffTypeEntity ste = new StaffTypeEntity();
        ste.setId(id);
        ste.setType(type);
        ste.setDescription(description);
        return ste;
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

    private ActiveEntity makeActive(int id, int activeTypeId, int value) {
        ActiveEntity active = new ActiveEntity();
        active.setId(id);
        active.setActiveType(activeTypeDAO.findById(activeTypeId));
        active.setValue(value);
        return active;
    }

    private PassiveEntity makePassive(int id, int passiveStatusId, int value) {
        PassiveEntity passive = new PassiveEntity();
        passive.setId(id);
        passive.setPassiveStatus(passiveStatusDAO.findById(passiveStatusId));
        passive.setValue(value);
        return passive;
    }

    private StaffEntity makeStaff(
        int id,
        String firstName,
        String lastName,
        String birthDate,
        boolean hired,
        int baseValue,
        int staffTypeId,
        int nationalityId,
        int contractId
    ){
        StaffEntity staff = new StaffEntity();
        staff.setId(id);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setBirthDate(FormatHelper.parseDate(birthDate));
        staff.setHired(hired);
        staff.setBaseValue(baseValue);
        staff.setStaffType(staffTypeDAO.findById(staffTypeId));
        staff.setNationality(nationalityDAO.findById(nationalityId));
        staff.setContract(contractDAO.findById(contractId));
        return staff;
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
