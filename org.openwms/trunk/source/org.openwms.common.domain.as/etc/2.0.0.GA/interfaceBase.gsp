<%--
  GRANITE DATA SERVICES
  Copyright (C) 2007-2008 ADEQUATE SYSTEMS SARL

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.

  @author Franck WOLFF
--%><%
    Set as3Imports = new TreeSet();

    for (jImport in jClass.imports) {
        if (jImport.as3Type.hasPackage() && jImport.as3Type.packageName != jClass.as3Type.packageName)
            as3Imports.add(jImport.as3Type.qualifiedName);
    }

%>/*
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
package ${jClass.as3Type.packageName} {<%

    ///////////////////////////////////////////////////////////////////////////
    // Write Import Statements.

    if (as3Imports.size() > 0) {%>
<%
    }
    for (as3Import in as3Imports) {%>
    import ${as3Import};<%
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write Interface Declaration.%>

    public interface ${jClass.as3Type.name}Base<%

    if (jClass.hasSuperInterfaces()) {
    	%> extends <%
    	boolean first = true;
    	for (jInterface in jClass.superInterfaces) {
    		if (first) {
    			first = false;
    		} else {
    			%>, <%
    		}
    		%>${jInterface.as3Type.name}<%
    	}
    }

    %> {<%

    ///////////////////////////////////////////////////////////////////////////
    // Write Public Getter/Setter.

    for (jProperty in jClass.properties) {

        if (jProperty.readable || jProperty.writable) {%>
<%
            if (jProperty.writable) {%>
        function set ${jProperty.name}(value:${jProperty.as3Type.name}):void;<%
            }
            if (jProperty.readable && jProperty.name == 'new') {%>
        function isNew():${jProperty.as3Type.name};<%
            } else if (jProperty.readable)
            {%>
        function ${jProperty.name}():${jProperty.as3Type.name};<%
            }
        }
    }%>
    }
}