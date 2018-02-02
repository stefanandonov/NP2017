package Exams;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
  public static void main(String[] args) {
    System.out.println("=== READING RECORDS ===");
    StudentRecords studentRecords = new StudentRecords();
    int total = studentRecords.readRecords(System.in);
    System.out.printf("Total records: %d\n", total);
    System.out.println("=== WRITING TABLE ===");
    studentRecords.writeTable(System.out);
    System.out.println("=== WRITING DISTRIBUTION ===");
    studentRecords.writeDistribution(System.out);
  }
}

class StudentRecords {
	private TreeMap<String,Smer> smerovi;
	
	public StudentRecords() {
		smerovi = new TreeMap<>();
	}
	
	public int readRecords(InputStream inputStream){
		int records = 0;
		
		Scanner sc = new Scanner(inputStream);
		
		while (sc.hasNext()){
			String input = sc.nextLine();
			String [] inputs = input.split("\\s+");
			ArrayList<Integer> ocenki = new ArrayList<>();
			for (int i=2;i<inputs.length;i++)
				ocenki.add(Integer.parseInt(inputs[i]));
			
			Zapis z = new Zapis(inputs[0],inputs[1],ocenki);
			
			records++;
			
			Smer s = smerovi.get(inputs[1]);
			if (s==null){
				s = new Smer(inputs[1]);
			}
			s.addStudent(z);
			smerovi.remove(inputs[1]);
			smerovi.put(inputs[1],s);
		}
		
		
		return records;
	}
	
	public void writeTable(OutputStream outputStream) {
		PrintWriter pw = new PrintWriter(outputStream);
		smerovi.values().stream().forEach(x -> pw.print(x.toString()));
		pw.flush();
		//pw.close();
	}
	
	public void writeDistribution(OutputStream outputStream){
		PrintWriter pw = new PrintWriter(outputStream);
		smerovi.values().stream().sorted(Comparator.comparing(Smer::getAs).thenComparing(Smer::getSmer)).forEach(x -> pw.println(x.distribution()));
		
		pw.flush();
		//pw.close();
	}
} 

class Zapis implements Comparable<Zapis> {
	String kod;
	String smer;
	ArrayList<Integer> ocenki;
	
	public Zapis(String kod, String smer, ArrayList<Integer> ocenki) {
		super();
		this.kod = kod;
		this.smer = smer;
		this.ocenki = ocenki;
	}
	
	public double average() {
		return ocenki.stream().mapToDouble(x -> x).average().getAsDouble();
	}
	
	public String toString() {
		return String.format("%s %.2f\n", kod, average());
	}
	
	

	@Override
	public int compareTo(Zapis z) {
		int r = -Double.compare(this.average(), z.average());
		if (r==0)
			return this.kod.compareTo(z.kod);
		else 
			return r;
			
	}	
}


class Smer implements Comparable<Smer>{
	TreeSet<Zapis> studenti;
	String smer;
	int [] ocenki;
	
	public Smer(String smer) {
		studenti = new TreeSet<>();
		this.smer=smer;
		ocenki=new int [5];
		for (int i=0;i<5;i++)
			ocenki[i]=0;
	}
	
	public void addStudent(Zapis z){
		studenti.add(z);		
		z.ocenki.stream().forEach(x -> ocenki[x-6]++);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(smer).append("\n");
		studenti.stream().forEach(x -> sb.append(x.toString()));
		return sb.toString();
	}
	
	public String distribution() {
		StringBuilder sb = new StringBuilder();	
		sb.append(smer).append("\n");
		for (int i=0;i<5;i++){
			sb.append(i+6).append(" | ");
			int pom = ocenki[i];
			while (pom>0){
				sb.append('*');
				pom-=10;
			}
			sb.append(String.format("(%d)", ocenki[i]));
			sb.append("\n");
		}
		return sb.toString();		
	}

	@Override
	public int compareTo(Smer s) {
		int ret = Integer.compare(this.ocenki[4], s.ocenki[4]);
		if (ret==0)
			return smer.compareTo(s.smer);
		else return ret;
	}
	
	public int getAs(){
		return ocenki[4];
	}
	
	public String getSmer() {
		return smer;
	}
	
	
}