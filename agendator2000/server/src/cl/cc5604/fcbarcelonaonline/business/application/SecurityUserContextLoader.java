package cl.cc5604.fcbarcelonaonline.business.application;

import cl.cc5604.fcbarcelonaonline.business.application.captcha.CaptchaSingleton;
import cl.cc5604.fcbarcelonaonline.entity.UserEntity;
import cl.cc5604.fcbarcelonaonline.facade.IUserAdministration;
import org.apache.log4j.Logger;
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

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 24-06-13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "securityUserDetailsService")
public class SecurityUserContextLoader implements UserDetailsService {

    private static Logger logger = Logger.getLogger(SecurityUserContextLoader.class);

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
