package supplier;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//supplier ı databaseden çekmek için kullanılan kod
public class SupplierLoader {
    public static List<Supplier> loadSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try {

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "SELECT * FROM furniture_suppliers"; 
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	
                int supplierId = resultSet.getInt("supplier_id");
                
                String supplierName = resultSet.getString("name");
                
                Supplier supplier = new Supplier(supplierId, supplierName);
                
                suppliers.add(supplier);
                
            }

            conn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return suppliers;
    }
}