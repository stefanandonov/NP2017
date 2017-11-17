package prvkolokvium;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class F1Test {

	public static void main(String[] args) {
		
		/*Race r = new Race ("Alonso 1:53:563 1:56:187 1:54:139");
		System.out.println(r.toString());*/
		F1Race f1Race = new F1Race();
		f1Race.readResults(System.in);
		f1Race.printSorted(System.out);
	}

}

class Race implements Comparable<Race>{
	private String name;
	private String [] laps;
	private String best;
	
	public Race (String input) {
		String [] inputs = input.split("\\s+");
		laps = new String [3];
		name = inputs[0];
		IntStream.range(1, 3).forEach(i -> laps[i-1]=inputs[i]);
		best = Arrays.stream(inputs).map(x -> x.toString()).min(String::compareTo).get();
	}
	
	public String toString() {
		return String.format("%-10s%10s", name, best);
	}

	@Override
	public int compareTo(Race r) {
		return best.compareTo(r.best);
	} 
}

class F1Race {
	private List<Race> races;
	
	public F1Race(){
		races= new ArrayList<>();
	}
	
	public void readResults(InputStream inputStream){
		Scanner sc = new Scanner(inputStream);
		
		while(sc.hasNext()){
			Race r = new Race (sc.nextLine());
			races.add(r);
		}
	}
	
	public void printSorted(OutputStream outputStream){
		PrintWriter pw = new PrintWriter(outputStream);
		
		Collections.sort(races);
		IntStream.range(0, races.size()).forEach(x -> System.out.println((x+1)+". "+races.get(x)));
		
		pw.flush();
	}
    
}