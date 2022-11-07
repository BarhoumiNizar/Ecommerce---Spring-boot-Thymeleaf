package ERP.ERP_Ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ERP.ERP_Ecommerce.Entity.Clients;
import ERP.ERP_Ecommerce.Repository.ClientsRepository;


@Service
@Transactional
public class ClientsService {

	@Autowired(required=false)
	ClientsRepository cltService;
	public void SaveClient(Clients clt)
	{
		cltService.save(clt);
	}
	
	public Clients SaveApiClient(Clients clt)
	{
		return cltService.save(clt);
	}
	// Authentification Client
		public Clients LoginClient(String email,String password) {
			// Il faut ajouter dans l'entité "Clients" un constructeur vide 
			//public Clients() {}
			Clients resultat= cltService.findByEmailAndPassword(email, password);
			
			return resultat;
		}
		
		// Verif si l'email existe dans la table clients lors de l'inscription
				public Clients VerifEmail(String email) {
				
					Clients resultat= cltService.findByEmail(email);
					
					return resultat;
				}
		/*
	// getAllEmployee
	
		public List<Employee> getAll(){
			List<Employee> employes=(List<Employee>) emprep.findAll();
			return employes;
		}
		
		// Recherche Employe
		
			public List<Employee> SearchEmploye(String nom,String role){
				List<Employee> employes=(List<Employee>) emprep.Recherche(nom,role);
				return employes;
			}*/
}
