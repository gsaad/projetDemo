package fr.persistence.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
@Repository 
public interface DocumentDao {
	
	public void addDocument(Document doc);
	
	public void updateDocument(Document doc);
	
	public void removeDocument(Document doc);

	public Document getDocument(Integer idDocument, String login);
	
	public List<Document> findListDocumentByLogin(String login);
	
	public List<TypeDocument> findAllTypeDocument();
	
}
