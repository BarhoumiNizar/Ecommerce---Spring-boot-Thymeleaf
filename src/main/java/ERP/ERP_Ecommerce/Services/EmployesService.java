package ERP.ERP_Ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ERP.ERP_Ecommerce.Entity.Employee;
import ERP.ERP_Ecommerce.Repository.EmployesRepository;

@Service
@Transactional
public class EmployesService {

	@Autowired(required=false)
	EmployesRepository emprep;
	public void SaveEmp(Employee emp)
	{
		emprep.save(emp);
	}
	// Verifier l'existence de l'email+password dans la table
		public Employee loginEmploye(String email,String mdp) {
			return emprep.findByEmailAndPassword(email, mdp);
		}
	// getAllEmployee
	
		public List<Employee> getAll(){
			List<Employee> employes=(List<Employee>) emprep.findAll();
			return employes;
		}
		
		// Recherche Employe
		
			public List<Employee> SearchEmploye(String nom,String role){
				List<Employee> employes=(List<Employee>) emprep.Recherche(nom,role);
				return employes;
			}
}
