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
package com.moss.greenshell.wizard.filechooser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.swtdesigner.SwingResourceManager;

public class FileChooserScreenView extends JPanel {
	private JLabel selectADestinationLabel;
	private JFileChooser fileChooser;
	private JButton okButton;
	private JPanel inspectorPanel;
	public FileChooserScreenView() {
		super();
		setLayout(new BorderLayout());

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridBagLayout());
		add(panel_1, BorderLayout.SOUTH);

		okButton = new JButton();
		okButton.setIcon(SwingResourceManager.getIcon(FileChooserScreenView.class, "/go-next.png"));
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 5, 5);
		gridBagConstraints_8.anchor = GridBagConstraints.EAST;
		gridBagConstraints_8.weightx = 1.0;
		panel_1.add(okButton, gridBagConstraints_8);
		okButton.setText("Next");

		final JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		fileChooser = new JFileChooser();
		splitPane.setLeftComponent(fileChooser);
		fileChooser.setControlButtonsAreShown(false);

		inspectorPanel = new JPanel();
		inspectorPanel.setLayout(new BorderLayout());
		inspectorPanel.setMinimumSize(new Dimension(200, 200));
		splitPane.setRightComponent(inspectorPanel);

		selectADestinationLabel = new JLabel();
		selectADestinationLabel.setOpaque(true);
		selectADestinationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectADestinationLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
		selectADestinationLabel.setText("Select a Destination");
		add(selectADestinationLabel, BorderLayout.NORTH);
	}
	public JButton okButton() {
		return okButton;
	}
	public JFileChooser fileChooser() {
		return fileChooser;
	}
	public JPanel inspectorPanel() {
		return inspectorPanel;
	}
	public JLabel titleLabel() {
		return selectADestinationLabel;
	}
}
