<%--
  GRANITE DATA SERVICES
  Copyright (C) 2007-2008 ADEQUATE SYSTEMS SARL

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
  for more details.

  You should have received a copy of the GNU General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.

  @author Franck WOLFF
--%>/*
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
package ${jClass.as3Type.packageName} {<%

    ///////////////////////////////////////////////////////////////////////////
    // Write Import Statements.

    if (jClass.hasInterfaces()) {
        Set as3Imports = new TreeSet();

        for (jProperty in jClass.interfacesProperties) {
            if (jProperty.as3Type.hasPackage() && jProperty.as3Type.packageName != jClass.as3Type.packageName && jProperty.as3Type.packageName != "java.io.Serializable")
                as3Imports.add(jProperty.as3Type.qualifiedName);
        }

        if (as3Imports.size() > 0) {%>
<%
        }
        for (as3Import in as3Imports) {%>
    import ${as3Import};<%
        }
    }%>

    [Bindable]
    [RemoteClass(alias="${jClass.qualifiedName}")]
    public class ${jClass.as3Type.name} extends ${jClass.as3Type.name}Base {<%

    ///////////////////////////////////////////////////////////////////////////
    // (Re)Write Public Getters/Setters for Implemented Interfaces.

    if (jClass.hasInterfaces()) {
        for (jProperty in jClass.interfacesProperties) {
            if (jProperty.readable || jProperty.writable) {%>
<%
                if (jProperty.writable) {%>
        override public function set ${jProperty.name}(value:${jProperty.as3Type.name}):void {
            // TODO: Gas3 empty generated setter.
        }<%
                }
                if (jProperty.readable) {%>
        override public function get ${jProperty.name}():${jProperty.as3Type.name} {
            // TODO: Gas3 default generated getter.
            return ${jProperty.as3Type.nullValue};
        }<%
                }
            }
        }
    }%>
    }
}