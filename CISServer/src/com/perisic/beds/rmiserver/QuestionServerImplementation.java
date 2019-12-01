package com.perisic.beds.rmiserver;
/**
 * Implementation of the questionnaire. Note that chosen answers are collected in this
 * object as well. That means that if the object is destroyed, for instance server restart
 * the collected data is all gone. 
 * To do: make data persistent, for instance link collected data to a database or save data
 * in a text file.  
 * 
 * @author Kavindi De Silva
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;
import com.perisic.beds.others.BillDetails;
import com.perisic.beds.others.MyQuestion;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.others.UserSatisfactionAnswers;
import com.perisic.beds.predefinemethods.DBMethods;
import com.perisic.beds.predefinemethods.PasswordUtils;
import com.perisic.beds.predefinemethods.predefineMethods;
import com.perisic.beds.rmiinterface.Question;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class QuestionServerImplementation 
extends UnicastRemoteObject implements RemoteQuestions{
	PreparedStatement pst;
	private static final long serialVersionUID = -3763231206310491048L;
	Vector<Question> myQuestions = new Vector<Question>(); 
	/**
	 * All questions and answers are initialised in the constructor of this class. 
	 * To do: read questions and options from an external data file. 
	 * @throws Exception 
	 */
	QuestionServerImplementation() throws Exception {
		super();
		System.out.println("QuestionServerImplementation Created");
		
		MyQuestion ob1=new MyQuestion();
		int rowCount=predefineMethods.questionCount();
		System.out.println(rowCount);
		int countValue=1;
		
		
		
		//Yes, No, Maybe Answers
		String[] answers = {"Yes", "No", "Maybe" }; 
		
		//Satisfaction Answers
		String[] answers1 = {"Very Satisfied", "Satisfied", "Neutral", "Dissatisfied", "Very dissatisfied" }; 
		
		//Agree, Disagree Answers
		String[] answers2 = {"Strongly Agree", "Agree", "Neutral", "Disagree", "Strongly Disagree" }; 
		
		
	
		while(countValue<=rowCount) {
			ob1=predefineMethods.getQuestionFromQID(countValue);
			String[] answerIdentity;
			
			
			System.out.println(ob1.getQuiz());

			
			if(ob1.getAnswerID()==1) {
				answerIdentity=answers;
			}else if(ob1.getAnswerID()==2) {
				answerIdentity=answers1;
			}else
			{
				answerIdentity=answers2;
			}
			
			Question question = new Question(ob1.getQuiz(), answerIdentity );
			myQuestions.add(question); 
			countValue++;
		}
		
		
		

	}

	/**
	 * Implementation of remote interface method. 
	 */
	@Override
	public int getNumberOfQuestions() throws RemoteException {
		return myQuestions.size();
	}
	/**
	 * Implementation of remote interface method. 
	 */
	@Override
	public Question getQuestion(int i) throws RemoteException {
		return myQuestions.elementAt(i);
	}
	/**
	 * Implementation of remote interface method. 
	 */	
	@Override
	public void submitAnswer(int i, String answer) throws RemoteException {
		myQuestions.elementAt(i).addAnswer(answer);
	}
	/**
	 * Implementation of remote interface method. 
	 */	
	@Override
	public Vector<Question> getData() throws RemoteException {
		return myQuestions; 
	}
	
//******************************************************************************************************************** My Methods

//Method to get name from bill number 
	@Override
	public String getName(int billNo) throws Exception {
		String qry="SELECT c.name FROM clientdata c, billinfo r WHERE c.nic=r.nic AND r.bill_no="+billNo;
		String user_name="";
			ResultSet rst=DBMethods.getREsultSet(qry);
			while(rst.next()){
				user_name = rst.getString("name");
			}
	        rst.close();

		System.out.println("Server Side : getName : getName Success");
		return user_name;
	}

