package Exams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Word {
	private String word;

	public Word(String word) {
		super();
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}
	
	public int length() {
		return word.length();
	}
	
	public char chatAt(int i){
		return word.charAt(i);
	}
	
	public boolean hasIdenticRepresentation (Word w){
		if (this.length()!=w.length())
			return false;
		
		int check = 0;
		for (int i=0;i<w.length();i++){
			if (this.chatAt(i)!=w.chatAt(i))
				check++;
		}
		
		if (check!=1)
			return false;
		else 
			return true;
	}
	
	public String toString() {return word;}
}

class Vector {
	private List<Integer> vector;
	
	public static final Vector DEFAULT = new Vector (Arrays.asList(5,5,5,5,5));
	public static final Vector IDENTITY = new Vector (Arrays.asList(0,0,0,0,0));
	

	public Vector(List<Integer> vector) {
		super();
		this.vector = vector;
	}
	
	public List<Integer> getVector() {
		return vector;
	}
	
	public Integer getIntAt (int i){
		return vector.get(i);
	}
	
	public Vector sum (Vector v){
		List<Integer> ret = new ArrayList<>();
		
		IntStream.range(0, 5).forEach(i -> {
			ret.add(i, this.getIntAt(i)+v.getIntAt(i));
		});
		
		return new Vector(ret);
	}
	
	public int max() {
		return vector.stream().mapToInt(x -> x).max().orElse(0);
	}
	
	public String toString() {
		return vector.toString();
	}
}

class WordVectors {
	public Map<Word,Vector> wordVectors;
	private List<Word> words;
	private List<Vector> vectors;
	
	private Comparator<Word> wordComparator = (l,r) -> l.getWord().compareTo(r.getWord());

	
	public WordVectors(String [] words, List<List<Integer>> vectors) {
		this.words = new ArrayList<Word>();
		wordVectors = new TreeMap<>(wordComparator);
		
		IntStream.range(0, words.length).forEach(i -> {
			wordVectors.put(new Word(words[i]), new Vector(vectors.get(i)));
		});
	}
	
	public void readWords(List<String> w){
		w.forEach(x -> words.add(new Word(x)));
		vectors = w.stream().map(word -> wordVectors.getOrDefault(new Word(word), Vector.DEFAULT)).collect(Collectors.toList());
	}
	
	public List<Integer> slidingWindow (int n){
		return IntStream.range(0, vectors.size()-n+1).mapToObj(i -> vectors
				.subList(i, i+n).stream().reduce
				(Vector.IDENTITY, Vector::sum))
		.map(Vector::max)
		.collect(Collectors.toList());	
		
	
	}
	

}

/**
 * Word vectors test
 */
public class WordVectorsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] words = new String[n];
        List<List<Integer>> vectors = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            words[i] = parts[0];
            List<Integer> vector = Arrays.stream(parts[1].split(":"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            vectors.add(vector);
        }
        n = scanner.nextInt();
        scanner.nextLine();
        List<String> wordsList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            wordsList.add(scanner.nextLine());
        }
        WordVectors wordVectors = new WordVectors(words, vectors);
        wordVectors.readWords(wordsList);
        n = scanner.nextInt();
       // System.out.println(wordVectors.wordVectors);
        List<Integer> result = wordVectors.slidingWindow(n);
        System.out.println(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        scanner.close();
    }
}




