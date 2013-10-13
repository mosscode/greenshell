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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.moss.greenshell.wizard.AbstractScreen;
import com.moss.greenshell.wizard.ProcessPanel;
import com.moss.swing.test.TestFrame;


public class ProcessPanelTestRunner {
	public static void main(String[] args) {
		ProcessPanel panel = new ProcessPanel();
		
		TestScreen screen = new TestScreen("A").precedes(
				new TestScreen("A.x", false).precedes(
						new TestScreen("A.y", false).precedes(
					new TestScreen("B").precedes(
							new TestScreen("B.x", false).precedes(
									new TestScreen("C").precedes(
											new TestScreen("C.x", false).precedes(
													new TestScreen("D"))))))))
			
			
			
			
			
			;
		
		panel.start(screen);
		new TestFrame(panel);
	}
	
	static class TestScreen extends AbstractScreen<JPanel>{
		private TestScreen previous;
		private TestScreen next;
		
		private JButton nextButton = new JButton("Next");
		private JButton backButton = new JButton("Back");
		
		public TestScreen(String title, boolean notable){
			this(title);
			super.setNotablePathMilestone(notable);
		}
		
		public TestScreen(String title) {
			super(title, new JPanel());
			view.add(backButton);
			view.add(new JLabel(title));
			view.add(nextButton);
			
			backButton.setAction(new AbstractAction("Back"){
				public void actionPerformed(ActionEvent e) {
					environment.previous(previous);
				}
			});
			
			nextButton.setAction(new AbstractAction("Next"){
				public void actionPerformed(ActionEvent e) {
					setPathNote("was " + getTitle());
					environment.next(next);
				}
			});
			
		}
		
		public TestScreen follows(TestScreen preceding){
			previous = preceding;
			return this;
		}
		
		TestScreen precedes(TestScreen succeeding){
			next = succeeding;
			next.follows(this);
			return this;
		}
	}
}
