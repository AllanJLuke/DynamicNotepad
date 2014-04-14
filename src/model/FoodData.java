package model;
public class FoodData {

	String servingSize = "0g";
	int calories;
	String totalFat = "0g";
	String saturatedFat = "0g";
	String transFat = "0g";
	String cholesterol = "0g";
	String sodium = "0g";
	String totalCarbs = "0g";
	String sugar = "0g";
	String protein = "0g";
	
	public String generateChart()
	{
		return ("Serving Size: " + servingSize + "\n"+
				"Calories: " + calories + "\n"+
				"Total Fat: " + totalFat + "\n"+
				"Sodium: " + sodium + "\n" +
				"Total Carbohydrates: " + totalCarbs + "\n"+
				"Sugars: " + sugar + "\n" + 
				"Protein: " + protein +"\n");
		
	}
	
}
