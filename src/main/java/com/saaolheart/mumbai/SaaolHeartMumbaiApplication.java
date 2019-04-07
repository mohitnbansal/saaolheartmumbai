package com.saaolheart.mumbai;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.saaolheart.mumbai.common.utility.PdfGenerator;
import com.saaolheart.mumbai.configuration.repositoryconfig.CustomRepositoryImpl;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;


@SpringBootApplication
@ComponentScan(basePackages= {"com.saaolheart.mumbai"})
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@EnableAutoConfiguration
public class SaaolHeartMumbaiApplication {

	public static void main(String[] args) throws IOException, XDocReportException, Docx4JException {
		SpringApplication.run(SaaolHeartMumbaiApplication.class, args);

		/*
		 * DocxStamper<DocxContext> stamper = new DocxStamper<DocxContext>(new
		 * DocxStamperConfiguration()); DocxContext context = new DocxContext();
		 * context.setCompanyName("Johnwewewe"); List<String> str = new
		 * ArrayList<String>(); str.add("wqeqwe"); str.add("wqeqwqwee");
		 * str.add("wqeqwqweqe"); str.add("wqeqwweqwee"); str.add("wqeqwe");
		 * context.setStringList(str); InputStream template = new FileInputStream(new
		 * File(
		 * "/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/ThankYouNote_Template.docx"
		 * )); OutputStream out = new FileOutputStream("output_template.docx");
		 * stamper.stamp(template, context, out); out.close();
		 * 
		 * 
		 */
		
//		  String templatePath = "/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/ThankYouNote_Template.docx";
//		  
//		  Map<String, Object> nonImageVariableMap = new HashMap<String, Object>();
//		  nonImageVariableMap.put("thank_you_date", "24-June-2013");
//		  nonImageVariableMap.put("name", "Rajani Jha");
//		  nonImageVariableMap.put("website", "www.sambhashanam.com");
//		  nonImageVariableMap.put("author_name", "Dhananjay Jha"); Map<String, String>
//		  imageVariablesWithPathMap =new HashMap<String, String>();
//		  imageVariablesWithPathMap.put("header_image_logo","/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/im.png");
//		  
//		  byte[] mergedOutput =  PdfGenerator.mergeAndGeneratePDFOutput(templatePath, TemplateEngineKind.Velocity, nonImageVariableMap, imageVariablesWithPathMap); 
//		  FileOutputStream os = new FileOutputStream("/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/"+System.nanoTime()+".pdf");
//		  os.write(mergedOutput);
//		  os.flush();
//		  os.close();
		 
	}

}
