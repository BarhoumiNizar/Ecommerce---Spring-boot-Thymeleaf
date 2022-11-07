package ERP.ERP_Ecommerce.Controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.net.httpserver.Authenticator.Success;

import ERP.ERP_Ecommerce.Entity.Employee;
import ERP.ERP_Ecommerce.Services.EmployesService;
import ERP.ERP_Ecommerce.config.SecurityConfig;

@RestController
@RequestMapping("/admin")
public class EmployesController {
	SecurityConfig cryptage=new SecurityConfig();
	@Autowired
	EmployesService empS;
	private static String UploadEmployes="src/main/resources/static/uploads/employes/";

	
  // Fonction Authentification pour les employes
	 @RequestMapping(value = "")
	 	public String LoginEmploye(@RequestParam(value="email",required = false) String email,@RequestParam(value="password",required = false) String password,
								RedirectAttributes msgFlash,HttpSession session)
			{
				if(email!=null && password!=null) {
					// si l'email=admin@gmail.com et password=admin123 ==> c'est le super-Admin
					if(email.equals("admin@gmail.com") && password.equals("admin123"))
					{
						Employee emp=new Employee();
						emp.setEmail("admin@gmail.com");
						emp.setNom("SuperAdmin");
						emp.setRole("Super-Admin");
						
						session.setAttribute("user", emp);
						return "redirect:/admin/home";
					}
					else {
							if(empS.loginEmploye(email, cryptage.cryptage(password,"SHA-256"))!=null) {
								Employee resLogin=empS.loginEmploye(email, cryptage.cryptage(password,"SHA-256"));
								session.setAttribute("user", resLogin);
								System.out.println("Nom : "+resLogin.getNom());
								return "redirect:/admin/home";
							}
							else
							{
								msgFlash.addFlashAttribute("msg", "Email/Password incorrect");
							
								return "redirect:/admin";
							}
					}
				}
				return "Employes/login";
			}
	  @GetMapping("/json")
	  public ResponseEntity<List<Employee>> getJsonOutput() {  
		  List<Employee> resLogin=empS.getAll();
		  if(resLogin == null)
		  {
			  return ResponseEntity.notFound().build();
		  }
		  else {
		 return  ResponseEntity.ok(resLogin);
		  }
	  }

   // Home Admin
	  // Ajout Employe
		// on ajoute required=false si la recupération des champs de form dans la meme fonction qui return le formulaire
		@RequestMapping("/home")
		public String HomeAdmin(HttpSession session,Model mod,RedirectAttributes msgFlash)
			{
			
			 if(session.getAttribute("user")!=null) {
				 mod.addAttribute("user", session.getAttribute("user"));
				 return "admin/main-layout";
			 }
			 else {
					 msgFlash.addFlashAttribute("msg", "Il faut connecter");
					 return "redirect:/admin";
			 	  }
			}
	
		// Déconnexion
		@RequestMapping("/logout")
		public String logOutAdmin(HttpSession session,RedirectAttributes msgFlash)
			{
			session.setAttribute("user", null);
				
			 return "redirect:/admin";
			}
	// Fonction Add Employe
		  @RequestMapping(value = "/AjoutEmploye")
			 public String AjoutEmploye(@RequestParam(value="email",required = false) String email,@RequestParam(value="mdp",required = false) String password,
					 @RequestParam(value="nom",required = false) String nom,@RequestParam(value="prenom",required = false) String prenom,
					 @RequestParam(value="photo",required = false) MultipartFile photo,@RequestParam(value="role",required = false) String role,
					 RedirectAttributes msgFlash,HttpSession session,Model mod) throws IOException
					{
			  		if(session.getAttribute("user")!=null) {
			  			mod.addAttribute("user", session.getAttribute("user"));
			  			if(email!=null) {
			  				// UploadFile
			  				byte[] bytes= photo.getBytes();
			  				 Path ph=Paths.get(UploadEmployes+photo.getOriginalFilename());
						        Files.write(ph, bytes);
			  				Employee emp=new Employee();
			  				emp.setEmail(email);
			  				emp.setPrenom(prenom);
			  				emp.setNom(nom);
			  				emp.setPassword(cryptage.cryptage(password,"SHA-256"));
			  				emp.setPhoto(photo.getOriginalFilename());
			  				emp.setRole(role);
			  				empS.SaveEmp(emp);
			  				
			  			}
						return "Employes/AjoutEmploye";
			  		}
			  		else {
			  			 msgFlash.addFlashAttribute("msg", "Il faut connecter");
						 return "redirect:/admin";
			  		}
					}

	// Afficher les Employes
		  @RequestMapping("/Afficher")
			public String AfficherEmployes(HttpSession session,Model mod,RedirectAttributes msgFlash)
				{
				
				 if(session.getAttribute("user")!=null) {
					 mod.addAttribute("employes", empS.getAll());
					 mod.addAttribute("user", session.getAttribute("user"));
					 return "Employes/affichage";
				 }
				 else {
						 msgFlash.addFlashAttribute("msg", "Il faut connecter");
						 return "redirect:/admin";
				 	  }
				}
		
   // Search Employe
		  @GetMapping("/search/{nom}/{role}")
		  public ResponseEntity<List<Employee>> Search(@PathVariable("nom") String nom,@PathVariable("role") String role) {  
			  List<Employee> resSearch=empS.SearchEmploye(nom,role);
			  int nbres=resSearch.size();
			  System.out.println(nbres+"\n");
			  if(nbres==0)
			  {
				  return  ResponseEntity.notFound().build();
				
			  }
			  else {
			 return  ResponseEntity.ok(resSearch);
			  }
		  }
		  	
		  @GetMapping("/getAllEMp")
		  public ResponseEntity<?> getClient(){
			  List<Employee> emps=empS.getAll();
			  return  ResponseEntity.ok(emps);
			}
/*********************************************** API Post Employe ************************************************/
		  @PostMapping(value="/api/AddEmp",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
		  public ResponseEntity<Employee> addEmp(@RequestBody Employee emp){
			  emp.setPhoto("hhhh");
			   System.out.println("NOmmmmsmsms "+emp.getNom());
				empS.SaveEmp(emp);
				return ResponseEntity
			            .created(URI
			                     .create(String.format("/persons/%s", emp.getNom())))
			            .body(emp);
			}
}
