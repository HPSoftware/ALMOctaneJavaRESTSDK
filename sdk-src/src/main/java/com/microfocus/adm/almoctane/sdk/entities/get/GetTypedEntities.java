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
package com.microfocus.adm.almoctane.sdk.entities.get;

import com.microfocus.adm.almoctane.sdk.entities.OctaneCollection;
import com.microfocus.adm.almoctane.sdk.entities.TypedEntityList;
import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.model.OctaneCollectionSupplier;
import com.microfocus.adm.almoctane.sdk.model.TypedEntityModel;
import com.microfocus.adm.almoctane.sdk.network.OctaneHttpClient;
import com.microfocus.adm.almoctane.sdk.network.OctaneRequest;
import com.microfocus.adm.almoctane.sdk.query.Query;

import java.util.stream.Collectors;

/**
 * The generic super class for the context of get for typed entities.
 *
 * @param <T> The type of the entity model
 * @param <E> The implementing subclass of this class
 * @param <F> The type of {@link com.microfocus.adm.almoctane.sdk.entities.TypedEntityList.AvailableFields}
 * @param <G> The type of {@link com.microfocus.adm.almoctane.sdk.entities.TypedEntityList.SortableFields}
 * @see GetEntities for the non typed version
 */
public abstract class GetTypedEntities<T extends TypedEntityModel, E extends GetTypedEntities, F extends TypedEntityList.AvailableFields, G extends TypedEntityList.SortableFields>
        extends TypedEntityList.TypedEntityRequest<T> {

    private final OctaneRequest octaneRequest;

    protected GetTypedEntities(final Class<T> typedEntityModelClass, final OctaneHttpClient octaneHttpClient, final String baseDomain) {
        super(typedEntityModelClass);
        octaneRequest = new OctaneRequest(octaneHttpClient, baseDomain);
    }

    /**
     * Carries out the execution and returns the entities
     *
     * @return The entities

     */
    public final OctaneCollection<T> execute()  {
        final OctaneCollection<EntityModel> entityModels = GetHelper.getInstance().getEntityModels(octaneRequest);
        return entityModels
                .stream()
                .map(this::getEntityInstance)
                .collect(Collectors.toCollection(new OctaneCollectionSupplier<>(entityModels)));
    }

    /**
     * Adds fields of type F
     *
     * @param fields Array of fields
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public final E addFields(final F... fields) {
        GetTypedHelper.addFields(octaneRequest, fields);
        return (E) this;
    }

    /**
     * Add Limit parameter
     *
     * @param limit The entity limit
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public final E limit(final int limit) {
        octaneRequest.getOctaneUrl().setLimitParam(limit);
        return (E) this;
    }

    /**
     * Add offset parameter
     *
     * @param offset The entity limit offset
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public final E offset(final int offset) {
        octaneRequest.getOctaneUrl().setOffsetParam(offset);
        return (E) this;
    }

    /**
     * Add OrderBy parameters
     *
     * @param sortableField The string which determines how the entities should be ordered
     * @param asc           - true=ascending/false=descending
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public final E addOrderBy(final G sortableField, boolean asc) {
        octaneRequest.getOctaneUrl().setOrderByParam(sortableField.getFieldName(), asc);
        return (E) this;
    }

    /**
     * @param query The query to use
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public final E query(final Query query) {
        octaneRequest.getOctaneUrl().setDqlQueryParam(query);
        return (E) this;
    }
}