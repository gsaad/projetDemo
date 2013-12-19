package fr.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.amazonaws.services.s3.model.S3Object;

import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.web.form.DocumentForm;

public interface DocumentService {
	
	/**
	 *  ajouter un nouveau document
	 * @param docFrom
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws BusinessServiceException 
	 */
	void addDocument(DocumentForm docFrom) throws BusinessServiceException ;
	
	/**
	 * maj des informations d'un document
	 * @param doc
	 */
	void updateDocument(Document doc);
	
	/**
	 * supprimer un doc
	 * @param doc
	 */
	void removeDocument(Document doc);
	
	/**
	 * recuperer un document d'un utilisateur 
	 * @param idDocument : id du document
	 * @param login : login de l'utilisateur connecté
	 * @return document
	 */
	Document getDocument(Integer idDocument, String login);
	
	/** 
	 * recuperer la liste des documents par utilisateurs
	 * @param login
	 * @return liste des documents
	 */
	List<Document> findListDocumentByLogin(String login);

	/**
	 * 
	 * @return
	 */
	public List<TypeDocument> findAllTypeDocument();

	S3Object loadDocument(Integer idDocument, String login) throws BusinessServiceException;
	
}
