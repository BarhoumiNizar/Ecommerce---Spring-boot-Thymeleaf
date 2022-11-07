package ERP.ERP_Ecommerce.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ERP.ERP_Ecommerce.Entity.Produits;
import ERP.ERP_Ecommerce.Services.ProduitsService;



@Controller
@RequestMapping("/Produit")
public class ProduitsController {
	
	@Autowired
	ProduitsService produitS;
	//Upload Photo Produits
	private static String UploadPhotosProduits="src/main/resources/static/uploads/produits/";
	//Fonction Add Client
	  @RequestMapping(value = "/Ajout")
	  public String AddProduit(@RequestParam(value="nom",required = false) String nom,
			  @RequestParam(value="description",required = false) String description, @RequestParam(value="photo",required = false) MultipartFile photo,
			  @RequestParam(value="prix",required = false) Double prix,@RequestParam(value="qteStock",required = false) Integer qteStock,
			  @RequestParam(value="reference",required = false) String reference,Model mod,HttpSession session,RedirectAttributes msgFlash) throws IOException{
		   if(session.getAttribute("user")!=null) {
			   mod.addAttribute("user", session.getAttribute("user"));
			   	if(reference!=null) {
				   	
					  Produits produit=new Produits(nom,description,photo.getOriginalFilename(),prix,qteStock,reference);
					  String msg=null;
					  if(produitS.VerifExistReference(reference)!=null) {
						  msg="Reférence "+reference+" existe déja";
						 
					  }
					  else {
						// UploadFile
			  				byte[] bytes= photo.getBytes();
			  				 Path ph=Paths.get(UploadPhotosProduits+photo.getOriginalFilename());
						        Files.write(ph, bytes);
						  produitS.SaveProduit(produit);
						  msg="Produit Ajouté avec Succée";
						  return "redirect:/Produit/Ajout";
					  }
					 
					  mod.addAttribute("msg", msg);
				  } // Fin if(reference!=null)
			  }	
		   else {
	  			 msgFlash.addFlashAttribute("msg", "Il faut connecter");
				 return "redirect:/admin";
	  		}
		  
		  return "produits/Ajout";
	  }
	  
	  // Affcihage Produit
	  @RequestMapping("")
	  public String AffichageProduit(Model mod,HttpSession session,RedirectAttributes msgFlash) {
		  if(session.getAttribute("user")!=null) {
			   mod.addAttribute("user", session.getAttribute("user"));
			   mod.addAttribute("produits", produitS.getAllProduits());
		  }
		  else {
			  msgFlash.addFlashAttribute("msg", "Il faut connecter");
				 return "redirect:/admin";
		  }
		  return "produits/affichage";
	  }
}
