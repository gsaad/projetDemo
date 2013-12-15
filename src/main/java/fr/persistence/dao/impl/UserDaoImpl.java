package fr.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.persistence.dao.UserDao;
import fr.persistence.domain.Role;
import fr.persistence.domain.RolesEnum;
import fr.persistence.domain.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() throws HibernateException {
		List<User> list = sessionFactory.getCurrentSession()
				.createCriteria(User.class).list();
		
		return list;
	}

	public void createUser(User user) throws HibernateException {
		user.setCreationDate(new Date());;
		user.setEnabled(true);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = new Role();
        userRole.setRole(RolesEnum.ROLE_USER.name());
		user.getRoles().add(userRole);
		sessionFactory.getCurrentSession().persist("User",
				user);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUserByLogin(String login) throws HibernateException {
		List<User> list = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.like("login",login+"%")).list();
		return list;
	}
	
	public User findUserByLogin(String login) throws HibernateException {
		User user = (User) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.like("login",login)).uniqueResult();
		return user;
	}

	public void updateUser(User user) throws HibernateException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		sessionFactory.getCurrentSession().update(user);
	}
}