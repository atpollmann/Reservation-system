package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.ActiveTypeEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IActiveTypeDAO;
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
public class ActiveTypeDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IActiveTypeDAO dao;

    private ActiveTypeEntity makeType(int id, String type, String description) {
        ActiveTypeEntity ate = new ActiveTypeEntity();
        ate.setId(id);
        ate.setType(type);
        ate.setDescription(description);
        return ate;
    }

    @Test
    public void testCrud() {
        List<ActiveTypeEntity> activeTypes = dao.findAll();
        assertTrue(activeTypes.isEmpty());

        ActiveTypeEntity type1 = makeType(1, "bien raiz", "activo - bien raiz");

        dao.save(type1);
//        dao.flush();

        ActiveTypeEntity type2 = dao.findById(1);
        assertEquals(type1, type2);

        dao.delete(type2);
//        dao.flush();

        assertTrue(dao.findAll().isEmpty());

        ActiveTypeEntity typeA = makeType(2, "foo", "bar");
        ActiveTypeEntity typeB = makeType(3, "foo2", "bar2");
        dao.save(typeA);
        dao.save(typeB);
//        dao.flush();

        List<ActiveTypeEntity> ls = dao.findAll();
        assertEquals(2, ls.size());
    }

}
