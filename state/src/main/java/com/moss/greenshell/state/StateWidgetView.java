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

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.swtdesigner.SwingResourceManager;

public class StateWidgetView extends JPanel {
	private JLabel mortonZZingelheimerLabel;
	private JLabel label;
	private JPanel panel_1;
	private boolean mouseOver;
	public StateWidgetView() {
		super();
		setLayout(new BorderLayout());
		setOpaque(false);
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		
		final JPanel panel = new JPanel();
		panel.setOpaque(false);
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.rowHeights = new int[] {7};
		gridBagLayout_1.columnWidths = new int[] {0,7};
		panel.setLayout(gridBagLayout_1);
		add(panel);

		label = DefaultComponentFactory.getInstance().createLabel("");
		label.setIcon(SwingResourceManager.getIcon(StateWidgetView.class, "/ist1_6493701-friendly-man-with-arms-crossed.jpg"));
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints_2.gridheight = 2;
		gridBagConstraints_2.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_2.weighty = 1.0;
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 0;
		panel.add(label, gridBagConstraints_2);

		mortonZZingelheimerLabel = new JLabel();
		mortonZZingelheimerLabel.setFont(new Font("Sans", Font.BOLD | Font.ITALIC, 14));
		mortonZZingelheimerLabel.setText("Morton Z. Zingelheimer");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(0, 10, 0, 10);
		gridBagConstraints_5.gridx = 1;
		gridBagConstraints_5.gridy = 0;
		panel.add(mortonZZingelheimerLabel, gridBagConstraints_5);

		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints_3.fill = GridBagConstraints.BOTH;
		gridBagConstraints_3.weightx = 1.0;
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 1;
		panel.add(panel_1, gridBagConstraints_3);
	}
	public JPanel contentPanel() {
		return panel_1;
	}
	public JLabel imageLogo() {
		return label;
	}
//	public JPanel titleHolder() {
//		return panel_2;
//	}
	public JLabel titleLabel() {
		return mortonZZingelheimerLabel;
	}
	
	private double finalOpacityLevel = .5;
	public Color fadeColor = Color.black;
	@Override
	public void paint(Graphics g) {
//		graphics.set
		super.paint(g);
		
		if(mouseOver) {
			Graphics2D graphics = (Graphics2D)g;
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)finalOpacityLevel);
			graphics.setComposite(alphaComposite);
			graphics.setColor(fadeColor);
			graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		}	
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
		repaint();
	}

}
