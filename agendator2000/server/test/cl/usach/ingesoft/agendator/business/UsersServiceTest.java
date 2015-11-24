package cl.usach.ingesoft.agendator.business;

import cl.usach.ingesoft.agendator.business.dao.IAdministratorDao;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.util.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class UsersServiceTest {

    // Mock DAO as dependency.
    @Mock private IAdministratorDao mockUserDAO;

    // To retrieve arguments of delegated calls for UserAdministrationService.
    private ArgumentCaptor<UserEntity> userCaptor;

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

        assertNotNull(mockUserDAO);
        assertNotNull(userCaptor);
    }

    @Test(expected = BusinessException.class)
    public void testCreateUser_bad1() {
        // Username null should raise an exception.

    }

    @Test(expected = BusinessException.class)
    public void testCreateUser_bad2() {
        // Password null should raise an exception.

    }

}
