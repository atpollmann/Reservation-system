package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IStaffTypeDAO;
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
public class StaffTypeDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IStaffTypeDAO dao;

    private StaffTypeEntity makeStaffType(int id, String description, String type) {
        StaffTypeEntity staffType = new StaffTypeEntity();
        staffType.setId(id);
        staffType.setDescription(description);
        staffType.setType(type);
        return staffType;
    }

    @Test
    public void testCrud() {
        List<StaffTypeEntity> staffTypes0 = dao.findAll();
        assertTrue(staffTypes0.isEmpty());

        dao.save(makeStaffType(1, "lleva agua", "aguatero"));
        dao.save(makeStaffType(2, "guarda metas", "arquero"));
        dao.save(makeStaffType(3, "mete goles", "delantero"));
        dao.save(makeStaffType(4, "rehabilita traumas", "kinesiologo"));
//        dao.flush();

        List<StaffTypeEntity> staffTypes4 = dao.findAll();
        assertEquals(4, staffTypes4.size());

        StaffTypeEntity kine = dao.findById(4);
        assertEquals("kinesiologo", kine.getType());

        dao.delete(kine);
//        dao.flush();

        List<StaffTypeEntity> staffTypes3 = dao.findAll();
        assertEquals(3, staffTypes3.size());

        // TODO: revisar este metodo y copiar pruebas de NationalityDAOTest
    }

}
