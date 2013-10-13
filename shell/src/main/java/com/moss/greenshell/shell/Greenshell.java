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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.moss.greenshell.dialogs.PanelDialogPresenter;
import com.moss.greenshell.state.StateListener;
import com.moss.greenshell.state.StateWidget;
import com.moss.greenshell.wizard.Screen;
import com.moss.greenshell.wizard.catastrophe.ErrorReportDecorator;

public class Greenshell extends JPanel {
	private final GreenshellView view = new GreenshellView();
	
	public Greenshell() {
		setLayout(new BorderLayout());
		add(view);
		
		view.getCancelButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				view.processPanel().cancel();
			}
		});
	}
	
	public void start(Screen initScreen){
		view.processPanel().start(initScreen);
	}
	
	public void setDialogPresenter(PanelDialogPresenter dialogPresenter) {
		view.processPanel().setDialogPresenter(dialogPresenter);
		view.stateWidgetsPanel().setDialogPresenter(dialogPresenter);
	}
	
	public void reset(){
		view.getCancelButton().resetTrigger();
		view.stateWidgetsPanel().reset();
	}
	public void init(StateListener[] listeners, ErrorReportDecorator ... errorDecorators) {
		view.processPanel().init(listeners, errorDecorators);
	}
	
	
	
	public void loadWidgets(StateWidget ... widgets){
		if(widgets!=null){
			for(StateWidget next : widgets){
				view.stateWidgetsPanel().addWidget(next);
			}
		}
		view.stateWidgetsPanel().load();
	}
	public void setFinalAction(Runnable r){
		view.processPanel().setFinalAction(r);
	}
}
