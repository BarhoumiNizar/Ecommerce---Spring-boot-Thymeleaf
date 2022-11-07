package ERP.ERP_Ecommerce.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ERP.ERP_Ecommerce.Entity.Produits;


public interface ProduitsRepository extends CrudRepository<Produits, Integer>{

	Produits findByReference(String reference);
	
	/*@Query(value = "SELECT * FROM employee  WHERE nom = :nom and role =:role", nativeQuery = true)
	List<Employee> Recherche(@Param("nom")  String nom,@Param("role")  String role);*/
  
	
}
