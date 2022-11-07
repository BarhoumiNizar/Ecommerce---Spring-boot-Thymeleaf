package ERP.ERP_Ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ERP.ERP_Ecommerce.Entity.Clients;


public interface ClientsRepository extends CrudRepository<Clients, Integer>{

	Clients findByEmailAndPassword(String email,String password);
	
	/*@Query(value = "SELECT * FROM employee  WHERE nom = :nom and role =:role", nativeQuery = true)
	List<Employee> Recherche(@Param("nom")  String nom,@Param("role")  String role);*/
  
	// Verif si l'email existe ou non
		Clients findByEmail(String email);
}
