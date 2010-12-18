/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms.util.event;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;

/**
 * A TransportServiceEvent.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 */
public class TransportServiceEvent extends ApplicationEvent implements Serializable {

    public enum TYPE {

        TRANSPORT_CREATED,

        TRANSPORT_INTERRUPTED,

        TRANSPORT_ONFAILURE,

        TRANSPORT_CANCELED,

        TRANSPORT_FINISHED
    }

    private TYPE type;

    private static final long serialVersionUID = -2454660395280026756L;

    /**
     * Create a new RootApplicationEvent.
     * 
     * @param source
     */
    public TransportServiceEvent(Object source) {
        super(source);
    }

    public TransportServiceEvent(Object source, TYPE type) {
        super(source);
        this.type = type;
    }

    /**
     * FIXME [russelltina] Comment this
     * 
     * @return
     */
    public TYPE getType() {
        return type;
    }
}
