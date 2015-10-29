package cl.cc5604.fcbarcelonaonline.business;

import cl.cc5604.fcbarcelonaonline.common.BusinessException;
import cl.cc5604.fcbarcelonaonline.facade.IFinancesReport;
import cl.cc5604.fcbarcelonaonline.facade.IPassiveAdministration;
import cl.cc5604.fcbarcelonaonline.entity.PassiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

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
public class PassiveAdministrationBeanTest {

    @Autowired
    IPassiveAdministration passiveAdministration;
    @Autowired
    IFinancesReport financesReport;

    @Test
    public void crudTest() {
        assertEquals(0, financesReport.getTotalPassives());
        assertEquals(350000, financesReport.getTotalNet());

        List<PassiveStatusEntity> status = passiveAdministration.findAllPassiveStatus();
        assertFalse(status.isEmpty());

        PassiveStatusEntity pendiente = passiveAdministration.findPassiveStatusByStatus("Pendiente");
        PassiveStatusEntity pagado = passiveAdministration.findPassiveStatusByStatus("Pagado");

        // crear pasivo pendiente
        PassiveEntity p = new PassiveEntity();
        p.setPassiveStatus(pendiente);
        p.setValue(45000);

        passiveAdministration.createPassive(p);
        assertNotNull(p.getId());

        // el neto se recalcula
        assertEquals(45000, financesReport.getTotalPassives());
        assertEquals(350000 - 45000, financesReport.getTotalNet());

        // los pasivos pagados no afectan el neto
        PassiveEntity q = new PassiveEntity();
        q.setPassiveStatus(pagado);
        p.setValue(10000);
        passiveAdministration.createPassive(q);
        assertNotNull(q.getId());

        // deben mantenerse los valores
        assertEquals(45000, financesReport.getTotalPassives());
        assertEquals(350000 - 45000, financesReport.getTotalNet());

        int theId = p.getId();

        // modificar pasivo
        p.setValue(2000);

        // respaldar
        PassiveEntity backup = new PassiveEntity();
        backup.setId(p.getId());
        backup.setValue(p.getValue());
        backup.setPassiveStatus(p.getPassiveStatus());

        // persistir y recuperar nuevamente
        passiveAdministration.updatePassive(p);

        assertEquals(backup, passiveAdministration.findById(theId));
    }

    @Test(expected = BusinessException.class)
    public void create1Test() {
        PassiveEntity p = new PassiveEntity();
        p.setId(2); // malo
        p.setValue(2);
        p.setPassiveStatus(new PassiveStatusEntity());
        passiveAdministration.createPassive(p);
    }

    @Test(expected = BusinessException.class)
    public void create2Test() {
        PassiveEntity p = new PassiveEntity();
        p.setId(null);
        p.setValue(-2); // malo
        p.setPassiveStatus(new PassiveStatusEntity());
        passiveAdministration.createPassive(p);
    }

    @Test(expected = BusinessException.class)
    public void create3Test() {
        PassiveEntity p = new PassiveEntity();
        p.setId(null);
        p.setValue(2);
        p.setPassiveStatus(null); // malo
        passiveAdministration.createPassive(p);
    }

    @Test(expected = BusinessException.class)
    public void update1Test() {
        PassiveEntity p = new PassiveEntity();
        p.setId(null); // malo
        p.setValue(2);
        p.setPassiveStatus(new PassiveStatusEntity());
        passiveAdministration.updatePassive(p);
    }

    @Test(expected = BusinessException.class)
    public void update2Test() {
        PassiveEntity p = new PassiveEntity();
        p.setId(2);
        p.setValue(-2); // malo
        p.setPassiveStatus(new PassiveStatusEntity());
        passiveAdministration.updatePassive(p);
    }

    @Test(expected = BusinessException.class)
    public void update3Test() {
        PassiveEntity p = new PassiveEntity();
        p.setId(2);
        p.setValue(2);
        p.setPassiveStatus(null); // malo
        passiveAdministration.updatePassive(p);
    }

    @Test(expected = BusinessException.class)
    public void deleteTest() {
        passiveAdministration.deletePassive(-2); // id valido, pero inexistente
    }
}
