package welcomePages;
import java.awt.Color;  
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import loginPage.LoginPage;
import workerPages.ItemManagementPage;
import workerPages.OrderManagementPage;
import workerPages.UserManagementPage;

public class WelcomePageWorker {
    JFrame frame = new JFrame("Main Menu");
    JPanel panel = new JPanel(new GridBagLayout());
    JLabel welcomeLabel = new JLabel();
    JButton confirmOrdersButton = new JButton("Manage Orders");
    JButton addItemsButton = new JButton("Manage Items");
    JButton manageUsersButton = new JButton("Manage Users");
 // frame de bulunacak componentlar
    public WelcomePageWorker(String userID ) {
        panel.setBackground(Color.WHITE);
        
        welcomeLabel.setText("Main Menu" );
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
        //   Belirtilen kullanıcıid ile  WelcomePage
        //  GridBagConstraints ile componentları eklemek için düzen yapısı kurmak
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.add(welcomeLabel, gbc);
        
        confirmOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openConfirmOrdersPage(userID);
            }
        });

        addItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddItemsPage(userID);
            }
        });
       
       
        manageUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	openUserManagementPage(userID);
            }
        });
        
        
        gbc.gridy = 3;
        panel.add(manageUsersButton, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.add(confirmOrdersButton, gbc);
        gbc.gridy = 2;
        panel.add(addItemsButton, gbc);
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 300);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
     // panelin direkt sayfaya eklenmesi
    }
// confirm orders page açmak
    private void openConfirmOrdersPage(String userID) {
    	 new OrderManagementPage();
    	 frame.dispose(); 
    }
 // additem page açmak
    private void openAddItemsPage(String userID) {
         new ItemManagementPage();
    frame.dispose(); 
    
    }
 // usermanagement page açmak
    private void openUserManagementPage(String userID) {
         new UserManagementPage();
    	 frame.dispose(); 
    }
}
































