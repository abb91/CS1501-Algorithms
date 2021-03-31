import java.io.*;
import java.util.*;

class Node<T>
{
	T data;
	Node<T> next=null;
	LinkedList<T> below=null;
	public Node(T data, Node<T> next)
	{
		this.data = data;
		this.next = next;
	}
	public Node(T data)
	{
		this.data = data;
	}
	public LinkedList<T> getBelow()
	{
		if(below==null)
			below = new LinkedList<T>();

		return below;
	}
}
class LinkedList<T>
{
	Node<T> head;
	public LinkedList()
	{
		head = null;
	}
	public Node<T> insert(T data) //always insert at head, return node created (or found) for use in DLB -- duplicates simply ignored 
	{
		if(head==null)
			head = new Node<T>(data);

		if(!contains(data))
			head = new Node<T>(data, head);

		return search(data);
	}
	public boolean contains(T data)
	{
		return search(data)!=null;
	}
	public Node<T> search(T data)
	{
		Node<T> curr = head;
		while(curr != null)
		{
			if(data.equals(curr.data))
				return curr;

			curr = curr.next;
		}
		return null;
	}
	public int size()
	{
		int count=0;
		Node<T> curr = head;
		while(curr != null)
		{
			count++;
			curr = curr.next;
		}

		return count;
	}
	public boolean empty()
	{
		return head==null; 
	}
}
public class DLB
{
	LinkedList<Character> root;
	public DLB(String fileName)
	{
		root = new LinkedList<Character>();
		try
		{
			File file = new File(fileName);
			if(!file.exists())
				file.createNewFile();

			BufferedReader inFile = new BufferedReader(new FileReader(file));
			while(inFile.ready())
				add(inFile.readLine());

			inFile.close();
		}
		catch(Exception e)
		{
			System.out.println("Error: "+ e);
			System.exit(0);
		}
	}
	public void add(String word)
	{
		char[] key = (word+"^").toCharArray();
		LinkedList<Character> level = root;
		Node<Character> currNode;
		for(int i=0;i<key.length;i++)
		{
			currNode = level.insert(key[i]);
			level = currNode.getBelow();
		}
	}
	public boolean contains(String word)
	{
		return search(word+"^")!=null;
	}
	public Node<Character> search(String word)
	{
		char[] key = word.toCharArray();
		LinkedList<Character> level = root;
		Node<Character> currNode=null;
		for(int i=0;i<key.length;i++)
		{
			currNode = level.search(key[i]);
			if(currNode == null)
				return null;
			level = currNode.getBelow();
		}
		return currNode;
	}
	public ArrayList<String> getEndings(Node<Character> curr)
	{
		Node<Character> iterator = curr.below.head;
		ArrayList<String> endings = new ArrayList<String>();

		if(iterator == null)
		{
			endings.add("^");
			return endings;
		}
		ArrayList<String> vals;
		while(iterator != null)
		{
			vals = getEndings(iterator);
			for(int i=0;i<vals.size();i++)
			{
				endings.add(String.valueOf(curr.data) + vals.get(i));
			}

			iterator = iterator.next;
		}

		return endings;
	}
	public ArrayList<String> getWords(String key) //always requires 5
	{
		Node<Character> currNode = search(key);
		if(currNode == null)
			return new ArrayList<String>();

		ArrayList<String> temp = new ArrayList<String>();
		if(contains(key))
			temp.add(key);

		Node<Character> iterator = currNode.below.head;
		while(iterator != null) //generate subwords as necessary
		{
			temp.addAll(getEndings(iterator));
			if(temp.size() >= 5) //found necessary amount, can break
				break;

			iterator = iterator.next;
		}

		ArrayList<String> words = new ArrayList<String>(); //trimmed/processed list
		//take the smaller amt of either required words or size of words found and return processed to main
		for(int i=0; i < (temp.size() < 5 ? temp.size() : 5);i++) 
		{
			words.add(key+(temp.get(i)).substring(0, (temp.get(i)).length() - 1) );
		}

		return words;
	}
	public ArrayList<String> getWords(String key, ArrayList<String> alreadyFound)
	{
		int reqAmt = 5-alreadyFound.size();
		if(reqAmt == 0)
			return new ArrayList<String>();

		Node<Character> currNode = search(key);
		if(currNode == null)
			return new ArrayList<String>();

		ArrayList<String> temp = new ArrayList<String>();

		Node<Character> iterator = currNode.below.head;
		while(iterator != null) //generate subwords as necessary
		{
			temp.addAll(getEndings(iterator));
			if(temp.size() >= reqAmt) //found necessary amount, can break
				break;

			iterator = iterator.next;
		}

		ArrayList<String> words = new ArrayList<String>(); //trimmed/processed list
		String word;
		//take the smaller amt of either required words or size of words found and return processed to main
		int count = 0;
		if(contains(key) && !alreadyFound.contains(key)) //ensures that the original key itself will be checked for
		{
			words.add(key);
			count=1;
		}
		int maxCount = temp.size() < reqAmt ? temp.size() : reqAmt;
		for(int i=0; i < temp.size();i++) 
		{
			word = key+temp.get(i).substring(0, temp.get(i).length()-1);
			if(!alreadyFound.contains(word))
			{
				words.add(word);
				count++;
			}
			if(count == maxCount)
				return words;
		}

		return words;
	}
	public void printToFile(String fileName) 
	{
		try
		{
			BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
			Node<Character> curr = root.head;
			ArrayList<String> words;
			while(curr != null)
			{
				words = getWords(String.valueOf(curr.data));
				for(int i=0;i<words.size();i++)
				{
					outFile.write(words.get(i)+"\n");
				}

				curr = curr.next;
			}
			outFile.close();
		}
		catch(IOException e)
		{
			System.exit(0);
		}
	}
}