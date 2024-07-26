package ciic4020.project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import ciic4020.list.ArrayList;
import ciic4020.list.LinkedList;
import ciic4020.list.List;
import ciic4020.set.SinglyLinkedSet;
import ciic4020.set.Set;

/**
 * 
 * Title: Election Class
 * 
 * Election Methodology:
 * 		There usually is some discrepancy with how the elections are performed, and how 
 *  	a person that have less than half of the votes of the nation can wins, because of
 *  	this we looked to implement a different way of election. In this type of elections
 *  	in the ballots you don't vote for just one candidate or party, but will give a 
 *  	ranking scale from 1 (favorite) to n (less favorite), assuming we have 1 to n
 *  	candidates. Before starting with the elimination rounds we have to check if there
 *  	is some candidate with more than half of #1's rank, this will make him the absolute 
 *  	winner. Otherwise, the first candidate to be eliminated will be the candidate with less
 *  	#1's rank, in the case there are more than one candidates with less #1's, the #2's
 *  	will be considered, if there is still more than one with less #2's, #3's will be
 *  	considered, and so on. In the case all the ranks have been evaluated and the is
 *  	still more than one to be eliminated, the one to be eliminated will be the one with
 *  	the higher candidate id. When the candidate is eliminated the one following him takes
 *  	it's rank position, and this procedure is repeated until there is finally a winner.
 *  
 *  
 * This class is in charge of running everything belonging to the election. It consists
 * on getting the information from an external file and dividing respectively to create
 * the ballots that will contain the information of the file which will by necessary on
 * the process of deciding who will be the winner. There are two external files, one for
 * name of the candidates in terms of their candidate id#, and other for the candidate 
 * id# in terms of the rank given to the candidate; or the other way around. This two
 * files gives us all the connections we need to find a winner. After election there will
 * be an output file containing the results of the election. Here we use some Sets and
 * List to archive information (ballots, candidate's id, candidates eliminated, etc.)
 * There are some methods, described where they are found and/or implemented, that make
 * easier the process of election.  
 * 
 * List and Sets:
 * 		In this class we use some sets and list to keep data organized and compared easily
 * 		some things with others. In the case of the list, we wanted a structure that could
 * 		store things and access them easily when needed, the size of this list is going to
 * 		be fixed (to the number of candidates) so there is no preoccupation of occupying 
 * 		extra memory. For the set, we basically needed that it could hold and store things
 * 		to make easier the counting and elimination process. Having this details in mind
 * 		Array List and Singly Linked Set where the structures selected for this purposes. 
 * 		The Singly Linked Set was chosen as challenge, this structure was made using all the
 * 		method from Linked List (LL) that could be applied to the set concept and some others 
 * 		were adapted to fulfill the specifications of a Set.
 * 
 * 		P.D. LL methods were used to recycle codes.
 * 
 * 
 * @author christian.robles3
 * Project 1 - CIIC4020 Data Structures
 * Due Date: 03/13/2020.
 */
public class Election {

	public static void main(String[] args) throws FileNotFoundException {
		//set for all valid and not empty ballots
		Set<Ballot> allBallots = new SinglyLinkedSet<Ballot>();
		//list of candidates names
		List<String> names = new ArrayList<String>(10);
		
		int emptyBal = 0;
		int inValidBal = 0;
		
		//input files' names
		File candidates = new File("res/candidates.csv");
		File ballots = new File("res/ballots2.csv");
		
		
		try(Scanner scan = new Scanner(candidates)){
			//scans the entered file
			while(scan.hasNextLine()) {
				//add the names to the list of names.
				names.add(scan.nextLine().split(",")[0]);

			}

		}
		catch(IOException e) {
			System.out.println("ERROR: No such file named '"+ candidates.getName()+"' in directory." );
			return;

		}
		
		try(Scanner scan = new Scanner(ballots)){
			Ballot b;
			//scans the entered file
			while(scan.hasNextLine()) {
				//creates a ballot
				b = new Ballot(scan.nextLine(), names.size());
				//adds the ballot to the set of all ballots.
				if(b.isValid() && !b.isEmpty()) {
				allBallots.add(b);
				}
				else if(b.isEmpty()) {
					emptyBal++;
					
				}
				else {
					inValidBal++;
				}
				
			}
			
		}
		catch(IOException e) {
			System.out.println("ERROR: No such file named '"+ ballots.getName()+"' in directory." );
			return;
			
		}
		
		//create an election
		Election thisYearElection = new Election(allBallots, names, emptyBal, inValidBal);
		
		//print the results of the results of the election.
		PrintWriter output = new PrintWriter("res/results.txt");
		thisYearElection.printResults(output); 
		output.close();
		
		

	}
	
