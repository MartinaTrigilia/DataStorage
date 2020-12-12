
//Martina Trigilia
package pr2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HashMapDataContainer<E> implements SecureDataContainer<E> {
	   //REP
			private Map<String,Dati<E>> table;
			private int dim;
			
			//Abstraction Function
			// AF: < Owner-i, table.get(Owner-i).getPassword(), table.get(Owner-i).getDati()  | 0<=i<dim >
	      
	     	// Rep Invariant
			// IR : table!=null
			// && for all i, 0<= i < dim  ==> Owner_i !=null
			// && for all i, 0<= i < dim  ==> table.get(Owner_i) != null
			// && for all i, 0<= i < dim  ==> table.get(Owner_i).getPassword() = password && table.get(Owner_i).getDati() = dati
			// && for all i,j. 0<i<j< dim ==> Owner_i != Owner_j	
			
			public  HashMapDataContainer() {
				table=new HashMap<String,Dati<E>>();
				dim=0;
			}
			 
			// Inserisco dei paramentri non null e verifico che l'utente non sia già presente nella collezione
			// degli utenti
			public void createUser(String Id, String passw) throws NullPointerException,IllegalArgumentException{
				if (Id==null || passw==null) throw new NullPointerException();
				if(table.containsKey(Id)==true) throw new IllegalArgumentException(); // se l'utente è già presente lancia una eccezione.
				dim++; //incremento il numero di utenti presenti nella collezione
				table.put(Id,new Dati<E>(passw));
			}
			
			// Inserisco dei paramentri non null e verifico che Owner sia un utente della collezione
			public int getSize(String Owner, String passw) throws NullPointerException,IllegalArgumentException{
				if (Owner==null || passw==null) throw new NullPointerException();
				if(table.containsKey(Owner)==false) throw new IllegalArgumentException(); // se l'utente non è presente lancia una eccezione.
				return (table.get(Owner).getDati().size());//ritorno la dimensione del vettore dove sono salvati i dati di Owner
			}
			
			// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
			// e che la password inserita sia corretta.
			public boolean put(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
				if (Owner==null || passw==null || data==null ) throw new NullPointerException();
				if(table.containsKey(Owner)==false) throw new IllegalArgumentException();
				if(verifyId(Owner,passw) == false)  throw new WrongPasswordException ();
				table.get(Owner).getDati().addElement(data); //aggiungo data nella collezione di Owner
				return true;
			}
			
			// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
			// e che la password inserita sia corretta.
			public E get(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
				if (Owner==null || passw==null || data==null ) throw new NullPointerException();
				if(table.containsKey(Owner)==false) throw new IllegalArgumentException();  
				if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();   
				if(table.get(Owner).getDati().contains(data)) {//controllo che data è presente nella collezione di Owner
					E copy=data; //creo una shallow copy
					return copy; 
				}
				else 
					throw new IllegalArgumentException();
			}
			
			// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
			// e che la password inserita sia corretta.
			public E remove(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
				if (Owner==null || passw==null || data==null ) throw new NullPointerException();
				if(table.containsKey(Owner)==false) throw new IllegalArgumentException();
				if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
				if(table.get(Owner).getDati().contains(data)){//controllo che data è presente nella collezione di Owner
					E tmp=data; // salvo data in tmp così lo posso ritornare 
					table.get(Owner).getDati().remove(data); //rimuovo data
					return tmp;
				}
				else
					throw new IllegalArgumentException();
			}
			
			// Inserisco dei paramentri non null ,verifico che Owner sia un utente della collezione 
			// e che la password inserita sia corretta.
			public void copy(String Owner, String passw, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
				if (Owner==null || passw==null || data==null ) throw new NullPointerException();
				if(table.containsKey(Owner)==false) throw new IllegalArgumentException();
				if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
				if(table.get(Owner).getDati().contains(data)){//controllo che data è presente nella collezione di Owner
					E copy=data; //creo una shallow copy
					table.get(Owner).getDati().addElement(copy); //aggiungo alla collezione di Owner una copia di data
				}
				else 
					throw new IllegalArgumentException();
			}
			
			// Inserisco dei paramentri non null ,verifico che Owner e Other siano utenti della collezione 
			// e che la password inserita sia corretta. Assumo che Owner possa condividere data
			// anche se questo è già presente nella collezione di Other.
			public void share(String Owner, String passw, String Other, E data) throws NullPointerException,WrongPasswordException,IllegalArgumentException{
				if (Owner==null || passw==null || data==null ) throw new NullPointerException();
				if(table.containsKey(Owner)==false) throw new IllegalArgumentException();
				if(verifyId(Owner,passw) == false)  throw new WrongPasswordException();
				if(table.get(Owner).getDati().contains(data)){ //controllo che data sia presente nella collezione di Owner
					if(table.containsKey(Other)) // Other deve essere un utente della collezione 
						table.get(Other).getDati().addElement(data);
					else
						throw new IllegalArgumentException(); // se Other non è un utente della collezione lancio una eccezione
				}
				else
					throw new IllegalArgumentException();
			}
		    
			// Inserisco dei paramentri non null ,verifico che Owner e Other siano utenti della collezione 
			// e che la password inserita sia corretta.
			public Iterator<E> getIterator(String Owner, String passw){
				return new Myitr(table.get(Owner).getDati().iterator());//restituisco un iteratore sulla collezione di dati di Owner
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
			//Owner sia uguale alla password con la quale l'utente prova ad effettuare l'accesso.
			public boolean verifyId(String Owner, String passw) throws NullPointerException,IllegalArgumentException{
				if(Owner==null || passw==null) throw new NullPointerException();
				if(table.containsKey(Owner)) {
					if(table.get(Owner).getPassword().equals(passw)) return true;
					else return false;
				}
				else throw new IllegalArgumentException();
			}
			
			//il metodo getSizeUtente mi restituisce il numero di utenti appartenenti alla collezione
			public int getSizeUtente() {
				return dim;	
			}
}