package fr.persistence.dao;

import java.util.List;

import org.hibernate.HibernateException;

import fr.persistence.domain.Document;
import fr.persistence.domain.User;

public interface UserDao {
	
	public List<User> findAllUsers() throws HibernateException ;

	public void createUser(User user) throws HibernateException ;

	public List<User> findAllUserByLogin(String login) throws HibernateException ;
	
	public User findUserByLogin(String login) throws HibernateException ;
	
	public void updateUser(User user) throws HibernateException ;
	
}