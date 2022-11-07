package ERP.ERP_Ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ERP.ERP_Ecommerce.Entity.Produits;
import ERP.ERP_Ecommerce.Repository.ProduitsRepository;


@Service
@Transactional
public class ProduitsService {

	@Autowired
	ProduitsRepository prdService;
	public void SaveProduit(Produits prd)
	{
		prdService.save(prd);
	}
	
		public Produits VerifExistReference(String reference) {
			// Il faut ajouter dans l'entité "Clients" un constructeur vide 
			//public Clients() {}
			Produits resultat= prdService.findByReference(reference);
			
			return resultat;
		}
		
		
		
	// getAllProduits
	
		public List<Produits> getAllProduits(){
			List<Produits> ListProduits=(List<Produits>) prdService.findAll();
			return ListProduits;
		}
		
		/*// Recherche Employe
		
			public List<Employee> SearchEmploye(String nom,String role){
				List<Employee> employes=(List<Employee>) emprep.Recherche(nom,role);
				return employes;
			}*/
}
