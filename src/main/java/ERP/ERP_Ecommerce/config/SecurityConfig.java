package ERP.ERP_Ecommerce.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SecurityConfig {
	
	public String cryptage(String mdp,String type)
    {
		//"SHA-256"
	 String password = mdp ;
      MessageDigest md ;
        StringBuffer sb ;
        
       try {
			md = MessageDigest.getInstance(type);
			
			md.update(password.getBytes());

	        byte byteData[] = md.digest();

	        //convertir le tableau de bits en une format hexadécimal - méthode 1
	        sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        
	        return sb.toString() ;
	        
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        return null ;

        
    }

}
