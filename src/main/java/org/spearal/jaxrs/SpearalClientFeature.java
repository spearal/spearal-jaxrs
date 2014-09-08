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

import org.spearal.DefaultSpearalFactory;
import org.spearal.SpearalFactory;
import org.spearal.configuration.Configurable;
import org.spearal.jaxrs.providers.SpearalClientRequestFilter;
import org.spearal.jaxrs.providers.SpearalMessageBodyIO;

/**
 * Implements the client-side Spearal JAX-RS feature
 * 
 * @author William DRAI
 */
public class SpearalClientFeature implements Feature {
	
	public boolean configure(FeatureContext context) {
		
		SpearalFactory spearalFactory = null;
		
		// Retrieve or create a SpearalFactory in the JAX-RS context
		for (Object instance : context.getConfiguration().getInstances()) {
			if (instance instanceof SpearalFactory) {
				spearalFactory = (SpearalFactory)instance;
				break;
			}
		}
		if (spearalFactory == null) {
			spearalFactory = new DefaultSpearalFactory();
			context.register(spearalFactory);
		}
		
		for (Object instance : context.getConfiguration().getInstances()) {
			if (instance instanceof Configurable)
				spearalFactory.getContext().configure((Configurable)instance);
		}
		
		// Register the message body reader/writer
		context.register(new SpearalMessageBodyIO());
		
		context.register(new SpearalClientRequestFilter());
		
		return true;
	}
}
