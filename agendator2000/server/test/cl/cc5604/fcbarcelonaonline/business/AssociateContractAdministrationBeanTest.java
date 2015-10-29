package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.common.BusinessException;
import cl.cc5604.fcbarcelonaonline.entity.*;
import cl.cc5604.fcbarcelonaonline.facade.IAssociateAdministration;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
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
public class AssociateContractAdministrationBeanTest {

    // interfaz a la capa de servicio
    @Autowired
    private IAssociateAdministration associateAdministration;
    @Autowired
    private IContractAdministration contractAdministration;

    private Date makeDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        long millis = cal.getTimeInMillis();
        return new Date(millis - millis % 1000);
    }

    @Test
    public void crudTest() {
        // guardar un contrato
        ContractEntity newContract = new ContractEntity();
        newContract.setInitDate(makeDate(2013, Calendar.MARCH, 1));
        newContract.setExpirationDate(makeDate(2013, Calendar.MAY, 25));
        newContract.setMonthlyPayment(5000);
        contractAdministration.createContract(newContract);

        assertFalse(contractAdministration.findAllContracts().isEmpty());

        ContractEntity savedContract = contractAdministration.findById(newContract.getId());
        assertEquals(newContract, savedContract);

        List<NationalityEntity> nationalities = associateAdministration.findAllNationalities();
        assertNotNull(nationalities);
        assertFalse(nationalities.isEmpty());

        AssociateEntity a = new AssociateEntity();
        a.setContract(savedContract);
        a.setNationality(nationalities.get(0));
        a.setSeatRight(true);
        a.setFirstName("foo");
        a.setLastName("bar");
        a.setBirthDate(makeDate(1991, Calendar.JULY, 12));

        associateAdministration.createAssociate(a);
        assertFalse(associateAdministration.findAllAssociates().isEmpty());

        // recuperar tipos de contacto
        List<ContactTypeEntity> contactTypes = associateAdministration.findAllContactTypes();
        assertFalse(contactTypes.isEmpty());

        // agregar datos de contacto
        ContactDataEntity cd = new ContactDataEntity();
        cd.setAssociate(a);
        cd.setContactType(contactTypes.get(0));
        cd.setValueData("12345678");

        associateAdministration.createContactData(cd);
        assertEquals(cd, associateAdministration.findAllContactDataByAssociate(a.getId()).get(0));

        // borrar socio
        associateAdministration.deleteAssociate(a.getId());

        // verificar que se ha borrado su contrato
        ContractEntity deletedContrat = contractAdministration.findById(savedContract.getId());
        assertNull(deletedContrat);

        // verificar que no hay datos de contacto
        assertTrue(associateAdministration.findAllContactDataByAssociate(a.getId()).isEmpty());
    }

    @Test(expected = BusinessException.class)
    public void createAssociate1Test() {
        associateAdministration.createAssociate(null);
    }

    @Test(expected = BusinessException.class)
    public void createAssociate2Test() {
        AssociateEntity ae = new AssociateEntity();
        ae.setId(12); // no se debe colocar id
        associateAdministration.createAssociate(ae);
    }

    @Test(expected = BusinessException.class)
    public void createAssociate3Test() {
        AssociateEntity ae = new AssociateEntity();
        ae.setContract(null); // no debe ser nulo!
        associateAdministration.createAssociate(ae);
    }

    @Test(expected = BusinessException.class)
    public void createAssociate4Test() {
        AssociateEntity ae = new AssociateEntity();
        ae.setContract(new ContractEntity());
        ae.setNationality(null); // no debe ser nulo!
        associateAdministration.createAssociate(ae);
    }

    @Test(expected = BusinessException.class)
    public void updateAssociate1Test() {
        associateAdministration.updateAssociate(null);
    }

    @Test(expected = BusinessException.class)
    public void updateAssociate2Test() {
        AssociateEntity ae = new AssociateEntity();
        ae.setId(null); // no debe ser nulo!
        associateAdministration.updateAssociate(ae);
    }

    @Test(expected = BusinessException.class)
    public void updateAssociate3Test() {
        AssociateEntity a = new AssociateEntity();
        a.setId(1);
        a.setContract(null); // no debe ser nulo!
        associateAdministration.updateAssociate(null);
    }

    @Test(expected = BusinessException.class)
    public void updateAssociate4Test() {
        AssociateEntity a = new AssociateEntity();
        a.setId(1);
        a.setContract(new ContractEntity());
        a.setNationality(null);// no debe ser nulo!
        associateAdministration.updateAssociate(null);
    }

    @Test(expected = BusinessException.class)
    public void deleteAssociate1() {
        associateAdministration.deleteAssociate(-2); // valido pero inexistente
    }

    @Test(expected = BusinessException.class)
    public void createContactData1Test() {
        associateAdministration.createContactData(null);
    }

    @Test(expected = BusinessException.class)
    public void createContactData2Test() {
        associateAdministration.createContactData(null);
    }

    @Test(expected = BusinessException.class)
    public void createContactData3Test() {
        ContactDataEntity cde = new ContactDataEntity();
        cde.setId(1); // malo!
        associateAdministration.createContactData(cde);
    }

    @Test(expected = BusinessException.class)
    public void createContactData4Test() {
        ContactDataEntity cde = new ContactDataEntity();
        cde.setId(null);
        cde.setValueData(null); // malo
        associateAdministration.createContactData(cde);
    }

    @Test(expected = BusinessException.class)
    public void createContactData5Test() {
        ContactDataEntity cde = new ContactDataEntity();
        cde.setId(null);
        cde.setValueData("foo");
        cde.setAssociate(null);
        associateAdministration.createContactData(cde);
    }

    @Test(expected = BusinessException.class)
    public void createContactData6Test() {
        ContactDataEntity cde = new ContactDataEntity();
        cde.setId(null);
        cde.setValueData("foo");
        cde.setAssociate(new AssociateEntity());
        cde.setContactType(null); // malo
        associateAdministration.createContactData(cde);
    }
}
