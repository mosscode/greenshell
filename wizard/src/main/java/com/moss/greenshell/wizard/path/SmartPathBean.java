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
package com.moss.greenshell.wizard.path;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import com.moss.greenshell.pathbean.PathBean;
import com.moss.greenshell.pathbean.PathSegment;
import com.moss.greenshell.wizard.Screen;

public class SmartPathBean extends JPanel {
	private PathBean bean = new PathBean();
	private PathHistory history = new PathHistory();
	private Screen current;
	public SmartPathBean() {
		super(new BorderLayout());
		add(bean);
	}
	
	public void next(Screen s){
		if(history.last()!=null){
			history.last().pathNote = current.pathNote();
		}
		
		
		for(PathRecord r = history.last(); r!=null && !r.wasNotable; r = history.last()){
			history.remove(r);
			bean.remove(r.pathSegment);
		}
		
		PathRecord last = history.last();
		if(last!=null){
			PathSegment newSegment = new PathSegment(last.pathNote, PathSegment.State.PAST);
			bean.replace(last.pathSegment, newSegment);
			last.pathSegment = newSegment;
		}
		
		PathSegment segment = new PathSegment(s.getTitle(), PathSegment.State.PRESENT);
		history.record(s,segment);
		bean.addSegment(segment);
		
		current = s;
	}
	
	public void back(Screen s){
		// REMOVE ALL THE FILLING
		for(PathRecord r = history.last(); !r.wasNotable; r = history.last()){
			history.remove(r);
			bean.remove(r.pathSegment);
		}
		
		// REMOVE THE LAST NON-FILLING ONE
		PathRecord last = history.last();
		if(last!=null){
			history.remove(last);
			bean.remove(last.pathSegment);
		}
		
		// REMOVE THE HISTORICAL RECORD FOR THIS ONE AND RECREATE IT
		last = history.last();
		if(last!=null){
			history.remove(last);
			bean.remove(last.pathSegment);
			PathSegment segment = new PathSegment(s.getTitle(), PathSegment.State.PRESENT);
			history.record(s,segment);
			bean.addSegment(segment);
		}
		
		current = s;
	}
	
	public void predict(List<PathSegment> segments){

		bean.clearFuture();
		for (PathSegment segment : segments) {
			bean.addSegment(segment);
		}
	}
	
	public void reset(){
		history = new PathHistory();
		bean.clearAll();
	}

	public final void setCurrentColor(Color currentColor) {
		bean.setCurrentColor(currentColor);
	}

	public final void setFutureColor(Color futureColor) {
		bean.setFutureColor(futureColor);
	}

	public final void setCompletedColor(Color completedColor) {
		bean.setCompletedColor(completedColor);
	}

	public void setBorderWidth(int borderWidth) {
		bean.setBorderWidth(borderWidth);
	}
	
}
