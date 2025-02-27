package workerPages;

import javax.swing.*; 
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import connectDB.JDBCUtil.ShipmentStatus;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.prefs.Preferences;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class OrderInspectionPage {
    private JFrame frame = new JFrame();
    private JTable orderItemsTable = new JTable();
    private JTable furnitureItemsTable = new JTable();
    private JLabel label = new JLabel("Order Inspection ");
    private JButton backButton = new JButton("Back to MainMenu");
    private JButton sendButton = new JButton("Send Order Confirmation");
    
    public OrderInspectionPage(int orderId) {
        label.setBounds(10, 10, 400, 35);
        label.setFont(new Font(null, Font.PLAIN, 20));

        backButton.setBounds(50, 50, 200, 30);
        backButton.addActionListener(e -> {
            frame.dispose(); 
            new OrderManagementPage();
        });
        
        sendButton.setBounds(270, 50, 250, 30);
        sendButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to send this order confirmation?", "Send Order Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                sendOrderConfirmation();
            } 
        });

        JScrollPane orderScrollPane = new JScrollPane(orderItemsTable);
        orderScrollPane.setBounds(10, 90, 350, 200);
        
        JScrollPane furnitureScrollPane = new JScrollPane(furnitureItemsTable);
        furnitureScrollPane.setBounds(400, 90, 500, 200);

        frame.setTitle("Order Inspection Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.add(label);
        frame.add(backButton);
        frame.add(sendButton);
        frame.add(orderScrollPane);
        frame.add(furnitureScrollPane);
        // sayfaya componentların eklenmesi
        populateOrderTable(orderId);
        populateFurnitureTable();
    }
       
    private void populateOrderTable(int orderId) {
    	
        DefaultTableModel model = new DefaultTableModel() {
        	
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) { 
                    return Boolean.class;
                }
                
                return super.getColumnClass(columnIndex);
            }
            // sadece son kolonnun editable olması için
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        model.addColumn("Order ID");
        model.addColumn("Item ID");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Available");
       
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "SELECT  order_id, item_id, quantity, price, is_selected_for_shipping FROM furniture_order_items WHERE order_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity"),
                    String.format("%,.2f", rs.getDouble("price")), 
                    rs.getBoolean("is_selected_for_shipping"),
                });
            }
// tablonun databaseden çekilip doldurulması
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        orderItemsTable.setModel(model);  
        furnitureItemsTable.setModel(model);  
        restoreColumnWidths(furnitureItemsTable); 
        furnitureItemsTable.getTableHeader().addMouseListener(new MouseAdapter() {  
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths(furnitureItemsTable); 
                // tablonun eklen mesi
            }
        }); 
        // kolonların align edilmesi
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = orderItemsTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            orderItemsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); 
            orderItemsTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(SwingConstants.RIGHT); 
                    return c;
                }
            });
        }
        // kolonların align edilmesi ve uygunluğa göre renklerinin belirlenmesi
        orderItemsTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                boolean isAvailable = (boolean) value;
                String availabilityText = isAvailable ? "Available" : "Not Available";
                c.setForeground(isAvailable ? Color.GREEN : Color.RED);
                setText(availabilityText);
                return c;
            }
        });

        // available değişimi için tablo yenileme
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 4) { 
                    boolean newValue = (boolean) model.getValueAt(row, column);
                    int orderID = (int) model.getValueAt(row, 0); 
                    int itemID = (int) model.getValueAt(row, 1);
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
                        String updateSql = "UPDATE furniture_order_items SET is_selected_for_shipping = ? WHERE order_id = ? AND item_id = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                        updateStmt.setBoolean(1, newValue);
                        updateStmt.setInt(2, orderID);
                        updateStmt.setInt(3, itemID);
                        updateStmt.executeUpdate();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }); 
    }
    private void populateFurnitureTable() {
        DefaultTableModel model = new DefaultTableModel() {
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Category");
        model.addColumn("Supplier ID");
        model.addColumn("Stock");
        model.addColumn("Price");

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "SELECT fi.item_id, fi.name, fc.name AS category_name, fi.supplier_id, fi.stock_quantity ,fi.price " +
                         "FROM furniture_items fi " +
                         "INNER JOIN furniture_categories fc ON fi.category_id = fc.category_id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
// databaseden istenilen şekilde tablonun çekilip doldurulması
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getString("category_name"),
                    rs.getInt("supplier_id"),
                    rs.getInt("stock_quantity"),
                    String.format("%,.2f", rs.getDouble("price"))
                });
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        furnitureItemsTable.setModel(model);
        // kolonların istenilen gibi kalması
        restoreColumnWidths(furnitureItemsTable);
        furnitureItemsTable.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths(furnitureItemsTable);
            }
        });
