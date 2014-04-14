package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JLabel;

class DatabaseManager {

	Hashtable <String,Object> database = new Hashtable <String,Object>();
	String fileName;
	public DatabaseManager (String file)
	{
		this.fileName = file;
		populate();
	}

	private void populate()
	{   
		FileReader file = null;	
		try
		{
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				if (line.trim().split(":")[0].compareTo("Name") == 0)
				{
					String [] data;
					Object current = null;
					String[] names;//PRIORITY: PARSE THE NAMES
					names = line.trim().split(":")[1].split(",");

					while ((line = reader.readLine().trim()).compareTo("End") != 0)
					{
						data = line.replaceAll(" ","").split(":");
						if (data[0].compareTo("Type") == 0)//ADD VARIOUS TYPES HERE
						{
							if (data[1].compareTo("Person") == 0)
							{
								current = new Person(names);
							}
							else if (data[1].compareTo("Food") == 0)
							{
								current = new Food(names,new FoodData());
							}
						}
						//ADD VARIOUS ATTRIBUTES FROM HERE
						else if (data[0].compareTo ("Age") == 0)
						{
							((Person)current).data.age = Integer.parseInt(data[1].trim());
						}
						else if (data[0].compareTo ("Price") == 0)
						{
							((Product)current).setPrice(Double.parseDouble(data[1].split("/")[0]));
							((Product)current).setUnit(data[1].split("/")[1].trim());
						}
						else if (data[0].compareTo("Barcode") == 0)
						{
							((Product)current).setBarcode(data[1].trim());
						}
						else if (data[0].compareTo("Calories") == 0)
						{
							((Food)current).data.calories = Integer.parseInt(data[1].trim());
						}
						else if (data[0].compareTo ("ServingSize") == 0)
						{
							((Food)current).data.servingSize = data[1].trim();
						}
						else if (data[0].compareTo ("TotalFat") == 0)
						{
							((Food)current).data.totalFat = data[1].trim();
						}
						else if (data[0].compareTo("SaturatedFat") == 0)
						{
							((Food)current).data.saturatedFat = data[1].trim();
						}
						else if (data[0].compareTo("Cholesterol") == 0)
						{
							((Food)current).data.cholesterol = data[1].trim();
						}
						else if (data[0].compareTo("Sodium") == 0)
						{
							((Food)current).data.sodium = data[1].trim();
						}
						else if (data[0].compareTo("TotalCarbohydrates") == 0)
						{
							((Food)current).data.totalCarbs = data[1].trim();
						}
						else if (data[0].compareTo("Sugar") == 0)
						{
							((Food)current).data.sugar = data[1].trim();
						}
						else if (data[0].compareTo("Protein") == 0)
						{
							((Food)current).data.protein = data[1].trim();
						}			
					}
					for (int i = 0; i < names.length; i++)
					{

						database.put(names[i].trim(),current);
					}
					current = null;
				}
			}
			file.close();
		}
		catch (IOException e)
		{
			System.out.println (e);
		}
	}

	public Object search(String word)
	{
		String value = clean(word.trim());

		return(database.get(word));



		//				JLabel label = new JLabel(data[i]);
		//				label.setAlignmentY(0.85f);
		//				label.addMouseListener(new LabelListener(database.get(data[i]),(JFrame)this));	
		//				int caret = findPos(data[i]);  
		//				text.insertComponent(label);
		//				label.addPropertyChangeListener(new LabelPropertyChange(caret,offset));

	}

	String clean (String value)
	{
		String fixed = "";
		for (int i = 0; i < value.length(); i++)
		{
			if (Character.isLetter(value.charAt(i))||value.charAt(i) == ' ')
			{
				fixed += Character.toString((value.charAt(i)));
			}
			else
				fixed +=' ';
		}
		return fixed;
	}

}
