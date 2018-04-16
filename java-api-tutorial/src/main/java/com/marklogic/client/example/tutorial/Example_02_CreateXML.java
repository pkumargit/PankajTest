/*
 * Copyright 2012 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marklogic.client.example.tutorial;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.Transaction;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.InputStreamHandle;

/**
 * CreateXML illustrates how to write XML content to a database document1.
 */
public class Example_02_CreateXML {
	static Logger logger = Logger.getLogger(Example_02_CreateXML.class.getName());
	public static void main(String[] args) throws IOException {

		System.out.println("example: "+Example_02_CreateXML.class.getName());

		// create the client
		DatabaseClient client = DatabaseClientFactory.newClient(Config.host, Config.port, Config.user, Config.password, Config.authType);
		
		// acquire the content
		InputStream docStream = Example_02_CreateXML.class.getClassLoader().getResourceAsStream(
			"data"+File.separator+"flipper.xml");

		// create a manager for XML documents
		XMLDocumentManager docMgr = client.newXMLDocumentManager();

		// create a handle on the content
		InputStreamHandle handle = new InputStreamHandle(docStream);
		
		// url encode
		
		/*ServerEvaluationCall theCall = client.newServerEval();
		String query = "xquery version '1.0-ml';" + " xdmp:url-encode('/dms/document/報告書.xml');";
		theCall.xquery(query);

		String response = theCall.evalAs(String.class);*/
		
		// url encode end

		// write the document content
		docMgr.write("/example/pankaj.xml", handle);
		
		/*if(docMgr.exists("/example/flipper3.xml") == null) {
			//System.out.println("URI does not exists hecne loading it to the database!");
			logger.info("/example/flipper.xml");
			docMgr.write("/example/flipper3.xml", handle);
		}
		else */
			
//System.out.println("The document URI already exists!");
		//System.out.println("Wrote /example/flipper.xml content");

		// release the client
		
		// decode
		// read the document content
		// create a handle to receive the document content
	/*	System.out.println();
				DOMHandle readhandle = new DOMHandle();
				docMgr.read(response, readhandle);

				// access the document content
				Document document = readhandle.get();

				
				
				//System.out.println("document fetched is: " + document.toString());
				
				String rootName = document.getDocumentElement().getTagName();
				
				System.out.println("Read /example/flipper.xml content with the <"+rootName+"/> root element");
		
		//decode end */
		client.release();
	}
}
