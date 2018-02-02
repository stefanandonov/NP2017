package lab5Listi;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.IntStream;

class SuperString {
	private LinkedList<String> strings;
	
	public SuperString () {
		strings = new LinkedList<>();
	}
	
	public void append (String s){
		strings.add(s);
	}
	
	public void insert (String s){
		strings.addFirst(s);
	}
	
	public boolean contains (String s){
		StringBuilder sb = new StringBuilder();
		
		strings.stream().forEach(x -> sb.append(x));
		return sb.toString().contains(s);
	}
	
	public String reverseString(String s){
		StringBuilder sb = new StringBuilder();
		
		for (int i=s.length();i>=0;i--){
			sb.append(s.charAt(i));
		}
		
		return sb.toString();
	}
	
	public void reverse () {
		LinkedList<String> pom = new LinkedList<>();
		
		strings.stream().forEach(x -> pom.addFirst(reverseString(x)));
		
		strings=pom;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();		
		strings.stream().forEach(x -> sb.append(x));
		return sb.toString();		
	}
	
	public void removeLast (int k){
		
		while (k!=0)
			strings.removeLast();
	}
}

public class SuperStringTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int k = jin.nextInt();
		if (  k == 0 ) {
			SuperString s = new SuperString();
			while ( true ) {
				int command = jin.nextInt();
				if ( command == 0 ) {//append(String s)
					s.append(jin.next());
				}
				if ( command == 1 ) {//insert(String s)
					s.insert(jin.next());
				}
				if ( command == 2 ) {//contains(String s)
					System.out.println(s.contains(jin.next()));
				}
				if ( command == 3 ) {//reverse()
					s.reverse();
				}
				if ( command == 4 ) {//toString()
					System.out.println(s);
				}
				if ( command == 5 ) {//removeLast(int k)
					s.removeLast(jin.nextInt());
				}
				if ( command == 6 ) {//end
					break;
				}
			}
		}
	}

}
