
public class ReverseString {
	
	
	public String reverseString(String initial) {
		
		StringBuilder b = new StringBuilder(initial.toLowerCase());
		
		for(int i=0; i<initial.length()/2 ;i++) {			
			b.setCharAt(i, initial.charAt(initial.length()-1-i));
			b.setCharAt(initial.length()-1-i,initial.charAt(i));
			
		}	
		return b.toString();
	}
	
	public static void main( String args[]) {
		ReverseString r = new ReverseString();
		System.out.println(r.reverseString("Soumen1"));	
	}
}
