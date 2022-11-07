package ERP.ERP_Ecommerce.Controller;

import java.net.URI;

import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import ERP.ERP_Ecommerce.Entity.Clients;
import ERP.ERP_Ecommerce.Services.ClientsService;
import ERP.ERP_Ecommerce.config.SecurityConfig;
import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
@RequestMapping("/Client")
public class ClientsController {
	SecurityConfig cryptage=new SecurityConfig();
	@Autowired
	ClientsService clientS;
	//Fonction Add Client
	  @RequestMapping(value = "")
	  public String AddClient(@RequestParam(value="email",required = false) String email,
			  @RequestParam(value="nom_client",required = false) String nom_client,@RequestParam(value="prenom_client",required = false) String prenom_client,
			  @RequestParam(value="tel",required = false) String tel,@RequestParam(value="password",required = false) String password,@RequestParam(value="rue",required = false) String rue
			  ,@RequestParam(value="code_postal",required = false) String code_postal,@RequestParam(value="ville",required = false) String ville,
			  @RequestParam(value="pays",required = false) String pays,Model mod,HttpSession session) {
		  if(email!=null) {
			  Clients client=new Clients(email,nom_client,prenom_client,tel,cryptage.cryptage(password,"SHA-256"),rue,code_postal,ville,pays);
			 String msg=null;
			  if(clientS.VerifEmail(email)!=null) {
				  msg="Email "+email+" existe déja";
				 
			  }
			  else {
			  clientS.SaveClient(client);
			  msg="Création compte effectuée avec Succée";
			  session.setAttribute("client", client);
			
			  return "redirect:/";
			  }
			  session.getAttribute("client");
			  mod.addAttribute("msg", msg);
			  mod.addAttribute("client", client);
			/*  mod.addAttribute("email", client.getEmail());
			  mod.addAttribute("nom", client.getNom_client());*/
		  }
		  
		  return "clients/Ajout";
	  }
	  
	  // LOgin Client
	  
	  @RequestMapping(value = "/login",method = RequestMethod.POST)
	  public String LoginClient(@RequestParam(value="pseudo") String pseudo,@RequestParam(value="password") String password,
			  RedirectAttributes msgFlash,HttpSession session,Model mod) {
		 
			 
			Clients client= clientS.LoginClient(pseudo,cryptage.cryptage(password,"SHA-256"));
			if(client!=null) {
				 session.setAttribute("client", client);
				 System.out.println("Nom "+client.getNom_client());
				  mod.addAttribute("client", client);
				  return "redirect:/";
			}
			else {
				System.out.println("nononono");
			}
			
			 // msgFlash.addFlashAttribute("msg", "Création compte effectuée avec Succée");
		  
		  
		  return "clients/Ajout";
	  }
/****************  API   ********************/
	  @PostMapping(value="/request", produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity postController(@RequestBody Clients loginForm) {
	   
		  clientS.SaveClient(loginForm);
	      return ResponseEntity.ok(HttpStatus.OK);
	  }
}
