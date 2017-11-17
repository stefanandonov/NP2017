package lab3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;

interface Item {
	int getPrice();
	void setCount(int c);
	int getCount();
	default int getTotal() {
		return getPrice() * getCount();
	}
	String getType();
	
	
}

class InvalidExtraTypeException extends Exception {

	InvalidExtraTypeException(){
		super("InvalidExtraTypeException");
	}
	
	private static final long serialVersionUID = 1L;
	
}

class InvalidPizzaTypeException extends Exception {

	InvalidPizzaTypeException(){
		super("InvalidPizzaTypeException");
	}
	
	private static final long serialVersionUID = 1L;
	
}

class ItemOutOfStockException extends Exception {

	ItemOutOfStockException(int count){
		super(String.format("%d", count));
	}
	
	private static final long serialVersionUID = 1L;
	
}

class EmptyOrder extends Exception {

	EmptyOrder(){
		super("EmptyOrder");
	}
	
	private static final long serialVersionUID = 1L;
	
}

class OrderLockedException extends Exception {

	OrderLockedException(){
		super("OrderLockedException");
	}
	
	private static final long serialVersionUID = 1L;
	
}


class ExtraItem implements Item {
	
	String [] allowed = {"Ketchup", "Coke"};
	public String type;
	int count;
	
	public ExtraItem(String type) throws InvalidExtraTypeException {
		if (Arrays.stream(allowed).anyMatch(x -> x.equals(type))){
			this.type = type;
			count = 0;
		}
		else{
			throw new InvalidExtraTypeException();
		}	
	}

	@Override
	public int getPrice() {
		if (type.equals("Ketchup"))
			return 3;
		else 
			return 5;		
	}

	@Override
	public void setCount(int c) {
		count = c;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtraItem other = (ExtraItem) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
@Override
public int getCount() {
	
	return count;
}

@Override
public String getType() {
	return type;
}
	
	
	
}


class PizzaItem implements Item {
	
	String [] allowed = {"Standard", "Pepperoni", "Vegetarian"};
	public String type;
	int count;
	
	public PizzaItem (String type) throws InvalidPizzaTypeException{
		if (Arrays.stream(allowed).anyMatch(x -> x.equals(type))){
			this.type=type;
			count = 0;
		}
		else throw new InvalidPizzaTypeException();
	}

	@Override
	public int getPrice() {
		if (type.equals("Standard"))
			return 10;
		else if (type.equals("Pepperoni"))
			return 12;
		else 
			return 8;
	}

	@Override
	public void setCount(int c) {
		count = c;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PizzaItem other = (PizzaItem) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
@Override
public int getCount() {
	
	return count;
}

@Override
public String getType() {
	
	return type;
}
	
	
	
	
}

class Order {
	
	List<Item> items;
	boolean lock;
	
	public Order(){
		items = new ArrayList<Item>();
		lock = false;
	}
	
	public void addItem (Item item, int count) throws ItemOutOfStockException, OrderLockedException {
		
		if (lock)
			throw new OrderLockedException();
		if (count>10)
			throw new ItemOutOfStockException(count);
		
		Item it = null;
        int idx = 0;
		for (Item i : items){
            if (i.equals(item)){
                it=i;
                idx = items.indexOf(i);
            }
				
		}
		
		if (it!=null){
			items.remove(it);
			it.setCount(count);
			items.add(idx, it);		
		}
		else {
			item.setCount(count);
			items.add(item);
		}		
	}
	
	public int getPrice() {
		return items.stream().mapToInt(Item::getTotal).sum();
	}
	
	public void displayOrder() {
		StringBuilder sb = new StringBuilder();
		items.stream().forEach(i -> sb.append(String.format("%3d.%-15sx%2d%5d$\n", items.indexOf(i)+1, i.getType(), i.getCount(), i.getTotal())));
		String s = "Total:";
		sb.append(String.format("%-22s%5d$", s, this.getPrice()));
		
		System.out.println(sb.toString());
	}
	
	public void removeItem(int idx) throws OrderLockedException {
		
		if (lock)
			throw new OrderLockedException();
		if (idx<0 || idx >= items.size() )
			throw new ArrayIndexOutOfBoundsException(idx);
		items.remove(idx);
	}
	
	public void lock () throws EmptyOrder {
		if (items.size()==0)
			throw new EmptyOrder();
		
		lock=true;
	}
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    //System.out.println(type);
                    String name = jin.next();
                    //System.out.println(name);
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass());
                    
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}