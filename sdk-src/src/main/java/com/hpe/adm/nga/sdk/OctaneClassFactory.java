package com.hpe.adm.nga.sdk;

import com.hpe.adm.nga.sdk.entities.EntityList;
import com.hpe.adm.nga.sdk.network.OctaneHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory for the {@link Octane} class, can implemented by clients to modify the behaviour of the sdk
 */
public interface OctaneClassFactory {

    /**
     * Sys param name used by {@link #getInstance()}
     */
    String OCTANE_CLASS_FACTORY_CLASS_NAME = "octaneClassFactoryClassName";
    String OCTANE_HTTP_CLIENT_CLASS_NAME = "octaneHttpClientClassName";

    /**
     * Create an instance of the OctaneHttpClient for the {@link Octane} to use
     * @param urlDomain for the {@link OctaneHttpClient} constructor
     * @return OctaneHttpClient implementation with urlDomain set
     */
    OctaneHttpClient getOctaneHttpClient(String urlDomain);

    /**
     * Get the implementation of the {@link EntityList} used by the {@link Octane} object
     * @param octaneHttpClient used to create the
     * @param baseDomain for the entity list
     * @param entityName
     * @return
     */
    EntityList getEntityList(OctaneHttpClient octaneHttpClient, String baseDomain, String entityName);

    /**
     * Get the implementation implementation of {@link OctaneClassFactory}, can be modified by changing the OCTANE_CLASS_FACTORY_CLASS_NAME system param
     * @return
     */
    static OctaneClassFactory getInstance() {
        Logger logger = LogManager.getLogger(Octane.class.getName());
        String octaneClassFactoryClassName = System.getProperty(OctaneClassFactory.OCTANE_CLASS_FACTORY_CLASS_NAME);

        if (octaneClassFactoryClassName != null) {
            logger.info("Creating OctaneClassFactory using implementation {}", octaneClassFactoryClassName);
            //Use reflection to instantiate the class
            Class<OctaneClassFactory> clazz;
            try {
                clazz = (Class<OctaneClassFactory>) Class.forName(octaneClassFactoryClassName);
            } catch (ClassNotFoundException e) {
                logger.error(e);
                throw new RuntimeException("Failed to find class with name: " + octaneClassFactoryClassName, e);
            }

            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error(e);
                throw new RuntimeException("Failed to instantiate class " + octaneClassFactoryClassName, e);
            }
        } else {
            return DefaultOctaneClassFactory.getInstance();
        }
    }

}