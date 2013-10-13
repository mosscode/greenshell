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
package com.moss.greenshell.wizard.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import com.swtdesigner.SwingResourceManager;

public class AbstractMenuScreenView extends JPanel{
	private JTextPane thisIsATextPane;
	private JButton backButton;
	private JLabel label;
	private JPanel panel;
	public AbstractMenuScreenView() {
		super();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,0,0};
		setLayout(gridBagLayout);
		setBackground(Color.WHITE);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.fill = GridBagConstraints.BOTH;
		gridBagConstraints_5.gridx = 0;
		gridBagConstraints_5.gridy = 0;
		add(panel_2, gridBagConstraints_5);

		label = new JLabel();
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.fill = GridBagConstraints.BOTH;
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 0;
		panel_2.add(label, gridBagConstraints_4);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
		label.setFont(new Font("Sans", Font.BOLD | Font.ITALIC, 24));
		label.setText("Select a Destination");

		thisIsATextPane = new JTextPane();
		thisIsATextPane.setContentType("text/html");
		thisIsATextPane.setText("<html><body><p>This is a description.  It is a lot of text.  If this were a real emergency, this text would be followed by news and information.</p></body></html>");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints_1.weightx = 1.0;
		gridBagConstraints_1.fill = GridBagConstraints.BOTH;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 0;
		panel_2.add(thisIsATextPane, gridBagConstraints_1);
		thisIsATextPane.setBackground(new Color(255, 255, 153));

		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.fill = GridBagConstraints.BOTH;
		gridBagConstraints_2.weighty = 1;
		gridBagConstraints_2.weightx = 1;
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.gridy = 1;
		add(panel, gridBagConstraints_2);

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.fill = GridBagConstraints.BOTH;
		gridBagConstraints_3.gridx = 0;
		gridBagConstraints_3.gridy = 2;
		add(panel_1, gridBagConstraints_3);

		backButton = new JButton();
		backButton.setIcon(SwingResourceManager.getIcon(AbstractMenuScreenView.class, "/go-back.png"));
		backButton.setText("Back");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		panel_1.add(backButton, gridBagConstraints);
	}
	public JPanel getPanel() {
		return panel;
	}
	public JLabel getTitleLabel() {
		return label;
	}
	public JButton backButton() {
		return backButton;
	}
	public JTextPane notesField() {
		return thisIsATextPane;
	}
	

}
