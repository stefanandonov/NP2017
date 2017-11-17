package prvkolokvium;

import java.util.Scanner;

/**
 * I partial exam 2016
 */
public class ApplicantEvaluationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int creditScore = scanner.nextInt();
        int employmentYears = scanner.nextInt();
        boolean hasCriminalRecord = scanner.nextBoolean();
        int choice = scanner.nextInt();
        Applicant applicant = new Applicant(name, creditScore, employmentYears, hasCriminalRecord);
        Evaluator.TYPE type = Evaluator.TYPE.values()[choice];
        Evaluator evaluator = null;
        try {
            evaluator = EvaluatorBuilder.build(type);
            System.out.println("Applicant");
            System.out.println(applicant);
            System.out.println("Evaluation type: " + type.name());
            if (evaluator.evaluate(applicant)) {
                System.out.println("Applicant is ACCEPTED");
            } else {
                System.out.println("Applicant is REJECTED");
            }
        } catch (InvalidEvaluation invalidEvaluation) {
            System.out.println("Invalid evaluation");
        }
    }
}

class Applicant {
    private String name;

    private int creditScore;
    private int employmentYears;
    private boolean hasCriminalRecord;

    public Applicant(String name, int creditScore, int employmentYears, boolean hasCriminalRecord) {
        this.name = name;
        this.creditScore = creditScore;
        this.employmentYears = employmentYears;
        this.hasCriminalRecord = hasCriminalRecord;
    }

    public String getName() {
        return name;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getEmploymentYears() {
        return employmentYears;
    }

    public boolean hasCriminalRecord() {
        return hasCriminalRecord;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nCredit score: %d\nExperience: %d\nCriminal record: %s\n",
                name, creditScore, employmentYears, hasCriminalRecord ? "Yes" : "No");
    }
}

interface Evaluator {
    enum TYPE {
        NO_CRIMINAL_RECORD,
        MORE_EXPERIENCE,
        MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE,
        MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE,
        NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE,
        INVALID // should throw exception
    }

    boolean evaluate(Applicant applicant) throws InvalidEvaluation;
}

class EvaluatorBuilder {
    public static Evaluator build(Evaluator.TYPE type) throws InvalidEvaluation {
        
        if (type==Evaluator.TYPE.NO_CRIMINAL_RECORD || type==Evaluator.TYPE.MORE_CREDIT_SCORE || type==Evaluator.TYPE.MORE_CREDIT_SCORE || type==Evaluator.TYPE.INVALID)
        	return new SingleEvaluator(type);
        else if (type==Evaluator.TYPE.MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE){
        	return new CombinatedEvaluator(new SingleEvaluator(Evaluator.TYPE.MORE_CREDIT_SCORE),new SingleEvaluator(Evaluator.TYPE.MORE_EXPERIENCE));
        }
        else if (type==Evaluator.TYPE.NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE) {
        	return new CombinatedEvaluator(new SingleEvaluator(Evaluator.TYPE.MORE_CREDIT_SCORE),new SingleEvaluator(Evaluator.TYPE.NO_CRIMINAL_RECORD));
        }
        else 
        	return new CombinatedEvaluator(new SingleEvaluator(Evaluator.TYPE.NO_CRIMINAL_RECORD),new SingleEvaluator(Evaluator.TYPE.MORE_EXPERIENCE));
        
        
    }
}


class SingleEvaluator implements Evaluator {

	private TYPE t;
	
	public SingleEvaluator(TYPE t) {
		this.t=t;
	}
	@Override
	public boolean evaluate(Applicant applicant) throws InvalidEvaluation {
		if (t==TYPE.MORE_CREDIT_SCORE){
			return applicant.getCreditScore()>=500;
			}
		else if (t==TYPE.MORE_EXPERIENCE){
			return applicant.getEmploymentYears()>=10;
		}
		else if (t==TYPE.NO_CRIMINAL_RECORD)
			return !applicant.hasCriminalRecord();
		else 
			throw new InvalidEvaluation();
			
	}
	
}

class CombinatedEvaluator implements Evaluator {
	private Evaluator e1;
	private Evaluator e2;
	
	public CombinatedEvaluator (Evaluator e1, Evaluator e2) {
		this.e1=e1;
		this.e2=e2;
	}

	@Override
	public boolean evaluate(Applicant applicant) throws InvalidEvaluation {
		return e1.evaluate(applicant) && e2.evaluate(applicant);
	}	
}

class InvalidEvaluation extends Exception {

	InvalidEvaluation () {
		super ("InvalidEvaluation");
	}
	private static final long serialVersionUID = 1L;
	
}



