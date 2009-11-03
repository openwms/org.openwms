/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	import org.openwms.client.business.LocationGroupDelegate;
	import org.openwms.client.model.ModelLocator;
	import org.openwms.common.domain.LocationGroup;

	/**
	 * A LoadLocationGroupCommand.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class LoadLocationGroupsCommand implements ICommand, IResponder
	{
		[Bindable]
		private var modelLocator:ModelLocator = ModelLocator.getInstance();

		public function LoadLocationGroupsCommand()
		{
			super();
		}

		public function execute(event:CairngormEvent):void
		{
			if (modelLocator.allLocationGroups.length == 0) {
				var delegate:LocationGroupDelegate = new LocationGroupDelegate(this)
				delegate.getLocationGroups();
			}
		}
		
		public function result(data:Object):void
		{
			var rawResult:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
			modelLocator.allLocationGroups = (data as ResultEvent).result as ArrayCollection;
		}
		
		public function fault(info:Object):void
		{
			Alert.show("Fault in ["+this+"] Errormessage : "+info);
		}
		
	}
}