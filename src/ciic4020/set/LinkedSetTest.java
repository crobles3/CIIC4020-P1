package ciic4020.set;

public class LinkedSetTest {

	public static void main(String[] args) {
		Set<String> set1 = new SinglyLinkedSet<String>();
		set1.add("Patria");
		set1.add("Joe");
		set1.add("Joel");
		set1.add("Yoly");
		set1.add("Alondra");
		set1.add("Pedro");
		set1.add("Patria");
		set1.add("Jose");
		set1.add("Patria");
		set1.add("Joe");
		set1.add("Maria");
		set1.add("Yoly");
		set1.add("Joel");
		set1.add("Christian");
		set1.add("Dariel");
		set1.add("Christian");
		System.out.println("Set 1-------------------------------------------");
		printSet(set1);
		
		Set<String> set2 = new SinglyLinkedSet<String>();
		set2.add("Patria");
		set2.add("Joe");
		set2.add("Joel");
		set2.add("Yoly");
		set2.add("Alondra");
		set2.add("Pedro");
		set2.add("Maria");
		set2.add("Jose");
		set2.add("Patria");
		set2.add("Joe");
		set2.add("Christian");
		set2.add("Dariel");
		System.out.println("Set 2-------------------------------------------");
		printSet(set2);
		System.out.println("Union-------------------------------------------");
		printSet(set1.union(set2));
		System.out.println("Difference (set1 - set2)------------------------");
		printSet(set1.difference(set2));
		System.out.println("Difference (set2 - set1)------------------------");
		printSet(set2.difference(set1));
		System.out.println("Intersection------------------------------------");
		printSet(set1.intersection(set2));
		System.out.println("Is set1 a subset of set2: " + set1.isSubSet(set2));
		System.out.println("Is set2 a subset of set1: " + set2.isSubSet(set1));
		System.out.println("Removing Pedro and Maria from set1...");
		set1.remove("Pedro");
		set1.remove("Maria");
		System.out.println("Removing Jose and Alondra from set2...");
		set2.remove("Jose");
		set2.remove("Alondra");
		System.out.println("Set 1-------------------------------------------");
		printSet(set1);
		System.out.println("Set 2-------------------------------------------");
		printSet(set2);
		System.out.println("Is set 1 empty? " + set1.isEmpty());
		System.out.println("Is set 2 empty? " + set2.isEmpty());
		System.out.println("Removing Joel and Yoly from set2...");
		set2.remove("Joel");
		set2.remove("Yoly");
		System.out.println("Adding Karina to set2...");
		set2.add("Karina");
		System.out.println("Set 2-------------------------------------------");
		printSet(set2);
		
		System.out.println("Adding Michael and Castiel to set1...");
		set1.add("Michael");
		set1.add("Castiel");
		System.out.println("Set 1-------------------------------------------");
		printSet(set1);
		
		System.out.println("Union-------------------------------------------");
		printSet(set1.union(set2));
		System.out.println("Difference (set1 - set2)------------------------");
		printSet(set1.difference(set2));
		System.out.println("Difference (set2 - set1):-----------------------");
		printSet(set2.difference(set1));
		System.out.println("Intersection------------------------------------");
		printSet(set1.intersection(set2));
		System.out.println("Is set1 a subset of set2: " + set1.isSubSet(set2));
		System.out.println("Is set2 a subset of set1: " + set2.isSubSet(set1));
		
		System.out.println("Is set 1 empty? " + set1.isEmpty());
		System.out.println("Is set 2 empty? " + set2.isEmpty());
		System.out.println("Clearing set1...");
		set1.clear();
		System.out.println("Set 1-------------------------------------------");
		printSet(set1);
		System.out.println("Is set 1 empty? " + set1.isEmpty());
		
		System.out.println("Is Karina member of set1: " + set1.isMember("Karina"));
		System.out.println("Is Michael member of set2: " + set2.isMember("Patria"));
		System.out.println("Set 2-------------------------------------------");
		printSet(set2);
		System.out.println("Clearing set2...");
		set2.clear();
		System.out.println("Is set 2 empty? " + set2.isEmpty());
		System.out.println("Done!");
		
		
		
		
		
		 
	}
	
	public static void printSet(Set<String> ss) {
		for(String s: ss) {
			System.out.println(s);
		}
		System.out.println("Size: " + ss.size());
	}
}
