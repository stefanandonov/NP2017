package av1;
import java.util.*;
import java.util.stream.IntStream;
import java.*;



public class StringPrefix {
	
	public static boolean isPrefix (String str1, String str2){
		if (str1.length()>str2.length())
			return false;
		
		return IntStream.range(0, str1.length())
		.allMatch(i -> str1.charAt(i) == str2.charAt(i));
		
		
	}

	public static void main(String[] args) {
		String s1 = "Stefan";
		String s2 = "Stefan Andonov";
		
		System.out.println(isPrefix(s1,s2));
/*
		Integer [] in = {1,2,3,4,5,6,7,8,9,10};
		
		Arrays.stream(IntStream.range(in[0], in[in.length-1]).filter(i -> i%2==0).toArray()).forEach(i -> System.out.println(i));
		
	*/			
		

	}

}
