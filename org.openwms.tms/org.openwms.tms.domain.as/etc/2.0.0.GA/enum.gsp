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
--%>/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ${jClass.as3Type.packageName} {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="${jClass.qualifiedName}")]
    public class ${jClass.as3Type.name} extends Enum {
<%

        for (jEnumValue in jClass.enumValues) {%>
        public static const ${jEnumValue.name}:${jClass.name} = new ${jClass.name}("${jEnumValue.name}", _);<%
        }%>

        function ${jClass.as3Type.name}(value:String = null, restrictor:* = null) {
            super((value || ${jClass.firstEnumValue.name}.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [<%
                for (jEnumValue in jClass.enumValues) {
                    if (jEnumValue != jClass.firstEnumValue) {
                        %>, <%
                    }
                    %>${jEnumValue.name}<%
                }
            %>];
        }

        public static function valueOf(name:String):${jClass.as3Type.name} {
            return ${jClass.as3Type.name}(${jClass.firstEnumValue.name}.constantOf(name));
        }
    }
}