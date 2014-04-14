package model;
class Person {

	String []names ;
	PersonData data = new PersonData();
	public Person(String [] s)
	{
		names = s;
	}
	
	public String toString()
	{
		return ("Name: " + names[0] + "\n" + "Age: " + data.age);
	}
}
