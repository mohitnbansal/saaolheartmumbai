package com.saaolheart.mumbai.common.utility;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Level;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.utils.Log4jConfigurator;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.io.internal.ByteArrayOutputStream;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

/**
 * @author Dhananjay Jha
 *
 */
public class PdfGenerator<T> {
	
	private String filePath;
	
	 public PdfGenerator() {
		// TODO Auto-generated constructor stub  https://github.com/opensagres/xdocreport/wiki/DocxReportingJavaMainListFieldInTable  http://www.sambhashanam.com/mail-merge-in-java-for-microsoft-word-document-and-convert-to-pdf-without-itext-part-ii/
	}
	/**
	 * Takes file path as input and returns the stream opened on it
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	 private  static InputStream loadDocumentAsStream(String filePath) throws IOException{
		//URL url =new File(filePath).toURL();
		URL url =new File(filePath).toURI().toURL();
		InputStream documentTemplateAsStream=null;
		documentTemplateAsStream= url.openStream();
		return documentTemplateAsStream;
	}
	/**
	 * Loads the docx report 
	 * @param documentTemplateAsStream
	 * @param freemarkerOrVelocityTemplateKind
	 * @return
	 * @throws IOException
	 * @throws XDocReportException
	 */
	private static IXDocReport loadDocumentAsIDocxReport(InputStream documentTemplateAsStream, TemplateEngineKind freemarkerOrVelocityTemplateKind) throws IOException, XDocReportException{
		IXDocReport xdocReport = XDocReportRegistry.getRegistry().loadReport(documentTemplateAsStream, freemarkerOrVelocityTemplateKind);
		return xdocReport;
	}
	/**
	 * Takes the IXDocReport instance, creates IContext instance out of it and puts variables in the context 
	 * @param report
	 * @param variablesToBeReplaced
	 * @return
	 * @throws XDocReportException
	 */
	private static IContext replaceVariabalesInTemplateOtherThanImages(IXDocReport report, Map<String, Object> variablesToBeReplaced) throws XDocReportException{
		IContext context = report.createContext();
		for(Map.Entry<String, Object> variable: variablesToBeReplaced.entrySet()){
			context.put(variable.getKey(), variable.getValue());
		}
		return context;
	}
	/**
	 * Takes Map of image variable name and fileptah of the image to be replaced. Creates IImageprovides and adds the variable in context
	 * @param report
	 * @param variablesToBeReplaced
	 * @param context
	 */
	private static void replaceImagesVariabalesInTemplate(IXDocReport report, Map<String, String> variablesToBeReplaced, IContext context){
			
		 FieldsMetadata metadata = new FieldsMetadata();
         for(Map.Entry<String, String> variable: variablesToBeReplaced.entrySet()){
                 metadata.addFieldAsImage(variable.getKey());
                 context.put(variable.getKey(), new FileImageProvider(new File(variable.getValue()),true));
         }
         report.setFieldsMetadata(metadata);
		
	}
	/**
	 * Generates byte array as output from merged template
	 * @param report
	 * @param context
	 * @return
	 * @throws XDocReportException
	 * @throws IOException
	 */
	private static byte[] generateMergedOutput(IXDocReport report,IContext context ) throws XDocReportException, IOException{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		report.process(context,outputStream);
		return outputStream.toByteArray();
	}
	/**
	 * Takes inputs and returns merged output as byte[]
	 * @param templatePath
	 * @param templateEngineKind
	 * @param nonImageVariableMap
	 * @param imageVariablesWithPathMap
	 * @return
	 * @throws IOException
	 * @throws XDocReportException
	 */
	public static byte[] mergeAndGenerateOutput(String templatePath, TemplateEngineKind templateEngineKind, Map<String, Object> nonImageVariableMap,Map<String, String> imageVariablesWithPathMap ) throws IOException, XDocReportException{
		InputStream inputStream = loadDocumentAsStream(templatePath);
		IXDocReport xdocReport = loadDocumentAsIDocxReport(inputStream,templateEngineKind);
		IContext context = replaceVariabalesInTemplateOtherThanImages(xdocReport,nonImageVariableMap);
		replaceImagesVariabalesInTemplate(xdocReport, imageVariablesWithPathMap, context);
		byte[] mergedOutput = generateMergedOutput(xdocReport, context); 
		return mergedOutput;
	}
	/**
	 * Generates byte array as pdf output from merged template
	 * @param report
	 * @param context
	 * @return
	 * @throws XDocReportException
	 * @throws IOException
	 * @throws Docx4JException 
	 */
	private static byte[] generatePDFOutputFromDocx(byte[] docxBytes) throws XDocReportException, IOException, Docx4JException{
		
        ByteArrayOutputStream pdfByteOutputStream = new ByteArrayOutputStream();
        WordprocessingMLPackage wordprocessingMLPackage=null;

        Docx4jProperties.getProperties().setProperty("docx4j.Log4j.Configurator.disabled", "true");
        Log4jConfigurator.configure();            
        org.docx4j.convert.out.pdf.viaXSLFO.Conversion.log.setLevel(Level.OFF);
        wordprocessingMLPackage = WordprocessingMLPackage.load(new ByteArrayInputStream(docxBytes));
        PdfSettings pdfSettings = new PdfSettings();
        PdfConversion docx4jViaXSLFOconverter = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wordprocessingMLPackage);
       
        docx4jViaXSLFOconverter.output(pdfByteOutputStream, pdfSettings);
        return pdfByteOutputStream.toByteArray();
	}

	/**
	 * Takes inputs and returns merged output as pdf byte[]
	 * @param templatePath
	 * @param templateEngineKind
	 * @param nonImageVariableMap
	 * @param imageVariablesWithPathMap
	 * @return
	 * @throws IOException
	 * @throws XDocReportException
	 * @throws Docx4JException 
	 */
	public static byte[] mergeAndGeneratePDFOutput(String templatePath, TemplateEngineKind templateEngineKind,
			Map<String, Object> nonImageVariableMap,
			Map<String, String> imageVariablesWithPathMap ) throws IOException, XDocReportException, Docx4JException{
		
		InputStream inputStream = loadDocumentAsStream(templatePath);
		IXDocReport xdocReport = loadDocumentAsIDocxReport(inputStream,templateEngineKind);
		IContext context = replaceVariabalesInTemplateOtherThanImages(xdocReport,nonImageVariableMap);
		//replaceImagesVariabalesInTemplate(xdocReport, imageVariablesWithPathMap, context);
		byte[] mergedOutput = generateMergedOutput(xdocReport, context);
		byte[] pdfBytes = generatePDFOutputFromDocx(mergedOutput);
		return pdfBytes;
	}
}