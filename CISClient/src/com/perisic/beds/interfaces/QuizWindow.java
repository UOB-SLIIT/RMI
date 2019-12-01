package com.perisic.beds.interfaces;
/**
 * Displays questionnaire which is provide by the server side and stores the given answers.
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.questionnaire.QuestionSet;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class QuizWindow extends JInternalFrame {
	private QuestionSet questionnaire = new QuestionSet(); 
	private JTextField textField;
	public RemoteQuestions connection;
	private UserDetails user1= new UserDetails();

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizWindow frame = new QuizWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class constructor
	public QuizWindow() throws Exception {
		connection = RemoteConnection.remoteConnect();
		user1=connection.getDeserializedUserDetails(); 
		setBounds(100, 100, 686, 480);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 177, 157));
		panel.setBounds(0, 0, 670, 469);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNext = new JButton("Start Quiz");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < questionnaire.numberOfQuestions(); i++) {
				String message = questionnaire.getQuestion(i);
				String [] options = questionnaire.getOptions(i); 

				JLabel label = new JLabel("");
				label.setFont(new Font("Century Gothic", Font.PLAIN, 20));
				label.setForeground(new Color(0, 177, 157));
				label.setText(message);
				
				int selectedValue =JOptionPane.showOptionDialog(null, label, "Question "+(i+1), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
				questionnaire.submitAnswer(i, options[selectedValue]);
				try {
					double questionScore=connection.getQuestionScore();
					
					connection=RemoteConnection.remoteConnect();
					connection.setClientSubmission(i, options[selectedValue], user1.getUser_nic());
					connection.updateWinningProgressValue(questionScore, user1.getUser_nic());	
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				}
				try {
					connection.setCompletenessValue(user1.getUser_nic());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MainWindow m1;
				try {
					m1 = new MainWindow();
					m1.setVisible(true);
					dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		});
		
		btnNext.setBackground(Color.WHITE);
		btnNext.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnNext.setBounds(247, 371, 182, 45);
		panel.add(btnNext);
		
		textField = new JTextField("Dear Customer,\n"
				+ "Kindly remind that you are able to\n"
				+ "submit answer for the each question once.\n\n"
				+ "Please be kind enough to give the answer inncently");
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 23));
		textField.setBackground(new Color(0, 177, 157));
		textField.setBounds(29, 31, 606, 319);
		panel.add(textField);
		textField.setColumns(10);
	}
}
