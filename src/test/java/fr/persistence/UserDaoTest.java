package fr.persistence;

import static org.junit.Assert.*;

import org.dbunit.dataset.DataSetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.persistence.dao.UserDao;
import fr.persistence.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@WebAppConfiguration
public class UserDaoTest extends AbstractUnitTest {

	@Autowired
	private UserDao userDao;
	

	@Test
	public void verification_chargement_donnees_table() throws DataSetException {
		 assertEquals(2,  dataset.getTable("role").getRowCount());
		 assertEquals(5, dataset.getTable("tuser").getRowCount());
	}
	
	@Test
	public void find_All_user(){
		for(User user : userDao.findAllUsers()){
			System.out.println(user);
		}
		assertEquals(5, userDao.findAllUsers().size());
	}
	
	@Test
	public void find_user_by_login_inexistant(){
		assertNull(userDao.findUserByLogin("inexistant"));
	}
	
	@Test
	public void find_user_by_login_ok(){
		assertEquals("lastname_login1", userDao.findUserByLogin("test_login1").getLastName());
		assertEquals(2, userDao.findUserByLogin("test_login1").getRoles().size());
	}
	
	@Test
	public void find_all_user_by_login_inexistant(){
		assertEquals(0, userDao.findAllUserByLogin("inexistant").size());
	}
	
	@Test
	public void find_all_user_by_login_ok(){
		assertEquals(5, userDao.findAllUserByLogin("test").size());
	}

	@Test
	public void create_user() throws DataSetException {
		User user = new User();
		user.setLogin("login");
		user.setFirstName("titi");
		user.setLastName("toto@titi.fr");
		user.setPassword("test");
		userDao.createUser(user);
		assertEquals(6, userDao.findAllUsers().size());
	}
	
	@Test
	public void update_user_verification_liste_documents(){
		User user = userDao.findUserByLogin("test_login1");
		assertEquals("firstname_login1", user.getFirstName());
		user.setFirstName("testmodif");
		userDao.updateUser(user);
		User user1 = userDao.findUserByLogin("test_login1");
		assertEquals("testmodif", user1.getFirstName());
	}
}