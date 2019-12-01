package com.perisic.beds.interfaces;
/**
 * Login interface of the system.
 * By entering bill number it directs to the other window as it is.
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.questionnaire.QuestionSet;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginInterface extends JFrame {
	public JFrame frame;
	public JTextField textField;
	public QuestionSet questionnaire = new QuestionSet(); 
	public RemoteQuestions connection;
	
//Main window of the system
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface window = new LoginInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class Constructor
	public LoginInterface() {
		super();
		initialize();
		setSize(900, 550);
	}


	private void btnValidateBillNoActionPerformed(ActionEvent e) throws MalformedURLException, RemoteException, NotBoundException {                                         
		String billNo=textField.getText();
		int billNoLength=billNo.length();
		
		if(!(billNoLength==10)) {
			JOptionPane.showMessageDialog(null,"Invalid Input!\nIncorrect Billnumber Length");
		}
		else
		{
			boolean returnValue;
			boolean registrationStatus;
			try {
				connection=RemoteConnection.remoteConnect();
				
				returnValue = connection.validateBillNo(Long.parseLong(billNo));
				registrationStatus= connection.checkRegistrationStatus(Long.parseLong(billNo));
				if(returnValue==true) {
					connection.getSerializedUserDetails(Long.parseLong(billNo));
					if(registrationStatus==true) {
						SignInWindow s1= new SignInWindow();
						s1.setVisible(true);	
						System.out.println("Client : User already Registered");
					}
					else {
						SignUpWindow s2= new SignUpWindow();
						s2.setVisible(true);	
						System.out.println("Client : User new to the system");
						dispose();
					}	
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Input!\nPlease Enter Valid Bill Number");
				}
					
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		}
	
    }
	
// Initialize the contents of the frame.	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel imageLabel = new JLabel("New label");
		imageLabel.setIcon(new ImageIcon("C:\\Users\\salit\\Desktop\\CIS Project\\CISClient\\src\\images\\homeBackground1.jpg"));
		imageLabel.setBounds(0, 0, 349, 503);
		frame.getContentPane().add(imageLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(351, 0, 531, 503);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bill No");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		lblNewLabel.setBounds(61, 134, 96, 45);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyValue=e.getKeyChar();
				if(!(Character.isDigit(keyValue)||(keyValue==KeyEvent.VK_BACK_SPACE)||(keyValue==KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		textField.setBounds(61, 175, 431, 45);
		panel.add(textField);
		textField.setColumns(10);
	
		
		JButton btnValidateBillNo = new JButton("Validate Bill No");
		btnValidateBillNo.addKeyListener(new KeyAdapter() {
	
		});
		btnValidateBillNo.addMouseListener(new MouseAdapter() {
		});
		btnValidateBillNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnValidateBillNoActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnValidateBillNo.setForeground(new Color(255, 255, 255));
		btnValidateBillNo.setBackground(new Color(0, 177, 157));
		btnValidateBillNo.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		btnValidateBillNo.setBounds(152, 412, 236, 66);
		panel.add(btnValidateBillNo);
		
	}
	

}
