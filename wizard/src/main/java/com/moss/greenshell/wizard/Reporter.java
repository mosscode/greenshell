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

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.moss.error_reporting.api.ErrorReport;
import com.moss.error_reporting.api.ErrorReportChunk;
import com.moss.error_reporting.api.ReportId;
import com.moss.error_reporting.api.ReportingService;

public class Reporter {
	private static String serviceUrlString = "http://localhost:6008/Reporting?wsdl";
	private ErrorReportServerConfiguration configuration = new ErrorReportServerConfiguration();
	
	public static void main(String[] args) {
		
		try {
			URL serviceUrl = new URL(serviceUrlString);
			ReportingService reportingService = materializeReportingService(serviceUrl);
			
			String data = "Blahblahblahblahblahblahblahblahblahblahblahblahblabhalhb";
			ErrorReportChunk chunk = new ErrorReportChunk("test", "text/plain", data.getBytes());
			List<ErrorReportChunk> chunks = new LinkedList<ErrorReportChunk>();
			chunks.add(chunk);
			ErrorReport report = new ErrorReport(chunks);
			Reporter reporter = new Reporter();
			ReportId id = reporter.submitReport(report);
			
			ErrorReport retrievedReport = reportingService.getReport(id);
			System.out.println("Retrieved data: "+ new String(retrievedReport.getReportChunks().get(0).getData()));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	public Reporter() {
		configuration = new ErrorReportServerConfiguration();
	}
	
	public ReportId submitReport(ErrorReport report) throws Exception {
			String urlText = configuration.getServiceUrl().toExternalForm();
			if(!urlText.endsWith("/Reporting?wsdl"))
				urlText = urlText + "/Reporting?wsdl";
			URL serviceUrl = new URL(urlText);
			ReportingService reportingService = materializeReportingService(serviceUrl);
			
			ReportId id = reportingService.submitReport(report);
			
			return id;
	}
	
	public static ReportingService materializeReportingService(URL serviceUrl) {
		QName serviceName = new QName("http://api.error_reporting.moss.com/", "ReportingServiceService");
		return materializeWithDecorations(serviceUrl, serviceName, ReportingService.class);
	}
	
	private static <T> T materializeWithDecorations(URL serviceUrl, QName serviceName, Class<T> serviceInterface){
		System.out.print("Materializing service (of type " + serviceInterface.getName() + ") defined at " + serviceUrl.toExternalForm() + "...");
		Service service = Service.create(serviceUrl, serviceName);
		
		T port = service.getPort(serviceInterface);
		
		System.out.println("DONE");
		
		return port;
	}
}
