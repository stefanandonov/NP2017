package Exams;
import java.util.*;

import java.util.Scanner;

class Component implements Comparable<Component> {
	String color;
	int weight;
	TreeSet<Component> components;
	
	public Component(String color, int weight){
		this.color=color;
		this.weight=weight;
		components = new TreeSet<>();
	}
	
	public void addComponent(Component c){
		components.add(c);
	}

	@Override
	public int compareTo(Component c) {
		int r =  Integer.compare(this.weight, c.weight);
		if (r==0)
			return this.color.compareTo(c.color);
		else
			return r;
	}
	
	public String toString(int level) {
		int pom = level;
		StringBuilder sb = new StringBuilder();
		while(pom>0){
			sb.append("---");
			--pom;
		}
		sb.append(String.format("%d:%s\n", weight,color));
		components.stream().forEach(x -> sb.append(x.toString(level+1)));
		
		return sb.toString();
	}
	
	public void changeColor(int w, String c){
		if (this.weight<w)
			this.color=c;
		
		components.stream().forEach(x -> x.changeColor(w, c));
	}
}

class InvalidPositionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public InvalidPositionException (int pos){
		super(String.format("Invalid position %d, alredy taken!", pos));
	}
	
}

class Window {
	String name;
	TreeMap<Integer,Component> comps;
	
	public Window (String name){
		this.name=name;
		comps = new TreeMap<>();
	}
	
	public void addComponent(int position, Component c) throws InvalidPositionException {
		if (comps.keySet().stream().anyMatch(x -> x==position))
			throw new InvalidPositionException(position);
		
		comps.put(position, c);
	}
	
	public void changeColor (int weight, String color){
		
		comps.keySet().stream().forEach(x -> {
			comps.computeIfPresent(x, (pos,comp) -> {
				comp.changeColor(weight, color);
				return comp;
			});
		});
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		
		sb.append("WINDOW ").append(name).append("\n");
		comps.keySet().stream().forEach(x -> {
			sb.append(x).append(":");
			Component comp = comps.get(x);
			sb.append(comp.toString(0));
		});
		
		return sb.toString();
	}
	
	public void swichComponents(int pos1, int pos2){
		Component c1 = comps.get(pos1);
		Component c2 = comps.get(pos2);
		
		comps.remove(pos1);
		comps.remove(pos2);
		
		comps.put(pos1, c2);
		comps.put(pos2, c1);
	}
	
}
public class ComponentTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		Window window = new Window(name);
		Component prev = null;
		while (true) {
            try {
				int what = scanner.nextInt();
				scanner.nextLine();
				if (what == 0) {
					int position = scanner.nextInt();
					window.addComponent(position, prev);
				} else if (what == 1) {
					String color = scanner.nextLine();
					int weight = scanner.nextInt();
					Component component = new Component(color, weight);
					prev = component;
				} else if (what == 2) {
					String color = scanner.nextLine();
					int weight = scanner.nextInt();
					Component component = new Component(color, weight);
					prev.addComponent(component);
                    prev = component;
				} else if (what == 3) {
					String color = scanner.nextLine();
					int weight = scanner.nextInt();
					Component component = new Component(color, weight);
					prev.addComponent(component);
				} else if(what == 4) {
                	break;
                }
                
            } catch (InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
            scanner.nextLine();			
		}
		
        System.out.println("=== ORIGINAL WINDOW ===");
		System.out.println(window);
		int weight = scanner.nextInt();
		scanner.nextLine();
		String color = scanner.nextLine();
		window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
		System.out.println(window);
		int pos1 = scanner.nextInt();
		int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
		window.swichComponents(pos1, pos2);
		System.out.println(window);
	}
}

// вашиот код овде
