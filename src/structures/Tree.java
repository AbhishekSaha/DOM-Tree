package structures;



import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {

	/**
	 * Root node
	 */
	TagNode root=null;

	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;

	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}

	/**
	 * Builds the DOM tree from input HTML file. The root of the 
	 * tree is stored in the root field.
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
			System.out.println("HTML file is empty, try again with a file that is not empty");
			return;
		}
		as = strip(as);
		TagNode nu = new TagNode(as, null, null);
		nu.firstChild = traverse();
		root = nu;
		
		
	}

	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		boolean one = false; boolean two = false;
		if(oldTag.equals("p") || oldTag.equals("em") || oldTag.equals("b")){
			if(newTag.equals("ol") || newTag.equals("ul")){
				one = true;
			}
		}
		
		if(newTag.equals("ol") || newTag.equals("ul")){
			if(oldTag.equals("p") || oldTag.equals("em") || oldTag.equals("b")){
				two = true;
			}
		}
		
		if(two == false && one == false){
		rep(oldTag, newTag, root);}
		else{
			System.out.println("Tags are not sensible replacement, please try again");
		}

	}

	private void rep(String oldTag, String newTag, TagNode temp){
		if(temp == null){
			return;
		}
		if(temp.tag.equals(oldTag)==true){
			temp.tag = newTag;
		}
		rep(oldTag, newTag, temp.firstChild);
		rep(oldTag, newTag, temp.sibling);

	}

	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		boolean tri = false;
		rip(row, root, tri, 0);
	}

	private void rip(int row, TagNode temp, boolean tri, int y){
		if(temp==null){
			return;
		}
		
		String tim = temp.tag;
		
		if(tim.length()>0){
		
	
		if(tim.length()>1 && y==row && tim.equals("td")){
			TagNode peet = new TagNode("b", temp.firstChild, null);
			temp.firstChild = peet;
			

		}
		else if(tim.equalsIgnoreCase("tr")){
			y++;
		}
		}
		
		
		rip(row, temp.firstChild, tri, y);
		rip(row, temp.sibling, tri, y);
	}

	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
		del(root, root.firstChild, tag, 2);
		if(tag.equals("ul") || tag.equals("ol")){
		replaceTag("li", "p");}
	}

	private void del(TagNode previous, TagNode current, String tag, int dir){

		//sibling
		String rondo = tag;

		if(current==null){
			return;
		}

		String tim = current.tag;
		if(tim.equals(rondo)==true){
			tim = current.tag;
			if(current.firstChild!=null && dir==1){
				previous.sibling = current.firstChild;
				TagNode dis = current.firstChild;
				TagNode nee = current.firstChild.sibling;
				while(nee!=null){
					dis = nee;
					nee = nee.sibling;
				}
				dis.sibling = current.sibling;
				current = dis;
			}

			else if(current.firstChild==null && dir==1){
				previous.sibling = current.sibling;
				current = current.sibling;
			}
			else if(current.firstChild!=null && dir==2){
				previous.firstChild = current.firstChild;
				TagNode dis = current.firstChild;
				TagNode nee = current.firstChild.sibling;
				while(nee!=null){
					dis = nee;
					nee = nee.sibling;
				}
				dis.sibling = current.sibling;
				current = previous.firstChild;
			}
			
			else if(current.firstChild==null && dir==2){
				previous.firstChild = current.sibling;
				
				current = current.sibling;

			}}


		del(current, current.firstChild, tag, 2);
		del(current, current.sibling, tag, 1);
	}


	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		add(root, word, tag);
	}

	private void add(TagNode temp, String wor, String tig){
		if(temp==null){
			return;
		}
		TagNode ptr = temp.sibling;
		boolean tempy = false;
		int size = wor.length();
		boolean hug = false;
		boolean yee = false;
		String tim = temp.tag;
		String jim = temp.tag;
		tim = tim.toLowerCase();
		String ron = ""; String hon = "";
		String word = wor;
		word = word.toLowerCase();
		char gee;
		
		if(word.equalsIgnoreCase(tim)== true && word.length()==tim.length()){
			temp.firstChild = new TagNode(tim, null, null);
			temp.tag = tig;
			temp = temp.firstChild;
		}

		else{
			int x = tim.indexOf(word);
			if(x==-1){

			}
			else{
				int y = x + word.length();
				String jer = jim.substring(x, y);
				if(y==tim.length()){
					gee = jim.charAt(jim.length()-1);
				}
				else{
				gee = jim.charAt(y);}
				if(y==(tim.length())){
					temp.tag = jim.substring(0, x);
					TagNode nu = new TagNode(tig, null, ptr);
					nu.firstChild = new TagNode(jer, null, null);
					temp.sibling = nu;
					temp = nu.sibling;}
				else{

					if(gee==' ' || gee=='.' || gee==',' || gee=='?' || gee=='!' || gee==';' || gee==':' || y==tim.length()){
						if(x==0){
							temp.tag = tig;
							temp.firstChild = new TagNode(jer, null, null);
							temp.sibling = new TagNode(jim.substring(y, tim.length()), null, ptr);
							temp = temp.sibling;
						}
						else {
							temp.tag = tim.substring(0, x);
							TagNode last = new TagNode(jim.substring(y, tim.length()), null, ptr);
							TagNode nu = new TagNode(tig, null, last);
							nu.firstChild = new TagNode(jer, null, null);
							temp.sibling = nu;
							temp = nu.sibling;
							
						}
					}}
			}

		}

		try{
			add(temp.firstChild, word, tig);}
		catch(NullPointerException e){

		}
		add(temp.sibling, word, tig);

	}

	private int[] serch(String big, String small){
		String hon = "";
		int [] p = new int [2];
		p[0] = -1;
		p[1] = -1;
		int j = 0;

		for(int i = 0; i<big.length(); i++){
			char gee = big.charAt(i);
			String geee = Character.toString(gee);
			if(gee==' ' || gee=='.' || gee==',' || gee=='?' || gee=='!' || gee==';' || gee==':' ){
				boolean t = hon.equalsIgnoreCase(small);
				if(t==true){
					p[0] = j;
					p[1] = i;
					return p;
				}
				else{
					j=i;
					hon="";
				}
			}
			hon.concat(geee);
		}


		return p;
	}

	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}

	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}

}
