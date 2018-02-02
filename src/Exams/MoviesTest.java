package Exams;
import java.util.*;
import java.util.stream.Collectors;


class Movie {
	protected String name;
	protected int [] ratings;
	private double ratingcoef;
	
	public Movie(String name, int[] ratings) {
		super();
		this.name = name;
		this.ratings = ratings;
		ratingcoef = 0;
	} 
	
	public double average() {
		return Arrays.stream(ratings).average().getAsDouble();
	}
	
	public String toString() {
		return String.format("%s (%.2f) of %d ratings", name,this.average(),ratings.length);
	}	
	
	public void ratingCoef(int max) {
		ratingcoef = this.average()*ratings.length/max;
	}	
	
	public double getRatingCoef(){
		return ratingcoef;
	}
	
	public String getName() {
		return name;
	}
}

class MoviesList {
	private List<Movie> movies;
	
	public MoviesList() {
		movies = new ArrayList<>();
	}
	
	public void addMovie(String title, int[] ratings){
		movies.add(new Movie(title,ratings));
	}
	
	public List<Movie> top10ByAvgRating() {
		return movies.stream()
				.sorted(Comparator.comparing(Movie::average).reversed().thenComparing(Movie::getName))
				.collect(Collectors.toList())
				.subList(0, 10);
	}
	
	public List<Movie> top10ByRatingCoef() {
		movies.stream().forEach(x -> x.ratingCoef(movies.size()));
		return movies.stream()
				.sorted(Comparator.comparing(Movie::getRatingCoef).reversed().thenComparing(Movie::getName))
				.collect(Collectors.toList())
				.subList(0, 10);
	}
}
public class MoviesTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    MoviesList moviesList = new MoviesList();
    int n = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < n; ++i) {
      String title = scanner.nextLine();
      int x = scanner.nextInt();
      int[] ratings = new int[x];
      for (int j = 0; j < x; ++j) {
        ratings[j] = scanner.nextInt();
      }
      scanner.nextLine();
      moviesList.addMovie(title, ratings);
    }
    scanner.close();
    List<Movie> movies = moviesList.top10ByAvgRating();
    System.out.println("=== TOP 10 BY AVERAGE RATING ===");
    for (Movie movie : movies) {
      System.out.println(movie);
    }
    movies = moviesList.top10ByRatingCoef();
    System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
    for (Movie movie : movies) {
      System.out.println(movie);
    }
  }
}


// vashiot kod ovde