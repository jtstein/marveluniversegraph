/* Jordan Stein
 * CSCI 3412 - Fall 2015
 * Program 3
 */
import java.io.*;
import java.util.*;
public class MarvelGraph2 {
	
		public static void main(String[] args){
		
		System.out.println("How many marvel characters would you like to view?");
		Scanner in = new Scanner(System.in);
		int x = in.nextInt();
		in.close();
					
		Characters ch[] = readData();	
		int accesses = computeSpiderNum(ch);
		
		for (int i=0; i < x; i++)
			System.out.println(ch[i].getName() + " has spider man number of " + ch[i].spiderNum);
		
		
		System.out.println("\nIt took " + accesses + " verticy accesses to compute every spiderman number for every comic list.");
		
	}
		
	public static int computeSpiderNum(Characters[] ch){
		
		int accesses = 0;
		
		int spidermanIndex = 0;
		Boolean foundSpidey = false;
		
		int i=0;
		while (!foundSpidey) // set spiderman's spider number to zero.
		{
			for (int j=0; j < ch[i].comicsList.size(); j++)
			{
				if (ch[i].getName().equals("SPIDER-MAN/PETER PAR"))
				{	
					ch[i].spiderNum = 0; // set spiderNum to zero for spider man himself.
					spidermanIndex = i; // stores spider man's index
					foundSpidey = true;
					break;
				}
			}
			i++;
		}
		
		if (spidermanIndex == 0)
			spidermanIndex = 5305; // hard coded his index in case user didn't input enough characters to get to spider man.
		
		
		//System.out.println("Amount whose spiderNum is 0 = " + 1);
		
		ArrayList<Characters> numberOners = new ArrayList<Characters>(); // stores everyone with a spiderman number of 1
		
		
		for (i = 0; i < ch.length-1; i++) // calculate everyone who has a spiderman number of 1.
		{
			innerLoop:
			for (int j=0; j < ch[i].comicsList.size(); j++)
			{
				int comic = ch[i].comicsList.get(j);

				for (int k=0; k < ch[spidermanIndex].comicsList.size(); k++)
				{
					if (comic == ch[spidermanIndex].comicsList.get(k))
					{
						ch[i].spiderNum = 1; // this character is in a comic with spiderman.
						numberOners.add(ch[i]);
						break innerLoop;
					}
				}
				
				accesses++;
			}
		
		}

	//	System.out.println("Amount whose spiderNum is 1 = " + numberOners.size());
		
		ArrayList<Characters> numberTwoers = new ArrayList<Characters>(); // stores everyone with a spiderman number of 2
		for (i = 0; i < ch.length-1; i++) // calculate everyone who has a spiderman number of 1.
		{
			innerLoop:
			if (ch[i].spiderNum == -1)
			{
				for (int j=0; j < ch[i].comicsList.size(); j++)
				{
					int comic = ch[i].comicsList.get(j);
					for (int k=0; k < numberOners.size(); k++)
					{
						for (int l=0; l < numberOners.get(k).comicsList.size(); l++)
						{
							if (comic == numberOners.get(k).comicsList.get(l))
							{
								ch[i].spiderNum = 2; // this character is in a comic with spiderman.
								numberTwoers.add(ch[i]);
								break innerLoop;
							}
						}
					}
					accesses++;
				}
			}
		}

	//	System.out.println("Amount whose spiderNum is 2 = " + numberTwoers.size());

		ArrayList<Characters> numberThreeers = new ArrayList<Characters>(); // stores everyone with a spiderman number of 3
		for (i = 0; i < ch.length-1; i++) // calculate everyone who has a spiderman number of 1.
		{
			innerLoop:
			if (ch[i].spiderNum == -1)
			{
				for (int j=0; j < ch[i].comicsList.size(); j++)
				{
					int comic = ch[i].comicsList.get(j);
					for (int k=0; k < numberTwoers.size(); k++)
					{
						for (int l=0; l < numberTwoers.get(k).comicsList.size(); l++)
						{
							if (comic == numberTwoers.get(k).comicsList.get(l))
							{
								ch[i].spiderNum = 3; // this character is in a comic with spiderman.
								numberThreeers.add(ch[i]);
								break innerLoop;
							}
						}
					}
					accesses++;
				}
			}
		}
		
	//	System.out.println("Amount whose spiderNum is 3 = " + numberThreeers.size());
		
		ArrayList<Characters> numberFourers = new ArrayList<Characters>(); // stores everyone with a spiderman number of 3
		for (i = 0; i < ch.length-1; i++) // calculate everyone who has a spiderman number of 1.
		{
			innerLoop:
			if (ch[i].spiderNum == -1)
			{
				for (int j=0; j < ch[i].comicsList.size(); j++)
				{
					int comic = ch[i].comicsList.get(j);
					for (int k=0; k < numberThreeers.size(); k++)
					{
						for (int l=0; l < numberThreeers.get(k).comicsList.size(); l++)
						{
							if (comic == numberThreeers.get(k).comicsList.get(l))
							{
								ch[i].spiderNum = 3; // this character is in a comic with spiderman.
								numberFourers.add(ch[i]);
								break innerLoop;
							}
						}
					}
					accesses++;
				}
			}
		}

		return accesses;
	}
	

	public static Characters[] readData(){ // read data from file to build list graph
		File file = new File("porgat.txt");
		Characters[] readIn = new Characters[6487];
		try{
			Scanner sc = new Scanner(file);
			int i = 0;
			while (sc.hasNextLine() && i < 6487)
			{
				String data = sc.nextLine();
				
				char[] dataCh = data.toCharArray();
				
				int j=0;
				while (j < dataCh.length) // calculates the offset for inserting name string into array.
				{
					if (!Character.isDigit(dataCh[j]))
						break;
					j++; // j stores that offset
				}
				
				if (i > 0)
				{				// reads that offset into the substring of what was read in from the file.
					readIn[i-1] = new Characters(data.substring(j+2, data.length()-1), data.substring(0,j));
				} 				// creates new character, first field is the name, second field is their number.
				i++;
			}
			
			while (i < 19430)
			{
				String comicVerticies = sc.nextLine();
				i++;
			}
			// now the scanner is at the edgelist.

			String edgeList[] = new String[30520-19430];
			i=0;
			while(sc.hasNextLine())
			{
				edgeList[i] = sc.nextLine(); // stores all edges into the array.
				i++;
			}
			
			i=0;
			while (i < edgeList.length)
			{
				String[] edges = edgeList[i].split(" "); // creates a string array, splitting all edge values.
				for (int j = 1; j < edges.length; j++)
				{
					int edgeNum = Integer.parseInt(edges[0]);
					edgeNum--; //decrement because edge list starts at 1.
					readIn[edgeNum].comicsList.add(Integer.parseInt(edges[j])); // adds comic to the comic list
				}	
				i++;
			}
				
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return readIn;
	}
}
