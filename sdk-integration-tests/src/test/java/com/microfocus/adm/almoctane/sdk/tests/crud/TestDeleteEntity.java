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
package com.microfocus.adm.almoctane.sdk.tests.crud;

import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.query.Query;
import com.microfocus.adm.almoctane.sdk.tests.base.TestBase;
import com.microfocus.adm.almoctane.sdk.utils.CommonUtils;
import com.microfocus.adm.almoctane.sdk.utils.QueryUtils;
import com.microfocus.adm.almoctane.sdk.utils.generator.DataGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 *
 * Created by Guy Guetta on 25/04/2016.
 */
public class TestDeleteEntity extends TestBase {
    public TestDeleteEntity() {
        entityName = "releases";
    }

    @Test
    public void testDeleteEntityById() throws Exception{
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModel(octane, entityName);
        Collection<EntityModel> entityModels = entityList.create().entities(generatedEntity).execute();

        EntityModel entityModel = entityModels.iterator().next();
        String entityId = CommonUtils.getIdFromEntityModel(entityModel);

        entityList.at(entityId).delete().execute();

        Collection<EntityModel> getEntity = entityList.get().execute();

        List<String> entityIds = CommonUtils.getIdFromEntityModelCollection(getEntity);

        Assert.assertFalse(entityIds.contains(entityId));
    }

    @Test
    public void testDeleteEntitiesByQuery() throws Exception{
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModelCollection(octane, entityName);
        Collection<EntityModel> entityModels = entityList.create().entities(generatedEntity).execute();
        List<String> entityIds = CommonUtils.getIdFromEntityModelCollection(entityModels);

        Query query = QueryUtils.getQueryForIds(entityIds);

        entityList.delete().query(query).execute();

        Collection<EntityModel> getEntity = entityList.get().execute();

        List<String> actualEntityIds = CommonUtils.getIdFromEntityModelCollection(getEntity);

        //check there are no common ids
        actualEntityIds.retainAll(entityIds);

        Assert.assertTrue(actualEntityIds.isEmpty());
    }
}