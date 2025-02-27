package customerPages;

import javax.swing.*;
import welcomePages.WelcomePage;
import workerPages.ItemManagementPage;

import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.prefs.Preferences;

import connectDB.JDBCUtil.ShipmentStatus;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyOrdersPage {
    private JFrame frame = new JFrame("My Orders");
    private JLabel welcomeLabel = new JLabel();
    private JTable ordersTable = new JTable();
    private JComboBox<String> orderStateComboBox = new JComboBox<>(new String[]{"", "READY", "SHIPPED", "DECLINED", "APPROVED", "PENDING", "AWAITING_CONFIRMATION", "COMPLETED"});
    private JLabel filterLabel = new JLabel("Filter Your Orders:");
    private JButton backButton = new JButton("Back to Main Menu");
    private boolean hasAwaitingConfirmationOrders = false;
// frame de bulunacak componentlar
    public MyOrdersPage(String userTaxNumber) {
        frame.getContentPane().setLayout(null);

        welcomeLabel.setText("My Orders");
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 25));
        welcomeLabel.setBounds(20, 20, 200, 30);
        frame.getContentPane().add(welcomeLabel);

        ordersTable.setBounds(20, 70, 760, 400);
        frame.getContentPane().add(ordersTable);

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBounds(20, 70, 380, 300);
        frame.getContentPane().add(scrollPane);

        orderStateComboBox.setBounds(140, 400, 150, 30);
        frame.getContentPane().add(orderStateComboBox);

        filterLabel.setBounds(20, 400, 150, 30);
        frame.getContentPane().add(filterLabel);

        backButton.setBounds(20, 450, 150, 30);
        frame.getContentPane().add(backButton);
//frame de bulunan componentların büyüklüklerini ayarlamak ve yerlerini belirlemek
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Order ID", "Order Date", "Order State", "Total Price"}, 0);
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");

            String sql2 = "SELECT customer_id FROM customers WHERE customer_tax_number = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, userTaxNumber);
            ResultSet rs2 = stmt2.executeQuery();

            int customerId = -1; 
            if (rs2.next()) {
                customerId = rs2.getInt("customer_id");
            } else {
                JOptionPane.showMessageDialog(null, "No customer found with the provided tax number");
                return; 
            }    
//data baseden customer tablosunu seçip hali hazırda myorderspage e gelen taxnumberdan kullanıcı id sini çekmek.
            ordersTable.setDefaultEditor(Object.class, null);

            String sql = "SELECT o.order_id, o.order_date, o.order_state, SUM(oi.price * oi.quantity) AS total_price FROM orders o " +
                    "INNER JOIN furniture_order_items oi ON o.order_id = oi.order_id " +
                    "WHERE o.customer_address_customeridfk = ? " +
                    "GROUP BY o.order_id, o.order_date, o.order_state, o.customer_address_addressidfk " +
                    "ORDER BY CASE WHEN o.order_state = 'AWAITING_CONFIRMATION' THEN 0 ELSE 1 END, o.order_state";
       PreparedStatement stmt = conn.prepareStatement(sql);
       stmt.setInt(1, customerId);
       ResultSet rs = stmt.executeQuery(); 
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("order_id"), rs.getDate("order_date"), ShipmentStatus.valueOf(rs.getString("order_state")), rs.getDouble("total_price")});
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching orders from database");
        }
        // tabloyu doldurmak için databaseden istenilen şekile göre özel kolonların çekilmesi
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
            private final NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText(format.format(value));
                } else {
                    super.setValue(value);
                }
            }
        };
       
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        ordersTable.setModel(model);
        ordersTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        ordersTable.getColumnModel().getColumn(2).setCellRenderer(new ShipmentStatusRenderer());
        ordersTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        restoreColumnWidths();
        ordersTable.getTableHeader().addMouseListener(new MouseAdapter() {  
            public void mouseReleased(MouseEvent e) {
                saveColumnWidths();
            }
        });
//tablonun kolonlarının align edilmesi ve filter edilmesi
        orderStateComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterOrdersByState((String) orderStateComboBox.getSelectedItem(), model);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new WelcomePage(userTaxNumber);
            // geri sayfaya geçmek için butona action listener
        });

        ordersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int selectedRow = ordersTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int orderId = (int) ordersTable.getValueAt(selectedRow, 0); 
                        openOrderConfirmationPage(userTaxNumber);
                        // row ile confirmationpage açılması.
                    }
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
        // sayfanın açılacağı büyüklüğü ayarlamak ve ekrana merkezleme
    }
    
    private void openOrderConfirmationPage(String userTaxNumber) {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow != -1) {
            int orderId = (int) ordersTable.getValueAt(selectedRow, 0); 
            new OrderConfirmationPage(userTaxNumber, orderId); 
            frame.dispose();
            //orderconfirmation page i buradan çekilen (userTaxNumber, orderId) ile açmak
        }
    }

    private void filterOrdersByState(String selectedState, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        ordersTable.setRowSorter(sorter);
        if (selectedState.equals("")) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter(selectedState, 2);
            sorter.setRowFilter(rowFilter);
        }
    }

    private static class ShipmentStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,  Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value instanceof ShipmentStatus) {
                ShipmentStatus status = (ShipmentStatus) value;
                String statusText = status.toString();
                c.setForeground(getColorForStatus(status));
                setText(statusText);
            }
            // durumları göstermek için kullanılan component ve renklerinin durumlara göre değişiklik göstermesi
            setHorizontalAlignment(JLabel.CENTER); 
            return c;
        }

        private Color getColorForStatus(ShipmentStatus status) {
            switch (status) {
                case READY:
                    return Color.GREEN;
                case SHIPPED:
                    return Color.GREEN;
                case DECLINED:
                    return Color.RED;
                case APPROVED:   
                     return Color.GREEN;
                case AWAITING_CONFIRMATION:
                    return Color.ORANGE;
                case COMPLETED:
                    return Color.GREEN;

                default:
                    return Color.BLACK; 
            }
        }
    }
    
    public boolean hasAwaitingConfirmationOrders() {
        return hasAwaitingConfirmationOrders;
    }
    private void saveColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(ItemManagementPage.class);
        TableColumnModel columnModel = ordersTable.getColumnModel();
        for (int i = 0; i < ordersTable.getColumnCount(); i++) {
            String columnName = ordersTable.getColumnName(i);
            int width = columnModel.getColumn(i).getWidth();
            prefs.putInt(columnName, width);
        }
    }
// kolonların ayarlanmış şekilde kalması için methodlar
    private void restoreColumnWidths() {
        Preferences prefs = Preferences.userNodeForPackage(ItemManagementPage.class);
        TableColumnModel columnModel = ordersTable.getColumnModel();
        for (int i = 0; i < ordersTable.getColumnCount(); i++) {
            String columnName = ordersTable.getColumnName(i);
            int width = prefs.getInt(columnName, 100); 
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
}























