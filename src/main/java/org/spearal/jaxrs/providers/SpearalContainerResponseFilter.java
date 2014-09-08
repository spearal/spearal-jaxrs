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
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Providers;

import org.spearal.SpearalFactory;
import org.spearal.filter.SpearalPropertyFilterBuilder;
import org.spearal.jaxrs.Spearal;
import org.spearal.jaxrs.impl.PartialEntityWrapper;

/**
 * JAX-RS server response filter
 * 
 * @author William DRAI
 */
public class SpearalContainerResponseFilter implements ContainerResponseFilter {
	
	@Context
	private Configuration configuration;
	
	@Context
	private Providers providers;
	
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		List<String> headers = requestContext.getHeaders().get(Spearal.PROPERTY_FILTER_HEADER);
		if (headers == null || headers.isEmpty())
			return;
		
		SpearalFactory factory = Spearal.locateFactory(configuration, providers);
		
		// Wrap entity to store property filters
		SpearalPropertyFilterBuilder serverPropertyFilters = SpearalPropertyFilterBuilder.fromHeaders(factory.getContext(), headers);
		responseContext.setEntity(new PartialEntityWrapper(responseContext.getEntity(), serverPropertyFilters));
	}
}
