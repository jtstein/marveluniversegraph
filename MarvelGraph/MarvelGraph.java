/* Jordan Stein
 * CSCI 3412 - Fall 2015
 * Program 3
 */

import java.io.*;
import java.util.*;
public class MarvelGraph {
	
		public static void main(String[] args){
		
		System.out.println("How many marvel characters would you like to view?");
		Scanner in = new Scanner(System.in);
		int x = in.nextInt();
		in.close();
					
		Characters ch[] = readData();	
		int accesses = computeSpiderNum(ch);
		
		//collaborationMatrix(ch); // long run time, un-comment to view total collaborations / pairs
		
		for (int i=0; i < x; i++)
			System.out.println(ch[i].getName() + " has spider man number of " + ch[i].spiderNum);
		
		System.out.println("\nIt took " + accesses + " verticy accesses to compute every spiderman number for the entire matrix.");
		
	}
		
	public static void collaborationMatrix(Characters[] ch){
		
		int[][] collab = new int[ch.length][ch.length]; // collaboration matrix
		
		for (int i=0; i < ch.length-1; i++)
		{
			int count = 0; // reset count for next character
			
			for(int j=1; j < ch.length-2; j++)
			{
				for (int k=0; k < ch[i].comics.length-1; k++)
				{
					if (ch[i].comics[k] == ch[j].comics[k] && i != j)
						count++; // increment count if their comics are the same
				}
				collab[i][j] = count; // store that count into the collab matrix
			}
		}
		
		int totalCollab = 0;
		int totalCollabPairs = 0;
		
		for(int i=0; i < ch.length; i++)
		{
			for(int j=1; j < ch.length-1; j++) // iterate through every element in the collaboration matrix
			{
				if (collab[i][j] > 0) // if there is a collaboration,
				{	
					totalCollab++;    // increment the total collaboration count
				 	totalCollabPairs += collab[i][j]; // add that collaboration value to the total pairs amount.
				}
			}
		}
		
		totalCollabPairs /= 2; // divide total collab pairs by 2 because we double counted in the calculation.
		
		System.out.println("Total number of collaborations = " + totalCollab);
		System.out.println("Total number of collaborating pairs of characters = " + totalCollabPairs);
	}
		
		
	public static int computeSpiderNum(Characters[] ch){
		
		int accesses = 0;
		
		int spidermanIndex = 0;
		Boolean foundSpidey = false;
		
		int i=0;
		while (!foundSpidey) // set spiderman's spider number to zero.
		{
			for (int j=0; j < ch[i].comics.length; j++)
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
			for (int j=0; j < ch[i].comics.length; j++)
			{
				if (ch[i].comics[j] == true && ch[spidermanIndex].comics[j] == true && i != j)
				{
					ch[i].spiderNum = 1; // this character is in a comic with spiderman.
					numberOners.add(ch[i]);
					break;
				}
				accesses++;
			}
		}

		//System.out.println("Amount whose spiderNum is 1 = " + numberOners.size());
		
		ArrayList<Characters> numberTwoers = new ArrayList<Characters>(); // stores everyone with a spiderman number of 2
		for (i = 0; i < ch.length-1; i++)
		{
			if (ch[i].spiderNum == -1)
			{
				for (int j = 0; j < ch[i].comics.length; j++)
				{
					if (ch[i].comics[j] == true && i != j)
					{
						for (int k=0; k < numberOners.size(); k++)
						{
							if (numberOners.get(k).comics[j] == true)
							{
								ch[i].spiderNum = 2;
								numberTwoers.add(ch[i]);
								break;
							}
						}
					}
					accesses++;
				}
			}
		}

		//System.out.println("Amount whose spiderNum is 2 = " + numberTwoers.size());
		
		ArrayList<Characters> numberThreeers = new ArrayList<Characters>(); // stores everyone with a spiderman number of 3
		for (i = 0; i < ch.length-1; i++)
		{
			if (ch[i].spiderNum == -1)
			{
				for (int j = 0; j < ch[i].comics.length; j++)
				{
					if (ch[i].comics[j] == true  && i != j)
					{
						for (int k=0; k < numberTwoers.size(); k++)
						{
							if (numberTwoers.get(k).comics[j] == true)
							{
								ch[i].spiderNum = 3;
								numberThreeers.add(ch[i]);
								break;
							}
						}
					}
					accesses++;
				}
			}
		}
		
		//System.out.println("Amount whose spiderNum is 3 = " + numberThreeers.size());
		
		ArrayList<Characters> numberFourers = new ArrayList<Characters>(); // stores everyone with a spiderman number of 3
		for (i = 0; i < ch.length-1; i++)
		{
			if (ch[i].spiderNum == -1)
			{
				for (int j = 0; j < ch[i].comics.length; j++)
				{
					if (ch[i].comics[j] == true  && i != j)
					{
						for (int k=0; k < numberThreeers.size(); k++)
						{
							if (numberThreeers.get(k).comics[j] == true)
							{
								ch[i].spiderNum = 4;
								numberFourers.add(ch[i]);
								break;
							}
						}
					}
					accesses++;
				}
			}
		}
		
		return accesses;
	}
	

	public static Characters[] readData(){
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
					readIn[edgeNum].comics[Integer.parseInt(edges[j])] = true; // stores the edge value at the edgeNum index of the array.
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
