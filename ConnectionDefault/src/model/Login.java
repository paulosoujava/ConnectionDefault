package model;

/*
 *  POJO
 *  Modelo de login
 */
public class Login {
	
	private String login;
	private String senha;
	private Integer id;
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Login [login=" + login + ", senha=" + senha + ", id=" + id + "]";
	}
	
	

}
