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
	import org.openwms.client.model.TreeNode;
	import org.openwms.common.domain.LocationGroup;

	/**
	 * A ShowLocationGroupCommand.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class ShowLocationGroupCommand implements ICommand, IResponder
	{
		[Bindable]
		private var modelLocator:ModelLocator = ModelLocator.getInstance();

		public function ShowLocationGroupCommand()
		{
			super();
		}

		public function execute(event:CairngormEvent):void
		{
			var delegate:LocationGroupDelegate = new LocationGroupDelegate(this)
			delegate.getLocationGroups();
			modelLocator.mainViewStackIndex = ModelLocator.MAIN_VIEW_STACK_LOCATIONGROUP_VIEW;
		}
		
		public function result(event:Object):void {
			var rawResult:ArrayCollection = (event as ResultEvent).result as ArrayCollection;
			modelLocator.allLocationGroups = (event as ResultEvent).result as ArrayCollection;
			// Setup tree if not set before
			if ( null == modelLocator.locationGroupTree ) {
				modelLocator.locationGroupTree = new TreeNode();
				modelLocator.locationGroupTree.build(modelLocator.allLocationGroups);
			}
		}
		
		public function fault(event:Object):void {
			Alert.show("Fault in ["+this+"] Errormessage : "+event);
		}
		
	}
}