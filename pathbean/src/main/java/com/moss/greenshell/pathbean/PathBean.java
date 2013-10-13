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
package com.moss.greenshell.pathbean;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PathBean extends JPanel {
	public static void main(String[] args) {
		JFrame window = new JFrame("Test");
		window.getContentPane().setLayout(new BorderLayout());
		PathBean bean = new PathBean();
		bean.clearAll();
		bean.addSegment(new PathSegment("Distant Past", PathSegment.State.PAST));
		bean.addSegment(new PathSegment("Near Past", PathSegment.State.PAST));
		bean.addSegment(new PathSegment("Present", PathSegment.State.PRESENT));
		bean.addSegment(new PathSegment("Near Future", PathSegment.State.FUTURE));
		bean.addSegment(new PathSegment("Distant Future", PathSegment.State.FUTURE));
		
		window.getContentPane().add(bean, BorderLayout.NORTH);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1024, 768);
		window.setVisible(true);
	}
	private static final Color TRANSPARENT = new Color(100, 100, 100, 0);
	
	private Color completedColor = Color.GREEN;
	private Color currentColor = Color.YELLOW;
	private Color futureColor = Color.PINK;
	private Font completedFont = new Font("Sans", Font.PLAIN, 12);
	private Font currentFont = completedFont.deriveFont(Font.BOLD | Font.ITALIC);
	private Font futureFont = completedFont.deriveFont(Font.ITALIC);
	private int borderWidth=2;
	
	private List<PathSegment> segments = new LinkedList<PathSegment>();
	
	private int pWidth = 0;
	private int pHeight = 0;
	
	public PathBean() {
		addSegment(new PathSegment("Distant Past", PathSegment.State.PAST));
		addSegment(new PathSegment("Near Past", PathSegment.State.PAST));
		addSegment(new PathSegment("Present", PathSegment.State.PRESENT));
		addSegment(new PathSegment("Near Future", PathSegment.State.FUTURE));
		addSegment(new PathSegment("Distant Future", PathSegment.State.FUTURE));
		
		addComponentListener(new ComponentListener() {
			
			public void componentShown(ComponentEvent e) {
			}
			
			public void componentResized(ComponentEvent e) {
				reconfigure();
			}
			
			public void componentMoved(ComponentEvent e) {
			}
			
			public void componentHidden(ComponentEvent e) {
			}
		});
	}
	
	public void removeLastSegment(){
		int numSegments = segments.size();
		if(numSegments>0){
			remove(segments.get(numSegments-1));
		}
	}
	
	public void remove(PathSegment segment){
		segments.remove(segment);
		reconfigure();
	}
	
	public void replace(PathSegment old, PathSegment newSeg){
		segments.set(segments.indexOf(old), newSeg);
		reconfigure();
	}
	
	public int getBorderWidth() {
		return borderWidth;
	}
	
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
		reconfigure();
	}
	
	public void addSegment(PathSegment segment){
		segments.add(segment);
		reconfigure();
	}
	
	
	private void reconfigure(){
		removeAll();
		setLayout(null);
		
		int componentsWidth = 0;
		
		for (int x = segments.size()-1;x>=0;x--){
			PathSegment segment = segments.get(x);
			PathSegment lastSegment = null;
			PathSegment nextSegment = null;
			
			if(x>0)
				lastSegment = segments.get(x-1);
			
			if(x<segments.size()-1)
				nextSegment = segments.get(x+1);
			
			JLabel label = new JLabel(segment.getLabel());
			label.setOpaque(true);
			label.setFont(font(segment.getState()));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(new TopBottomBorder(borderWidth));
			ProcessStepPieceTransition transition = new ProcessStepPieceTransition(borderWidth);
			transition.setPreferredSize(new Dimension(10, 0));
			transition.setBorderColor(getForeground());
			
			label.setBackground(color(segment.getState()));
			transition.setLeftColor(color(segment.getState()));
			if(nextSegment==null){
				transition.setRightColor(TRANSPARENT);
			}else{
				transition.setBorder(new TopBottomBorder(borderWidth));
				transition.setRightColor(color(nextSegment.getState()));
			}
			
			transition.setSize(10, 0);
			add(transition);
			bumpSize(transition);
			label.setSize(label.getPreferredSize());
			add(label);
			bumpSize(label);
			
			componentsWidth += transition.getWidth() + label.getWidth();
		}
		

		System.out.println("Laying out with size " + getSize().width);
		
		int location;
		if(getSize().width>componentsWidth){
			System.out.println("roomy");
			location = componentsWidth;
		}else{
			System.out.println("tight");
			location = getSize().width;
		}
		for(Component c: getComponents()){
			c.setSize(c.getWidth(), pHeight);
			
			location = location - c.getSize().width;
			c.setLocation(location, 0);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(pWidth, pHeight);
	}
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	private void bumpSize(Component r){
		if(r.getWidth()>pWidth) pWidth = r.getWidth();
		if(r.getHeight()>pHeight) pHeight = r.getHeight();
	}

	private Font font(PathSegment.State state){
		switch(state){
		case PAST:
			return completedFont;
		case PRESENT:
			return currentFont;
		case FUTURE:
			return futureFont;
		default:
			return null;
		}
		
	}
	private Color color(PathSegment.State state){
		switch(state){
		case PAST:
			return completedColor;
		case PRESENT:
			return currentColor;
		case FUTURE:
			return futureColor;
		default:
			return null;
		}
		
	}
	
	public void clearAll(){
		segments.clear();
		reconfigure();
	}
	public void clearFuture(){
		List<PathSegment> futureSegments = new LinkedList<PathSegment>();
		for (PathSegment segment : segments) {
			if(segment.getState()==PathSegment.State.FUTURE)
				futureSegments.add(segment);
		}
		for (PathSegment segment : futureSegments) {
			segments.remove(segment);
		}
		reconfigure();
	}
	
	public final Color getCompletedColor() {
		return completedColor;
	}

	public final void setCompletedColor(Color completedColor) {
		this.completedColor = completedColor;
		reconfigure();
	}

	public final Color getCurrentColor() {
		return currentColor;
	}

	public final void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
		reconfigure();
	}

	public final Color getFutureColor() {
		return futureColor;
	}

	public final void setFutureColor(Color futureColor) {
		this.futureColor = futureColor;
		reconfigure();
	}

	public final Font getCompletedFont() {
		return completedFont;
	}

	public final void setCompletedFont(Font completedFont) {
		this.completedFont = completedFont;
		reconfigure();
	}

	public final Font getCurrentFont() {
		return currentFont;
	}

	public final void setCurrentFont(Font currentFont) {
		this.currentFont = currentFont;
		reconfigure();
	}

	public final Font getFutureFont() {
		return futureFont;
	}

	public final void setFutureFont(Font futureFont) {
		this.futureFont = futureFont;
		reconfigure();
	}
	
	
}


class TopBottomBorder implements Border {
	private int lineWidth =2;
	
	public TopBottomBorder(int lineWidth) {
		super();
		this.lineWidth = lineWidth;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(lineWidth, 0, lineWidth, 0);
	}
	
	public boolean isBorderOpaque() {
		return false;
	}
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(lineWidth));
		int halfLineWidth = lineWidth/2;
		g2.drawLine(x, y, x + width, y);
		g2.drawLine(x, y+height-lineWidth, x + width, y+height-lineWidth);
	}
}