package ciic4020.set;

import java.util.Iterator;

import ciic4020.list.LinkedList;
import ciic4020.list.List;

public class SinglyLinkedSet<E> implements Set<E> {
	
	private List<E> theSet;//implemented everything with LinkedList 
						   //(LL) to recycle code.
	

	//class constructor.
	public SinglyLinkedSet() {
		//initiate the set, delegating to LL.
		theSet = new LinkedList<E>();
	}
	
	
	@Override
	public Iterator<E> iterator() {
		//calls LL iterator.
		return theSet.iterator();
	}

	
	@Override
	public boolean add(E obj) {
		//checks if obj is a member
		if(isMember(obj)) return false;
		//if obj isn't, then call LL add method.
		//it is always added in front so the complexity
		//will be reduced to O(1). In this case it doesn't
		//matter where it is inserted because sets are unordered
		//but it has no repetitions.
		theSet.add(0,obj);
		return true;
	}

	@Override
	public boolean isMember(E obj) {
		//calls LL contains method.
		return theSet.contains(obj);
	}

	@Override
	public boolean remove(E obj) {
		//calls LL remove method.
		return theSet.remove(obj);
	}

	@Override
	public boolean isEmpty() {
		//calls LL isEmpty method.
		return theSet.isEmpty();
	}

	@Override
	public int size() {
		//calls LL size method.
		return theSet.size();
	}

	@Override
	public void clear() {
		//calls LL clear method.
		theSet.clear();

	}

	@Override
	public Set<E> union(Set<E> S2) {
		Set<E> union = new SinglyLinkedSet<E>();
		
		//adds all the elements of S2
		for(E elem : S2) {
			union.add(elem);
		}
		//adds all the elements of S2
		for(E elem : this) {
			union.add(elem);
		}
		//Note: add method doesn't add repetitions.
		return union;
	}

	@Override
	public Set<E> difference(Set<E> S2) {
		Set<E> dif = new SinglyLinkedSet<E>();

		//add all the elements of this that aren't 
		//found in S2.
		for(E elem : this) {
			if(!S2.isMember(elem)) {
				dif.add(elem);
			}
		}

		return dif;
	}

	@Override
	public Set<E> intersection(Set<E> S2) {
		//all the elements that are in both, this & S2.
		//used formula: S1 ^ S2 = S1 - (S1 - S2)
		return this.difference(this.difference(S2));
	}

	@Override
	public boolean isSubSet(Set<E> S2) {
		//all elements of this have to be on S2, so this
		//is a subset of S2.
		for (E elem : this)
			if (!S2.isMember(elem))
				return false;
		return true;
	}

	

}
