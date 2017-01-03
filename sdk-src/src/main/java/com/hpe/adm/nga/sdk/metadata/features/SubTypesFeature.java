package com.hpe.adm.nga.sdk.metadata.features;

/**
 *    Copyright 2017 Hewlett-Packard Development Company, L.P.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * This class hold the SubTypesFeature object and serve all functionality concern to SubTypes Feature
 *
 */
public class SubTypesFeature extends Feature{
	
	private String[] types;
	private boolean editable;

	/**
	 * get types
	 * @return
	 */
	public String[] getTypes(){return types;}

    /**
	 * get editable
	 * @return
     */
	public boolean getEditable() { return editable; }
}



