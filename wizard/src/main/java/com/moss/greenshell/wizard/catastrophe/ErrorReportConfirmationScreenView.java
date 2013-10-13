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
package com.moss.greenshell.wizard.catastrophe;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class ErrorReportConfirmationScreenView extends JPanel {
	
	private JButton exitButton = new JButton("Exit");
	
	public ErrorReportConfirmationScreenView() {
		super();
		setLayout(new GridBagLayout());

		final JTextPane somethingCatastrophicallyFailedLabel = new JTextPane();
		somethingCatastrophicallyFailedLabel.setContentType("text/html");
		somethingCatastrophicallyFailedLabel.setText("<html><body><p>An Error Report has been sent.</p></body></html>");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.weightx = 1.0;
		gridBagConstraints_1.insets = new Insets(5, 10, 5, 5);
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		add(somethingCatastrophicallyFailedLabel, gridBagConstraints_1);
		
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(5, 10, 5, 5);
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 1;
		add(exitButton, gridBagConstraints_2);
	}

	public JButton getExitButton() {
		return exitButton;
	}

	public void setExitButton(JButton exitButton) {
		this.exitButton = exitButton;
	}
}