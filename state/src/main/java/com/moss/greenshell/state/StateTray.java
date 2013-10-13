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
package com.moss.greenshell.state;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moss.greenshell.dialogs.DialogDisplayProcess;
import com.moss.greenshell.dialogs.PanelDialogPresenter;

/**
 * TODO: Move to greenshell
 */
@SuppressWarnings("serial")
public class StateTray extends JPanel {
	private Log log = LogFactory.getLog(getClass());
	private GridBagConstraints c;
	private List<StateWidget> widgets = new LinkedList<StateWidget>();
	private PanelDialogPresenter presenter;
	
	public StateTray() {
		reset();
	}
	
	public void setDialogPresenter(PanelDialogPresenter p){
		this.presenter = p;
	}
	
	public void reset(){
		widgets.clear();
		removeAll();
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.insets.left=15;
		c.insets.top=5;
		c.insets.bottom=5;
		c.fill = GridBagConstraints.BOTH;
		repaint();
	}
	
	public void addWidget(StateWidget widget){
		c.gridx++;
		add(widget, c);
		widgets.add(widget);
	}
	
	public void removeWidget(StateWidget widget) {
		c.gridx--;
		remove(widget);
		widgets.remove(widget);
		invalidate();
		if(getParent()!=null){
			getParent().validate();
		}
			
	}
	
	public void load(){
		final long loadCall = System.currentTimeMillis();
		new Thread(){
			@Override
			public void run() {
				setPriority(MIN_PRIORITY);
				double elapsedCallDelay = System.currentTimeMillis() - loadCall;
				log.info("Loading State Widgets (took " + (elapsedCallDelay/1000) + " seconds)");
				for (StateWidget widget : widgets) {
					log.info("Loading a " + widget.getClass().getSimpleName());
					long start = System.currentTimeMillis();
					widget.load(new WidgetEnvImpl());
					double elapsed = System.currentTimeMillis()-start;
					log.info("Done Loading widget, loading took " + (elapsed/1000) + " seconds");
				}
				log.info("Done Loading State Widgets");
			}
		}.start();
	}
	
	public void unload() {
		for (StateWidget widget : widgets) {
			widget.unload();
		}
	}
	
	class DialogDisplayProcessWrapper implements DialogDisplayProcess{
		DialogDisplayProcess process;
		
		private DialogDisplayProcessWrapper(DialogDisplayProcess process) {
			super();
			this.process = process;
		}

		public void dialogComplete(final JPanel panel) {
			process.dialogComplete(panel);
		}
		
	}
	class WidgetEnvImpl implements StateWidgetEnvironment {
		public void hideDialog(JPanel dialog) {
			presenter.hideDialog(dialog);
		}
		public DialogDisplayProcess showDialog(boolean modal) {
			return new DialogDisplayProcessWrapper(presenter.showDialog(modal));
		}
		public DialogDisplayProcess replaceDialog(boolean modal) {
			return new DialogDisplayProcessWrapper(presenter.replaceDialog(modal));
		}
	}
}
