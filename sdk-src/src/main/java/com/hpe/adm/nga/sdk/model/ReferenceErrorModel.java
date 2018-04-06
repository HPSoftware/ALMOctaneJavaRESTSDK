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
package com.hpe.adm.nga.sdk.model;

/**
 *
 * This class hold the ReferenceErrorModel objects and serve as a ReferenceError
 * type FieldModel data holder
 *
 *
 */
public class ReferenceErrorModel implements FieldModel<ReferenceErrorModel.ReferenceError> {

    // Private
    private ReferenceError refValue;
    private String refName;

    /**
     * Creates a new ReferenceErrorModel object
     * 
     * @param name
     *            - Field name
     * @param value
     *            - Field Value
     */
    public ReferenceErrorModel(String name, ReferenceError value) {

        setValue(name, value);
    }

    /**
     * get value
     */
    @Override
    public ReferenceError getValue() {
        return refValue;
    }

    /**
     * get name
     */
    @Override
    public String getName() {
        return refName;
    }

    /**
     * set value
     */
    @Override
    public void setValue(String name, ReferenceError value) {

        refValue = value;
        refName = name;
    }

    /**
     * data structure for referenceError
     *
     */
    public static class ReferenceError {

        private long entity_id; // Variable name must reflect Rest variable name
                                // ( Gson().fromJson )
        private String entity_type; // Variable name must reflect Rest variable
                                    // name ( Gson().fromJson )

        /**
         * Creates a new ReferenceError object
         * 
         * @param Id
         *            - ReferenceError id
         * @param type
         *            - ReferenceError type
         */
        public ReferenceError(long Id, String type) {

            setValues(Id, type);
        }

        /**
         * set value
         * 
         * @param id
         *            the entity id
         * @param type
         *            the entity type
         */
        public void setValues(long id, String type) {

            entity_id = id;
            entity_type = type;
        }

        /**
         * get id
         * 
         * @return entity id
         */
        public long getId() {
            return entity_id;
        }

        /**
         * get type
         * 
         * @return entity type
         */
        public String gettype() {
            return entity_type;
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((refName == null) ? 0 : refName.hashCode());
        result = prime * result + ((refValue == null) ? 0 : refValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReferenceErrorModel other = (ReferenceErrorModel) obj;
        if (refName == null) {
            if (other.refName != null)
                return false;
        } else if (!refName.equals(other.refName))
            return false;
        if (refValue == null) {
            if (other.refValue != null)
                return false;
        } else if (!refValue.equals(other.refValue))
            return false;
        return true;
    }

}
