package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectionSingleton;
import model.Login;

/*
 * Responsavel por editar um usuario
 */
public class Update {
	
	// para consultar no banco de dados
	PreparedStatement stmt = null;
	private static Connection conexao = null;

	/***************************************************************************
	 * STRING SQL
	 * 
	 ****************************************************************************/
	private final String SQL_UPDATE_BY_ID = "UPDATE login SET log_email= ?, log_senha = ? WHERE id_login = ?";
	

	public Update() {

		// otem a CONEXAO e
		conexao = ConnectionSingleton.getInstacia().getConector();
	}
	
	public Boolean updateUser(Login login) {

		Boolean idInserido = false;

	
		
		try {

			   stmt = conexao.prepareStatement(SQL_UPDATE_BY_ID );
				// seta os valores
				stmt.setString(1, login.getLogin());
				stmt.setString(2, login.getSenha());
				stmt.setInt(3, login.getId());
				
	 
				stmt.executeUpdate();
	
			
				if ( stmt.executeUpdate() > 0) {
					idInserido = true;
				}
	
				if (idInserido ) {
					System.out.println("Login alterado com sucesso");
				} else {
					System.out.println("Erro ao alterar login");
				}
	
				stmt.close();
	
				return idInserido;

		} catch (SQLException e) {
			System.out.println("Erro ao alterar  o login: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// FECHANDO STATAMENT
			// AO MEU VER eh MAIS INTERESSANTE DEIXAR A CONEXAO ABERTA
			// ATE O FIM DA APLICACAO POIS COMO EH UM SONGLETON
			// TEREMOS APENAS UMA INSTANCIA DO COMECAO AO FIM
			try {
				stmt.close();
				// conexao.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar conexao / statement " + e.getMessage());
				e.printStackTrace();

			}
		}
		return idInserido;
	}


}
