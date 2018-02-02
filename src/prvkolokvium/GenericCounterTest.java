package prvkolokvium;

import java.util.*;

public class GenericCounterTest {
	  public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    int n = scanner.nextInt();
	    GenericCounter<Integer> counterInt = new GenericCounter<Integer>();
	    scanner.nextLine();
	    for (int i = 0; i < n; ++i) {
	      int x = scanner.nextInt();
	      counterInt.count(x);
	    }
	    System.out.println("=====INTEGERS=====");
	    System.out.println(counterInt);
	    n = scanner.nextInt();
	    scanner.nextLine();
	    GenericCounter<String> counterString = new GenericCounter<String>();
	    for (int i = 0; i < n; i++) {
	      String s = scanner.nextLine();
	      counterString.count(s);
	    }
	    System.out.println("=====STRINGS=====");
	    System.out.println(counterString);
	    n = scanner.nextInt();
	    scanner.nextLine();
	    GenericCounter<Float> counterFloat = new GenericCounter<Float>();
	    for (int i = 0; i < n; i++) {
	      float f = scanner.nextFloat();
	      counterFloat.count(f);
	    }
	    System.out.println("=====FLOATS=====");
	    System.out.println(counterFloat);
	    scanner.close();
	  }
}

class Element<T>  {
	private T element;
	private int count;
	
	public Element(T element){
		count=1;
		this.element=element;
	}
	
	public void incCount (){
		++count;
	}
	
	public T getElement() {
		return element;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
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
		Element other = (Element) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<count;i++){
			sb.append("*");
		}
		return String.format("%s|%s", element.toString(),sb.toString());
	}
}

class GenericCounter<T> {
	private List<Element<T>> elements;
	
	public GenericCounter() {
		elements = new ArrayList<>();
	}
	
	public void count (T element){
		Element<T> e = null;
		
		for (Element<T> ee : elements){
			if (ee.getElement().equals(element)){
				ee.incCount();
				e=ee;
				break;
			}
		}
		
		if (e==null){
			e = new Element<T>(element);
			elements.add(e);
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		elements.stream().forEach(x -> sb.append(x+"\n"));
		
		return sb.toString();
	}
	
	
}


