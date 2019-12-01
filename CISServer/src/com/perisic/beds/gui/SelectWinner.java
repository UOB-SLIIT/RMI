package com.perisic.beds.gui;
/**
 * Class showing the candidates table 
 * Ability to select the winner and send email to the winner
 * 
 * @author Kavindi De Silva
 *
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.TableColumnModel;
import com.perisic.beds.predefinemethods.DBMethods;
import com.perisic.beds.predefinemethods.predefineMethods;
import net.proteanit.sql.DbUtils;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectWinner extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private PreparedStatement pst;
	private PreparedStatement pst1;
	private TableColumnModel columnModel;
	private JLabel lblNewLabel;
	private String currentDate;
	private JButton btnNewButton;
	private JLabel lblSelectionDate;
	private String selectionDate;
	private JLabel lblSelectionPortal;
	private JTextArea textArea;
	private JLabel lblNewLabel_1;
	private JButton btnSelectWinner;
	private JPanel panel;
	private JLabel lblCandidates;
	
//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectWinner frame = new SelectWinner();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//fill the table with billinginfo and clientdata tables
	public void tableload() throws Exception {
		String qry="select b.bill_no,c.nic,c.name from billinfo b, clientdata c where b.nic=c.nic and c.winningPorgress >= 75";
		ResultSet rst=DBMethods.getREsultSet(qry);
		
		table.setModel(DbUtils.resultSetToTableModel(rst));
	}
	
//Class Constructor
	public SelectWinner() throws Exception {
		selectionDate=predefineMethods.readSelectionDateFile();
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 495);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(42, 49, 66));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(380, 35, 300, 400);
		contentPane.add(scrollPane);
		
		table = new JTable();
		
		scrollPane.setViewportView(table);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		currentDate=dtf.format(localDate);

		
		lblNewLabel = new JLabel("Today Date : "+ currentDate);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 17));
		lblNewLabel.setForeground(new Color(0, 177, 157));
		lblNewLabel.setBounds(23, 13, 228, 24);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("Change Selection Date");
		btnNewButton.setBackground(new Color(0, 177, 157));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean result=predefineMethods.adminPasswordVerification(predefineMethods.getAdminPassword());
					if(result) {
						
						String[] date= {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
					    String[] month= {"01","02","03","04","05","06","07","08","09","10","11","12"};
					    String[] year={"2018","2019"};
					    JComboBox jcd = new JComboBox(date);
					    JComboBox jcm = new JComboBox(month);
					    JComboBox jcy = new JComboBox(year);
					      jcd.setEditable(true);
					      jcm.setEditable(true);
					      jcy.setEditable(true);
					    JOptionPane.showMessageDialog( null, jcd, "Date", JOptionPane.QUESTION_MESSAGE);
					    JOptionPane.showMessageDialog( null, jcm, "Month", JOptionPane.QUESTION_MESSAGE);
					    JOptionPane.showMessageDialog( null, jcy, "Year", JOptionPane.QUESTION_MESSAGE);
					    
					    selectionDate=jcy.getSelectedItem().toString()+"/"+jcm.getSelectedItem().toString()+"/"+jcd.getSelectedItem().toString();
					    
					    String[] data = selectionDate.split("/", 3);
					    String[] data1 = currentDate.split("/", 3);
					    
					    int tempSelectionYear=Integer.parseInt(data[0]+data[1]+data[2]);
					    int tempCurrentYear=Integer.parseInt(data1[0]+data1[1]+data1[2]);
  
					    if(tempSelectionYear<tempCurrentYear) {  

					    	JOptionPane.showMessageDialog( null,"Invalid Date");
					    	selectionDate=predefineMethods.readSelectionDateFile();
					    }else {
					    	JOptionPane.showMessageDialog( null,"Selection date Changed Successfully");
					    	predefineMethods.writeSelectionDateFile(selectionDate);
					    }
					    	lblSelectionDate.setText("Selection Date : "+selectionDate);
					       
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnNewButton.setBounds(23, 70, 334, 31);
		contentPane.add(btnNewButton);

		lblSelectionDate = new JLabel("Selection Date : "+selectionDate);
		lblSelectionDate.setForeground(new Color(178, 34, 34));
		lblSelectionDate.setFont(new Font("Consolas", Font.PLAIN, 17));
		lblSelectionDate.setBounds(23, 39, 300, 24);
		contentPane.add(lblSelectionDate);
		
		panel = new JPanel();
		panel.setBounds(12, 114, 358, 321);
		panel.setBackground(new Color(45, 55, 80));
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblSelectionPortal = new JLabel("Selection Portal");
		lblSelectionPortal.setBounds(107, 5, 144, 21);
		lblSelectionPortal.setForeground(new Color(0, 177, 157));
		lblSelectionPortal.setFont(new Font("Consolas", Font.PLAIN, 17));
		panel.add(lblSelectionPortal);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
		textArea.setForeground(Color.WHITE);
		textArea.setBounds(12, 39, 334, 158);
		textArea.setBackground(new Color(42, 49, 66));
		panel.add(textArea);
		
		lblNewLabel_1 = new JLabel("winner");
		lblNewLabel_1.setForeground(new Color(0, 177, 157));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 39));
		lblNewLabel_1.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel_1.setVerticalAlignment(JLabel.CENTER);
		lblNewLabel_1.setBounds(0, 210, 346, 68);
		panel.add(lblNewLabel_1);
		
		btnSelectWinner = new JButton("Select Winner");
		btnSelectWinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String winner=null;
					String text="";
					String[] candidatesArray= predefineMethods.getCandidatesBillNumbers();
					Thread.sleep(3000);
					
					winner= candidatesArray[new Random().nextInt(candidatesArray.length)];
					
					lblNewLabel_1.setText(winner);
					
					String winner_name=predefineMethods.getName(winner);
					text="The winner for the 2019\n\n"
							+ "Bill No : "+winner+"\n\n"
									+ "Winner Name : "+winner_name;
					
					String winner_email=predefineMethods.getEmail(winner);
					System.out.println(winner_email);
					String email_subject="ABC Super Market Raffle";
					String email_message="Hi "+winner_name+",\n\n"
							+ "Congratulations! You have been drawn as a winner.\n\n"
							+ "We ABC Super are delighted to let you know that you were drawn as a winner in the 'ABC Raffle'!\n\n"
							+ "In order to confirm that you are eligible and for us to arrange for the prize to awarded, Please call withing 10 days."
							+ "If you not confirm the elibility in the time frame specified in the competition terms and conditions we will need to award the prize to another entrant.\n"
							+ "Best Regards,\n"
							+ "ABC Super,\n"
							+ "Management\n"
							+ "0115628795";
					if(winner_email.equals("")) {
						JOptionPane.showMessageDialog( null,"Email Value is empty on database, Cannot let the winner know via emails");
					}else {
						predefineMethods.sendEmail(email_subject, email_message, winner_email);
						System.out.println("Email Sent Successfully");
					}
					
					textArea.setText(text);
					if(!(winner==null)) {
						predefineMethods.setSelection("true");
						predefineMethods.storeWinnerValue(winner, winner_name);
					}

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnSelectWinner.setForeground(new Color(255, 255, 255));
		btnSelectWinner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnSelectWinner.setBackground(new Color(220, 20, 60));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSelectWinner.setBackground(new Color(42, 49, 66));
			}
		});
		btnSelectWinner.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnSelectWinner.setBackground(new Color(42, 49, 66));
		btnSelectWinner.setBounds(12, 277, 334, 44);
		panel.add(btnSelectWinner);
		
		lblCandidates = new JLabel("Candidates");
		lblCandidates.setForeground(new Color(0, 177, 157));
		lblCandidates.setFont(new Font("Consolas", Font.PLAIN, 17));
		lblCandidates.setBounds(487, 13, 105, 24);
		contentPane.add(lblCandidates);
		columnModel = table.getColumnModel();
		
		boolean result= predefineMethods.verifySelectionDateValidity();
		
	
		if(result) {
			panel.setVisible(false);
		}else {
			panel.setVisible(true);
		}
		
		
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 17));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setBackground(new Color(42, 49, 66));
		
		try {
			tableload();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		columnModel.getColumn(0).setPreferredWidth(0);
		columnModel.getColumn(1).setPreferredWidth(250);
	}
}