	// private fields
	private List<Set<Ballot>> setsOfBallots; //array of sets where ballots are classified.
	private List<String> candidatesNames; //list with the name of the election's candidates.
	private int aliveCandidates; //number of candidates that are still on the game.
	private int validNotEmpty; //number of valid and not empty ballots.
	private List<String> results = new LinkedList<String>(); //list of the results.
	private int round = 1; //number of the round.
	private int winnerID = -1; //ID# of the winner (if there is one, -1 means no winner).
	private Set<Integer> eliminated = new SinglyLinkedSet<Integer>(); //set of eliminated candidates.
	
	/**
	 * Main Constructor of Election Class: Runs the elections.
	 * 
	 * @param allBallots :  all ballots for the election
	 * @param candidates :  names of all the candidates
	 * @param eBal       :  number of empty ballots
	 * @param iBal       :  number of invalid ballots 
	 */
	@SuppressWarnings("unchecked")
	public Election(Set<Ballot> allBallots, List<String> candidates, int eBal, int iBal) 
	{
		//stored the quantity of ballots.
		int numOfB = allBallots.size();
		//initialize some of the private fields.
		candidatesNames = candidates;
		aliveCandidates = candidatesNames.size();
		setsOfBallots  = new ArrayList<Set<Ballot>>(aliveCandidates);
		
		//initialize the sets of the array where the ballots are going to be classified.
		for(int i = 0; i < candidates.size() + 2; i++) 
		{
			setsOfBallots.add(new SinglyLinkedSet<Ballot>()) ;
		}
		
		//classify the ballots on the candidate ranked with 1.
		allocate(allBallots);
		
		//calculate the number valid valid and not empty ballots
		validNotEmpty = numOfB;
		
		//add the number of all ballots, number of empty ballots and number of invalid
		//ballots to the list of results.
		addToResults(null,getRound(), numOfB + eBal + iBal, 1);
		addToResults(null,getRound(), eBal, 2);
		addToResults(null,getRound(), iBal, 3);
		
		Set<Integer> candidatesIDs = new SinglyLinkedSet<Integer>();
		for(int id = 1 ; id < candidatesNames.size()+1; id++) {
			candidatesIDs.add(id);
			
		}
		//run the elections until there is a winner.
		while(!isThereAWinner()) 
		{
			elimination(candidatesIDs);
			round++;
		}
		
		//add winner to the results.
		addToResults(getCandidateName(getWinnerID()),getRound(), setsOfBallots.get(getWinnerID()-1).size(), 5);

			
		
	}
	
	//looks for the candidate to be eliminated in the current round.
	/**
	 * Looks for the candidate to be eliminated in the current round and
	 * eliminates him.
	 * 
	 * @param cand : contains the id# of all the candidates.
	 */
	private void elimination(Set<Integer> cand) 
	{
		//eliminates the candidate that has lesser #1's or lost the untie.
		int byeByeLoser = lookMin(cand, 1);
		cand.remove(byeByeLoser);
		eliminated.add(byeByeLoser);
		eliminates(byeByeLoser);
		
	}
	
