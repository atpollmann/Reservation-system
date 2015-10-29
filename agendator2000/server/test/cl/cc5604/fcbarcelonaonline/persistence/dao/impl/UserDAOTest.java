package cl.cc5604.fcbarcelonaonline.persistence.dao.impl;

import cl.cc5604.fcbarcelonaonline.entity.UserEntity;
import cl.cc5604.fcbarcelonaonline.persistence.dao.IUserDAO;
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
public class UserDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private IUserDAO dao;

    private UserEntity makeUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    @Test
    public void crudTest() {
        // asegurar que la BD esta vacia
        List<UserEntity> users0 = dao.findAll();
        assertEquals(0, users0.size());

        // insertar 3 usuarios
        dao.save(makeUser("admin", "admin"));
        dao.save(makeUser("donkey", "kong"));
        dao.save(makeUser("root", "12345"));
        dao.flush();

        // recuperar los ultimos 3
        List<UserEntity> users3 = dao.findAll();
        assertEquals(3, users3.size());

        // insertar otro
        dao.save(makeUser("foo", "bar"));
        dao.flush();

        // recuperar exactamente 4
        List<UserEntity> users4 = dao.findAll();
        assertEquals(4, users4.size());

        // recuperar admin
        UserEntity admin = dao.findById(users4.get(3).getId());
        assertEquals(users4.get(3), admin);

        // ----------

        // verificar que siguen habiendo 4 usuarios!
        users4 = dao.findAll();
        assertEquals(4, users4.size());

        // cambiar y hacer update
        admin.setPassword("clown");
        dao.update(admin);
        dao.flush();

        // comprobar
        admin = dao.findById(users4.get(3).getId());
        assertEquals("clown", admin.getPassword());

        // ----------

        // borrar admin
        dao.delete(admin);
        dao.flush();

        // ahora deben haber exactamente 3 usuarios (todos menos admin)
        users3 = dao.findAll();
        assertEquals(3, users3.size());

        for (UserEntity user : users4) {
            assertNotNull(user);
        }

        // insertar mediante saveOrUpdate (NO SE PUEDE setear el id en una entidad que tiene id generado por secuencia)
        /*
        admin = makeUser(5, "admin", "admin2");
        dao.saveOrUpdate(session, admin);
        session.flush();
        */
        admin = makeUser("adminx", "admin2"); // cambiado test, dado que se cambio una constraint en la BD
        dao.save(admin);
        dao.flush();


        // chequear que hay 4 usuarios
        users4 = dao.findAll();
        assertEquals(4, users4.size());

        // recuperar admin
        admin = dao.findAll().get(0);

        // verificar que son los mismos
        assertEquals(admin, users4.get(0));

        // -----------------

        // guardar mas usuarios nuevamente
        dao.save(makeUser("admin1", "pass1"));
        dao.save(makeUser("admin2", "pass2"));
        dao.save(makeUser("admin3", "pass3"));
        dao.save(makeUser("admin4", "pass4"));
        // admin tiene id 5
        dao.save(makeUser("admin6", "pass6"));
        dao.save(makeUser("admin7", "pass7"));
        dao.save(makeUser("admin8", "pass8"));
        dao.flush();

        for (UserEntity u : dao.findAll()) {
            assertNotNull(u);
        }

        // buscar admin4 (findByUsername)
        UserEntity admin4 = dao.findByUsername("admin4");
        assertEquals("admin4", admin4.getUsername());
        assertEquals("pass4", admin4.getPassword());

        // buscar usuarios via query
        List<UserEntity> users2 = dao.findByStatement("from UserEntity u where u.username=:username1 or u.username=:username2",
                "username1", "admin1",
                "username2", "admin2"
        );
        assertEquals(2, users2.size());

        // buscar admin6 via query
        UserEntity admin6 = dao.findOneByStatement("from UserEntity u where u.username=:username and u.password=:password",
                "username", "admin6",
                "password", "pass6"
        );
        assertEquals("admin6", admin6.getUsername());
        assertEquals("pass6", admin6.getPassword());
    }

}
