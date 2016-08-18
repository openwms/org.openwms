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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.openwms.common.TransportUnit;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.springframework.http.MediaType;

/**
 * A ChangeStateDocumentation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ChangeStateDocumentation extends DocumentationBase {

    public
    @Test
    void whenAlreadyStarted() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrder.State.STARTED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-change"))
        ;
    }

    public
    @Test
    void turnBackState() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrder.State.INITIALIZED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageKey", is(TMSMessageCodes.TO_STATE_CHANGE_BACKWARDS_NOT_ALLOWED)))
                .andDo(document("to-patch-state-change-back"))
        ;
    }

    public
    @Test
    void oneIsAlreadyStarted() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrder.State.STARTED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo2))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageKey", is(TMSMessageCodes.START_TO_NOT_ALLOWED_ALREADY_STARTED_ONE)))
                .andDo(document("to-patch-state-change-start-no-allowed-one-exists"))
        ;
    }

    public
    @Test
    void cancellingAnStartedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrder.State.CANCELED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-cancel-to"))
        ;
    }

    public
    @Test
    void cancellingAnInitializedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrder.State.CANCELED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo2))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-change-start-no-allowed-one-exists"))
        ;
    }

    public
    @Test
    void settingAnInitializedOneToFailure() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrder.State.ONFAILURE.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo2))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-initialize-to-failure"))
        ;
    }
}
