package model;

class Food extends Product {
    
	FoodData data;
	public Food (String []names, double price, String barcode, String per)
	{
		super (price,barcode,names,per);
		data = new FoodData();
	}
	
	public Food (String []names, FoodData data)
	{
		super(names);
		this.data = data;
	}
	
	public String toString()
	{
		return (names[0].trim() + "\n" +data.generateChart());
	}
	
}
