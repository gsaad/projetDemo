package fr.web.form;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import fr.persistence.domain.User;


public class DocumentForm implements Serializable {
	private String name;
	private CommonsMultipartFile fileData;
	private String intituleDocument;
	private Long idTypeDocument;
	private String nomFichier;
	private Long mois;
	private Long annee;
	private User User;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public String getIntituleDocument() {
		return intituleDocument;
	}
	public void setIntituleDocument(String intituleDocument) {
		this.intituleDocument = intituleDocument;
	}
	public Long getIdTypeDocument() {
		return idTypeDocument;
	}
	public void setIdTypeDocument(Long idTypeDocument) {
		this.idTypeDocument = idTypeDocument;
	}
	public String getNomFichier() {
		return nomFichier;
	}
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	public Long getMois() {
		return mois;
	}
	public void setMois(Long mois) {
		this.mois = mois;
	}
	public Long getAnnee() {
		return annee;
	}
	public void setAnnee(Long annee) {
		this.annee = annee;
	}
	public User getUser() {
		return User;
	}
	public void setUser(User user) {
		User = user;
	}
	

	
}
