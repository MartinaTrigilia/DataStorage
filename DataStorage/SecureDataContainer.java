//Martina Trigilia
package pr2;

import java.util.Iterator;

public interface SecureDataContainer<E>{
	
	//OVERVIEW: Tipo modificabile di una collezione di oggetti di tipo E. 
	
		//TYPICAL ELEMENT : <utente-i,passw-i, <obj-E0,...,obj-En-1>> | 0<=i<this.getSizeUtente(),
		// && n=this.getSize(utente-i,passw-i) && for all i,j. 0<=i<j<getSizeUtente() => (utente-i)!= (utente-j)
		
		// Crea l’identità di un nuovo utente della collezione
	    public void createUser(String Id, String passw) throws NullPointerException,IllegalArgumentException;
	   /*
		REQUIRES: Id!=null && passw!=null e Id non è già presente nella collezione.
		THROWS: se (Id==null || passw==null) lancia NullPointerException(dispoibile in Java, unchecked)
	        && se esiste utente-i tale che utente-i.equals(Id) | 0<=i<this.getSizeUtente(), lancia IllegalArgumentException(disponibile in Java,unchecked)
		MODIFIES: la collezione degli utenti.
		EFFECTS: aggiunge Id e password di un utente senza alcun dato, se non è già presente nella collezione.
	   */  
		
		// Restituisce il numero degli elementi di un utente presenti nella collezione.
		public int getSize(String Owner, String passw) throws NullPointerException,IllegalArgumentException;
		/*
		 REQUIRES: Owner!=null && passw!=null e Owner utente esistente della collezione.
		 THROWS: se (Owner==null || passw==null) lancia NullPointerException(disponibile in Java, unchecked)
		 && se Owner non è presente nella collezione lancia IllegalArgumentException(disponibile in Java,unchecked)
		 EFFECTS : restituisce la cardinalita' della collezione  <obj-E0, ... , obj-En> relativa all'utente Owner con password passw.
		*/
		
		// Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità
		public boolean put(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException;
		/*
		 REQUIRES: Owner!=null && passw!=null  && Owner utente esistente della collezione.
		 THROWS: se (Owner==null || passw==null) lancia NullPointerException(dispoibile in Java, unchecked)
		 && se Owner non è presente nella collezione lancia IllegalArgumentException(disponibile in Java,unchecked)
		 && se non vengono rispettati i controlli di identità lancia WrongPasswordException(non disponibile in Java, checked)
		 MODIFIES: la collezione di Owner
	     EFFECTS:  se vengono rispettati i controlli di identità inserisce un nuovo dato nella collezione <obj-E0,...,obj-En>
	      relativa all'utente Owner e ritorna true.
		*/
		
		// Ottiene una copia del valore del dato nella collezione
		// se vengono rispettati i controlli di identità
		public E get(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException;
		/*
		 REQUIRES:  Owner!=null && passw!=null && data!=null  && Owner utente esistente della collezione && data è un dato di Owner
	     THROWS: se (Owner==null || passw==null || data==null) lancia NullPointerException(dispoibile in Java, unchecked)
		 && se Owner non è presente nella collezione lancia IllegalArgumentException(disponibile in Java,unchecked)
	         && se data non è presente nella collezione <obj-E0, ... , obj-En> relativa all'utente Owner lancia IllegalArgumentException
	         && se non vengono rispettati i controlli di identità lancia WrongPassword(non dispoibile in Java, checked)
		 EFFECTS: se vengono rispettati i controlli di identità restituisce una copia di data.
		*/
		
		// Rimuove il dato nella collezione se vengono rispettati i controlli di identità
		public E remove(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException;
		/*
		 REQUIRES:  Owner!=null && passw!=null && data!=null && Owner utente esistente della collezione && data è un dato di Owner
		 THROWS: se (Owner==null || passw==null|| data==null) lancia NullPointerException(dispoibile in Java, unchecked)
		 && se Owner non è presente nella collezione lancia IllegalArgumentException(disponibile in Java,unchecked)
		 && se data non è presente nella collezione <obj-E0, ... , obj-En> relativa all'utente Owner lancia IllegalArgumentException
		 && se non vengono rispettati i controlli di identità lancia WrongPassword(non dispoibile in Java, checked)
		 MODIFIES: la collezione di Owner.
		 EFFECTS: se vengono rispettati i controlli di identità  elimina data dalla collezione <obj-E0,...,obj-En-1> di Owner e ritorna una copia di data.
		*/
		
		// Crea una copia del dato nella collezione se vengono rispettati i controlli di identità
		public void copy(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException;
		 /* 
		  REQUIRES:  Owner!=null && passw!=null && data!=null && Owner utente esistente della collezione && data è un dato di Owner.
		  THROWS: se (Owner==null || passw==null || data==null) lancia NullPointerException(dispoibile in Java, unchecked)
		  && se Owner non è presente nella collezione lancia IllegalArgumentException(disponibile in Java,unchecked)
		  && se data non è presente nella collezione <obj-E0, ... , obj-En> relativa all'utente Owner lancia IllegalArgumentException
		  && se non vengono rispettati i controlli di identità lancia WrongPassword(non dispoibile in Java, checked)
		  MODIFIES: la collezione di Owner.
		  EFFECTS: se vengono rispettati i controlli di identità crea una copia di data e la aggiunge alla collezione <obj-E0, ... , obj-En> relativa all'utente Owner
		*/
		
		// Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
		public void share(String Owner, String passw, String Other, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException;
		/*
		 REQUIRES:  Owner!=null && passw!=null && data!=null && Other!=null && Owner e Othere utenti esistenti della collezione && data è un dato di Owner.
		 THROWS: se (Owner==null || passw==null || data==null || Other==null) lancia NullPointerException(dispoibile in Java, unchecked)
	         && se Owner non è presente nella collezione || Other non è presente nella collezione  lancia IllegalArgumentException(disponibile in Java,unchecked)
		 && se non vengono rispettati i controlli di identità lancia WrongPassword(non dispoibile in Java, checked)
		 && se Other non appartiene agli utenti della collezione lancia IllegalArgumentException
		 && se data non è presente nella collezione <obj-E0, ... , obj-En> relativa all'utente Owner lancia IllegalArgumentException
	     MODIFIES: la collezione di Other
		 EFFECTS: se vengono rispettati i controlli di identità condivide il dato con un altro utente Other, aggiungendo una copia di dato alla collezione di Other.
		*/
		
		// restituisce un iteratore (senza remove) che genera tutti i dati
		//dell’utente in ordine arbitrario
		// se vengono rispettati i controlli di identità
		public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException,WrongPasswordException,IllegalArgumentException;
		/* 
		  REQUIRES:  Owner!=null && passw!=null && data!=null e Owner utente esistente della collezione && data è un dato di Owner.
		  THROWS: se (Owner==null || passw==null || data==null) lancia NullPointerException(dispoibile in Java, unchecked)
		  && se Owner non è presente nella collezione lancia IllegalArgumentException(disponibile in Java,unchecked)
		  && se data non è presente nella collezione <obj-E0, ... , obj-En> relativa all'utente Owner lancia IllegalArgumentException
		  && se non vengono rispettati i controlli di identità lancia WrongPassword(non dispoibile in Java, checked)
		  EFFECTS: se vengono rispettati i controlli di identità restituisce un oggetto Iterator<E> sulla collezione di Owner.
		*/

	    //Verifica che vengano rispettati i controlli di identità
	    public boolean verifyId(String Owner, String passw);
	    //REQUIRES:  Owner!=null && passw!=null 
	    //THROWS: se (Owner==null || passw==null || data==null) lancia NullPointerException(dispoibile in Java, unchecked)
	    //EFFECTS: ritorna true se Owner appartiene a this con password passw, altrimenti false.

		// ritorna il numero degli utenti.
		public int getSizeUtente();
		//EFFECTS : ritorna il numero degli utenti presenti in this.
		
	    
	    //Lanciata se una password non è corretta
	    class WrongPasswordException extends Exception {
	    	private static final long serialVersionUID = 1L;
	        public WrongPasswordException() {
	            super();
	        }
	    }
}