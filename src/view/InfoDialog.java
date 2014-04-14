package view;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Locale;

import javax.swing.*;

public class InfoDialog extends JDialog {
	JButton button;
	JButton wikiButton;
	JTextArea area;
	public InfoDialog(String data,JFrame owner,boolean showButton,boolean showWiki)
	{
		super (owner,"Details",false);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		area = new JTextArea();
		//area.setLocale(Locale.ROOT);
		area.setFocusable(false);
		area.setEditable(false);
		area.setOpaque(false);
		area.setText(data);
		this.add(area);
		this.setLocationRelativeTo(owner);
		this.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e){		
				dispose();		
			}
			public void keyReleased(KeyEvent e)
			{}
			public void keyTyped(KeyEvent e)
			{}
		}
				);
		if (showButton)
		{
			button = new JButton ("Nutritional Facts");
			button.setFocusable(false);
			gbc.anchor = GridBagConstraints.LAST_LINE_END;
			add(button,gbc);
		}
		
		if (showWiki)
		{
			wikiButton = new JButton ("Wikipedia");
			wikiButton.setFocusable(false);
			gbc.anchor = GridBagConstraints.EAST;
			add(wikiButton,gbc);
		}
		pack();
		this.setResizable(false);
		this.requestFocusInWindow(true);
		setVisible(true);

	}
	public void setText(String data)
	{
		area.setText(data);
	}
}
