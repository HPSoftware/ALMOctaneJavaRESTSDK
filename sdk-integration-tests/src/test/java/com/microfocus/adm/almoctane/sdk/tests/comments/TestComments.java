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
package com.microfocus.adm.almoctane.sdk.tests.comments;

import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.model.FieldModel;
import com.microfocus.adm.almoctane.sdk.model.ReferenceFieldModel;
import com.microfocus.adm.almoctane.sdk.tests.base.TestBase;
import com.microfocus.adm.almoctane.sdk.utils.CommonUtils;
import com.microfocus.adm.almoctane.sdk.utils.generator.DataGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by Guy Guetta on 02/05/2016.
 */
public class TestComments extends TestBase {
    public TestComments() {
        entityName = "product_areas";
    }

    @Test
    public void testCreateCommentForDefect() throws Exception {
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModel(octane, "defects");
        Collection<EntityModel> entityModels = octane.entityList("defects").create().entities(generatedEntity).execute();

        Collection<EntityModel> expectedComments = createComments("owner_work_item", entityModels);

        Collection<EntityModel> actualComments = octane.entityList("comments").get().execute();

        Assert.assertTrue(CommonUtils.isCollectionAInCollectionB(expectedComments, actualComments));
    }

    private Collection<EntityModel> createComments(String fieldEntityType, Collection<EntityModel> entityModels) throws Exception {
        EntityModel entity = entityModels.iterator().next();

        Collection<EntityModel> users = octane.entityList("workspace_users").get().execute();

        EntityModel user = users.iterator().next();
        Set<FieldModel> fields = new HashSet<>();
        fields.add(new ReferenceFieldModel("author", user));
        fields.add(new ReferenceFieldModel(fieldEntityType, entity));

        Collection<EntityModel> comments = new ArrayList<>();
        comments.add(new EntityModel(fields));

        octane.entityList("comments").create().entities(comments).execute();

        return comments;
    }
}