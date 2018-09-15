package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;


@SuppressWarnings("serial")
public class SortedArrayList<E> extends ArrayList<E> {
	
	 private Comparator<E> cmp;
	 
	 public SortedArrayList(Comparator<E> cmp) { 
		 this.cmp = cmp;
	 }
	 
	 // Add
	 @Override
	 public boolean add(E e) {
		
		int i = 0; 
		while(i < this.size() && cmp.compare(this.get(i),e) <= 0) {
			 i++;
		}
		super.add(i, e);
		return true;
		 
	 }
	 
	 // AddAll
	 @Override
	 public boolean addAll(Collection<? extends E> c) {
		 for(E elem : c) {
			 this.add(elem);
		 }
		 return true;
	 }
	 
	// Unsupported operations
	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		throw new UnsupportedOperationException("Cannot insert element");
	}

	@Override
	public E set(int arg0, E arg1) {
		throw new UnsupportedOperationException("Cannot insert element");
	}

	@Override
	public void add(int arg0, E arg1) {
		throw new UnsupportedOperationException("Cannot insert element");
	}
	
}