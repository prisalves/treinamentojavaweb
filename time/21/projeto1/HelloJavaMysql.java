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
			String dbPassword = "admin"; 
			Class.forName(dbDriver);
			Connection con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword); 
			
			int cadastro = 1;
			// Cadastrar
			if(cadastro == 1) {
				PreparedStatement pst = con.prepareStatement("INSERT INTO funcionarios (`celular`, `cliente_alocado`, `email`, `funcao`, `nome`, `usuario_github`) VALUES (?,?,?,?,?,?) ");
				pst.setString(1, "11999006803"); // Celular
				pst.setString(2, "Interno"); // Cliente Alocado
				pst.setString(3, "samuel.a.silva@capgemini.com"); // Email
				pst.setString(4, "Analista de Sistemas"); // Função dentro da CAP
				pst.setString(5, "Samuel Soares da Silva"); // Nome Completo
				pst.setString(6, "doctorget"); // Usuario do Github
				pst.executeUpdate();
				pst.close();
			
			}

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
