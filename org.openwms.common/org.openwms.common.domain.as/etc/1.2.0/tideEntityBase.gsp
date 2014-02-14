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

    import org.granite.generator.as3.reflect.JavaFieldProperty;
    import org.granite.generator.as3.reflect.JavaAbstractType;
    
    import java.lang.reflect.Field;
	import java.lang.reflect.Modifier;
    
    import javax.persistence.Version;
    import javax.persistence.EmbeddedId;
    import javax.persistence.Transient;


	// Check if we have at least an id or a uid in jClass hierarchy.	
    JavaAbstractType jc = jClass;
    boolean hasUid = jClass.hasUid();
    while (!hasUid && jc.hasSuperclass()) {
        jc = jc.getSuperclass();
        hasUid = jc.hasUid();
    }
    
    jc = jClass;
    boolean hasId = jClass.hasIdentifiers();
    while (!hasId && jc.hasSuperclass()) {
        jc = jc.getSuperclass();
        hasId = jc.hasIdentifiers();
    }
    
    if (!hasUid && !hasId)
        throw new RuntimeException("Explicit uid field is required for: " + jClass.qualifiedName);

	// Only generates default uid block for the class that owns the id.
	boolean generateDefaultUidMethods = !hasUid && jClass.hasIdentifiers();


    JavaFieldProperty versionField = null;
    for (jProperty in jClass.properties) {
        if (jProperty.isAnnotationPresent(Version.class)) {
            versionField = (JavaFieldProperty)jProperty;
            break;
        }
    }

    Set as3Imports = new TreeSet();

    as3Imports.add("flash.utils.IDataInput");
    as3Imports.add("flash.utils.IDataOutput");
    as3Imports.add("org.granite.meta");
    as3Imports.add("org.granite.tide.IPropertyHolder");
    as3Imports.add("org.granite.tide.IEntityManager");

    if (jClass.hasIdentifiers())
        as3Imports.add("org.granite.collections.IPersistentCollection");

    if (jClass.hasUid() || generateDefaultUidMethods) {
        as3Imports.add("mx.core.IUID");
        if (generateDefaultUidMethods)
        	as3Imports.add("flash.utils.getQualifiedClassName");
    }

    if (!jClass.hasSuperclass()) {
        as3Imports.add("flash.utils.IExternalizable");
        as3Imports.add("flash.events.EventDispatcher");
        as3Imports.add("org.granite.tide.IEntity");
    }

    if (jClass.hasEnumProperty())
        as3Imports.add("org.granite.util.Enum");

    for (jImport in jClass.imports) {
        if (jImport.as3Type.hasPackage() && jImport.as3Type.packageName != jClass.as3Type.packageName)
            as3Imports.add(jImport.as3Type.qualifiedName);
    }

