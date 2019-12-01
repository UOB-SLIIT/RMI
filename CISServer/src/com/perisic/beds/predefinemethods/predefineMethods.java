package com.perisic.beds.predefinemethods;
/**
 * Contains with server side methods
 * 
 * @author Kavindi De Silva
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import com.perisic.beds.others.MyQuestion;

public class predefineMethods {
	
//method to get the max number of question ID
public static int getMaxValueQuestion()throws Exception{
	String qry="SELECT max(question_id)as max_value from questiontable";
	
	ResultSet rst=DBMethods.getREsultSet(qry);
	int maxValue=0;
	
	while(rst.next()){
		maxValue = Integer.parseInt(rst.getString("max_value"));   
    }
    rst.close();
    
    return maxValue;
}

//method to get the question count
public static  int questionCount()throws Exception{
	String qry="SELECT COUNT(*)AS numberofrows FROM questiontable";
	ResultSet rst=DBMethods.getREsultSet(qry);
	int rowCount=0;
	
	
	while(rst.next()){
		rowCount = Integer.parseInt(rst.getString("numberofrows"));   
    }
    rst.close();
    
    return rowCount;
}

//To get the Question from ID
public static MyQuestion getQuestionFromQID(int id)throws Exception{
	MyQuestion ob1=new MyQuestion();
	String qry="SELECT * FROM questiontable WHERE question_id="+id;
	ResultSet rst=DBMethods.getREsultSet(qry);
	
	while(rst.next()){
		ob1.setQuizID(id);
		
		String quiz = rst.getString("question");
		ob1.setQuiz(quiz);
		
		String answer = rst.getString("answer_id");
		ob1.setAnswerID(Integer.parseInt(answer));
	}
    rst.close();
    
    
	return ob1;
}

//To get the admin password 
public static String getAdminPassword()throws Exception{
	
	String password1 = null;
	JPanel panel = new JPanel();
	JLabel label = new JLabel("Enter admin password:");
	JPasswordField pass = new JPasswordField(10);
	panel.add(label);
	panel.add(pass);
	String[] options = new String[]{"OK", "Cancel"};
	int option = JOptionPane.showOptionDialog(null, panel, "Admin Password Verification",
	                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
	                         null, options, options[1]);
	if(option == 0) // pressing OK button
	{
	    char[] password = pass.getPassword();
	   
	    password1= new String(password);
	}else {
		System.out.println("Select Cancel");
		System.exit(0);
		
	}
	
	return password1;
}

//Return the question where question id equals to parameter value
public static String getQuestionFromQID(String id)throws Exception{
	String question=null;
	String qry="SELECT question FROM questiontable WHERE question_id="+id;
	
	ResultSet rst= DBMethods.getREsultSet(qry);
	
	while(rst.next()){
		question=rst.getString("question");
	}
    rst.close();
    
    return question;
}

//Return the answer where question id equals to parameter value
public static String getAnswerFromQID(String id)throws Exception{
	String answer=null;
	String qry="SELECT answer_id FROM questiontable WHERE question_id="+id;
	
	ResultSet rst= DBMethods.getREsultSet(qry);
	
	while(rst.next()){
		answer=rst.getString("answer_id");
	}
  rst.close();
  
  return answer;
}

//Read Selection date file 
public static String readSelectionDateFile()throws Exception{
	
	 File file = new File("C:\\Users\\User\\Documents\\CISProjectServer\\CISServer\\OuterFiles\\selectionDate.txt");
	    StringBuilder fileContents = new StringBuilder((int)file.length());        

	    try (Scanner scanner = new Scanner(file)) {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + System.lineSeparator());
	        }
	        return fileContents.toString();
	    }
}

//Write on Selection date file 
public static void writeSelectionDateFile(String text)throws Exception{
	
	try (PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\User\\Documents\\CISProjectServer\\CISServer\\OuterFiles\\selectionDate.txt"))) {
	    out.print(text);
	}catch (Exception e) {
		System.err.println(e);
	}
}

//method to verify the selection date validity
public static boolean verifySelectionDateValidity()throws Exception{
	boolean result = false;
	
	String selectionDate=readSelectionDateFile();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate = LocalDate.now();
	String currentDate=dtf.format(localDate);
	
	String actual = "";
	for(int i=0;i<selectionDate.length();i++) {
		if(Character.isDigit(selectionDate.charAt(i))) {
			actual+=selectionDate.charAt(i);
		}
	}

    String[] data1 = currentDate.split("/", 3);

    int tempSelectionYear=Integer.parseInt(actual);
    int tempCurrentYear=Integer.parseInt(data1[0]+data1[1]+data1[2]);

    System.out.println(tempSelectionYear+" "+tempCurrentYear);
    if(tempSelectionYear<tempCurrentYear) {  
    	System.out.println("Selection Year small");
    	result=false;
    }else {
    	result=true;
    	System.out.println("Selection Year large");
    }
	
	return result;
}

//Method to store candidates values to an array
public static String[] getCandidatesBillNumbers() throws Exception {
	
	String qry1="SELECT COUNT(*)AS numberofrows FROM billinfo b, clientdata c where b.nic=c.nic and c.winningPorgress >= 75";
	ResultSet rst1=DBMethods.getREsultSet(qry1);
	int rowCount=0;
	
	while(rst1.next()){
		rowCount = Integer.parseInt(rst1.getString("numberofrows"));   
    }
    rst1.close();
    
    
    String qry="select b.bill_no from billinfo b, clientdata c where b.nic=c.nic and c.winningPorgress >= 75";
	ResultSet rst=DBMethods.getREsultSet(qry);

	String[] billNo= new String[rowCount];
	int i=0;
	while(rst.next()) {
		billNo[i]=rst.getString("bill_no");
		i++;
	} 
	rst.close();
		
	return billNo;
}

//Method to get admin password from DB
public static String  getAdminPasswordDB()throws Exception{
	String pass=null;
	String qry="SELECT password FROM admin WHERE user='admin'";
	
	ResultSet rst= DBMethods.getREsultSet(qry);
	
	while(rst.next()){
		pass=rst.getString("password");
	}
	rst.close();
	
	return pass; 
}

//Get salt value from the DB
public static String  getAdminSaltDB()throws Exception{
	String salt=null;
	String qry="SELECT salt FROM admin WHERE user='admin'";
	
	ResultSet rst= DBMethods.getREsultSet(qry);
	
	while(rst.next()){
		salt=rst.getString("salt");
	}
	rst.close();
	
	return salt; 
}

//To verfify admin password
public static boolean adminPasswordVerification(String givenPass)throws Exception{
      // User provided password to validate
	        String providedPassword = givenPass;
	                
	        // Encrypted and Base64 encoded password read from database
	        String securePassword = predefineMethods.getAdminPasswordDB();
	        
	        // Salt value stored in database 
	        String salt = predefineMethods.getAdminSaltDB();
	        
	        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
	        
	      
			return passwordMatch;

}

// to set the admin password
public static void setAdminPassword(String password) throws Exception {
	PreparedStatement pst;
	String myPassword = password;
    
    // Generate Salt. The generated value can be stored in DB. 
    String salt = PasswordUtils.getSalt(30);
    
    // Protect user's password. The generated value can be stored in DB.
    String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);
    
    // Store protected password in DB
	String qry="UPDATE admin SET password='"+mySecurePassword+"',salt='"+salt+"' WHERE user='admin'";
	pst=DBMethods.prepare(qry);
	pst.execute();
	
}

//to get the name of the client using billNo
public static String getName(String billNo) throws Exception {
	String qry="SELECT c.name FROM clientdata c, billinfo r WHERE c.nic=r.nic AND r.bill_no="+billNo;
	String user_name="";
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			user_name = rst.getString("name");
		}
        rst.close();

	return user_name;
}

//To set the selection status
public static void setSelection(String value)throws Exception{

		
		try (PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\User\\Documents\\CISProjectServer\\CISServer\\OuterFiles\\selection.txt"))) {
		    out.print(value);
		}catch (Exception e) {
			System.err.println(e);
		}
}

//to get email of the client using billNo
public static String getEmail(String billNo) throws Exception {
	String qry="SELECT c.email FROM clientdata c, billinfo r WHERE c.nic=r.nic AND r.bill_no="+billNo;
	String user_email="";
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			user_email = rst.getString("email");
		}
        rst.close();

	return user_email;
}

//To send email
public static void sendEmail(String subject, String message1, String to) throws Exception {
	
	String username="abcsuperportal@gmail.com";
	String password="KFvhc@6687";

	Properties props = new Properties();
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");

	Session session = Session.getDefaultInstance(props,
		new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});

	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to)); // recipient
		message.setSubject(subject);
		message.setText(message1);

		Transport.send(message);

		System.out.println("Done");

	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}
	
}

//To store winner details
public static void storeWinnerValue(String billNumber, String winner) throws Exception {
	PreparedStatement pst;
	String sql="UPDATE winnertable set winnername='"+winner+"',winnerbillno='"+billNumber+"'WHERE id=1";
	pst=DBMethods.prepare(sql);
	pst.execute();
	
	System.out.println("Server : storeWinnerValue : Winner Value is successfully Stored");
}


}
