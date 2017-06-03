import java.util.List;
import dao.Create;
import dao.Delete;
import dao.Read;
import dao.Update;
import model.Login;


public class Test {
	
	
	public static void main(String[] args) {
	
		
		//teste login id
		Read r = new Read();
		Login l = new Login();
		l.setId(1);
		
		//by ID
		// System.out.println( r.readLoginById(l, 0) );
	
		 
		//by EMAIL
		 l.setLogin("paulosoujava@gmail.com");
		// System.out.println( r.readLoginById(l, 1 ) );
		
		 //read ALL
		 for (Login log : r.readAllLogin()) {
		//	System.out.println(log.getLogin() );
		} 
		
	
		 Login pauloUpdate = new Login();
		 pauloUpdate.setId(21);
		 pauloUpdate.setLogin("pauloupdate@update.com");
		 pauloUpdate.setSenha("000");
		 Update up = new Update();
		 up.updateUser(pauloUpdate);
		
		Create c = new Create();
		 
		//insert a email that exits
		// c.createUser(l);
		//new User
		 Login paulo = new Login();
		 paulo.setLogin("paulo@paulo.com");
		 paulo.setSenha("123");
		// c.createUser(paulo);
		 
		 
		 //deletar id
		 Delete d = new Delete();
		 paulo.setId(19); //setando o id para deletar
		// d.deleteUser(paulo);
		 
		
		 //by I don`t know the name of column
		// c.genericCreateObject("login");
			
		
	}

}
