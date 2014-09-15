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
package org.spearal.jaxrs;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;

import org.spearal.SpearalFactory;

/**
 * Spearal constants
 * 
 * @author William DRAI
 */
public class SpearalJaxrs {
	
	public final static MediaType APPLICATION_SPEARAL_TYPE = new MediaType("application", "spearal");
	
	public static final String PROPERTY_FILTER_CLIENT = "spearalClientPropertyFilter";
	public static final String PROPERTY_FILTER_SERVER = "spearalServerPropertyFilter";
	
	
	public static SpearalFactory locateFactory(Configuration configuration, Providers providers) {
		ContextResolver<SpearalFactory> resolver = providers.getContextResolver(SpearalFactory.class, APPLICATION_SPEARAL_TYPE);
		if (resolver != null)
			return resolver.getContext(SpearalFactory.class);
		
		for (Object instance : configuration.getInstances()) {
			if (instance instanceof SpearalFactory)
				return (SpearalFactory)instance;
		}
		
		throw new RuntimeException("Could not locate SpearalFactory in JAX-RS context");
	}
}
