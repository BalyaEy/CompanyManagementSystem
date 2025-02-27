package customerPages;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.prefs.Preferences;

import connectDB.JDBCUtil; // Replace package_name with the actual package name if applicable
import connectDB.JDBCUtil.ShipmentStatus;
import welcomePages.WelcomePage;
import workerPages.OrderInspectionPage;

public class OrderPage {
    private JFrame frame = new JFrame("Order");
    private JLabel welcomeLabel = new JLabel("Order Menu");
    private JTable itemsTable = new JTable();
    private JButton backButton = new JButton("Go back to Main Menu");
    private JTable orderItemsTable = new JTable();
    private DefaultTableModel orderTableModel;
    private String userTaxNumber;
    public OrderPage(String userTaxNumber) {
    	// frame de bulunacak componentlar
    	  this.userTaxNumber = userTaxNumber;
    	  
        welcomeLabel.setBounds(10, 10, 400, 35);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 20));

        backButton.setBounds(10, 50, 200, 35);
        backButton.addActionListener(e -> {
        	
            frame.dispose();
            
            new WelcomePage(userTaxNumber);
        });

        JScrollPane itemsScrollPane = new JScrollPane(itemsTable);
        itemsScrollPane.setBounds(10, 90, 400, 250);

        JScrollPane orderScrollPane = new JScrollPane(orderItemsTable);
        orderScrollPane.setBounds(420, 90, 400, 250);

        JButton addToOrderButton = new JButton("Add to Order");
        addToOrderButton.setBounds(10, 350, 120, 35);
        addToOrderButton.addActionListener(e -> addToOrder());

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.setBounds(140, 350, 120, 35);
        createOrderButton.addActionListener(e -> createOrder(userTaxNumber));

        JButton deleteFromOrderButton = new JButton("Delete from Order");
        deleteFromOrderButton.setBounds(270, 350, 150, 35);
        deleteFromOrderButton.addActionListener(e -> deleteFromOrder());

        frame.add(welcomeLabel);
        frame.add(backButton);
        frame.add(itemsScrollPane);
        frame.add(orderScrollPane);
        frame.add(addToOrderButton);
        frame.add(createOrderButton);
        frame.add(deleteFromOrderButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 520);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        initializeOrderTable();
        populateTable();
    }  // frame de bulunacak componentların bound atanması ve butonlara event eklenmesi ve saayfaya eklenmesi

    private void populateTable() {
    	// tablonun kolonlarının tanımlanması ve içeriğinin doldurulması
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Item Category");
        model.addColumn("Price");
        model.addColumn("Availability");
      
        try {
        	
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT item_id, name, category_id, price, stock_quantity FROM furniture_items;");

            while (rs.next()) {
            	
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("name");
                int categoryId = rs.getInt("category_id");
                double originalPrice = rs.getDouble("price");
                int stockQuantity = rs.getInt("stock_quantity");
                // sipariş verilecek itemları kişilerin kendi iskonto değerine göre gözükmesi
                double discountPercentage = getCustomerDiscountPercentage(userTaxNumber, conn);
                double discountedPrice = calculateDiscountedPrice(originalPrice, discountPercentage);

                model.addRow(new Object[]{
                		
                        itemId,
                        itemName,
                        categoryId,
                        discountedPrice,
                        stockQuantity
                });
                
                updateDiscountedPrice(conn, itemId, discountedPrice);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        itemsTable.setModel(model);
        itemsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        itemsTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        itemsTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        
        // kolonların genişliğinin ayarladığım gibi ve ssabit kalması için
        restoreColumnWidths();
        itemsTable.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths();
            }
        });
        
        orderItemsTable.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths();
            }
        });
        
        // tablolara row ile sort
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        itemsTable.setRowSorter(sorter);

        // id kolonu için özel karşılaştırıcı
        sorter.setComparator(0, new Comparator<Integer>() {
           
            public int compare(Integer o1, Integer o2) {
                return Double.compare(o1, o2);
            }
        });

        // item kolonu için özel karşılaştırıcı
        sorter.setComparator(2, new Comparator<Integer>() {
          
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        // price kolonu için özel karşılaştırıcı
        sorter.setComparator(3, new Comparator<Double>() {
          
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        });
     // stok kolonu için özel karşılaştırıcı
        sorter.setComparator(4, new Comparator<Integer>() {
          
            public int compare(Integer o1, Integer o2) {
                return Double.compare(o1, o2);
            }
        });
    }
// iskontolu fiyat hesaplanması
    private double calculateDiscountedPrice(double originalPrice, double discountPercentage) {
        return originalPrice * (1 - discountPercentage / 100);
    }

    private void updateDiscountedPrice(Connection conn, int itemId, double discountedPrice) throws SQLException {
        String sql = "UPDATE furniture_items SET discounted_price = ? WHERE item_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, discountedPrice);
        stmt.setInt(2, itemId);
        stmt.executeUpdate();
    }
// order tableın initilaze edilmesi
    private void initializeOrderTable() {
        orderTableModel = new DefaultTableModel();
        orderTableModel.addColumn("Item ID");
        orderTableModel.addColumn("Item Name");
        orderTableModel.addColumn("Price");
        orderTableModel.addColumn("Quantity");
        orderItemsTable.setModel(orderTableModel);
        orderItemsTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        orderItemsTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

    }
// delete butonu order tablosundan
    private void deleteFromOrder() {
        int selectedRow = orderItemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to delete from the order.");
            return;
        }

        orderTableModel.removeRow(selectedRow);
        
    }
    
    private void addToOrder() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to add to the order.");
            
            return;
        }

        int itemId = (int) itemsTable.getValueAt(selectedRow, 0);
        String itemName = (String) itemsTable.getValueAt(selectedRow, 1);
        double itemPrice = (double) itemsTable.getValueAt(selectedRow, 3);

        // ürün seçilmişse sayısının artması
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            int currentItemId = (int) orderTableModel.getValueAt(i, 0);
            if (currentItemId == itemId) {
                int currentQuantity = (int) orderTableModel.getValueAt(i, 3);
                orderTableModel.setValueAt(currentQuantity + 1, i, 3);
                return;
            }
        }

       // tabloya ürünün eklenmesi
        orderTableModel.addRow(new Object[]{itemId, itemName, itemPrice, 1});
    }

   // create işlemi
    private void createOrder(String userTaxNumber) {
    	
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            conn.setAutoCommit(false);

            int customerId = getCustomerId(userTaxNumber, conn);
            if (customerId == -1) {
                JOptionPane.showMessageDialog(null, "No customer found with the provided tax number");
                return;
            }
// database bağlantısı ve main method olduğu zaman customer tax number ın doğruluğunun testi

            int addressId = -1;
            PreparedStatement addressStmt = conn.prepareStatement("SELECT address_id_fk1 FROM customer_address WHERE customer_id_fk1 = ?");
            addressStmt.setInt(1, customerId);
            ResultSet addressRs = addressStmt.executeQuery();
            if (addressRs.next()) {
                addressId = addressRs.getInt("address_id_fk1");
            }
// customer_address tablosundan id=address yapılması

            PreparedStatement ordersStmt = conn.prepareStatement("INSERT INTO orders (order_state, order_date, customer_address_CustomerIdFK, customer_address_AddressIdFK) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ordersStmt.setObject(1, ShipmentStatus.PENDING, Types.OTHER); 
            ordersStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ordersStmt.setInt(3, customerId);
            ordersStmt.setInt(4, addressId);
            ordersStmt.executeUpdate();
            // siparişin oluşuturlması
            ResultSet generatedKeys = ordersStmt.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);   
            }
            
            double totalCost = 0.0;
            
            StringBuilder orderDetails = new StringBuilder("Order Details:\n");
