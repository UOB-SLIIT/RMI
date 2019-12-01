package com.perisic.beds.interfaces;
/**
 * In this GUI it allow the user to edit private details
 * It displays bill informations
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import com.perisic.beds.others.BillDetails;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfileWindow extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public RemoteQuestions connection;
	private UserDetails user1= new UserDetails();
	private String location;
	private String cashierID;
	private String cashierName;
	private String managerName;
	private String managerID;
	private UserDetails user=new UserDetails();

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfileWindow frame = new ProfileWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class constructor
	public ProfileWindow() throws Exception {
		connection=RemoteConnection.remoteConnect();
		user1=connection.getDeserializedUserDetails(); 
		BillDetails ob1= new BillDetails();
		ob1=connection.getBillDetails(user1.getUser_nic());
		location=connection.getBranchLocation(ob1.getBranchCode());
		cashierID=connection.getEmployeeIDFromBillNo(ob1.getBillNo(),"cashier");
		managerID=connection.getEmployeeIDFromBillNo(ob1.getBillNo(),"manager");
		cashierName=connection.getEmployeeNameFromEmpID(cashierID);
		managerName=connection.getEmployeeNameFromEmpID(managerID);
		user=connection.getUserDetailsFromNIC(user1.getUser_nic());
		
		setBounds(100, 100, 686, 485);
		setBounds(100, 100, 686, 455);
		getContentPane().setLayout(null);
		
		JLabel lblBillNo = new JLabel("Bill No");
		lblBillNo.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblBillNo.setBounds(31, 13, 56, 29);
		getContentPane().add(lblBillNo);
		
		JLabel lblBranchCode = new JLabel("Branch Code");
		lblBranchCode.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblBranchCode.setBounds(31, 105, 108, 29);
		getContentPane().add(lblBranchCode);
		
		JLabel lblBranchLocation = new JLabel("Branch Location");
		lblBranchLocation.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblBranchLocation.setBounds(31, 58, 151, 29);
		getContentPane().add(lblBranchLocation);
		
		JLabel lblBranchManager = new JLabel("Branch Manager ID");
		lblBranchManager.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblBranchManager.setBounds(337, 148, 174, 29);
		getContentPane().add(lblBranchManager);
		
		JLabel lblPurchasedDate = new JLabel("Purchased Date");
		lblPurchasedDate.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblPurchasedDate.setBounds(31, 148, 151, 29);
		getContentPane().add(lblPurchasedDate);
		
		JLabel lblPurchasedTime = new JLabel("Purchased Time");
		lblPurchasedTime.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblPurchasedTime.setBounds(31, 190, 151, 29);
		getContentPane().add(lblPurchasedTime);
		
		JLabel lblCostOfThe = new JLabel("Cost of the Bill");
		lblCostOfThe.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblCostOfThe.setBounds(337, 13, 151, 29);
		getContentPane().add(lblCostOfThe);
		
		JLabel lblCashierName = new JLabel("Cashier ID");
		lblCashierName.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblCashierName.setBounds(337, 58, 151, 29);
		getContentPane().add(lblCashierName);
		
		JLabel lblCashierName_1 = new JLabel("Cashier Name");
		lblCashierName_1.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblCashierName_1.setBounds(337, 105, 151, 29);
		getContentPane().add(lblCashierName_1);
		
		JLabel lblEnterName = new JLabel("Name");
		lblEnterName.setForeground(Color.WHITE);
		lblEnterName.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblEnterName.setBounds(26, 257, 95, 29);
		getContentPane().add(lblEnterName);
		
		textField = new JTextField();
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField.setBounds(143, 257, 188, 34);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel billNo = new JLabel("<Bill No>");
		billNo.setForeground(new Color(0, 0, 128));
		billNo.setFont(new Font("Century Gothic", Font.BOLD, 17));
		billNo.setBounds(97, 13, 188, 29);
		getContentPane().add(billNo);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setForeground(Color.WHITE);
		lblAddress.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblAddress.setBounds(26, 300, 95, 29);
		getContentPane().add(lblAddress);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_1.setColumns(10);
		textField_1.setBounds(143, 300, 188, 34);
		getContentPane().add(textField_1);
		
		JLabel lblTelephoneNo = new JLabel("Phone No");
		lblTelephoneNo.setForeground(Color.WHITE);
		lblTelephoneNo.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblTelephoneNo.setBounds(26, 343, 115, 29);
		getContentPane().add(lblTelephoneNo);
		
		JLabel label = new JLabel("<Branch Location>");
		label.setForeground(new Color(0, 0, 128));
		label.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label.setBounds(183, 58, 142, 29);
		getContentPane().add(label);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_2.setColumns(10);
		textField_2.setBounds(143, 343, 188, 34);
		getContentPane().add(textField_2);
		
		JLabel label_2 = new JLabel("<Purchased Date>");
		label_2.setForeground(new Color(0, 0, 128));
		label_2.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_2.setBounds(183, 148, 151, 29);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("<Purchased Time>");
		label_3.setForeground(new Color(0, 0, 128));
		label_3.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_3.setBounds(180, 190, 151, 29);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("<Cost >");
		label_4.setForeground(new Color(0, 0, 128));
		label_4.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_4.setBounds(473, 13, 151, 29);
		getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("<Cashier ID>");
		label_5.setForeground(new Color(0, 0, 128));
		label_5.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_5.setBounds(441, 58, 151, 29);
		getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("<Cashier Name>");
		label_6.setForeground(new Color(0, 0, 128));
		label_6.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_6.setBounds(473, 105, 151, 29);
		getContentPane().add(label_6);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblEmail.setBounds(26, 385, 115, 29);
		getContentPane().add(lblEmail);
		
		JLabel label_7 = new JLabel("<Branch Manager>");
		label_7.setForeground(new Color(0, 0, 128));
		label_7.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_7.setBounds(523, 148, 174, 29);
		getContentPane().add(label_7);
		
		JLabel label_1 = new JLabel("<Branch Code>");
		label_1.setForeground(new Color(0, 0, 128));
		label_1.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_1.setBounds(159, 105, 135, 29);
		getContentPane().add(label_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_3.setColumns(10);
		textField_3.setBounds(143, 385, 188, 34);
		getContentPane().add(textField_3);
		
		JLabel lblBranchManagerId = new JLabel("Branch Manager ");
		lblBranchManagerId.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblBranchManagerId.setBounds(337, 190, 174, 29);
		getContentPane().add(lblBranchManagerId);
		
		JLabel label_8 = new JLabel("<Branch Manager ID>");
		label_8.setForeground(new Color(0, 0, 128));
		label_8.setFont(new Font("Century Gothic", Font.BOLD, 17));
		label_8.setBounds(484, 190, 174, 29);
		getContentPane().add(label_8);
		
		JLabel lblFeedback = new JLabel("Feedback");
		lblFeedback.setForeground(Color.WHITE);
		lblFeedback.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblFeedback.setBounds(352, 257, 95, 29);
		getContentPane().add(lblFeedback);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textArea.setBounds(353, 284, 282, 73);
		getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=textField.getText().toString();
				String address=textField_1.getText().toString();
				String phoneNo=textField_2.getText().toString();
				String email=textField_3.getText().toString();
				String feedback=textArea.getText().toString();
				
				if(name.equals("")||address.equals("")||phoneNo.equals("")||email.equals("")||feedback.equals("")) {
					JOptionPane.showMessageDialog(null,"cannot Proceed with empty Fields.");
				}else {
					try {
						connection.updateUser(user1.getUser_nic(), name, address, phoneNo, email, feedback);
						JOptionPane.showMessageDialog(null,"UserDetails Successfully Updated");
						connection.getSerializedUserDetails(user1.getUser_billNo());
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnNewButton.setBounds(353, 365, 135, 53);
		btnNewButton.setBackground(Color.WHITE);
		getContentPane().add(btnNewButton);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textArea.setText("");
			}
		});
		
		btnClear.setForeground(Color.WHITE);
		btnClear.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnClear.setBounds(500, 365, 135, 53);
		btnClear.setBackground(new Color(220, 20, 60));
		getContentPane().add(btnClear);
		
		JLabel lblNewLabel = new JLabel("Customer Name");
		lblNewLabel.setIcon(new ImageIcon("/CISClient/src/images/profileWindow.jpg"));
		lblNewLabel.setBounds(0, -25, 670, 503);
		getContentPane().add(lblNewLabel);
		
		//Set Label Values according to Bill Details
		billNo.setText(ob1.getBillNo());
		label_4.setText(Double.toString(ob1.getBillcost()));
		label_1.setText(ob1.getBranchCode());
		label_2.setText(ob1.getPurchasedDate());
		label_3.setText(ob1.getPurchasedTime());
		label.setText(location);
		label_5.setText(cashierID);
		label_6.setText(cashierName);
		label_7.setText(managerID);
		label_8.setText(managerName);
		textField.setText(user.getUser_firstname()+" "+user.getUser_lastname());
		textField_1.setText(user.getUser_address());
		textField_2.setText(Long.toString(user.getUser_phoneNo()));
		textField_3.setText(user.getUser_email());
		textArea.setText(user.getUser_feedback());
	}
}
