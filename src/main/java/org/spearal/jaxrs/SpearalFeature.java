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

import org.spearal.SpearalFactory;
import org.spearal.jaxrs.impl.SpearalClientRequestFilter;
import org.spearal.jaxrs.impl.SpearalContainerResponseFilter;
import org.spearal.jaxrs.impl.SpearalProvider;

/**
 * @author William DRAI
 */
@Provider
public class SpearalFeature implements Feature {
	
	private SpearalFactory factory = new SpearalFactory();
	
	public boolean configure(FeatureContext context) {
		SpearalFactory factory = SpearalFactory.getInstance();
		if (factory == null) {
			for (Object instance : context.getConfiguration().getInstances()) {
				if (instance instanceof SpearalFactory) {
					factory = (SpearalFactory)instance;
					break;
				}
			}
			if (factory != null)
				this.factory = factory;
			else
				factory = this.factory;
		}
		
		context.register(factory);
		context.register(new SpearalProvider(factory, context.getConfiguration()));
		try {
			Thread.currentThread().getContextClassLoader().loadClass("javax.ws.rs.client.ClientRequestFilter");
			context.register(new SpearalClientRequestFilter(factory));
		}
		catch (ClassNotFoundException e) {
			// Not on the client
		}
		try {
			Thread.currentThread().getContextClassLoader().loadClass("javax.ws.rs.container.ContainerResponseFilter");
			context.register(new SpearalContainerResponseFilter(factory));
		}
		catch (ClassNotFoundException e) {
			// Not on the server
		}
		return true;
	}

}