// sipariş içeriğinin girilmesi
            PreparedStatement orderItemsStmt = conn.prepareStatement("INSERT INTO furniture_order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)");
            for (int i = 0; i < orderTableModel.getRowCount(); i++) {
                int itemId = (int) orderTableModel.getValueAt(i, 0);
                int quantity = (int) orderTableModel.getValueAt(i, 3);
                double price = (double) orderTableModel.getValueAt(i, 2);
                double itemCost = quantity * price;
                totalCost += itemCost;

                updateStockQuantity(conn, itemId, quantity);

                orderDetails.append("Item ID: ").append(itemId).append(", ")
                        .append("Item Name: ").append(orderTableModel.getValueAt(i, 1)).append(", ")
                        .append("Quantity: ").append(quantity).append(", ")
                        .append("Price: $").append(price).append(", ")
                        .append("Item Cost: $").append(itemCost).append("\n");

                orderItemsStmt.setInt(1, orderId);
                orderItemsStmt.setInt(2, itemId);
                orderItemsStmt.setInt(3, quantity);
                orderItemsStmt.setDouble(4, price);
                orderItemsStmt.addBatch();
            }
            // frame de bulunacak componentların bound atanması ve butonlara event eklenmesi
            orderItemsStmt.executeBatch();
            conn.commit();
            conn.close();
            orderDetails.append("\nTotal Cost: $").append(totalCost);
            JOptionPane.showMessageDialog(frame, orderDetails.toString());
            JOptionPane.showMessageDialog(frame, "Order created successfully!");
            orderTableModel.setRowCount(0);
            populateTable();
