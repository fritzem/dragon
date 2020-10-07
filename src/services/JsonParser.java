package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class JsonParser {
	
	private Stack<Object> jsonStack;
	private Object head;
	
	private String name;
	private String content;
	private boolean open;
	private boolean named;
	private boolean contented;
	
	//Quick json parser....
	//this is bad
	//no error catching so just dont mess up
	//no booleans
	public void parse(File file)
	{
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		head = null;
		jsonStack = new Stack<Object>();
		
		name = "";
		content = "";
		open = false;
		named = false;
		contented = false;
		
		while (s.hasNext()) {
			String line = s.nextLine();
			
			
			for (int i = 0; i < line.length(); i++)
			{
				char ch = line.charAt(i);
				
				
				switch (ch)
				{
				case '{':
					HashMap<String, Object> dict = new HashMap<String, Object>();
					if (head == null)
						head = dict;
					else
						place(dict, name);
					jsonStack.push(dict);
					break;
				case '}':
					if (contented)
						place(content, name);
					jsonStack.pop();
					break;
				case '[':
					ArrayList<Object> arr = new ArrayList<Object>();
					if (head == null)
						head = arr;
					else
						place(arr, name);
					jsonStack.push(arr);
					break;
				case ']':
					if (contented)
						place(content, name);
					jsonStack.pop();
					break;
				case '"':
					if (open)
					{
						open = false;
						if (!named && inMap()) 
							named = true;
					}
					else
					{
						open = true;
					}
					break;
				case ' ':
				case ':':
					if (open)
					{
						if (named || !inMap())
							content += ch;
						else
							name += ch;
					}
					break;
				case ',':
					if (open)
					{
						if (named || !inMap())
							content += ch;
						else
							name += ch;
					}
					else if (contented)
						place(content, name);
					break;
				default:
					if (open)
					{
						if (named || !inMap())
						{
							content += ch;
							contented = true;
						}
						else if (inMap() && !named)
							name += ch;
					}
					else if (ch >= 48 && ch <= 57)
					{
						content += ch;
						contented = true;
					}
					break;	
				}
				
			}
		}
	}
	
	public boolean inMap()
	{
		return (jsonStack.peek() instanceof HashMap<?,?>);
	}
	
	public boolean isMap(Object obj) {return (obj instanceof HashMap<?,?>);}
	public boolean isArr(Object obj) {return (obj instanceof ArrayList<?>);}
	
	public void place(Object toPlace, String name)
	{
		Object obj = jsonStack.peek();
		if (inMap())
			((HashMap<String, Object>) obj).put(name, toPlace);
		else
			((ArrayList) obj).add(toPlace);
		
		if (toPlace instanceof String)
		{
			content = "";
			contented = false;
		}
		this.name = "";
		named = false;
	}
	
	public void everything()
	{
		readObj(head);
	}
	
	public void readObj(Object obj)
	{
		if (isMap(obj))
		{
			HashMap<String, Object> mObj = ((HashMap<String, Object>) obj);
			for (String key : mObj.keySet())
			{
				readObj(mObj.get(key));
			}
		}
		else if (isArr(obj))
		{
			ArrayList<Object> aObj = ((ArrayList) obj);
			for (Object item : aObj)
			{
				readObj(item);
			}
		}
		else
		{
			System.out.println("\"" + (String)obj + "\"");
		}
	}
	
	public Object getObj(String name)
	{
		return diveObj(head, name);
	}
	
	public Object diveObj(Object obj, String search)
	{
		if (isMap(obj))
		{
			
			HashMap<String, Object> mObj = ((HashMap<String, Object>) obj);
			for (String key : mObj.keySet())
			{
				System.out.println(key);
				if (key.compareTo(search) == 0)
					return mObj.get(key);
				Object retObj = diveObj(mObj.get(key), search);
				if (retObj != null)
					return retObj;
			}
		}
		else if (isArr(obj))
		{
			System.out.println("arr");
			ArrayList<Object> aObj = ((ArrayList<Object>) obj);
			for (Object item : aObj)
			{
				Object retObj = diveObj(item, search);
				if (retObj != null)
					return retObj;
			}
		}
		
		return null; 
	}
	
}
