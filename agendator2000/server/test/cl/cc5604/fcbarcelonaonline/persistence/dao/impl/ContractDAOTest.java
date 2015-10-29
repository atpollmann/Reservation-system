package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.persistence.dao.IContractDAO;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class ContractDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IContractDAO dao;

    private Timestamp makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        long millis = cal.getTimeInMillis();
        return new Timestamp(millis - millis % 1000);
    }

    private ContractEntity makeContract(Timestamp expirationDate, Timestamp initDate, int monthlyPayment) {
        ContractEntity c = new ContractEntity();
        c.setExpirationDate(expirationDate);
        c.setInitDate(initDate);
        c.setMonthlyPayment(monthlyPayment);
        return c;
    }

    @Test
    public void crudTest() {
        // asegurar que la BD esta vacia
        List<ContractEntity> contracts0 = dao.findAll();
        assertEquals(0, contracts0.size());

        // insertar 3 contratos (save)
        dao.save(makeContract(makeDate(2013, 3, 1), makeDate(2013, 1, 1), 213000));
        dao.save(makeContract(makeDate(2014, 6, 11), makeDate(2014, 3, 1), 214000));
        dao.save(makeContract(makeDate(2015, 9, 21), makeDate(2015, 9, 1), 215000));
//        dao.flush();

        // recuperar los 3 ultimos
        List<ContractEntity> contracts3 = dao.findAll();
        assertEquals(3, contracts3.size());

        // insertar otro
        dao.save(makeContract(makeDate(2016, 12, 31), makeDate(2016, 6, 1), 216000));
//        dao.flush();

        // recuperar exactamente 4
        List<ContractEntity> contracts4 = dao.findAll();
        assertEquals(4, contracts4.size());

        ContractEntity first = contracts4.get(0);

        // recuperar el contrato 1
        ContractEntity c1 = dao.findById(first.getId());
        assertNotNull(c1);
        assertEquals(first.getExpirationDate(), c1.getExpirationDate());
        assertEquals(first.getInitDate(), c1.getInitDate());
        assertEquals(first.getMonthlyPayment(), c1.getMonthlyPayment());
    }

}
