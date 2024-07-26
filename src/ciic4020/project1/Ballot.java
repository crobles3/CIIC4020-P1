package ciic4020.project1;

import ciic4020.list.LinkedList;
import ciic4020.list.List;

/**
 * 
 * Title: Ballot Class
 * 
 * This class creates the ballots that are going to be use in the Election Class. It uses
 * a ballot number, an id per candidate and a respective rank to each candidate given by  
 * the voters. There can be valid, empty (no candidates ranked) and invalid (repeated rank,
 * repeated candidate id, rank greater than quantity of candidates, in-consecutive 
 * ranks, etc).
 * 
 * Reasoning:
 * 		There was an insecurity between LinkedList or ArrayList, an ArrayList wins by time
 * 		complexity in most of the methods, but it takes more space in memory. Basically the
 * 		opposite happens with LinkedList. It was a very tough decision; finally I decided
 * 		for LinkedList because I put more weight on the space taking, plus LinkedList is a
 * 		very sophisticated ADT, and for me is more interesting than ArrayList. It was really
 * 		cool learning and understanding it, and how node works, etc. This is why I decided
 * 		for LinkedList as my main structure on this program. It's also important to add that
 * 		in the way the program works, meaning this that it uses the add method just at the 
 * 		beginning and the remove method is usually used to eliminate the element rank as 1 
 * 		in the ballot, in context, it was managed to reduce this methods' time complexity to 
 *  	O(1) mostly.
 *  
 * @author christian.robles3
 * Project 1 - CIIC4020 Data Structures
 * Due Date: 03/13/2020.
 * 
 */
public class Ballot{
	
	//private fields
	private int ballotNum; //store the number of the ballot.
	private List<Integer> ballot = new LinkedList<Integer>(); //ballot structured as an array list.
	private boolean validOrNot = true; //variable to know if the ballot is valid or it isn't.
	
	//class constructor
	/**
	 * Ballot Class Main Constructor: This constructor is designated to create ballots.
	 * 
	 * @param lineOfInfo           : string of candidates and ranks.
	 * @param quantityOfCandidates : number of candidates in the election.
	 */
	public Ballot(String lineOfInfo, int quantityOfCandidates) {
		//format: "ballot#, c#:1, c#:2, c#:3, c#:4, c#:5, ..., c#:n"
		//c#'s are the candidates's ID.
		//1-n are the ranks given to each candidate respectively.
		
		//split the string, wherever is comma, into an array of strings.
		String[] splitted = lineOfInfo.split(",");
		//get and store the ballot#.
		ballotNum = Integer.parseInt(splitted[0]);
		
		//iterate trough all the remaining elements of the array, in reverse.
		//reason: it is done in reverse because in LinkedList the add method
		//        is O(n), if we add the elements in reverse and on the first
		//		  position of the LinkedList it becomes O(1).
		for( int i = splitted.length - 1; i > 0; i--) {
			String[] split = splitted[i].split(":");
			int rank = Integer.parseInt(split[1]);
			int candidateID = Integer.parseInt(split[0]);
			
			//check if ballot is invalid:
			if ((rank != i) || (ballot.contains(candidateID) || candidateID > quantityOfCandidates)) validOrNot = false;
			
			//add candidate at the start of the linked list.
			ballot.add(0,candidateID);
			
		}
		
		
	}
	
	/**
	 * Looks for the ballot number.
	 * 
	 * @return : ballot's id number.
	 *
	 */
	public int getBallotNum() {
		// Worst Case : AL O(1) - LL O(1)
		return ballotNum;
	}
	
	/**
	 * Gets the rank of the candidate with the given candidate's id#.
	 * 
	 * @param candidateId : candidate id#.
	 * @return			  : candidate's rank.
	 */
	public int getRankByCandidate(int candidateId) {
		// Worst Case: AL O(n) - LL O(n)
		return ballot.firstIndex(candidateId)+1;
	}
	
	/**
	 * Gets the candidate's id of the candidate ranked as 'rank'.
	 * @param rank : rank's value.
	 * @return     : candidate's id#.
	 */
	public int getCandidateByRank(int rank) {
		// Worst Case:  AL O(1) - LL O(n)
		if( 0 < rank && rank < ballot.size()+1) {
			return ballot.get(rank - 1);
		}
		else return -1;
	}
	
	/**
	 * Gets the number of candidates on the ballot.
	 * 
	 * @return : quantity of candidates.
	 */
	public int getNumOfCandidates() {
		// Worst Case:  AL O(1) - LL O(1)
		return ballot.size(); 
	}
	 
	/**
	 * Eliminates the candidate using it's candidate id.
	 *  
	 * @param candidateId : candidate's id#.
	 * @return            : true if removed, false otherwise
	 */
    public boolean eliminate(int candidateId){
    	// Worst Case:  AL O(n) - LL O(n)
    	return ballot.remove(candidateId);
    }
    
    /**
	 * Eliminates the candidate ranked as 'rank'.
	 *  
	 * @param candidateId : rank's value.
	 * @return            : true if removed, false otherwise
	 */
    public boolean eliminateByRank(int rank){
    	// Worst Case:  AL O(1) - LL O(n)
    	return ballot.removeIndex(rank-1);
    }
    
    /**
     * Checks if the ballot is valid or not.
     * 
     * @return : true if valid, false otherwise.
     */
    public boolean isValid() {
    	// Worst Case:  AL O(1) - LL O(1)
    	return validOrNot;
    }
    
    /**
     * Verifies if the ballot is empty.
     * 
     * @return : true if empty, false otherwise.
     */
    public boolean isEmpty() {
    	// Worst Case:  AL O(1) - LL O(1)
    	return ballot.isEmpty();
    }

}
