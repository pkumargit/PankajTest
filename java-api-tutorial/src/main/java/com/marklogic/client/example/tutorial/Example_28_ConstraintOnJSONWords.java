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

import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.admin.QueryOptionsManager;
import com.marklogic.client.admin.config.QueryOptionsBuilder;
import com.marklogic.client.io.QueryOptionsHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

/**
 * ConstraintOnJSONWords shows how to define and use a JSON key word constraint.
 */
public class Example_28_ConstraintOnJSONWords {

	public static void main(String[] args) throws IOException {
		System.out.println("example: "+Example_28_ConstraintOnJSONWords.class.getName());

		// create the client, connecting as the rest-admin user
		DatabaseClient client = DatabaseClientFactory.newClient(
    	    Config.host, 
		    Config.port,
		    Config.admin_user,
		    Config.admin_password, 
		    Config.authType);

		/**** SERVER CONFIGURATION ****/
		// get an options manager
		QueryOptionsManager optionsMgr = client.newServerConfigManager().newQueryOptionsManager();

		// Create a builder for constructing query configurations.
		QueryOptionsBuilder qob = new QueryOptionsBuilder();

		// get the existing tutorial options
		QueryOptionsHandle tutorialOpts =
		    optionsMgr.readOptions("tutorial", new QueryOptionsHandle());

		// add a JSON word constraint
		if (tutorialOpts.getConstraint("bio") == null)
		    tutorialOpts.addConstraint(
		        qob.constraint("bio",
		            qob.word(
		                qob.jsonTermIndex("bio"))));

		// write the query options back to the server
		optionsMgr.writeOptions("tutorial", tutorialOpts);
		
		System.out.println("Wrote \"tutorial\" options to server.");
		
		/**** SEARCH ****/
		// create a manager for searching
		QueryManager queryMgr = client.newQueryManager();

		// create a search definition using the "tutorial" options
		StringQueryDefinition query = queryMgr.newStringDefinition("tutorial");

		// search for speakers whose bio mentions "strategy"
		query.setCriteria("bio:strategy");
		
		// run the search
		SearchHandle resultsHandle = queryMgr.search(query, new SearchHandle());

		// format the results
		TutorialUtil.displayResults(resultsHandle);

		// release the client
		client.release();
	}
}
