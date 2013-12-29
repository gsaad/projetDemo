package fr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.persistence.dao.UserDao;
import fr.persistence.domain.User;
import fr.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Transactional(readOnly = true)
    public long getNumberOfUsers() {
    	return userDao.findAllUsers().size();
    }
    
    @Transactional(readOnly = true)
    public User findUser(String login) {
        User user = userDao.findUserByLogin(login);
        return user;
    }
  
    @Transactional(readOnly = true)
    public List<User> findUsersByLogin(String loginStart) {
        List<User> users = userDao.findAllUserByLogin(loginStart);
        return users;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth == null) {
            return null;
        } else {
	         Object userObject = auth.getPrincipal();
	         if(userObject!=null && userObject instanceof org.springframework.security.core.userdetails.User){
	        	return this.findUser(((org.springframework.security.core.userdetails.User) userObject).getUsername());	 
	         }else{
	        	return null;
	         }
        }
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

	public void createUser(User user){
		userDao.createUser(user);
	}
}
