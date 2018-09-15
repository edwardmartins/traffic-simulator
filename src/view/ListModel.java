package view;

import java.util.List;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class ListModel<T> extends DefaultListModel<T> {

	private List<T> list;

	ListModel() {
		this.list = null;
	}

	// Subclasses must call this method after one or more elements of the list change
	public void setList(List<T> list) {
		this.list = list;
		// Must call this methon when you change element in the list
		fireContentsChanged(this, 0, this.list.size()); 
	}

	@Override
	 public T getElementAt(int index) { return list.get(index); }

	@Override
	public int getSize() {
		return this.list == null ? 0 : this.list.size();
	}

	@Override
	public void addElement(T elem) {	
		this.list.add(elem);
		this.fireContentsChanged(this, this.list.size()-1,
				 this.list.size()-1); 
	}

	public void removeElement(int i){
		this.list.remove(i);
		this.fireContentsChanged(this, i,i);
	}

	
}
