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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import customerPages.MyOrdersPage;
import customerPages.OrderPage;

public class WelcomePage {
    JFrame frame = new JFrame("Main Menu");
    JPanel panel = new JPanel(new GridBagLayout());
    JLabel welcomeLabel = new JLabel();
    JButton orderPlaceButton = new JButton("Place a new order!");
    JButton myOrdersButton = new JButton("My Orders");
	// frame de bulunacak componentlar

    public WelcomePage(String userTaxNumber) {
        panel.setBackground(Color.WHITE);

        welcomeLabel.setText(" Main Menu ");
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
     //   Belirtilen kullanıcı tax number ile  WelcomePage
        //  GridBagConstraints ile componentları eklemek için düzen yapısı kurmak
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); 
        panel.add(welcomeLabel, gbc);

        orderPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openOrderPage(userTaxNumber);
            }
        });

        myOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openConfirmOrdersPage(userTaxNumber);
            }
        });

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 20); 
        panel.add(myOrdersButton, gbc);

        gbc.gridy = 2;
        panel.add(orderPlaceButton, gbc);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 300);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);}
        // panelin direkt sayfaya eklenmesi
      
// confirm orders page i açmak
    private void openConfirmOrdersPage(String userTaxNumber) {
        new MyOrdersPage(userTaxNumber); 
        frame.dispose(); 
    }
 //  orders page i açmak
    private void openOrderPage(String userTaxNumber) {
        new OrderPage(userTaxNumber);
        frame.dispose(); 
    }

    
}


