package Exams;
import java.util.*;

class Block<T extends Comparable<T>> {
	private TreeSet<T> elements;
	int max;
	
	public Block(int n) {
		elements = new TreeSet<>();
		max=n;
	}
	
	public boolean addElement(T element){
		System.out.println(elements.size()==max);
		if (elements.size()==max)
			return false;
		else {
			elements.add(element);
			return true;
		}
	}
	
	public boolean isEmpty() {
		return elements.size()==0;
	}
	
	public boolean removeElement(T el){
		return elements.remove(el);
	}
	
	public TreeSet<T> getElements() {
		return elements;
	}
	
	public String toString() {
		return elements.toString();
	}
}

class BlockContainer<T extends Comparable<T>> {
	List<Block<T>> blocks;
	int n;
	
	public BlockContainer(int n) {
		this.n=n;
		blocks = new ArrayList<>();
		blocks.add(new Block<T>(n));		
	}
	
	public void add (T a){
		Block<T> b = blocks.get(blocks.size()-1);
		if (b.addElement(a)){
			blocks.remove(blocks.size()-1);
			blocks.add(b);
		}
		else {
			Block<T> b1 = new Block<T>(n);
			b1.addElement(a);
			blocks.add(b1);
		}
	}
	
	public boolean isEmpty (){
		return blocks.size()==0;
	}
	
	public boolean remove(T a){
		Block<T> b = blocks.get(blocks.size()-1);
		boolean ret = b.removeElement(a);
		
		if (b.isEmpty()){
			blocks.remove(blocks.size()-1);
		}
		else {
			blocks.remove(blocks.size()-1);
			blocks.add(b);
		}
		
		
		return ret;
	}
	
	public void sort() {
		
		BlockContainer<T> bc = new BlockContainer<>(this.n);
		TreeSet<T> allElements = new TreeSet<>();
		
		blocks.stream().forEach(x -> allElements.addAll(x.getElements()));
		
		allElements.stream().forEach(x -> bc.add(x));
		
		this.blocks = bc.blocks;		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		blocks.forEach(x -> sb.append(x.toString()).append(","));
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
} 

public class BlockContainerTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int size = scanner.nextInt();
		BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
		scanner.nextLine();
		Integer lastInteger = null;
		for(int i = 0; i < n; ++i) {
			int element = scanner.nextInt();
			lastInteger = element;
			integerBC.add(element);
		}
		System.out.println("+++++ Integer Block Container +++++");
		System.out.println(integerBC);
		System.out.println("+++++ Removing element +++++");
		integerBC.remove(lastInteger);
		System.out.println("+++++ Sorting container +++++");
		integerBC.sort();
		System.out.println(integerBC);
		BlockContainer<String> stringBC = new BlockContainer<String>(size);
		String lastString = null;
		for(int i = 0; i < n; ++i) {
			String element = scanner.next();
			lastString = element;
			stringBC.add(element);
		}
		System.out.println("+++++ String Block Container +++++");
		System.out.println(stringBC);
		System.out.println("+++++ Removing element +++++");
		stringBC.remove(lastString);
		System.out.println("+++++ Sorting container +++++");
		stringBC.sort();
		System.out.println(stringBC);
	}
}

// Вашиот код овде



