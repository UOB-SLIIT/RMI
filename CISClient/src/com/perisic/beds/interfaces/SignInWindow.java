package com.perisic.beds.interfaces;
/**
 * it validates the provided user credential and deirects to the main window if corrects
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignInWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private RemoteQuestions connection;
	private UserDetails user1= new UserDetails();

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInWindow frame = new SignInWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class Constructor
	public SignInWindow() throws Exception{
		connection = RemoteConnection.remoteConnect();
		user1=connection.getDeserializedUserDetails();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 177, 157));
		panel.setBounds(0, 0, 882, 82);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sign In",SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
		lblNewLabel.setBounds(0, 13, 882, 55);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(-16, 81, 898, 422);
		panel.add(panel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		textField.setBounds(176, 142, 517, 51);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField();
		textField_1.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		textField_1.setColumns(10);
		textField_1.setBounds(176, 236, 517, 51);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1 = new JLabel("USERNAME");
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_1.setBounds(176, 113, 107, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 18));
		lblPassword.setBounds(176, 206, 107, 29);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Sign In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String username=textField.getText().toString();
					String password=textField_1.getText().toString();
					connection = RemoteConnection.remoteConnect();
					String getPass=connection.getPasswordFromUsername(username);
					String getSalt=connection.getSaltFromUsername(username);
					
					boolean passwordMatch=connection.verfiySaltedPassword(password, getPass, getSalt);
					
					if(passwordMatch) {
						NameWindow m1;
					try {
						m1 = new NameWindow();
						m1.setVisible(true);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}else {
						JOptionPane.showMessageDialog(null,"Password Not Match");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setBackground(new Color(90, 215, 200));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(0, 177, 157));
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnNewButton.setBounds(176, 423, 258, 56);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 177, 157));
		contentPane.add(btnNewButton);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				textField_1.setText("");
			}
		});
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClear.setBackground(new Color(90, 215, 200));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btnClear.setBackground(new Color(0, 177, 157));
			}
		});
		btnClear.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnClear.setBounds(435, 423, 258, 56);
		btnClear.setForeground(new Color(255, 255, 255));
		btnClear.setBackground(new Color(0, 177, 157));
		contentPane.add(btnClear);
		
		JLabel lblForgetPassword = new JLabel("Forgot Password");
		lblForgetPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblForgetPassword.setForeground(new Color(10, 187, 167));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblForgetPassword.setForeground(new Color(0, 177, 157));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String randomNumberGenerated = String.format("%04d", new Random().nextInt(10000));

				try {
					 
					String email1 = user1.getUser_email();
					System.out.println(email1);
					String subject="ABC Raffle account Security code";
					
					String message= "Security Code\n\n"
							+ "Please use the following security code for the ABC Raffle portal Account\n\n"
							+ "Security Code :"+randomNumberGenerated+"\n\n"
							+ "Best Regards,\n"
							+ "ABC Super,\n"
							+ "Management\n"
							+ "0115628795";
					
					try {
						connection.sendEmail(subject, message, email1);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					String digitValue = JOptionPane.showInputDialog(null, "Please Enter the 4 digit code which sent to the "+email1);
					
					if(digitValue.equals(randomNumberGenerated)) {
						ResetPasswordWindow r1= new ResetPasswordWindow();
						r1.setVisible(true);
					}else
					{
						JOptionPane.showMessageDialog(null,"Sorry, Enterd Value Incorrect");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			
		});
		lblForgetPassword.setForeground(new Color(0, 177, 157));
		lblForgetPassword.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblForgetPassword.setBounds(176, 300, 151, 29);
		contentPane.add(lblForgetPassword);
	}
}
