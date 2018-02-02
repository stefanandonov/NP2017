package Exams;
import java.util.*;
import java.util.stream.Collectors;

class Name {
	String name;
	int freq;
	
	public Name (String name){
		this.name=name;
		freq=1;
	}
	
	public void incFreq () {
		++freq;
	}
	
	public int uniqueLetters () {
		Set<Character> letters = new TreeSet<>();
		
		for (int i=0;i<name.length();i++)
			letters.add(Character.toLowerCase(name.charAt(i)));
		
		return letters.size();
	}
	
	public String toString() {
		return String.format("%s (%d) %d", name,freq,this.uniqueLetters());
	}
} 

class Names {
	TreeMap<String,Name> names;
	
	public Names() {
		names = new TreeMap<>();
	}
	
	public void addName(String name){
		names.computeIfPresent(name, (k,v) -> {
			v.incFreq();
			return v;
		});
		
		names.putIfAbsent(name, new Name(name));
	}
	
	public void printN (int N) {
		names.values().stream().filter(x -> x.freq>=N).forEach(System.out::println);
	}
	
	public String findName(int len, int x){		
		List<String> filteredNames = names.keySet().stream()
		.filter(name -> name.length()<len)
		.collect(Collectors.toList());
		
		return filteredNames.get(x % filteredNames.size());		
	}	
}

public class NamesTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    scanner.nextLine();
    Names names = new Names();
    for (int i = 0; i < n; ++i) {
      String name = scanner.nextLine();
      names.addName(name);
    }
    n = scanner.nextInt();
    System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
    names.printN(n);
    System.out.println("===== FIND NAME =====");
    int len = scanner.nextInt();
    int index = scanner.nextInt();
    System.out.println(names.findName(len, index));
    scanner.close();

  }
}

// vashiot kod ovde