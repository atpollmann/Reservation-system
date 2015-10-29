package cl.cc5604.fcbarcelonaonline;

import cl.cc5604.fcbarcelonaonline.business.*;
import cl.cc5604.fcbarcelonaonline.business.validator.ValidatorTest;
import cl.cc5604.fcbarcelonaonline.persistence.dao.impl.*;
import cl.cc5604.fcbarcelonaonline.persistence.BaseEntityTest;
import cl.cc5604.fcbarcelonaonline.persistence.EntityAccessorsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 06-05-13
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SuiteTest.DynamicSuite.class)
public class SuiteTest {

    public static class DynamicSuite extends Suite {
        public DynamicSuite(Class<?> setupClass) throws InitializationError {
            super(setupClass, SuiteTest.classes());
        }
    }

    public static Class<?>[] classes() {
        return new Class<?>[]{

                BaseEntityTest.class,
                EntityAccessorsTest.class,
                ValidatorTest.class,

                NationalityDAOTest.class,
                UserDAOTest.class,
                ContractDAOTest.class,
                AssociateDAOTest.class,
                StaffTypeDAOTest.class,
                StaffDAOTest.class,
                ActiveTypeDAOTest.class,
                ActiveDAOTest.class,
                PassiveStatusDAOTest.class,
                PassiveDAOTest.class,
                ContactTypeDAOTest.class,
                ContactDataDAOTest.class,

                SetupAdministrationTest.class,

                ActiveAdministrationBeanTest.class,
                PassiveAdministrationBeanTest.class,
                ContractAdministrationBeanTest.class,
                UserAdministrationBeanTest.class,
                AssociateContractAdministrationBeanTest.class,
                StaffContractAdministrationBeanTest.class
        };
    }
}


