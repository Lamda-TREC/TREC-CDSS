package com.IndexServer.socketServer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.UrlValidator;

public class tset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		tset t = new tset();
		System.out.println(t.isCheckEmailCorrect("gerd@kortuem.com"));
		System.out.println(t.isUrlValidator("http://www.ecovisionsolutions.com"));
	}

	
	  
    public boolean isCheckEmailCorrect(String email) {
        if(email.length() == 0) {
            return false;
        }
     
        String pttn = "^\\D.+@.+\\.[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(email);
     
        if(m.matches()) {
            return true;
        }
     
        return false;
    }
    public boolean isUrlValidator(String url)
    {
    	UrlValidator urlValidator = new UrlValidator();
    	
    	String newUrl="";
    	if(!url.contains("http")){
    		newUrl+="http://"+url;
    	}else
    	{
    		newUrl = url;
    	}
    	System.out.println(newUrl);
	    //valid URL
	    if (urlValidator.isValid(newUrl)) {
	       return true;
	    }else
	    {
	    	return false;
	    }
    }
    
}
