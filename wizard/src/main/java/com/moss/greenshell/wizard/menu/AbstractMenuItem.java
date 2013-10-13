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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moss.swing.NormalizedImageIcon;
import com.swtdesigner.SwingResourceManager;

public class AbstractMenuItem extends AbstractMenuItemView {
	
	private static final long serialVersionUID = 1L;
	
	private final Log log = LogFactory.getLog(getClass());

	public AbstractMenuItem() {
		this("/go-next.png");
	}
	
	public AbstractMenuItem(String buttonIconResource){
		button.setIcon(SwingResourceManager.getIcon(AbstractMenuItem.class, buttonIconResource));
		titleLabel.setPreferredSize(new Dimension(20,titleLabel.getPreferredSize().height));
		description.setPreferredSize(new Dimension(20,50));
	}
	
	
	public AbstractMenuItem(String name, String description, Icon icon, String buttonImageResource){
		this(buttonImageResource);
		setItemName(name);
		setItemDescription(description);
		setIcon(icon);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				button.setEnabled(false);
				selected();
			}
		});
	}
	public AbstractMenuItem(String name, String description, Icon icon){
		this();
		setItemName(name);
		setItemDescription(description);
		setIcon(icon);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selected();
			}
		});
	}
	public AbstractMenuItem(String name, String description, String iconResource){
		this();
		setItemName(name);
		setItemDescription(description);
		setIconResource(iconResource);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selected();
			}
		});
	}
	
	public AbstractMenuItem(String name, String description, URL iconLocation) {
		this();
		setItemName(name);
		setItemDescription(description);
		setIcon(iconLocation);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				selected();
			}
		});
	}
	
	public void selected(){
		
	}
	
	private void useDefaultIcon(){
		setIcon((Icon)null);
	}

	public void setIcon(Icon icon){
		this.iconResource = null;
		if(icon!=null && icon.getIconWidth() > 0){
			if(icon instanceof ImageIcon){
				// kind of a hack to normalize the image
				// works because most of our Icons are actually ImageIcons
				icon = new NormalizedImageIcon((((ImageIcon)icon).getImage()), 100, 100);
			}else{
				log.warn("Unable to scale icon " + icon);
			}
			this.getLabelPicture().setIcon(icon);
		} else {
			this.getLabelPicture().setIcon(SwingResourceManager.getIcon(AbstractMenuItem.class, "/help-browser.png"));
		}
		
//		this.getLabelPicture().setBorder(new LineBorder(Color.BLACK));
//		qty2Label.setMinimumSize(new Dimension(100,100));
	}

	public void setIcon(URL url){
		this.iconResource = null;
		if(url!=null){
			ImageIcon icon = null;
			try {
				icon = new ImageIcon(url);
				if(icon.getImage().getHeight(this)>0) 
					setIcon(icon);
				else 
					useDefaultIcon();
			} catch(Exception ex) {
				ex.printStackTrace();
				useDefaultIcon();
			}
		} else 	
			useDefaultIcon();
	}
	
	public void setIconResource(String iconResource){
		this.iconResource = iconResource;
		if(iconResource!=null){
			try {
				
				ImageIcon icon = new ImageIcon(AbstractMenuItem.class.getResource(iconResource));
				setIcon(icon);
			} catch(Exception ex) {
				ex.printStackTrace();
				useDefaultIcon();
			}
		} else 	
			useDefaultIcon();


	}
	
	public void setItemName(String name){
		this.getLabelTitle().setText(name);
	}
	
	public void setItemDescription(String description){
		this.getTextPaneDescription().setText(description);
	}
	
	@Deprecated
	public String getIconResource() {
		return iconResource;
	}
	
}
