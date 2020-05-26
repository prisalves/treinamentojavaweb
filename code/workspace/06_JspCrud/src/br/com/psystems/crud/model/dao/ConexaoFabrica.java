/**
 * 
 */
package br.com.psystems.crud.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import br.com.psystems.crud.exception.PersistenciaException;


public class ConexaoFabrica {

	private static final String STR_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE = "produto-crud?useSSL=false&createDatabaseIfNotExist=true";
	private static final String STR_CON = "jdbc:mysql://localhost:3306/" + DATABASE;
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	private static Logger logger = Logger.getLogger(ConexaoFabrica.class);

	public static Connection getConexao() throws PersistenciaException {
		
		Connection conn = null;
		String mensagem =  "Conexão com o banco de dados obtida com sucesso.";
		
		try {
			
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			conn.setAutoCommit(false);
			
			logger.info(mensagem);
			
			return conn;
		} catch (ClassNotFoundException e) {
			
			mensagem = "Driver (JDBC) não encontrado";
			logger.error(mensagem, e);
			throw new PersistenciaException("Driver (JDBC) não encontrado", e);
			
		} catch (SQLException e) {
			
			mensagem = "Erro ao obter a conexão";
			throw new PersistenciaException(mensagem, e);
		}
	}
	
	public static void fecharConexao(Connection connection) throws PersistenciaException {
		
		String mensagem =  "Conexão com o banco de dados fechada com sucesso.";
		
		try {
			if (null != connection) {
				connection.close();
				logger.info(mensagem);
			}
		} catch (Exception e) {
			mensagem = "Houve um erro ao fechar a conexão com o banco de dados.";
			logger.error(mensagem,e);
			throw new PersistenciaException(mensagem, e);
		}
	}
	
	public static void fecharConexaoEstatement(Connection connection, Statement statement) throws PersistenciaException {
		
		String mensagem =  "Instrução de banco de dados fechada com sucesso.";
		
		if (null != connection) {
			fecharConexao(connection);
		}
		
		try {
			if (null != statement) {
				statement.close();
			}
		} catch (Exception e) {
			mensagem = "Houve um erro ao fechar a instrução de banco de dados.";
			logger.error(mensagem, e);
			throw new PersistenciaException(mensagem, e);
		}
	}
	
	public static void fecharTodos(Connection connection, Statement statement, ResultSet resultset) throws PersistenciaException {
		
		String mensagem =  "Conjunto de resultados do banco de dados fechado com sucesso.";
		
		fecharConexaoEstatement(connection, statement);
		
		try {
			if (null != resultset) {
				resultset.close();
			}
		} catch (Exception e) {
			mensagem = "Houve um erro ao fechar o conjunto de resultados do banco de dados.";
			logger.error(mensagem, e);
			throw new PersistenciaException(mensagem, e);
		}
	}
}
