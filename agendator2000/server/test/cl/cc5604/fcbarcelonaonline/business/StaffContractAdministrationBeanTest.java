package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.common.BusinessException;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
import cl.cc5604.fcbarcelonaonline.facade.IStaffAdministration;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
public class StaffContractAdministrationBeanTest {

    @Autowired private IStaffAdministration staffAdministration;
    @Autowired private IContractAdministration contractAdministration;

    private Date makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        long millis = cal.getTimeInMillis();
        return new Date(millis - millis % 1000);
    }

    @Test
    public void crudTest() {

        // tipos de personal
        List<StaffTypeEntity> types = staffAdministration.findAllStaffTypes();
        assertFalse(types.isEmpty());

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
        staff.setBaseValue(2500000);
        staff.setHired(false);
        staff.setContract(contracts.get(0));
        staff.setNationality(nationalities.get(0));
        staff.setStaffType(types.get(0));

        staffAdministration.createStaff(staff);

        // recuperar id de secuencia
        int theId = staff.getId();

        // recuperar largo de la lista en BD
        assertFalse(staffAdministration.findAllStaffs().isEmpty());

        // volver a recuperar
        StaffEntity another = staffAdministration.findById(theId);

        // comparar contra instancia previa al guardado
        assertEquals(staff, another);
        assertEquals(staff.getNationality(), another.getNationality());
        assertEquals(staff.getContract(), another.getContract());
        assertEquals(staff.getStaffType(), another.getStaffType());

        int idContract = contracts.get(0).getId();

        // borrar personal
        staffAdministration.deleteStaff(another.getId());

        // verificar que se ha borrado su contrato asociado
        assertNull(contractAdministration.findById(idContract));
    }


    @Test(expected = BusinessException.class)
    public void createStaff1Test() {
        staffAdministration.createStaff(null);
    }

    @Test(expected = BusinessException.class)
    public void createStaff2Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(1); // malo!
        staffAdministration.createStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void createStaff3Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(null);
        staff.setBaseValue(-5); // malo
        staffAdministration.createStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void createStaff4Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(null);
        staff.setBaseValue(5000);
        staff.setContract(null); // malo
        staffAdministration.createStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void createStaff5Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(null);
        staff.setBaseValue(5000);
        staff.setContract(new ContractEntity());
        staff.setNationality(null);// malo
        staffAdministration.createStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void createStaff6Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(null);
        staff.setBaseValue(5000);
        staff.setContract(new ContractEntity()); // relacion valida, pero inexistente
        staff.setNationality(new NationalityEntity()); // relacion valida, pero inexistente
        staff.setStaffType(null);// malo
        staffAdministration.createStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void updateStaff1Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(null); // malo
        staff.setBaseValue(5000);
        staff.setContract(new ContractEntity());
        staff.setNationality(new NationalityEntity());
        staff.setStaffType(null);
        staffAdministration.updateStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void updateStaff2Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(2);
        staff.setBaseValue(null); // malo
        staff.setContract(new ContractEntity());
        staff.setNationality(new NationalityEntity());
        staff.setStaffType(null); // malo
        staffAdministration.updateStaff(staff);
    }

    @Test(expected = BusinessException.class)
    public void updateStaff3Test() {
        StaffEntity staff = new StaffEntity();
        staff.setId(1);
        staff.setBaseValue(5000);
        staff.setContract(new ContractEntity());
        staff.setNationality(null);// malo
        staff.setStaffType(new StaffTypeEntity());
        staffAdministration.updateStaff(staff);
    }

}
