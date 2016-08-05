package com.hpe.adm.nga.sdk.unit_tests.entityListService;

import com.hpe.adm.nga.sdk.EntityList;
import com.hpe.adm.nga.sdk.EntityListService;
import com.hpe.adm.nga.sdk.NGA;
import com.hpe.adm.nga.sdk.model.EntityModel;
import com.hpe.adm.nga.sdk.unit_tests.common.CommonMethods;
import com.hpe.adm.nga.sdk.utils.CommonUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.Collection;

import static org.junit.Assert.fail;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
public class TestCreateEntities {
	private static NGA nga;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		nga = new NGA(CommonMethods.getRequestfactory(), CommonMethods.getDomain(), CommonMethods.getSharedSpace() , CommonMethods.getWorkSpace());

	}
	
	@Test
	public void testCreateEntity(){		
		final String jsonCreateString = "{\"data\":[{\"parent\":{\"id\":1002,\"type\":\"feature\"},\"phase\":{\"id\":1007,\"type\":\"phase\"},\"severity\":{\"id\":1004,\"type\":\"list_node\"},\"id\":1,\"name\":\"moris2\"}],\"total_count\":1}";
		
		EntityList defects = nga.entityList("defects");
		EntityListService.Create spiedCreateEntity = PowerMockito.spy(defects.create());
		EntityList spiedDefects = PowerMockito.spy(defects);
		EntityListService service = (EntityListService)Whitebox.getInternalState(spiedDefects, "entityListService");							
		
		try{	
			Collection<EntityModel> entityModelsIn = spiedDefects.testGetEntityModels(jsonCreateString);
			spiedCreateEntity.entities(entityModelsIn);
			
			Collection<EntityModel> internalModels = (Collection<EntityModel>) Whitebox.getInternalState(spiedCreateEntity, "entityModels");
			Method getEntitiesJSONObject = service.getClass().getDeclaredMethod("getEntitiesJSONObject", new Class[] {Collection.class});
			getEntitiesJSONObject.setAccessible(true);
			JSONObject jsonEntity = (JSONObject)getEntitiesJSONObject.invoke(service, internalModels);

			Collection<EntityModel> entityModelsOut = spiedDefects.testGetEntityModels(jsonEntity.toString());
			Assert.assertTrue(CommonUtils.isCollectionAInCollectionB(entityModelsIn, entityModelsOut));
		}
		catch(Exception ex){
			fail("Failed with exception: " + ex);
		}
		
	}
}