//tablodaki kolonların align edilmesi ve siparişte bulunan ürünlerin item tablosunda en üstte gözükmesi,
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = furnitureItemsTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (i != 1) { 
                furnitureItemsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            } else {
                DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
                leftRenderer.setHorizontalAlignment(JLabel.LEFT);
                furnitureItemsTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
            }
        }


        for (int i = furnitureItemsTable.getRowCount() - 1; i >= 0; i--) {
            int itemId = (int) furnitureItemsTable.getValueAt(i, 0); 
            boolean found = false;
            for (int j = 0; j < orderItemsTable.getRowCount(); j++) {
                int orderId = (int) orderItemsTable.getValueAt(j, 1); 
                if (itemId == orderId) {
//to do 
                    for (int k = 0; k < furnitureItemsTable.getColumnCount(); k++) {
                        furnitureItemsTable.getCellRenderer(i, k).getTableCellRendererComponent(furnitureItemsTable,
                                furnitureItemsTable.getValueAt(i, k), true, true, i, k);
                    }
                    
                    Object[] rowData = new Object[model.getColumnCount()];
                    for (int k = 0; k < model.getColumnCount(); k++) {
                        rowData[k] = furnitureItemsTable.getValueAt(i, k);
                    }
                    model.insertRow(0, rowData);
                    model.removeRow(i + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {    
                for (int k = 0; k < furnitureItemsTable.getColumnCount(); k++) {
                    furnitureItemsTable.getCellRenderer(i, k).getTableCellRendererComponent(furnitureItemsTable,
                            furnitureItemsTable.getValueAt(i, k), false, false, i, k);
                }
            }
        }
    }

         
     
     
    // müşteri için onay gönderme butonunun içeriği
    private void sendOrderConfirmation() {
        DefaultTableModel model = (DefaultTableModel) orderItemsTable.getModel();
        ShipmentStatus shipmentStatus = null; 
        // bütün ürünler unavailable ise direkt declined göndermesi için 
        boolean isAllUnavailable = true;
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 4)) {
                isAllUnavailable = false;
                break;
            }
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String updateSql = "UPDATE orders SET order_state = ? WHERE order_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);

            if (isAllUnavailable) {
                shipmentStatus = ShipmentStatus.DECLINED;
            } else {
                shipmentStatus = ShipmentStatus.AWAITING_CONFIRMATION;
            }// ürünlerin hepsi unavailable ise declnied yoksa awaiting confirmation

            updateStmt.setObject(1, shipmentStatus, Types.OTHER);
            int orderId = (int) orderItemsTable.getValueAt(0, 0); 
            updateStmt.setInt(2, orderId);
            updateStmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(frame, "Order confirmation sent successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to send order confirmation.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveColumnWidths(JTable table) {
        Preferences prefs = Preferences.userNodeForPackage(OrderInspectionPage.class);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
    // kolonların ayarlanmış şekilde kalması için methodlar
    private void restoreColumnWidths(JTable table) {
        Preferences prefs = Preferences.userNodeForPackage(OrderInspectionPage.class);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
}

