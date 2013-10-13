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
package com.moss.greenshell.wizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moss.greenshell.dialogs.DialogDisplayProcess;
import com.moss.greenshell.dialogs.PanelDialogPresenter;
import com.moss.greenshell.pathbean.PathSegment;
import com.moss.greenshell.wizard.catastrophe.ErrorReportDecorator;
import com.moss.greenshell.wizard.catastrophe.PostMortemScreen;
import com.moss.greenshell.wizard.effects.SlidingPanel;
import com.moss.greenshell.wizard.effects.SlidingPanel.Mode;
import com.moss.greenshell.wizard.path.SmartPathBean;

@SuppressWarnings("serial")
public class ProcessPanel extends JPanel implements ScreenEnvironment {
	
	private Log log = LogFactory.getLog(getClass());
	private ProcessPanelView view = new ProcessPanelView();
	
	/*
	 * Stuff that someone gives us
	 */
	private Window window;
	protected PanelDialogPresenter dialogPresenter;
	private ErrorReportDecorator[] errorDecorators;
	
	/*
	 * Internal data
	 */
	private Screen currentScreen;
	private EnvironmentWrapper currentScreensEnvironment;
	private PathSegment lastSegment;
	
	
	public ProcessPanel(ErrorReportDecorator ... errorDecorators) {
		setLayout(new BorderLayout());
		add(view);
		currentScreen = new BlankScreen();
		this.errorDecorators = errorDecorators;
	}

	public ProcessPanel(Screen initScreen, ErrorReportDecorator ... errorDecorators){
		this(errorDecorators);
		start(initScreen);
	}
	
	public void setPathBeanVisible(boolean visible) {
		view.setPathBeanVisible(visible);
	}
	
	public void setViewBackground(Color color) {
		view.setBackground(color);
	}
	
	public void setErrorReportDecorators(ErrorReportDecorator[] decorators){
		this.errorDecorators = decorators;
	}
	
	protected void setTitle(String title){
		System.out.println("WARN: Nobody has overridden me, so there will be no real effect of setting title to \"" + title + "\"");
	}

	public Window frame() {
		return window;
	}
	private Runnable finalAction;
	public void setFinalAction(Runnable action){
		this.finalAction = action;
	}
	public void done() {
		if(finalAction!=null)
			finalAction.run();
	}

	public void previous(Screen previousScreen) {
		runOnEventDispatchThread(new PreviousScreenAction(previousScreen));
	}
	
	private class PreviousScreenAction implements Runnable {
		private final Screen previousScreen;
		
		private PreviousScreenAction(Screen previousScreen) {
			super();
			this.previousScreen = previousScreen;
		}
		
		public void run() {
			view.getPathBean().back(previousScreen);
			
			// CHANGE OUT THE SCREEN
			changeScreen(previousScreen, SlidingPanel.Mode.HORIZONTAL_RIGHT);			
		}
	}
	
	public void next(final Screen nextScreen) {
		runOnEventDispatchThread(new NextScreenAction(nextScreen));
	}
	
	private class NextScreenAction implements Runnable {
		private final Screen nextScreen;

		private NextScreenAction(Screen nextScreen) {
			super();
			this.nextScreen = nextScreen;
		}
		public void run() {
			// PATH WIDGET MAINTAINANCE

			view.getPathBean().next(nextScreen);


			// CHANGE OUT THE SCREEN
			changeScreen(nextScreen, SlidingPanel.Mode.HORIZONTAL_LEFT);			
		}
	}
	
