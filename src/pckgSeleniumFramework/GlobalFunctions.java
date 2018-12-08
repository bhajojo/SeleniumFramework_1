package pckgSeleniumFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


//import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import net.sourceforge.htmlunit.corejs.javascript.tools.shell.Global;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class GlobalFunctions {

     //*****************************************************************************************
    //*	Name		    : fCopyXLS
    //*	Description	    : Function to copy given XLS
	//*	Author		    : Anil Agarwal
    //*	Input Params	: string inputXLS - Path of the input XLS
    //*				  	  String destXLS - PAth to store the Destination xls	
    //*	Return Values	: Connection object having the connection
    //*****************************************************************************************
    public void fCopyXLS(String inputXLS, String destXLS){
    	try{  
    		File f1 = new File(inputXLS);  
			File f2 = new File(destXLS);  
			InputStream in = new FileInputStream(f1);    
			OutputStream out = new FileOutputStream(f2);   
			byte[] buf = new byte[1024];  
			int len;  
			
			while ((len = in.read(buf)) > 0){  
				out.write(buf, 0, len);  
			}  
			
			in.close();  
			out.close();  
			
			} catch(FileNotFoundException ex){  
				System.out.println(ex.getMessage() + " in the specified directory.");  
				System.exit(0);  
			} catch(IOException e){  
				System.out.println(e.getMessage());
				System.exit(0);  
			}  
    	}
    
 	
	//Global Function to read the XML file and get the concerned path for the variable

	public String Environment(String variable) {
		String path = "C:\\Documents and Settings\\agarwaaf\\Desktop\\Environment_Variables.xml";
		String value = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(path));
			NodeList nodeList = doc.getElementsByTagName("Variable");
			if (nodeList.getLength() <= 0) {
				System.out
						.println("The Tag Environment is not present in the XML file");
			} else {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						NodeList varNmLst1 = element
								.getElementsByTagName("Name");
						Element varNmElmnt = (Element) varNmLst1.item(0);
						NodeList varNmLst2 = varNmElmnt.getChildNodes();
						String varName = (varNmLst2.item(0)).getNodeValue();
						if (varName.equals(variable)) {
							NodeList valLst1 = element
									.getElementsByTagName("Value");
							Element valElmnt = (Element) valLst1.item(0);
							NodeList valLst2 = valElmnt.getChildNodes();
							value = (valLst2.item(0)).getNodeValue();
							break;
						}
					}
				}
			}

		} catch (DOMException de) {
			de.printStackTrace();
		} catch (ParserConfigurationException pe) {
			pe.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		return value;
	}
	
	//*****************************************************************************************
	//*	Name		    : fGlobalDeleteFolder
	//*	Description	    : Deletes a folder after deleting all its sub-folders and files
	//*	Author		    : Abhishek Pandey
	//* Parameters		: FolderPath
	//* Dictionary		: 
	//*	Date Modified	: 2-Sep-12
	//*	Return Values	: None
	//*****************************************************************************************
	public void fGlobalDeleteFolder(File FolderPath) {
		if (FolderPath.isDirectory()) {
		    String[] arrChildNodes = FolderPath.list();
		    for (int i=0; i<arrChildNodes.length; i++) {
		    	fGlobalDeleteFolder(new File(FolderPath, arrChildNodes[i]));
		    }
		}
		FolderPath.delete();
	}
	

}

