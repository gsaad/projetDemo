package fr.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

import com.amazonaws.services.identitymanagement.model.Role;

import fr.persistence.domain.Document;
import fr.persistence.domain.RolesEnum;
import fr.persistence.domain.TypeDocument;
import fr.persistence.domain.User;

public class EntiteTest  {

	
	@Test
	public void testDocumentEntite(){
		Document document1 = new Document();
		document1.setPk(1);
		document1.setAnnee(2013);
		document1.setMois(1);
		document1.setDateAjout("2013-01-01");
		document1.setIntitule("Nouveau document");
		TypeDocument tp2 = new TypeDocument();
		tp2.setIdTypeDocument(1);
		tp2.setLibelleTypeDocument(" autres");
		document1.setTypeDocument(tp2);
		document1.setNomFichier("fichier 1");
		assertEquals("janvier", document1.getLibelleMois());
		document1.setMois(0);
		assertEquals("", document1.getLibelleMois());
		
		Document document2 = new Document();
		document2.setPk(1);
		document2.setAnnee(2014);
		document2.setMois(12);
		document2.setDateAjout("2013-01-02");
		document2.setIntitule("Nouveau document 1");
		TypeDocument tp1 = new TypeDocument();
		tp1.setIdTypeDocument(1);
		tp1.setLibelleTypeDocument(" autres");
		document2.setTypeDocument(tp1);
		document2.setNomFichier("fichier 2.txt");
		assertEquals("décembre", document2.getLibelleMois());
		
		assertTrue(document1.equals(document2));
		assertFalse(document1.equals(new User()));
	}
	
	@Test
	public void testRoleEntite(){
		fr.persistence.domain.Role role1 = new 	fr.persistence.domain.Role();
		role1.setPk(1);
		role1.setRole(RolesEnum.ROLE_ADMIN.name());
		
		fr.persistence.domain	.Role role2 = new 	fr.persistence.domain.Role();
		role2.setPk(2);
		role2.setRole(RolesEnum.ROLE_ADMIN.name());
		assertTrue(role1.equals(role2));
		
		role2.setRole(RolesEnum.ROLE_USER.name());
		assertFalse(role1.equals(role2));
		
		assertFalse(role1.equals(null));
		assertFalse(role1.equals(new User()));
	}
	
	@Test
	public void testUserEntite(){
		fr.persistence.domain.Role role1 = new 	fr.persistence.domain.Role();
		role1.setPk(1);
		role1.setRole(RolesEnum.ROLE_USER.name());
		
		User user1 = new User();
		user1.setLogin("USER");
		user1.setEmail("email");
		user1.setFirstName("firstname");
		user1.setLastName("lastName");
		user1.setPassword("password");
		user1.getRoles().add(role1);
		
		User user2 = new User();
		user2.setLogin("user");
		user2.setEmail("email");
		user2.setFirstName("firstname");
		user2.setLastName("lastName");
		user2.setPassword("password");
		
		assertTrue(user1.equals(user2));
		assertFalse(user1.equals(null));
		assertFalse(user1.equals(new Role()));
	}
}