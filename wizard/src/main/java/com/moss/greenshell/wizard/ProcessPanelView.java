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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import com.moss.greenshell.wizard.path.SmartPathBean;

public class ProcessPanelView extends JPanel{
	
	private JPanel stateContainer;
	private static final long serialVersionUID = 1L;
	
	private JPanel pathBeanContainer;
	private SmartPathBean pathBean;
	private JPanel scrollPane;
	
	public ProcessPanelView() {
		super();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0};
		setLayout(gridBagLayout);

		scrollPane = new JPanel();
		scrollPane.setLayout(new BorderLayout());
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		add(scrollPane, gridBagConstraints);

		final JPanel panel = new JPanel();
		final GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[] {0,7,0,7};
		panel.setLayout(gridBagLayout_1);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		add(panel, gridBagConstraints_1);

		pathBean = new SmartPathBean();
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3.weightx = 1.0;
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 0;
		
		pathBeanContainer = new JPanel(new BorderLayout());
		pathBeanContainer.add(pathBean, BorderLayout.CENTER);
		panel.add(pathBeanContainer, gridBagConstraints_3);
		
		pathBean.setBorderWidth(1);
		pathBean.setCompletedColor(new Color(153, 204, 102));
		pathBean.setCurrentColor(new Color(153, 255, 102));
		pathBean.setFutureColor(new Color(153, 255, 102, 75));

		stateContainer = new JPanel();
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 2;
		panel.add(stateContainer, gridBagConstraints_2);
	}
	public JPanel getActionArea() {
		return scrollPane;
	}
	public SmartPathBean getPathBean() {
		return pathBean;
	}
	protected JPanel getStateContainer() {
		return stateContainer;
	}
	public void setPathBeanVisible(boolean visible) {
		pathBeanContainer.setVisible(visible);
	}
}
