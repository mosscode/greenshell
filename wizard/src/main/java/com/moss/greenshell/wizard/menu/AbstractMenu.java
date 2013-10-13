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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import com.moss.greenshell.wizard.ScreenAction;

public class AbstractMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JPanel itemsPanel;
	private JScrollPane scrollPane;
	private List<AbstractMenuItem> menuItems = new ArrayList<AbstractMenuItem>();
	
	private boolean allowMultiSelect = false;
	private boolean selectOnce = false;
	
	private ActionListener singleSelectionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(!allowMultiSelect){
				for (AbstractMenuItem item : menuItems) {
					item.getActionButton().setEnabled(false);
				}
			} else {
				if(selectOnce) {
					((JButton)e.getSource()).setEnabled(false);
				}
			}
		}
	};
	
	public AbstractMenu() {
		super();
		setOpaque(true);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		scrollPane = new JScrollPane();
		scrollPane.setOpaque(true);
		add(scrollPane, BorderLayout.CENTER);

		itemsPanel = new ScrollableJPanel();
		itemsPanel.setBackground(Color.WHITE);
		
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setViewportView(itemsPanel);
		scrollPane.getViewport().setViewPosition(new Point(0,0));
	}
	
	public void resetTriggers() {
		for (AbstractMenuItem item : menuItems) {
			item.getActionButton().setEnabled(true);
		}
	}
	
	public void setAllowMultiSelect(boolean allowMultiSelect) {
		this.allowMultiSelect = allowMultiSelect;
	}
	
	public void setSelectOnce(boolean selectOnce) {
		this.selectOnce = selectOnce;
	}
	
	private void layoutMenu(){
		Point position = scrollPane.getViewport().getViewPosition();
		
		itemsPanel.removeAll();
		itemsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridheight=1;
		c.gridwidth=1;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1;
		c.weighty=0;
		c.gridx=1;
		c.gridy=1;
		c.insets.top=5;
		c.insets.left=5;
		c.insets.right=5;
		c.anchor=GridBagConstraints.NORTH;
		for (AbstractMenuItem menuItem : menuItems) {
			itemsPanel.add(menuItem, c);
			c.gridy++;
		}
		c.weighty=1;
		JPanel spacer = new JPanel();
		spacer.setOpaque(false);
		itemsPanel.add(spacer, c);
		
		scrollPane.getViewport().setViewPosition(position);
	}
	
	public void scrollToTop(){
		scrollPane.getViewport().setViewPosition(new Point(0,0));
	}
	
	public void add(AbstractMenuItem item){
		item.getActionButton().addActionListener(singleSelectionListener);
		menuItems.add(item);
		layoutMenu();
	}
	
	public void delete(AbstractMenuItem item){
		menuItems.remove(item);
		layoutMenu();
	}
	
	public void clearAll(){
		menuItems.clear();
		layoutMenu();
	}
	
	public boolean contains(AbstractMenuItem item){
		return menuItems.contains(item);
	}
	
	@SuppressWarnings("serial")
	private static class ScrollableJPanel extends JPanel implements Scrollable {
		ScrollableJPanel(){
		}
		public Dimension getPreferredScrollableViewportSize() {
			return getPreferredSize();
		}

		public int getScrollableBlockIncrement(Rectangle visibleRect,
				int orientation, int direction) {
			return 48;
		}

		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		public boolean getScrollableTracksViewportWidth() {
			return true;
		}

		public int getScrollableUnitIncrement(Rectangle visibleRect,
				int orientation, int direction) {
			return 48;
		}
		
	}

}
