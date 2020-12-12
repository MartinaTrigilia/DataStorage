//Martina Trigilia
package pr2;

import java.util.Iterator;


import pr2.SecureDataContainer.WrongPasswordException;

public class Test {
	// vado a testare le mie implementazioni facendo l'ipotesi che i dati inseriti siano di tipo String
	public static void  testContainerString(SecureDataContainer<String> container) throws NullPointerException,IllegalArgumentException, WrongPasswordException{
		//creo degli utenti
		 String M="mariorossi", M_passw="mariorossi11";
		 String L="luigiverdi", L_passw="luigiverdi11";
		 String F="Francesco",  F_passw="francesco11";
	     container.createUser(M, M_passw);
	     container.createUser(L,L_passw);
	     container.createUser(F,F_passw);
	     
	     //provo a creare un utente con un username già presente nella collezione 
	     try{
	    	 container.createUser(M, M_passw);
	    	 
	     }
	     catch(IllegalArgumentException e) {
	    	    System.out.println("Utente già presente");
	     }
	     
	     //inserisco dei dati nella collezione di dati degli utenti
	     container.put(M,M_passw,"documento1.doc");
	     container.put(M,M_passw,"documento2.doc"); 
	     container.put(M,M_passw,"documento3.doc"); 
	     container.put(L,L_passw,"documento4.doc");
	     container.put(L,L_passw,"documento5.doc"); 
	     container.put(F,F_passw,"documento6.doc"); 
	     container.remove(M,M_passw, "documento1.doc");
	     container.copy(M,M_passw,"documento3.doc");
	     container.share(M, M_passw, L, "documento2.doc"); 
	    
	     //provo a condividere un dato che non è presente nella collezione di M
	     try {
	    	 container.share(M,M_passw,F,"documento5.doc");
	     }
	     catch(IllegalArgumentException e) {
	    	  System.out.println("File non trovato");
	     }
	    
	     //provo a fare una share con un utente che non è presente nella collezione
	     try{
	    	 container.share(M, M_passw, "Carlo", "documento2.doc");
	    	 
	     }
	     catch(IllegalArgumentException e) {
	    	    System.out.println("Utente con cui condividere non presente");
	     }
	     
	     //provo a rimuovere un dato dalla collezione di M ma accedo con le credenziali sbagliate
	     try{
	    	 container.remove(M, L_passw, "documento2.doc");
	    	 
	     }
	     catch(WrongPasswordException e) {
	    	    System.out.println("Password errata");
	     }
	     
	    //provo a ottenere una copia di un dato che è stato rimosso dalla collezione di M precedentemente
	     try {
	    	 container.get(M, M_passw, "documento1.doc");
	     }
	     catch(IllegalArgumentException e){
	    	 System.out.println("File non trovato ");
	     }
	     
	     System.out.println("Il numero di oggetti nella collezione di " + M + " é " +  container.getSize(M,M_passw)); 
	     System.out.println("Il numero di oggetti nella collezione di " + L + " é " +  container.getSize(L,L_passw)); 
	     System.out.println("Il numero di oggetti nella collezione di " + F + " é " +  container.getSize(F,F_passw)); 
	   
	     //genero con un iteratore tutti i dati degli utenti presenti nella collezione
	     
	     Iterator<String> file= container.getIterator(M, M_passw);
	     System.out.println("I file di " + M + " sono : ");
	     while (file.hasNext()) {
             String filestring = (String) file.next();
             System.out.println(filestring);
	     }
	     
	     Iterator<String> file2= container.getIterator(L, L_passw);
	     System.out.println("I file di " + L + " sono : ");
	     while (file2.hasNext()) {
             String filestring = (String) file2.next();
             System.out.println(filestring);
	     }
	     
	     Iterator<String> file3= container.getIterator(F, F_passw);
	     System.out.println("I file di " + F + " sono : ");
	     while (file3.hasNext()) {
             String filestring = (String) file3.next();
             System.out.println(filestring);
	     }
	   
     }
	   
	    
	
	//testo entrambe le mie implementazioni chiamando testContainerString
	public static void main(String[] args) throws NullPointerException, WrongPasswordException, IllegalArgumentException{
		 System.out.println("IMPLEMENTAZIONE CON ARRAYLIST");
	     SecureDataContainer<String> primo  = new ArrayListDataContainter<String>();
     	 SecureDataContainer<String> secondo = new HashMapDataContainer<String>();

		 testContainerString(primo);
		 System.out.println("IMPLEMENTAZIONE CON HASHMAP");
	     testContainerString(secondo);
		
	}
}