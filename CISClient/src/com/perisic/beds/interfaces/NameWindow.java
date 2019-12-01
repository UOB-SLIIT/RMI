package com.perisic.beds.interfaces;
/**
 * It displays the name of the client by getting bill number
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
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class NameWindow extends JFrame {
	private JPanel contentPane;
	private RemoteQuestions connection;
	private UserDetails user1= new UserDetails();
	private String selectionStatus;

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NameWindow frame = new NameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
//Class Constructor
	public NameWindow() throws MalformedURLException, RemoteException, NotBoundException, Exception {
		connection = RemoteConnection.remoteConnect();
		user1=connection.getDeserializedUserDetails(); 
		selectionStatus=connection.readSelectionFile();
		setBackground(new Color(0, 177, 157));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					MainWindow m1=new MainWindow();
					ProgressMessageWindow p1=new ProgressMessageWindow();
					
					String temp="";
					for(int x=0; x<selectionStatus.length();x++) {
						if(Character.isLetter(selectionStatus.charAt(x))) {
							temp+=selectionStatus.charAt(x);
						}
					}
					
					if(!temp.equals("false")) {
						int x= JOptionPane.showConfirmDialog(null, "Winner Selection is almost over, Do you wish to continue");
						if(x==0) {
							m1.setVisible(true);
						}else
						{
							System.exit(0);
						}
					}else
					{
						dispose();
						m1.setVisible(true);
						p1.setVisible(true);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.setBackground(new Color(0, 177, 157));
		panel.setBounds(5, 5, 873, 498);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hi <Name>",SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 99));
		lblNewLabel.setBounds(0, 102, 873, 154);
		panel.add(lblNewLabel);
		
		lblNewLabel.setText("Hi "+user1.getUser_firstname());
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to ABC raffle portal..",SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 36));
		lblNewLabel_1.setBounds(0, 217, 873, 87);
		panel.add(lblNewLabel_1);
		
		JLabel lblTapToContinue = new JLabel("Tap to Continue",SwingConstants.CENTER);
		lblTapToContinue.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblTapToContinue.setBounds(0, 375, 873, 37);
		panel.add(lblTapToContinue);
	}

}
