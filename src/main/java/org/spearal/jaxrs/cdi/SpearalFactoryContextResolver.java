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
package org.spearal.jaxrs.cdi;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.spearal.DefaultSpearalFactory;
import org.spearal.Spearal;
import org.spearal.SpearalFactory;

/**
 * CDI resolver for SpearalFactory 
 * 
 * @author William DRAI
 */
@Provider
@Produces({ Spearal.APPLICATION_SPEARAL })
public class SpearalFactoryContextResolver implements ContextResolver<SpearalFactory> {
	
	@Inject
	private Instance<SpearalFactory> spearalFactory;
	
	private SpearalFactory defaultFactory;
	
	@PostConstruct
	private void initDefaultSpearalFactory() {
		if (spearalFactory == null || spearalFactory.isUnsatisfied())
			this.defaultFactory = new DefaultSpearalFactory();
	}
	
	public SpearalFactory getContext(Class<?> clazz) {
		if (clazz.equals(SpearalFactory.class) && !spearalFactory.isUnsatisfied())
			return spearalFactory.get();
		return defaultFactory;
	}
}
