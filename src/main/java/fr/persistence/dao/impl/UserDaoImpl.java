package fr.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<User> findAllUsers() {
		List<User> list = sessionFactory.getCurrentSession()
				.createCriteria(User.class).list();
		
		return list;
	}

	public void createUser(User user) {
		user.setCreationDate(new Date());;
		user.setEnabled(true);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = getRoleByCode(RolesEnum.ROLE_USER.name());
		user.getRoles().add(userRole);
		user.setLogin(user.getLogin().toLowerCase());
		sessionFactory.getCurrentSession().persist("User",
				user);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUserByLogin(String login) {
		List<User> list = sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.like("login",login+"%")).list();
		return list;
	}
	
	public User findUserByLogin(String login) {
		User user = (User) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.like("login",login)).uniqueResult();
		return user;
	}

	public void updateUser(User user)  {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		sessionFactory.getCurrentSession().update(user);
	}
	
	public Role getRoleByCode(String code){
		Role role = (Role) sessionFactory.getCurrentSession()
				.createCriteria(Role.class)
				.add(Restrictions.like("role",code)).uniqueResult();
		return role;
	}
}