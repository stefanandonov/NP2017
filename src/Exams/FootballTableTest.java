package Exams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */


class Team {
	private String teamName;
	private int games;
	private int wins;
	private int draws;
	private int lost;
	private int goalsFor;
	private int goalsAgainst;
	
	public Team (String t){
		teamName=t;
		games=wins=draws=lost=goalsFor=goalsAgainst = 0;
	}
	
	public void updateTeam (int result, int za, int protiv){
		games++;
		if (result==1)
			wins++;
		else if (result==0)
			draws++;
		else 
			lost++;
		
		goalsFor+=za;
		goalsAgainst+=protiv;			
	}
	
	public int points() {
		return wins*3+draws;
	}
	
	public int goalsDif() {
		return goalsFor-goalsAgainst;
	}
	
	public String toString() {
		return String.format("%-15s%5d%5d%5d%5d%5d%5d", teamName,games,wins,draws,lost,this.points());
	}
	
}
class FootballTable {
	
	Map<String,Team> teams;
	int i;
	
	public FootballTable () {
		teams = new HashMap<>();
		i=1;
	}
	
	public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
		Team home = teams.get(homeTeam);
		Team away = teams.get(awayTeam);
		
		if (home==null)
			home = new Team(homeTeam);
		if (away==null)
			away = new Team(awayTeam);
		
		if (homeGoals>awayGoals){
			home.updateTeam(1, homeGoals, awayGoals);
			away.updateTeam(-1, awayGoals, homeGoals);
		}
		else if (homeGoals==awayGoals){
			home.updateTeam(0, homeGoals, awayGoals);
			away.updateTeam(0, awayGoals, homeGoals);
		}
		else {
			home.updateTeam(-1, homeGoals, awayGoals);
			away.updateTeam(1, awayGoals, homeGoals);
		}
		
		teams.put(homeTeam, home);
		teams.put(awayTeam, away);
	}
	
	public void printTable() {
		//int i=1;
		teams
		.values()
		.stream()
		.sorted(Comparator.comparing(Team::points).thenComparing(Team::goalsDif).reversed())
		.collect(Collectors.toList()).forEach(x-> {
			System.out.println(String.format("%-2d", i)+". "+x.toString());
			i++;
		});;
	}
}
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

