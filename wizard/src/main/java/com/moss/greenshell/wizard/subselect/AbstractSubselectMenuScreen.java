/**
 * Copyright (C) 2013, Moss Computing Inc.
 *
 * This file is part of greenshell.
 *
 * greenshell is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * greenshell is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with greenshell; see the file COPYING.  If not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under
 * terms of your choice, provided that you also meet, for each linked
 * independent module, the terms and conditions of the license of that
 * module.  An independent module is a module which is not derived from
 * or based on this library.  If you modify this library, you may extend
 * this exception to your version of the library, but you are not
 * obligated to do so.  If you do not wish to do so, delete this
 * exception statement from your version.
 */
package com.moss.greenshell.wizard.subselect;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.moss.greenshell.wizard.AbstractScreen;
import com.moss.greenshell.wizard.menu.AbstractMenu;
import com.moss.greenshell.wizard.menu.AbstractMenuItem;


public abstract class AbstractSubselectMenuScreen extends AbstractScreen<AbstractSubselectMenuScreenView>{
	private AbstractMenu selectedItemsMenu = new AbstractMenu();
	private AbstractMenu availableItemsMenu = new AbstractMenu();
	
	public AbstractSubselectMenuScreen(String name) {
		super(name, new AbstractSubselectMenuScreenView());
		view.getTitleLabel().setText(name);
		view.getAvailableItemsPanel().add(availableItemsMenu, BorderLayout.CENTER);
		view.getSelectedItemsPanel().add(selectedItemsMenu, BorderLayout.CENTER);
		availableItemsMenu.setAllowMultiSelect(true);
		selectedItemsMenu.setAllowMultiSelect(true);
		
	}
	
	@Override
	public void shown() {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				view.splitPane().setDividerLocation(.5d);
			}
		});
	}
	
	public boolean isSelected(AbstractMenuItem menuItem){
		return (selectedItemsMenu.contains(menuItem));
	}
	
	public void addAvailableItem(AbstractMenuItem menuItem){
		menuItem.getActionButton().setIcon(new ImageIcon(getClass().getResource("/go-up.png")));
		availableItemsMenu.add(menuItem);
	}
	
	public void removeAvailableItem(AbstractMenuItem menuItem){
		availableItemsMenu.delete(menuItem);
	}
	
	public void selectItem(AbstractMenuItem menuItem){
		menuItem.getActionButton().setIcon(new ImageIcon(getClass().getResource("/stop2.png")));
		selectedItemsMenu.add(menuItem);
		selectedItemsMenu.resetTriggers();
		availableItemsMenu.delete(menuItem);
		redraw();
	}
	
	public void deSelectItem(AbstractMenuItem menuItem){
		menuItem.getActionButton().setIcon(new ImageIcon(getClass().getResource("/go-up.png")));
		availableItemsMenu.resetTriggers();
		selectedItemsMenu.delete(menuItem);
		availableItemsMenu.add(menuItem);
		redraw();
	}
	
	public void redraw(){
		selectedItemsMenu.invalidate();
		availableItemsMenu.invalidate();
		view.validate();
		view.repaint();
	}
}
