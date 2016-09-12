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

import javax.persistence.EntityManager;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.openwms.common.TransportUnit;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**
 * A ChangeStateDocumentation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ChangeStateDocumentation extends DocumentationBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeStateDocumentation.class);

    @Autowired
    private EntityManager em;

    public
    @Test
    void turnBackState() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrderState.INITIALIZED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("messageKey", is(TMSMessageCodes.TO_STATE_CHANGE_BACKWARDS_NOT_ALLOWED)))
                .andDo(document("to-patch-state-change-back"))
        ;
    }

    /* ----------------- INITIALIZED -------------------*/
    @Ignore("Test runs on OSX and Jenkins@Linux but not on TravisCI. Needs further investigation")
    public
    @Test
    void createAnNewOneWhenOneIsAlreadyStarted() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        // create a second one that shall wait in INITIALIZED
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrderState.STARTED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        LOGGER.debug("Calling API with:" + vo2);
        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
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
    void cancellingAnInitializedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrderState.CANCELED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo2))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-change-start-no-allowed-one-exists"))
        ;
    }

    public
    @Test
    void settingAnInitializedOneOnFailure() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrderState.ONFAILURE.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo2))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-initialize-to-failure"))
        ;
    }

    public
    @Test
    void finishingAnInitializedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        CreateTransportOrderVO vo2 = createTO();
        postTOAndValidate(vo2, NOTLOGGED);
        vo2.setState(TransportOrderState.FINISHED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo2))
        )
                .andExpect(status().isBadRequest())
                .andDo(document("to-patch-state-finish-an-initialized"))
        ;
    }

    /* ----------------- STARTED -------------------*/
    public
    @Test
    void startingAnStartedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrderState.STARTED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-change"))
        ;
    }

    public
    @Test
    void cancellingAnStartedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrderState.CANCELED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-cancel-a-started"))
        ;
    }

    public
    @Test
    void settingAnStartedOneOnFailure() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrderState.ONFAILURE.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-onfailure-a-started"))
        ;
    }

    public
    @Test
    void finishingAnStartedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setState(TransportOrderState.FINISHED.toString());
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-state-finish-a-started"))
        ;
    }

    /* ----------------- FINISHED -------------------*/
    public
    @Test
    void changingAnFinishedOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));
        vo.setState(TransportOrderState.FINISHED.toString());
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
        ;

        // test ...
        vo.setState(TransportOrderState.CANCELED.toString());
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isBadRequest())
                .andDo(document("to-patch-state-change-a-finished"))
        ;
    }

    /* ----------------- ONFAILURE -------------------*/
    public
    @Test
    void changingAnOnFailureOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));
        vo.setState(TransportOrderState.ONFAILURE.toString());
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
        ;

        // test ...
        vo.setState(TransportOrderState.CANCELED.toString());
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isBadRequest())
                .andDo(document("to-patch-state-change-an-onfailure"))
        ;
    }

    /* ----------------- CANCELED -------------------*/
    public
    @Test
    void changingAnCanceledOne() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));
        vo.setState(TransportOrderState.CANCELED.toString());
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
        ;

        // test ...
        vo.setState(TransportOrderState.ONFAILURE.toString());
        mockMvc.perform(
                patch(TMSConstants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isBadRequest())
                .andDo(document("to-patch-state-change-a-canceled"))
        ;
    }
}
