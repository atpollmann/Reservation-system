package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.common.BusinessException;
import cl.cc5604.fcbarcelonaonline.entity.*;
import cl.cc5604.fcbarcelonaonline.facade.IActiveAdministration;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
import cl.cc5604.fcbarcelonaonline.facade.IFinancesReport;
import cl.cc5604.fcbarcelonaonline.facade.IStaffAdministration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

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
public class ActiveAdministrationBeanTest {

    @Autowired private IActiveAdministration activeAdministration;

    @Autowired private IFinancesReport financesReport;

    @Autowired private IStaffAdministration staffAdministration;
    @Autowired private IContractAdministration contractAdministration;

    private Timestamp makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        long millis = cal.getTimeInMillis();
        return new Timestamp(millis - millis % 1000);
    }

    @Test
    public void crudTest() {

        assertEquals(0, financesReport.getTotalActives());
        assertEquals(0, financesReport.getTotalPassives());
        assertEquals(0, financesReport.getTotalNet());

        // findAllActiveTypes
        List<ActiveTypeEntity> activeTypes = activeAdministration.findAllActiveTypes();
        assertTrue(activeTypes.size() >= 2);

        // valid create ActiveEntity
        ActiveEntity ae1 = new ActiveEntity();
        ae1.setActiveType(activeTypes.get(0));
        ae1.setValue(234);

        activeAdministration.createActive(ae1);

        assertEquals(234, financesReport.getTotalActives());
        assertEquals(234, financesReport.getTotalNet());

        // findById
        ActiveEntity ae2 = activeAdministration.findById(ae1.getId());
        assertEquals(ae1, ae2);

        //findAllActivesTest
        List<ActiveEntity> allActives = activeAdministration.findAllActives();
        assertFalse(allActives.isEmpty());
        assertTrue(allActives.contains(ae1)); // dependencia implicita de ActiveEntity#equals !

        // updateActiveTest
        int theId = ae1.getId();

        // modificar ae1
        ae1.setValue(45000);

        // respaldar ae1
        ActiveEntity ae3 = new ActiveEntity();
        ae3.setId(ae1.getId());
        ae3.setActiveType(ae1.getActiveType());
        ae3.setValue(ae1.getValue());

        activeAdministration.updateActive(ae1);

        assertEquals(45000, financesReport.getTotalActives());
        assertEquals(45000, financesReport.getTotalNet());

        ActiveEntity ae4 = activeAdministration.findById(theId);

        assertEquals(ae3, ae4); // ae1 modificado antes de guardar == ae4

        int oldActivesCount = activeAdministration.findAllActives().size();

        activeAdministration.deleteActive(theId);

        assertTrue(activeAdministration.findAllActives().size() < oldActivesCount);

        assertEquals(0, financesReport.getTotalActives());
        assertEquals(0, financesReport.getTotalNet());

        // agregar 1
        ActiveEntity ae5 = new ActiveEntity();
        ae5.setId(null);
        ae5.setActiveType(activeTypes.get(0));
        ae5.setValue(350000);
        activeAdministration.createActive(ae5);

        //Probar Formula en staffentity     --------------------------------------

        // tipos de personal
        List<StaffTypeEntity> staffTypes = staffAdministration.findAllStaffTypes();
        assertFalse(staffTypes.isEmpty());

        System.out.println("--- StaffTypes ---");
        for(StaffTypeEntity ste : staffTypes){
            System.out.println(ste);
        }
        System.out.println("----------------");

        // nacionalidades
        List<NationalityEntity> nationalities = staffAdministration.findAllNationalities();
        assertFalse(nationalities.isEmpty());

        ContractEntity newContract = new ContractEntity();
        newContract.setInitDate(makeDate(1900, 1, 1));
        newContract.setExpirationDate(makeDate(1901, 1, 1));
        newContract.setMonthlyPayment(25456);
        contractAdministration.createContract(newContract);

        // recuperar todos los contratos (no se verifica si el contrato ya esta asignado)
        List<ContractEntity> contracts = contractAdministration.findAllContracts();
        assertFalse(contracts.isEmpty());

        // crear personal
        StaffEntity staff = new StaffEntity();
        staff.setFirstName("foo");
        staff.setLastName("bar");
        staff.setBirthDate(makeDate(2000, 4, 1));
        staff.setBaseValue(100000);
        staff.setHired(false);
        staff.setContract(contracts.get(0));
        staff.setNationality(nationalities.get(0));

        //System.out.println(staffTypes.get(0) + "\n" + "\n"+ "\n"+ "\n");

        staff.setStaffType(staffTypes.get(6)); // mediocampista

        staffAdministration.createStaff(staff);

        List<StaffEntity> personal = staffAdministration.findAllStaffs();
        System.out.println("------ StaffEntity ------");
        for(StaffEntity se : personal){
            System.out.println(se.toString());
        }
        System.out.println("--------------------------");

        // activos_anteriores+valor_base*1.5
        assertEquals((int)(350000+100000*1.5), financesReport.getTotalActives2());

        staffAdministration.deleteStaff(staff.getId());
    }

    @Test(expected = BusinessException.class)
    public void create1Test() {
        // null directamente
        activeAdministration.createActive(null);
    }

    @Test(expected = BusinessException.class)
    public void create2Test() {
        ActiveEntity ae = new ActiveEntity();
        // ae.getActiveType() == null
        activeAdministration.createActive(ae);
    }

    @Test(expected = BusinessException.class)
    public void create3Test() {
        ActiveTypeEntity ate = new ActiveTypeEntity();
        ate.setId(1);
        ate.setDescription("asdf");
        ate.setType("asdf");

        ActiveEntity ae = new ActiveEntity();
        ae.setId(1); // no puede colocarse id!
        ae.setActiveType(ate);

        activeAdministration.createActive(ae);
    }

    @Test(expected = BusinessException.class)
    public void create4Test() {
        ActiveEntity ae = new ActiveEntity();
        ae.setId(null);
        ae.setActiveType(null);
        activeAdministration.createActive(ae);
    }

    @Test(expected = BusinessException.class)
    public void update1Test() {
        ActiveTypeEntity ate = new ActiveTypeEntity();
        ate.setId(1);
        ate.setDescription("asdf");
        ate.setType("asdf");

        ActiveEntity ae = new ActiveEntity();
        ae.setId(null); // error!
        ae.setActiveType(ate);

        activeAdministration.updateActive(ae);
    }

    @Test(expected = BusinessException.class)
    public void update2Test() {
        ActiveTypeEntity ate = new ActiveTypeEntity();
        ate.setId(null); // error!
        ate.setDescription("asdf");
        ate.setType("asdf");

        ActiveEntity ae = new ActiveEntity();
        ae.setId(1);
        ae.setActiveType(ate);

        activeAdministration.updateActive(ae);
    }

    @Test(expected = BusinessException.class)
    public void update3Test() {
        // null directo
        activeAdministration.updateActive(null);
    }

    @Test
    public void findById1Test() {
        assertNull(activeAdministration.findById(-2)); // id valido, pero inexistente
    }

    @Test(expected = BusinessException.class)
    public void deleteTest() {
        activeAdministration.deleteActive(-2); // id valido, pero inexistente
    }
}
