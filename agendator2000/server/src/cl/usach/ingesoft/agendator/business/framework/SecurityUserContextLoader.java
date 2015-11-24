package cl.usach.ingesoft.agendator.business.framework;

import cl.usach.ingesoft.agendator.captcha.CaptchaSingleton;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

//import cl.usach.ingesoft.agendator.business.service.IUserAdministrationService;

@Service(value = "securityUserDetailsService")
public class SecurityUserContextLoader implements UserDetailsService {

    //@Autowired private IAdministrationService userAdministration;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
        // retrieve error in captcha mechanism
        ThreadLocal<String> threadLocal = CaptchaSingleton.getInstance().getThreadLocal();
        String errorMsg = threadLocal.get();
        if (errorMsg != null) {
            //throw new BadCredentialsException(errorMsg);
        }

        // user credential mechanism
        UserEntity userEntity = new UserEntity();//userAdministration.findByEmail(email);
        userEntity.setId(100);
        userEntity.setEmail("foo");
        userEntity.setHashedPassword("bar");
        userEntity.setRun(177);
        userEntity.setFirstName("rene");
        userEntity.setLastName("tapia");

        if (userEntity == null) {
            throw new BadCredentialsException("Acceso denegado");
        }

        return new User(userEntity.getEmail(), userEntity.getHashedPassword(), true, true, true, true,
                new LinkedList<GrantedAuthority>());
    }
}
