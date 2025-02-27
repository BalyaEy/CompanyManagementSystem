package workerPages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.prefs.Preferences;
import java.text.NumberFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import welcomePages.WelcomePageWorker;
import supplier.Supplier;
import supplier.SupplierLoader;
import category.*;
import javax.security.auth.Refreshable;
import javax.swing.*;


public class ItemManagementPage {
    private JFrame frame = new JFrame("Item Management");
    private JLabel label = new JLabel("Item Management ");
    private JLabel idLabel = new JLabel("Item id:");
    private JLabel nameLabel = new JLabel("Item Name:");
    private JLabel categoryLabel = new JLabel("Category:");
    private JLabel supplierLabel = new JLabel("Supplier:");
    private JLabel priceLabel = new JLabel("Price:");
    private JLabel stockLabel = new JLabel("Stock Quantity:");
    private JTextField idField = new JTextField();
    private JTextField nameField = new JTextField();
    private JComboBox<String> categoryComboBox = new JComboBox<>();
    private JComboBox<String> supplierComboBox = new JComboBox<>();
    private JTextField priceField = new JTextField();
    private JTextField stockField = new JTextField();
    private JButton addButton = new JButton("Add Item");
    private JButton deleteButton = new JButton("Delete Item");
    private JButton backButton = new JButton("Back to Main Menu");
    private JTable itemTable = new JTable();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JButton updateButton = new JButton("Update Item");
	// frame de bulunacak componentlar
    
    public ItemManagementPage() {
        label.setBounds                 (50, 20, 300, 30);
        idLabel.setBounds               (50, 60, 100, 30);
        nameLabel.setBounds             (50, 100, 100, 30);
        categoryLabel.setBounds         (50, 140, 100, 30);
        supplierLabel.setBounds         (50, 180, 100, 30);
        priceLabel.setBounds            (50, 220, 100, 30);
        stockLabel.setBounds            (50, 260, 100, 30);
        idField.setBounds               (150, 60, 200, 30);
        nameField.setBounds             (150, 100, 200, 30);
        categoryComboBox.setBounds      (150, 140, 200, 30);
        supplierComboBox.setBounds      (150, 180, 200, 30);
        priceField.setBounds            (150, 220, 200, 30);
        stockField.setBounds            (150, 260, 200, 30);
        addButton.setBounds             (50, 300, 150, 30);
        deleteButton.setBounds          (210, 300, 150, 30);
        backButton.setBounds            (50, 340, 150, 30);
        itemTable.setBounds             (50, 600, 1000, 300);
        updateButton.setBounds          (210, 340, 150, 30);
        frame.add(updateButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new WelcomePageWorker("User ID ITEM");
            }
           
        });
        
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateItem();
              
            }
        });
    	// frame de bulunacak componentlar ve ayarlarının yapılıp ekrana eklenmesi
        frame.add(label);
        frame.add(idLabel);
        frame.add(nameLabel);
        frame.add(categoryLabel);
        frame.add(supplierLabel);
        frame.add(priceLabel);
        frame.add(stockLabel);
        frame.add(idField);
        frame.add(nameField);
        frame.add(categoryComboBox);
        frame.add(supplierComboBox);
        frame.add(priceField);
        frame.add(stockField);
        frame.add(addButton);
        frame.add(deleteButton);
        frame.add(backButton);

        frame.setLayout(null);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        categoryComboBox.setFocusable(true);
        supplierComboBox.setFocusable(true);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadCategories();
        loadSuppliers();
        loadItems();

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBounds(50, 380, 770, 300);
        frame.add(scrollPane);
        configureCellRenderers();
        
        configureCellRenderers();
        itemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = itemTable.getSelectedRow();
                    if (selectedRow != -1) {
                        TableModel model = itemTable.getModel();
                        Object id = model.getValueAt(selectedRow, 0);
                        Object name = model.getValueAt(selectedRow, 1);
                        Object category = model.getValueAt(selectedRow, 2);
                        Object supplier = model.getValueAt(selectedRow, 3);
                        Object stock = model.getValueAt(selectedRow, 4);
                        Object price = model.getValueAt(selectedRow, 5);

                        setSelectedCategory(category.toString());
                        setSelectedSupplier(supplier.toString());

                        idField.setText(id.toString());
                        nameField.setText(name.toString()); 
                        stockField.setText(stock.toString());
                        priceField.setText(price.toString());
                    }//item table jscroll pane embed edilmesi ve kolonların getirilip içerisindeki datanın string sonucunu almak
                }
            }
        });}	
    //combo box için seçilen rowdaki supplierın getirilmesi
    private void setSelectedSupplier(String supplierName) {
        String[] parts = supplierName.split(" Contact Person: ");
        String extractedSupplierName = parts[0].substring("Company: ".length());
   //DEBUG System.out.println(" Supplier Name: " + extractedSupplierName);

        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) supplierComboBox.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            String item = model.getElementAt(i);
            if (item.equals(extractedSupplierName)) {
          //DEBUG   System.out.println("index: " + i); DEBUG
                supplierComboBox.setSelectedIndex(i);
                return;
            }
        }
      //DEBUG  System.out.println("Supplier : " + supplierName);
    }
    //combo box için seçilen rowdaki supplierin getirilmesi
        private void setSelectedCategory(String category) {
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) categoryComboBox.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                String item = model.getElementAt(i);
                if (item.startsWith(category)) {
                    categoryComboBox.setSelectedIndex(i);
					
                    return;
                }
            }
        } ;
        
     // kategorilerin komboboxlara eklenmesi
   
    private void loadCategories() {
        // databaseden çek combo box içeriğini
        List<Category> categories = CategoryLoader.loadCategories();
      System.out.println("kategoriler adet " + categories.size());

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Category category : categories) {
            String displayText = category.getCategoryId() + " - " + category.getCategoryName();
            model.addElement(displayText);
        }

        categoryComboBox.setModel(model);
        System.out.println("kombobox 1");
    }
