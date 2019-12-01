package com.perisic.beds.gui;
/**
 * Allow to update delete insert questions to question table 
 * 
 * @author Kavindi De Silva
 *
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManageQuestions extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextArea textArea;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JButton btnDelete;
	private JButton btnUpdate;
	private PreparedStatement pst;
	private PreparedStatement pst1;
	private TableColumnModel columnModel;
	private JButton btnClearAll;
	
//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageQuestions frame = new ManageQuestions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Method the get Values from the question table
	public void tableload() throws Exception {
		String qry="SELECT question_id as id, question from questiontable";
		ResultSet rst=DBMethods.getREsultSet(qry);
		
		table.setModel(DbUtils.resultSetToTableModel(rst));
	}
	
//Class Constructor	
	public ManageQuestions() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 495);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(42, 49, 66));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(320, 13, 360, 373);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				String id= table.getValueAt(row, 0).toString();
				textField.setText(id);
				
				try {
					String quiz= predefineMethods.getQuestionFromQID(id);
					textArea.setText(quiz);
					String ans=predefineMethods.getAnswerFromQID(id);
					
					if(ans.equals("1")) {
						comboBox.setSelectedItem("Yes, No Answers");
					}else if(ans.equals("2")) {
						comboBox.setSelectedItem("Satisfaction Answers");
					}else {
						comboBox.setSelectedItem("Agree, Disagree Answers");
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textArea.setBounds(12, 92, 296, 145);
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Question");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblNewLabel.setBounds(12, 68, 122, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblQuestionId = new JLabel("Question ID");
		lblQuestionId.setForeground(Color.WHITE);
		lblQuestionId.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblQuestionId.setBounds(12, 19, 122, 30);
		contentPane.add(lblQuestionId);
		
		textField = new JTextField();
		textField.setFont(new Font("Century Gothic", Font.BOLD, 16));
		textField.setBounds(146, 18, 116, 31);
		textField.setEditable(false);
		contentPane.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		comboBox.setBounds(12, 281, 296, 48);
		comboBox.addItem("Select Answer");
		comboBox.addItem("Yes, No Answers");
		comboBox.addItem("Satisfaction Answers");
		comboBox.addItem("Agree, Disagree Answers");
		contentPane.add(comboBox);
		
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setForeground(Color.WHITE);
		lblAnswer.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblAnswer.setBounds(12, 260, 122, 16);
		contentPane.add(lblAnswer);
		
		btnNewButton = new JButton("Insert");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int question_id = 0;
				try {
					question_id=1+predefineMethods.getMaxValueQuestion();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String question=textArea.getText();
				String answer=comboBox.getSelectedItem().toString();
				String answer_id=null;
				
				if(question.equals("")||answer.equals("Select Answer")) {
					JOptionPane.showMessageDialog(null,"Makes sure all fields are not empty before proceed");
				}else
				{
					if(answer.equals("Yes, No Answers")) {
						answer_id="1";
					}else if(answer.equals("Satisfaction Answers")) {
						answer_id="2";
					}else {
						answer_id="3";
					}
					
					String qry="INSERT INTO questiontable (question_id, question, answer_id) VALUES ('"+question_id+"', '"+question+"', '"+answer_id+"')";
					try {
						pst=DBMethods.prepare(qry);
						pst.execute();
						tableload();
						columnModel.getColumn(0).setPreferredWidth(0);
						columnModel.getColumn(1).setPreferredWidth(250);
						
						String sql="ALTER TABLE satisficationtable ADD question_"+(question_id-1)+" VARCHAR(100) NULL DEFAULT NULL";
						pst1=DBMethods.prepare(sql);
						pst1.execute();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
			}
		});
		btnNewButton.setBackground(new Color(0, 177, 157));
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 17));
		btnNewButton.setBounds(213, 394, 95, 41);
		contentPane.add(btnNewButton);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qID=textField.getText();
				if(qID.equals("")) {
					JOptionPane.showMessageDialog(null,"Please Select the question to be Deleted");
				}else
				{
					int x= JOptionPane.showConfirmDialog(null, "Do you want to Delete this");
					
					if(x==0) {

						String sql="DELETE FROM questiontable WHERE question_id ="+qID;
						try {
							pst=DBMethods.prepare(sql);
							pst.execute();
							tableload();
							columnModel.getColumn(0).setPreferredWidth(0);
							columnModel.getColumn(1).setPreferredWidth(250);
							int id=Integer.parseInt(qID);
							
								String qry="ALTER TABLE satisficationtable DROP question_"+(id-1);
								pst1=DBMethods.prepare(qry);
								pst1.execute();
								
								textField.setText("");
								textArea.setText("");
								comboBox.setSelectedItem("Select Answer");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
				}
			}
		});
		btnDelete.setBackground(new Color(0, 177, 157));
		btnDelete.setFont(new Font("Century Gothic", Font.BOLD, 17));
		btnDelete.setBounds(113, 394, 95, 41);
		contentPane.add(btnDelete);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String qID=textField.getText();
				String question=textArea.getText();
				if(qID.equals("")) {
					JOptionPane.showMessageDialog(null,"Plese Select the question to be updated");
				}else
				{
					int x= JOptionPane.showConfirmDialog(null, "Do you want to update this");
					
					if(x==0) {

						String qry="UPDATE questiontable set question ='"+question+"' WHERE question_id="+qID;
						try {
							pst=DBMethods.prepare(qry);
							pst.execute();
							tableload();
							columnModel.getColumn(0).setPreferredWidth(0);
							columnModel.getColumn(1).setPreferredWidth(250);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
				}
			}
		});
		btnUpdate.setBackground(new Color(0, 177, 157));
		btnUpdate.setFont(new Font("Century Gothic", Font.BOLD, 17));
		btnUpdate.setBounds(12, 394, 95, 41);
		contentPane.add(btnUpdate);
		
		btnClearAll = new JButton("Clear All Fields");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea.setText("");
				comboBox.setSelectedItem("Select Answer");
			}
		});
		btnClearAll.setFont(new Font("Century Gothic", Font.BOLD, 17));
		btnClearAll.setBackground(new Color(0, 177, 157));
		btnClearAll.setBounds(320, 394, 360, 41);
		contentPane.add(btnClearAll);
		columnModel = table.getColumnModel();
		

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
