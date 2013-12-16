package fr.persistence.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import fr.persistence.domain.Role;
import fr.persistence.domain.User;
@Repository
public interface UserDao {
	
	public List<User> findAllUsers() throws HibernateException ;

	public void createUser(User user) throws HibernateException ;

	public List<User> findAllUserByLogin(String login) throws HibernateException ;
	
	public User findUserByLogin(String login) throws HibernateException ;
	
	public void updateUser(User user) throws HibernateException ;
	
	public Role getRoleByCode(String code);
	
}