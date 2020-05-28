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
            String dbURL = "jdbc:mysql://localhost:3306/"; 
            String dbName = "cap?useSSL=false&useTimezone=true&serverTimezone=UTC"; 
            String dbUsername = "root"; 
            String dbPassword = "Cap@2021"; 
			Class.forName(dbDriver);
			Connection con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword); 
	
			int cadastro = 1;
			// Cadastrar
			if(cadastro == 1) {
				PreparedStatement pst = con.prepareStatement("INSERT INTO funcionarios (`celular`, `cliente_alocado`, `email`, `funcao`, `nome`, `usuario_github`) VALUES (?,?,?,?,?,?) ");
				pst.setString(1, "234567891"); // Celular
				pst.setString(2, "Cliente Alocado"); // Cliente Alocado
				pst.setString(3, "robson.b.silva@gmail.com"); // Email
				pst.setString(4, "Analista de Sistemas Jr"); // Função dentro da CAP
				pst.setString(5, "Robson Lima da Silva"); // Nome Completo
				pst.setString(6, "rdilimas"); // Usuario do Github
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
			System.out.println("Erro: " + e.getMessage());
		} 
	}
}
