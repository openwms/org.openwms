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
import org.openwms.common.TransportUnit;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

/**
 * A ChangeTUDocumentation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class ChangeTUDocumentation extends DocumentationBase {

    public
    @Test
    void testTUChange() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setBarcode(KNOWN);
        given(commonGateway.getTransportUnit(KNOWN)).willReturn(Optional.of(new TransportUnit(KNOWN, INIT_LOC, ERR_LOC_STRING)));

        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
                )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-tu-change"))
        ;
    }

    public
    @Test
    void testTUChangeUnknownTU() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setBarcode(UNKNOWN);
//        given(commonGateway.updateTransportUnit(new TransportUnit(UNKNOWN, ERR_LOC, ERR_LOC_STRING))).willThrow(new NotFoundException("", Messages.NOT_FOUND, UNKNOWN));
// TODO [openwms]: 10/08/16  
        // test ...
        MvcResult res = mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
                )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-tu-unknown"))
                .andReturn()
        ;
     //   assertThat(res.getResponse().getContentAsString().contains("[null]")).isTrue();
    }

    public
    @Test
    void testTUChangeTUWithNUll() throws Exception {
        // setup ...
        CreateTransportOrderVO vo = createTO();
        postTOAndValidate(vo, NOTLOGGED);
        vo.setBarcode(null);
// TODO [openwms]: 10/08/16
        // test ...
        mockMvc.perform(
                patch(Constants.ROOT_ENTITIES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo))
        )
                .andExpect(status().isNoContent())
                .andDo(document("to-patch-tu-null"))
        ;
    }
}
