package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.INationalityDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class NationalityDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private INationalityDAO dao;

    private NationalityEntity makeCountry(int id, String country) {
        NationalityEntity nationality = new NationalityEntity();
        nationality.setId(id);
        nationality.setCountry(country);
        return nationality;
    }

    @Test
    public void crudTest() {
        // asegurar que la BD esta vacia
        List<NationalityEntity> nationalities0 = dao.findAll();
        for(NationalityEntity ne : nationalities0){
            System.out.println(ne);
        }
        assertEquals(0, nationalities0.size());

        // insertar 3 paises (save)
        dao.save(makeCountry(1, "Chile"));
        dao.save(makeCountry(2, "Argentina"));
        dao.save(makeCountry(3, "Brasil"));
        dao.flush();

        // recuperar los ultimos 3
        List<NationalityEntity> nationalities3 = dao.findAll();
        assertEquals(3, nationalities3.size());

        // insertar otro
        dao.save(makeCountry(4, "Peru"));
        dao.flush();

        // recuperar exactamente 4
        List<NationalityEntity> nationalities4 = dao.findAll();
        assertEquals(4, nationalities4.size());

        // recuperar Chile
        NationalityEntity chile = dao.findById(1);
        assertNotNull(chile);
        assertEquals(new Integer(1), chile.getId());
        assertEquals("Chile", chile.getCountry());

        // ----------

        // cambiar y hacer update
        chile.setCountry("Chile");
        dao.update(chile);
        dao.flush();

        // comprobar
        chile = dao.findById(1);
        assertNotNull(chile);
        assertEquals(new Integer(1), chile.getId());
        assertEquals("Chile", chile.getCountry());

        // ----------

        // borrar chile
        dao.delete(chile);
        dao.flush();

        // ahora deben haber exactamente 3 paises (todos menos chile)
        nationalities3 = dao.findAll();
        assertEquals(3, nationalities3.size());

        // borrar todos
        for (NationalityEntity n : dao.findAll()) {
            assertNotNull(n);
//            System.out.println("DELETING " + n);
            dao.delete(n);
            dao.flush();
        }

        // -----------------

        // guardar mas paises nuevamente
        dao.save(makeCountry(1, "Peru"));
        dao.save(makeCountry(2, "Bolivia"));
        dao.save(makeCountry(3, "Argentina"));
        dao.save(makeCountry(4, "Venezuela"));
        // chile tiene id 5
        dao.save(makeCountry(6, "Colombia"));
        dao.save(makeCountry(7, "Uruguay"));
        dao.save(makeCountry(8, "Paraguay"));
        dao.flush();

        // buscar venezuela (findByName)
        NationalityEntity venezuela = dao.findByName("Venezuela");
        assertNotNull(venezuela);
        assertEquals(new Integer(4), venezuela.getId());
        assertEquals("Venezuela", venezuela.getCountry());

        // buscar paises via query
        List<NationalityEntity> nationalities2 = dao.findByStatement("from NationalityEntity n where n.id <= :id",
                "id", 2
        );
        assertEquals(2, nationalities2.size());

        // buscar colombia via query
        NationalityEntity colombia = dao.findOneByStatement("from NationalityEntity n where n.country = :country",
                "country", "Colombia"
        );
        assertNotNull(colombia);
        assertEquals(new Integer(6), colombia.getId());
        assertEquals("Colombia", colombia.getCountry());


        System.out.println("dao.findAll().size():" + dao.findAll().size());
    }

}
