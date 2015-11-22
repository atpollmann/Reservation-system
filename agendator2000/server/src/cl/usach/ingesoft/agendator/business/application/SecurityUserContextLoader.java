package cl.usach.ingesoft.agendator.business.application;

import cl.usach.ingesoft.agendator.business.application.captcha.CaptchaSingleton;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.facade.IUserAdministration;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service(value = "securityUserDetailsService")
public class SecurityUserContextLoader implements UserDetailsService {

    @Autowired
    private IUserAdministration userAdministration;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        // retrieve error in captcha mechanism
        ThreadLocal<String> threadLocal = CaptchaSingleton.getInstance().getThreadLocal();
        String errorMsg = threadLocal.get();
        if (errorMsg != null) {
            //throw new BadCredentialsException(errorMsg);
        }

        // user credential mechanism
        UserEntity userEntity = userAdministration.findByUsername(username);
        if (userEntity == null) {
            throw new BadCredentialsException("Acceso denegado");
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true, true, new LinkedList<GrantedAuthority>());
    }
}
