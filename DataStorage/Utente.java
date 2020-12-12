//Martina Trigilia 
package pr2;

public class Utente {
	//OVERVIEW: Tipo utilizzato per rappresentare una coppia costituita da un nome utente e password
		//Typical Element: <username,password>
		private String username;
		private String password;
	

		public Utente(String username, String password) {
			this.username = username;
			this.password= password;
		}
		public String getUserName() {
			return this.username;
		}
		public String getPassword() {
			return this.password;
		}

}
