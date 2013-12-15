package fr.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fr.persistence.dao.DocumentDao;
import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.persistence.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class DocumentDaoTest extends AbstractUnitTest {

	@Autowired
	private DocumentDao documentDao;
	
	@Test
	public void findlistdocumentbyuserok(){
		assertEquals(5, documentDao.findListDocumentByLogin("test_login1").size());
	}
	
	@Test
	public void findlistdocumentbyuserko(){
		assertEquals(0, documentDao.findListDocumentByLogin("test_ko").size());
	}
	
	@Test
	public void  addDocument(){
		Document document = new Document();
		document.setIntitule("intitule");
		document.setNomFichier("nom fichier");
		TypeDocument  tp = new TypeDocument();
		tp.setIdTypeDocument(1);
		document.setTypeDocument(tp);
		
		User user = new User();
		user.setLogin("test_login1");
		document.setUser(user);
		document.setMois(1);
		document.setAnnee(2000);
		documentDao.addDocument(document);
		Document doc = documentDao.getDocument(document.getPk(), "test_login1");
		assertEquals(doc, document);
	
	}
	
	@Test
	public void updateDocument(){
		Document doc = documentDao.getDocument(1, "test_login1");
		assertEquals("passeport 1", doc.getIntitule());
		doc.setIntitule("modif");
		documentDao.updateDocument(doc);
		doc = documentDao.getDocument(1, "test_login1");
		assertEquals("modif", doc.getIntitule());
	}
	
	@Test
	public void removeDocument(){
		Document doc = documentDao.getDocument(1, "test_login1");
		assertEquals("passeport 1", doc.getIntitule());
		documentDao.removeDocument(doc);
		doc = documentDao.getDocument(1, "test_login1");
		assertNull(doc);
	}

	@Test
	public void getDocument(){
		Document doc = documentDao.getDocument(1, "test_login1");
		assertNotNull(doc);
		assertEquals("passeport 1", doc.getIntitule());
	}
}
