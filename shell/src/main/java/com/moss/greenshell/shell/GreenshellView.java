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
package com.moss.greenshell.shell;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import com.moss.greenshell.state.StateTray;
import com.moss.greenshell.wizard.SingleFireButton;
import com.swtdesigner.SwingResourceManager;

class GreenshellView extends JPanel{
	
	private ProcessPanelWithStateTray processPanel;
	private StateTray stateWidgetsPanel;
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JSeparator separator;
	private SingleFireButton cancelButton;
	
	public GreenshellView() {
		super();
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.rowHeights = new int[] {0,0,7};
		setLayout(gridBagLayout_1);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0,7,7,7};
		panel.setLayout(gridBagLayout);
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.fill = GridBagConstraints.BOTH;
		gridBagConstraints_4.gridx = 0;
		gridBagConstraints_4.gridy = 0;
		add(panel, gridBagConstraints_4);

		final JPanel panel_3 = new JPanel();
		final GridBagLayout gridBagLayout_2 = new GridBagLayout();
		gridBagLayout_2.columnWidths = new int[] {7};
		panel_3.setLayout(gridBagLayout_2);
		panel_3.setOpaque(false);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_5.fill = GridBagConstraints.BOTH;
		gridBagConstraints_5.weightx = 1.0;
		panel.add(panel_3, gridBagConstraints_5);

		final JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(Color.black, 1, true));
		panel_7.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(5, 5, 15, 0);
		gridBagConstraints_1.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints_1.weighty = 1.0;
		gridBagConstraints_1.weightx = 1.0;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		panel_3.add(panel_7, gridBagConstraints_1);

		cancelButton = new SingleFireButton();
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 0;
		panel_7.add(cancelButton, gridBagConstraints_3);
		cancelButton.setIcon(SwingResourceManager.getIcon(GreenshellView.class, "/dialog-error.png"));
		cancelButton.setText("Cancel");

		stateWidgetsPanel = new StateTray();
		stateWidgetsPanel.setOpaque(false);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridx = 2;
		gridBagConstraints_6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		panel.add(stateWidgetsPanel, gridBagConstraints_6);

		separator = new JSeparator();
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		gridBagConstraints_18.fill = GridBagConstraints.BOTH;
		gridBagConstraints_18.gridy = 1;
		gridBagConstraints_18.gridx = 0;
		add(separator, gridBagConstraints_18);

		processPanel = new ProcessPanelWithStateTray(/*lender, servicer, crm, factory, new Log4jLogErrorReportDecorator()*/);
		processPanel.setStateWidgetsPanel(stateWidgetsPanel);
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		add(processPanel, gridBagConstraints);
	}
	public SingleFireButton getCancelButton() {
		return cancelButton;
	}
	public JSeparator getSeparator() {
		return separator;
	}
	public JPanel getLoginBar() {
		return panel;
	}
	public StateTray stateWidgetsPanel() {
		return stateWidgetsPanel;
	}
	public ProcessPanelWithStateTray processPanel() {
		return processPanel;
	}
}
