package engine.gui.components;

import java.util.ArrayList;

import engine.graphics.texture.Texture;
import engine.gui.GUI;

/**
 * Stores all toggle buttons on the GUI in an array list
 * @author User
 *
 */
public class SelectList {
	private GUI gui;
	private ArrayList<ToggleButton> buttons;
	private int selectedId;
	
	/**
	 * Initialises the array list
	 * @param gui
	 */
	public SelectList(GUI gui) {
		this.gui = gui;
		buttons = new ArrayList<>();	
		selectedId = -1; //nothing selected
	}
	
	/**
	 * Iterates the array list to find the toggled button and save it as selected
	 * @param button
	 * @param toggled
	 */
	public void clicked(ToggleButton button, boolean toggled) {
		if(toggled) {
			for(int i = 0; i < buttons.size(); i++) {
				if(buttons.get(i).equals(button)) {
					selectedId = i;
				} else {
					buttons.get(i).deselect();
				}
			}
		} else {
			selectedId = -1;
		}
	}
	
	/**
	 * Enable all the buttons in the array list
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		for(ToggleButton button : buttons) {
			button.setEnabled(enabled);
		}
	}
	
	/**
	 * Adds a button to the array list and the GUI
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param selected
	 * @param deselected
	 */
	public void addButton(float x, float y, float width, float height, Texture selected, Texture deselected) {
		ToggleButton button = new ToggleButton(x, y, width, height, selected, deselected) {
			@Override
			public void onClick(boolean toggled) {
				clicked(this, toggled);
			}
		};
		
		buttons.add(button);
		gui.add(button);
	}
	
	/**
	 * Removes a button from the array list and the GUI
	 * @param id
	 */
	public void removeButton(int id) {
		if(id >= buttons.size() || id < 0) {
			return;
		}
		
		ToggleButton tmp = buttons.get(id);
		gui.remove(tmp);
		buttons.remove(id);
		
		if(selectedId == id) {
			selectedId = -1;
		}
	}
	
	/**
	 * Gets the ID of the button that has been selected
	 * @return
	 */
	public int getSelectedId() {
		return selectedId;
	}
}