%>/*
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
<%

///////////////////////////////////////////////////////////////////////////////
// Write Import Statements.

    for (as3Import in as3Imports) {%>
    import ${as3Import};<%
    }%>

    use namespace meta;<%

///////////////////////////////////////////////////////////////////////////////
// Write Class Declaration.%>

    [Managed]
    public class ${jClass.as3Type.name}Base<%

        boolean implementsWritten = false;
        if (jClass.hasSuperclass()) {
            %> extends ${jClass.superclass.as3Type.name}<%
        } else {
            %> implements IExternalizable<%

            implementsWritten = true;
            if (jClass.hasUid() || generateDefaultUidMethods) {
                %>, IUID<%
            }
        }

        for (jInterface in jClass.interfaces) {
            if (!implementsWritten) {
                %> implements ${jInterface.as3Type.name}<%

                implementsWritten = true;
            } else {
                %>, ${jInterface.as3Type.name}<%
            }
        }

    %> {
<%

    ///////////////////////////////////////////////////////////////////////////
    // Write Private Fields.

    if (jClass.hasIdentifiers()) {%>
        protected var _em:IEntityManager = null;

        private var __laziness:String = null;
<%
    }
    for (jProperty in jClass.properties) {%>
        private var _${jProperty.name}:${jProperty.as3Type.name};<%
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write laziness (meta)method.

    if (jClass.hasIdentifiers()) {%>

        <%= (jClass.hasSuperclass() ? "override " : "") %>meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is ${jClass.as3Type.name}) || (property as ${jClass.as3Type.name}).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        [Transient]
        <%= (jClass.hasSuperclass() ? "override " : "") %>public function meta_getEntityManager():IEntityManager {
            return _em;
        }
        <%= (jClass.hasSuperclass() ? "override " : "") %>public function meta_setEntityManager(em:IEntityManager):void {
            _em = em;
        }<%
    }
    else if (!jClass.hasSuperclass()) {%>

        meta function isInitialized(name:String = null):Boolean {
            return true;
        }

        [Transient]
        public function meta_getEntityManager():IEntityManager {
            return _em;
        }
        public function meta_setEntityManager(em:IEntityManager):void {
            _em = em;
        }<%
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write Public Getter/Setter.

    for (jProperty in jClass.properties) {
        if (jProperty != jClass.uid) {
            if (jProperty.readable || jProperty.writable) {%>
<%
                if (jProperty.writable) {%>
        public function set ${jProperty.name}(value:${jProperty.as3Type.name}):void {
            _${jProperty.name} = value;
        }<%
                }
                if (jProperty.readable) {
                    if (jProperty == versionField) {%>
        [Version]<%
                    }%>
        public function get ${jProperty.name}():${jProperty.as3Type.name} {
            return _${jProperty.name};
        }<%
                }
            }
        } else {%>

        public function set uid(value:String):void {
            _${jClass.uid.name} = value;
        }
        public function get uid():String {
            return _${jClass.uid.name};
        }<%

        }
    }

    if (generateDefaultUidMethods) {%>

        public function set uid(value:String):void {
            // noop...
        }
        public function get uid():String {<%
        
        // First case: one or multiple (@IdClass) @Id simple fields.
        if (!jClass.firstIdentifier.isAnnotationPresent(EmbeddedId.class)) {
        %>
        	if (<%
        		for (int i = 0; i < jClass.identifiers.size(); i++) {
        			JavaFieldProperty jId = jClass.identifiers.get(i);
        			%><%= (i > 0) ? " && " : "" %><%= (jId.as3Type.name == "Number") ? ("isNaN(_" + jId.name + ")") : ("!_" + jId.name) %><%
        		}%>)
        		return getQualifiedClassName(this);
        	return getQualifiedClassName(this) + "#[" + <%
        		for (int i = 0; i < jClass.identifiers.size(); i++) {
        			JavaFieldProperty jId = jClass.identifiers.get(i);
        			%><%= (i > 0) ? (" + \",\" + ") : "" %>String(_${jId.name})<%
        		}%> + "]";
        	<%
        }
        // Second case: one @EmbeddedId composite field.
        else {
        	JavaFieldProperty jId = jClass.firstIdentifier;
        %>
        	if (!_${jId.name})
        		return getQualifiedClassName(this);
        	return getQualifiedClassName(this) + "#[" + <%
                int i = 0;
        		for (field in jId.type.declaredFields) {
	    			if (!Modifier.isStatic(field.modifiers) &&
	    				!Modifier.isTransient(field.modifiers) &&
	    				!field.isAnnotationPresent(Transient.class)) {
        				%><%= (i++ > 0) ? (" + \",\" + ") : "" %>String(_${jId.name}.${field.name})<%
        			}
        		}%> + "]";
        	<%
        }
        %>
        }<%
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
                if (jProperty.readable) {%>
        public function get ${jProperty.name}():${jProperty.as3Type.name} {
            return ${jProperty.as3Type.nullValue};
        }<%
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write Merge Implementation.%>

        <%= (jClass.hasSuperclass() ? "override " : "") %>public function meta_merge(em:IEntityManager, obj:*):void {
            var src:${jClass.as3Type.name}Base = ${jClass.as3Type.name}Base(obj);<%

    if (jClass.hasIdentifiers()) {%>
            __laziness = src.__laziness;<%
    }
    else if (jClass.hasSuperclass()) {%>
            super.meta_merge(em, obj);<%
    }%>
            if (meta::isInitialized()) {<%

    if (jClass.hasSuperclass() && jClass.hasIdentifiers()) {%>
                super.meta_merge(em, obj);<%
    }

    for (jProperty in jClass.properties) {%>
               em.meta_mergeExternal(src._${jProperty.name}, _${jProperty.name}, null, this, '${jProperty.name}', function setter(o:*):void{_${jProperty.name} = o as ${jProperty.as3Type.name}});<%
    }%>
            }<%

    if (jClass.hasIdentifiers()) {%>
            else {<%
        if (jClass.hasIdClass()) {
            for (jId in jClass.identifiers) {%>
               em.meta_mergeExternal(src._${jId.name}, _${jId.name}, null, this, '${jId.name}', function setter(o:*):void{_${jId.name} = o as ${jId.as3Type.name}});<%
            }
        }
        else {%>
               em.meta_mergeExternal(src._${jClass.firstIdentifier.name}, _${jClass.firstIdentifier.name}, null, this, '${jClass.firstIdentifier.name}', function setter(o:*):void{_${jClass.firstIdentifier.name} = o as ${jClass.firstIdentifier.as3Type.name}});<%
        }%>
            }<%
    }%>
        }<%

    ///////////////////////////////////////////////////////////////////////////
    // Write IExternalizable Implementation.%>

        <%= (jClass.hasSuperclass() ? "override " : "") %>public function readExternal(input:IDataInput):void {<%

    if (jClass.hasIdentifiers()) {%>
            __laziness = input.readObject() as String;<%
    }
    else if (jClass.hasSuperclass()) {%>
            super.readExternal(input);<%
    }%>
            if (meta::isInitialized()) {<%

    if (jClass.hasSuperclass() && jClass.hasIdentifiers()) {%>
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
            }<%

    if (jClass.hasIdentifiers()) {%>
            else {<%
        if (jClass.hasIdClass()) {
            String idClassType = jClass.idClass.as3Type.name;
            %>
                var id:${idClassType} = input.readObject() as ${idClassType};<%
            for (jId in jClass.identifiers) {%>
                _${jId.name} = id.${jId.name};<%
            }
        }
        else if (jClass.firstIdentifier.as3Type.isNumber()) {%>
                _${jClass.firstIdentifier.name} = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());<%
        }
        else {%>
                _${jClass.firstIdentifier.name} = input.readObject() as ${jClass.firstIdentifier.as3Type.name};<%
        }%>
            }<%
    }%>
        }

        <%= (jClass.hasSuperclass() ? "override " : "") %>public function writeExternal(output:IDataOutput):void {<%

    if (jClass.hasIdentifiers()) {%>
            output.writeObject(__laziness);<%
    }
    else if (jClass.hasSuperclass()) {%>
            super.writeExternal(output);<%
    }%>
            if (meta::isInitialized()) {<%

    if (jClass.hasSuperclass() && jClass.hasIdentifiers()) {%>
                super.writeExternal(output);<%
    }

    for (jProperty in jClass.properties) {%>
                output.writeObject((_${jProperty.name} is IPropertyHolder) ? IPropertyHolder(_${jProperty.name}).object : _${jProperty.name});<%
    }%>
            }<%

    if (jClass.hasIdentifiers()) {%>
            else {<%
        if (jClass.hasIdClass()) {
            String idClassType = jClass.idClass.as3Type.name;
            %>
                var id:${idClassType} = new ${idClassType}();<%
            for (jId in jClass.identifiers) {%>
                id.${jId.name} = _${jId.name};<%
            }%>
                output.writeObject(id);<%
        } else {%>
                output.writeObject(_${jClass.firstIdentifier.name});<%
        }%>
            }<%
    }%>
        }
    }
}
