package prvkolokvium;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	NonExistingItemException (int id) {
		super(String.format("Item with id %d doesn't exist", id));
	}
}

abstract class Archive {
	private int id;
	private Date date;
	private String type;
	
	public Archive(int id,String type){
		this.id=id;
		date=null;
		this.type = type;
	}
	
	public void archiveElement (Date d){
		date=d;
	}
	
	public int getId(){
		return id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getType() {
		return type;
	}
}

class LockedArchive extends Archive{
	private Date dateToOpen;
	
	public LockedArchive (int id, Date dateToOpen){
		super(id,"locked");
		this.dateToOpen=dateToOpen;
	}
	
	public Date getDateToOpen() {
		return dateToOpen;
	}
}

class SpecialArchive extends Archive{
	private int maxOpen;
	private int timesOpened;
	
	public SpecialArchive (int id, int m){
		super(id,"special");
		maxOpen=m;
		timesOpened=0;
	}
	public int getMaxOpen() {
		return maxOpen;
	}
	
	public int getTimesOpened() {
		return timesOpened;
	}
	
	public void openArchive () {
		++timesOpened;
	}
}


class ArchiveStore {
	private List<Archive> items;
	private StringBuilder logs;
	
	public ArchiveStore () {
		items = new ArrayList<>();
		logs = new StringBuilder();
	}
	
	public void archiveItem(Archive item, Date date){
		item.archiveElement(date);
		items.add(item);
		logs.append(String.format("Item %d archived at %s\n",item.getId(), date.toString().replaceAll("GMT", "UTC")));
	}
	
	public void openItem(int id, Date date) throws NonExistingItemException {
		Archive a = null;
		for (Archive archive : items){
			if (archive.getId()==id){
				a=archive;
			}
		}
		if (a==null){
			
			throw new NonExistingItemException(id);
		}
		else {
			if (a.getType().equals("locked")){
				LockedArchive la = (LockedArchive) a;
				if (date.before(la.getDateToOpen())){
					logs.append(String.format("Item %d cannot be opened before %s\n", id,la.getDateToOpen().toString().replaceAll("GMT", "UTC")));
				}
				else {
					logs.append(String.format("Item %d opened at %s\n", id, date.toString().replaceAll("GMT", "UTC")));
				}
			}
			else {
				SpecialArchive sa = (SpecialArchive) a;
				if (sa.getMaxOpen()==sa.getTimesOpened()){
					logs.append(String.format("Item %d cannot be opened more than %d times\n", id,sa.getMaxOpen()));
				}
				else {
					sa.openArchive();
					logs.append(String.format("Item %d opened at %s\n", id, date.toString().replaceAll("GMT", "UTC")));
				}
			}
		}
	}
	
	public String getLog() {
		return logs.toString();
	}
	
	
}
public class ArchiveStoreTest {
	public static void main(String[] args) {
		ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
		Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
		int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
		int i;
		for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
			long days = scanner.nextLong();
			Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
					* 60 * 1000));
			LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
			store.archiveItem(lockedArchive, date);
		}
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
		for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
			int maxOpen = scanner.nextInt();
			SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
		}
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
			int open = scanner.nextInt();
            try {
            	store.openItem(open, date);
            } catch(NonExistingItemException e) {
            	System.out.println(e.getMessage());
            }
        }
		System.out.println(store.getLog());
	}
}

// вашиот код овде



