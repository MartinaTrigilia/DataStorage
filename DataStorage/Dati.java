//Martina Trigilia
package pr2;

import java.util.Vector;

public class Dati<E> {
	//REP
	private String password;
	private Vector<E> dati;

	//Typical Element: <password, <obj-E0, ..., obj-En> > | n=dati.size();
	
		public Dati(String password) {
			this.password=password;
			dati=new Vector<E>();
		}
	public String getPassword() {
		return password;
	}
	
	public Vector<E> getDati(){
		return dati;
	}
}
