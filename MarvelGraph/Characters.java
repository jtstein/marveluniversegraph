// Jordan Stein

import java.util.ArrayList;

public class Characters {
	private String name="";
	private String num;
	public Boolean[] comics = new Boolean[19429];
	public int spiderNum=-1; // anyone without a spiderman number will be listed as -1
	
	public Characters(String name, String num){
		this.name = name;
		this.num = num;
		
		for (int i=0; i < comics.length; i++)
		{
			comics[i] = false; // initializes all comics to false when character is created
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getNum(){
		return this.num;
	}
}
