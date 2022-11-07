package ERP.ERP_Ecommerce.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProduit;
	@Column(length = 10)
	private String nom;
	String description,photo;
	private double prix;
	private int qteStock;
	@Column(unique=true,nullable = false)
	private String reference;
	public Produits() {}
	public Produits(String nom, String description, String photo, double prix, int qteStock, String reference) {
		super();
		this.nom = nom;
		this.description = description;
		this.photo = photo;
		this.prix = prix;
		this.qteStock = qteStock;
		this.reference = reference;
	}
	
	
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	public int getIdProduit() {
		return idProduit;
	}
	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQteStock() {
		return qteStock;
	}
	public void setQteStock(int qteStock) {
		this.qteStock = qteStock;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	

}
