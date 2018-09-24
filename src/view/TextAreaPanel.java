package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public abstract class TextAreaPanel extends JPanel {

	protected JTextArea textArea;

	public TextAreaPanel(String title, boolean editable) {

		this.setLayout(new GridLayout(1, 1));
		textArea = new JTextArea(40, 30);
		textArea.setEditable(editable);

		this.add(new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		mySetBorder(title);

	}

	public void mySetBorder(String title) {
		this.setBorder(
				BorderFactory.createTitledBorder(MainView.defaultBorder, 
						title, TitledBorder.LEFT, TitledBorder.TOP));
	}

	public String getTexto() {
		return textArea.getText();
	}

	public void setTexto(String text) {
		textArea.setText(text);
	}

	public void clearTextArea() {
		textArea.setText(null);
	}

	public void insertText(String valor) {
		textArea.insert(valor, textArea.getCaretPosition());
	}

}
