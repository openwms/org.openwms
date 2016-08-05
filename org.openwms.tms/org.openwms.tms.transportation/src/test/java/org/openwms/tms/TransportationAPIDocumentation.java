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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.common.CommonGateway;
import org.openwms.common.TransportUnit;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.openwms.tms.targets.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * A TransportationAPIDocumentation is a system test to test the public API of the component. It is marked as {@link Transactional} and to
 * be roll-backed ({@link Rollback}) after each test run. The reason for this is to open the transaction bracket around the controller to
 * rollback it afterwards.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class TransportationAPIDocumentation {

    public static final String INIT_LOC = "INIT/0000/0000/0000/0000";
    public static final String ERR_LOC = "ERR_/0000/0000/0000/0000";
    @Autowired
    private ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(System.getProperty("documentation.dir", System.getProperty("project.build.directory") + "/generated-snippets"));
    @Autowired
    protected WebApplicationContext context;
    @MockBean
    private CommonGateway commonGateway;

    /**
     * Do something before each test method.
     *
     * @throws Exception Any error
     */
    @Before
    public void setUp() throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8", true);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation).uris()
                        .withPort(8888))
                .addFilters(filter)
                .build();
    }

    public
    @Test
    void testCreateTO() throws Exception {
        MvcResult res = postTOAndValidate(createTO());
        assertThat(res.getResponse().getHeaderValue(HttpHeaders.LOCATION)).isNotNull();
    }

    public
    @Test
    void testCreateTOAndGet() throws Exception {
        CreateTransportOrderVO vo = createTO();
        MvcResult res = postTOAndValidate(vo);

        String toLocation = (String) res.getResponse().getHeaderValue(HttpHeaders.LOCATION);
        mockMvc.perform(get(toLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("state", is(TransportOrder.State.STARTED.toString())))
                .andExpect(jsonPath("sourceLocation", is(INIT_LOC)))
                .andExpect(jsonPath("targetLocation", is(ERR_LOC)))
                .andDo(document("to-create-and-get"))
        ;
    }

    public
    @Test
    void testCreateTOUnknownTU() throws Exception {
        CreateTransportOrderVO vo = createTO();
        vo.setBarcode("UNKNOWN");

        given(commonGateway.getTransportUnit(vo.getBarcode())).willReturn(Optional.empty());

        mockMvc.perform(post("/transportOrders")
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

        mockMvc.perform(post("/transportOrders")
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
        vo.setTarget(ERR_LOC);
        Location loc = new Location(ERR_LOC);
        loc.setIncomingActive(false);
        given(commonGateway.getLocation(vo.getTarget())).willReturn(Optional.of(loc));

        MvcResult res = mockMvc.perform(post("/transportOrders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isCreated())
                .andReturn();

        String toLocation = (String) res.getResponse().getHeaderValue(HttpHeaders.LOCATION);
        mockMvc.perform(get(toLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("state", is(TransportOrder.State.INITIALIZED.toString())))
                .andExpect(jsonPath("sourceLocation", is(INIT_LOC)))
                .andExpect(jsonPath("targetLocation", is(ERR_LOC)))
                .andDo(document("to-create-and-get-target-na"))
        ;
    }

    public
    @Test
    void testCreateTOUnknownPriority() throws Exception {
        CreateTransportOrderVO vo = createTO();
        vo.setPriority("UNKNOWN");

        mockMvc.perform(post("/transportOrders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isNotFound())
                .andReturn()
        ;
    }

    private CreateTransportOrderVO createTO() {
        CreateTransportOrderVO vo = new CreateTransportOrderVO();
        vo.setBarcode("4711");
        vo.setPriority(PriorityLevel.HIGHEST.toString());
        vo.setTarget(ERR_LOC);

        Location actualLocation = new Location(INIT_LOC);
        Location errorLocation = new Location(ERR_LOC);
        TransportUnit tu = new TransportUnit(vo.getBarcode(), actualLocation, vo.getTarget());

        given(commonGateway.getTransportUnit(vo.getBarcode())).willReturn(Optional.of(tu));
        given(commonGateway.getLocation(vo.getTarget())).willReturn(Optional.of(errorLocation));
        given(commonGateway.getLocationGroup(vo.getTarget())).willReturn(Optional.empty());
        return vo;
    }

    private MvcResult postTOAndValidate(CreateTransportOrderVO vo) throws Exception {
        return mockMvc.perform(post("/transportOrders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isCreated())
                .andDo(document("to-create"))
                .andReturn();
    }
}
