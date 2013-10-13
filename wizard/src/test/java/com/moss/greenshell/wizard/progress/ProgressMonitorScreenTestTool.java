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
package com.moss.greenshell.wizard.progress;

import javax.swing.UIManager;

import com.moss.greenshell.wizard.ProcessPanel;
import com.moss.greenshell.wizard.Screen;
import com.moss.greenshell.wizard.menu.AbstractMenuScreen;
import com.moss.greenshell.wizard.progress.ProgressMonitorScreen;
import com.moss.swing.test.TestFrame;

public class ProgressMonitorScreenTestTool  {
	static class TestScreen extends AbstractMenuScreen {
		public TestScreen() {
			super("Test");
		}
		@Override
		public boolean isNotablePathMilestone() {
			return false;
		}
		public void done(Screen screen){
			environment.next(screen);
		}
	}
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		final ProcessPanel panel = new ProcessPanel();
		Thread pain = new Thread(){
			@Override
			public void run() {
				try {
					ProgressMonitorScreen screen = new ProgressMonitorScreen("Initial");
//					TestScreen screen = new TestScreen();
					panel.next(screen);
					
					Thread.sleep(500);
					while(true){
						long start = System.currentTimeMillis();
//						Thread.sleep(200);
						ProgressMonitorScreen next = new ProgressMonitorScreen("Testing");
//						TestScreen next = new TestScreen();
						screen.done(next);
						screen = next;
						
						System.out.println("Duration: " + (System.currentTimeMillis() - start));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		new TestFrame(panel);
		pain.start();
	}
}
