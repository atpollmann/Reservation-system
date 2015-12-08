package cl.usach.ingesoft.agendator.business.framework;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.captcha.CaptchaSingleton;
import cl.usach.ingesoft.agendator.entity.UserEntity;
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

import java.util.ArrayList;
import java.util.LinkedList;

@Service(value = "securityUserDetailsService")
public class SecurityUserContextLoader implements UserDetailsService {

    @Autowired private IUsersService usersService;

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
        UserEntity userEntity = usersService.findUserByEmail(email);

        if (userEntity == null) {
            throw new BadCredentialsException("Acceso denegado");
        }

        return new User(userEntity.getEmail(), userEntity.getHashedPassword(), true, true, true, true,
                userEntity.getAuthoritiesByUserType());
    }
}
