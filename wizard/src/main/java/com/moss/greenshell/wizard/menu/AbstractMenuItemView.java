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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.swtdesigner.SwingResourceManager;

class AbstractMenuItemView extends JPanel {

	JButton button;
	JTextPane titleLabel;
	JTextArea description;
	JLabel imageLabel;
	String iconResource;
	Icon icon;
	
	public AbstractMenuItemView(){
		super();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,0,0};
		setLayout(gridBagLayout);
		setBackground(Color.WHITE);

		imageLabel = new JLabel();
		imageLabel.setFont(new Font("Sans", Font.BOLD, 24));
		imageLabel.setIcon(SwingResourceManager.getIcon(AbstractMenuItem.class, "/help-browser.png"));
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.insets = new Insets(5, 5, 0, 5);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		imageLabel.setMinimumSize(new Dimension(100,100));
		imageLabel.setSize(100, 100);
		
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		imageLabel.setVerticalAlignment(JLabel.CENTER);
		
		add(imageLabel, gridBagConstraints);

		titleLabel = new JTextPane();
		titleLabel.setOpaque(false);
		titleLabel.setEditable(false);
		titleLabel.setBackground(Color.WHITE);
		titleLabel.setDisabledTextColor(Color.BLACK);
		titleLabel.setFont(new Font("Sans", Font.BOLD, 24));
		titleLabel.setText("This is the Title");
//		titleLabel.setLineWrap(false);
//		titleLabel.setEnabled(false);
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.fill = GridBagConstraints.BOTH;
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 1;
		gridBagConstraints_2.insets.top = 10;
		add(titleLabel, gridBagConstraints_2);

		button = new JButton();
//		button.setIcon(SwingResourceManager.getIcon(AbstractMenuItem.class, buttonIconResource));
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.gridheight = 2;
		gridBagConstraints_1.insets = new Insets(0, 5, 0, 5);
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 2;
		add(button, gridBagConstraints_1);

		description = new JTextArea();
		description.setOpaque(false);
		description.setEditable(false);
		description.setRows(3);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
//		description.setContentType("text/html");
		description.setText("<html><body><p align=\"right\">Squishy and bright mechanical pencil.  Great for writing top secret documents, little notes, or just about anything!</p></body></html>");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.anchor = GridBagConstraints.WEST;
		gridBagConstraints_3.weightx = 1.0;
		gridBagConstraints_3.insets = new Insets(0, 10, 5, 5);
		gridBagConstraints_3.fill = GridBagConstraints.BOTH;
		gridBagConstraints_3.gridy = 2;
		gridBagConstraints_3.gridx = 1;
		add(description, gridBagConstraints_3);
		
		setMinimumSize(titleLabel.getPreferredSize());
	}
	

	public JLabel getLabelPicture() {
		return imageLabel;
	}
	public JTextArea getTextPaneDescription() {
		return description;
	}
	public JTextPane getLabelTitle() {
		return titleLabel;
	}
	public JButton getActionButton() {
		return button;
	}
	
}
