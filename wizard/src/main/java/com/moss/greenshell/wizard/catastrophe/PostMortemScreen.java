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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import com.moss.error_reporting.api.ErrorReport;
import com.moss.error_reporting.api.ErrorReportChunk;
import com.moss.error_reporting.api.ReportId;
import com.moss.greenshell.wizard.AbstractScreen;
import com.moss.greenshell.wizard.ProcessPanel;
import com.moss.greenshell.wizard.Reporter;
import com.moss.greenshell.wizard.ScreenAction;
import com.moss.greenshell.wizard.progress.ProgressMonitorScreen;
import com.moss.internalerror.InternalErrorException;

public class PostMortemScreen extends AbstractScreen<PostMortemScreenView>{
	private Log log = LogFactory.getLog(getClass());
	
	public PostMortemScreen(final Throwable cause, final ScreenAction backAction, final ErrorReportDecorator ... decorators) {
		
		super("The program died - this is the postmortem.", new PostMortemScreenView());
		log.fatal("Catastrophic Failure!", cause);
		
		if (backAction == null) {
			view.getBackPanel().setVisible(false);
		}
		else {
			view.getButtonBack().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					backAction.setEnvironment(environment);
					backAction.actionPerformed(e);
				}
			});
		}
		
		try {
			
			new Thread(new Runnable() {
				public void run() {
					try {
						screenSubmitErrorReport(cause, decorators);
					} catch(Exception ex) {
						ex.printStackTrace();
						PostMortemScreen.this.getView().progressFailed();
					}
				}
			}).start();
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
//		super.getView().getReportButton().addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				ProgressMonitorScreen progressScreen = new ProgressMonitorScreen("Sending report ...");
//				environment.next(progressScreen);
//				
//				try {
//					submitErrorReport(cause);
//					environment.next(new ErrorReportConfirmationScreen());
//				} catch(Exception ex) {
//					environment.next(PostMortemScreen.this);
//					JOptionPane.showMessageDialog(PostMortemScreen.this.getView().getRootPane(), "Could not send an error report!");
//					ex.printStackTrace();
//				}
//			}
//		});
	}
	
	public PostMortemScreen(final Throwable cause, final ErrorReportDecorator ... decorators) {
		this(cause, null, decorators);
	}
	
	private void screenSubmitErrorReport(final Throwable cause, final ErrorReportDecorator[] decorators) throws Exception {
		
		view.startProgress();
		
		submitErrorReport(cause, decorators);

		view.stopProgress();
	}
	
	public static void submitErrorReport(final Throwable cause, final ErrorReportDecorator...decorators) throws Exception {
		
		List<ErrorReportChunk> chunks = new LinkedList<ErrorReportChunk>();
		
		try {
			if (cause instanceof InternalErrorException) {
				InternalErrorException ie = (InternalErrorException)cause;
				ErrorReportChunk chunk = new ErrorReportChunk("internal-error-id", "text/plain", ie.id().getBytes("UTF8"));
				chunks.add(chunk);
			}
			else if (cause instanceof SOAPFaultException) {
				SOAPFaultException soapFault = (SOAPFaultException)cause;
				String content = soapFault.getFault().getFirstChild().getTextContent();
				String prefix = "Internal Service Error Occurred: ";
				if (content.startsWith(prefix)) {
					String id = content.substring(prefix.length());
					ErrorReportChunk chunk = new ErrorReportChunk("internal-error-id", "text/plain", id.getBytes("UTF8"));
					chunks.add(chunk);	
				}
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		
		// STACK TRACE
		ByteArrayOutputStream stackBytes = new ByteArrayOutputStream();
		PrintStream stackPrintStream = new PrintStream(stackBytes);
		cause.printStackTrace(stackPrintStream);
		stackPrintStream.close();
		stackBytes.close();
		
		ErrorReportChunk chunk = new ErrorReportChunk("stack trace", "text/plain", stackBytes.toByteArray());
		chunks.add(chunk);
		
		// THREAD DUMP
		
		ByteArrayOutputStream dumpBytes = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(dumpBytes);
		Map<Thread, StackTraceElement[]> traceMap = Thread.getAllStackTraces();
		for(Map.Entry<Thread, StackTraceElement[]> next: traceMap.entrySet()){
			out.println();
			out.println(next.getKey().getName());
			for(StackTraceElement line: next.getValue()){
				String className = emptyIfNull(line.getClassName());
				String methodName = emptyIfNull(line.getMethodName());
				String fileName = emptyIfNull(line.getFileName());
				
				out.println("    " + className + "." + methodName + " (" + fileName + " line " + line.getLineNumber() + ")");
			}
		}
		out.flush();
		out.close();
		ErrorReportChunk stackDump = new ErrorReportChunk("thread dump", "text/plain", dumpBytes.toByteArray());
		chunks.add(stackDump);
		
		// SYSTEM PROPERTIES
		ByteArrayOutputStream propsBytes = new ByteArrayOutputStream();
		PrintStream propsOut = new PrintStream(propsBytes);
		for(Map.Entry<Object, Object> next:System.getProperties().entrySet()){
			propsOut.println(" " + next.getKey() + "=" + next.getValue());
		}
		propsOut.flush();
		propsOut.close();
		chunks.add(new ErrorReportChunk("system properties", "text/plain", propsBytes.toByteArray()));
		
		// LOCAL CLOCK
		chunks.add(new ErrorReportChunk("local clock", "text/plain", new DateTime().toString().getBytes()));
		
		// NETWORKING
		StringBuffer networking = new StringBuffer();
		Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		while(ifaces.hasMoreElements()){
			NetworkInterface iface = ifaces.nextElement();
			networking.append("INTERFACE: " + iface.getName() + " (" + iface.getDisplayName() + ")\n");
			Enumeration<InetAddress> addresses = iface.getInetAddresses();
			while(addresses.hasMoreElements()){
				InetAddress address = addresses.nextElement();
				networking.append("  Address:" + address.getHostAddress() + "\n");
				networking.append("      Cannonical Host Name: " + address.getCanonicalHostName() + "\n");
				networking.append("                 Host Name: " + address.getHostName() + "\n");
			}
		}
		chunks.add(new ErrorReportChunk("network configuration", "text/plain", networking.toString().getBytes()));
		
		// DECORATORS
		if(decorators!=null){
			for(ErrorReportDecorator decorator: decorators){
				chunks.addAll(decorator.makeChunks(cause));
			}
		}
		ErrorReport report = new ErrorReport(chunks);
		Reporter reporter = new Reporter();
		ReportId id = reporter.submitReport(report);
	}
	
	private static String emptyIfNull(String in){
		return in==null?"":in;
	}
	

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(800,600);
		
		final PostMortemScreen postMortemScreen = new PostMortemScreen(new Throwable("Just testing the PostMortemScreen"));
		
		final ProgressMonitorScreen progress = new ProgressMonitorScreen("Waiting for something to fail.");
		
		ProcessPanel processPanel = new ProcessPanel(progress);
//		postMortemScreen.setEnvironment(processPanel);
		
		frame.add(processPanel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				progress.done(postMortemScreen);
			}
		}.start();
	}
	
}
