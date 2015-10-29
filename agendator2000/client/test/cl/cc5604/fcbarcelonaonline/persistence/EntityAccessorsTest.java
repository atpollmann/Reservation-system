package cl.cc5604.fcbarcelonaonline.persistence;

import cl.cc5604.fcbarcelonaonline.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 03-05-13
 * Time: 08:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityAccessorsTest {

    private ActiveEntity active;
    private ActiveTypeEntity activeType;
    private AssociateEntity associate;
    private ContactDataEntity contactData;
    private ContactTypeEntity contactType;
    private ContractEntity contract;
    private NationalityEntity nationality;
    private PassiveEntity passive;
    private PassiveStatusEntity passiveStatus;
    private StaffEntity staff;
    private StaffTypeEntity staffType;
    private UserEntity user;

    @Before
    public void setUp() {
        active = new ActiveEntity();
        activeType = new ActiveTypeEntity();
        associate = new AssociateEntity();
        contactData = new ContactDataEntity();
        contactType = new ContactTypeEntity();
        contract = new ContractEntity();
        nationality = new NationalityEntity();
        passive = new PassiveEntity();
        passiveStatus = new PassiveStatusEntity();
        staff = new StaffEntity();
        staffType = new StaffTypeEntity();
        user = new UserEntity();
    }

    @Test
    public void activeEntityTest() {
        assertNotNull(active);
        assertNotNull(activeType);

        // id
        assertNull(active.getId());
        Integer id = 9;
        active.setId(id);
        assertEquals(id, active.getId());

        // value
        assertEquals(0, active.getValue());
        active.setValue(7);
        assertEquals(7, active.getValue());

        // activeType
        assertNull(active.getActiveType());
        active.setActiveType(activeType);
        assertEquals(active.getActiveType(), activeType);
    }

    @Test
    public void activeTypeEntityTest() {
        assertNotNull(activeType);

        // id
        assertNull(activeType.getId());
        Integer id = 4;
        activeType.setId(id);
        assertEquals(id, activeType.getId());

        // description
        assertNull(activeType.getDescription());
        activeType.setDescription("foo");
        assertEquals(activeType.getDescription(), "foo");

        // type
        assertNull(activeType.getType());
        activeType.setType("bar");
        assertEquals(activeType.getType(), "bar");
    }

    @Test
    public void associateEntityTest() {
        assertNotNull(associate);
        assertNotNull(contract);
        assertNotNull(nationality);

        // id
        assertNull(associate.getId());
        Integer id = 8;
        associate.setId(id);
        assertEquals(id, associate.getId());

        // seatRight
        assertFalse(associate.getSeatRight());
        associate.setSeatRight(true);
        assertTrue(associate.getSeatRight());

        // contract
        assertNull(associate.getContract());
        associate.setContract(contract);
        assertEquals(associate.getContract(), contract);

        // nationality
        assertNull(associate.getNationality());
        associate.setNationality(nationality);
        assertEquals(associate.getNationality(), nationality);

        // PersonEntity

        // lastName
        assertNull(associate.getLastName());
        associate.setLastName("foo");
        assertEquals(associate.getLastName(), "foo");

        // birthDate
        Timestamp bd = new Timestamp(new Date().getTime());
        assertNull(associate.getBirthDate());
        associate.setBirthDate(bd);
        assertEquals(associate.getBirthDate(), bd);

        // firstName
        assertNull(associate.getFirstName());
        associate.setFirstName("bar");
        assertEquals(associate.getFirstName(), "bar");
    }

    @Test
    public void contactDataEntityTest() {
        assertNotNull(contactData);
        assertNotNull(associate);
        assertNotNull(contactType);

        // id
        assertNull(contactData.getId());
        Integer id = 7;
        contactData.setId(id);
        assertEquals(id, contactData.getId());

        // valueData
        assertNull(contactData.getValueData());
        contactData.setValueData("foo");
        assertEquals(contactData.getValueData(), "foo");

        // associate
        assertNull(contactData.getAssociate());
        contactData.setAssociate(associate);
        assertEquals(contactData.getAssociate(), associate);

        // contactType
        assertNull(contactData.getContactType());
        contactData.setContactType(contactType);
        assertEquals(contactData.getContactType(), contactType);
    }

    @Test
    public void contactTypeTest() {
        assertNotNull(contactType);

        // id
        assertNull(contactType.getId());
        Integer id = 7;
        contactType.setId(id);
        assertEquals(id, contactType.getId());

        // description
        assertNull(contactType.getDescription());
        contactType.setDescription("foo");
        assertEquals(contactType.getDescription(), "foo");

        // type
        assertNull(contactType.getType());
        contactType.setType("bar");
        assertEquals(contactType.getType(), "bar");
    }

    @Test
    public void contractEntityTest() {
        assertNotNull(contract);

        // id
        assertNull(contract.getId());
        Integer id = 8;
        contract.setId(id);
        assertEquals(id, contract.getId());

        Timestamp init = new Timestamp(new Date().getTime());
        Timestamp end = new Timestamp(new Date().getTime());

        // expirationDate
        assertNull(contract.getExpirationDate());
        contract.setExpirationDate(end);
        assertEquals(contract.getExpirationDate(), end);

        // initDate
        assertNull(contract.getInitDate());
        contract.setInitDate(init);
        assertEquals(contract.getInitDate(), init);

        // monthlyPayment
        assertEquals(contract.getMonthlyPayment(), 0);
        contract.setMonthlyPayment(100);
        assertEquals(contract.getMonthlyPayment(), 100);
    }

    @Test
    public void nationalityEntityTest() {
        assertNotNull(nationality);

        // id
        assertNull(nationality.getId());
        Integer id = 19;
        nationality.setId(id);
        assertEquals(nationality.getId(), id);

        // country
        assertNull(nationality.getCountry());
        nationality.setCountry("Chile");
        assertEquals(nationality.getCountry(), "Chile");
    }

    @Test
    public void passiveEntityTest() {
        assertNotNull(passive);
        assertNotNull(passiveStatus);

        // id
        assertNull(passive.getId());
        Integer id = 7;
        passive.setId(id);
        assertEquals(id, passive.getId());

        // value
        assertEquals(passive.getValue(), 0);
        passive.setValue(56);
        assertEquals(56, passive.getValue());

        // passiveStatus
        assertNull(passive.getPassiveStatus());
        passive.setPassiveStatus(passiveStatus);
        assertEquals(passiveStatus, passive.getPassiveStatus());
    }

    @Test
    public void passiveStatusEntityTest() {
        assertNotNull(passiveStatus);

        // id
        assertNull(passiveStatus.getId());
        Integer id = 8;
        passiveStatus.setId(id);
        assertEquals(id, passiveStatus.getId());

        // description
        assertNull(passiveStatus.getDescription());
        passiveStatus.setDescription("foo");
        assertEquals("foo", passiveStatus.getDescription());

        // status
        assertNull(passiveStatus.getStatus());
        passiveStatus.setStatus("bar");
        assertEquals("bar", passiveStatus.getStatus());
    }

    @Test
    public void staffEntityTest() {
        assertNotNull(staff);
        assertNotNull(contract);
        assertNotNull(nationality);
        assertNotNull(staffType);

        // id
        assertNull(staff.getId());
        Integer id = 8;
        staff.setId(id);
        assertEquals(id, staff.getId());

        // hired
        assertFalse(staff.getHired());
        staff.setHired(true);
        assertTrue(staff.getHired());

        // baseValue
        assertNull(staff.getBaseValue());
        staff.setBaseValue(452);
        assertEquals(new Integer(452), staff.getBaseValue());

        // contract
        assertNull(staff.getContract());
        staff.setContract(contract);
        assertEquals(contract, staff.getContract());

        // nationality
        assertNull(staff.getNationality());
        staff.setNationality(nationality);
        assertEquals(nationality, staff.getNationality());

        // PersonEntity
        assertNull(staff.getStaffType());
        staff.setStaffType(staffType);
        assertEquals(staffType, staff.getStaffType());

        // lastName
        assertNull(staff.getLastName());
        staff.setLastName("foo");
        assertEquals("foo", staff.getLastName());

        // birthDate
        Timestamp bd = new Timestamp(new Date().getTime());
        assertNull(staff.getBirthDate());
        staff.setBirthDate(bd);
        assertEquals(bd, staff.getBirthDate());

        // firstName
        assertNull(staff.getFirstName());
        staff.setFirstName("bar");
        assertEquals("bar", staff.getFirstName());

    }

    @Test
    public void staffTypeEntityTest() {
        assertNotNull(staffType);

        // id
        assertNull(staffType.getId());
        Integer id = 9;
        staffType.setId(id);
        assertEquals(id, staffType.getId());

        // description
        assertNull(staffType.getDescription());
        staffType.setDescription("foo");
        assertEquals("foo", staffType.getDescription());

        // type
        assertNull(staffType.getType());
        staffType.setType("bar");
        assertEquals("bar", staffType.getType());
    }

    @Test
    public void userEntityTest() {

        assertNotNull(user);

        // id
        assertNull(user.getId());
        Integer id = 8;
        user.setId(id);
        assertEquals(id, user.getId());

        // password
        assertNull(user.getPassword());
        user.setPassword("foo");
        assertEquals("foo", user.getPassword());

        // username
        assertNull(user.getUsername());
        user.setUsername("bar");
        assertEquals("bar", user.getUsername());
    }

}
