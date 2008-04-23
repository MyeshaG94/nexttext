/*
  This file is part of the NextText project.
  http://www.nexttext.net/

  Copyright (c) 2004-08 Obx Labs / Jason Lewis

  NextText is free software: you can redistribute it and/or modify it under
  the terms of the GNU General Public License as published by the Free Software 
  Foundation, either version 2 of the License, or (at your option) any later 
  version.

  NextText is distributed in the hope that it will be useful, but WITHOUT ANY
  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
  A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with 
  NextText.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.nexttext.input;

import java.awt.Component;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * An interface for the keyboard.
 *
 * <p>The {@link KeyEvent} objects generated by the Java {@link KeyListener} 
 * are wrapped into {@link KeyboardEvent} objects and added to the event 
 * list.  This keyboard input source does not keep the current state of the 
 * keys, but it could be done by adding a list of key that are being pressed.
 * </p>
 */
/* $Id$ */
public class KeyboardDefault extends Keyboard implements KeyListener {

	/**
	 * Class constructor
	 *
	 * @param	component	the component the keyboard is added to
	 */
	public KeyboardDefault(Component component) {
		component.addKeyListener(this);
	}
	
	// Key listener
	public void keyPressed(KeyEvent event) {
		addEvent(new KeyboardEvent(event));
	}
	public void keyReleased(KeyEvent event) {
		addEvent(new KeyboardEvent(event));
	}
	public void keyTyped(KeyEvent event) {
		addEvent(new KeyboardEvent(event));
	}
}