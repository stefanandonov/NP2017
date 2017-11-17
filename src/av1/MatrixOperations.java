package av1;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class MatrixOperations {
	public static double sum (double [][] matrix){
		return Arrays.stream(matrix)
		.mapToDouble(row -> Arrays.stream(row).sum())
		.sum();
	}
	
	public static double average (double [][] matrix){
		return Arrays.stream(matrix).mapToDouble(row -> Arrays.stream(row).sum()).sum()/(matrix.length*matrix[0].length);
	}
	public static void main(String[] args) {
		double [][] matrix = {{1,2},{3,4}};
		
		System.out.println(sum(matrix)+" "+average(matrix));
		

	}

}
