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

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.spearal.jaxrs.providers.SpearalContainerResponseFilter;
import org.spearal.jaxrs.providers.SpearalMessageBodyIO;

/**
 * Implements the server-side Spearal JAX-RS feature for use in Servlet containers
 * 
 * @author William DRAI
 */
@Provider
public class SpearalServerFeature implements Feature {
	
	public boolean configure(FeatureContext context) {
		
		// Register the message body reader/writer
		context.register(new SpearalMessageBodyIO());
		
		context.register(new SpearalContainerResponseFilter());
		
		return true;
	}
}