// tekrardan populateTable çağrısı düzenlenen yenilikleri içine koyması için
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStockQuantity(Connection conn, int itemId, int quantity) throws SQLException {
        String sql = "UPDATE furniture_items SET stock_quantity = stock_quantity - ? WHERE item_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quantity);
        stmt.setInt(2, itemId);
        stmt.executeUpdate();
    }




    // tax number ile Id
    private int getCustomerId(String userTaxNumber, Connection conn) throws SQLException {
        String sql = "SELECT customer_id FROM customers WHERE customer_tax_number = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userTaxNumber);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("customer_id");
        }
        return -1;
    }
// tekrardan tax numberdan discount bulma
    private double getCustomerDiscountPercentage(String userTaxNumber, Connection conn) throws SQLException {
        String sql = "SELECT discount_percentage FROM customers WHERE customer_tax_number = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userTaxNumber);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("discount_percentage");
        }
        
        return -1;
    }
    // 
    private void restoreColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderPage.class); 
        TableColumnModel columnModel = itemsTable.getColumnModel();
        for (int i = 0; i < itemsTable.getColumnCount(); i++) {
            String columnName = itemsTable.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width);
        }
        TableColumnModel orderColumnModel = orderItemsTable.getColumnModel();
        for (int i = 0; i < orderItemsTable.getColumnCount(); i++) {
            String columnName = orderItemsTable.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            orderColumnModel.getColumn(i).setPreferredWidth(width);
        }
    }
    // kolonların ayarlanmış şekilde kalması için methodlar
    private void saveColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderPage.class); 
        TableColumnModel columnModel = itemsTable.getColumnModel();
        for (int i = 0; i < itemsTable.getColumnCount(); i++) {
            String columnName = itemsTable.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
        TableColumnModel orderColumnModel = orderItemsTable.getColumnModel();
        for (int i = 0; i < orderItemsTable.getColumnCount(); i++) {
            String columnName = orderItemsTable.getColumnName(i);
            int width = orderColumnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
    
    //kolon içeriğinin ortalanması
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
        {
            setHorizontalAlignment(JLabel.CENTER);
        }
    };
  //price için kolon içeriğinin ortalanması
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
        {
            setHorizontalAlignment(JLabel.RIGHT);
        }
// digit grouping yapılması price k0olonu için, basamak gruplandırma
        protected void setValue(Object value) {
            if (value instanceof Double) {
                DecimalFormat df = new DecimalFormat("#,###.##");
                setText(df.format(value));
            } else {
                super.setValue(value);
            }
        }
    };
}


























