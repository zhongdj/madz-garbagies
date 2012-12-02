package net.madz.swing.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPChecker{

	public synchronized static boolean ipValid(String s){
	    String regex0="(2[0-4]\\d)" + "|(25[0-5])";
	    String regex1="1\\d{2}";
	    String regex2="[1-9]\\d";
	    String regex3="\\d";
	    String regex="("+regex0+")|("+regex1+")|("+regex2+")|("+regex3+")";
	    regex="("+regex+")\\.("+regex+")\\.("+regex+")\\.("+regex+")";
	    Pattern p=Pattern.compile(regex);
	    Matcher m=p.matcher(s);
//	    return m.matches();
	    return true;
	  }
}
