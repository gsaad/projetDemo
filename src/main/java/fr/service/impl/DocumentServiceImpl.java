package fr.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.model.S3Object;

import fr.persistence.dao.DocumentDao;
import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.service.AmazonS3Bucket;
import fr.service.BusinessServiceException;
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
	public void addDocument(DocumentForm docForm) throws BusinessServiceException {
		Document document = new Document();
		String nomFile = initialiserDocument(docForm, document);
		amazonS3Bucket.saveFileInbucket( docForm.getFileData(), nomFile);
		documentDao.addDocument(document);
	}

	private String initialiserDocument(DocumentForm docForm, Document document) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		document.setDateAjout(df.format(new Date()));
		TypeDocument tp = new TypeDocument();
		tp.setIdTypeDocument(docForm.getIdTypeDocument().intValue());
		document.setTypeDocument(tp);
		document.setIntitule(docForm.getIntituleDocument());
		java.util.Date date= new java.util.Date();
		String nomFile = date.getTime() + "_" + docForm.getFileData().getOriginalFilename();

		document.setUser(docForm.getUser());
		document.setMois(docForm.getMois());
		document.setAnnee(docForm.getAnnee());
		document.setNomFichier(nomFile);
		return nomFile;
	}

	@Override
	public S3Object loadDocument(Integer idDocument, String login)
			throws BusinessServiceException {
		Document document = this.getDocument(idDocument, login);
		if (document != null) {
			return amazonS3Bucket.loadFileInbucket(document.getNomFichier());
		} 
		throw new BusinessServiceException("Le document n'existe pas");
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
