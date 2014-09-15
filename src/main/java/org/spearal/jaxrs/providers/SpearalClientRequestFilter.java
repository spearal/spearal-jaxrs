/**
 * == @Spearal ==>
 * 
 * Copyright (C) 2014 Franck WOLFF & William DRAI (http://www.spearal.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spearal.jaxrs.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Providers;

import org.spearal.Spearal;
import org.spearal.SpearalFactory;
import org.spearal.filter.SpearalPropertyFilterBuilder;
import org.spearal.jaxrs.SpearalJaxrs;
import org.spearal.jaxrs.impl.SpearalEntity;

/**
 * JAX-RS client request filter
 * 
 * @author William DRAI
 */
public class SpearalClientRequestFilter implements ClientRequestFilter {
	
	@Context
	private Configuration configuration;
	
	@Context
	private Providers providers;
	
	public void filter(ClientRequestContext requestContext) throws IOException {
		// Wrap entity to store property filters
		SpearalPropertyFilterBuilder clientPropertyFilterBuilder = (SpearalPropertyFilterBuilder)requestContext.getProperty(SpearalJaxrs.PROPERTY_FILTER_CLIENT);
		if (clientPropertyFilterBuilder != null)
			requestContext.setEntity(new SpearalEntity(requestContext.getEntity(), clientPropertyFilterBuilder));
		
		// Transmit server property filters as http header
		SpearalPropertyFilterBuilder serverPropertyFilterBuilder = (SpearalPropertyFilterBuilder)requestContext.getProperty(SpearalJaxrs.PROPERTY_FILTER_SERVER);
		if (serverPropertyFilterBuilder == null)
			return;
		
		SpearalFactory factory = SpearalJaxrs.locateFactory(configuration, providers);
		
		List<Object> serverPropertyFilterHeaders = new ArrayList<Object>(serverPropertyFilterBuilder.toHeaders(factory.getContext()));
		requestContext.getHeaders().put(Spearal.PROPERTY_FILTER_HEADER, serverPropertyFilterHeaders);
	}
}
