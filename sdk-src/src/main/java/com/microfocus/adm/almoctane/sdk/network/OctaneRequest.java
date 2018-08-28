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
package com.microfocus.adm.almoctane.sdk.network;

import com.microfocus.adm.almoctane.sdk.entities.OctaneCollection;
import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.model.ModelParser;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract representation of a request
 */
public final class OctaneRequest {

    private final Logger logger = LoggerFactory.getLogger(OctaneRequest.class.getName());

    private final OctaneUrl octaneUrl;
    protected final OctaneHttpClient octaneHttpClient;

    // constant
    private static final String LOGGER_RESPONSE_JSON_FORMAT = "Response_Json: %s";

    public OctaneRequest(final OctaneHttpClient octaneHttpClient, final String urlDomain) {
        octaneUrl = new OctaneUrl(urlDomain);
        this.octaneHttpClient = octaneHttpClient;
    }

    public OctaneRequest(final OctaneHttpClient octaneHttpClient, final String urlDomain, final String entityId) {
        this(octaneHttpClient, urlDomain);
        octaneUrl.addPaths(entityId);
    }

    public final OctaneUrl getOctaneUrl() {
        return octaneUrl;
    }

    public final String getFinalRequestUrl() {
        return octaneUrl.toString();
    }

    /**
     * get entities result based on Http Request
     *
     * @param octaneHttpRequest - http request
     * @return entities ased on Http Request
     */
    public final OctaneCollection<EntityModel> getEntitiesResponse(OctaneHttpRequest octaneHttpRequest) {

        OctaneCollection<EntityModel> newEntityModels = null;

        OctaneHttpResponse response = octaneHttpClient.execute(octaneHttpRequest);

        String json = response.getContent();
        logger.debug(String.format(LOGGER_RESPONSE_JSON_FORMAT, json));

        if (response.isSuccessStatusCode() && json != null && !json.isEmpty()) {
            newEntityModels = ModelParser.getInstance().getEntities(json);

        }

        return newEntityModels;
    }

    /**
     * get entity result based on Http Request
     *
     * @param octaneHttpRequest the request object
     * @return EntityModel
     */
    public EntityModel getEntityResponse(OctaneHttpRequest octaneHttpRequest) {

        EntityModel newEntityModel = null;

        OctaneHttpResponse response = octaneHttpClient.execute(octaneHttpRequest);

        String json = response.getContent();
        logger.debug(String.format(LOGGER_RESPONSE_JSON_FORMAT, json));
        if (response.isSuccessStatusCode() && (json != null && !json.isEmpty())) {

            JSONTokener tokener = new JSONTokener(json);
            JSONObject jsonObj = new JSONObject(tokener);
            newEntityModel = ModelParser.getInstance().getEntityModel(jsonObj);
        }

        return newEntityModel;

    }

}