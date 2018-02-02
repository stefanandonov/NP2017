package lab6;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Random;   

class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	protected T element;
	protected Node<T> pred, succ;

	public Node(T elem, Node<T> pred, Node<T> succ) {
		this.element = elem;
		this.pred = pred;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return element.toString();
	}

	@Override
	public int compareTo(Node<T> o) {
		return this.element.compareTo(o.element);
	}	
}

class SortedLinkedList<T extends Comparable<T>>{
	private Node<T> first, last;
	
	public SortedLinkedList(){
		first=last=null;
	}
	
	public void add (T element) {
		if (first==null){
			insertFirst(element);}
		else if (element.compareTo(last.element)>0){
			insertLast(element);
		}
		else if (element.compareTo(first.element)<0){
			insertFirst(element);
		}
		else {
			addOrdered(element);
		}
			
	}
	public void addOrdered(T element){
		Node<T> curr = last;
		while (curr!=null&&curr.element.compareTo(element)<0){
			curr=curr.pred;
		}
		
		if (curr!=null){
			if (element.compareTo(curr.element)>0)
				this.insertAfter(element, curr);
           
		}
        else {
            insertFirst(element);
        }
        
       
	}
	
	public boolean isEmpty() {
		return first==null;
	}
	
	public int size() {
		if (isEmpty())
			return 0;
		else {
			int rez=1;
			Node<T> curr = first;
			while (curr.succ!=null){
				curr=curr.succ;
				rez++;
			}
			
			return rez;
		}
	}
	
	public ArrayList<T> toArrayList() {
		ArrayList<T> result = new ArrayList<>();
		
		if (isEmpty())
			return null;
		
		Node<T> curr = first;
		
		while (curr!=null){
			result.add(curr.element);
			curr=curr.succ;
		}
		
		return result;
	}
	
	public boolean containsAll(SortedLinkedList<? extends T> a){
		ArrayList<T> list = new ArrayList<>();
		
		list = this.toArrayList();
		
		ArrayList<? extends T> sublist = new ArrayList<>();
		sublist = a.toArrayList();
		
		return list.containsAll(sublist);
	}
	
	public void addAll(SortedLinkedList<? extends T> a){
		ArrayList<? extends T> list = a.toArrayList();
		
		list.stream().forEach(x -> this.add(x));
	}
	public Node<T> find (T element){
		if (isEmpty())
			return null;
		
		Node<T> curr = first;
		while (curr!=null&&curr.element.compareTo(element)<0)
			curr=curr.succ;
		
		if (curr!=null&&curr.element.compareTo(element)==0)
			return curr;
		
		return null;
	}
	
	public boolean contains (T element){
		return this.find(element)!=null;
	}
	
	public boolean remove (T element){
		Node<T> node = find(element);
		if (node==null)
			return false;
		else {
			delete(node);
			return true;
		}
	}
	public T delete(Node<T> node){
		if (node==first){
			return deleteFirst();
		}
		if (node==last){
			return deleteLast();
		}
		
		node.pred.succ=node.succ;
		node.succ.pred=node.pred;
		return node.element;
	}
	
	public T deleteFirst() {
		if (first!=null){
			Node<T> tmp = first;
			first=first.succ;
			if (first!=null)
				first.pred=null;
			if (first==null)
				last=null;
			return tmp.element;			
		}
		else 
			return null;
	}
	
	public T deleteLast() {
		if (first!=null){
			if (first.succ==null)
				return deleteFirst();
		}
		else {
			Node<T> tmp = last;
			last=last.pred;
			last.succ=null;
			return tmp.element;
		}
		
		return null;
	}
	
	public void insertFirst (T element){
		Node<T> ins = new Node<T>(element,null,first);
		if (first==null)
			last=ins;
		else 
			first.pred=ins;
		first=ins;
	}
	
	public void insertLast(T element){
		if (first==null){
			insertFirst(element);
		}
		else {
			Node<T> ins = new Node<>(element,last,null);
			last.succ=ins;
			last=ins;
		}
	}
	
	public void insertAfter(T element, Node<T> after){
		if (after==last){
			insertLast(element);
			return;
		}
		Node<T> ins = new Node<T>(element,after,after.succ);
		after.succ.pred=ins;
		after.succ=ins;
	}
	public void insertBefore(T element, Node<T> before){
		if (before==first){
			insertFirst(element);
			return;
		}
		Node<T> ins = new Node<T>(element,before.pred,before);
		before.pred.succ=ins;
		before.pred=ins;
	}
	
	
}

