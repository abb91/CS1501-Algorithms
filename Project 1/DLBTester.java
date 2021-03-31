public class DLBTester
{
	public static void main(String[] args)
	{
	DLB dB = new DLB("dictionary.txt");
	System.out.println("contains andrew: "+dB.contains("andrew"));
	System.out.println("contains tijme: "+dB.contains("tijme"));
	System.out.println("contains time: "+dB.contains("time"));
	System.out.println("contains thename: "+dB.contains("thename"));
	System.out.println("contains timetogonow: "+dB.contains("timetogonow"));
	System.out.println("contains timetogo: "+dB.contains("timetogo"));
	}
}