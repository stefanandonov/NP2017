package lab6;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

class Matrix <T> {
	private ArrayList<ArrayList<T>> matrix;
	private int rows;
	private int columns;
	
	public Matrix(int numRows, int numCols) {
		rows = numRows;
		columns = numCols;
		matrix = new ArrayList<ArrayList<T>>(rows);
		
		for (int i=0;i<rows;i++){
			ArrayList<T> list = new ArrayList<>();
			
			for (int j=0;j<columns;j++)
				list.add(null);
			
			matrix.add(list);
		}
	}
	
	public int getNumRows() {
		return rows;
	}
	
	public int getNumColumns() {
		return columns;
	}
	
	public T getElementAt (int r, int c){
		return matrix.get(r).get(c);
	}
	
	public void setElementAt (int r, int c, T e){
		ArrayList<T> column = matrix.get(r);
		column.set(c, e);
		matrix.set(r, column);
	}
	
	public void fill (T e){
		for (int i=0;i<rows;i++){
			for (int j=0;j<columns;j++)
				matrix.get(i).add(j, e);
		}
	}
	
	public void insertRow (int r){
		if (r<0 || r>rows)
			throw new ArrayIndexOutOfBoundsException();
		
		ArrayList<T> newRow = new ArrayList<>();
		IntStream.range(0, columns).forEach(x-> newRow.add(null));
		matrix.add(r, newRow);	
		rows++;
	}
	
	public void deleteRow (int r){
		if (r<0 || r>rows)
			throw new ArrayIndexOutOfBoundsException();
		
		matrix.remove(r);
		rows--;
	}
	
	public void insertColumn (int c) {
		if (c<0 || c>columns)
			throw new ArrayIndexOutOfBoundsException();
		
		matrix.stream().forEach(x -> x.add(c, null));
		columns++;
	}
	
	public void deleteColumn (int c){
		if (c<0 || c>columns)
			throw new ArrayIndexOutOfBoundsException();
		
		matrix.stream().forEach(x -> x.remove(c));
		columns--;
	}
	
	public void resize (int r, int c) {
        
        
        
		if (r<rows){
			for (int i=rows-1;i>=r;i--){
				matrix.remove(i);
			}				
		}
		if (r>rows){
			for (int i=rows;i<r;i++)
				insertRow(i);
		}
		
		if (c<columns){
			for (int i=columns-1;i>=c;i--)
				deleteColumn(i);
		}
		
		if (c>columns){
			for (int i=columns;i<c;i++)
				insertColumn(i);
		}
        
        columns=c;
        rows=r;
	}
}

public class MatrixTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int t = jin.nextInt();
		if ( t == 0 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<Integer> matrix = new Matrix<Integer>(r,c);
			print(matrix);
		}
		if ( t == 1 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<Integer> matrix = new Matrix<Integer>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.nextInt());
				}
			}
			print(matrix);
		}
		if ( t == 2 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<String> matrix = new Matrix<String>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.next());
				}
			}
			print(matrix);
		}
		if ( t == 3 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<String> matrix = new Matrix<String>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.next());
				}
			}
			print(matrix);
			matrix.deleteRow(jin.nextInt());
			matrix.deleteRow(jin.nextInt());
			print(matrix);
			int ir = jin.nextInt();
			matrix.insertRow(ir);
			for ( int k = 0 ; k < c ; ++k ) {
				matrix.setElementAt(ir, k, jin.next());
			}
			ir = jin.nextInt();
			matrix.insertRow(ir);
			for ( int k = 0 ; k < c ; ++k ) {
				matrix.setElementAt(ir, k, jin.next());
			}
			print(matrix);
			matrix.deleteColumn(jin.nextInt());
			matrix.deleteColumn(jin.nextInt());
			print(matrix);
			int ic = jin.nextInt();
			matrix.insertColumn(ir);
			for ( int i = 0 ; i < r ; ++i ) {
				matrix.setElementAt(i, ic, jin.next());
			}
			ic = jin.nextInt();
			matrix.insertColumn(ic);
			for ( int i = 0 ; i < r ; ++i ) {
				matrix.setElementAt(i, ic, jin.next());
			}
			print(matrix);
		}
		if ( t == 4 ) {
			int r = jin.nextInt();
			int c = jin.nextInt();
			Matrix<Integer> matrix = new Matrix<Integer>(r,c);
			for ( int i = 0 ; i < r ; ++i ) {
				for ( int k = 0 ; k < c ; ++k ) {
					matrix.setElementAt(i, k, jin.nextInt());
				}
			}
			print(matrix);
			int nr = jin.nextInt();
			int nc = jin.nextInt();
			matrix.resize(nr, nc);
			print(matrix);
			matrix.fill(jin.nextInt());
			print(matrix);
		}
	}
	
	public static void print ( Matrix<?> m ) {
		int r = m.getNumRows();int c = m.getNumColumns();
		System.out.println("  "+r+" x "+c);
		System.out.print("    ");
		for ( int k = 0 ; k < c ; ++k ) {
			System.out.print(k+"    ");
		}
		System.out.println();
		System.out.print("  ");
		for ( int k = 0 ; k < c ; ++k ) {
			System.out.print("-----");
		}
		System.out.println();
		for ( int i = 0 ; i < r ; ++i ) {
			System.out.print(i+"|");
			for ( int k = 0 ; k < c ; ++k ) {
				if ( k > 0 ) System.out.print(" ");
				System.out.print(m.getElementAt(i, k));
			}
			System.out.println();
		}
		System.out.println();
	}

}