public class SortedLinkedListTest {			
    	public static void main(String[] args) {
    		Scanner jin = new Scanner(System.in);
    		int k = jin.nextInt();
    		System.out.println("Test#"+k);
    		System.out.print("testing SortedLinkedList::toArrayList():ArrayList<T> ,");
    		if ( k == 0 ) {
    			System.out.println(" SortedLinkedList::add(T), SortedLinkedList::isEmpty():boolean , SortedLinkedList::remove(T):boolean , SortedLinkedList::size():int , T is Integer");
    			
    			SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
    			System.out.println("List is empty? "+list.isEmpty());
    			System.out.println("Adding elements:");
    			boolean flag = false;
    			while ( jin.hasNextInt() ) {
    				System.out.print(flag?" ":"");
    				int i = jin.nextInt();
    				list.add(i);
    				System.out.print(i);
    				flag = true;
    			}
    			System.out.println();
    			System.out.println("List size? "+list.size());
    			jin.next();
    			flag = false;
    			System.out.println("Removing elements:");
    			while ( jin.hasNextInt() ) {
    				System.out.print(flag?" ":"");
    				int i = jin.nextInt();
    				list.remove(i);
    				System.out.print(i);
    				flag = true;
    			}System.out.println();
    			System.out.println("List size? "+list.size());
    			System.out.println("Final list: "+list.toArrayList());
    		}
    		if ( k == 1 ) {
    			System.out.println(" SortedLinkedList::add(T) , T is Integer");
    			System.out.println("Adding elements:");
    			SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
    			boolean flag = false;
    			while ( jin.hasNextInt() ) {
    				System.out.print(flag?" ":"");
    				int i = jin.nextInt();
    				list.add(i);
    				System.out.print(i);
    				flag = true;
    			}
                System.out.println();
    			System.out.print("Final list: ");
    			System.out.println(list.toArrayList());
    		}
            if ( k == 2 ) {
    			System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::addAll(SortedLinkedList<? etends T>) , T is Integer");
    			
    			int num_testcases = jin.nextInt();
    			for ( int w = 0 ; w < num_testcases ; ++w ) {
    				System.out.println("Subtest #"+(w+1));
    				SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
    				while ( jin.hasNextInt() ) {
    					list.add(jin.nextInt());
    				}
    				SortedLinkedList<Integer> query = new SortedLinkedList<Integer>();
    				jin.next();
    				while ( jin.hasNextInt() ) {
    					query.add(jin.nextInt());
    				}
    				System.out.println("List a="+list.toArrayList());
    				System.out.println("List b="+query.toArrayList());
    				list.addAll(query);
    				System.out.println("Add all elements from b to a");
    				System.out.println("List a="+list.toArrayList());
    				jin.next();
    			}
    		}
    		if ( k == 3 ) {
    			System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::containsAll(SortedLinkedList<? etends T>) , T is Integer");
    			int num_testcases = jin.nextInt();
    			for ( int w = 0 ; w < num_testcases ; ++w ) {
    				System.out.println("Subtest #"+(w+1));
    				SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
    				while ( jin.hasNextInt() ) {
    					list.add(jin.nextInt());
    				}
    				SortedLinkedList<Integer> query = new SortedLinkedList<Integer>();
    				jin.next();
    				while ( jin.hasNextInt() ) {
    					query.add(jin.nextInt());
    				}
    				System.out.println("List a="+list.toArrayList());
    				System.out.println("List b="+query.toArrayList());
    				System.out.println("List a contains all elements in list b? "+list.containsAll(query));
    				jin.next();
    			}
    		}
    		if ( k == 4 ) {
    			System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::remove(T):boolean , SortedLinkedList::contains(T) , T is String");
    		    SortedLinkedList<String> list = new SortedLinkedList<String>();
    			TreeSet<String> control_list = new TreeSet<String>();
    			ArrayList<String> all = new ArrayList<String>();
    			all.add("Sample");
    			boolean same = true;
    			for ( int i = 0 ; i < 1000 ; ++i ) {
    				double rand = Math.random();
    				if ( rand > 0.3 ) { //addelement
    					String srand = randomString();
    					if ( Math.random() < 0.1 ) {
    						srand = all.get((int)(Math.random()*all.size()));
    					}
    					control_list.add(srand);list.add(srand);
    				}
    				if ( rand >= 0.3&&rand < 0.8 ) {//query
    					String srand = randomString();
    					if ( Math.random() < 0.6 ) {
    						srand = all.get((int)(Math.random()*all.size()));
    					}
    					same &= control_list.contains(srand)==list.contains(srand);
    				}
    				if ( rand >= 0.8 ) {//remove
    					String srand = randomString();
    					if ( Math.random() < 0.8 ) {
    						srand = all.get((int)(Math.random()*all.size()));
    					}
    					control_list.remove(srand);list.remove(srand);
    				}
    			}
    			System.out.println("Your list outputs compared to the built in java structure were the same? "+same);
    			
    		}
    		if ( k == 5 ) {
    			System.out.println(" SortedLinkedList::add(T) , SortedLinkedList::remove(T):boolean , T is Long");
    			int n = jin.nextInt();
    			SortedLinkedList<Long> list = new SortedLinkedList<Long>();
    			ArrayList<Long> all = new ArrayList<Long>();
    			all.add(684165189745L);
    			for ( int i = 0 ; i < n ; ++i ) {
    				double rand = Math.random();
    				if ( rand < 0.7 ) { //addelement
    					Long srand = (long) (Math.random()*45668948941984L);
    					if ( Math.random() < 0.1 ) {
    						srand = all.get((int)(Math.random()*all.size()));
    					}
    					list.add(srand);
    				}
    				if ( rand >= 0.7 ) {
    					Long srand = (long) (Math.random()*45668948941984L);
    					if ( Math.random() < 0.1 ) {
    						srand = all.get((int)(Math.random()*all.size()));
    					}
    					list.remove(srand);
    				}
    			}
    			System.out.println("Your program was really fast. You are a great developer!");
    		}
    	}
    
    	private static String randomString() {
    		byte buf[] = new byte[(int)(Math.random()*10)+1];
    		new Random().nextBytes(buf);
    		return new String(buf);
    	}
    
    }
