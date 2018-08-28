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
import com.microfocus.adm.almoctane.sdk.query.Query;
import com.microfocus.adm.almoctane.sdk.query.QueryMethod;
import com.microfocus.adm.almoctane.sdk.tests.base.TestBase;
import com.microfocus.adm.almoctane.sdk.utils.CommonUtils;
import com.microfocus.adm.almoctane.sdk.utils.generator.DataGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by Guy Guetta on 25/04/2016.
 */
public class TestSupportFiltering extends TestBase {

    public TestSupportFiltering() {
        entityName = "releases";
    }

    @Test
    public void supportEqual() throws Exception {
        testFiltering("EQ");
    }

    @Test
    public void supportLessThan() throws Exception {
        testFiltering("LT");
    }

    @Test
    public void supportGreaterThan() throws Exception {
        testFiltering("GT");
    }

    @Test
    public void supportLessEqual() throws Exception {
        testFiltering("LE");
    }

    @Test
    public void supportGreaterEqual() throws Exception {
        testFiltering("GE");
    }

    private void testFiltering(String logicalOperation) throws Exception {
        Set<FieldModel> fields = new HashSet<>();
        Collection<EntityModel> generatedEntity = DataGenerator.generateEntityModel(octane, entityName, fields);
        Collection<EntityModel> entityModels = DataGenerator.getAllDataForEntities(entityList.create().entities(generatedEntity).execute(),octane,entityName);
        EntityModel entityModel = entityModels.iterator().next();
        String entityName = CommonUtils.getValueFromEntityModel(entityModel, "name");

        Query query = getQuery(entityName, logicalOperation);

        Collection<EntityModel> getEntity = entityList.get().query(query).execute();

        Assert.assertTrue(CommonUtils.isCollectionAInCollectionB(generatedEntity, getEntity));
    }

    private Query getQuery(String entityName, String logicalOperation) {
        switch (logicalOperation) {
            case "EQ":
                return Query.statement("name", QueryMethod.EqualTo, entityName).build();
            case "LT":
                return Query.statement("name", QueryMethod.LessThan, "z_" + entityName).build();
            case "GT":
                return Query.statement("name", QueryMethod.GreaterThan, "a_" + entityName).build();
            case "LE":
                return Query.statement("name", QueryMethod.LessThanOrEqualTo, entityName).build();
            case "GE":
                return Query.statement("name", QueryMethod.GreaterThanOrEqualTo, entityName).build();
            default:
                return null;
        }
    }
}