package workerPages;
import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import connectDB.JDBCUtil;
import connectDB.JDBCUtil.PaymentMethod;
import connectDB.JDBCUtil.ShipmentStatus;
import welcomePages.WelcomePageWorker;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.prefs.Preferences;


public class UserManagementPage {
    private JFrame frame = new JFrame("User Management");
    private JLabel titleLabel = new JLabel("User Management Page");
    private JTable userManagementTable = new JTable();
    JLabel label = new JLabel("Order Management");
    JButton backButton = new JButton("Back to MainMenu");
    private JTextField customerIdField = new JTextField();
    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField emailField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField taxNumberField = new JTextField();
    private JTextField passwordField = new JTextField();
    private JTextField discountPercentageField = new JTextField();
    private JComboBox<JDBCUtil.PaymentMethod> paymentMethodComboBox = new JComboBox<>(JDBCUtil.PaymentMethod.values());
// sayfadaki componentlar
    public UserManagementPage() {
    	 label.setBounds(50, 50, 300, 30);
         backButton.setBounds(50, 50, 200, 30);
  

         backButton.addActionListener(e -> {
             frame.dispose(); 
             new WelcomePageWorker("User from usermanagements");
         });
         
        titleLabel.setBounds(10, 10, 400, 35);
        titleLabel.setFont(new Font(null, Font.PLAIN, 20));
        

        JScrollPane userScrollPane = new JScrollPane(userManagementTable);
        userScrollPane.setBounds(10, 90, 750, 400);


        JLabel discountPercentageLabel = new JLabel("Discount Percentage:");
        discountPercentageLabel.setBounds(800, 420, 150, 30);
       
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setBounds(800, 100, 100, 30);
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(800, 140, 100, 30);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(800, 180, 100, 30);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(800, 220, 100, 30);
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(800, 260, 100, 30);
        JLabel taxNumberLabel = new JLabel("Tax Number:");
        taxNumberLabel.setBounds(800, 300, 100, 30);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(800, 340, 100, 30);
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodLabel.setBounds(800, 380, 120, 30);


        customerIdField.setBounds(920, 100, 200, 30);
        firstNameField.setBounds(920, 140, 200, 30);
        lastNameField.setBounds(920, 180, 200, 30);
        emailField.setBounds(920, 220, 200, 30);
        phoneField.setBounds(920, 260, 200, 30);
        taxNumberField.setBounds(920, 300, 200, 30);
        passwordField.setBounds(920, 340, 200, 30);
        paymentMethodComboBox = new JComboBox<>(JDBCUtil.PaymentMethod.values());
        paymentMethodComboBox.setBounds(920, 380, 200, 30);
        discountPercentageField.setBounds(950, 420, 100, 30);

        JButton addButton = new JButton("Add");
        addButton.setBounds(800, 50, 100, 35);
        addButton.addActionListener(e -> {
            addRowToUserManagementTable();
        });

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(920, 50, 100, 35);
        updateButton.addActionListener(e -> {
            updateRowInUserManagementTable();
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(1040, 50, 100, 35);
        deleteButton.addActionListener(e -> {
            deleteFromUserManagementTable();
        });
        userManagementTable.addMouseListener(new MouseAdapter() {
            
            public void mouseClicked(MouseEvent e) {
                int selectedRow = userManagementTable.getSelectedRow();
                if (selectedRow != -1) { 
                    DefaultTableModel model = (DefaultTableModel) userManagementTable.getModel();
                    Object customerId = model.getValueAt(selectedRow, 0);
                    Object firstName = model.getValueAt(selectedRow, 1);
                    Object lastName = model.getValueAt(selectedRow, 2);
                    Object email = model.getValueAt(selectedRow, 3);
                    Object phone = model.getValueAt(selectedRow, 4);
                    Object taxNumber = model.getValueAt(selectedRow, 5);
                    Object password = model.getValueAt(selectedRow, 6);
                    Object paymentMethod = model.getValueAt(selectedRow, 7);
                    Object discount = model.getValueAt(selectedRow, 8);
                    
                    customerIdField.setText(String.valueOf(customerId));
                    firstNameField.setText(String.valueOf(firstName));
                    lastNameField.setText(String.valueOf(lastName));
                    emailField.setText(String.valueOf(email));
                    phoneField.setText(String.valueOf(phone));
                    taxNumberField.setText(String.valueOf(taxNumber));
                    passwordField.setText(String.valueOf(password));
                    paymentMethodComboBox.setSelectedItem(paymentMethod);
                    discountPercentageField.setText(String.valueOf(discount));
                    
                    
                }
            }
        });
     // sayfadaki componentlar ve hepsinin sayfada nasıl gözükeceğini ayarlamak için boundlarının ayarlanması
        // tablonun üzerine tıklandığında oradaki rowun bilgilerinin string halinin seçilmesi
        paymentMethodComboBox.addMouseListener(new MouseAdapter() {
 
            public void mouseClicked(MouseEvent e) {
                int selectedRow = userManagementTable.getSelectedRow();
                if (selectedRow != -1) { 
                    DefaultTableModel model = (DefaultTableModel) userManagementTable.getModel();
                    Object paymentMethod = model.getValueAt(selectedRow, 7); 
                    if (paymentMethod instanceof String) {
                        try {
                        //kombo boxa kişinin ödeme yönetimin çekilmesi	
                            PaymentMethod selectedPaymentMethod = PaymentMethod.valueOf((String) paymentMethod);
                            paymentMethodComboBox.setSelectedItem(selectedPaymentMethod);
                            
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Invalid payment method: " + paymentMethod);
                        }
                    }
                }
            }
        });
        frame.add(titleLabel);
        frame.add(backButton);
        frame.add(addButton);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(userScrollPane);
        frame.add(customerIdLabel);
        frame.add(firstNameLabel);
        frame.add(lastNameLabel);
        frame.add(emailLabel);
        frame.add(phoneLabel);
        frame.add(taxNumberLabel);
        frame.add(passwordLabel);
        frame.add(paymentMethodLabel);
        frame.add(discountPercentageLabel);
        frame.add(discountPercentageField);
        frame.add(customerIdField);
        frame.add(firstNameField);
        frame.add(lastNameField);
        frame.add(emailField);
        frame.add(phoneField);
        frame.add(taxNumberField);
        frame.add(passwordField);
        frame.add(paymentMethodComboBox);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        populateTable();
    }
    // sayfanın doldurulması

    private void populateTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Customer ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Email");
        model.addColumn("Phone");
        model.addColumn("Tax Number");
        model.addColumn("Password");
        model.addColumn("Payment Method");
        model.addColumn("Discount Percentage");

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers;");
// istenilen tablonun databaseden getirilmesi
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("customer_id"),
                        rs.getString("customer_firstname"),
                        rs.getString("customer_lastname"),
                        rs.getString("customer_email"),
                        rs.getString("customer_phone"),
                        rs.getString("customer_tax_number"),
                        rs.getString("customer_password"),
                        JDBCUtil.PaymentMethod.valueOf(rs.getString("customer_payment_method")), // Convert string to enum directly
                        rs.getString("discount_percentage"),
                });
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userManagementTable.setModel(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        userManagementTable.setRowSorter(sorter);
        restoreColumnWidths();
    }// tablo için sort işlemleri
// yeni müşteri eklemek
    private void addRowToUserManagementTable() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText()); 
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String taxNumber = taxNumberField.getText();
            String password = passwordField.getText();
            PaymentMethod selectedPaymentMethod = (PaymentMethod) paymentMethodComboBox.getSelectedItem(); 
            
            double discountPercentage;
            String discountText = discountPercentageField.getText();
            if (!discountText.isEmpty()) {
                discountPercentage = Double.parseDouble(discountText);
            } else {
                discountPercentage = 0.0; 
            }
            
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
// alınan text bilgilerine göre yeni müşteriler eklenmesi
            String sql = "INSERT INTO customers (customer_id, customer_firstname, customer_lastname, customer_email, customer_phone, customer_tax_number, customer_password, customer_payment_method, discount_percentage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, taxNumber);
            statement.setString(7, password);
            statement.setObject(8, selectedPaymentMethod, Types.OTHER);
            statement.setDouble(9, discountPercentage);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Row added successfully!");
                populateTable(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add row.");
            }

            conn.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid customer ID."); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

