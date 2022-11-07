package ERP.ERP_Ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ERP.ERP_Ecommerce.Entity.Employee;

public interface EmployesRepository extends CrudRepository<Employee, Integer>{

	Employee findByEmailAndPassword(String email,String mdp);
	@Query(value = "SELECT * FROM employee  WHERE nom = :nom and role =:role", nativeQuery = true)
	List<Employee> Recherche(@Param("nom")  String nom,@Param("role")  String role);

}
