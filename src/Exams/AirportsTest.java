package Exams;

import java.util.*;

/*
 * Not finished!
 */

class Airport {
	protected String name;
	protected String country;
	protected String code;
	protected int passengers;
	protected TreeMap<String,TreeSet<Flight>> flightsTo;
	
	public Airport(String name, String country, String code, int passengers) {
		super();
		this.name = name;
		this.country = country;
		this.code = code;
		this.passengers = passengers;
		flightsTo = new TreeMap<>();
	}
	
	public void addFlight (String to, int time, int duration){
		Flight f = new Flight(this.code,to,time,duration);
		TreeSet<Flight> flights = flightsTo.get(to);
		if (flights==null)
			flights = new TreeSet<Flight>();
		flightsTo.remove(to);
		flights.add(f);
		flightsTo.put(to, flights);
	}
	
	
}

class Flight {
	protected String from;
	protected String to;
	protected int time;
	protected int duration;
	
	
	public Flight(String from, String to, int time, int duration) {
		super();
		this.from = from;
		this.to = to;
		this.time = time;
		this.duration = duration;
	}
	
}


class Airports {
	protected Map<String,Airport> airports;
	
	public Airports(){
		airports = new HashMap<>();
	}
	
	public 
	
	
}

public class AirportsTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Airports airports = new Airports();
    int n = scanner.nextInt();
    scanner.nextLine();
    String[] codes = new String[n];
    for (int i = 0; i < n; ++i) {
      String al = scanner.nextLine();
      String[] parts = al.split(";");
      airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
      codes[i] = parts[2];
    }
    int nn = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < nn; ++i) {
      String fl = scanner.nextLine();
      String[] parts = fl.split(";");
      airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }
    int f = scanner.nextInt();
    int t = scanner.nextInt();
    String from = codes[f];
    String to = codes[t];
    System.out.printf("===== FLIGHTS FROM %S =====\n", from);
    airports.showFlightsFromAirport(from);
    System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
    airports.showDirectFlightsFromTo(from, to);
    t += 5;
    t = t % n;
    to = codes[t];
    System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
    airports.showDirectFlightsTo(to);
  }
}

// vashiot kod ovde

