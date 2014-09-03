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

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.spearal.SpearalFactory;
import org.spearal.jaxrs.Spearal;
import org.spearal.jaxrs.filter.PropertyFilterBuilder;
import org.spearal.jaxrs.impl.PartialEntityWrapper;

/**
 * JAX-RS client request filter
 * 
 * @author William DRAI
 */
public class SpearalClientRequestFilter implements ClientRequestFilter {
	
	private final SpearalFactory factory;
	
	public SpearalClientRequestFilter(SpearalFactory factory) {
		this.factory = factory;
	}
	
	public void filter(ClientRequestContext requestContext) throws IOException {
		// Wrap entity to store property filters
		PropertyFilterBuilder clientPropertyFilters = (PropertyFilterBuilder)requestContext.getProperty(PropertyFilterBuilder.CLIENT);
		if (clientPropertyFilters != null)
			requestContext.setEntity(new PartialEntityWrapper(requestContext.getEntity(), clientPropertyFilters));
		
		// Transmit server property filters as http header
		PropertyFilterBuilder serverPropertyFilters = (PropertyFilterBuilder)requestContext.getProperty(PropertyFilterBuilder.SERVER);
		if (serverPropertyFilters != null)
			requestContext.getHeaders().put(Spearal.PROPERTY_FILTER_HEADER, serverPropertyFilters.toHeaders(factory.getContext()));
	}
}
