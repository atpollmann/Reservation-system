package cl.usach.ingesoft.agendator.entity;

import cl.usach.ingesoft.agendator.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class EntityAccessorsTest {

    private AssociateEntity associate;
    private ContactDataEntity contactData;
    private ContactTypeEntity contactType;
    private ContractEntity contract;
    private NationalityEntity nationality;
    private UserEntity user;

    @Before
    public void setUp() {
        associate = new AssociateEntity();
        contactData = new ContactDataEntity();
        contactType = new ContactTypeEntity();
        contract = new ContractEntity();
        nationality = new NationalityEntity();
        user = new UserEntity();
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
