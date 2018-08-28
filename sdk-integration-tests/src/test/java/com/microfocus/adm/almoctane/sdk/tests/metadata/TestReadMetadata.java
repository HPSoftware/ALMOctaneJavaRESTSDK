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
package com.microfocus.adm.almoctane.sdk.tests.metadata;

import com.microfocus.adm.almoctane.sdk.metadata.EntityMetadata;
import com.microfocus.adm.almoctane.sdk.metadata.FieldMetadata;
import com.microfocus.adm.almoctane.sdk.metadata.Metadata;
import com.microfocus.adm.almoctane.sdk.tests.base.TestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 *
 * Created by Dmitry Zavyalov on 08/05/2016.
 */
public class TestReadMetadata extends TestBase {

    public TestReadMetadata() {
        entityName = "product_areas";
    }

    @Test
    public void testReadMetadataEntities() throws Exception {
        Metadata metadata = octane.metadata();
        Collection<EntityMetadata> entityMetadata = metadata.entities().execute();
        Collection<EntityMetadata> entityMetadataTwoEntities = metadata.entities("defects").execute();
    }

    @Test
    public void testReadMetadataFields() throws Exception {
        Metadata metadata = octane.metadata();
        Collection<FieldMetadata> fieldMetadata = metadata.fields().execute();

        int referenceTypeDataIsEmpty = 0;
        for (FieldMetadata fieldMetadataFields : fieldMetadata) {
            if (fieldMetadataFields.getFieldType().equals(FieldMetadata.FieldType.Reference.toString().toLowerCase()) && fieldMetadataFields.getFieldTypedata() == null) {
                referenceTypeDataIsEmpty++;
            }
        }

        String message = "[field_type_data] is null for field type [reference] " + referenceTypeDataIsEmpty + " times in \"Metadata.fields().execute()\"";
        Assert.assertFalse(message, referenceTypeDataIsEmpty > 0);
    }
}