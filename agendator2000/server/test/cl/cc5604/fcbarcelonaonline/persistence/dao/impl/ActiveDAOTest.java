package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.ActiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.ActiveTypeEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveDAO;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveTypeDAO;
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
public class ActiveDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IActiveDAO dao;
    @Autowired
    private IActiveTypeDAO activeTypeDAO;

    @Test
    public void testCrud() {

        // encontrar los tipos de activos
        List<ActiveTypeEntity> types = activeTypeDAO.findAll();
        assertFalse(types.isEmpty());

        // encontrar 0 activos
        List<ActiveEntity> actives = dao.findAll();
        assertTrue(actives.isEmpty());

        // guardar 1 activo
        ActiveEntity a1 = new ActiveEntity();
        a1.setValue(2500);
        a1.setActiveType(types.get(0));

        dao.save(a1);
        dao.flush();

        assertEquals(1, dao.findAll().size());

        ActiveEntity a2 = dao.findOneByStatement("from ActiveEntity"); // solo hay 1!
        assertEquals(a1, a2);
        assertEquals(a1.getActiveType(), a2.getActiveType());
    }
}
