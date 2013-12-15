package fr.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.persistence.dao.DocumentDao;
import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.service.AmazonS3Bucket;
import fr.service.DocumentService;
import fr.web.form.DocumentForm;

@Transactional
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentDao documentDao;
	
	@Autowired
	AmazonS3Bucket amazonS3Bucket;

	@Override
	public void addDocument(DocumentForm docForm) throws IOException {
		Document document = new Document();
		document.setDateAjout(new Date().toLocaleString());
		TypeDocument tp = new TypeDocument();
		tp.setIdTypeDocument(docForm.getIdTypeDocument().intValue());
		document.setTypeDocument(tp);
		document.setIntitule(docForm.getIntituleDocument());
		java.util.Date date= new java.util.Date();
		String nomFile = date.getTime() + "_" + docForm.getFileData().getOriginalFilename();

		document.setUser(docForm.getUser());
		document.setMois(12);
		document.setAnnee(2000);
		document.setNomFichier(nomFile);
		amazonS3Bucket.saveFileInbucket( docForm.getFileData(), nomFile);
		documentDao.addDocument(document);
	}

	@Override
	public void updateDocument(Document doc) {
		documentDao.updateDocument(doc);
	}

	@Override
	public void removeDocument(Document doc) {
		documentDao.removeDocument(doc);

	}
	@Override
	public List<Document> findListDocumentByLogin(String login) {
		return documentDao.findListDocumentByLogin(login);
	}

	@Override
	public Document getDocument(Integer idDocument, String login) {
		return documentDao.getDocument(idDocument, login);
	}

	@Override
	public List<TypeDocument> findAllTypeDocument() {
		return documentDao.findAllTypeDocument(); 
	}
}
