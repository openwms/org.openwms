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

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * A RedirectTODocumentation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class RedirectTODocumentation extends DocumentationBase {


    public
    @Test
    void testRedirectToUnknownLocationButGroup() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setTarget(UNKNOWN);
        given(commonGateway.getLocationGroup(UNKNOWN)).willReturn(Optional.empty());
        given(commonGateway.getLocation(UNKNOWN)).willReturn(Optional.of(INIT_LOC));

        // test ...
        sendPatch(vo, status().isNoContent(), "to-patch-target-unknown-loc");
    }

    public
    @Test
    void testRedirectToUnknownLocationGroupButLoc() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setTarget(UNKNOWN);
        given(commonGateway.getLocationGroup(UNKNOWN)).willReturn(Optional.of(ERR_LOCGRB));
        given(commonGateway.getLocation(UNKNOWN)).willReturn(Optional.empty());

        // test ...
        sendPatch(vo, status().isNoContent(), "to-patch-target-unknown-locgb");
    }

    public
    @Test
    void testRedirectToUnknownTargets() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setTarget(UNKNOWN);
        given(commonGateway.getLocationGroup(UNKNOWN)).willReturn(Optional.empty());
        given(commonGateway.getLocation(UNKNOWN)).willReturn(Optional.empty());

        // test ...
        sendPatch(vo, status().isConflict(), "to-patch-target-unknown");
    }

    public
    @Test
    void testRedirectToKnownTargets() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setTarget(UNKNOWN);
        given(commonGateway.getLocationGroup(UNKNOWN)).willReturn(Optional.of(ERR_LOCGRB));
        given(commonGateway.getLocation(UNKNOWN)).willReturn(Optional.of(INIT_LOC));

        // test ...
        sendPatch(vo, status().isNoContent(), "to-patch-target-known-target");
    }

    public
    @Test
    void testRedirectToKnownTargets2() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setTarget(UNKNOWN);
        given(commonGateway.getLocation(UNKNOWN)).willReturn(Optional.of(INIT_LOC));
        given(commonGateway.getLocationGroup(UNKNOWN)).willReturn(Optional.of(ERR_LOCGRB));
        sendPatch(vo, status().isNoContent() , "to-patch-target-known-target2");
    }

    private void sendPatch(CreateTransportOrderVO vo, ResultMatcher rm, String output) throws Exception {
        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(rm)
                .andDo(document(output))
        ;
    }
}