// alınan bilgilere göre müşteri update edilmesi
    private void updateRowInUserManagementTable() {
        int customerId = Integer.parseInt(customerIdField.getText());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String taxNumber = taxNumberField.getText();
        String password = passwordField.getText();
        PaymentMethod selectedPaymentMethod = (PaymentMethod) paymentMethodComboBox.getSelectedItem();
        double discountPercentage = Double.parseDouble(discountPercentageField.getText());

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

            String sql = "UPDATE customers SET customer_firstname = ?, customer_lastname = ?, customer_email = ?, customer_phone = ?, customer_tax_number = ?, customer_password = ?, customer_payment_method = ?, discount_percentage  = ? WHERE customer_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, taxNumber);
            statement.setString(6, password);
            statement.setObject(7, selectedPaymentMethod, Types.OTHER);
            statement.setInt(9, customerId); 
            statement.setDouble(8, discountPercentage);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Row updated successfully!");
                populateTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update row.");
            }

            conn.close();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
// alınan bilgilere göre müşteri
// silinmesi ancak adresi varsa silinmiyor address tablosu yönetim sayfası yapılmadı
    private void deleteFromUserManagementTable() {
        int selectedRow = userManagementTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) userManagementTable.getModel();
        int customerId = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

             
                String checkReferencesSql = "SELECT COUNT(*) FROM customer_address WHERE customer_id_fk1 = ?";
                try (PreparedStatement checkReferencesStmt = conn.prepareStatement(checkReferencesSql)) {
                    checkReferencesStmt.setInt(1, customerId);
                    ResultSet rs = checkReferencesStmt.executeQuery();
                    rs.next();
                    int referencesCount = rs.getInt(1);
                    if (referencesCount > 0) {
                        JOptionPane.showMessageDialog(frame, "Cannot delete user. User has a address record.");
                        return;
                    }
                }
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?");
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();

                conn.close();

                model.removeRow(selectedRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderInspectionPage.class);
        TableColumnModel columnModel = userManagementTable.getColumnModel();
        for (int i = 0; i < userManagementTable.getColumnCount(); i++) {
            String columnName = userManagementTable.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
// kolonların genişliğinin istenildiği gibi ayarlanması
    private void restoreColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderInspectionPage.class);
        TableColumnModel columnModel = userManagementTable.getColumnModel();
        for (int i = 0; i < userManagementTable.getColumnCount(); i++) {
            String columnName = userManagementTable.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width); }
        }
    
    
}




