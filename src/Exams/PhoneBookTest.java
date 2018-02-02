package Exams;
import java.util.stream.Collectors;
import java.util.*;

public class PhoneBookTest {

	public static void main(String[] args) {
		PhoneBook phoneBook = new PhoneBook();
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
			String line = scanner.nextLine();
			String[] parts = line.split(":");
			try {
				phoneBook.addContact(parts[0], parts[1]);
			} catch (DuplicateNumberException e) {
				System.out.println(e.getMessage());
			}
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
            System.out.println(line);
			String[] parts = line.split(":");
			if (parts[0].equals("NUM")) {
				phoneBook.contactsByNumber(parts[1]);
			} else {
				phoneBook.contactsByName(parts[1]);
			}
		}
	}

}

class Contact implements Comparable<Contact>{
	private String name;
	private String phone;
	
	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public Contact(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	
	public boolean containsNumber(String number){
		return phone.contains(number);
	}

	@Override
	public int compareTo(Contact c) {
		int ret = this.name.compareTo(c.name);
		if (ret==0)
			return this.phone.compareTo(c.phone);
		else 
			return ret;
	}
	
	@Override
	public String toString() {
		return name + " " + phone;
	}
}

class DuplicateNumberException extends Exception{
	
	public DuplicateNumberException(String m){
		super(m);
	}
}

class PhoneBook {
	TreeSet<Contact> contactsNumbers;
	HashMap<String,TreeSet<Contact>> contactsNames;
	
	public PhoneBook() {
		contactsNumbers = new TreeSet<>();
		contactsNames = new HashMap<>();
	}
	
	public void addContact(String name,String number) throws DuplicateNumberException {
		
		if (contactsNumbers.stream().anyMatch(x -> x.getPhone()==number))
			throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
		
		Contact c = new Contact(name,number);
		
		contactsNumbers.add(c);
		
		contactsNames.computeIfPresent(name, (k,v)-> {
			v.add(c);
			return v;
		});
		
		contactsNames.computeIfAbsent(name, key -> {
			TreeSet<Contact> set = new TreeSet<Contact>(Comparator.comparing(Contact::getName).thenComparing(Contact::getPhone));
			set.add(c);
			return set;			
		});
	}
	
	public void contactsByNumber (String number){
		
		List<Contact> list = null;
		list = contactsNumbers
		.stream()
		.filter(x -> x.containsNumber(number))
		.collect(Collectors.toList());
		
		if (list.size()==0){
			System.out.println("NOT FOUND");
			return;
		}
			
		
		list.stream()
		.forEach(x -> System.out.println(x.toString()));
	}
	
	public void contactsByName (String name){
		if (!contactsNames.containsKey(name)){
			System.out.println("NOT FOUND");
			return;
		}
			
		contactsNames.get(name).forEach(System.out::println);
	}
}


