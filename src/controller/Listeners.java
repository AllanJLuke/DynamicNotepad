package controller;



import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Listeners {

	private static class LabelListener implements MouseListener{

		Object data;
		CoreController controller;
		public LabelListener (CoreController controller,Object data)
		{
			this.data = data;
			this.controller = controller;
		}

		public void mouseClicked (MouseEvent e)
		{
			controller.spawnDialog(data.toString(),null,false);
		}

		public void mouseEntered(MouseEvent e){
		}
		public void mouseExited(MouseEvent e){
			//dig.dispose();
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){

		}

	}

	private static class LabelPropertyChange implements PropertyChangeListener {

		int pos;
		CoreController controller;
		public LabelPropertyChange (CoreController controller,int pos)
		{
			this.controller = controller;
			this.pos = pos;
		}
		public void propertyChange(PropertyChangeEvent arg) {
			if (arg.getNewValue() == null)
			{
				controller.removeOffset(new Integer(pos));
			}

		}

	}

	private static class textMouseListener implements MouseListener 
	{
		CoreController controller;
		
		public textMouseListener (CoreController controller)
		{
			this.controller = controller;
		}
		
		public void mouseReleased(MouseEvent e){
			controller.textMouseReleased();
		}
		
		
		public void mouseClicked (MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){}
		
	
	};

	private static class textKeyListener implements KeyListener
	{
		CoreController controller;
		public textKeyListener (CoreController controller)
		{
			this.controller = controller;
		}
		public void keyPressed(KeyEvent e){}
		public void keyReleased(KeyEvent e){}
		public void keyTyped(KeyEvent e)
		{
			if (e.getKeyChar() == ' ')
			{
				controller.textKeyReleased();
			}
		}
	}

	/*private class DialogKeyAdapter extends KeyAdapter
	{
		CoreController controller;
		public DialogKeyAdapter (CoreController controller)
		{
			this.controller = controller;
		}
		public void keyPressed(KeyEvent e){						
			setVisible(false);
			dispose();		
		}
		public void keyReleased(KeyEvent e)
		{}
		public void keyTyped(KeyEvent e)
		{}
	}*/

	private static class InfoButtonListener implements ActionListener
	{
		CoreController controller;
		Object data;
		public InfoButtonListener (CoreController controller,Object data)
		{
			this.controller = controller;
			this.data = data;
		}
		public void actionPerformed (ActionEvent e)
		{
			System.out.println (data.toString());
			controller.spawnDialog(data.toString(),null,false);
		}
	};
	
	public static KeyListener getTextKeyListener(CoreController controller)
	{
		return new textKeyListener(controller);
	}

	public static MouseListener getTextMouseListener(CoreController controller)
	{
		return new textMouseListener(controller);
	}

//	public static KeyAdapter getInfoDialogKeyAdapter(CoreController controller)
//	{
//		return new DialogKeyAdapter(controller);
//	}
//	
	public static ActionListener getInfoButtonListener (CoreController controller, Object data)
	{
		return new InfoButtonListener (controller,data);
	}
	
	public static MouseListener getLabelListener (CoreController controller,Object data)
	{
		return new LabelListener (controller,data);
	}
	
	public static PropertyChangeListener getLabelPropertyChangeListener (CoreController controller,int pos)
	{
		return new LabelPropertyChange (controller,pos);
	}


}
