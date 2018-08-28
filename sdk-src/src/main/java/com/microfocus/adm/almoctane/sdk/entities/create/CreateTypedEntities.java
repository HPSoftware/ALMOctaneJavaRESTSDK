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
package com.microfocus.adm.almoctane.sdk.entities.create;

import com.microfocus.adm.almoctane.sdk.entities.OctaneCollection;
import com.microfocus.adm.almoctane.sdk.entities.TypedEntityList;
import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.model.OctaneCollectionSupplier;
import com.microfocus.adm.almoctane.sdk.model.TypedEntityModel;
import com.microfocus.adm.almoctane.sdk.network.OctaneHttpClient;
import com.microfocus.adm.almoctane.sdk.network.OctaneRequest;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The generic super class for the context of create for typed entities.
 *
 * @param <T> The type of the entity model
 * @param <E> The implementing subclass of this class
 * @see CreateEntities for the non typed version
 */
public abstract class CreateTypedEntities<T extends TypedEntityModel, E extends CreateTypedEntities> extends TypedEntityList.TypedEntityRequest<T> {
    private Collection<T> entityModels = null;
    private final OctaneRequest octaneRequest;

    protected CreateTypedEntities(final Class<T> typedEntityModelClass, final OctaneHttpClient octaneHttpClient, final String urlDomain) {
        super(typedEntityModelClass);
        octaneRequest = new OctaneRequest(octaneHttpClient, urlDomain);
    }

    /**
     * Carries out the execution and returns a collection of created entities
     * @return The collection of typed entities

     */
    public final OctaneCollection<T> execute()  {
        final List<EntityModel> convertedEntityModels = entityModels.stream().map(T::getWrappedEntityModel).collect(Collectors.toList());
        final OctaneCollection<EntityModel> createdEntities = CreateHelper.getInstance().createEntities(convertedEntityModels, octaneRequest);
        return createdEntities
                .stream()
                .map(this::getEntityInstance)
                .collect(Collectors.toCollection(new OctaneCollectionSupplier<>(createdEntities)));
    }

    /**
     * Adds the entities to be sent to the server to be created
     * @param entities A collection of entities
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public final E entities(Collection<T> entities) {
        entityModels = entities;
        return (E) this;
    }
}