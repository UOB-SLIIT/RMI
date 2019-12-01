package com.perisic.beds.predefinemethods;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.perisic.beds.rmiinterface.RemoteQuestions;

public class testCon {

	public static void main(String[] args) throws Exception {
		
		RemoteQuestions connection;
		try {
			connection = RemoteConnection.remoteConnect();
			String returnValue = connection.testConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
