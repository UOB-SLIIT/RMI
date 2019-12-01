package com.perisic.beds.interfaces;
/**
 * Main window of the system.
 * All the components and operations are done in here.
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.InternalFrameUI;
import com.perisic.beds.barChart.BarChartSample;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.predefinemethods.OtherMethods;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.questionnaire.QuestionSet;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import javafx.application.Application;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class MainWindow extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private RemoteQuestions connection;
	private QuestionSet questionnaire = new QuestionSet(); 
	private UserDetails user1= new UserDetails();
	private boolean completenessValue;
	private String selectionValue;
	
//Main method of the system
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class constructor
	public MainWindow() throws NumberFormatException, Exception {
		
		connection=RemoteConnection.remoteConnect();
		user1=connection.getDeserializedUserDetails(); 
		completenessValue=connection.getCompletenessValue(user1.getUser_nic());
		selectionValue=connection.readSelectionFile();
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(42, 49, 66));
		panel.setBounds(0, 56, 221, 447);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 177, 157));
		panel_1.setBounds(0, 0, 883, 55);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(488, 5, 279, 46);
		panel_2.setBackground(new Color(0, 177, 157));
		panel_2.setVisible(true);
		
		JLabel label_3 = new JLabel("%");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 21));
		label_3.setBounds(744, 9, 23, 42);
		panel_1.add(label_3);
		
		textField = new JTextField();
		textField.setFont(new Font("Century Gothic", Font.BOLD, 17));
		textField.setBounds(679, 17, 88, 29);
		textField.setEditable(false);
		panel_1.add(textField);
		textField.setColumns(10);;
		OtherMethods.updatePogressBar(textField, Integer.parseInt(user1.getUser_nic()));
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(42, 49, 66));
		desktopPane.setBounds(219, 56, 664, 447);
		contentPane.add(desktopPane);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\salit\\Desktop\\CIS Project\\CISClient\\src\\images\\destopPaneBackground1.jpg"));
		lblNewLabel.setBounds(0, 0, 704, 447);
		desktopPane.add(lblNewLabel);
		
		JLabel label = new JLabel("MAIN");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Calibri", Font.BOLD, 15));
		label.setBounds(12, 52, 74, 36);
		panel.add(label);
		
		JButton button = new JButton("Home ");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setBackground(new Color(0, 177, 157));
			} 
			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(42, 49, 66));
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OtherMethods.updatePogressBar(textField, Integer.parseInt(user1.getUser_nic()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblNewLabel.setVisible(false);
				HomeWindow home= new HomeWindow();
				desktopPane.add(home).setVisible(true);
				Dimension desktopSize = desktopPane.getSize();
				Dimension jInternalFrameSize = home.getSize();
				home.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
				    (desktopSize.height- jInternalFrameSize.height)/2);
				InternalFrameUI ui = home.getUI();
	            ((javax.swing.plaf.basic.BasicInternalFrameUI)ui).setNorthPane(null ); 
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		button.setBackground(new Color(42, 49, 66));
		button.setBounds(12, 80, 197, 42);
		button.setBorder(emptyBorder);
		panel.add(button);
		
		JButton button_1 = new JButton("View Profile");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OtherMethods.updatePogressBar(textField, Integer.parseInt(user1.getUser_nic()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblNewLabel.setVisible(false);
				ProfileWindow profile = null;
				try {
					profile = new ProfileWindow();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				desktopPane.add(profile).setVisible(true);
				Dimension desktopSize = desktopPane.getSize();
				Dimension jInternalFrameSize = profile.getSize();
				profile.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
				    (desktopSize.height- jInternalFrameSize.height)/2);
				InternalFrameUI ui = profile.getUI();
	            ((javax.swing.plaf.basic.BasicInternalFrameUI)ui).setNorthPane(null );  
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button_1.setBackground(new Color(0, 177, 157));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button_1.setBackground(new Color(42, 49, 66));
			}
		});
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		button_1.setBackground(new Color(42, 49, 66));
		button_1.setBounds(12, 124, 197, 42);
		button_1.setBorder(emptyBorder);
		panel.add(button_1);
		
		JButton button_2 = new JButton("Start Quiz");
		if(completenessValue==true) {
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						OtherMethods.updatePogressBar(textField, Integer.parseInt(user1.getUser_nic()));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,"Already Submitted");
					
				}
				
			});
		}else {
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						OtherMethods.updatePogressBar(textField, Integer.parseInt(user1.getUser_nic()));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					lblNewLabel.setVisible(false);
					QuizWindow quiz;
					try {
						quiz = new QuizWindow();
						desktopPane.add(quiz).setVisible(true);
						Dimension desktopSize = desktopPane.getSize();
						Dimension jInternalFrameSize = quiz.getSize();
						quiz.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						    (desktopSize.height- jInternalFrameSize.height)/2);
						InternalFrameUI ui = quiz.getUI();
			            ((javax.swing.plaf.basic.BasicInternalFrameUI)ui).setNorthPane(null ); 
					} catch (Exception e) {
						
						e.printStackTrace();
					}
	
				}
			});
		}

		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button_2.setBackground(new Color(0, 177, 157));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button_2.setBackground(new Color(42, 49, 66));
			}
		});
		button_2.setForeground(Color.WHITE);
		button_2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		button_2.setBackground(new Color(42, 49, 66));
		button_2.setBounds(12, 168, 197, 42);
		button_2.setBorder(emptyBorder);
		panel.add(button_2);
		
		JButton button_3 = new JButton("Analyze Answers");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OtherMethods.updatePogressBar(textField, Integer.parseInt(user1.getUser_nic()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BarChartSample b1= new BarChartSample();
				try {
					Application.launch(b1.getClass());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button_3.setBackground(new Color(0, 177, 157));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button_3.setBackground(new Color(42, 49, 66));
			}
		});
		button_3.setForeground(Color.WHITE);
		button_3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		button_3.setBackground(new Color(42, 49, 66));
		button_3.setBounds(12, 212, 197, 42);
		button_3.setBorder(emptyBorder);
		panel.add(button_3);
		
		JButton btnViewOtherBills = new JButton("View Other Bills");
		btnViewOtherBills.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnViewOtherBills.setBackground(new Color(0, 177, 157));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
				btnViewOtherBills.setBackground(new Color(42, 49, 66));
			}
			
	
		});
		btnViewOtherBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				OtherBillsWindow bill1;
				try {
					bill1 = new OtherBillsWindow();
					desktopPane.add(bill1).setVisible(true);
					Dimension desktopSize = desktopPane.getSize();
					Dimension jInternalFrameSize = bill1.getSize();
					bill1.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
					    (desktopSize.height- jInternalFrameSize.height)/2);
					InternalFrameUI ui = bill1.getUI();
		            ((javax.swing.plaf.basic.BasicInternalFrameUI)ui).setNorthPane(null ); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnViewOtherBills.setForeground(Color.WHITE);
		btnViewOtherBills.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnViewOtherBills.setBackground(new Color(42, 49, 66));
		btnViewOtherBills.setBounds(12, 257, 197, 42);
		panel.add(btnViewOtherBills);
		
		JButton btnViewWinner = new JButton("View Winner");
		btnViewWinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String winner[]=connection.getWinnerDetails();
					JOptionPane.showMessageDialog(null,"Winner's Bill Number :    "+winner[0]+"\n"
							+ "Winner name :    "+winner[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		btnViewWinner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnViewWinner.setBackground(new Color(0, 177, 157));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
				btnViewWinner.setBackground(new Color(42, 49, 66));
			}
		});
		btnViewWinner.setForeground(Color.WHITE);
		btnViewWinner.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnViewWinner.setBackground(new Color(42, 49, 66));
		btnViewWinner.setBounds(12, 302, 197, 42);
		panel.add(btnViewWinner);

		JLabel label_2 = new JLabel("ABC Super");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("mellony dry brush", Font.BOLD, 23));
		label_2.setBounds(65, 0, 162, 65);
		panel_1.add(label_2);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setIcon(new ImageIcon("/CISClient/src/images/logo.jpg"));
		label_1.setBounds(12, 0, 56, 55);
		panel_1.add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel("Winning Progress");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 17));
			
		lblNewLabel_1.setBounds(534, 13, 150, 33);
		panel_1.add(lblNewLabel_1);
		
		String temp="";
		for(int x=0; x<selectionValue.length();x++) {
			if(Character.isLetter(selectionValue.charAt(x))) {
				temp+=selectionValue.charAt(x);
			}
		}
		
		if(!temp.equals("false")) {
			lblNewLabel_1.setVisible(false);
			textField.setVisible(false);
			label_3.setVisible(false);
			btnViewWinner.setVisible(true);
		}else
		{
			btnViewWinner.setVisible(false);
		}
		
		//lblNewLabel_1.setVisible(false);
		JLabel lblNewLabel_2 = new JLabel("Sign Out");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				LoginInterface l1=new LoginInterface();
				l1.setVisible(true);
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2.setForeground(new Color(50, 50, 50));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2.setForeground(Color.BLACK);
			}
		});
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 19));
		lblNewLabel_2.setBounds(779, 15, 104, 30);
		panel_1.add(lblNewLabel_2);
	}
}
