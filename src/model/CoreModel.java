package model;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.wordnik.client.api.WordApi;
import com.wordnik.client.common.ApiException;
import com.wordnik.client.model.Definition;


public class CoreModel {

	String apiKey = "";
	WordApi dictionaryApi;
	DatabaseManager database;

	public CoreModel()
	{
		dictionaryApi = new WordApi();
		dictionaryApi.getInvoker().addDefaultHeader("api_key",apiKey);
	}


	public boolean getNutritionalInfo(String word, FoodData food)
	{
		//WIKI REQUEST
		String data = "";
		try
		{
			Document dom = Jsoup.connect("http://en.wikipedia.org/wiki/" + word.toLowerCase().trim()).get();
			Elements table = dom.select("table[class=infobox]");
			if (table.size() > 0)
			{
				for (int i = 0; i < table.size(); i++)
				{
					if (table.get(i).text().contains("Energy"))
					{
						data = table.get(i).text();
						break;
					}
				}
			}
			else
				return false; 
		}
		catch(Exception e){return false;}


		if (data.length() > 1)
		{
			String temp = "";
			String [] tokens = data.split(" ");
			boolean servingSize = false;
			if (data.contains("Serving size"))
				servingSize = true;
			for (int i = 0; i < tokens.length; i++)
			{

				if (tokens[i].compareTo("size") == 0 && servingSize)
				{
					temp = tokens[i+1] + " " + tokens[i+2];
					food.servingSize = temp;
				}
				else if (tokens[i].compareTo("per") == 0 && !servingSize)
				{
					temp = tokens[i+1] + tokens[i+2];
					food.servingSize = temp;
				}
				else if (tokens[i].compareTo("Energy") == 0)
				{
					String val = "371";
					int cals = (((Integer.parseInt(val)*100)/4) - Integer.parseInt(val))/100;
					food.calories = cals;

				}
				else if (tokens[i].compareTo("Carbohydrates") == 0)
				{
					temp = tokens[i+1] + tokens[i+2];
					food.totalCarbs = temp;

				}
				else if (tokens[i].compareTo("Sugars") == 0)
				{
					temp = tokens[i+1] + tokens[i+2];
					food.sugar = temp;

				}
				else if (tokens[i].compareTo("fiber") == 0)
				{
					//Fiber support?
				}
				else if (tokens[i].compareTo("Fat") == 0)
				{
					temp = tokens[i+1] + tokens [i+2];
					food.totalFat = temp;

				}
				else if (tokens[i].compareTo("Protein") == 0)
				{
					temp = tokens[i+1] + tokens [i+2];
					food.protein = temp;

				}
				else if (tokens[i].compareTo("Sodium") == 0)
				{
					temp = tokens[i+1] + tokens [i+2];
					food.sodium = temp;

				}
			}
			return true;
		}
		else return false;
	}




	public FoodData getFoodDataObject()
	{
		return new FoodData();
	}

	public Food getFoodObject (String [] names, FoodData data)
	{
		return new Food (names,data);
	}

	public String getDefinition (String word)
	{
		String val = "";
		try
		{
			String [] wordList = word.split(" ");
			for (int i = 0; i < wordList.length; i++)
			{
				val+=wordList[i];
				if (i+1 != wordList.length)
					val+="+";
			}
			List <Definition> definitions = dictionaryApi.getDefinitions(val,"","wiktionary",3,"false","true","false");
			String defs = "";
			for (int i = 0; i < definitions.size(); i++)
			{
				int seq = 0;
				int maxLength = 70;
				String temp = Integer.toString(i+1) + ". " + definitions.get(i).getText() + "\n\n" ;	
				int len = temp.length();
				for (int j = 0; j < len;j++,seq++)
				{
					if (seq > maxLength && temp.charAt(j) == ' ')
					{
						seq = 0;
						temp = temp.substring(0,j)+ "\n  " + temp.substring(j);

					}
				}
				defs+= temp;
			}
			if (defs.length() > 1)
			{		
				return defs; 
			}
			else return "!None" + val;
		}
		catch (ApiException e)
		{
			e.printStackTrace();
		}
		return "!None" + val;
	}

	public void loadDatabase(String databaseName)
	{
		database = new DatabaseManager(databaseName);
	}

	public Object searchDatabase (String text)
	{
		return database.search(text);
	}


	public String getSummary(String title) {

		try
		{
			String sumDirty = "";
			String inputLine;
			System.out.println (title);
			URL wiki = new URL(" http://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&redirects&exintro=&titles=" + title);
			BufferedReader in = new BufferedReader (new InputStreamReader (wiki.openStream(),"UTF-8"));
			while ((inputLine = in.readLine()) != null)
			{
				sumDirty +=inputLine;
				System.out.println(inputLine);
			}

			int seq = 0;
			int maxLength = 70;
			String temp = sumDirty;
			int len = temp.length();
			for (int j = 0; j < len;j++,seq++)
			{
				if (seq > maxLength && temp.charAt(j) == ' ')
				{
					seq = 0;
					temp = temp.substring(0,j)+ "\n" + temp.substring(j);

				}
			}
			in.close();
			return clean (temp);		//.replaceAll("\\"," "));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private String clean (String data)
	{
		System.out.println(data);
		int trim = data.indexOf("extract") + 10;
		try
		{
			data = data.substring(trim,data.lastIndexOf('.')+1);
		}
		catch (Exception e)
		{
			return"";
		}
		String clean = "";
		boolean inBrace = false;
		for (int i = 0; i < data.length(); i++)
		{
			if (data.charAt(i) == '<' || data.charAt(i) == '>')
			{
				inBrace = !inBrace;
				continue;
			}

			if (inBrace)
				continue;
			else
				clean+=Character.toString(data.charAt(i));
		}
		return clean;

	}

}
