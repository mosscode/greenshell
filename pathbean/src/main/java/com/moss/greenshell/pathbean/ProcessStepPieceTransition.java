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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JComponent;

public class ProcessStepPieceTransition extends JComponent {
	private Color leftColor = Color.GREEN;
	private Color rightColor = Color.RED;
	private Color borderColor = Color.BLUE;
	private int lineWidth = 3;
	
	public ProcessStepPieceTransition() {
		super();
	}
	
	public ProcessStepPieceTransition(int lineWidth) {
		super();
		this.lineWidth = lineWidth;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension size = getSize();
		
		
		Point a = new Point(0,0);
		Point b = new Point(size.width - lineWidth, divide(size.height, 2));
		Point c = new Point( 0, size.height);
		
		Polygon left = new Polygon();
		left.addPoint(a.x, a.y);
		left.addPoint(b.x, b.y);
		left.addPoint(c.x, c.y);
		left.addPoint(a.x, a.y);
		
		Polygon right = new Polygon();
		
		right.addPoint(size.width, 0);
		right.addPoint(a.x, a.y);
		right.addPoint(b.x, b.y);
		right.addPoint(c.x, c.y);
		right.addPoint(size.width, size.height);
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		g2.setColor(leftColor);
		g2.fill(left);

		g2.setColor(rightColor);
		g2.fill(right);
		
		g2.setStroke(new BasicStroke(lineWidth));
		g2.setColor(borderColor);
		drawLine(a, b, g2);
		drawLine(b, c, g2);
}
	
	private int divide(int dividend, int divisor){
		return (int)(((double)dividend)/((double)divisor));
	}
	
	private void drawLine(Point a, Point b, Graphics2D g2){
		g2.drawLine(a.x, a.y, b.x, b.y);
	}
	public final Color getLeftColor() {
		return leftColor;
	}
	public final void setLeftColor(Color leftColor) {
		this.leftColor = leftColor;
	}
	public final Color getRightColor() {
		return rightColor;
	}
	public final void setRightColor(Color rightColor) {
		this.rightColor = rightColor;
	}
	public final Color getBorderColor() {
		return borderColor;
	}
	public final void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	
	
}
