package fr.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.persistence.dao.DocumentDao;
import fr.persistence.domain.Document;
import fr.persistence.domain.TypeDocument;
import fr.persistence.domain.User;
@Repository
public class DocumentDaoImpl implements DocumentDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addDocument(Document doc) {
		sessionFactory.getCurrentSession().persist("Document", doc);
	}

	@Override
	public void updateDocument(Document doc) {
		sessionFactory.getCurrentSession().update(doc);
	}

	@Override
	public void removeDocument(Document doc) {
		sessionFactory.getCurrentSession().delete(doc);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Document> findListDocumentByLogin(String login){
		Criteria critera = sessionFactory.getCurrentSession()
				.createCriteria(Document.class)
				.createAlias("user", "userAlias")
				.add(Restrictions.like("userAlias.login", login + ""));
		return critera.list();
	}

	@Override
	public Document getDocument(Integer idDocument, String login) {
		Document doc = (Document) sessionFactory.getCurrentSession()
				.createCriteria(Document.class)
				.add(Restrictions.eq("pk",idDocument))
				.add(Restrictions.eq("user.login",login))
				.uniqueResult();
		return doc;
	}

	@Override
	public List<TypeDocument> findAllTypeDocument() {
		Criteria critera = sessionFactory.getCurrentSession()
				.createCriteria(TypeDocument.class);
		return critera.list();
	}
}
