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
package org.spearal.jaxrs.impl;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.spearal.SpearalFactory;
import org.spearal.jaxrs.Spearal;
import org.spearal.jaxrs.helper.PropertyFilters;

/**
 * @author William DRAI
 */
public class SpearalContainerResponseFilter implements ContainerResponseFilter {
	
	private final SpearalFactory factory;
	
	public SpearalContainerResponseFilter(SpearalFactory factory) {
		this.factory = factory;
	}
		
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		List<String> headers = requestContext.getHeaders().get(Spearal.PROPERTY_FILTER_HEADER);
		if (headers == null || headers.isEmpty())
			return;
		
		PropertyFilters serverPropertyFilters = PropertyFilters.fromHeaders(factory.getContext(), headers);
		responseContext.setEntity(new PartialEntityWrapper(responseContext.getEntity(), serverPropertyFilters));
	}
}
