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

import org.spearal.jaxrs.helper.PropertyFilters;

/**
 * @author William DRAI
 */
public class PartialEntityWrapper {
	
	private final Object entity;
	private final PropertyFilters propertyFilter;
	
	
	public PartialEntityWrapper(Object entity, PropertyFilters propertyFilter) {
		this.entity = entity;
		this.propertyFilter = propertyFilter;
	}
	
	public Object getEntity() {
		return entity;
	}
	
	public PropertyFilters getPropertyFilter() {
		return propertyFilter;
	}
}