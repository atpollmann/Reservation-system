package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IContractDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.INationalityDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IStaffDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IStaffTypeDAO;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;
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
public class StaffDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IStaffDAO dao;
    @Autowired
    private IContractDAO contractDao;
    @Autowired
    private INationalityDAO nationalityDao;
    @Autowired
    private IStaffTypeDAO staffTypeDao;

    private Timestamp makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        long millis = cal.getTimeInMillis();
        return new Timestamp(millis - millis % 1000);
    }

    @Test
    public void testCrud() {

        List<StaffTypeEntity> staffTypes = staffTypeDao.findAll();
        assertFalse(staffTypes.isEmpty());

        List<ContractEntity> contracts = contractDao.findAll();
        assertFalse(contracts.isEmpty());

        List<NationalityEntity> nationalities = nationalityDao.findAll();
        assertFalse(nationalities.isEmpty());

        List<StaffEntity> staffs0 = dao.findAll();
        assertTrue(staffs0.isEmpty());

        // construir staff entity
        StaffEntity aStaff = new StaffEntity();
        aStaff.setFirstName("indiana");
        aStaff.setLastName("jones");
        aStaff.setBirthDate(makeDate(1915, 1, 1));
        aStaff.setHired(true);
        aStaff.setBaseValue(12345);

        aStaff.setContract(contracts.get(0));
        aStaff.setNationality(nationalities.get(0));
        aStaff.setStaffType(staffTypes.get(0));

        dao.save(aStaff);
//        dao.flush();

        List<StaffEntity> staffs1 = dao.findAll();
        assertFalse(staffs1.isEmpty());

        assertEquals(aStaff, staffs1.get(0));
    }
}
