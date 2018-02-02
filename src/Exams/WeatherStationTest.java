package Exams;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;



class Data implements Comparable<Data> {
	protected float temperature;
	protected float humidity;
	protected float wind;
	protected float vis;
	protected Date date;
	public Data(float temperature, float wind, float hum, float vis, Date date) {
		//super();
		this.temperature = temperature;
		this.humidity = hum;
		this.wind = wind;
		this.vis = vis;
		this.date = date;
	}
	
	public String toString() {
		return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperature,wind,humidity,vis,date.toString());
	}

	@Override
	public int compareTo(Data o) {
		long time = Math.abs(this.date.getTime() - o.date.getTime());
		if (time<150000)
			return 0;
		return this.date.compareTo(o.date);
	}	
	
	public double getTemp() {
		return temperature;
	}
	
}

class WeatherStation {
	TreeSet<Data> data;
	int days;
	static long MILLISECONDS_IN_A_DAY = 86400000;
	
	public WeatherStation(int days){ 
		this.days=days;
		data = new TreeSet<>();
	}
	
	public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
		Data newData = new Data(temperature,wind,humidity,visibility,date);
		
		if (!data.add(newData))
			return;
		
		data
		.removeIf(x -> newData.date.getTime() - x.date.getTime() > days*MILLISECONDS_IN_A_DAY);		
	}
	
	public int total() {
		return data.size();
	}
	
	public void status (Date from, Date to){
		List<Data> list = new ArrayList<>();
		list = data.stream()
				.filter(x -> (x.date.after(from) || x.date.equals(from))
						&& (x.date.before(to) || x.date.equals(to)))
				.collect(Collectors.toList());
		
		if (list.size()==0)
			throw new RuntimeException();
		
		double avg = list.stream().mapToDouble(x -> x.getTemp()).average().getAsDouble();
		
		list
		.stream()
		.forEach(System.out::println);
		
		System.out.println(String.format("Average temperature: %.2f", avg));
	}
} 

public class WeatherStationTest {
	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
		WeatherStation ws = new WeatherStation(n);
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("=====")) {
				break;
			}
			String[] parts = line.split(" ");
			float temp = Float.parseFloat(parts[0]);
			float wind = Float.parseFloat(parts[1]);
			float hum = Float.parseFloat(parts[2]);
			float vis = Float.parseFloat(parts[3]);
			line = scanner.nextLine();
			Date date = df.parse(line);
			ws.addMeasurment(temp, wind, hum, vis, date);
		}
		String line = scanner.nextLine();
		Date from = df.parse(line);
		line = scanner.nextLine();
		Date to = df.parse(line);
		scanner.close();
		System.out.println(ws.total());
		try {
			ws.status(from, to);
		} catch (RuntimeException e) {
			System.out.println(e);
		}
	}
}

// vashiot kod ovde