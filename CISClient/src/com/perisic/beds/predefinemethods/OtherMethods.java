package com.perisic.beds.predefinemethods;
/**
 * Contains Client Side Methods
 * 
 * @author Nuwantha Fernando
 *
 */

import javax.swing.JTextField;
import com.perisic.beds.rmiinterface.RemoteQuestions;

public class OtherMethods {

// Method to update progress bar getting the count of the questions and the number of the questions answered
	public static void updatePogressBar(JTextField textField, int nic) throws Exception {
		RemoteQuestions connection= RemoteConnection.remoteConnect();
		double progress=0.0;
		double temp=0.0;
		progress=connection.getWiningProgree(nic);
		temp=Math.floor(progress * 100);
		temp= temp /100;
		
		if(temp > 99.00) {
			temp=100.00;
		}
		textField.setText(Double.toString(temp));
		
	}
}
