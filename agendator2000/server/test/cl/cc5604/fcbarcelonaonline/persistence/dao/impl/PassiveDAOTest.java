package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.PassiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IPassiveDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IPassiveStatusDAO;
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
public class PassiveDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IPassiveDAO dao;
    @Autowired
    private IPassiveStatusDAO passiveStatusDAO;

    @Test
    public void testCrud() {
        List<PassiveStatusEntity> passiveStatus = passiveStatusDAO.findAll();
        assertFalse(passiveStatus.isEmpty());

        assertTrue(dao.findAll().isEmpty());

        PassiveEntity pe = new PassiveEntity();
        pe.setValue(25300);
        pe.setPassiveStatus(passiveStatus.get(0));

        dao.save(pe);
//        dao.flush();

        List<PassiveEntity> passives = dao.findAll();
        assertEquals(1, passives.size());

        PassiveEntity pe2 = dao.findOneByStatement("from PassiveEntity"); // retorna 1!

        assertEquals(pe, passives.get(0));
        assertEquals(pe, pe2);
    }

}
