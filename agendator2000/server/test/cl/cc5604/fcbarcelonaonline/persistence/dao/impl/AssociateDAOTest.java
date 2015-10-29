package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.AssociateEntity;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IAssociateDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IContractDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.INationalityDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 03-05-13
 * Time: 09:04 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
public class AssociateDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IContractDAO contractDao;
    @Autowired
    private INationalityDAO nationalityDao;
    @Autowired
    private IAssociateDAO dao;

    private Timestamp makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return new Timestamp(cal.getTimeInMillis());
    }

    private AssociateEntity makeAssociate(
            String firstName,
            String lastName,
            Timestamp birthDate,
            ContractEntity contract,
            NationalityEntity nationality
    ) {
        AssociateEntity associate = new AssociateEntity();
        associate.setFirstName(firstName);
        associate.setLastName(lastName);
        associate.setBirthDate(birthDate);
        associate.setContract(contract);
        associate.setNationality(nationality);
        return associate;
    }

    @Test
    public void testCrud() {
        List<NationalityEntity> nationalities = nationalityDao.findAll();
        List<ContractEntity> contracts = contractDao.findAll();

        // dependemos de datos insertados anteriormente por otros tests

        assertFalse(nationalities.isEmpty());
        assertFalse(contracts.isEmpty());

        // verificar que no hay socios
        List<AssociateEntity> associates0 = dao.findAll();
        assertTrue(associates0.isEmpty());

        // guardar socio
        AssociateEntity a1 = makeAssociate("sheldon", "cooper", makeDate(1975, 1, 1), contracts.get(0), nationalities.get(0));
        dao.save(a1);
//        dao.flush();

        // recuperar y comparar
        List<AssociateEntity> associates1 = dao.findAll();
        assertEquals(1, associates1.size());
        assertEquals(a1, associates1.get(0));

        // borrar
        dao.delete(associates1.get(0));
//        dao.flush();

        // verificar que no hay socios
        associates0 = dao.findAll();
        assertTrue(associates0.isEmpty());

        // consultar lista de objetos relacionados
        List<NationalityEntity> nationalitiesAfter = nationalityDao.findAll();
        List<ContractEntity> contractsAfter = contractDao.findAll();

        assertEquals(nationalities.size(), nationalitiesAfter.size());
        assertEquals(contracts.size(), contractsAfter.size());

        dao.save(makeAssociate("sheldon1", "cooper1", makeDate(1975, 1, 1), contracts.get(0), nationalities.get(0)));
        dao.save(makeAssociate("sheldon2", "cooper2", makeDate(1975, 1, 1), contracts.get(0), nationalities.get(0)));
        dao.save(makeAssociate("sheldon3", "cooper3", makeDate(1975, 1, 1), contracts.get(0), nationalities.get(0)));
//        dao.flush();

        assertEquals(3, dao.findAll().size());

    }
}