// supplierın komboboxlara eklenmesi
    private void loadSuppliers() {
        List<Supplier> suppliers = SupplierLoader.loadSuppliers();
        System.out.println("supplier adet " + suppliers.size());

        for (Supplier supplier : suppliers) {
            supplierComboBox.addItem(supplier.getSupplierName());
        }
        System.out.println("komboboxz2");
    }
//itemlerin database e eklenmesi için method, category id yi ikiye bölüp yanına aynı zamanda adını da yazmak,
    private void addItem() {
        String itemName = nameField.getText();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        int categoryId = Integer.parseInt(selectedCategory.split(" - ")[0]);
        String selectedSupplier = (String) supplierComboBox.getSelectedItem();
        Supplier supplier = getSupplierByName(selectedSupplier);
        String priceText = priceField.getText();
        String stockText = stockField.getText();
        int stockValue = Integer.parseInt(stockText);

        // Validate input TO DO çalışıyordu bozuldu bul
        if (itemName.isEmpty() ||selectedCategory == null || selectedSupplier == null || priceText == null || stockText == null) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        try {
            BigDecimal price = new BigDecimal(priceText);
            
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "INSERT INTO furniture_items (name, category_id, supplier_id, price, stock_quantity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, itemName);
            statement.setInt(2, categoryId);
            statement.setInt(3, supplier.getSupplierId());
            statement.setBigDecimal(4, price);
            statement.setInt(5, stockValue);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Item added successfully!");
                loadItems(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add item.");
            }

            conn.close();
            
        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
   
    //belirtilen tedarikçi adına göre tedarikçi döndürür, döngü ile kontrol eder
    //eşleşme bulunamazsa null değerini döndürür
    private Supplier getSupplierByName(String supplierName) {
        List<Supplier> suppliers = SupplierLoader.loadSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier;
            }
        }
        return null;
    }
