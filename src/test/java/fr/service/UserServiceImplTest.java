package fr.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import fr.persistence.dao.UserDao;
import fr.persistence.domain.User;
import fr.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/applicationContext-test.xml" })

public class UserServiceImplTest {
		
		private UserService userService ; 
		private UserDao userDao ;
		@Before
		public void setUp() throws Exception {	
			userService = new UserServiceImpl();
			userDao = EasyMock.createMock(UserDao.class);
			ReflectionTestUtils.setField(userService, "userDao", userDao);
			reset(userDao);
		}
		
		@Test
		public void testRecupererNbredutilisateurs()throws Exception{
			User user = new User();
			user.setLogin("login");
			user.setFirstName("firstname");
			user.setLastName("lastname");
			user.setEmail("email");
			List<User> listeUser = new ArrayList<User>();
			listeUser.add(user);
			expect(userDao.findAllUsers()).andReturn(listeUser);
			replay(userDao);			

			long nbreUser = userService.getNumberOfUsers();
			assertEquals(1, nbreUser);
			verify(userDao);
		}
		
		@Test
		public void testFindAllUsersbyloginaucun_user(){
			expect(userDao.findAllUserByLogin("test")).andReturn(new ArrayList<User>());
			replay(userDao);
			
			List<User>  listeUserTest= userService.findUsersByLogin("test");
			assertEquals(0, listeUserTest.size());
			verify(userDao);
		}
		

		@Test
		public void testFindAllUsersByloginplusieurs_user(){
			List<User> listeUser = new ArrayList<User>();
			User user = new User();
			user.setLogin("login1_3");
			user.setFirstName("firstname");
			user.setLastName("lastname");
			user.setEmail("email");
			listeUser.add(user);
			
			User user1 = new User();
			user1.setLogin("login1_4");
			user1.setFirstName("firstname");
			user1.setLastName("lastname");
			user1.setEmail("email");
			listeUser.add(user1);
			
			expect(userDao.findAllUserByLogin("login1")).andReturn(listeUser);
			
			replay(userDao);
			List<User> listUserLogin1 = userService.findUsersByLogin("login1");
			assertEquals(2, listUserLogin1.size());
			
			verify(userDao);
		}
		
		@Test
		public void testfindUserByLogin(){
			User user = new User();
			user.setLogin("login1");
			user.setFirstName("firstname");
			user.setLastName("lastname");
			user.setEmail("email");

			expect(userDao.findUserByLogin("login1")).andReturn(user);
			
			replay(userDao);
			User userLogin1 = userService.findUser("login1");
			assertEquals("firstname", userLogin1.getFirstName());
			
			verify(userDao);
		}
		
		@Test
		public void updateUser(){
			userDao.updateUser(new User());
			EasyMock.expectLastCall().once();
			replay(userDao);
			userService.updateUser(new User());
			verify(userDao);
		}
		
		@Test
		public void createUser(){
			userDao.createUser(((User)EasyMock.anyObject()));
			EasyMock.expectLastCall().once();
			replay(userDao);
			userService.createUser(new User());
			verify(userDao);
		}
}