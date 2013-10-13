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
package com.moss.greenshell.wizard.effects;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.moss.effectpanels.demo.DemoPanel;

public class SlidingPanelDemo extends JPanel {
	
	private static JButton slideUpButton = new JButton("Slide Up");
	private static JButton slideDownButton = new JButton("Slide Down");
	private static JButton slideLeftButton = new JButton("Slide Left");
	private static JButton slideRightButton = new JButton("Slide Right");
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setSize(600,400);
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new BorderLayout());
		
		final SlidingPanel slidingPanel = new SlidingPanel();
		slidingPanel.setLayout(new BorderLayout());
		slidingPanel.add(new DemoPanel());
		
		slideUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slideUp(slidingPanel);
			}
		});
		
		slideDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slideDown(slidingPanel);
			}
		});
		
		slideLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slideLeft(slidingPanel);
			}
		});
		
		slideRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slideRight(slidingPanel);
			}
		});
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(slideUpButton);
		controlPanel.add(slideDownButton);
		controlPanel.add(slideLeftButton);
		controlPanel.add(slideRightButton);
		
		window.getContentPane().add(slidingPanel, BorderLayout.CENTER);
		window.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		window.setVisible(true);
	}
	
	public static void slideUp(SlidingPanel slidingPanel) {
		slidingPanel.setMode(SlidingPanel.Mode.VERTICAL_UP);
		slidingPanel.setTranslateMax(slidingPanel.getWidth());	
		slidingPanel.slide(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sliding up");
			}
		});
	}
	
	public static void slideDown(SlidingPanel slidingPanel) {
		slidingPanel.setMode(SlidingPanel.Mode.VERTICAL_DOWN);
		slidingPanel.setTranslateMax(slidingPanel.getWidth());	
		slidingPanel.slide(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sliding down");
			}
		});	
	}
	
	public static void slideRight(SlidingPanel slidingPanel) {
		slidingPanel.setMode(SlidingPanel.Mode.HORIZONTAL_RIGHT);
		slidingPanel.setTranslateMax(slidingPanel.getWidth());	
		slidingPanel.slide(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sliding right");
			}
		});	
	}
	
	public static void slideLeft(SlidingPanel slidingPanel) {
		slidingPanel.setMode(SlidingPanel.Mode.HORIZONTAL_LEFT);
		slidingPanel.setTranslateMax(slidingPanel.getWidth());	
		slidingPanel.slide(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sliding left");
			}
		});	
	}
}
