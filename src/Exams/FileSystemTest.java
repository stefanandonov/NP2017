package Exams;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */

class File implements Comparable<File>{
	protected String name;
	protected int size;
	protected LocalDateTime date;
	
	public File(String name, int size, LocalDateTime date) {
		super();
		this.name = name;
		this.size = size;
		this.date = date;
	}

	@Override
	public int compareTo(File o) {
		if (!this.date.equals(o.date))
			return this.date.compareTo(o.date);
		else {
			if (!this.name.equals(o.name)){
				return this.name.compareTo(o.name);
			}
			else {
				return Integer.compare(this.size, o.size);
			}				
		}			
	}
	
	public String toString() {
		return String.format("%-10s %5dB %s", name,size,date.toString());
	}
	
	public boolean isHidden(int s) {
		return name.startsWith(".")&&size<s;
	}
	
	public int getSize() {
		return size;
	}
	
	
}

class FileSystem {
	
	Map<Character,TreeSet<File>> files;
	Map<Integer, Set<File>> byYear;
	Map<String,Long> byMonthAndDay;
	
	
	public FileSystem () {
		files = new HashMap<>();
		byYear = new TreeMap<Integer,Set<File>>();
		byMonthAndDay = new TreeMap<String,Long>();
	}
	
	public void addFile(char folder, String name, int size, LocalDateTime createdAt){
			
		TreeSet<File> f = files.get(folder);
		if (f==null){
			f = new TreeSet<>();
		}
		f.add(new File(name,size,createdAt));
		files.put(folder, f);
		
		int year = createdAt.getYear();
		Set<File> set = byYear.get(year);
		if (set==null){
			set = new TreeSet<File>();
		}
		set.add(new File(name,size,createdAt));
		byYear.remove(year);
		byYear.put(year, set);
		
		;
		String s = String.format("%s-%d", createdAt.getMonth().toString(),createdAt.getDayOfMonth());
		
		Long l = byMonthAndDay.get(s);
		if (l==null){
			l=(long) size;
		}
		else {
			l+=(long) size;
		}
		
		byMonthAndDay.remove(s);
		byMonthAndDay.put(s, l);
	}
	
	public List<File> findAllHiddenFilesWithSizeLessThen(int size){
		return files.values().stream().flatMap(TreeSet::stream).filter(x -> x.isHidden(size)).collect(Collectors.toList());
	}
	
	public int totalSizeOfFilesFromFolders(List<Character> folders){
		
		return folders.stream().mapToInt(x -> files.get(x).stream().mapToInt(File::getSize).sum()).sum();
	}
	
	public Map<Integer, Set<File>> byYear() {
		return byYear;
		
	}
	
	public Map<String, Long> sizeByMonthAndDay(){
		return byMonthAndDay;
	}
	
	
}
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
           Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here

