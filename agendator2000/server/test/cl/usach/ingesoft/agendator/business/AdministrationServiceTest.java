package cl.usach.ingesoft.agendator.business;

import cl.usach.ingesoft.agendator.business.service.impl.AdministrationService;
import cl.usach.ingesoft.agendator.util.BusinessException;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.business.service.IUserAdministrationService;
import cl.usach.ingesoft.agendator.business.dao.IUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class AdministrationServiceTest {

    // Mock DAO as dependency.
    @Mock private IUserDAO mockUserDAO;

    // To retrieve arguments of delegated calls for UserAdministrationService.
    private ArgumentCaptor<UserEntity> userCaptor;

    // Interface for UserAdministrationService.
    private IUserAdministrationService userAdministration;

    /**
     * Helper method for building a simple UserEntity.
     *
     * @param id Id for the UserEntity.
     * @param username Username for the UserEntity.
     * @param password Password for the UserEntity.
     * @return a newly-built non-nul UserEntity.
     */
    private UserEntity makeUser(Integer id, String username, String password) {
        UserEntity ue = new UserEntity();
        ue.setId(id);
        ue.setEmail(username);
        ue.setHashedPassword(password);
        return ue;
    }

    @Before
    public void setUp() {
        // Inject mocks for this object.
        MockitoAnnotations.initMocks(this);
        userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        // Creates an isolated instance, with mocked dependencies.
        userAdministration = new AdministrationService(mockUserDAO);

        assertNotNull(mockUserDAO);
        assertNotNull(userCaptor);
        assertNotNull(userAdministration);
    }

    @Test(expected = BusinessException.class)
    public void testCreateUser_bad1() {
        // Username null should raise an exception.
        userAdministration.createUser(null, "aPassword");
    }

    @Test(expected = BusinessException.class)
    public void testCreateUser_bad2() {
        // Password null should raise an exception.
        userAdministration.createUser("aUsername", null);
    }

    @Test
    public void testCreateUser_ok() {
        // Happy path.
        UserEntity user = userAdministration.createUser("aUsername", "aPassword");

        verify(mockUserDAO).findByEmail(eq("aUsername"));
        verify(mockUserDAO).save(userCaptor.capture());
        verify(mockUserDAO).flush();

        assertNotNull(user);
        assertEquals(user, userCaptor.getValue());

        assertEquals("aUsername", user.getEmail());
        assertEquals("aPassword", user.getHashedPassword());

        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    public void testFindAllUsers() {
        // Happy path.
        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(makeUser(1, "1", "11"));
        users.add(makeUser(2, "a", "aa"));
        when(mockUserDAO.findAll()).thenReturn(users);

        List<UserEntity> users2 = userAdministration.findAllUsers();

        verify(mockUserDAO).findAll();

        assertEquals(2, users2.size());
        assertEquals("1", users2.get(0).getEmail());
        assertEquals("11", users2.get(0).getHashedPassword());

        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    public void testFindById_null() {
        when(mockUserDAO.findById(38)).thenReturn(null);

        UserEntity user = userAdministration.findById(38);

        verify(mockUserDAO).findById(eq(38));

        assertNull(user);

        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    public void testFindById() {
        when(mockUserDAO.findById(17)).thenReturn(makeUser(17, "a", "aa"));

        UserEntity user = userAdministration.findById(17);

        verify(mockUserDAO).findById(eq(17));

        assertNotNull(user);
        assertEquals(17, (int)user.getId());
        assertEquals("a", user.getEmail());
        assertEquals("aa", user.getHashedPassword());

        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test(expected = BusinessException.class)
    public void testFindByUsername_bad1() {
        userAdministration.findByEmail(null);
    }

    @Test(expected = BusinessException.class)
    public void testFindByUsername_bad2() {
        userAdministration.findByEmail("");
    }

    @Test
    public void testFindByUsername() {
        when(mockUserDAO.findByEmail(eq("fish"))).thenReturn(makeUser(17, "fish", "1234"));

        UserEntity user = userAdministration.findByEmail("fish");

        verify(mockUserDAO).findByEmail(eq("fish"));

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getEmail());
        assertNotNull(user.getHashedPassword());

        assertEquals(17, (int)user.getId());
        assertEquals("fish", user.getEmail());
        assertEquals("1234", user.getHashedPassword());

        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test(expected = BusinessException.class)
    public void testDeleteUser_bad() {
        // delete a non-existent user id
        userAdministration.deleteUser(20);
    }

    @Test
    public void testDeleteUser_ok() {
        when(mockUserDAO.findById(eq(20))).thenReturn(makeUser(20, "a", "aa"));

        userAdministration.deleteUser(20);

        verify(mockUserDAO).delete(userCaptor.capture());

        assertNotNull(userCaptor.getValue());
        assertEquals(20, (int)userCaptor.getValue().getId());
    }

    @Test(expected = BusinessException.class)
    public void testUpdateUser_bad1() {
        // Null username is not allowed.
        userAdministration.updateUser(18, null, "aPassword");
    }

    @Test(expected = BusinessException.class)
    public void testUpdateUser_bad2() {
        // Null password is not allowed.
        userAdministration.updateUser(18, "aUsername", null);
    }

    @Test(expected = BusinessException.class)
    public void testUpdateUser_bad3() {
        when(mockUserDAO.findById(39)).thenReturn(null);

        // Non existent BD user should raise an exception.
        userAdministration.updateUser(39, "aUsername", "aPassword");
    }

    @Test
    public void testUpdateUser() {
        UserEntity aUser = makeUser(17, "foo", "bar");

        when(mockUserDAO.findById(17)).thenReturn(aUser);

        UserEntity user = userAdministration.updateUser(17, "fuu", "baz");

        verify(mockUserDAO).findById(eq(17));
        verify(mockUserDAO).update(userCaptor.capture());
        verify(mockUserDAO).flush();

        assertNotNull(user);
        assertEquals(17, (int)user.getId());
        assertEquals("fuu", user.getEmail());
        assertEquals("baz", user.getHashedPassword());

        verifyNoMoreInteractions(mockUserDAO);
    }

    @Test
    public void testDeleteAllUsers() {
        userAdministration.deleteAllUsers();
        verify(mockUserDAO).deleteAll();
        verify(mockUserDAO).flush();

        verifyNoMoreInteractions(mockUserDAO);
    }
}
