/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.uaa;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ameba.annotation.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A RoleServiceImpl is a Spring supported transactional implementation of a general {@link RoleService}. Using Spring 2 annotation support
 * autowires collaborators, therefore XML configuration becomes obsolete. This class is marked with Springs {@link Service} annotation to
 * benefit from Springs exception translation intercepter. Traditional CRUD operations are delegated to a {@link RoleRepository} instance.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @see RoleRepository
 * @since 0.1
 */
@TxService
class RoleServiceImpl implements RoleService {

    private RoleRepository repository;

    @Autowired
    RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Return type ArrayList
     */
    @Override
    public Collection<Role> findAll() {
        List<Role> roles = repository.findAll();
        return roles == null ? Collections.emptyList() : roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role create(@NotNull Role role) {
        return repository.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}