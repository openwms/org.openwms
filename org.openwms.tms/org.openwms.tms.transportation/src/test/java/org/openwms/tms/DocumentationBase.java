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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openwms.common.CommonGateway;
import org.openwms.common.TransportUnit;
import org.openwms.tms.api.CreateTransportOrderVO;
import org.openwms.tms.targets.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * A DocumentationBase.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public abstract class DocumentationBase {

    @Autowired
    @Qualifier(Constants.BEAN_NAME_OBJECTMAPPER)
    protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @MockBean
    protected CommonGateway commonGateway;
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(System.getProperty("documentation.dir", System.getProperty("project.build.directory") + "/generated-snippets"));
    public static final String NOTLOGGED = "--";
    public static final String INIT_LOC_STRING = "INIT/0000/0000/0000/0000";
    public static final Location INIT_LOC = new Location(INIT_LOC_STRING);
    public static final String ERR_LOC_STRING = "ERR_/0000/0000/0000/0000";
    public static final Location ERR_LOC = new Location(ERR_LOC_STRING);
    public static final String KNOWN = "KNOWN";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String BC_4711 = "4711";

    /**
     * Do something before each test method.
     *
     * @throws Exception Any error
     */
    @Before
    public void setUp() throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8", true);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation).uris()
                        .withPort(8888))
                .addFilters(filter)
                .build();
    }

    protected CreateTransportOrderVO createTO() {
        CreateTransportOrderVO vo = new CreateTransportOrderVO();
        vo.setPriority(PriorityLevel.HIGHEST.toString());
        vo.setBarcode(BC_4711);
        vo.setTarget(ERR_LOC_STRING);

        Location actualLocation = new Location(INIT_LOC_STRING);
        Location errorLocation = new Location(ERR_LOC_STRING);
        TransportUnit tu = new TransportUnit(vo.getBarcode(), actualLocation, vo.getTarget());

        given(commonGateway.getTransportUnit(vo.getBarcode())).willReturn(Optional.of(tu));
        given(commonGateway.getLocation(vo.getTarget())).willReturn(Optional.of(errorLocation));
        given(commonGateway.getLocationGroup(vo.getTarget())).willReturn(Optional.empty());
        return vo;
    }

    protected MvcResult postTOAndValidate(CreateTransportOrderVO vo, String outputFile) throws Exception {
        MvcResult res = mockMvc.perform(post("/transportOrders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andExpect(status().isCreated())
                .andDo(document(outputFile))
                .andReturn();

        String toLocation = (String) res.getResponse().getHeaderValue(HttpHeaders.LOCATION);
        toLocation = toLocation.substring(0, toLocation.length()-1);
        vo.setpKey(toLocation.substring(toLocation.lastIndexOf("/")+1));
        return res;
    }
}
