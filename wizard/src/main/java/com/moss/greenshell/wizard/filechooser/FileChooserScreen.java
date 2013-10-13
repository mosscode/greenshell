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
package com.moss.greenshell.wizard.filechooser;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

import com.moss.greenshell.wizard.AbstractScreen;
import com.moss.greenshell.wizard.AbstractScreenAction;
import com.moss.greenshell.wizard.ScreenAction;
import com.moss.swing.test.TestFrame;

public class FileChooserScreen extends AbstractScreen<FileChooserScreenView>{
	
	public static void main(String[] args) {
		
		FileChooserScreen s = new FileChooserScreen("Pick an XML File",
			new FileSelectionHandler() {
				
				public SelectionInfo handleSelection(File selection) {
					return new SelectionInfo(
							selection.getName().endsWith(".xml"),
							new JLabel("Super cool " + selection.getAbsolutePath())
						);
				}
				
				public void fileChosen(File selection) {
					System.out.println("You selected " + selection + "!");
				}
			},
			new FileFilter() {
				
				@Override
				public String getDescription() {
					return "XML Files";
				}
				
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || (f.isFile() && f.getName().endsWith(".xml"));
				}
			}
		);
		
		new TestFrame(s.view);
	}
	
	private FileSelectionHandler handler;
	
	public static class SelectionInfo {
		public final boolean isChosable;
		public final Component inspector;
		
		public SelectionInfo(boolean isChosable, Component inspector) {
			super();
			this.isChosable = isChosable;
			this.inspector = inspector;
		}
	}
	
	public static interface FileSelectionHandler {
		SelectionInfo handleSelection(File selection);
		void fileChosen(File selection);
	}
	
	
	public FileChooserScreen(String title, final FileSelectionHandler handler, FileFilter fileFilter) {
		this(title);
		setHandler(handler);
		setFileFilter(fileFilter);
	}
	public FileChooserScreen(String title) {
		super(title, new FileChooserScreenView());
		view.titleLabel().setText(title);
		this.handler = handler;
		final ScreenAction backhereAction = new AbstractScreenAction() {
			public void actionPerformed(ActionEvent e) {
				environment.previous(FileChooserScreen.this);
			}
		};

		view.okButton().setEnabled(false);
		view.okButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.fileChosen(view.fileChooser().getSelectedFile());
			}
		});
		

		//		view.initVersionSpec().setHandler(new AbstractAppSpecFileSelectionHandler() {
		//			
		//			public void selectionHappened(File path) {
		//				try {
		//					wizModel.params.spec = path==null?null:JavaAppSpec.read(path);
		//				} catch (IOException e) {
		//					JOptionPane.showMessageDialog(view, "Error reading spec: " + e.getMessage());
		//					wizModel.params.spec = null;
		//				}
		//				view.okButton().setEnabled(path!=null);
		//			}
		//			
		//		});


		view.fileChooser().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateSelection();
			}
		});
	}
	
	public void setHandler(FileSelectionHandler handler) {
		this.handler = handler;
	}
	
	public void setFileFilter(FileFilter fileFilter){
		view.fileChooser().setFileFilter(fileFilter);
	}
	
	private void updateSelection(){
		view.inspectorPanel().removeAll();

		File path = view.fileChooser().getSelectedFile();
		boolean isChoosable = false;
		if(path==null){
			view.inspectorPanel().add(new JLabel("Nothing selected"));
		}else{

			System.out.println("Selected " + path);
			
			SelectionInfo i = handler.handleSelection(path);
			isChoosable = i.isChosable;
			
			view.inspectorPanel().add(i.inspector);
			
		}

		view.inspectorPanel().invalidate();
		view.validate();
		view.repaint();
		view.okButton().setEnabled(path!=null && isChoosable);
	}

}
