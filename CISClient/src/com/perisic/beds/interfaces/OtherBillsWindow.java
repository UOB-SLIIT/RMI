 package com.perisic.beds.interfaces;
 /**
  * In this GUI it displays the details of all bills over rs.15000 of the logged client
  * 
  * @author Nuwantha Fernando
  *
  */
 
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import com.perisic.beds.others.BillDetails;
import com.perisic.beds.others.UserDetails;
import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OtherBillsWindow extends JInternalFrame {
	private JTable table;
	private RemoteQuestions connection;
	private JTextField billNotextField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private UserDetails user1= new UserDetails();

//Main method of the class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OtherBillsWindow frame = new OtherBillsWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Class constructor
	public OtherBillsWindow() throws Exception {
		connection = RemoteConnection.remoteConnect();
		user1=connection.getDeserializedUserDetails(); 
		getContentPane().setBackground(new Color(0, 177, 157));
		setBounds(100, 100, 680, 455);
		getContentPane().setLayout(null);
		
		billNotextField = new JTextField();
		billNotextField.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		billNotextField.setBounds(128, 16, 181, 28);
		billNotextField.setEditable(false);
		getContentPane().add(billNotextField);
		billNotextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(378, 13, 274, 406);
		getContentPane().add(scrollPane);
		

		
		String array[]=connection.billNoArray(user1.getUser_nic());
		Object columns[]= {"Bill No"};
		Object rows[][]=new Object[array.length][1];
		
		for(int x=0;x<array.length;x++) {
			rows[x][0]=array[x];
		}
		
		table = new JTable(rows,columns);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String id= table.getValueAt(row, 0).toString();
				billNotextField.setText(id);
				BillDetails ob1=new BillDetails();
				try {
					ob1=connection.getBillDetailsFromBillNumber(id);
					textField_1.setText(ob1.getPurchasedDate());
					textField_2.setText(ob1.getPurchasedTime());
					textField_3.setText(Double.toString(ob1.getBillcost()));
					String branch=connection.getBranchLocation(ob1.getBranchCode());
					textField_4.setText(branch);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		table.setFont(new Font("Century Gothic", Font.PLAIN, 15));

		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 17));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setBackground(new Color(42, 49, 66));
		scrollPane.setViewportView(table);
		
		JLabel lblBillNo = new JLabel("Bill no");
		lblBillNo.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblBillNo.setBounds(23, 13, 84, 29);
		getContentPane().add(lblBillNo);
		
		
		
		JLabel lblPurchasedDate = new JLabel("Purchased Date");
		lblPurchasedDate.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblPurchasedDate.setBounds(23, 55, 141, 29);
		getContentPane().add(lblPurchasedDate);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_1.setColumns(10);
		textField_1.setBounds(185, 57, 181, 28);
		textField_1.setEditable(false);
		getContentPane().add(textField_1);
		
		JLabel lblPurchasedTime = new JLabel("Purchased Time");
		lblPurchasedTime.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblPurchasedTime.setBounds(23, 97, 141, 29);
		getContentPane().add(lblPurchasedTime);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_2.setColumns(10);
		textField_2.setBounds(185, 99, 181, 28);
		textField_2.setEditable(false);
		getContentPane().add(textField_2);
		
		JLabel lblCost = new JLabel("Cost");
		lblCost.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblCost.setBounds(23, 139, 141, 29);
		getContentPane().add(lblCost);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_3.setColumns(10);
		textField_3.setBounds(112, 140, 181, 28);
		textField_3.setEditable(false);
		getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		textField_4.setColumns(10);
		textField_4.setBounds(140, 182, 181, 28);
		textField_4.setEditable(false);
		getContentPane().add(textField_4);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblBranch.setBounds(23, 181, 141, 29);
		getContentPane().add(lblBranch);
	}
}
