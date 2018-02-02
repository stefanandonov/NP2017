package lab6;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

class Node<T extends Comparable<T>>{
	public T info;
	public Node<T> left;
	public Node<T> right;
	boolean exist;
	
	public Node(T info, Node<T> left, Node<T> right) {
		this.info = info;
		this.left=left;
		this.right=right;	
		exist = true;
	}
}

class BinaryTreeSet<T extends Comparable<T>> {
	Node<T> root; 
	
	public BinaryTreeSet() {
		root = null;
	}
	
	public void addElement (T element) {
		root = add(element,root);
	}
	
	public Node<T> add (T element, Node<T> root){
		
		if (root == null)
			return new Node(element,null,null);
		
		if (root.info.equals(element)){
			if (!root.exist)
				root.exist=true;
			return root;
		}
		
		if (root.info.compareTo(element)>0){
			root.left = add(element,root.left);
		}
		else {
			root.right = add(element,root.right);
		}
			
		return root;
	}
	
	public boolean contains (T element) {
		return containsR(element,root);
	}
	
	public boolean containsR (T element, Node<T> root) {
		if (root==null)
			return false;
		
		if (root.info.equals(element)&&root.exist)
			return true;
		
		if (root.info.compareTo(element)>0)
			return containsR(element,root.left);
		else if (root.info.compareTo(element)<0)
			return containsR(element,root.right);
		
		return false;
	}
	
	public boolean removeElement (T element) {
		return remove(element,root);
	}
	
	public boolean  remove(T element, Node<T> root){
		if (root==null)
			return false;
		if (root.info.equals(element)){
			root.exist=false;
			return true;
		}
		else if (root.info.compareTo(element)<0)
			return remove(element,root.right);
		else
			return remove(element,root.left);
	}
	
	public void inorder(Node<T> root, StringBuilder sb){
		if (root!=null) {
			inorder(root.left,sb);
			if (root.exist)
				sb.append(root.info+" ");
			inorder(root.right,sb);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (root == null)
			return "EMPTY";
		
		inorder(root,sb);
		return sb.toString().substring(0, sb.length()-1);
	}
	
}

public class BinaryTreeSetTest {
	
	public static void main(String[] args) {
		Scanner jin = new Scanner(System.in);
		int t = jin.nextInt();
		if ( t == 0 ) {
			BinaryTreeSet<Integer> ts = new BinaryTreeSet<Integer>();
			while ( jin.hasNextInt() ) {
				ts.addElement(jin.nextInt());
			}
			System.out.println(ts);
		}
		if ( t == 1 ) {
			BinaryTreeSet<String> ts = new BinaryTreeSet<String>();
			while ( true ) {
				String next = jin.next();
				if ( next.equals("stop") ) break;
				ts.addElement(next);
			}
			System.out.println(ts);
		}
		if ( t == 2 ) {
			BinaryTreeSet<Long> ts = new BinaryTreeSet<Long>();
			while ( jin.hasNextLong() ) {
				ts.addElement(jin.nextLong());
			}
			jin.next();
			System.out.println(ts);
			while ( jin.hasNextLong() ) {
				System.out.println(ts.contains(jin.nextLong()));
			}
			System.out.println(ts);
		}
		if ( t == 3 ) {
			BinaryTreeSet<String> ts = new BinaryTreeSet<String>();
			int counter = 0;
			while ( true ) {
				if ( counter % 20 == 0 ) System.out.println(ts);
				++counter;
				String next = jin.next();
				if ( next.equals("stop") ) break;
				if ( next.equals("add") ) {
					ts.addElement(jin.next());
				}
				if ( next.equals("remove") ) {
					ts.removeElement(jin.next());
				}
				if ( next.equals("query") ) {
					System.out.println(ts.contains(jin.next()));
				}
			}
			System.out.println(ts);
		}
		if ( t == 4 ) {
			BinaryTreeSet<Long> ts = new BinaryTreeSet<Long>();
			TreeSet<Long> control_set = new TreeSet<Long>();
			ArrayList<Long> all = new ArrayList<Long>();
			all.add(5L);
			int n = jin.nextInt();
			boolean exact = true;
			for ( int i = 0 ; exact&&i < n ; ++i ) {
				if ( Math.random() < 0.4 ) {
					if ( Math.random() < 0.6 ) {
						long to_add = (long)(Math.random()*98746516548964156L);
						ts.addElement(to_add);
						control_set.add(to_add);
						all.add(to_add);
					}
					else {
						int add_idx = (int)(Math.random()*all.size());
						long to_add = all.get(add_idx);
						ts.addElement(to_add);
						control_set.add(to_add);
					}
				}
				else {
					if ( Math.random() < 0.4 ) {
						if ( Math.random() < 0.1 ) {
							long to_remove = (long)(Math.random()*98746516548964156L);
							ts.removeElement(to_remove);
							control_set.remove(to_remove);
						}
						else {
							int remove_idx = (int)(Math.random()*all.size());
							long to_remove = all.get(remove_idx);
							ts.removeElement(to_remove);
							control_set.remove(to_remove);
						}
					}
					else {
						if ( Math.random() < 0.3 ) {
							long to_query = (long)(Math.random()*98746516548964156L);
							exact &= ts.contains(to_query)==control_set.contains(to_query);
						}
						else {
							int query_idx = (int)(Math.random()*all.size());
							long to_query = all.get(query_idx);
							exact &= ts.contains(to_query)==control_set.contains(to_query);
						}
					}
				}
			}
			System.out.println(exact);
		}
	}

}

