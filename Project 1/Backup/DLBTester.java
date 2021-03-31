import java.util.*;

public class DLBTester
{
	public static void main(String[] args)
	{
		DLB dB = new DLB("dictionary.txt");
		System.out.println("contains andrew: "+dB.contains("Andrew"));
		System.out.println("contains tijme: "+dB.contains("tijme"));
		System.out.println("contains time: "+dB.contains("time"));
		System.out.println("contains thename: "+dB.contains("thename"));
		System.out.println("contains timetogonow: "+dB.contains("timetogonow"));
		System.out.println("contains timetogo: "+dB.contains("timetogo"));
		System.out.println("butt: ");
		ArrayList<String> words = dB.getWords("butt", 4);
		System.out.println(words.size());
		for(int i=0;i<words.size();i++)
		{
			System.out.println(words.get(i));
		}

		Map<String, String> map = new HashMap();
	}
}