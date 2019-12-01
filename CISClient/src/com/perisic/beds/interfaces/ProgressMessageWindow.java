package com.perisic.beds.interfaces;
/**
 * Displays some guidance to get into the candidate list
 * 
 * @author Nuwantha Fernando
 *
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ProgressMessageWindow extends JFrame {

	private JPanel contentPane;

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgressMessageWindow frame = new ProgressMessageWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class Constructor
	public ProgressMessageWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initComponents();
	}
	
// Initialize the contents of the frame.
	private void initComponents() {
		
		JLabel lblNewLabel = new JLabel("l");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\salit\\Desktop\\CIS Project\\CISClient\\src\\images\\progressWindowBackground1.jpg"));
		lblNewLabel.setBounds(0, 0, 405, 246);
		contentPane.add(lblNewLabel);
	}
}
