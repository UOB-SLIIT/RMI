package com.perisic.beds.interfaces;
/**
 * allow to reset the password 
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResetPasswordWindow extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private RemoteQuestions connection;
	private UserDetails user1= new UserDetails();
	int timeCount=0;

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPasswordWindow frame = new ResetPasswordWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class Constructor
	public ResetPasswordWindow() {
		
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
		
		JLabel lblNewLabel = new JLabel("Reset Password",SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
		lblNewLabel.setBounds(0, 13, 882, 55);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(-16, 81, 898, 422);
		panel.add(panel_1);
		
		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(timeCount==0) {
					JOptionPane.showMessageDialog(null,"Password must have an Uppercase letters, lowercase letters and numbers");
				}
				
			}
		});
		passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		passwordField.setBounds(176, 236, 517, 51);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 18));
		lblPassword.setBounds(176, 206, 107, 29);
		contentPane.add(lblPassword);
		
		JLabel lblRenterPassword = new JLabel("RE-ENTER PASSWORD");
		lblRenterPassword.setFont(new Font("Calibri", Font.BOLD, 18));
		lblRenterPassword.setBounds(176, 300, 177, 29);
		contentPane.add(lblRenterPassword);
		
		JButton btnNewButton = new JButton("Reset Password");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String password=passwordField.getText().toString();
					String password1=passwordField_1.getText().toString();
					
					if(!password.equals(password1)) {
						JOptionPane.showMessageDialog(null,"Passowrds Incorrect");
						passwordField.setText("");
						passwordField_1.setText("");
					}else {
						connection=RemoteConnection.remoteConnect();
						user1=connection.getDeserializedUserDetails(); 
						connection.resetPassword(password, user1.getUser_nic());
						JOptionPane.showMessageDialog(null,"Password Reset Successfully");
						NameWindow n1= new NameWindow();
						n1.setVisible(true);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
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
				passwordField.setText("");
				passwordField_1.setText("");
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
		
		passwordField_1 = new JPasswordField();
		passwordField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				timeCount++;
				String password=passwordField.getText();
				if(password.equals("")) {
					JOptionPane.showMessageDialog(null,"Password Field is Empty");
				}else
				{
					if(password.length()<5) {
						JOptionPane.showMessageDialog(null,"Passowrd is too Short");
					}else
					{
						if(password.length()>15) {
							JOptionPane.showMessageDialog(null,"Passowrd is too Large");
						}else
						{
							int upper=0;
							int lower=0;
							int symbol=0;
							int number=0;
							int spaceOrTab=0;
							int passLength=password.length();
							
							for(int x=0;x<passLength;x++) {
								Character ch=password.charAt(x);
								if(Character.isWhitespace(ch)) {
									spaceOrTab++;
								}
								else {
									if(Character.isDigit(ch)) {
										number++;
									}else {
										if(Character.isUpperCase(ch)){
											upper++;
										}
										else {
											if(Character.isLowerCase(ch)) {
												lower++;
											}else {
												symbol++;
											}
										}
									}
									
								}
									
							}
							
							if(!(spaceOrTab==0)) {
								timeCount++;
								JOptionPane.showMessageDialog(null,"Passowrd Contains Space Character");
								
								passwordField.setText("");
							}else {
								if(number==0) {
									timeCount++;
									JOptionPane.showMessageDialog(null,"Passowrd must contains a digit");
									passwordField.setText("");
									
								}
								else
								{
									if(upper==0) {
										timeCount++;
										JOptionPane.showMessageDialog(null,"Passowrd must contains an Uppercase Character");
										passwordField.setText("");
										
									}
									else
									{
										if(lower==0) {
											timeCount++;
											JOptionPane.showMessageDialog(null,"Passowrd must contains an Lowercase Character");
											passwordField.setText("");
											
										}else
										{
											timeCount++;
											
											
										}
									}
								}
							}
						}
					}
				}
			}
		});
		passwordField_1.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		passwordField_1.setBounds(176, 329, 517, 51);
		contentPane.add(passwordField_1);
	}
}
