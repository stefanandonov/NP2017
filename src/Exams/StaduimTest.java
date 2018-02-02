package Exams;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;


class SeatTakenException extends Exception {
	
	public SeatTakenException() {
		super();
	}
}

class SeatNotAllowedException extends Exception {
	
	public SeatNotAllowedException() {
		super();
	}
}

class Sector {
	String name;
	int capacity;
	int [] seats;
	int type;
	
	public Sector (String name, int capacity) {
		this.name=name;
		this.capacity=capacity;
		seats = new int [capacity];
		IntStream.range(0, capacity).forEach(i -> seats[i]=0);
		type = -1;
	}
	
	public int occupancy () {
		return Arrays.stream(seats).sum();
	}
	
	public double occupancyRate() {
		return  this.occupancy()*1.0 /capacity *100.0;
	}
	
	public String getName() {
		return name;
	}
	
	public void buyTicket(int seat, int type) throws SeatNotAllowedException, SeatTakenException{
		
		if (seats[seat-1]==1)
			throw new SeatTakenException();
		
		if (this.type==-1 || type==this.type || type==0){			
			seats[seat-1]=1;
            if (type!=0)
                this.type=type;			
		}
		else 
			throw new SeatNotAllowedException();
	}
	
	public String toString() {
		return String.format("%s\t%d/%d\t%.1f%%", name, capacity - this.occupancy(), capacity, this.occupancyRate());
	}
}

class Stadium {
	String name;
	TreeMap<String,Sector> sectors;
	
	public Stadium(String name){
		this.name=name;
		sectors = new TreeMap<>();
	}
	
	public void createSectors(String[] sectorNames, int[] sizes) {
		IntStream.range(0, sizes.length).forEach(i -> {
			sectors.put(sectorNames[i],new Sector(sectorNames[i],sizes[i]));
		});
	}
	
	public void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException{
		Sector s = sectors.get(sectorName);
		//System.out.println(sectorName+" "+seat+" "+type+" "+s.type);
		s.buyTicket(seat, type);		
		sectors.remove(sectorName);
		sectors.put(sectorName, s);
	}
	
	public void showSectors() {
		TreeSet<Sector> sec = new TreeSet<>(Comparator.comparing(Sector::occupancyRate).thenComparing(Sector::getName));
		
		sec.addAll(sectors.values());
		sec.stream().forEach(System.out::println);
	}
	
	
}

public class StaduimTest {
		public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		String[] sectorNames = new String[n];
		int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			sectorNames[i] = parts[0];
			sectorSizes[i] = Integer.parseInt(parts[1]);
		}
		Stadium stadium = new Stadium(name);
		stadium.createSectors(sectorNames, sectorSizes);
		n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			try {
				stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]));
			} catch (SeatNotAllowedException e) {
				System.out.println("SeatNotAllowedException");
			} catch (SeatTakenException e) {
				System.out.println("SeatTakenException");
			}
		}
		stadium.showSectors();
	}
}
