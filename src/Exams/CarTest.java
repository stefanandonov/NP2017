package Exams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Car {
	String manufacturer;
	String model;
	int price;
	float power;
	
	public Car(String manufacturer, String model, int price, float power) {
		super();
		this.manufacturer = manufacturer;
		this.model = model;
		this.price = price;
		this.power = power;
	}
	
	public String toString() {
		return String.format("%s %s (%.0fKW) %d", manufacturer,model,power,price);			
	}
	
	public int getPrice() {
		return price;
	}
	
	public float getPower() {
		return power;
	}
	
	public String getModel() {
		return model;
	}
	
	
}

class CarCollection {
	private List<Car> cars;
	
	public CarCollection () {
		cars = new ArrayList<>();
	}
	
	public void addCar(Car c){
		cars.add(c);		
	}
	
	public void sortByPrice(boolean ascending) {		
		Collections.sort(cars, Comparator.comparing(Car::getPrice).thenComparing(Car::getPower));
		if (!ascending)
			Collections.reverse(cars);		
	}
	
	public List<Car> filterByManufacturer(String man){
		return cars.stream()
				.filter(x -> x.manufacturer.equalsIgnoreCase(man))
				.sorted(Comparator.comparing(Car::getModel))
				.collect(Collectors.toList());
	}
	
	public List<Car> getList() {
		return cars;
	}
}

public class CarTest {
	public static void main(String[] args) {
		CarCollection carCollection = new CarCollection();
		String manufacturer = fillCollection(carCollection);
		carCollection.sortByPrice(true);
		System.out.println("=== Sorted By Price ASC ===");
		print(carCollection.getList());
		carCollection.sortByPrice(false);
		System.out.println("=== Sorted By Price DESC ===");
		print(carCollection.getList());
		System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
		List<Car> result = carCollection.filterByManufacturer(manufacturer);
		print(result);
	}

	static void print(List<Car> cars) {
		for (Car c : cars) {
			System.out.println(c);
		}
	}

	static String fillCollection(CarCollection cc) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
            if(parts.length < 4) return parts[0];
			Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
					Float.parseFloat(parts[3]));
			cc.addCar(car);
		}
        scanner.close();
		return "";
	}
}


// vashiot kod ovde