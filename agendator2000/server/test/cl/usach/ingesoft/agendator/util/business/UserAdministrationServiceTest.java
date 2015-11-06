package cl.usach.ingesoft.agendator.util.business;

import cl.usach.ingesoft.agendator.facade.IUserAdministration;
import cl.usach.ingesoft.agendator.util.dao.IUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserAdministrationServiceTest {

    @Mock private IUserDAO mockUserDAO;

    private IUserAdministration userAdministration;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userAdministration = new UserAdministrationService(mockUserDAO);

        assertNotNull(mockUserDAO);
        assertNotNull(userAdministration);
    }

    @Test
    public void foo() {
        assertEquals(1, 1);
    }

}
