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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.swtdesigner.SwingResourceManager;

public class PostMortemScreenView extends JPanel {
	
	private JButton buttonBack;
	private JPanel backPanel;
	private JProgressBar progress = new JProgressBar();
	private JLabel progressLabel = new JLabel("Sending error report ...");
	private JLabel progressDoneLabel = new JLabel("An error report has been sent");
	private JLabel progressFailedLabel = new JLabel("Failed to send error report");
	
	public PostMortemScreenView() {
		super();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,0,0,0};
		gridBagLayout.columnWidths = new int[] {0,7};
		setLayout(gridBagLayout);

		final JLabel sheeeoooowwwtSomethingCatastrophicallyLabel = new JLabel();
		sheeeoooowwwtSomethingCatastrophicallyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sheeeoooowwwtSomethingCatastrophicallyLabel.setFont(new Font("Sans", Font.PLAIN, 32));
		sheeeoooowwwtSomethingCatastrophicallyLabel.setText("Unexpected Error!");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		add(sheeeoooowwwtSomethingCatastrophicallyLabel, gridBagConstraints);

		final JTextPane somethingCatastrophicallyFailedLabel = new JTextPane();
		somethingCatastrophicallyFailedLabel.setContentType("text/html");
		somethingCatastrophicallyFailedLabel.setText("<html><body><p> Sorry, a situation has arisen with which this program cannot cope.</p><p>Possible causes include:</p><ul><li>Network Connectivity Errors, e.g. \"My Internet Connection Is Down\" </li><li>Network Service Errors - e.g. \"A Server Is Down\"</li><li>A deficiency in this program - \"e.g. Programmer Mistake\".</li></ul></body></html>");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.weightx = 1.0;
		gridBagConstraints_1.insets = new Insets(5, 10, 5, 5);
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 0;
		add(somethingCatastrophicallyFailedLabel, gridBagConstraints_1);
		
		JPanel progressPanel = new JPanel();
		progressPanel.setLayout(new GridBagLayout());
		progressPanel.setOpaque(false);
		final GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.insets = new Insets(5,5,5,5);
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.gridy = 2;
		gridBagConstraints7.anchor = GridBagConstraints.WEST;
		add(progressPanel, gridBagConstraints7);
		
		final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.insets = new Insets(5,5,5,5);
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 0;
		progressPanel.add(progressDoneLabel, gridBagConstraints6);
		progressDoneLabel.setVisible(false);
		
		final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.insets = new Insets(5,5,5,5);
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.gridy = 0;
		progressPanel.add(progressLabel, gridBagConstraints5);
		progressLabel.setVisible(false);
		
		final GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.insets = new Insets(5,5,5,5);
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.gridy = 0;
		progressPanel.add(progressFailedLabel, gridBagConstraints8);
		progressFailedLabel.setVisible(false);
		progressFailedLabel.setForeground(Color.RED);
		
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
//		gridBagConstraints_2.weightx = 1.0;
		gridBagConstraints_2.insets = new Insets(5, 5, 5, 5);
//		gridBagConstraints_2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2.gridx = 1;
		gridBagConstraints_2.gridy = 0;
		progressPanel.add(progress, gridBagConstraints_2);
		progress.setIndeterminate(true);
		progress.setVisible(false);
		

		final JLabel label = new JLabel();
		label.setIcon(SwingResourceManager.getIcon(PostMortemScreenView.class, "/tombstone.png"));
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 1;
		add(label, gridBagConstraints_3);

		backPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		backPanel.setLayout(flowLayout);
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.gridwidth = 2;
		gridBagConstraints_4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_4.gridy = 3;
		gridBagConstraints_4.gridx = 0;
		add(backPanel, gridBagConstraints_4);

		buttonBack = new JButton();
		buttonBack.setIcon(SwingResourceManager.getIcon(PostMortemScreenView.class, "/go-back.png"));
		buttonBack.setText("Back");
		backPanel.add(buttonBack);
	}

	public JProgressBar getProgressBar() {
		return progress;
	}
	
	public void startProgress() {
		progress.setVisible(true);
		progressLabel.setVisible(true);
		progressDoneLabel.setVisible(false);
	}
	
	public void stopProgress() {
		progress.setVisible(false);
		progressLabel.setVisible(false);
		progressDoneLabel.setVisible(true);
	}
	
	public void progressFailed() {
		progress.setVisible(false);
		progressLabel.setVisible(false);
		progressFailedLabel.setVisible(true);
	}
	
	public JLabel getProgressDoneLabel() {
		return progressDoneLabel;
	}

	public JLabel getProgressLabel() {
		return progressLabel;
	}
	public JPanel getBackPanel() {
		return backPanel;
	}
	public JButton getButtonBack() {
		return buttonBack;
	}

}
