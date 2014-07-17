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
package org.spearal.jaxrs.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spearal.SpearalContext;
import org.spearal.SpearalPropertyFilter;
import org.spearal.impl.util.ClassDescriptionUtil;

/**
 * @author William DRAI
 */
public class PropertyFilters {
	
	public static final String CLIENT = "org.spearal.jaxrs.Client";
	public static final String SERVER = "org.spearal.jaxrs.Server";
	
	private final Map<Class<?>, String[]> propertyFilters = new LinkedHashMap<Class<?>, String[]>();
	
	public static PropertyFilters of(Class<?> entityClass, String... propertyNames) {
		PropertyFilters propertyFilter = new PropertyFilters();
		return propertyFilter.and(entityClass, propertyNames);
	}
	
	public PropertyFilters and(Class<?> entityClass, String... propertyNames) {
		propertyFilters.put(entityClass, propertyNames);
		return this;
	}
	
	public void apply(SpearalPropertyFilter propertyFilter) {
		for (Entry<Class<?>, String[]> pf : propertyFilters.entrySet()) {
			propertyFilter.add(pf.getKey(), pf.getValue());
		}
	}
	
	public List<Object> toHeaders(SpearalContext context) {
		List<Object> headers = new ArrayList<Object>();
		for (Entry<Class<?>, String[]> pf : propertyFilters.entrySet())
			headers.add(ClassDescriptionUtil.createAliasedDescription(context, pf.getKey(), pf.getValue()));		
		return headers;
	}
	
	public static PropertyFilters fromHeaders(SpearalContext context, List<String> headers) {
		PropertyFilters propertyFilters = null;
		for (String header : headers) {
			String classNames = ClassDescriptionUtil.classNames(header);
			Class<?> filterClass = context.loadClass(classNames, null);
			String[] propertyNames = ClassDescriptionUtil.splitPropertyNames(header);
			if (propertyFilters == null)
				propertyFilters = of(filterClass, propertyNames);
			else
				propertyFilters.and(filterClass, propertyNames);
		}
		return propertyFilters;
	}
}
