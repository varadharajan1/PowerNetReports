package com.pfg.pnet.reports.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.pfg.pnet.reports.dto.ReportRequest;

public class ObjectXmlConvertor {

	private static final Logger logger = Logger.getLogger(ObjectXmlConvertor.class.getName());
	
	private ObjectXmlConvertor() { }
	
	public static String convertToXML(ReportRequest request) {			
		logger.log(Level.INFO, "ObjectXmlConvertor: convertToXML started. {0}", request);
		String xmlContent = null;
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportRequest.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			 
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(request, sw);

			xmlContent = sw.toString();

		} catch (JAXBException e) {
			logger.log(Level.SEVERE, "JAXBException: {0}", e);
        }
		
		logger.log(Level.INFO, "ObjectXmlConvertor: convertToXML ended. {0}", xmlContent);
		return xmlContent;
	}

	public static ReportRequest convertToObject(String xmlContent) {
		logger.log(Level.INFO, "ObjectXmlConvertor: convertToObject started. {0}", xmlContent);
		ReportRequest request = null;
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(ReportRequest.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			request = (ReportRequest) jaxbUnmarshaller.unmarshal(new StringReader(xmlContent));
			
		} catch (JAXBException e) {
			logger.log(Level.SEVERE, "JAXBException: {0}", e);
        }
		logger.log(Level.INFO, "ObjectXmlConvertor: convertToObject ended. {0}", request);
		return request;
	}
}
