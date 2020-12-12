// Martina Trigilia 
package pr2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class ArrayListDataContainter<E> implements SecureDataContainer<E> {
	//REP
	private  ArrayList<Vector<E>> dati;
	private  ArrayList<Utente> profilo;
	private int dim;
	
	//Abstraction Function
	//AF: < <profilo.get(i).getUserName(), profilo.get(i).getPassword()>, dati.get(i) | 0<=i< dim. >

	// Rep Invariant
	// IR : dati !=null
			// &&  profilo != null 
			// && for all i, 0<= i< dim ==> profilo.get(i)!= null
			// && for all i, 0<= i< dim ==> dati.get(i)!= null
			// && for all i,j.  0<=i<j<dim ==> profilo.get(i).getUserName() != profilo.get(j).getUserName()	
	
		public  ArrayListDataContainter() {
			dati= new ArrayList<Vector<E>>();
			profilo= new ArrayList<Utente>();
			dim=0;
		}
		
	// Inserisco dei paramentri non null e verifico che l'utente non sia già presente nella collezione
	// degli utenti
	public void createUser(String Id, String passw) throws NullPointerException,IllegalArgumentException{
		if (Id==null || passw==null) throw new NullPointerException();
		if(this.indexOf(Id)!=-1) throw new IllegalArgumentException();
		Vector<E> dato=new Vector<E>(); 
		Utente utente=new Utente(Id,passw);
		dati.add(dim,dato); // inserisco un nuovo Vector<E> vuoto in dati in posizione dim
		profilo.add(dim,utente); // inserisco un nuovo utente in profilo in posizione dim
		dim ++; // incremento il numero degli utenti iscritti alla collezione utenti
	}
	
	// Inserisco dei paramentri non null e verifico che Owner sia un utente della collezione
	public int getSize(String Owner, String passw) throws NullPointerException,IllegalArgumentException{
		if (Owner==null || passw==null) throw new NullPointerException();
		int index=this.indexOf(Owner); 
		if(index==-1) throw new IllegalArgumentException(); //Owner non è presente nella collezione 
		return (dati.get(index).size());
	}
	
	// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
	// e che la password inserita sia corretta.
	public boolean put(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
		if (Owner==null || passw==null || data==null ) throw new NullPointerException();
		int index=this.indexOf(Owner);
		if(index==-1) throw new IllegalArgumentException();
		if(verifyId(Owner,passw) == false)  throw new WrongPasswordException(); // faccio un controllo delle credenziali di accesso
		dati.get(index).addElement(data);
		return true;
	}
	
	// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
	// e che la password inserita sia corretta.
	public E get(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
		if (Owner==null || passw==null || data==null ) throw new NullPointerException();
		int index=this.indexOf(Owner);
		if(index==-1) throw new IllegalArgumentException();
		if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
		if(dati.get(index).contains(data)) { //controllo che data è presente nella collezione di dati di Owner
			E copy=data; //creo una shallow copy
			return copy;
		}
		else 
			throw new IllegalArgumentException(); // se non è presente lancio eccezione
	}
	
	// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
	// e che la password inserita sia corretta.
	public E remove(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
		if (Owner==null || passw==null || data==null ) throw new NullPointerException();
		int index=this.indexOf(Owner);
		if(index==-1) throw new IllegalArgumentException();
		if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
		if(dati.get(index).contains(data)) { //controllo che data è presente nella collezione di dati di Owner
			E tmp=data; // mi salvo il dato in una variabile tmp
			dati.get(this.indexOf(Owner)).remove(data);
			return tmp;
		}
		else 
			throw new IllegalArgumentException();
	}
	
	// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
	// e che la password inserita sia corretta.
	public void copy(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
		if (Owner==null || passw==null || data==null ) throw new NullPointerException();
		int index=this.indexOf(Owner);
		if(index==-1) throw new IllegalArgumentException();
		if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
		if(dati.get(index).contains(data)) { //controllo che data è presente nella collezione di dati di Owner
			E copy=data; // creo una copia 
			dati.get(index).addElement(copy); //aggiungo la copia nella collezione di dati di Owner
		}
		else 
			throw new IllegalArgumentException();
	}
	
	// Inserisco dei paramentri non null ,verifico che Owner e Other siano utenti della collezione
	// e che la password inserita sia corretta. Assumo che Owner possa condividere data
	// anche se questo è già presente nella collezione di Other.
	public void share(String Owner, String passw, String Other, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
		if (Owner==null || passw==null || data==null || Other==null) throw new NullPointerException();
		int index=this.indexOf(Owner);
		int index2=this.indexOf(Other);
		if(index==-1) throw new IllegalArgumentException();
		if(index2==-1) throw new IllegalArgumentException();
		if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
		if(dati.get(index).contains(data)) { //controllo che data è presente nella collezione di dati di Owner
			E copy=data; // creo una copia 
			dati.get(index2).addElement(copy); //aggiungo la copia nella collezione di dati di Other
		}
		else 
			throw new IllegalArgumentException();
	}
	// Inserisco dei paramentri non null ,verifico che Owner e Other siano utenti della collezione
	// e che la password inserita sia corretta.
	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
		if (Owner==null || passw==null) throw new NullPointerException();
		int index=this.indexOf(Owner);
		if(index==-1) throw new IllegalArgumentException();
		if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
		return new Myitr(dati.get(index).iterator()); //restituisco un iteratore sulla collezione di dati di Owner
	}

	private class Myitr implements Iterator<E> {
		
		private Iterator<E> myit; 
		
		public Myitr(Iterator<E> t) {
	        myit=t;
	    }
	    
	    public boolean hasNext() {
	        return myit.hasNext();
	    } 
		
	    public E next() {
	        return myit.next();
	    } 
		
	    public void remove() {
	        throw new UnsupportedOperationException("Remove non supportato");
	    }
	
	}
    //il metodo veridyId(String Owner,String passw) mi verifica che la password associata a 
	//Owner sia uguale alla password con la quale l'utente prova ad effettuare l'accesso
	public boolean verifyId(String Owner, String passw) throws NullPointerException{
		for(Utente i : profilo) {
			if(i.getUserName().equals(Owner) && i.getPassword().equals(passw))
				return true;
		}
		return false;
	}
	//il metodo getSizeUtente mi restituisce il numero di utenti appartenenti alla collezione
	public int getSizeUtente() {
		return dim;
	}
	
	//il metodo indexOf(String Owner) controlla che in profilo sia presente un oggetto di tipo Utente
	//tale che profilo.get(i).getUserName().equals(Owner)) e ne restituisce la posizione all'interno dell'ArrayList
	// oppure restituisce -1 se Owner non è presente nella collezione degli utenti
	public int indexOf(String Owner) {
		for(int i=0; i<profilo.size(); i++) {
			if(profilo.get(i).getUserName().equals(Owner)) 
					return i;
		}
		return -1;
	}
}