package prvkolokvium;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * I partial exam 2016
 */



class Temperature implements Comparable<Temperature> {
	int day;
	Double[] temperatures;
	char scale;
	
	public Temperature (String s) {
		String [] input = s.split("\\s+");
		day = Integer.parseInt(input[0]);
		temperatures = new Double [input.length-1];
		IntStream.range(1, input.length).forEach(i -> temperatures[i-1]=Double.parseDouble(input[i].substring(0, input[i].length()-1)));
		scale = input[1].charAt(input[1].length()-1);
	}
	
	public static double C (double x) {
		return (x-32)*5/9;
	}
	
	public static double F (double x){
		return x*9/5 +32;
	}
	
	public void toC () {
		if (scale=='C')
			return;
		Double [] temps1 = new Double[temperatures.length];
		IntStream.range(0, temps1.length).forEach(i -> temps1[i]=C(temperatures[i]));
		scale='C';
		temperatures=temps1;
	}
	
	public void toF() {
		if (scale=='F')
			return;
		Double [] temps1 = new Double[temperatures.length];
		IntStream.range(0, temps1.length).forEach(i -> temps1[i]=F(temperatures[i]));
		scale='F';
		temperatures=temps1;
	}

	@Override
	public int compareTo(Temperature t) {
		
		return Integer.compare(this.day, t.day);
	}	
	
	public String toString() {
		Double max = Arrays.stream(temperatures).mapToDouble(x->x).max().getAsDouble();
		double min = Arrays.stream(temperatures).mapToDouble(x->x).min().getAsDouble();
		double avrg = Arrays.stream(temperatures).mapToDouble(x -> x).sum()/temperatures.length;
		int count = temperatures.length;
		return String.format("%3d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c", day, count, min, scale, max, scale, avrg, scale);
	}
}

class DailyTemperatures {
	
	List<Temperature> temps;
	
	public DailyTemperatures () {
		temps = new ArrayList<>();
	}
	
	public void readTemperatures(InputStream inputStream) {
		
		Scanner sc = new Scanner (inputStream);
		
		while (sc.hasNext()) {
			String in = sc.nextLine();
			Temperature t = new Temperature(in);
			temps.add(t);
		}
	}
	
	public void writeDailyStats(OutputStream outputStream, char scale){
		
		PrintWriter pw = new PrintWriter(outputStream);
		
		if (scale=='F') {
			temps.stream().forEach(x -> x.toF());
		}
		else if (scale=='C'){
			temps.stream().forEach(x -> x.toC());			
		}
		
		temps.sort(null);
		
		temps.forEach(x -> pw.println(x));
		pw.flush();
		
		
	}
}
public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');     			
    }
}

// Vashiot kod ovde