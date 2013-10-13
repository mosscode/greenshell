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
package com.moss.greenshell.wizard.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SlidingPanel extends JPanel implements ActionListener {
	public enum Mode{HORIZONTAL_LEFT, VERTICAL_DOWN, HORIZONTAL_RIGHT, VERTICAL_UP}
	
	private int translate =0;
	private int translateMax;
	private Timer timer = new Timer(5, this);
	private ActionListener slidingCompletionAction;
	private long start;
	private long duration = 250;
	private Mode mode = Mode.HORIZONTAL_LEFT;
	
	public SlidingPanel() {
		setBackground(Color.WHITE);
	}
	
	public void setTranslateMax(int translateMax) {
		switch(mode){
		case HORIZONTAL_LEFT:
			this.translateMax = -translateMax;
			break;
		case VERTICAL_DOWN:
			this.translateMax = translateMax;
			break;
		case HORIZONTAL_RIGHT: 
			this.translateMax = translateMax;
			break;
		case VERTICAL_UP: 
			this.translateMax = -translateMax;
		}
		System.out.println("Max: " + this.translateMax);
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		switch(mode){
		case HORIZONTAL_LEFT:
			g.translate(translate, 0);
			break;
		case VERTICAL_DOWN:
			g.translate(0, translate);
			break;
		case HORIZONTAL_RIGHT: 
			g.translate(translate, 0);
			break;
		case VERTICAL_UP: 
			g.translate(0, translate);
			break;
		}
		
//		System.out.println("paint");
		super.paint(g);
	}
	
	public void slide(ActionListener action, long duration){
		this.duration = duration;
		slide(action);
	}
	public void slide(ActionListener action){
//		System.out.println("Sliding " + mode);
		translate=0;
		start = System.currentTimeMillis();
		
		timer.start();
		this.slidingCompletionAction = action;
	}
	
	public void actionPerformed(ActionEvent e) {
		long now = System.currentTimeMillis();
		long elapsed = now - start;
		double percentComplete = (double)elapsed/(double)duration;
		translate = (int)(percentComplete * translateMax);
//		System.out.println(translate);
		repaint();
		if(elapsed >= duration){
			timer.stop();
			slidingCompletionAction.actionPerformed(new ActionEvent(this, 0, ""));
		}
	}
}
