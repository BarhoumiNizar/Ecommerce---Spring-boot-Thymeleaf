package ERP.ERP_Ecommerce.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
	@RequestMapping(value = "/")
	public String index(HttpSession session,Model mod) {
		 mod.addAttribute("client", session.getAttribute("client"));
		// System.out.println("Nom "+session.getAttribute("client").toString());
		return "index";
	}
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session,Model mod) {
		session.setAttribute("client",null);
	
		// System.out.println("Nom "+session.getAttribute("client").toString());
		 return "redirect:/";
	}

}
