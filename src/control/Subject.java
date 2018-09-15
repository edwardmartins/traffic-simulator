package control;

public interface Subject<T> {
	
	// Adds an observer
	public void addObserver(T o);
	
	// Deletes an observer
	public void removeObserver(T o);

}
