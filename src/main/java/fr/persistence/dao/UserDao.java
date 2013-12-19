package fr.persistence.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.persistence.domain.Role;
import fr.persistence.domain.User;
@Repository
public interface UserDao {
	
	public List<User> findAllUsers() ;

	public void createUser(User user) ;

	public List<User> findAllUserByLogin(String login) ;
	
	public User findUserByLogin(String login) ;
	
	public void updateUser(User user) ;
	
	public Role getRoleByCode(String code);
	
}