package Exams;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

class Participant {
	String code;
	String name;
	int age;
	
	public Participant(String code, String name, int age) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
	}
	
	public String toString() {
		return String.format("%s %s %d", code,name,age);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}	
}

class Audition {
	private Map<String,HashSet<Participant>> participants;
	
	private static Comparator<Participant> comparator = (l,r) -> {
		int ret = l.name.compareTo(r.name);
		int ret2;
		if (ret==0){
			ret2 = Integer.compare(l.age,r.age);
			if (ret2==0)
				return l.code.compareTo(r.code);
			else 
				return ret2;
		}
	
		
		else
			return ret;
	};
	
	public Audition() {
		participants = new HashMap<>();
	}
	
	public void addParticpant(String city, String code, String name, int age) {
		participants.computeIfPresent(city, (k,v)->{
			v.add(new Participant(code,name,age));
			return v;
		});
		
		participants.computeIfAbsent(city, (k)-> {
			HashSet<Participant> v = new HashSet<Participant>();
			v.add(new Participant(code,name,age));
			return v;
		});
	}
	
	public void listByCity(String city) {
		
		participants.get(city).stream().sorted(Audition.comparator).forEach(System.out::println);
	}
}

public class AuditionTest {
	public static void main(String[] args) {
		Audition audition = new Audition();
		List<String> cities = new ArrayList<String>();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			if (parts.length > 1) {
				audition.addParticpant(parts[0], parts[1], parts[2],
						Integer.parseInt(parts[3]));
			} else {
				cities.add(line);
			}
		}
		for (String city : cities) {
			System.out.printf("+++++ %s +++++\n", city);
			audition.listByCity(city);
		}
		scanner.close();
	}
}