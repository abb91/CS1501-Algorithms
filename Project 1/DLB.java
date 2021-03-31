import java.io.*;
import java.util.*;

class Node<T>
{
	T data;
	Node<T> next;
	LinkedList<T> below;
	public Node(T data, Node<T> next, LinkedList<T> below)
	{
		this.data = data;
		this.next = next;
		this.below = below;
	}
	public Node(T data, Node<T> next)
	{
		this.data = data;
		this.next = next;
		this.below = null;
	}
	public Node(T data)
	{
		this.data = data;
		this.next = null;
		this.below = null;
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
			BufferedReader inFile = new BufferedReader(new FileReader(fileName));
			while(inFile.ready())
				insertWord(inFile.readLine());

			inFile.close();
		}
		catch(Exception e)
		{
			System.out.println("Error: "+ e);
			System.exit(0);
		}
	}
	public void insertWord(String word)
	{
		char[] key = getChar(word);
		LinkedList<Character> level = root;
		Node<Character> currNode;
		for(int i=0;i<key.length;i++)
		{
			System.out.println(key[i]);
			currNode = level.insert(key[i]);
			level = currNode.getBelow();
		}
	}
	public boolean contains(String word)
	{
		char[] key = getChar(word);
		LinkedList<Character> level = root;
		Node<Character> currNode;
		for(int i=0;i<key.length;i++)
		{
			currNode = level.search(key[i]);
			if(currNode == null)
				return false;
			level = currNode.getBelow();
		}
		return true;
	}
	public char[] getChar(String word)
	{return (word+"^").toCharArray();}
}