import java.io.*;
import java.util.*;

public class ac_test
{
	public static void main(String[] args)
	{
		DLB dict = new DLB("dictionary.txt");
		DLB userHist = new DLB("user_history.txt");

		ArrayList<String> history, predictions;
		ArrayList<String> merged = new ArrayList<String>();
		ArrayList<Double> timings = new ArrayList<Double>();
		Double time;
		Boolean goAgain=false;
		String word="";
		Scanner sc = new Scanner(System.in);
		char letter;
		do
		{
			if(!goAgain) //used initially as a flag for the first run
			{
				System.out.print("Enter your first character: ");
				goAgain=true;
			}
			else
			{
				if(word.equals(""))
				{
					System.out.print("Enter the first character of the next word: ");
				}
				else
				{
					System.out.print("\nEnter the next character: ");
				}
			}
			letter = sc.next().charAt(0);
			switch(letter)
			{
				case '1':
					word = completeWord(merged.get(0), userHist);
					break;
				case '2':
					word = completeWord(merged.get(1), userHist);
					break;
				case '3':
					word = completeWord(merged.get(2), userHist);
					break;
				case '4':
					word = completeWord(merged.get(3), userHist);
					break;
				case '5':
					word = completeWord(merged.get(4), userHist);
					break;
				case '$':
					word = completeWord(word, userHist);
					break;
				case '!':
					double sum = 0;
					userHist.printToFile("user_history.txt");
					for(int i=0;i<timings.size();i++)
						sum+=timings.get(i);

					System.out.print("\n\nAverage time: ");
					System.out.printf("%.6f",sum/timings.size());
					System.out.print(" s\nBye!");
					goAgain = false;
					break;
				default:
					word += String.valueOf(letter);
					time = getTime();
					history = userHist.getWords(word);
					predictions = dict.getWords(word, history);
					timings.add(getTime()-time);
					merged = mergeLists(history, predictions);
					printPredictions(merged, timings.get(timings.size()-1));
					break;
			}
		}while(goAgain);
	}
	public static double getTime()
	{return (double)System.nanoTime()/1e9;}
	public static ArrayList<String> mergeLists(ArrayList<String> a, ArrayList<String> b)
	{
		a.addAll(b);
		return a;
	}
	public static void printPredictions(ArrayList<String> predictions, double time)
	{
		System.out.print("\n(");
		System.out.printf("%.6f", time);
		System.out.println(" s)\nPredictions:");
		for(int i=0;i<predictions.size();i++)
		{
			System.out.print("("+(i+1)+") "+predictions.get(i)+"     ");
		}
		System.out.println();
	}
	public static String completeWord(String word, DLB userHist)
	{
		System.out.println("\n\nWORD COMPLETED: " + word);
		System.out.println();
		userHist.add(word);
		return "";
	}
}