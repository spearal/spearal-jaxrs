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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.spearal.SpearalDecoder;
import org.spearal.SpearalEncoder;
import org.spearal.SpearalFactory;
import org.spearal.jaxrs.Spearal;
import org.spearal.jaxrs.impl.PartialEntityWrapper;

/**
 * JAX-RS message body reader/writer 
 * 
 * @author William DRAI
 */
@Consumes(Spearal.APPLICATION_SPEARAL)
@Produces(Spearal.APPLICATION_SPEARAL)
public class SpearalMessageBodyIO implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
	
	private final SpearalFactory factory;
	
	public SpearalMessageBodyIO(SpearalFactory factory) {
		this.factory = factory;
	}
	
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return PartialEntityWrapper.class == type || (Serializable.class.isAssignableFrom(type) && mediaType.equals(Spearal.APPLICATION_SPEARAL_TYPE));
	}
	
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return Serializable.class.isAssignableFrom(type) && mediaType.equals(Spearal.APPLICATION_SPEARAL_TYPE);
	}

	public long getSize(Object obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	public void writeTo(Object obj, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {
		
		SpearalEncoder encoder = factory.newEncoder(entityStream);
		if (obj instanceof PartialEntityWrapper) {
			// Unwrap partial entities that may have been wrapped by Client/Container filters 
			// and apply property filters
			PartialEntityWrapper ew = (PartialEntityWrapper)obj;
			ew.getPropertyFilter().apply(encoder.getPropertyFilter());
			obj = ew.getEntity();
		}
		encoder.writeAny(obj);
	}
	
	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		
		SpearalDecoder decoder = factory.newDecoder(entityStream);
		return decoder.readAny(genericType);
	}

}
