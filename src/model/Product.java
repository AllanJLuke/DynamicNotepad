package model;
abstract class Product {

	protected double price;
	protected String barcode;
	protected String[] names; 
	protected String unit;
	
    public Product(double price, String barcode, String[] names, String unit)
    {
    	this.price = price;
    	this.barcode = barcode;
    	this.names = names;
    	this.unit = unit;
    }
    
    public Product (String[] names)
    {
    	this.names = names;
    }
    
    public double getPrice()
    {
    	return price;
    }
    
    public void setPrice(double price)
    {
    	this.price = price;
    }
    
    public void setBarcode(String code)
    {
    	this.barcode = code;
    }
    
    public void setUnit (String unit)
    {
    	this.unit = unit;
    }
    public String getBarcode()
    {
    	return barcode;
    }
    
    public abstract String toString();
    	
}
