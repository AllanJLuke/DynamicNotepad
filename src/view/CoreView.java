package view;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JFrame;



public class CoreView extends JFrame {

	JTextPane text;
	Hashtable <Integer,Integer> offset = new Hashtable <Integer,Integer>();
	public CoreView()
	{
		super("Simple Lookup"); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		gbc.weightx = gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		text = new JTextPane();
		text.setPreferredSize(new Dimension (400,600));
		add(text,gbc); 
		pack();
		setResizable(false);
		setVisible(true);
	}

	public void addTextKeyListener(KeyListener listener)
	{
		text.addKeyListener(listener);
	}
	
	public void addTextMouseListener (MouseListener listener)
	{
		text.addMouseListener(listener);
	}

	public String getSelectedText ()
	{
		return text.getSelectedText();
	}

	public void showDialog (String content, ActionListener listener,boolean showNutritionButton, boolean showWikiButton, ActionListener wikiListener)
	{
		InfoDialog dialog = new InfoDialog (content,this,showNutritionButton,showWikiButton);
		if (showNutritionButton)
		{
			dialog.button.addActionListener (listener);
		}
		
		if (showWikiButton)
		{
			dialog.wikiButton.addActionListener(wikiListener);
		}
		dialog.setModal(true);
		text.setSelectionStart(0);
		text.setSelectionEnd(0);
	}
	
	public void insertLabel (String word, MouseListener listener,PropertyChangeListener labelListener, int caret)
	{
		JLabel label = new JLabel(word);
		label.setAlignmentY(0.85f);
		label.addMouseListener(listener);	
		text.insertComponent(label);
		label.addPropertyChangeListener(labelListener);
		System.out.println ("ADDED LABEL");
	}
	
	public int findPos(String word)
	{
		int caret = 0;
		String [] data = clean(text.getText()).split(" ");
		for (int i = 0;i < data.length; i++)
		{
			if (offset.get(i) != null)
			{
				caret+=offset.get(i);
			}
			if (data[i].compareTo(word) == 0)
			{   
				text.setSelectionStart(caret);
				text.setSelectionEnd(caret+word.length());
				offset.put(caret,2);
				return caret;
			}
			else
			{
				caret+=data[i].length()+1;
			}
		}
		return -1;
	}
	
	private String clean (String value)
	{
		String fixed = "";
		for (int i = 0; i < value.length(); i++)
		{
			if (Character.isLetter(value.charAt(i))||value.charAt(i) == ' ')
			{
				fixed += Character.toString((value.charAt(i)));
			}
			else
				fixed +=' ';
		}
		return fixed;
	}
	
	public void removeOffset (Integer pos)
	{
		offset.remove(pos);
	}

	public String getText() {
		return text.getText();
	}

}
