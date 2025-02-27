package workerPages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import welcomePages.WelcomePageWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.prefs.Preferences;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderManagementPage {
    JFrame frame = new JFrame("Order Management");
    JLabel label = new JLabel("Order Management");
    JButton backButton = new JButton("Back to Main Menu");
    JButton deleteButton = new JButton("Delete Selected Order");
    JTable table;

    public OrderManagementPage() {
        label.setBounds(50, 50, 300, 30);
        backButton.setBounds(50, 100, 200, 30);
        deleteButton.setBounds(270, 100, 200, 30);

        backButton.addActionListener(e -> {
            frame.dispose(); 
            new WelcomePageWorker("User ID ORDER"); 
        });
        // sayfaya componentların eklenmesi
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this order?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int orderId = (int) model.getValueAt(selectedRow, 0);
                    deleteOrder(orderId);
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
            }
        });//  delete butonu ile ordereın silinmesi


        frame.add(label);
        frame.add(backButton);
        frame.add(deleteButton);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("Order ID");
        model.addColumn("Order State");
        model.addColumn("Order Date");
        model.addColumn("Customer Id");
        model.addColumn("Customer Address");

// tablonun istenilen şekilde databaseden çekilip doldurulması
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            Statement statement = conn.createStatement();
            String query = "SELECT o.order_id, o.order_state, o.order_date, o.customer_address_CustomerIdFK, " +
                           "CONCAT(a.address_country, ', ', a.address_state, ', ', a.address_postal_code, ', ', a.address_district, ', ', " +
                           "a.address_avenue, ', ', a.address_street, ', ', a.address_rest) AS address " +
                           "FROM orders o " +
                           "JOIN address a ON o.customer_address_AddressIdFK = a.address_id " +
                           "ORDER BY CASE WHEN o.order_state = 'PENDING' THEN 0 ELSE 1 END, o.order_id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("order_id"),
                        resultSet.getString("order_state"),
                        resultSet.getDate("order_date"),
                        resultSet.getInt("customer_address_CustomerIdFK"),
                        resultSet.getString("address")
                };
                model.addRow(row);
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 700, 300);
        frame.add(scrollPane);

        frame.setSize(800, 500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table.setDefaultEditor(Object.class, null);
        table.setCellSelectionEnabled(false);
        restoreColumnWidths();
        
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths();
            }// sayfanın doldurulması ve tablodaki kolonların istenilen şekilde ayarlanıp o şekilde kalması
        });
// align işlemleri
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String orderState = (String) value;
                if (orderState.equals("PENDING")) {
                    c.setBackground(Color.ORANGE);
                } else if (orderState.equals("APPROVED")) {
                    c.setBackground(Color.GREEN);
                } else if (orderState.equals("DECLINED")) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(table.getBackground());
                }
                return c;
            }
        });// align işlemleri devamı ve durumlara göre arkaplan renkleri


        table.addMouseListener(new MouseAdapter() {
    // çift tıklama ile rowdaki id ye göre inspection page açılması
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int orderId = (int) table.getValueAt(selectedRow, 0); 
                        openOrderInspectionPage(orderId);
                    }
                }
            }
        });
    }

    private void deleteOrder(int orderId) {
        try {
         // siparişlerin tek tek silin mesi ve foreign key odlukalrı tablolardan da silinmesi öncelik önemli
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

            String deleteFurnitureOrderItemsQuery = "DELETE FROM furniture_order_items WHERE order_id = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteFurnitureOrderItemsQuery);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            String deleteShipmentsQuery = "DELETE FROM shipments WHERE order_id_fk1 = ?";
            stmt = conn.prepareStatement(deleteShipmentsQuery);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            String deleteOrderQuery = "DELETE FROM orders WHERE order_id = ?";
            stmt = conn.prepareStatement(deleteOrderQuery);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   // inspection page aaçılması orderId ye göre
    private void openOrderInspectionPage(int orderId) {
        frame.dispose();
        OrderInspectionPage orderInspectionPage = new OrderInspectionPage(orderId);
    }
    private void saveColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderManagementPage.class);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
// kolonların istenilen şekilde korunması için methodlar
    private void restoreColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(OrderManagementPage.class);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
}













































