package Exams;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

class Event implements Comparable<Event>{
	protected String name;
	protected String location;
	protected Date date;
	
	public Event(String name, String location, Date date) {
		super();
		this.name = name;
		this.location = location;
		this.date = date;
	}
	
	public static int getMinutes (Date d){
		int hours = d.getHours();
		int minutes = d.getMinutes();
		return hours*60+minutes;
	}

	@Override
	public int compareTo(Event e) {		
		int r = Integer.compare(Event.getMinutes(date), Event.getMinutes(e.date));
		if (r==0)
			return name.compareTo(e.name);
		else return r;
	}
	
	public String toString(){
		DateFormat df = new SimpleDateFormat("dd MMM, YYY HH:mm");
		return String.format("%s at %s, %s", df.format(date).toString().replaceAll("GMT", "UTC"),location,name);
	}	
}

class WrongDateException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public WrongDateException(Date d){
		super(String.format("Wrong date: %s", d.toString().replaceAll("GMT", "UTC")));
	}	
}

class EventCalendar {
	int year;
	HashMap<String,TreeSet<Event>> events;
	HashMap<Integer,Integer> eventsByMonth;
	
	public EventCalendar(int year){
		this.year=year;
		events = new HashMap<>();
		eventsByMonth = new HashMap<>();
	}
	
	public static String getDayAndMonth(Date d){
		return String.format("%2d %2d", d.getDate(), d.getMonth()+1);
	}
	
	
	
	public void addEvent(String name, String location, Date date) throws WrongDateException{
        if ((date.getYear()+1900)!=year)
			throw new WrongDateException(date);
		
		
		Event e = new Event(name,location,date);
		String key = EventCalendar.getDayAndMonth(date);
		
		TreeSet<Event> ev = events.get(key);
		if (ev==null){
			ev = new TreeSet<Event>();
		}
		ev.add(e);
		events.remove(key);
		events.put(key, ev);
		
		eventsByMonth.computeIfPresent(date.getMonth()+1, (k,value) -> ++value);
        eventsByMonth.putIfAbsent(date.getMonth()+1, 1);
        
	}
	
	public void listEvents (Date d){
		String key = EventCalendar.getDayAndMonth(d);
		
		if (!events.containsKey(key)){
			System.out.println("No events on this day!");
			return;
		}
			
		events.computeIfPresent(key, (k,v)-> {
			v.stream().forEach(x -> System.out.println(x.toString()));
			return v;
		});
	}
	
	public void listByMonth(){
		StringBuilder sb = new StringBuilder();
		
		for (int i=1;i<=12;i++){
			Integer x = eventsByMonth.get(i);
			if (x==null)
				x=0;
			sb.append(i+" : "+ x + "\n");
		}
		
		System.out.println(sb.toString());
	}
}

public class EventCalendarTest {
	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		int year = scanner.nextInt();
		scanner.nextLine();
		EventCalendar eventCalendar = new EventCalendar(year);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(";");
			String name = parts[0];
			String location = parts[1];
			Date date = df.parse(parts[2]);
			try {
				eventCalendar.addEvent(name, location, date);
			} catch (WrongDateException e) {
				System.out.println(e.getMessage());
			}
		}
		Date date = df.parse(scanner.nextLine());
		eventCalendar.listEvents(date);
		eventCalendar.listByMonth();
	}
}

// vashiot kod ovde