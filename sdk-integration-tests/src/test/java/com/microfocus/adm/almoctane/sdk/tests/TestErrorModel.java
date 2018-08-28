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

package com.microfocus.adm.almoctane.sdk.tests;

import com.microfocus.adm.almoctane.sdk.exception.OctaneException;
import com.microfocus.adm.almoctane.sdk.model.EntityModel;
import com.microfocus.adm.almoctane.sdk.model.StringFieldModel;
import com.microfocus.adm.almoctane.sdk.tests.base.TestBase;
import org.junit.Test;

public class TestErrorModel extends TestBase {

    @Test
    public void testpls() throws Exception {
        EntityModel em = new EntityModel();
        em.setValue(new StringFieldModel("blocked_reason", "blockage!"));

        try {
            octane.entityList("defects").at("1002").update().entity(em).execute();
        } catch (OctaneException ex) {
            System.out.println(ex);
        }
    }
}
