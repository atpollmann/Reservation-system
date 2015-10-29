package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.ContactTypeEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IContactTypeDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class ContactTypeDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IContactTypeDAO dao;

    @Test
    public void testCrud() {
        assertTrue(dao.findAll().isEmpty());

        ContactTypeEntity c1 = new ContactTypeEntity();
        c1.setId(1);
        c1.setType("email");
        c1.setDescription("correo electronico");

        ContactTypeEntity c2 = new ContactTypeEntity();
        c2.setId(2);
        c2.setType("telefono");
        c2.setDescription("numero de celular");

        dao.save(c1);
        dao.save(c2);
//        dao.flush();

        List<ContactTypeEntity> contacTypes = dao.findByStatement("from ContactTypeEntity c order by c.id");
        assertEquals(2, contacTypes.size());
        assertEquals(c1, contacTypes.get(0));
        assertEquals(c2, contacTypes.get(1));

        dao.delete(c1);
//        dao.flush();

        assertEquals(1, dao.findAll().size());
    }

}
