/* 
   (originally from R.I. Nishat - 08/02/2014)
   (revised by N. Mehta - 11/7/2018)
   
   edited by Ronald Liu V00838627
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class KMP{
	
    private String pattern;
	
	public int[][] dfa;
	
	public int M;
	
    
	public int row(char a){
		
		if ( a == 'A' ){
			return 0;
		}else if ( a == 'C' ){
			return 1;
		}else if ( a == 'G' ){
			return 2;
		}else if ( a == 'T' ){
			return 3;
		}
		
		return 0;
	}
	
    
    public KMP(String pattern){  
		
		this.pattern = pattern;
		M = pattern.length();
		dfa = new int[4][M];
		
		
		dfa[row(pattern.charAt(0))][0] = 1;
		
		for ( int x = 0, j = 1; j < M ; j++){
			
			for ( int c = 0; c < 4; c++){
				dfa[c][j] = dfa[c][x];
			}
			
			dfa[row(pattern.charAt(j))][j] = j+1;
			x = dfa[row(pattern.charAt(j))][x];
		}
    }
    

	
	
    public int search(String txt){  
	
		int i, j, N = txt.length();
		for( i = 0, j =0; i < N && j<M; i++ ){
			
			j = dfa[row(txt.charAt(i))][j];
			
		}
			
		if ( j == M ){
			return i-M;
		}else{
			return N;
		}
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    try{
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e){
		System.out.println("Unable to open "+args[0]+ ".");
		return;
	    }
	    System.out.println("Opened file "+args[0] + ".");
	    String text = "";
	    while(s.hasNext()){
		text += s.next() + " ";
	    }
	    
	    for(int i = 1; i < args.length; i++){
		KMP k = new KMP(args[i]);
		int index = k.search(text);
		if(index >= text.length()){
		    System.out.println(args[i] + " was not found.");
		}
		else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
	    }
	}
	else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
	}
    }
}
