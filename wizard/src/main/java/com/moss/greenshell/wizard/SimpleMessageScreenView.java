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

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.swtdesigner.SwingResourceManager;

public class SimpleMessageScreenView extends JPanel {
	private JButton backButton;
	private JPanel panel_1;
	private JLabel label;
	public SimpleMessageScreenView() {
		super();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {7};
		setLayout(gridBagLayout);

		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("New JLabel");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(25, 25, 25, 25);
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.weighty = 1.0;
		gridBagConstraints_1.gridx = 0;
		gridBagConstraints_1.gridy = 0;
		add(label, gridBagConstraints_1);

		final JPanel panel = new JPanel();
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[] {7,0};
		panel.setLayout(gridBagLayout_1);
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		add(panel, gridBagConstraints);

		backButton = new JButton();
		backButton.setIcon(SwingResourceManager.getIcon(SimpleMessageScreenView.class, "/go-back.png"));
		backButton.setText("Back");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 0;
		panel.add(backButton, gridBagConstraints_3);

		panel_1 = new JPanel();
		panel_1.setLayout(new FlowLayout());
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridx = 2;
		gridBagConstraints_2.insets = new Insets(5, 0, 5, 5);
		gridBagConstraints_2.anchor = GridBagConstraints.EAST;
		gridBagConstraints_2.weightx = 1.0;
		panel.add(panel_1, gridBagConstraints_2);
	}
	public JLabel label() {
		return label;
	}
	public JPanel buttonPanel() {
		return panel_1;
	}
	public JButton backButton() {
		return backButton;
	}

}
