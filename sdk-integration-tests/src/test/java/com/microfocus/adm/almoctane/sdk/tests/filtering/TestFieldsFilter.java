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
package com.microfocus.adm.almoctane.sdk.tests.filtering;

import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.model.FieldModel;
import com.microfocus.adm.almoctane.sdk.tests.base.TestBase;
import com.microfocus.adm.almoctane.sdk.utils.CommonUtils;
import com.microfocus.adm.almoctane.sdk.utils.generator.DataGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 *
 * Created by Guy Guetta on 25/04/2016.
 */
public class TestFieldsFilter extends TestBase {
    public TestFieldsFilter() {
        entityName = "releases";
    }

    @Test
    public void testFieldsFilterWithIdOneField() throws Exception {

        List<String> filterFields = Arrays.asList("attachments");
        Set<FieldModel> fields = new HashSet<>();
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModel(octane, entityName, fields);
        Collection<EntityModel> entityModels = entityList.create().entities(generatedEntity).execute();
        EntityModel entityModel = entityModels.iterator().next();
        String entityId = CommonUtils.getIdFromEntityModel(entityModel);

        EntityModel getEntity = entityList.at(entityId).get().addFields("attachments").execute();

        Assert.assertTrue(isOnlyRequestedFieldsExistInEntity(getEntity, filterFields));
    }

    @Test
    public void testFieldsFilterWithIdMultipleFields() throws Exception {

        List<String> filterFields = Arrays.asList("attachments", "creation_time", "has_attachments");
        Set<FieldModel> fields = new HashSet<>();
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModel(octane, entityName, fields);
        Collection<EntityModel> entityModels = entityList.create().entities(generatedEntity).execute();
        EntityModel entityModel = entityModels.iterator().next();
        String entityId = CommonUtils.getIdFromEntityModel(entityModel);

        EntityModel getEntity = entityList.at(entityId).get().addFields("attachments", "creation_time", "has_attachments").execute();

        Assert.assertTrue(isOnlyRequestedFieldsExistInEntity(getEntity, filterFields));
    }

    @Test
    public void testFieldsFilterMultipleFields() throws Exception {

        List<String> filterFields = Arrays.asList("done_work", "description", "expected_velocity");
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModelCollection(octane, entityName);
        entityList.create().entities(generatedEntity).execute();

        Collection<EntityModel> getEntity = entityList.get().addFields("done_work", "description", "expected_velocity").execute();

        Assert.assertTrue(getIdFromEntityModelCollection(getEntity, filterFields));
    }

    public static boolean isOnlyRequestedFieldsExistInEntity(EntityModel entityModel, List<String> fields) {
        Set<FieldModel> fieldModelSet = entityModel.getValues();
        for (FieldModel fm : fieldModelSet) {
            if (!fm.getName().equals("workspace_id") && !fm.getName().equals("id") && !fm.getName().equals("type") && !fields.contains(fm.getName())) {
                return false;
            }
        }
        return true;
    }


    public static boolean getIdFromEntityModelCollection(Collection<EntityModel> entityModels, List<String> fields) {
        ArrayList<Boolean> existList = new ArrayList<>();
        entityModels.forEach(entityModel -> existList.add(isOnlyRequestedFieldsExistInEntity(entityModel, fields)));
        return !existList.contains(false);
    }
}