package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/* Author: Paulo Jorge de Oliveira
 * 
 * Conexao  banco de dados
 * Design Pattern
 * Singleton
 */
public class ConnectionSingleton {

	
	private static Connection conexao = null;
	private static ConnectionSingleton self = null;
	
	//********************************************************************************
	 /* CONFIGURACAOSE DE DRIVE, URL, USUARIO E SEHA
	 */
	    // This will disable SSL and also suppress the SSL errors. = ?autoReconnect=true&useSSL=false
	
	//NOMDE DO BANCO
		private static final String BANCO = "newEgresso";
	//MYSQL 
		private static final String  URL = "jdbc:mysql://localhost/"+BANCO +"?autoReconnect=true&useSSL=false";
		private static final String DRIVE = "com.mysql.jdbc.Driver";
	//POSTGRES	
		private static final String  URLPOST = "jdbc:postgresql://localhost/"+BANCO +"?autoReconnect=true&useSSL=false";
		private static final String DRIVEPOST = "org.postgresql.Driver";
	//USUARIO E SENHA	
		private static final String USUARIO = "root";
		private static final String SENHA = "root";
		
	 //********************************************************************************//
		
		
	public Connection getConector( ){
		
		
		try {
			if (conexao == null) {
				
				 Class.forName( DRIVE );
				 conexao = DriverManager.getConnection( URL, USUARIO, SENHA );
				
				 System.out.println("Conexão aberta");
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao obter conexão com o banco");
			throw new RuntimeException(e);
		}
 
		
		return conexao;
	}
	
	//ACESSO SONCRONISSADO PARA NAO ACONTER RECE-CONDITION
	//CASO UMA THEAD CHAME TERA QUE ESPERAR PELO FINAL DA OUTRA
	public static synchronized ConnectionSingleton getInstacia() {
		if (self == null)
			return new ConnectionSingleton();
		else
			return self;
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		if (self != null) {
			desconectar();
		}
	}
	
	
	public void desconectar() {
		try {
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
