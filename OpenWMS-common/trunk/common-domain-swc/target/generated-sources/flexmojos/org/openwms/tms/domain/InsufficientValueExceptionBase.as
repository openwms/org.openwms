/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.domain {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;

    [Bindable]
    public class InsufficientValueExceptionBase implements IExternalizable {


        public function readExternal(input:IDataInput):void {
        }

        public function writeExternal(output:IDataOutput):void {
        }
    }
}