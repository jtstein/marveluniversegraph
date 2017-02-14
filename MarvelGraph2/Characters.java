// Jordan Stein
import java.util.ArrayList;

public class Characters {
	private String name="";
	private String num;
	public ArrayList<Integer> comicsList = new ArrayList<Integer>(); // list of all comics this character is in.
	public int spiderNum=-1; // anyone without a spiderman number will be listed as -1
	
	public Characters(String name, String num){
		this.name = name;
		this.num = num;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getNum(){
		return this.num;
	}
}
