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
package com.moss.greenshell.wizard.menu;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Action;
import javax.swing.JPanel;

import com.moss.greenshell.dialogs.DialogDisplayProcess;
import com.moss.greenshell.pathbean.PathSegment;
import com.moss.greenshell.wizard.AbstractScreen;
import com.moss.greenshell.wizard.AbstractScreenAction;
import com.moss.greenshell.wizard.ScreenEnvironment;
import com.moss.greenshell.wizard.FakeEnvironmentDelegate;
import com.moss.greenshell.wizard.Screen;
import com.moss.greenshell.wizard.ScreenAction;
import com.moss.greenshell.wizard.ScreenEnvironment;
import com.moss.greenshell.wizard.SingleFireButton;
import com.moss.greenshell.wizard.progress.ProgressMonitorScreen;

/**
 * Subclassing this is the easy way to create standard menus
 */
public class AbstractMenuScreen extends AbstractScreen<AbstractMenuScreenView> {
	private AbstractMenu menu = new AbstractMenu();
	private ScreenAction backAction;
	private boolean hasShown = false;
	
	public AbstractMenuScreen(String label) {
		super(label, new AbstractMenuScreenView());
		view.notesField().setVisible(false);
		
		view.getTitleLabel().setText(label);
		view.getPanel().add(menu, BorderLayout.CENTER);
		showBackButton(false);
		view.backButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				backAction.setEnvironment(environment);
				backAction.actionPerformed(e);
			}
		});
	}
	
	protected final void addScreenActionItemMonitored(String name, String description, String icon, final ScreenAction action){
		if(action.getValue(Action.NAME)==null){
			throw new NullPointerException("This method requires that the action have a NAME specified");
		}
		addItem(new AbstractMenuItem(name, description, icon){
			@Override
			public void selected() {
				ProgressMonitorScreen.runAndMonitor(action, environment);
			}
		});
	}
	
	protected final void addScreenActionItem(String name, String description, String icon, final ScreenAction action){
		addItem(new AbstractMenuItem(name, description, icon){
			@Override
			public void selected() {
				action.setEnvironment(environment);
				action.actionPerformed(new ActionEvent(this, 0, ""));
			}
		});
	}
	
	public void showNote(String html){
		view.notesField().setVisible(true);
		view.notesField().setText(html);
		view.invalidate();
		view.repaint();
	}
	
	protected void setBackAction(ScreenAction action){
		
		action.setEnvironment(environment);
		
		this.backAction = action;
	}
	
	protected void setDefaultBackBehavior(final Screen previousScreen) {
		
		if (previousScreen != null) {
			
			showBackButton(true);
			
			setBackAction(new AbstractScreenAction() {
				public void actionPerformed(ActionEvent e) {
					environment.previous(previousScreen);
				}
			});
		}
	}
	
	protected void showBackButton(boolean enabled){
		view.backButton().setVisible(enabled);
	}
	
	protected void addItem(AbstractMenuItem menuItem){
		menu.add(menuItem);
		if(!hasShown)
			menu.scrollToTop();
	}
	protected AbstractMenu getMenu() {
		return menu;
	}
	
	protected void removeItem(AbstractMenuItem menuItem){
		menu.remove(menuItem);
	}
	
	protected void clearItems() {
		menu.clearAll();
	}
	
	@Override
	public final void shown() {
		hasShown = true;
		setPathNote(getTitle());
		menu.resetTriggers();
	}
	
	public void scrollToTop(){
		menu.scrollToTop();
	}
}
