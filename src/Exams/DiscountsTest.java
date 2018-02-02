package Exams;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.*;

class Product{
	protected int discountPrice;
	protected int regularPrice;
	
	public Product(int discountPrice, int regularPrice) {
		super();
		this.discountPrice = discountPrice;
		this.regularPrice = regularPrice;
	}
	
	public int getPercent () {
		return (int) ((1-(discountPrice*1.0/regularPrice))*100);
	}
	
	public float getRealPercent() {
		return (float) ((1-(discountPrice*1.0/regularPrice))*100); 
	}
	
	public int getAbsoluteDiscount() {
		return regularPrice - discountPrice;
	}
	
	public String toString() {
		return String.format("%2d%% %d/%d\n", this.getPercent(), discountPrice, regularPrice);
	}

}

class Store {
	protected String name;
	TreeSet<Product> products;
	
	public Store(String input){
		String [] inputs = input.split("\\s+");
		name = inputs[0];
		
		products = new TreeSet<>(Comparator
				.comparing(Product::getPercent)
				.thenComparing(Product::getAbsoluteDiscount)
				.thenComparing(Product::getRealPercent)
				.reversed());
		
		
		for(int i=1;i<inputs.length;i++){
			String [] prices = inputs[i].split(":");
			Product p = new Product (Integer.parseInt(prices[0]),Integer.parseInt(prices[1]));
			products.add(p);
		}
	}
	
	public double averageDiscount() {
		return products.stream().mapToInt(Product::getPercent).average().orElse(0);
	}
	
	public int totalDiscount() {
		return products.stream().mapToInt(Product::getAbsoluteDiscount).sum();
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name)
		.append("\n")
		.append(String.format("Average discount: %.1f%%\n", this.averageDiscount()))
		.append(String.format("Total discount: %d\n", this.totalDiscount()));
		
		//products.stream().forEach(x -> sb.append(x.toString()));
		List<Product> list = new ArrayList<>();
		list.addAll(products);
		Collections.reverse(list);
		list.stream().forEach(x -> sb.append(x.toString()));
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}	
}

class Discounts {
	TreeSet<Store> storesByAverage;
	TreeSet<Store> storesByTotal;
	
	
	
	public Discounts() {
		storesByAverage = new TreeSet<>(Comparator
				.comparing(Store::averageDiscount)
				.thenComparing(Store::getName));
		
		storesByTotal = new TreeSet<>(Comparator
				.comparing(Store::totalDiscount)
				.thenComparing(Store::getName));
	}
	
	public int readStores(InputStream is){
		int count = 0;		
		Scanner sc = new Scanner(is);		
		while (sc.hasNext()){
			String input = sc.nextLine();
			Store store = new Store(input);
			storesByAverage.add(store);
			storesByTotal.add(store);
			count++;
		}		
		return count;
	}
	
	public List<Store> byAverageDiscount() {
		List<Store> result = new ArrayList<>();
		
		result.addAll(storesByAverage);
		result = result.subList(result.size()-3, result.size());
		Collections.reverse(result);
		return result;
	}
	
	public List<Store> byTotalDiscount() {
		List<Store> result = new ArrayList<>();
		
		result.addAll(storesByTotal);
		result = result.subList(0, 3);
		//Collections.reverse(result);
		return result;
	}
}

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    	
    	/*Product p = new Product(2579,4985);
    	System.out.println(p.toString());
    	String input = "Levis 6385:9497  9988:19165  7121:11287  1501:2316  2579:4985  6853:8314";
    	Store store = new Store(input);
    	System.out.println(store.toString());*/
    }
}

// Vashiot kod ovde
