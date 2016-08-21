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
package org.openwms.tms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.openwms.tms.targets.Location;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * A CreateTODocumentation is a system test to test the public API of the component. It is marked as {@link Transactional} and to be
 * roll-backed ({@link Rollback}) after each test run. The reason for this is to open the transaction bracket around the controller to
 * rollback it afterwards.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class CreateTODocumentation extends DocumentationBase {

    public
    @Test
    void testCreateTO() throws Exception {
        MvcResult res = postTOAndValidate(createTO(), "to-create");
        assertThat(res.getResponse().getHeaderValue(HttpHeaders.LOCATION)).isNotNull();
    }

    public
    @Test
    void testCreateTOAndGet() throws Exception {
        CreateTransportOrderVO vo = createTO();
        MvcResult res = postTOAndValidate(vo, NOTLOGGED);

        String toLocation = (String) res.getResponse().getHeaderValue(HttpHeaders.LOCATION);
        mockMvc.perform(get(toLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("state", is(TransportOrder.State.STARTED.toString())))
                .andExpect(jsonPath("sourceLocation", is(INIT_LOC_STRING)))
                .andExpect(jsonPath("targetLocation", is(ERR_LOC_STRING)))
                .andDo(document("to-create-and-get"))
        ;
    }

    public
    @Test
    void testCreateTOUnknownTU() throws Exception {
        CreateTransportOrderVO vo = createTO();
        vo.setBarcode("UNKNOWN");

        given(commonGateway.getTransportUnit(vo.getBarcode())).willReturn(Optional.empty());

        mockMvc.perform(post(TMSConstants.ROOT_ENTITIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isNotFound())
                .andDo(document("to-create-uk-tu"))
        ;
    }

    public
    @Test
    void testCreateTOUnknownTarget() throws Exception {
        CreateTransportOrderVO vo = createTO();
        vo.setTarget("UNKNOWN");
        given(commonGateway.getLocation(vo.getTarget())).willReturn(Optional.empty());
        given(commonGateway.getLocationGroup(vo.getTarget())).willReturn(Optional.empty());

        mockMvc.perform(post(TMSConstants.ROOT_ENTITIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isNotFound())
                .andReturn()
        ;
    }

    public
    @Test
    void testCreateTOTargetNotAvailable() throws Exception {
        CreateTransportOrderVO vo = createTO();
        vo.setTarget(ERR_LOC_STRING);
        Location loc = new Location(ERR_LOC_STRING);
        loc.setIncomingActive(false);
        given(commonGateway.getLocation(vo.getTarget())).willReturn(Optional.of(loc));

        MvcResult res = mockMvc.perform(post(TMSConstants.ROOT_ENTITIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isCreated())
                .andReturn();

        String toLocation = (String) res.getResponse().getHeaderValue(HttpHeaders.LOCATION);
        mockMvc.perform(get(toLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("state", is(TransportOrder.State.INITIALIZED.toString())))
                .andExpect(jsonPath("sourceLocation", is(INIT_LOC_STRING)))
                .andExpect(jsonPath("targetLocation", is(ERR_LOC_STRING)))
                .andDo(document("to-create-and-get-target-na"))
        ;
    }

    public
    @Test
    void testCreateTOUnknownPriority() throws Exception {
        CreateTransportOrderVO vo = createTO();
        vo.setPriority("UNKNOWN");

        mockMvc.perform(post(TMSConstants.ROOT_ENTITIES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isNotFound())
                .andReturn()
        ;
    }
}
