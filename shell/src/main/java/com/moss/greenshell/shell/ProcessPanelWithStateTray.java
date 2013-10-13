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
package com.moss.greenshell.shell;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moss.greenshell.dialogs.DialogDisplayProcess;
import com.moss.greenshell.dialogs.PanelDialogPresenter;
import com.moss.greenshell.state.StateListener;
import com.moss.greenshell.state.StateHandlerEnvironment;
import com.moss.greenshell.state.StateListener;
import com.moss.greenshell.state.StateTray;
import com.moss.greenshell.state.StateWidget;
import com.moss.greenshell.state.StateWidgetEnvironment;
import com.moss.greenshell.wizard.catastrophe.ErrorReportDecorator;

@SuppressWarnings("serial")
class ProcessPanelWithStateTray extends com.moss.greenshell.wizard.ProcessPanel {
	
	private final Log log = LogFactory.getLog(this.getClass());;
	private final List<StateListener> listeners = new LinkedList<StateListener>();
	
	private StateTray stateWidgetsPanel;

	protected PanelDialogPresenter dialogPresenter;
	
	public ProcessPanelWithStateTray() {
	}
	
	public void init(StateListener[] listeners, ErrorReportDecorator ... errorDecorators) {
		setErrorReportDecorators(errorDecorators);
		this.listeners.addAll(Arrays.asList(listeners));
		
		StateHandlerEnvironment e = new StateHandlerEnvironment() {
			
			public void addMutableStateThingie(JComponent c) {
				processStateContainer().add(c);
			}
			public void addWidget(StateWidget w) {

				stateWidgetsPanel.addWidget(w);
				stateWidgetsPanel.invalidate();
				validate();
				repaint();
				stateWidgetsPanel.doLayout();
				stateWidgetsPanel.repaint();
				
				w.load(new StateWidgetEnvironment() {
					public void hideDialog(JPanel dialog) {
						dialogPresenter.hideDialog(dialog);
					}
					public DialogDisplayProcess replaceDialog(boolean modal) {
						return dialogPresenter.replaceDialog(modal);
					}

					public DialogDisplayProcess showDialog(boolean modal) {
						return dialogPresenter.showDialog(modal);
					}
				});
				
			}
			
			public void removeWidget(StateWidget w) {

				stateWidgetsPanel.removeWidget(w);
				stateWidgetsPanel.doLayout();
				stateWidgetsPanel.repaint();
			}
		};
		
		for(StateListener next : listeners){
			next.init(e);
		}
	}
	
	public void setDialogPresenter(PanelDialogPresenter dialogPresenter) {
		this.dialogPresenter = dialogPresenter;
		super.setDialogPresenter(dialogPresenter);
	}
	
	public void addStateListener(StateListener l) {
		listeners.add(l);
	}
	
	public void removeStateListener(StateListener l) {
		listeners.remove(l);
	}
	
	public StateTray getStateWidgetsPanel() {
		return stateWidgetsPanel;
	}

	public void setStateWidgetsPanel(StateTray stateWidgetsPanel) {
		this.stateWidgetsPanel = stateWidgetsPanel;
	}
	
	public void done() {
		super.done();
		
		if (log.isWarnEnabled()) {
			log.warn("Resetting state listeners");
		}
		
		for (StateListener l : listeners) {
			try {
				l.reset();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void stateGained(Object state) {
		
		if (state == null) {
			if (log.isWarnEnabled()) {
				log.warn("Null state was passed to environment.stateGained()");
			}
			return;
		}
		
		for (StateListener l : listeners) {
			try {
				l.stateGained(state);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void stateLost(Object state) {
		

		if (state == null) {
			if (log.isWarnEnabled()) {
				log.warn("Null state was passed to environment.stateLost()");
			}
			return;
		}
		
		for (StateListener l : listeners) {
			try {
				l.stateLost(state);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
