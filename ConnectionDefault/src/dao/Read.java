package dao;

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
 * AUTHOR: PAULO OLIVEIRE
 * EMAIL: PAULOSOUJAVA@GMAIL.COM
 * CLASSE RESPONSAVEL POR LEITURAS NO BANCO DE DADOS
 * LEITURA POR ID
 * LEITURA POR EMAIL
 * LEITURA DE TODOS OS LOGINs
 */

public class Read {

	// para consultar no banco de dados
	PreparedStatement stmt = null;
	private static Connection conexao = null;

	/***************************************************************************
	 * STRING SQL`S
	 * 
	 ****************************************************************************/
	private final String SQL_SELECT_ALL = "SELECT * FROM  login";
	private final String SQL_SELECT_BY_DATA = "SELECT * FROM login WHERE log_email = ?";
	private final String SQL_SELECT_BY_ID = "SELECT * FROM login WHERE id_login  = ?";

	public Read() {

		// otem a CONEXAO e
		conexao = ConnectionSingleton.getInstacia().getConector();
	}

	// ********************************************************************************************************
	// SQL_SELECT_BY_ID OR SQL_SELECT_BY_DATA
	// INTEGER ACTION_WHAT (0,1) 0 -> ID 1-> EMAIL
	// RETORNA UM OBEJTO LOGIN
	// ********************************************************************************************************
	public Login readLoginById(Login login, Integer action_what) {

		Login log = null;
		try {

			if (action_what == 1) {
				// BY EMAIL
				// pega a conexao prepara a query com a string passada e executa
				stmt = conexao.prepareStatement(SQL_SELECT_BY_DATA);
				stmt.setString(1, login.getLogin());

			} else {
				// BY ID
				// pega a conexao prepara a query com a string passada e executa
				stmt = conexao.prepareStatement(SQL_SELECT_BY_ID);
				stmt.setInt(1, login.getId());
			}

			stmt.setMaxRows(1); // LIMIT = 1
			// executa
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				log = new Login();
				log.setLogin(rs.getString("log_email"));
				log.setSenha(rs.getString("log_senha"));
				log.setId(rs.getInt("id_login"));
			}
			stmt.close();
			return log;

		} catch (SQLException e) {

			System.out.println("Erro ao obter login by id: " + e.getMessage());
			e.printStackTrace();
			return log;

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


	// ********************************************************************************************************
	// SQL_SELECT_ONE_BY_ID RETORNA UM OBEJTO LOGIN
	// ********************************************************************************************************
	public List<Login> readAllLogin() {

		Login log = null;
		List<Login> mListLogin = new ArrayList<>();
		try {
			// pega a conexao prepara a query com a string passada e executa
			stmt = conexao.prepareStatement(SQL_SELECT_ALL);
			// executa
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				log = new Login();
				log.setLogin(rs.getString("log_email"));
				log.setSenha(rs.getString("log_senha"));
				log.setId(rs.getInt("id_login"));
				
				mListLogin.add(log);
			}
			stmt.close();
			
			return mListLogin;

		} catch (SQLException e) {

			System.out.println("Erro ao obter login by id: " + e.getMessage());
			e.printStackTrace();
			return mListLogin;

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

	
	
	
	
	/*************************************************************************************
	 * Leitura de todos os dados da tabela passada Este method serve para caso
	 * nao saibamos quantas colunas tem a tabela e quais os nomes das colunas
	 * 
	 */
	public void ReadAllData(String witchTable) {
		try {

			// pega a conexao prepara a query com a string passada e executa
			stmt = conexao.prepareStatement(SQL_SELECT_ALL);
			stmt.setString(1, witchTable);
			// executa
			ResultSet rs = stmt.executeQuery();
			// obtem os metadatas para sabermos nomes de colunas e quantas
			// colunas tem
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			int aux = 1;

			while (rs.next()) {
				do {

					System.out.println(rsmd.getColumnName(aux) + " - " + rs.getString(rsmd.getColumnName(aux)) + "\n ");
					aux++;
				} while (aux <= numColumns);

				aux = 1;

			}

		} catch (SQLException e) {
			System.err.println("Opss, erro ao obter dados");
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.err.println("Opss, erro ao tentar fechar as conexoes");
				e.printStackTrace();
			}
		}

	}

}