	private void runOnEventDispatchThread(Runnable action){
		try {
			if(SwingUtilities.isEventDispatchThread()){
				action.run();
			}else{
				SwingUtilities.invokeLater(action);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	class ScreenChangeAction implements Runnable {
		private Screen nextScreen;
		private SlidingPanel.Mode direction;
		public ScreenChangeAction(Screen nextScreen, Mode direction) {
			super();
			this.nextScreen = nextScreen;
			this.direction = direction;
		}
		public void run() {

			final EnvironmentWrapper nextEnvironment = new EnvironmentWrapper();
			nextEnvironment.proxy(nextScreen);

			setTitle(nextScreen.getTitle());

			Runnable followupAction = new Runnable(){
				public void run() {
					currentScreensEnvironment = nextEnvironment;
					currentScreen = nextScreen;
					currentScreen.shown();
					log.info("Screen shown: \"" + currentScreen.getTitle() + "\"");
				}
			};
			view.getPathBean().setVisible(true);
			transitionAnimated(currentScreen.getView(), nextScreen.getView(), followupAction, direction);
			
				}
		
	}
	
	private synchronized void changeScreen(Screen nextScreen, SlidingPanel.Mode direction){
		runOnEventDispatchThread(new ScreenChangeAction(nextScreen, direction));
	}

	public void start(Screen firstScreen){
		view.getPathBean().reset();
		lastSegment = null;
		currentScreen = new BlankScreen();
		next(firstScreen);
	}

	public void cancel(){
		currentScreen.cancelled();
		done();
	}

	private void transitionAnimated(final JPanel oldView, final JPanel newView, final Runnable followupAction, SlidingPanel.Mode direction) {

		final Dimension dimension = view.getActionArea().getSize();
		final SlidingPanel slidingPanel = new SlidingPanel();



		int gapBetweenViews = 100;
		
		synchronized(view.getActionArea()){
			view.getActionArea().removeAll();
			view.getActionArea().add(slidingPanel);
		}
		
		slidingPanel.setSize(dimension);
		slidingPanel.setLocation(0, 0);
		slidingPanel.setLayout(null);
		slidingPanel.add(oldView);
		slidingPanel.add(newView);


		oldView.setSize(dimension);
		oldView.setLocation(0, 0);

		if(direction.equals(SlidingPanel.Mode.HORIZONTAL_LEFT)) {
			newView.setSize(dimension);
			newView.setLocation(dimension.width + gapBetweenViews, 0);
		} else if(direction.equals(SlidingPanel.Mode.HORIZONTAL_RIGHT)) {
			newView.setSize(dimension);
			newView.setLocation(0 - dimension.width - gapBetweenViews, 0);
		} else if(direction.equals(SlidingPanel.Mode.VERTICAL_UP)) {
			newView.setSize(dimension);
			newView.setLocation(0, dimension.width + gapBetweenViews);
		} else if(direction.equals(SlidingPanel.Mode.VERTICAL_DOWN)) {
			newView.setSize(dimension);
			newView.setLocation(0, 0 - dimension.width - gapBetweenViews);
		}
		
		view.getActionArea().invalidate();
		view.validate();
		view.repaint();

		slidingPanel.setMode(direction);
		slidingPanel.setTranslateMax(dimension.width + gapBetweenViews);

		slidingPanel.slide(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				transitionStatic(oldView, newView, followupAction);
			}
		});
	}

	private void transitionStatic(final JPanel oldView, final JPanel newView, final Runnable followupAction) {
		try {
			Runnable action = new Runnable(){
				public void run() {
					synchronized(view.getActionArea()){
						view.getActionArea().removeAll();
						view.getActionArea().add(newView);
						view.getActionArea().invalidate();
						view.validate();
						if(followupAction!=null)
							followupAction.run();
					}
				}
			};

			if(SwingUtilities.isEventDispatchThread()){
				action.run();
			}else{
				SwingUtilities.invokeAndWait(action);
			}
		} catch (Exception e) {
			failCatastrophically(e);
		}
	}

	public void failCatastrophically(Throwable t) {
		ScreenAction debugBack = null;
		if (Boolean.getBoolean("com.moss.greenshell.catastrophe.backButton")) {
			debugBack = new AbstractScreenAction() {
				final Screen lastScreen = currentScreen;
				public void actionPerformed(ActionEvent e) {
					environment.previous(lastScreen);
				}
			};
		}
		next(new PostMortemScreen(t, debugBack, errorDecorators));
	}

	public void predict(List<PathSegment> segments) {
		SmartPathBean path = view.getPathBean();
		
		path.predict(segments);
		
		path.invalidate();
		view.validate();
		view.repaint();
	}

	public void setDialogPresenter(PanelDialogPresenter dialogPresenter) {
		this.dialogPresenter = dialogPresenter;
	}
	public void hideDialog(JPanel dialog) {
		dialogPresenter.hideDialog(dialog);
	}
	public DialogDisplayProcess replaceDialog(boolean modal) {
		return dialogPresenter.replaceDialog(modal);
	}

	public DialogDisplayProcess showDialog(boolean modal) {
		return dialogPresenter.showDialog(modal);
	}

	class EnvironmentWrapper implements InvocationHandler {
		private ScreenEnvironment proxy;
		private Screen screen;

		public EnvironmentWrapper() {
		}


		private boolean isValid(){
			return screen == currentScreen;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if(!isValid() && (method.getName().equals("next") || method.getName().equals("done")))
				throw new RuntimeException("Something tried to call " + method.getName() + "() on a stale environment!\nThis environment was created for " + screen + ".  The current screen is " + currentScreen);
			try {
				return method.invoke(ProcessPanel.this, args);
			} catch (InvocationTargetException e) {
				if(e.getCause()!=null)
					throw e.getCause();
				else throw e;
			}
		}

		public ScreenEnvironment proxy(Screen screen){
			this.screen = screen;

			if(proxy==null)
				proxy = (ScreenEnvironment) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{ScreenEnvironment.class}, this);

			screen.setEnvironment(proxy);
			return proxy;
		}
	}

	public void stateGained(Object state) {
	}

	public void stateLost(Object state) {
		
	}
	
	protected final JPanel processStateContainer() {
		return view.getStateContainer();
	}
}


class BlankScreen extends AbstractScreen<JPanel>{
	public BlankScreen() {
		super("", new JPanel());
	}
}