//Method to Validate the Bill No
	@Override
	public boolean validateBillNo(long billNo) throws RemoteException {
		
		String qry="SELECT bill_no FROM billinfo";
		try {
			ResultSet rst=DBMethods.getREsultSet(qry);
			while(rst.next()){
	            long user_billNo = Long.parseLong(rst.getString("bill_no"));
	            if(user_billNo==billNo) {
	            	return true;
	            }  
	        }
	        rst.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		System.out.println("Server Side : validateBillNo : Bill No Successfully Validated.");
		return false;
	}

// to Serialize userDetails
	@Override
	public void getSerializedUserDetails(long billNo) throws RemoteException {
		UserDetails user1=new UserDetails();
		String filename="error";
		String qry="SELECT c.*,b.bill_no FROM clientdata c, billinfo b WHERE c.nic=b.nic AND b.bill_no="+billNo;
		try {
			ResultSet rst=DBMethods.getREsultSet(qry);
			while(rst.next()){
				String user_nic = rst.getString("nic");
				user1.setUser_nic(user_nic);
				
	            String user_firstname = rst.getString("name");
	            user1.setUser_firstname(user_firstname);
	            
	            String user_lastname = rst.getString("last_name");
	            user1.setUser_lastname(user_lastname);
	            
	            String user_fullname = user_firstname+" "+user_lastname;
	            user1.setUser_fullname(user_fullname);
	            
	            String user_address = rst.getString("address");
	            user1.setUser_address(user_address);
	            
	            int user_phoneNo = Integer.parseInt(rst.getString("phone_no"));
	            user1.setUser_phoneNo(user_phoneNo);
	            
	            String user_email = rst.getString("email");
	            user1.setUser_email(user_email);
	            
	            String user_feedback= rst.getString("feedback");
	            user1.setUser_feedback(user_feedback);
	            
	            String user_type =rst.getString("type");
	            user1.setUser_type(user_type);
	            
	            long user_billNo= Long.parseLong(rst.getString("bill_no"));
	            user1.setUser_billNo(user_billNo);
	            
	            filename="serialized_user_details";
	            
	        }
	        rst.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		try {
			FileOutputStream fileout = new FileOutputStream("C:\\Users\\Tharaka\\Desktop\\CIS Project\\CISClient\\SerializedFiles\\"+filename+".txt");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(user1);
			out.close();
			fileout.close();
			System.out.println("Server Side : getSerializedUserDetails : Serialization is success");
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
	}

// To get deSerialized user details
	@Override
	public UserDetails getDeserializedUserDetails() throws Exception {
		UserDetails user= new UserDetails();
		String filename="serialized_user_details";
		FileInputStream fileIn = new FileInputStream("C:\\Users\\Tharaka\\Desktop\\CIS Project\\CISClient\\SerializedFiles\\"+filename+".txt");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		user = (UserDetails)in.readObject();
		in.close();
		fileIn.close();
		System.out.println("Server Side : getDeserializedUserDetails : Deserialization is success");
		return user;
		
	}

//To chek the registration status
	@Override
	public boolean checkRegistrationStatus(long billNo) throws Exception {
		String qry="SELECT c.registration FROM clientdata c, billinfo b WHERE c.nic=b.nic AND b.bill_no="+billNo;
		
			ResultSet rst=DBMethods.getREsultSet(qry);
			boolean status=false;
			while(rst.next()){
				status = Boolean.parseBoolean(rst.getString("registration"));   
	        }
	        rst.close();
	
		System.out.println("Server Side : checkRegistrationStatus : Registration Status Successfully returned.");
		return status;
	}
	
//To get winner progress Value
	@Override
	public double getWiningProgree(int nic) throws Exception {
		double progress = 0;
		String qry="SELECT winningPorgress from clientdata where nic="+nic;
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			progress = Double.parseDouble(rst.getString("winningPorgress"));   
        }
        rst.close();
        System.out.println("Server Side : getWiningProgree : Winning Progress get Successfully.");
		return progress;
	}

// to set the client submission
	@Override
	public void setClientSubmission(int qID, String answer, String nic) throws Exception {
		String qry="UPDATE satisficationtable SET question_"+qID+" = '"+answer+"' WHERE nic ="+nic;
		pst=DBMethods.prepare(qry);
		pst.execute();
		
		System.out.println("Server : setClientSubmission : SatisficationTable Updated");
		
	}

//Create table coloumn in satisfication table
	@Override
	public void createSatisficationTableColumn(String nic) throws Exception {
		String qry="INSERT INTO satisficationtable(nic)VALUES("+nic+")";
		pst=DBMethods.prepare(qry);
		pst.execute();
		
		System.out.println("Server : setClientSubmission : Insert Row into SatisficationTable ");
	}

//Method to get serialzed user satisfaction answers
	@Override
	public void getSerializedUserSatisficationAnswers(int nic) throws RemoteException {
		UserSatisfactionAnswers user1= new UserSatisfactionAnswers();
		String filename="Satisfication_answerFile_error";
		String qry="SELECT * FROM satisficationtable WHERE nic="+nic;
		try {
			ResultSet rst=DBMethods.getREsultSet(qry);
			while(rst.next()){
				String q0=rst.getString("question_0");
				user1.setQuestion_0(q0);
				
				String q1=rst.getString("question_1");
				user1.setQuestion_0(q1);
				
				String q2=rst.getString("question_2");
				user1.setQuestion_0(q2);
				
				String q3=rst.getString("question_3");
				user1.setQuestion_0(q3);
				
				String q4=rst.getString("question_4");
				user1.setQuestion_0(q4);
				
				String q5=rst.getString("question_5");
				user1.setQuestion_0(q5);
				
				String q6=rst.getString("question_6");
				user1.setQuestion_0(q6);
				
				String q7=rst.getString("question_7");
				user1.setQuestion_0(q7);
				
				String q8=rst.getString("question_8");
				user1.setQuestion_0(q8);
				
				String q9=rst.getString("question_9");
				user1.setQuestion_0(q9);
				
				String q10=rst.getString("question_10");
				user1.setQuestion_0(q10);
				
				String q11=rst.getString("question_11");
				user1.setQuestion_0(q11);
			}
			rst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream fileout = new FileOutputStream("C:\\Users\\Tharaka\\Desktop\\CIS Project\\CISClient\\SerializedFiles\\"+filename+".txt");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(user1);
			out.close();
			fileout.close();
			System.out.println("Server Side : getSerializedUserDetails : Serialization is success");
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

//Get client sastisfactionj answers
	@Override
	public String[][] getClientsSatisficationAnswers() throws Exception {
		String qry="SELECT COUNT(*)AS numberofrows FROM satisficationtable";
		ResultSet rst=DBMethods.getREsultSet(qry);
		int rowCount=0;
		
		
		while(rst.next()){
			rowCount = Integer.parseInt(rst.getString("numberofrows"));   
        }
        rst.close();
        
        
        String value[][] = null;
       
        rst.close();
        for(int x=0;x<rowCount;x++) {
        	String qry1="SELECT * FROM satisficationtable WHERE rowid="+(x+1);
            ResultSet rst1=DBMethods.getREsultSet(qry1);
        	 while(rst1.next()){
        		 
     			String nic = rst.getString("nic");
     			value[x][0]=nic;
     			String q0 = rst.getString("question_0"); 
     			value[x][1]=q0;
     			String q1 = rst.getString("question_1"); 
     			value[x][2]=q1;
     			String q2 = rst.getString("question_2"); 
     			value[x][3]=q2;
     			String q3 = rst.getString("question_3"); 
     			value[x][4]=q3;
     			String q4 = rst.getString("question_4"); 
     			value[x][5]=q4;
     			String q5 = rst.getString("question_5"); 
     			value[x][6]=q5;
     			String q6 = rst.getString("question_6"); 
     			value[x][7]=q6;
     			String q7 = rst.getString("question_7"); 
     			value[x][8]=q7;
     			String q8 = rst.getString("question_8"); 
     			value[x][9]=q8;
     			String q9 = rst.getString("question_9"); 
     			value[x][10]=q9;
     			String q10 = rst.getString("question_10"); 
     			value[x][11]=q10;
     			String q11 = rst.getString("question_11"); 
     			value[x][12]=q11;
             }
        		
        }
		return value;
	}


//Update registration value
	@Override
	public void updateRegistration(String nic) throws Exception {
		String qry="UPDATE clientdata SET registration= 'true' WHERE nic ="+nic;
		pst=DBMethods.prepare(qry);
		pst.execute();
		
		System.out.println("Server : setUsernameAndPassword : Succesully registartion Column Updated");
	}

//get the password from username
	@Override
	public String getPasswordFromUsername(String username) throws Exception {
		String qry="SELECT password FROM clientdata WHERE username='"+username+"'";
		
		ResultSet rst=DBMethods.getREsultSet(qry);
		String pass="";
		while(rst.next()){
			pass = rst.getString("password");   
        }
        rst.close();

	System.out.println("Server Side : getPasswordFromUsername : Password Successfully Returned");
	return pass;
	}
	
//Get answer count of yes no Quiz
	@Override
	public int getAnswerCountYesNoQuiz(String quiz, String ans) throws Exception {
		String qry="Select count(*) as numberofAnswers from satisficationtable where '"+quiz+"'='"+quiz+"'";
		ResultSet rst=DBMethods.getREsultSet(qry);
		int count=0;
		
		
		while(rst.next()){
			count = Integer.parseInt(rst.getString("numberofAnswers"));   
        }
        rst.close();
        System.out.println("Server Side : getAnswerCountYesNoQuiz : Answer Count Successfully Returned");
		return count;
	}

	
	/*Methods to get Bill Details
	First it creates an object according to database values 
	then it returns the object*/
	@Override
	public BillDetails getBillDetails(String nic) throws Exception {
		
		String qry="SELECT * FROM billinfo WHERE nic="+nic;
		BillDetails ob1=new BillDetails();
		
		try {
			ResultSet rst=DBMethods.getREsultSet(qry);
			
			while(rst.next()){
				String billNo=rst.getString("bill_no");
				ob1.setBillNo(billNo);
				
				String branchCode=rst.getString("branch_code");
				ob1.setBranchCode(branchCode);
				
				double billCost=Double.parseDouble(rst.getString("cost"));
				ob1.setBillcost(billCost);
				
				String purchasedDate=rst.getString("purchase_date");
				ob1.setPurchasedDate(purchasedDate);
				
				String purchasedTime=rst.getString("purchase_time");
				ob1.setPurchasedTime(purchasedTime);
			}
			rst.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server Side : getBillDetails : BillDetails Object Successfully Returned");
		return ob1;
		
	}

//Method to Get Branch Location
	@Override
	public String getBranchLocation(String branchCode) throws Exception {
		String qry="SELECT location FROM branchdetails WHERE branch_code='"+branchCode+"'";
		String location=null;
		
		
			ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			location=rst.getString("location");
		}
		rst.close();
		System.out.println("Server Side : getBranchLocation : branch Location Successfully Returned");
		return location;
	}

//Method to Get EmployeeId From Bill no
	@Override
	public String getEmployeeIDFromBillNo(String billNo, String empPostition) throws Exception {
		String qry="select * from billinfo where bill_no="+billNo;
		
		String id=null;
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			if(empPostition.equals("manager")) {
				id=rst.getString("managerID");
			}
			else
			{
				id=rst.getString("cashierID");
			}
		
	}
	rst.close();
	System.out.println("Server Side : getEmployeeIDFromBillNo : employeeID Successfully Returned");
	return id;
	}

//Method to Get Employee Name From EmployeeID
	@Override
	public String getEmployeeNameFromEmpID(String empID) throws Exception {
		String empName=null;
			String qry="SELECT * FROM employeetable WHERE employee_Id ="+empID;
			ResultSet rst=DBMethods.getREsultSet(qry);
			while(rst.next()){
			
				empName=rst.getString("first_name");
				empName=empName+" "+rst.getString("last_name");	
		}
		rst.close();
		System.out.println("Server Side : getEmployeeNameFromEmpID : employee Name Successfully Returned");
		return empName;
	}

//get userdetails from nic
	@Override
	public UserDetails getUserDetailsFromNIC(String nic) throws Exception {
		String qry="SELECT * FROM clientdata where nic="+nic;
		UserDetails user=new UserDetails();
		
		try {
			ResultSet rst=DBMethods.getREsultSet(qry);
			
			while(rst.next()){
				String name=rst.getString("name");
				user.setUser_firstname(name);
				
				String lastName=rst.getString("last_name");
				user.setUser_lastname(lastName);
				
				String address=rst.getString("address");
				user.setUser_address(address);
				
				String phoneNo=rst.getString("phone_no");
				user.setUser_phoneNo(Integer.parseInt(phoneNo));
				
				String email=rst.getString("email");
				user.setUser_email(email);
				
				String feedback=rst.getString("feedback");
				user.setUser_feedback(feedback);
			}
			rst.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Server Side : getUserDetailsFromNIC : UserDetails Object Successfully Returned");
		return user;
	}

//update uservalues
	@Override
	public void updateUser(String nic, String name, String address, String phoneNo, String email, String feedback)
			throws Exception {
		String[] data = name.split(" ", 2);
		String qry="UPDATE clientdata SET name = '"+data[0] +"', last_name = '"+data[1]+"', address = '"+address+"', phone_no = '"+phoneNo+"', email = '"+email+"', feedback = '"+feedback+"' WHERE nic ="+nic;
		pst=DBMethods.prepare(qry);
		pst.execute();
		System.out.println("Server Side : updateUser : UserDetails Successfully Updated");
		
	}

//Method to set Question Completeness Value, It will check the question submission is complete or not
	@Override
	public void setCompletenessValue(String nic) throws Exception {
		String qry="UPDATE satisficationtable SET completeness = 'true' WHERE nic ="+nic;
		pst=DBMethods.prepare(qry);
		pst.execute();
		System.out.println("Server Side : setCompletenessValue : CompletenessValue Successfully Updated");
	}

//Method to check whether question Submission is complete or not
	@Override
	public boolean getCompletenessValue(String nic) throws Exception {
		boolean result=false;
		String qry="select completeness from satisficationtable where nic="+nic;
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			result = Boolean.parseBoolean(rst.getString("completeness"));   
        }
        rst.close();
        System.out.println("Server Side : getCompletenessValue : Completeness Value successfully returned");
		return result;
	}

//This Method will update the progress Value of the Client according to the nic 
	@Override
	public void updateWinningProgressValue(double Value, String nic) throws Exception {
		double progress = 0.0;
		String qry="SELECT winningPorgress from clientdata where nic="+nic;
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			progress = Double.parseDouble(rst.getString("winningPorgress"));   
        }
        rst.close();
		
        progress+=Value;
        String qry1="UPDATE clientdata SET winningPorgress = "+progress+" WHERE nic ="+nic;
		pst=DBMethods.prepare(qry1);
		pst.execute();
		
		System.out.println("Server Side : updateWinningProgressValue : Winning Progress Value successfully Increased");
	}

//Stores Bill Numbers in an Array
	@Override
	public String[] billNoArray(String nic) throws Exception {
		
		String qry1="SELECT COUNT(*)AS numberofrows FROM billinfo where nic="+nic;
		ResultSet rst1=DBMethods.getREsultSet(qry1);
		int rowCount=0;
		
		
		while(rst1.next()){
			rowCount = Integer.parseInt(rst1.getString("numberofrows"));   
        }
        rst1.close();
		
		String qry="select bill_no from billinfo where nic="+nic;
		ResultSet rst=DBMethods.getREsultSet(qry);
		String[] billNo= new String[rowCount];
		int i=0;
		while(rst.next()) {
			billNo[i]=rst.getString("bill_no");
			i++;
		} 
		rst.close();
		
		System.out.println("Server Side : billNoArray : Bill Numbers Successfully Returned as Array");		
		return billNo;
	}

//It will return the billDetails Type object from billno
	@Override
	public BillDetails getBillDetailsFromBillNumber(String billNo) throws Exception {
		BillDetails ob1=new BillDetails();
		String qry="SELECT * from billinfo where bill_no="+billNo;
		ResultSet rst=DBMethods.getREsultSet(qry);
		
		while(rst.next()){
			ob1.setBillNo(billNo);
			String purchasedDate=rst.getString("purchase_date");
			ob1.setPurchasedDate(purchasedDate);
			String purchasedTime=rst.getString("purchase_time");	
			ob1.setPurchasedTime(purchasedTime);
			String cost= rst.getString("cost");
			ob1.setBillcost(Double.parseDouble(cost));
			String branchID=rst.getString("branch_code");	
			ob1.setBranchCode(branchID);
	}
	rst.close();
		
	System.out.println("Server Side : getBillDetailsFromBillNumber : Bill Deatils returnd successfully");		
		return ob1;
	}

//Return the question score value
	@Override
	public double getQuestionScore() throws Exception {
		double score; 
		score=90.0/ predefineMethods.questionCount();
		return score;
	}

//get salt value from username
	@Override
	public String getSaltFromUsername(String username) throws Exception {

		String qry="SELECT salt FROM clientdata WHERE username='"+username+"'";
		
		ResultSet rst=DBMethods.getREsultSet(qry);
		String salt="";
		while(rst.next()){
			salt = rst.getString("salt");   
        }
        rst.close();

	System.out.println("Server Side : getSaltFromUsername : Salt Successfully Returned");
	return salt;

	}

// Method to verfiy password by comparing given password and salt+dbPass
	@Override
	public boolean verfiySaltedPassword(String password, String dbPass, String saltValue) {
		// User provided password to validate
        String providedPassword = password;
                
        // Encrypted and Base64 encoded password read from database
        String securePassword = dbPass;
        
        // Salt value stored in database 
        String salt = saltValue;
        
        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
        
        return passwordMatch;
	}

//set username and password
	@Override
	public void setUsernameAndPassword(String username, String password, String nic) throws Exception {
		
		String myPassword = password;
        
        // Generate Salt. The generated value can be stored in DB. 
        String salt = PasswordUtils.getSalt(30);
        
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);
        
        // Store protected password in DB
		String qry="UPDATE clientdata SET username= '"+username+"',password='"+mySecurePassword+"',salt='"+salt+"' WHERE nic ="+nic;
		pst=DBMethods.prepare(qry);
		pst.execute();
		
		System.out.println("Server : setUsernameAndPassword : Username and Password Successcully Added");
		
	}

//read outer file selection
	@Override
	public String readSelectionFile() throws Exception {
		
		 File file = new File("C:\\Users\\Tharaka\\Desktop\\CIS Project\\CISServer\\OuterFiles\\selection.txt");
		    StringBuilder fileContents = new StringBuilder((int)file.length());        

		    try (Scanner scanner = new Scanner(file)) {
		        while(scanner.hasNextLine()) {
		            fileContents.append(scanner.nextLine() + System.lineSeparator());
		        }
		        return fileContents.toString();
		    }

	}

//Send an email
	@Override
	public void sendEmail(String subject, String message1, String to) throws Exception {
		
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

//get email address from billno
	@Override
	public String getEmail(String billNo) throws Exception {
		String qry="SELECT c.email FROM clientdata c, billinfo r WHERE c.nic=r.nic AND r.bill_no="+billNo;
		String user_email="";
			ResultSet rst=DBMethods.getREsultSet(qry);
			while(rst.next()){
				user_email = rst.getString("email");
			}
	        rst.close();

		return user_email;
	}

//get email from username
	@Override
	public String getEmailFromUsername(String username) throws Exception {
		String qry="SELECT email from clientdata WHERE username='952680196nuw'";
		
		String user_email1="";
		ResultSet rst=DBMethods.getREsultSet(qry);
		while(rst.next()){
			user_email1= rst.getString("email");
			
		}
        rst.close();

	return user_email1;
	}

//reset the password
	@Override
	public void resetPassword(String password, String nic) throws Exception {
		String myPassword = password;
        
        // Generate Salt. The generated value can be stored in DB. 
        String salt = PasswordUtils.getSalt(30);
        
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);
        
        // Store protected password in DB
		String qry="UPDATE clientdata SET password='"+mySecurePassword+"',salt='"+salt+"' WHERE nic ="+nic;
		pst=DBMethods.prepare(qry);
		pst.execute();
		
		System.out.println("Server : resetPassword : assword reset Successcully");
		
	}

//Store winner value
	@Override
	public void storeWinnerValue(String billNumber, String winner) throws Exception {
		String sql="UPDATE winnertable set winnername='"+winner+"',winnerbillno='"+billNumber+"'WHERE id=1";
		pst=DBMethods.prepare(sql);
		pst.execute();
		
		System.out.println("Server : storeWinnerValue : Winner Value is successfully Stored");
	}

//get winner details
	@Override
	public String[] getWinnerDetails() throws Exception {

		String qry="SELECT winnername, winnerbillno FROM winnertable WHERE id=1";
		ResultSet rst=DBMethods.getREsultSet(qry);


		String[] winnerArray= new String[2];

		while(rst.next()) {
			winnerArray[0]=rst.getString("winnerbillno");
			winnerArray[1]=rst.getString("winnername");

		} 
		rst.close();
		
		System.out.println("Server Side : getWinnerDetails : Winner Details Successfully returnbed");		
		return winnerArray;
	}


}
