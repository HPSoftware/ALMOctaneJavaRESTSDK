/*
 * Copyright 2017 Hewlett-Packard Enterprise Development Company, L.P.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microfocus.adm.almoctane.sdk.model;


/**
 *
 * This class hold the ReferenceFieldModel objects and serve as a ReferenceField type FieldModel data holder 
 *
 *
 */
public class ReferenceFieldModel implements FieldModel<EntityModel> {
	
	//Private 
	private EntityModel refValue;
	private String refName;
	
	/**
	 * Creates a new ReferenceFieldModel object
	 * 
	 * @param name - Field name
	 * @param value - Field Value
	 */
	public ReferenceFieldModel(String name,EntityModel value){
		
		setValue( name, value);
	}
	
	/**
	 * GetEntities Value
	 */
	public EntityModel getValue(){
		return refValue ;
	}
	
	/**
	 * GetEntities name
	 */
	public String getName(){
		return refName;
	}
	
	/**
	 * Set name/value
	 */
	public void setValue(String name,EntityModel value){
		
		refValue = value;
		refName = name;
	}
	
	
}