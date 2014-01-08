package structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class build {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	private String strip(String temp){
		String tri = temp;
		char cee = tri.charAt(0);
		int siz;
		if(cee!='<'){
			return tri;
		}
		else{
			siz = temp.length();
			tri = tri.substring(1,siz-1);

			return tri;}
	}
	
	
	private TagNode traverse(){
		String ji = sc.nextLine();
		if(ji.charAt(0)=='<' && ji.charAt(1)=='/'){
			return null;
		}
		else if(ji.charAt(0)!='<'){
			TagNode ptr = new TagNode(ji, null, null);
			ptr.sibling = traverse();
			return ptr;
		}
		else{
			ji = strip(ji);
			TagNode ptr = new TagNode(ji, null, null);
			ptr.firstChild = traverse();
			ptr.sibling = traverse();
			return ptr;
		}
	}

	public void build() {
		/** COMPLETE THIS METHOD **/
		String as;
		try{
			as= sc.nextLine();}
		catch(NoSuchElementException e){
			System.out.println("HTML is empty");
			return;
		}
		as = strip(as);
		TagNode nu = new TagNode(as, null, null);
		nu.firstChild = traverse();
		root = nu;
		as= sc.nextLine();
		
	}

}
