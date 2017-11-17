package prvkolokvium;
import java.util.Scanner;
import java.util.stream.IntStream;

class ZeroDenominatorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZeroDenominatorException() {
		super("Denominator cannot be zero");
	}
}

class GenericFraction<T extends Number,U extends Number> {
	T numerator;
	U denominator;
	
	public GenericFraction(T n, U d) throws ZeroDenominatorException{
		if (d.doubleValue() == 0.0){
			throw new ZeroDenominatorException();
		}
		numerator = n;
		denominator = d;
	}
	
	public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
		GenericFraction<Double,Double>  gfNew = new GenericFraction<> (this.numerator.doubleValue(),this.denominator.doubleValue());
		
		if (gfNew.denominator==gf.denominator.doubleValue()){
			gfNew.numerator +=gf.numerator.doubleValue();
		}
		else {
			gfNew.numerator = this.numerator.doubleValue()  * gf.denominator.doubleValue() 
					+ gf.numerator.doubleValue() * this.denominator.doubleValue();
			gfNew.denominator = this.denominator.doubleValue()*gf.denominator.doubleValue();
		}
		
		return gfNew;
	}
	
	public double toDouble() {
		return this.numerator.doubleValue()/this.denominator.doubleValue();
	}
	
	public String toString() {
		double num,den;
		num = this.numerator.doubleValue();
		den = this.denominator.doubleValue();
		
		for (int i = (int) Double.max(num, den); i >= 2; i--) {
			if (num%i==0 && den%i==0){
				num/=i;
				den/=i;
			}
		}
		
		
		return String.format("%.2f / %.2f", num,den);
	}
	
	
}

public class GenericFractionTest {
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
        	GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
        	GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
        	GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }
        
        scanner.close();
    }

}

// вашиот код овде
