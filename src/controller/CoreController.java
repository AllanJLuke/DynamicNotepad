package controller;

import java.awt.event.ActionListener;

import model.CoreModel;
import model.FoodData;
import view.CoreView;

public class CoreController {

	CoreView appView;
	CoreModel appModel;
	public CoreController (CoreModel model, CoreView view)
	{
		this.appView = view;
		this.appModel = model;
	}

	public void textMouseReleased()
	{
		String text = appView.getSelectedText();
		String wiki = "";
		boolean showWiki = false;
		if (text != null)
		{

			String def = appModel.getDefinition(text);
			if (def.startsWith("!None"))
			{
				def = appModel.getSummary(def.substring(5));
				if (def.length() < 1)
					return;
			}
			else
			{
				String [] wordList = text.split(" ");
				String val = "";
				for (int i = 0; i < wordList.length; i++)
				{
					val+=wordList[i];
					if (i+1 != wordList.length)
						val+="+";
				}
				
				wiki = appModel.getSummary(val);
				if (wiki.length() > 1)
					showWiki = true;
			}
				
			FoodData food = appModel.getFoodDataObject();
			boolean nutrition = appModel.getNutritionalInfo(text,food);
			String [] name = {text};
			Object foodObject = appModel.getFoodObject (name,food);
			if (nutrition && showWiki)
				appView.showDialog(def, Listeners.getInfoButtonListener(this,foodObject),true,true,Listeners.getInfoButtonListener(this,wiki));
			else if (nutrition)
				appView.showDialog(def, Listeners.getInfoButtonListener(this,foodObject),true,false,null);
			else if (showWiki)
				appView.showDialog(def,null, false, true,Listeners.getInfoButtonListener(this,wiki) );
			else 
				appView.showDialog(def,null,false,false,null);
		}
	}


	public void textKeyReleased()
	{
		String [] words = appView.getText().split(" ");
		if (words.length > 0)
		{
			String firstWord = words[words.length-1];
			Object data = appModel.searchDatabase(firstWord);
			if (data != null)
			{
				int caret = appView.findPos(firstWord);
				appView.insertLabel(firstWord,Listeners.getLabelListener(this,data),Listeners.getLabelPropertyChangeListener(this,caret),caret);
			}
		}
	}

	public void spawnDialog (String def, ActionListener listener,boolean showButton)
	{
		appView.showDialog(def, listener,showButton,false,null);
	}

	public void launch (String database)
	{
		appModel.loadDatabase(database);
		appView.addTextMouseListener(Listeners.getTextMouseListener(this));
		appView.addTextKeyListener(Listeners.getTextKeyListener(this));
	}

	public void removeOffset (Integer pos)
	{
		appView.removeOffset(pos);
	}
}
