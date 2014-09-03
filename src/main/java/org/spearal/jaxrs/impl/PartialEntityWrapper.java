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

import java.io.Serializable;

import org.spearal.jaxrs.filter.PropertyFilterBuilder;

/**
 * Internal wrapper to transmit property filters between request/response filters and message body reader/writer
 * 
 * @author William DRAI
 */
public class PartialEntityWrapper implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Object entity;
	private transient final PropertyFilterBuilder propertyFilter;
	
	
	public PartialEntityWrapper(Object entity, PropertyFilterBuilder propertyFilter) {
		this.entity = entity;
		this.propertyFilter = propertyFilter;
	}
	
	public Object getEntity() {
		return entity;
	}
	
	public PropertyFilterBuilder getPropertyFilter() {
		return propertyFilter;
	}
}