package fr.persistence.domain;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DOCUMENT")
public class Document implements Serializable{
	

	private static final long serialVersionUID = 516333672031779172L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pk;
	    

	@Column
	private String intitule;
	
	@Column
	private String dateAjout;
	
	@Column
	private int mois;
	
	@Transient
	private String libelleMois;
	
	@Column
	private int annee;
	
	@Column
	private String nomFichier;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "login")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_DOCUMENT")
	private TypeDocument typeDocument;
	
	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getDateAjout() {
		return dateAjout;
	}

	public void setDateAjout(String dateAjout) {
		this.dateAjout = dateAjout;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getLibelleMois() {
		DateFormatSymbols dfsFR = new DateFormatSymbols(Locale.FRENCH);
		if(mois != 0){
			return dfsFR.getMonths()[this.getMois()-1];	
		}else{
			return "";
		}
	}

	public void setLibelleMois(String libelleMois) {
		this.libelleMois = libelleMois;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
}