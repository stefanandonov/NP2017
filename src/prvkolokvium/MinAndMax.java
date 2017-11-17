package prvkolokvium;

import java.util.Scanner;

class MinMax <T extends Comparable<T>>{
	T max;
	T min;
	int maxb;
	int minb;
	int counter;
	
	public MinMax(){
		maxb=minb=counter=0;
		max=null;
		min=null;
	}
	
	public void update (T element) {
		if (max==null & min==null){
			max=min=element;
			maxb=minb=1;
			counter++;
			//System.out.println(String.format("%s %s %d %d %d\n", min.toString(), max.toString(), maxb,minb,counter));
			return;
		}
			
		
		else if (element.compareTo(max)>0){
			max=element;
			maxb=1;	
			
		}
		
		else if (max.compareTo(element)==0){
			maxb++;
		}
		else if (min.compareTo(element)>0){
			min=element;
			minb=1;
		}
		else if (min.compareTo(element)==0){
			minb++;
		}
		counter++;	
		
		//System.out.println(String.format("%s %s %d %d %d\n", min.toString(), max.toString(), maxb,minb,counter));
	}
	
	public T min(){
		return min;
	}
	
	public T max(){
		return max;
	}
	
	public String toString() {
		return String.format("%s %s %d\n", min.toString(), max.toString(), counter-maxb-minb);
	}
}

public class MinAndMax {
	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
		System.out.println(strings);
		MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
           	int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
	}
}
