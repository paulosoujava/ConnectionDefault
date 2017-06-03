package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionSingleton;
import model.Login;

/*
 * RESPONSAVEL POR CRIAR UM ITEM login 
 * 	NA TABELA DO BANCO DE DADOS
 */
public class Create {

	// para consultar no banco de dados
	PreparedStatement stmt = null;
	private static Connection conexao = null;

	/***************************************************************************
	 * STRING SQL
	 * 
	 ****************************************************************************/
	private final String SQL_SELECT_ONE_BY_DATA = "SELECT * FROM login WHERE log_email = ?";
	private final String SQL_INSERT = "INSERT INTO login (log_nivel, log_email, log_senha)" +
	                                            " VALUES (?,         ?,          ?       );";

	public Create() {

		// otem a CONEXAO e
		conexao = ConnectionSingleton.getInstacia().getConector();
	}

	// ********************************************************************************************************
	// INSERE UM LOGIN NA TABELA
	// RETURN: ID DO INSERIDO
	// 0 = NAO INCLUIDO
	// ********************************************************************************************************
	public int createUser(Login login) {

		int idInserido = 0;

		//VERIFICACAO DE EXISTENCIA
		if( this.isLogin(login) > 0 ){
			System.out.println("Sorry, exist a user with this email.");
			return 0;
		}
		
		try {

			PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			// seta os valores
			stmt.setInt(1, 0); // nivel default = 0
			stmt.setString(2, login.getLogin());
			stmt.setString(3, login.getSenha());

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				idInserido = rs.getInt(1);
			}

			if (idInserido > 0) {
				System.out.println("Login criado com sucesso");
			} else {
				System.out.println("Erro ao inserir login");
			}

			stmt.close();

			return idInserido;

		} catch (SQLException e) {
			System.out.println("Erro ao inserir login: " + e.getMessage());
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

	/************************************************************************************************
	 * VERIFICA SE JA NAO EXISTE UM EMAIL CADASTRADO RETORNO ID DO USUARIO
	 ************************************************************************************************/
	public Integer isLogin(Login login) {

		try {
				stmt = conexao.prepareStatement(SQL_SELECT_ONE_BY_DATA);
				stmt.setMaxRows(1);
				stmt.setString(1, login.getLogin());
				// executa
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					return (Integer.parseInt(rs.getString("id_login")));
				}
	
				stmt.close();
				return 0;

		} catch (SQLException e) {
			System.out.println("Erro ao obter login: " + e.getMessage());
			e.printStackTrace();
			return 0;
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
