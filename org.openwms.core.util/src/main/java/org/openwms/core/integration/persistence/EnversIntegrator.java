/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.integration.persistence;

import org.hibernate.HibernateException;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversListenerDuplicationStrategy;
import org.hibernate.envers.event.spi.EnversPostCollectionRecreateEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPostInsertEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPostUpdateEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPreCollectionRemoveEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPreCollectionUpdateEventListenerImpl;
import org.hibernate.envers.event.spi.EnversPreUpdateEventListenerImpl;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A EnversIntegrator.
 *
 * @author Heiko Scherrer
 */
public class EnversIntegrator implements Integrator {

    private static final Logger log = LoggerFactory.getLogger( EnversIntegrator.class );

    public static final String AUTO_REGISTER = "hibernate.envers.autoRegisterListeners";

    /**
     * {@inheritDoc}
     */
    @Deprecated
    @Override
    public void integrate(
            Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        final EnversService enversService = serviceRegistry.getService( EnversService.class );

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Opt-out of registration if EnversService is disabled
        if ( !enversService.isEnabled() ) {
            log.debug( "Skipping Envers listener registrations : EnversService disabled" );
            return;
        }

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Verify that the EnversService is fully initialized and ready to go.
        if ( !enversService.isInitialized() ) {
            throw new HibernateException(
                    "Expecting EnversService to have been initialized prior to call to EnversIntegrator#integrate"
            );
        }

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Opt-out of registration if no audited entities found
        if ( !enversService.getEntitiesConfigurations().hasAuditedEntities() ) {
            log.debug( "Skipping Envers listener registrations : No audited entities found" );
            return;
        }

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Do the registrations
        final EventListenerRegistry listenerRegistry = serviceRegistry.getService( EventListenerRegistry.class );
        listenerRegistry.addDuplicationStrategy( EnversListenerDuplicationStrategy.INSTANCE );

        if ( enversService.getEntitiesConfigurations().hasAuditedEntities() ) {
            listenerRegistry.appendListeners(
                    EventType.POST_DELETE,
                    new CustomPostDeleteEventListener( enversService )
            );
            listenerRegistry.appendListeners(
                    EventType.POST_INSERT,
                    new EnversPostInsertEventListenerImpl( enversService )
            );
            listenerRegistry.appendListeners(
                    EventType.PRE_UPDATE,
                    new EnversPreUpdateEventListenerImpl( enversService )
            );
            listenerRegistry.appendListeners(
                    EventType.POST_UPDATE,
                    new EnversPostUpdateEventListenerImpl( enversService )
            );
            listenerRegistry.appendListeners(
                    EventType.POST_COLLECTION_RECREATE,
                    new EnversPostCollectionRecreateEventListenerImpl( enversService )
            );
            listenerRegistry.appendListeners(
                    EventType.PRE_COLLECTION_REMOVE,
                    new EnversPreCollectionRemoveEventListenerImpl( enversService )
            );
            listenerRegistry.appendListeners(
                    EventType.PRE_COLLECTION_UPDATE,
                    new EnversPreCollectionUpdateEventListenerImpl( enversService )
            );
        }
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        // nothing to do
    }
}
