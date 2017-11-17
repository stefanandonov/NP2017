package prvkolokvium;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ShapesTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Canvas canvas = new Canvas();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
			int type = Integer.parseInt(parts[0]);
			String id = parts[1];
			if (type == 1) {
                Color color = Color.valueOf(parts[2]);
				float radius = Float.parseFloat(parts[3]);
				canvas.add(id, color, radius);
			} else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
				float width = Float.parseFloat(parts[3]);
				float height = Float.parseFloat(parts[4]);
				canvas.add(id, color, width, height);
			} else if (type == 3) {
				float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
				System.out.print(canvas);
				canvas.scale(id, scaleFactor);
				System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
				System.out.print(canvas);
			}

		}
	}
}

enum Color {
	RED, GREEN, BLUE
}

interface Scalable {
	void scale(float scaleFactor);
}

interface Stackable {
	float weight();
}

abstract class Shape {
	String id;
	Color color;
	
	public Shape(String id, Color c){
		this.id=id;
		color=c;
	}
	
	public String toString(float weight) {
		return String.format("%-5s%10s%10.2f", id,color.toString(),weight);
	}
}

class Circle extends Shape implements Stackable,Scalable {
	float radius;
	
	public Circle (String id, Color c, float r){
		super (id,c);
		radius=r;
	}

	@Override
	public void scale(float scaleFactor) {
		radius*=scaleFactor;
		
	}

	@Override
	public float weight() {
		return (float) (radius*radius* Math.PI);
	}
	
	public String toString() {
		return String.format("R: %s", this.toString(this.weight()));
	}
}

class Rectangle extends Shape implements Stackable,Scalable {
	float width;
	float height;
	
	public Rectangle (String id, Color c, float w, float h){
		super(id,c);
		width=w;
		height=h;
	}

	@Override
	public void scale(float scaleFactor) {
		width*=scaleFactor;
		height*=scaleFactor;
		
	}

	@Override
	public float weight() {
		return width*height;
	}
	
	public String toString() {
		return String.format("R: %s", this.toString(this.weight()));
	}
}

class Canvas {
	private List<Shape> shapes;
	
	public Canvas () {
		shapes = new ArrayList<Shape>();
	}
	
	public int find (Stackable c){
		int i;
		for (i=0;i<shapes.size();i++){
			Shape shape = shapes.get(i);
			Stackable s = (Stackable) shape;
			if (s.weight() < c.weight()){
				return i;
			}
		}
		return i;
	}
	
	public void add(String id, Color color, float radius){
		Shape s = new Circle (id,color,radius);
		int idx = find((Stackable) s);
		shapes.add(idx,s);
	}
	
	public void add (String id, Color color, float width, float height){
		Shape s = new Rectangle(id,color,width,height);
		int idx = find((Stackable) s);
		shapes.add(idx,s);
	}
	
	public void scale (String id,float scaleFactor){
		Scalable scalable = null;
		int idx=0;
		for (int i=0;i<shapes.size();i++){
			if (shapes.get(i).id.equals(id)){
				scalable = (Scalable) shapes.get(i);
				scalable.scale(scaleFactor);
				idx=i;
				break;
			}
		}
		
		
		Shape toAdd = shapes.get(idx);
		shapes.remove(idx);
		idx = find ((Stackable) toAdd);
		shapes.add(idx, toAdd);
			
	}
	
	@Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (Shape shape : shapes) {
            res.append(shape);
            res.append("\n");
        }
        return res.toString();
    }

}