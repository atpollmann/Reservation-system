package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.common.BusinessException;
import cl.cc5604.fcbarcelonaonline.facade.IUserAdministration;
import cl.cc5604.fcbarcelonaonline.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 04-05-13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = false)
public class UserAdministrationBeanTest {

    @Autowired IUserAdministration userAdministration;

    @Test
    public void crudTest() {
        assertTrue(userAdministration.findAllUsers().isEmpty());

        UserEntity user = new UserEntity();
        user.setUsername("root");
        user.setPassword("admin");

        userAdministration.createUser(user);

        // verificar que se ha asignado id
        assertNotNull(user.getId());

        UserEntity another = userAdministration.findById(user.getId());

        UserEntity other = userAdministration.findByUsername("root");

        // user == another == other
        assertEquals(user, another);
        assertEquals(user, other);

        // borrar user
        int userId = user.getId();
        userAdministration.deleteUser(userId);

        // deben haber 0 usuarios
        assertTrue(userAdministration.findAllUsers().isEmpty());
        assertNull(userAdministration.findById(userId));


        UserEntity mine = new UserEntity();
        mine.setUsername("administrator");
        mine.setPassword("dummy");
        userAdministration.createUser(mine);
        int mineId = mine.getId();

        // modify
        mine.setPassword("sheep");

        // copy, modified
        UserEntity backup = new UserEntity();
        backup.setId(mineId);
        backup.setUsername(mine.getUsername());
        backup.setPassword(mine.getPassword());

        userAdministration.updateUser(mine);

        assertEquals(backup, userAdministration.findById(mineId));

    }

    @Test(expected = BusinessException.class)
    public void createUser1Test() {
        UserEntity user = new UserEntity();
        user.setId(2); // malo
        user.setUsername("foo");
        user.setPassword("bar");
        userAdministration.createUser(user);
    }

    @Test(expected = BusinessException.class)
    public void createUser2Test() {
        UserEntity user = new UserEntity();
        user.setId(null);
        user.setUsername("");// malo
        user.setPassword("bar");
        userAdministration.createUser(user);
    }

    @Test(expected = BusinessException.class)
    public void createUser3Test() {
        UserEntity user = new UserEntity();
        user.setId(null);
        user.setUsername(null); // malo
        user.setPassword("bar");
        userAdministration.createUser(user);
    }

    @Test(expected = BusinessException.class)
    public void createUser4Test() {
        UserEntity user = new UserEntity();
        user.setId(null);
        user.setUsername("foo");
        user.setPassword(null);// malo
        userAdministration.createUser(user);
    }

    @Test(expected = BusinessException.class)
    public void createUser5Test() {
        UserEntity user = new UserEntity();
        user.setId(null);
        user.setUsername("foo");
        user.setPassword("");// malo
        userAdministration.createUser(user);
    }

    @Test(expected = BusinessException.class)
    public void createUser6Test() {
        userAdministration.createUser(null); // mucho mas malo
    }

    @Test(expected = BusinessException.class)
    public void findByUsername1Test() {
        userAdministration.findByUsername(null); // malo
    }

    @Test(expected = BusinessException.class)
    public void findByUsername2Test() {
        userAdministration.findByUsername(""); // malo
    }

    @Test(expected = BusinessException.class)
    public void deleteUser1Test() {
        userAdministration.deleteUser(-2); // id valido pero inexistente
    }

    @Test(expected = BusinessException.class)
    public void update1Test() {
        userAdministration.updateUser(null); // malo
    }

    @Test(expected = BusinessException.class)
    public void update2Test() {
        UserEntity user = new UserEntity();
        user.setId(null); // malo
        user.setUsername("foo");
        user.setPassword("bar");
        userAdministration.updateUser(user);
    }

    @Test(expected = BusinessException.class)
    public void update3Test() {
        UserEntity user = new UserEntity();
        user.setId(2);
        user.setUsername("");// malo
        user.setPassword("bar");
        userAdministration.updateUser(user);
    }

    @Test(expected = BusinessException.class)
    public void update4Test() {
        UserEntity user = new UserEntity();
        user.setId(2);
        user.setUsername(null);// malo
        user.setPassword("bar");
        userAdministration.updateUser(user);
    }

    @Test(expected = BusinessException.class)
    public void update5Test() {
        UserEntity user = new UserEntity();
        user.setId(2);
        user.setUsername("foo");
        user.setPassword("");// malo
        userAdministration.updateUser(user);
    }

    @Test(expected = BusinessException.class)
    public void update6Test() {
        UserEntity user = new UserEntity();
        user.setId(2);
        user.setUsername("foo");
        user.setPassword(null);// malo
        userAdministration.updateUser(user);
    }

}
