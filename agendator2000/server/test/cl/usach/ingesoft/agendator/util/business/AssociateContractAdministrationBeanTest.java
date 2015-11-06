package cl.usach.ingesoft.agendator.util.business;

import cl.usach.ingesoft.agendator.facade.IAssociateAdministration;
import cl.usach.ingesoft.agendator.facade.IContractAdministration;
import cl.usach.ingesoft.agendator.util.dao.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AssociateContractAdministrationBeanTest {

    @Mock private IContractDAO mockContractDAO;
    @Mock private IAssociateDAO mockAssociateDAO;
    @Mock private INationalityDAO mockNationalityDAO;
    @Mock private IContactDataDAO mockContactDataDAO;
    @Mock private IContactTypeDAO mockContactTypeDAO;

    private IAssociateAdministration associateAdministration;
    private IContractAdministration contractAdministration;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        associateAdministration = new AssociateAdministrationService(mockContractDAO, mockAssociateDAO,
                mockNationalityDAO, mockContactDataDAO, mockContactTypeDAO);
        contractAdministration = new ContractAdministrationService(mockContractDAO, mockAssociateDAO,
                mockContactDataDAO);

        assertNotNull(mockContractDAO);
        assertNotNull(mockAssociateDAO);
        assertNotNull(mockNationalityDAO);
        assertNotNull(mockContactDataDAO);
        assertNotNull(mockContactTypeDAO);
        assertNotNull(associateAdministration);
        assertNotNull(contractAdministration);
    }

    @Test
    public void foo() {
        assertTrue(true);
    }
}
