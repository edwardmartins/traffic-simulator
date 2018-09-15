package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public abstract class TextAreaPanel extends JPanel{
	
	protected JTextArea textArea;
	
	// CREA UN PANEL PARA UN AREA DE TEXTO
	//-------------------------------------------------------------------
	// Creamos el Jtext area y lo añadimos al panel
	public TextAreaPanel(String titulo, boolean editable) {
		
		// Layout del panel matriz de 1X1
		this.setLayout(new GridLayout(1,1));
		
		// Creo el area de texto
		textArea = new JTextArea(40,30);
		// Hago el area de texto editable
		textArea.setEditable(editable); 
		
		// Añado al panel el area de texto junto con el scroll
		// El area de texto se añade en el new Scroll
		this.add(new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		// Pongo el borde con setborder
		setBorde(titulo);
		
	}
	
	public void setBorde(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(
				MainView.defaultBorder,
				titulo,
				TitledBorder.LEFT,
				TitledBorder.TOP));	
	}
	
	 public String getTexto() {
		 return textArea.getText(); 
	 }
	 
	 public void setTexto(String texto) {
		 textArea.setText(texto);
	 }
	 
	 public void limpiarAreaDeTexto() {
		 textArea.setText(null);
	 }
	 
	 // Inserta a continuacion
	 public void inserta(String valor) { 
		 textArea.insert(valor, textArea.getCaretPosition()); 
	 }
	

}
