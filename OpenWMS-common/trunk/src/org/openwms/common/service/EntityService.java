/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import java.io.Serializable;
import java.util.List;

import org.openwms.common.domain.Location;

/**
 * A EntityService.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface EntityService<T extends Serializable> {
    
    List<T> findAll(Class<T> clazz);
    
    T save(Class<T> clazz, T entity);
    
    public String findAll2();//Remove
    
    public List<T> findAll();//Remove
    
    public List<String> findPojos();//Remove
    
    /**
	 * Removes a persistent entity.
     * 
     * @param clazz
     * @param entity
     */
	void remove(Class<T> clazz, T entity);

}