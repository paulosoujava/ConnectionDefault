package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import connection.ConnectionSingleton;
import model.Login;

/*
 * Responsavel por deleta um usuario
 * pelo ID
 */
public class Delete {

	// para consultar no banco de dados
		PreparedStatement stmt = null;
		private static Connection conexao = null;

		/***************************************************************************
		 * STRING SQL
		 * 
		 ****************************************************************************/
		private final String SQL_DELETE_BY_ID = "DELETE  FROM  login WHERE id_login = ?";
		private final String SQL_SELECT_BY_ID = "SELECT * FROM login WHERE id_login  = ?";
	
		public Delete() {

			// otem a CONEXAO e
			conexao = ConnectionSingleton.getInstacia().getConector();
		}
		
		// ********************************************************************************************************
		// INSERE UM LOGIN NA TABELA
		// RETURN: ID DO INSERIDO
		// 0 = NAO INCLUIDO
		// ********************************************************************************************************
		public Boolean deleteUser( Login login ) {
			
			//verifica se existe o id
			if( this.isLogin(login) <= 0 ){
				System.out.println("Id Ja deletado");
				return false;
			}

			try {
				 stmt = conexao.prepareStatement( SQL_DELETE_BY_ID );
				stmt.setInt(1, login.getId());
				
				// executa
				   
				  if( stmt.executeUpdate() >= 1 ){
					  System.out.println("Deletado com sucesso ");
					  stmt.close();
						return true;
				  }

			} catch (SQLException e) {
				System.out.println("Erro ao deletar login : " + e.getMessage());
				e.printStackTrace();
				return false;
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
			return false;
		}
		/************************************************************************************************
		 * VERIFICA SE  EXISTE O ID DO USUARIO
		 ************************************************************************************************/
		public Integer isLogin(Login login) {

			int idLogin = 0;
			try {
					stmt = conexao.prepareStatement(SQL_SELECT_BY_ID);
					stmt.setMaxRows(1);
					stmt.setInt(1, login.getId());
					// executa
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						idLogin =  (Integer.parseInt(rs.getString("id_login")));
					}
		
					stmt.close();
					return idLogin;

			} catch (SQLException e) {
				System.out.println("Erro ao obter login: " + e.getMessage());
				e.printStackTrace();
				return idLogin;
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

		}
}
