package loginPage;

import java.awt.Color;  
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import welcomePages.WelcomePage;
import welcomePages.WelcomePageWorker;

public class LoginPage  implements ActionListener {

	
	JFrame frame = new JFrame("Login");
	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JTextField userIDField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIDLabel = new JLabel("User ID:");
	JLabel userPasswordLabel = new JLabel("Password:");
	JLabel messageLabel = new JLabel();
	// frame de bulunacak componentlar

	HashMap<String,String> logininfo = new HashMap<String, String>();
	
	LoginPage(HashMap<String,String> loginInfoOriginal){
		
		logininfo = loginInfoOriginal;
		
		userIDLabel.setBounds(50, 100, 75, 25);
		userPasswordLabel.setBounds(50, 150, 75, 25);
		
		messageLabel.setBounds(125, 250, 250, 35);
		messageLabel.setFont(new Font(null,Font.ITALIC,25));
		
		userIDField.setBounds(125,100,200,25);
		userPasswordField.setBounds(125,150,200,25);
		
		loginButton.setBounds(125,200,100,25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		resetButton.setBounds(225,200,100,25);
	    resetButton.setFocusable(false);
		resetButton.addActionListener(this);
	
		frame.add(userIDLabel);
		frame.add(userPasswordLabel);
		frame.add(messageLabel);
		frame.add(userIDField);
		frame.add(userPasswordField);
		frame.add(loginButton);
		frame.add(resetButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	// frame de bulunacak componentların ayarlanması ve sayafaya eklenmesi

	public void actionPerformed(ActionEvent e) {	
		if(e.getSource()==resetButton) {
			userIDField.setText("");
			userPasswordField.setText("");
		}
// reset button için fieldların boşa çekilmesi
		Connection connection = null;
		
		try {
		    Class.forName("org.postgresql.Driver");
		    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
		    Statement stmt = connection.createStatement();
		    String customerQuery = "SELECT * from customers where customer_tax_number='" + userIDField.getText().toString() + "' and customer_password='" + userPasswordField.getText().toString() + "'";
		    String workerQuery = "SELECT * from workers where worker_id='" + userIDField.getText().toString() + "' and worker_password='" + userPasswordField.getText().toString() + "'";
// en başta userID ile giriyordu customerler ama çakıştığı için unique olan tax numbera çektim onları
		    // database e bağlantı ve müşterilerin tax number ile girmesi ve çalışanların id ile girmesi
		    ResultSet customerResultSet = stmt.executeQuery(customerQuery);
		    if (customerResultSet.next()) {
		        JOptionPane.showMessageDialog(frame, "Login successful!");
		        frame.dispose(); 
		        WelcomePage welcomePage = new WelcomePage(userIDField.getText().toString()); 
		    } else {
		   
		        ResultSet workerResultSet = stmt.executeQuery(workerQuery);
		        if (workerResultSet.next()) {
		            JOptionPane.showMessageDialog(frame, "Login successful as Worker!");
		            frame.dispose(); 
		            WelcomePageWorker welcomePageWorker = new WelcomePageWorker(userIDField.getText().toString()); 
		       
		        } else {
		            JOptionPane.showMessageDialog(frame, "Login failed!");
		        }
		    }
		    connection.close();
		} catch (Exception e1) {
		    System.out.println(e1);
		}
		
	}
		
	}
	
	

