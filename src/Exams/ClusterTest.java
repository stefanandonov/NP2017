package Exams;
import java.util.*;
import java.util.stream.Collectors;

/**
 * January 2016 Exam problem 2
 */
public class ClusterTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Cluster<Point2D> cluster = new Cluster<>();
    int n = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < n; ++i) {
      String line = scanner.nextLine();
      String[] parts = line.split(" ");
      long id = Long.parseLong(parts[0]);
      float x = Float.parseFloat(parts[1]);
      float y = Float.parseFloat(parts[2]);
      cluster.addItem(new Point2D(id, x, y));
    }
    int id = scanner.nextInt();
    int top = scanner.nextInt();
    cluster.near(id, top);
    scanner.close();
  }
}

// your code here

interface IdAndDistance {
	long getId();
	
	float getX();
	float getY();
	float getZ();

	double getDistance(IdAndDistance iad);
} 

class Cluster<T extends IdAndDistance>{
	List<T> elements;
	
	public Cluster() {
		elements = new ArrayList<>();
	}
	
	public void addItem(T element){
		elements.add(element);
	}
	
	public void near(long id, int top){
		TreeMap<Double,T> map = new TreeMap<>();
		T el = null ;
		
		for (T elem : elements)
			if (elem.getId()==id){
				el=elem;
				break;
			}
		
		for (T elem : elements){
			if (elem.getId()!=id)
				map.put(elem.getDistance(el), elem);
		}
		
		List<Double> list = new ArrayList<Double>();
		list.addAll(map.keySet());
		list = list.subList(0, top);
		
		int i=1;
		for (Double d : list){
			System.out.println(i + ". " + map.get(d).getId() + " -> " + String.format("%.3f", d));
			i++;
		}
	}
}

class Point2D implements IdAndDistance{
	long id;
	float x;
	float y;
	
	public Point2D (long id, float x, float y){
		this.id=id;
		this.x=x;
		this.y=y;
	}
	
	

	@Override
	public long getId() {
		return id;
	}

	@Override
	public double getDistance(IdAndDistance iad) {
		return Math.sqrt((this.x-iad.getX())*(this.x-iad.getX()) + (this.y-iad.getY())*(this.y-iad.getY()));
	}



	@Override
	public float getX() {
		return x;
	}



	@Override
	public float getY() {
		return y;
	}



	@Override
	public float getZ() {
		return 0;
	}

} 


