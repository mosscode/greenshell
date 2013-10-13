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
package com.moss.greenshell.wizard.progress;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.moss.greenshell.wizard.AbstractScreen;
import com.moss.greenshell.wizard.Screen;
import com.moss.greenshell.wizard.ScreenAction;
import com.moss.greenshell.wizard.ScreenEnvironment;

public class ProgressMonitorScreen extends AbstractScreen<ProgressMonitorScreenView>{
	
	public static void runAndMonitor(String actionDescription, final QuickRunnable qr, final ScreenEnvironment currentEnvironment){
		final ProgressMonitorScreen progress = new ProgressMonitorScreen(actionDescription);
		currentEnvironment.next(progress);
		
		new Thread(actionDescription){
			public void run() {
				setPriority(MIN_PRIORITY);
				try {
					progress.waitForShow();
				} catch (InterruptedException e) {
					currentEnvironment.failCatastrophically(e);
				}
				
				progress.done(qr.run(progress.environment));
			};
			
		}.start();
	}
	
	public static void runAndMonitor(final ScreenAction action, final ScreenEnvironment currentEnvironment){
		
		String actionName = (String)action.getValue(Action.NAME);
		
		if (actionName == null) {
			throw new NullPointerException("This method requires that the action have a NAME specified. Either specify a NAME, or use the other version of this method that takes an action name explicitly.");
		}
		
		final String name = "Please wait, I'm working on that " + actionName + " action";
		runAndMonitor(name, action, currentEnvironment);
	}
	public static void runAndMonitor(final String name, final ScreenAction action, final ScreenEnvironment currentEnvironment){
		
		final ProgressMonitorScreen progress = new ProgressMonitorScreen(name);
		currentEnvironment.next(progress);
		
		new Thread(name){
			public void run() {
				setPriority(MIN_PRIORITY);
				try {
					progress.waitForShow();
				} catch (InterruptedException e) {
					currentEnvironment.failCatastrophically(e);
				}
				
				action.setEnvironment(progress.environment);
				action.actionPerformed(new ActionEvent(progress, 0, ""));
			};
			
		}.start();
	}
	
	
	private boolean shown = false;
	private String currentTitle;
	
	public ProgressMonitorScreen(String label) {
		super(label, new ProgressMonitorScreenView());
		currentTitle = label;
		setNotablePathMilestone(false);
		view.getLabel().setText(currentTitle);
		view.getProgressBar().setIndeterminate(true);
	}
	
	public void done(ScreenAction action){
		try {
			waitForShow();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		action.setEnvironment(environment);
		action.actionPerformed(new ActionEvent(this, 1, ""));
	}
	
	public void failCatastrophically(Throwable t){
		try {
			waitForShow();
		}
		catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
		environment.failCatastrophically(t);
	}
	
	public void done(Screen nextScreen){
		try {
			waitForShow();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		environment.next(nextScreen);
	}
	
	private Object showMonitor = new Object();
	public void waitForShow() throws InterruptedException {
		synchronized(showMonitor){
			if (shown) {
				return;
			}
			showMonitor.wait();
		}
	}
	
	public void shown() {
		synchronized(showMonitor){
			shown = true;
			showMonitor.notifyAll();
		}
	}
	
	public ScreenEnvironment env() {
		return environment;
	}
	
	public void changeTitle(String newTitle) {
		this.currentTitle = newTitle;
		
		Runnable r = new Runnable() {
			public void run() {
				view.getLabel().setText(currentTitle);		
			}
		};
		
		SwingUtilities.invokeLater(r);
	}

	public String getTitle() {
		return currentTitle;
	}
}
