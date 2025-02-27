package category;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryLoader {
	//kategorileri databaseden çekmek için kullanılan kod
    public static List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
        try {
        	
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "200201040");
            String sql = "SELECT * FROM furniture_categories";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
 
            while (resultSet.next()) {
            	
                int categoryId = resultSet.getInt("category_id");
                
                String categoryName = resultSet.getString("name");
         
                Category category = new Category(categoryId, categoryName);    
                categories.add(category);
                
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return categories;
    }
}
