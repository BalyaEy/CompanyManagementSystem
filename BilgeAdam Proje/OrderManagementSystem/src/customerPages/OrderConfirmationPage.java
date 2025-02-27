package customerPages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import workerPages.OrderInspectionPage;

import java.awt.*;
import java.sql.*;
import java.util.prefs.Preferences;

public class OrderConfirmationPage {

    private JFrame frame = new JFrame("Confirm Order");
    private JTable orderConfirmationTable = new JTable();
   
    private JButton backButton = new JButton("Back to Main Menu");
    private JButton confirmButton = new JButton("Confirm Order");
    private JButton cancelButton = new JButton("Cancel Order");
 // frame de bulunacak componentlar
    public OrderConfirmationPage(String userTaxNumber, int orderId) {
    	 JLabel label = new JLabel("Managing order: " + orderId);
        label.setBounds(10, 10, 400, 35);
        label.setFont(new Font(null, Font.PLAIN, 20));
        backButton.setBounds(50, 50, 200, 30);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MyOrdersPage(userTaxNumber);
        });
        confirmButton.setBounds(270, 50, 150, 30);
        confirmButton.addActionListener(e -> confirmOrder(orderId));
        cancelButton.setBounds(430, 50, 150, 30);
        cancelButton.addActionListener(e -> cancelOrder(orderId));
     // frame de bulunacak componentların bound atanması ve butonlara event eklenmesi
        
        

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Item ID", "Item Name", "Quantity", "Price", "Selected for Shipping"}, 0);
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

            String sql = "SELECT furniture_order_items.item_id, furniture_items.name, quantity, furniture_order_items.price, is_selected_for_shipping " +
                    "FROM furniture_order_items " +
                    "INNER JOIN furniture_items ON furniture_order_items.item_id = furniture_items.item_id " +
                    "WHERE furniture_order_items.order_id = ?"; // farklı tabloları inner joinle
         // tabloyu doldurmak için databaseden istenilen şekile göre özel kolonların çekilmesi
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId); // id yi parametreolarak alıyor
            ResultSet rs = stmt.executeQuery();
            
                while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                boolean isSelectedForShipping = rs.getBoolean("is_selected_for_shipping");
                model.addRow(new Object[]{itemId, itemName, quantity, price, isSelectedForShipping});
            }
                
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from the database");
        }

        orderConfirmationTable.setModel(model);
        restoreColumnWidths();
        orderConfirmationTable.getTableHeader().addMouseListener(new MouseAdapter() {
         
        	// tabloların istediğim gibi ve sabit şekilde genişlik alması
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths();
            }
        });
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
       {//fiyatları okunabilirlik açısından sağa yaslamak
         {
          orderConfirmationTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
         }
       }
       


        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        orderConfirmationTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        orderConfirmationTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
     // align işlemleri
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            	// ürünlerin durumlarına göre renklerinin renderlanması
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                boolean isAvailable = (boolean) value;
                String shippingStatus = isAvailable ? "Available" : "Not Available";
                c.setForeground(isAvailable ? Color.GREEN : Color.RED);
                setText(shippingStatus);
                setHorizontalAlignment(SwingConstants.CENTER); 
                return c;
            }
        });

        // her zaman available ile başla  yukarıda
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        sorter.setComparator(4, (Boolean b1, Boolean b2) -> Boolean.compare(b2, b1)); 
        sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(4, SortOrder.ASCENDING))); 
        
        orderConfirmationTable.setRowSorter(sorter);
        JScrollPane userScrollPane = new JScrollPane(orderConfirmationTable);
        userScrollPane.setBounds(10, 90, 550, 250);
        frame.setTitle("OrderConfirmation Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.add(label);
        frame.add(backButton);
        frame.add(confirmButton);
        frame.add(cancelButton);
        frame.add(userScrollPane);
        // componentlerin frame e eklenmesi
      
    }
        
    private void confirmOrder(int orderId) {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to confirm this order?", "Confirm Order", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // orderı APPROVED ile değiştirme
        	// database de tabloyu da aynı zamanda update etmesi
            try {
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

                String sql = "UPDATE orders SET order_state = 'APPROVED' WHERE order_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, orderId);
                int updatedRows = stmt.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(frame, "Order confirmed successfully"); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to confirm order");
                }
                
                conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error confirming order");
            }
        }
    }

    private void cancelOrder(int orderId) {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel this order?", "Cancel Order", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
        	// orderı DECINED ile değiştirme
            try {
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

                String sql = "UPDATE orders SET order_state = 'DECLINED' WHERE order_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, orderId);
                int updatedRows = stmt.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(frame, "Order canceled successfully");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to cancel order");
                }

                conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error canceling order");
            }
        }
    }
    
    private void saveColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderInspectionPage.class);
        TableColumnModel columnModel = orderConfirmationTable.getColumnModel();
        for (int i = 0; i < orderConfirmationTable.getColumnCount(); i++) {
            String columnName = orderConfirmationTable.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
 // kolonların ayarlanmış şekilde kalması için methodlar
    private void restoreColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderInspectionPage.class);
        TableColumnModel columnModel = orderConfirmationTable.getColumnModel();
        for (int i = 0; i < orderConfirmationTable.getColumnCount(); i++) {
            String columnName = orderConfirmationTable.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
}


        
        
        
        
        
        
        
        
        




















        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        