import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class HelloJavaMysql {
    public static void main(String[] args) {
        try {
			
			String dbDriver = "com.mysql.cj.jdbc.Driver"; 
            String dbURL = "jdbc:mysql://191.252.0.230:3306/"; 
            String dbName = "cap?useSSL=false"; 
            String dbUsername = "root"; 
            String dbPassword = ""; 
			Class.forName(dbDriver);
			Connection con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword); 

			Statement st = con.createStatement();
			ResultSet result = st.executeQuery("SELECT * FROM funcionarios");
			System.out.println("------------------------------------------------");
	        System.out.println("Funcionario \n");
	        int count = 1;
	        while(result.next()){
	            System.out.println(count++ + " ) Nome: " + result.getString("nome") + " / cel: " + result.getString("celular")  );
			}
			st.close();
			System.out.println("------------------------------------------------");
			
			
			// close
			con.close();

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
    }
}
