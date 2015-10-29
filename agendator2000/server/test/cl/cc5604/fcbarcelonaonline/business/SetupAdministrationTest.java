package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.persistence.dao.*;
import cl.cc5604.fcbarcelonaonline.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 04-05-13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
public class SetupAdministrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired private IActiveDAO activeDAO;
    @Autowired private IActiveTypeDAO activeTypeDAO;
    @Autowired private IAssociateDAO associateDAO;
    @Autowired private IContactDataDAO contactDataDAO;
    @Autowired private IContactTypeDAO contactTypeDAO;
    @Autowired private IContractDAO contractDAO;
    @Autowired private INationalityDAO nationalityDAO;
    @Autowired private IPassiveDAO passiveDAO;
    @Autowired private IPassiveStatusDAO passiveStatusDAO;
    @Autowired private IStaffDAO staffDAO;
    @Autowired private IStaffTypeDAO staffTypeDAO;
    @Autowired private IUserDAO userDAO;

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

    public void deleteAll() {
        passiveDAO.deleteAll();
        passiveDAO.flush();

        passiveStatusDAO.deleteAll();
        passiveStatusDAO.flush();

        contactDataDAO.deleteAll();
        contactDataDAO.flush();

        contactTypeDAO.deleteAll();
        contactTypeDAO.flush();

        associateDAO.deleteAll();
        associateDAO.flush();

        activeDAO.deleteAll();
        activeDAO.flush();

        activeTypeDAO.deleteAll();
        activeTypeDAO.flush();

        staffDAO.deleteAll();
        staffDAO.flush();

        staffTypeDAO.deleteAll();
        staffTypeDAO.flush();

        contractDAO.deleteAll();
        contractDAO.flush();

        nationalityDAO.deleteAll();
        nationalityDAO.flush();

        userDAO.deleteAll();
        userDAO.flush();
    }


    public void insertAll() {
        nationalityDAO.save(makeNationality(1, "Chile"));
        nationalityDAO.save(makeNationality(2, "Argentina"));
        nationalityDAO.save(makeNationality(3, "Brasil"));
        nationalityDAO.save(makeNationality(4, "Colombia"));
        nationalityDAO.save(makeNationality(5, "Venezuela"));
        nationalityDAO.save(makeNationality(6, "Ecuador"));
        nationalityDAO.flush();

        passiveStatusDAO.save(makePassiveStatus(1, "Pendiente", "aun no se paga la totalidad"));
        passiveStatusDAO.save(makePassiveStatus(2, "Pagado", "totalmente pagado"));
        passiveStatusDAO.flush();

        contactTypeDAO.save(makeContactType(1, "Dirección", "dirección"));
        contactTypeDAO.save(makeContactType(2, "Teléfono", "teléfono particular"));
        contactTypeDAO.save(makeContactType(3, "Email", "correo electrónico"));
        contactTypeDAO.flush();

        activeTypeDAO.save(makeActiveType(1, "Inmueble", "bien raíz"));
        activeTypeDAO.save(makeActiveType(2, "Mueble", "bien mueble"));
        activeTypeDAO.save(makeActiveType(3, "Automóvil", "camioneta para materiales"));
        activeTypeDAO.flush();

        staffTypeDAO.save(makeStaffType(1, "Directivo", "toma decisiones"));
        staffTypeDAO.save(makeStaffType(2, "Técnico", "entrena equipo"));
        staffTypeDAO.save(makeStaffType(3, "Doctor", "cura enfermedades"));
        staffTypeDAO.save(makeStaffType(4, "Fisioterapeuta", "rehabilita lesionados"));
        staffTypeDAO.save(makeStaffType(5, "Arquero", "guarda metas"));
        staffTypeDAO.save(makeStaffType(6, "Defensa", "defiende el arco"));
        staffTypeDAO.save(makeStaffType(7, "Mediocampista", "juega al medio del campo"));
        staffTypeDAO.save(makeStaffType(8, "Delantero", "mete goles"));
        staffTypeDAO.flush();
    }

    @Test
    public void setup() {
        deleteAll();
        insertAll();
    }
}
