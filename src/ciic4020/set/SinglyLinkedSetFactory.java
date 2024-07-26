package ciic4020.set;

public class SinglyLinkedSetFactory<E> implements SetFactory<E> {

	@Override
	public Set<E> newInstance() {
		
		return new SinglyLinkedSet<E>();
	}

}
