package fr.web.form;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fr.persistence.domain.User;


public class DocumentForm {
	private String name;
	private MultipartFile fileData;
	private String intituleDocument;
	private Long idTypeDocument;
	private String nomFichier;
	private String periode;
	private User User;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(MultipartFile fileData) {
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
	public Integer getMois() {
		if(StringUtils.isEmpty(periode)){
			return null;
		}else{
			return Integer.valueOf(periode.split("/")[0]);
		}
	}
	public Integer getAnnee() {
		if(StringUtils.isEmpty(periode)){
			return null;
		}else{
			return Integer.valueOf(periode.split("/")[1]);
		}
	}
	public User getUser() {
		return User;
	}
	public void setUser(User user) {
		User = user;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
}
