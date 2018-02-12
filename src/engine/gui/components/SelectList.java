package engine.gui.components;

import java.util.ArrayList;

import engine.graphics.texture.Texture;
import engine.gui.GUI;

public class SelectList
{
	private GUI gui;
	private ArrayList<ToggleButton> buttons;
	private int selectedId;
	
	public SelectList(GUI gui)
	{
		this.gui = gui;
		buttons = new ArrayList<>();	
		selectedId = -1; //nothing selected
	}
	
	public void clicked(ToggleButton button, boolean toggled)
	{
		if(toggled)
		{
			for(int i = 0; i < buttons.size(); i++)
			{
				if(buttons.get(i).equals(button))
				{
					selectedId = i;
				}else
				{
					buttons.get(i).deselect();
				}
			}
		}else
		{
			selectedId = -1;
		}
	}
	
	public void setEnabled(boolean enabled)
	{
		for(ToggleButton button : buttons)
		{
			button.setEnabled(enabled);
		}
	}
	
	public void addButton(float x, float y, float width, float height, Texture selected, Texture deselected)
	{
		ToggleButton button = new ToggleButton(x, y, width, height, selected, deselected)
		{
			@Override
			public void onClick(boolean toggled)
			{
				clicked(this, toggled);
			}
		};
		
		buttons.add(button);
		gui.add(button);
	}
	
	public void removeButton(int id)
	{
		if(id >= buttons.size() || id < 0)
		{
			return;
		}
		
		ToggleButton tmp = buttons.get(id);
		gui.remove(tmp);
		buttons.remove(id);
		
		if(selectedId == id)
		{
			selectedId = -1;
		}
	}
	
	public int getSelectedId()
	{
		return selectedId;
	}
}