	/**
	 * Looks for the candidate with the minor quantity of #rank's between does
	 * candidates that hasn't been eliminated.
	 * 
	 * @param candidates : contains the id# of all the candidates.
	 * @param rank		 : value of the rank.
	 * @return           : candidate with the lesser number of given rank.
	 */
	private int lookMin(Set<Integer> candidates ,int rank){

		//set to keep track of the candidates, with the lesser quantity 
		//of the given rank.
		Set<Integer> setOfMin = new SinglyLinkedSet<Integer>();

		//sets the first candidate as the one with the minor
		//number of rank for initiation purpose.
		int curLoser = 1;
		
		//checks that the initial candidate is not a member of the given set of
		//candidates id# or if he is already eliminated. If one of this two is
		//true the the loop continues until there is found a candidate that is a
		//member and is still eligible in the elections. 
		while(!candidates.isMember(curLoser) || isLoser(curLoser)) curLoser++;
		
		//sets the quantity of given rank of the founded candidate as the minimum.
		int min = counter(curLoser, rank);
		setOfMin.add(curLoser); //add the founded candidate to the set of candidates
								//with lesser number of given rank.
		int num;

		//iterates through all the candidates id# of the given set.
		for(int c: candidates) {
			//skip if candidate 'c' is the same as 'curLoser' or of
			//c is already a loser.
			if(c == curLoser || isLoser(c)) {
				continue;
			}
			//counts the quantity of rank that candidate 'c' has.
			num = counter(c, rank);

			//if count of 'c' is lesser than 'curLoser', set count of 'c'
			//to be the minimum, clear the set because there is a new
			//minimum and update 'curLoser' to be 'c'.
			if(num<min) {
				min = num;
				setOfMin.clear();
				setOfMin.add(c);
				curLoser = c;

			}
			//if count of 'c' is equal to 'curLoser', ad 'c' to
			//the list of candidates with minimum quantity of rank.
			else if (num==min) {
				setOfMin.add(c);
			}

		}

		//out of recursion condition 1:
		//if all the ranks have been evaluated and there still is
		//not a loser, look for the candidate with the higher id#,
		//which is going to be the loser.
		if(rank > aliveCandidates) {
			
			int maxID = 0;
			for(int c : candidates) {
				if(maxID <= 0) maxID = c;
				else {
					if(c>maxID) maxID = c;
				}
			}
			return maxID;
		}
		
		//out of recursion condition 2:
		//if there is only one candidate id# in the set of 
		//minimum id#, return it.
		if(setOfMin.size()<=1) 
		{
			return curLoser;
		}

		//recursive step:
		//call again the method but updating the set of all candidates id#
		//to be the set with the minimum candidates id#, and increment
		//the rank value.
		return lookMin(setOfMin, ++rank);

	}
	
	
	/**
	 * Counts how many of the given rank has the candidate with given
	 * candidate id#.
	 * 
	 * @param candidateID : candidate's id#.
	 * @param Rank        : value of the rank.
	 * @return			  : quantity of given rank number 
	 * 						for the given candidate's id.
	 */
	private int counter(int candidateID, int Rank) {
		
		int count = 0;
		
		if (Rank ==1) {
			count = setsOfBallots.get(candidateID - 1).size();
		}
		else {

			for(Set<Ballot> s: setsOfBallots ) {

				for(Ballot b : s) {

					if(b.isValid() && !b.isEmpty() && b.getCandidateByRank(Rank)==candidateID) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	
	/**
	 * Eliminates the candidate, that has the provided candidates id#, from the ballot
	 * that ranked him with 1.
	 * 
	 * @param candidateID : candidate's id#.
	 */
	private void eliminates(int candidateID) {
		//get the set where the ballots, that has the given candidate 
		//ranked as 1, are stored.
		Set<Ballot> loser = setsOfBallots.get(candidateID -1);
		
		//for each ballot in the set eliminate the candidate.
		for(Ballot b : loser) 
		{
			//in this case because of how the elements were added to the 
			//ballot, and candidate with candidateID is suppose to be in
			//the first position of the ballot, it will be found and erased
			//immediately. This will reduce time complexity to O(1).
			b.eliminate(candidateID);
		}
		
		//add the eliminated candidates to the results list.
		addToResults(getCandidateName(candidateID),getRound(), loser.size(), 4);
		
		//reallocate the ballots where the eliminated candidates 
		//was ranked as 1.
		allocate(loser);
		 
										
		aliveCandidates--;//decrease the # of candidates that are still on the elections.
		
	}
	
	
	
	
	/**
	 * Classifies the ballots using the candidates ranked with 1 on each ballot.
	 * Each ballot will go to the position corresponding to the candidate's id#
	 * of the candidate ranked as 1.
	 * 
	 * @param s : set of ballot to be organized.
	 */
	private void allocate(Set<Ballot> s)
	{
		//iterates through all the ballots in the given set.
		for(Ballot b : s) 
		{
			//if the ballot is empty, it locates the ballot
			//in the set located at before-last position in
			//the array.
			if(b.isEmpty()) {
				setsOfBallots.get(setsOfBallots.size() - 2).add(b);
			}
			//if the ballot is invalid, it locates the ballot
			//in the set located at last position in the array.
			else if(!b.isValid())
			{
				setsOfBallots.get(setsOfBallots.size() - 1).add(b);;
			}
			//if the ballot is valid and not empty...
			else {
				//erase the candidate ranked with 1 on the current ballot
				//if it is already out of the elections. 
				//stops when the candidate ranked as 1 is still on
				//the elections.
				while(isLoser(b.getCandidateByRank(1)) && getRound()>1){
					{
						b.eliminate(b.getCandidateByRank(1));
					}
				}
				//adds the current ballot to the set in which are found all the
				//sets that has the same candidate ranked as one as the 
				// current ballot.
				setsOfBallots.get(b.getCandidateByRank(1)-1).add(b); 
			}
		}
		//added this to clean space, once allocated the entry list
		//isn't used anymore
		s.clear();
		s = null;
	}
	
	/**
	 * Checks if the candidate with the given candidates's id# 
	 * is out of the elections (already eliminated).
	 * 
	 * @param candidateID : candidate's id#.
	 * @return			  : true if loser, false otherwise.
	 */
	private boolean isLoser(int candidateID) 
	{
		if(candidateID < 1 && candidateID > candidatesNames.size()+1) {
			return true;
		}
		
		return eliminated.isMember(candidateID);
		//checks if loser without set of losers:
		//return setsOfBallots.get(candidateID - 1).isEmpty();
	}
	
	
	/**
	 * Checks if there is a winner (have more than half of #1's).
	 * 
	 * @return : true if there is a winner, false otherwise.
	 */
	private boolean isThereAWinner() {
		
		for(int pos = 0; pos < setsOfBallots.size() - 2; pos++)
		{
			if(setsOfBallots.get(pos).size() > (validNotEmpty/2) && !isLoser(pos + 1)) 
			{
				winnerID = pos + 1;
				return true;
			}	
		}
		
		return false;
		
	}
	
	
	/**
	 * Adds a string, depending on the given code and parameters, to the results list.
	 * Some parameters could mean something different depending on the case.
	 * 
	 * @param name   : candidate's name.
	 * @param round  : current round's number.
	 * @param number : quantity of ballots or #1's
	 * @param code   : codification to different cases.
	 */
	private void addToResults(String name,int round, int number, int code) {
		
		switch (code) {
		
		case 1:
			//number of ballots in the election
			results.add("Number of ballots: " + number);
			break;
		case 2:
			//number of empty ballots in the election
			results.add("Number of blank ballots: " + number);
			break;
		case 3:
			//number of invalid ballots in the election
			results.add("Number of invalid ballots: " + number);
			break;
		case 4:
			//eliminated candidate
			results.add("Round " + round + ": " + name+ " was eliminated with " + number+ " #1's");
			break;
		case 5:
			//winner
			results.add("Winner: " + name + " wins with " + number + " #1's");
			break;
		default:
			//message if wrong code was entered
			results.add("Wrong code for addToResults.");
			break;
		}
		
	}
	
	
	/**
	 * Prints all the strings from the results list to the results output file.
	 * 
	 * @param p : printer.
	 */
	public void printResults(PrintWriter p) {
		
		for(String s : results) {
			p.println(s);
		}
	}
	
	//GETTERS------------------------------------------------
	
	
	/**
	 * Returns the respective ballot using the given ballot's number.
	 * 
	 * @param ballotNum : ballot's number
	 * @return          : ballot
	 */
	public Ballot getBallot(int ballotNum) {
		for(Set<Ballot> set : setsOfBallots) {
			for(Ballot b : set) {
				
				if(b.getBallotNum() == ballotNum) {
					return b;
				}
			}
			
		}
		return null;
	}
	
	
	/**
	 * Returns the number of the current round.
	 * 
	 * @return : round's number.
	 */
	public int getRound() {
		//return the number of the round
		return round;
	}
	
	
	/**
	 * Gets the winner ID# if there is one, if there isn't id will be -1.
	 * 
	 * @return : winner candidate's id#.
	 */
	public int getWinnerID() {
		
		return winnerID;
	}
	
	
	/**
	 * Looks for the respective candidate name of the given candidate's id#.
	 * 
	 * @param candidateID : candidate's id#.
	 * @return			  : candidate's name.
	 */
	public String getCandidateName(int candidateID) {
		if(candidateID < 1 || candidateID > candidatesNames.size()) {
			return "ERROR: Candidate ID# doesn't exists.";
		}
		return candidatesNames.get(candidateID-1);
	}
	

}
