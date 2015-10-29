package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IPassiveStatusDAO;
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
public class PassiveStatusDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IPassiveStatusDAO dao;

    @Test
    public void testCrud() {
        assertTrue(dao.findAll().isEmpty());

        PassiveStatusEntity pse = new PassiveStatusEntity();
        pse.setId(1);
        pse.setStatus("foo");
        pse.setDescription("foo description");

        dao.save(pse);
//        dao.flush();

        List<PassiveStatusEntity> passiveStatus = dao.findAll();
        assertEquals(1, passiveStatus.size());
        assertEquals(pse, passiveStatus.get(0));
    }

}
