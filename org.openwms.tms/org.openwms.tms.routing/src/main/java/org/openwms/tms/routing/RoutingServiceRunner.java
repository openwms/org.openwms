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
package org.openwms.tms.routing;

import org.ameba.app.SolutionApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * A RoutingServiceRunner.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@SpringBootApplication(scanBasePackageClasses = SolutionApp.class, scanBasePackages = "org.openwms")
public class RoutingServiceRunner {

    /**
     * Boot up!
     *
     * @param args Some args
     */
    public static void main(String[] args) {
        SpringApplication.run(RoutingServiceRunner.class, args);
    }

    @Bean
    public CommandLineRunner init(final ControlProgramRepository repo, final RouteRepository routeRepository){

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                Route route1 = routeRepository.save(new Route("R001"));
                repo.save(new ControlProgram(route1,"ACT001","EXT_/0000/0000/0000/0000",null, "REQ_", "CP001", "Start process CP001 when REQ_ on EXT_ location"));
                repo.save(new ControlProgram(route1,"ACT001",null, "ERRORPLACE","REQ_", "CP002", "Start process CP001 when REQ_ on EXT_ location"));
                repo.save(new ControlProgram(route1,"ACT001",null, "ROOT","REQ_", "CP002", "Start process CP001 when REQ_ on EXT_ location"));
            }
        };

    }

}
