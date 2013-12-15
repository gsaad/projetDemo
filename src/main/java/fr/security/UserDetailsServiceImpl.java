package fr.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.persistence.domain.Role;
import fr.persistence.domain.User;
import fr.service.UserService;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException, DataAccessException {

        login = login.toLowerCase();
        User user;
            user = userService.findUser(login);
            if(user==null){
            	throw new UsernameNotFoundException("User '" + login
                        + "' could not be found.");	
            }
        user.setLastAccessDate(Calendar.getInstance().getTime());

        Set<Role> roles = user.getRoles();

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new GrantedAuthorityImpl(role.getRole()));
        }

        return new org.springframework.security.core.userdetails.User(login,
                user.getPassword(), user.isEnabled(), true, true, true,
                authorities);
    }
}