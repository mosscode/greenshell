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

import java.awt.Window;
import java.util.List;

import javax.swing.JPanel;

import com.moss.greenshell.dialogs.DialogDisplayProcess;
import com.moss.greenshell.pathbean.PathSegment;

public class FakeEnvironmentDelegate implements ScreenEnvironment{
	private ScreenEnvironment environment;
	
	/**
	 * use this constructor if you're goint to override environment()
	 */
	protected FakeEnvironmentDelegate() {
		
	}
	public FakeEnvironmentDelegate(ScreenEnvironment environment) {
		super();
		this.environment = environment;
	}
	
	public void failCatastrophically(Throwable t) {
		environment().failCatastrophically(t);
	}
	public Window frame() {
		return environment().frame();
	}
	public void hideDialog(JPanel dialog) {
		environment().hideDialog(dialog);
	}
	public void predict(List<PathSegment> segments) {
		environment().predict(segments);
	}
	public DialogDisplayProcess replaceDialog(boolean modal) {
		return environment.replaceDialog(modal);
	}
	public DialogDisplayProcess showDialog(boolean modal) {
		return environment.showDialog(modal);
	}
	public void stateGained(Object state) {
		environment().stateGained(state);
	}
	public void stateLost(Object state) {
		environment().stateLost(state);		
	}
	
	protected ScreenEnvironment environment(){
		return environment;
	}
	public void done() {
		environment().done();
	}
	public void next(Screen screen) {
		environment().next(screen);
	}
	public void previous(Screen previousScreen) {
		environment().previous(previousScreen);
	}
}
