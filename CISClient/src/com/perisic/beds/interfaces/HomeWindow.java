 package com.perisic.beds.interfaces;
 /**
  * Internal frame of a main window
  * It displays some details of the ABC company.
  * 
  * @author Nuwantha Fernando
  *
  */
 
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class HomeWindow extends JInternalFrame {

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeWindow frame = new HomeWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class Constructor	
	public HomeWindow() {
		setBounds(100, 100, 680, 455);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\salit\\Desktop\\CIS Project\\CISClient\\src\\images\\homeBackground1.jpg"));
		lblNewLabel.setBounds(0, 0, 670, 450);
		getContentPane().add(lblNewLabel);

	}
}
