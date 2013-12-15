package fr.persistence.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TYPE_DOCUMENT")
public class TypeDocument implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 783081996646597759L;

	@Column(name = "ID_TYPE_DOCUMENT", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idTypeDocument;
		
	@Column(name = "LIBELLE_TYPE_DOCUMENT", nullable = false)
	private String libelleTypeDocument;
	
	@Column
	@OneToMany(mappedBy = "typeDocument")
	private Set<Document> listDocuments = new HashSet<Document>();


	public String getLibelleTypeDocument() {
		return libelleTypeDocument;
	}

	public void setLibelleTypeDocument(String libelleTypeDocument) {
		this.libelleTypeDocument = libelleTypeDocument;
	}

	public Integer getIdTypeDocument() {
		return idTypeDocument;
	}

	public void setIdTypeDocument(Integer idTypeDocument) {
		this.idTypeDocument = idTypeDocument;
	}

	public Set<Document> getListDocuments() {
		return listDocuments;
	}

	public void setListDocuments(Set<Document> listDocuments) {
		this.listDocuments = listDocuments;
	}
}