// delete item 
    private void deleteItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to delete.");
            return;
        }

        int itemId = (int) itemTable.getValueAt(selectedRow, 0); 
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION) {
            return; 
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

            String checkReferencesSql = "SELECT COUNT(*) FROM furniture_order_items WHERE item_id = ?";
            try (PreparedStatement checkReferencesStmt = conn.prepareStatement(checkReferencesSql)) {
                checkReferencesStmt.setInt(1, itemId);
                ResultSet rs = checkReferencesStmt.executeQuery();
                rs.next();
                int referencesCount = rs.getInt(1);
                if (referencesCount > 0) {
                    JOptionPane.showMessageDialog(frame, "Cannot delete item. There are orders that has this item.");
                    return;
                }// database den silmeden önce sipariş verildimi diye kontrol edip ona göre ürünü silemez
            }
// itemı databaseden silmek
            String deleteSql = "DELETE FROM furniture_items WHERE item_id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, itemId);
                int rowsDeleted = deleteStmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(frame, "Item deleted successfully!");
                    loadItems(); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete item.");
                }
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    //update item
    private void updateItem() {
        String itemName = nameField.getText();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        int categoryId = Integer.parseInt(selectedCategory.split(" - ")[0]);
        String selectedSupplier = (String) supplierComboBox.getSelectedItem();
        Supplier supplier = getSupplierByName(selectedSupplier); 
        String priceText = priceField.getText();
        String idText = idField.getText();
        String stockText = stockField.getText();

        if (itemName.isEmpty() || selectedCategory == null || selectedSupplier == null || priceText.isEmpty() || stockText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }
        
        BigDecimal price;
        int stockValue;
        int itemId;
        try {
            price = new BigDecimal(priceText);
            stockValue = Integer.parseInt(stockText);
            itemId = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Price and stock quantity must be valid numbers.");
            return;
        }

        try {
        	
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "UPDATE furniture_items SET name = ?, category_id = ?, supplier_id = ?, price = ?, stock_quantity = ? WHERE item_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(6, itemId);
            statement.setString(1, itemName);
            statement.setInt(2, categoryId);
            statement.setInt(3, supplier.getSupplierId());
            statement.setBigDecimal(4, price);
            statement.setInt(5, stockValue);

            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Item updated successfully!");
                loadItems();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update item.");
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    private void loadItems() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "SELECT i.item_id, i.name AS \"Item Name\", i.category_id AS \"Category\", i.price AS \"Price\", i.stock_quantity AS \"Stock Quantity\", CONCAT('Company: ', s.name, ' Contact Person: ', s.contact_person) AS \"Supplier Name\" " +
                         "FROM furniture_items i " +
                         "INNER JOIN furniture_suppliers s ON i.supplier_id = s.supplier_id";
            // itemları istenilen şekle göre databaseden tablo olarak çeker
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"Item ID", "Item Name", "Category", "Supplier Name", "Stock Quantity", "Price"});
            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("Item Name");
                int categoryId = resultSet.getInt("Category");
                String supplierName = resultSet.getString("Supplier Name");
                int stockQuantity = resultSet.getInt("Stock Quantity");
                BigDecimal price = resultSet.getBigDecimal("Price");

                model.addRow(new Object[]{itemId, itemName, categoryId,supplierName , stockQuantity, price});
            }

            itemTable.setModel(model);
            restoreColumnWidths();
            // column ların istenilen genişlikte kalması
            
            itemTable.getTableHeader().addMouseListener(new MouseAdapter() {
             
                public void mouseReleased(MouseEvent e) {
                    saveColumnWidths();
                }
            });
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


      
    
    private void configureCellRenderers() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
// sağa ve ya ortaya align edilmesi
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        for (int i = 0; i < itemTable.getColumnCount(); i++) {
       //     System.out.println("column: " + itemTable.getColumnName(i));
            if (!itemTable.getColumnName(i).equals("Price") && !itemTable.getColumnName(i).equals("Supplier Name") && !itemTable.getColumnName(i).equals("Item Name")) {
                itemTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        int priceColumnIndex = getColumnIndexByName("Price");
        if (priceColumnIndex != -1) {
            itemTable.getColumnModel().getColumn(priceColumnIndex).setCellRenderer(rightRenderer);
            itemTable.getColumnModel().getColumn(priceColumnIndex).setCellRenderer(new PriceRenderer());
        }
        restoreColumnWidths();
    }
// kolon indexi alıp hangi kolonu align edeceksem ona göre seçmek
    private int getColumnIndexByName(String columnName) {
        for (int i = 0; i < itemTable.getColumnCount(); i++) {
        //    System.out.println("column: " + itemTable.getColumnName(i));
            if (itemTable.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1; 
    }

    private void saveColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(ItemManagementPage.class);
        TableColumnModel columnModel = itemTable.getColumnModel();
        for (int i = 0; i < itemTable.getColumnCount(); i++) {
            String columnName = itemTable.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
    // kolonların ayarlanmış şekilde kalması için methodlar
    private void restoreColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(ItemManagementPage.class);
        TableColumnModel columnModel = itemTable.getColumnModel();
        for (int i = 0; i < itemTable.getColumnCount(); i++) {
            String columnName = itemTable.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
// group digiting, basamak gruplandırma 
    class PriceRenderer extends DefaultTableCellRenderer {
        private final NumberFormat formatter = new DecimalFormat("#,##0.00");

   
        protected void setValue(Object value) {
          //  System.out.println("fiyat formatting: " + value);
            if (value instanceof BigDecimal) {
                BigDecimal price = (BigDecimal) value;
                //System.out.println("fiyat formatting: " + price);
                value = formatter.format(price);
                //System.out.println("fiyat formatting: " + value);
            }
            setText(value != null ? value.toString() : "");
        }
    }

   
       
    
    
}




























