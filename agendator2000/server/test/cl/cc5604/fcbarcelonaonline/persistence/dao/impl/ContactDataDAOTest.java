package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.AssociateEntity;
import cl.cc5604.fcbarcelonaonline.entity.ContactDataEntity;
import cl.cc5604.fcbarcelonaonline.entity.ContactTypeEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IAssociateDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IContactDataDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IContactTypeDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

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
public class ContactDataDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IContactDataDAO dao;
    @Autowired
    private IContactTypeDAO contactTypeDAO;
    @Autowired
    private IAssociateDAO associateDAO;

    @Test
    public void testCrud() {
        // tipos
        List<ContactTypeEntity> types = contactTypeDAO.findAll();
        assertFalse(types.isEmpty());

        // socios
        List<AssociateEntity> associates = associateDAO.findAll();
        assertFalse(associates.isEmpty());

        assertTrue(dao.findAll().isEmpty());

        ContactDataEntity cde = new ContactDataEntity();
        cde.setValueData("foobar");
        cde.setAssociate(associates.get(0));
        cde.setContactType(types.get(0));

        dao.save(cde);
//        dao.flush();

        assertEquals(1, dao.findAll().size());

        ContactDataEntity aData = dao.findOneByStatement("from ContactDataEntity");
        assertEquals(cde, aData);
        assertEquals(cde.getContactType(), aData.getContactType());
        assertEquals(cde.getAssociate(), aData.getAssociate());

        int idAssociate = aData.getAssociate().getId();

        List<ContactDataEntity> contactData = dao.findContactDataByAssociate(idAssociate);
        assertEquals(1, contactData.size());

        // borrar dato de contacto
        dao.delete(cde);
//        dao.flush();

        // verificar que sigan las mismas listas para ContactTypeEntity y AssociateEntity

        // tipos
        List<ContactTypeEntity> typesAfter = contactTypeDAO.findAll();
        assertEquals(types.size(), typesAfter.size());

        // socios
        List<AssociateEntity> associatesAfter = associateDAO.findAll();
        assertEquals(associates.size(), associatesAfter.size());
    }

}
