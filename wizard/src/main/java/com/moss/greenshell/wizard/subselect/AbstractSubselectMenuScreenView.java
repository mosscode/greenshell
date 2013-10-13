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
package com.moss.greenshell.wizard.subselect;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

public class AbstractSubselectMenuScreenView extends JPanel {
	private JSplitPane splitPane;
	private JPanel panel_2;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel selectADestinationLabel;
	public AbstractSubselectMenuScreenView() {
		super();
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		selectADestinationLabel = new JLabel();
		selectADestinationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectADestinationLabel.setOpaque(true);
		selectADestinationLabel.setFont(new Font("Sans", Font.BOLD | Font.ITALIC, 24));
		selectADestinationLabel.setText("Somebody Define a Title!");
		add(selectADestinationLabel, BorderLayout.NORTH);

		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane);

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		splitPane.setLeftComponent(panel);

		panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());
		splitPane.setRightComponent(panel_1);

		panel_2 = new JPanel();
		add(panel_2, BorderLayout.SOUTH);
	}
	public JLabel getTitleLabel() {
		return selectADestinationLabel;
	}
	public JPanel getAvailableItemsPanel() {
		return panel_1;
	}
	public JPanel getSelectedItemsPanel() {
		return panel;
	}
	public JPanel getSouthPanel() {
		return panel_2;
	}
	public JSplitPane splitPane() {
		return splitPane;
	}
	

}
