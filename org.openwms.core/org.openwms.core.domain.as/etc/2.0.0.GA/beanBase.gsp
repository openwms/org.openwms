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
--%><%
    Set as3Imports = new TreeSet();

    as3Imports.add("flash.utils.IDataInput");
    as3Imports.add("flash.utils.IDataOutput");

    if (!jClass.hasSuperclass())
        as3Imports.add("flash.utils.IExternalizable");

    if (jClass.hasEnumProperty())
        as3Imports.add("org.granite.util.Enum");

    for (jImport in jClass.imports) {
        if (jImport.as3Type.hasPackage() && jImport.as3Type.packageName != jClass.as3Type.packageName
            && jImport.as3Type.qualifiedName != "java.io.Serializable")
            as3Imports.add(jImport.as3Type.qualifiedName);
    }

%>/*
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
package ${jClass.as3Type.packageName} {
<%
///////////////////////////////////////////////////////////////////////////////
// Write Import Statements.

    for (as3Import in as3Imports) {%>
    import ${as3Import};<%
    }

///////////////////////////////////////////////////////////////////////////////
// Write Class Declaration.%>

    [Bindable]
    public class ${jClass.as3Type.name}Base<%

        boolean implementsWritten = false;
        // If DomainObject is implemented directly, those methods aren't marked with overriden
        boolean implementsDomainObject = false;
        if (jClass.hasSuperclass()) {
            %> extends ${jClass.superclass.as3Type.name}<%
        } else {
            %> implements IExternalizable<%

            implementsWritten = true;
        }

        for (jInterface in jClass.interfaces) {
            if (!implementsWritten) {
                %> implements ${jInterface.as3Type.name}<%

                implementsWritten = true;
            } else {
                %>, ${jInterface.as3Type.name}<%
            }
            if (jInterface.as3Type.name == 'DomainObject') {
                implementsDomainObject = true;
            }
        }

    %> {
<%

    ///////////////////////////////////////////////////////////////////////////
    // Write Private Fields.

    for (jProperty in jClass.properties) {%>
        protected var _${jProperty.name}:${jProperty.as3Type.name};<%
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write Public Getter/Setter.

    for (jProperty in jClass.properties) {
        if (jProperty.readable || jProperty.writable) {%>
<%
            if (jProperty.writable) {%>
        public function set ${jProperty.name}(value:${jProperty.as3Type.name}):void {
            _${jProperty.name} = value;
        }<%
            }
            if (jProperty.readable && jProperty.name == 'new') {%>
        public function isNew():${jProperty.as3Type.name} {
            return true;
        }<%
            } else if (jProperty.readable && jProperty.name == 'version' && !implementsDomainObject) {%>
        override public function get version():${jProperty.as3Type.name} {
            return ${jProperty.as3Type.nullValue};
        }<%
            } else if (jProperty.readable && jProperty.name == 'version' && implementsDomainObject) {%>
        public function get version():${jProperty.as3Type.name} {
            return ${jProperty.as3Type.nullValue};
        }<%
            } else if (jProperty.readable && jProperty.as3Type.name != "Serializable") {%>
        public function get ${jProperty.name}():${jProperty.as3Type.name} {
            return _${jProperty.name};
        }<%
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write Public Getters/Setters for Implemented Interfaces.

    if (jClass.hasInterfaces()) {
        for (jProperty in jClass.interfacesProperties) {
            if (jProperty.readable || jProperty.writable) {%>
<%
                if (jProperty.writable) {%>
        public function set ${jProperty.name}(value:${jProperty.as3Type.name}):void {
        }<%
                }
                if (jProperty.readable && jProperty.as3Type.name != "Serializable") {%>
        public function get ${jProperty.name}():${jProperty.as3Type.name} {
            return ${jProperty.as3Type.nullValue};
        }<%
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write IExternalizable Implementation.%>

        <%= (jClass.hasSuperclass() ? "override " : "") %>public function readExternal(input:IDataInput):void {<%

    if (jClass.hasSuperclass()) {%>
            super.readExternal(input);<%
    }
    for (jProperty in jClass.properties) {
        if (jProperty.as3Type.isNumber()) {%>
            _${jProperty.name} = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());<%
        }
        else if (jProperty.isEnum()) {%>
            _${jProperty.name} = Enum.readEnum(input) as ${jProperty.as3Type.name};<%
        }
        else {%>
            _${jProperty.name} = input.readObject() as ${jProperty.as3Type.name};<%
        }
    }%>
        }

        <%= (jClass.hasSuperclass() ? "override " : "") %>public function writeExternal(output:IDataOutput):void {<%

    if (jClass.hasSuperclass()) {%>
            super.writeExternal(output);<%
    }
    for (jProperty in jClass.properties) {%>
            output.writeObject(_${jProperty.name});<%
    }%>
        }
    }
}