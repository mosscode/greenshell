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
package com.moss.greenshell.state;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * TODO: Move to greenshell
 */
@SuppressWarnings("serial")
public class StateWidget extends JPanel {
	private StateWidgetView view = new StateWidgetView();
	private Image image;
	private String title;
	protected StateWidgetEnvironment environment;
	private Runnable imageAction;
	
	public StateWidget() {
		setOpaque(false);
		setLayout(new BorderLayout());
		add(view);
		setImage(null);
		setTitle("Loading...");
		
		addMouseListener(new MouseListener(){
			
			public void mouseClicked(MouseEvent e) {
//				log.info("Clicked");
				if(imageAction!=null) {
					Thread t = new Thread(imageAction);
					t.start();
				}
//					SwingUtilities.invokeLater(imageAction);
			}
				
			public void mouseEntered(MouseEvent e) {
//				log.info("Mouse Over");
				view.setMouseOver(true);
			}
			public void mouseExited(MouseEvent e) {
//				log.info("Mouse Exited");
				view.setMouseOver(false);
			}
			public void mousePressed(MouseEvent e) {
			
			}
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
	}
	
	public void load(StateWidgetEnvironment environment) {
		this.environment = environment;
	}
	
	public void unload() {}
	
	public JPanel contentArea(){
		return view.contentPanel();
	}
	
	public void setImage(Image newImage){
		if(newImage!=null){
			
			int width = newImage.getWidth(this);
			int height = newImage.getHeight(this);
			System.out.println("width: "+width+" heigth: "+height);
			if(width == height) {
				
				this.image = newImage.getScaledInstance(75, 75, Image.SCALE_AREA_AVERAGING);
			
			} else if(width > height) {
				
				double ratio = ((double)height/(double)width);
				int scaledHeight = (int)(75*ratio);
				System.out.println("scaledHeight: "+scaledHeight);
				this.image = newImage.getScaledInstance(75, scaledHeight, Image.SCALE_AREA_AVERAGING);
			} else {
				
				double ratio = ((double)width/(double)height);
				int scaledWidth = (int)(75*ratio);
				System.out.println("scaledWidth: "+scaledWidth);
				this.image = newImage.getScaledInstance(scaledWidth, 75, Image.SCALE_AREA_AVERAGING);
			}
			
			view.imageLogo().setIcon(new ImageIcon(this.image));
			
		}else{
			
			this.image = null;
			view.imageLogo().setIcon(null);
		}
	}
	
	public void setImageAction(Runnable r){
		imageAction = r;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setTitle(String title){
		this.title = title;
		
//	OPTION A
//		view.titleHolder().removeAll();
//		
//		JLabel label = new JLabel(title, SwingConstants.CENTER);
//		label.setFont(new Font("Sans", Font.BOLD | Font.ITALIC, 14));
//		JComponent separator = DefaultComponentFactory.getInstance().createSeparator(label);
//		view.titleHolder().add(separator);
		
//  OPTION B
//		JComponent separator = DefaultComponentFactory.getInstance().createSeparator(title, SwingConstants.CENTER);
//		separator.setFont(new Font("Sans", Font.BOLD | Font.ITALIC, 14));
//		view.titleHolder().add(separator);
		
		// OPTION C
		view.titleLabel().setText(title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setMouseOver(boolean mouseOver) {
		view.setMouseOver(mouseOver);
	}
	
	public boolean isMouseOver() {
		return view.isMouseOver();
	}
	
}
