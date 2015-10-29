package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.common.BusinessException;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

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
public class ContractAdministrationBeanTest {

    // interfaz a la capa de servicio
    @Autowired private IContractAdministration contractAdministration;

    private Date makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        long millis = cal.getTimeInMillis();
        return new Date(millis - millis % 1000);
    }

    @Test
    public void crudTest() {

        ContractEntity c = new ContractEntity();
        c.setMonthlyPayment(23500);
        c.setInitDate(makeDate(2000, 1, 3));
        c.setExpirationDate(makeDate(2013, 1, 3));

        contractAdministration.createContract(c);
        assertNotNull(c.getId());

        assertFalse(contractAdministration.findAllContracts().isEmpty());

        c.setInitDate(makeDate(1995, 10, 1));

        int theId = c.getId();

        // backup
        ContractEntity d = new ContractEntity();
        d.setId(c.getId());
        d.setMonthlyPayment(c.getMonthlyPayment());
        d.setInitDate(c.getInitDate());
        d.setExpirationDate(c.getExpirationDate());

        contractAdministration.updateContract(c);
        assertEquals(d, contractAdministration.findById(theId));

        int oldSize = contractAdministration.findAllContracts().size();

        contractAdministration.deleteContract(d.getId());

        assertEquals(oldSize - 1, contractAdministration.findAllContracts().size());
    }

    @Test(expected = BusinessException.class)
    public void create1Test() {
        ContractEntity c = new ContractEntity();
        c.setId(1); // malo
        c.setMonthlyPayment(250);
        c.setInitDate(makeDate(2000, 1, 1));
        c.setExpirationDate(makeDate(2010, 1, 1));
        contractAdministration.createContract(c);
    }

    @Test(expected = BusinessException.class)
    public void create2Test() {
        ContractEntity c = new ContractEntity();
        c.setId(null);
        c.setMonthlyPayment(-250); // malo
        c.setInitDate(makeDate(2000, 1, 1));
        c.setExpirationDate(makeDate(2010, 1, 1));
        contractAdministration.createContract(c);
    }

    @Test(expected = BusinessException.class)
    public void create3Test() {
        ContractEntity c = new ContractEntity();
        c.setId(null);
        c.setMonthlyPayment(250);

        // malo
        c.setInitDate(makeDate(2010, 1, 1));
        c.setExpirationDate(makeDate(2009, 1, 1));

        contractAdministration.createContract(c);
    }

    @Test(expected = BusinessException.class)
    public void create4Test() {
        ContractEntity c = new ContractEntity();
        c.setId(null);
        c.setMonthlyPayment(250);
        c.setInitDate(null); // malo
        c.setExpirationDate(makeDate(2010, 1, 1));
        contractAdministration.createContract(c);
    }

    @Test(expected = BusinessException.class)
    public void create5Test() {
        ContractEntity c = new ContractEntity();
        c.setId(null);
        c.setMonthlyPayment(250);
        c.setInitDate(makeDate(2010, 1, 1));
        c.setExpirationDate(null);// malo
        contractAdministration.createContract(c);
    }

    @Test(expected = BusinessException.class)
    public void create6Test() {
        contractAdministration.createContract(null);
    }


    @Test(expected = BusinessException.class)
    public void update1Test() {
        ContractEntity c = new ContractEntity();
        c.setId(1);
        c.setMonthlyPayment(-200); // malo
        c.setInitDate(makeDate(2000, 1, 1));
        c.setExpirationDate(makeDate(2010, 1, 1));
        contractAdministration.updateContract(c);
    }

    @Test(expected = BusinessException.class)
    public void update2Test() {
        ContractEntity c = new ContractEntity();
        c.setId(1);
        c.setMonthlyPayment(200);

        // malo
        c.setInitDate(makeDate(2010, 1, 1));
        c.setExpirationDate(makeDate(2000, 1, 1));

        contractAdministration.updateContract(c);
    }

    @Test(expected = BusinessException.class)
    public void update3Test() {
        ContractEntity c = new ContractEntity();
        c.setId(null); // malo
        c.setMonthlyPayment(200);
        c.setInitDate(makeDate(2000, 1, 1));
        c.setExpirationDate(makeDate(2010, 1, 1));
        contractAdministration.updateContract(c);
    }

    @Test(expected = BusinessException.class)
    public void update4Test() {
        ContractEntity c = new ContractEntity();
        c.setId(1);
        c.setMonthlyPayment(200);
        c.setInitDate(null);     // malo
        c.setExpirationDate(null); // malo
        contractAdministration.updateContract(c);
    }

}
