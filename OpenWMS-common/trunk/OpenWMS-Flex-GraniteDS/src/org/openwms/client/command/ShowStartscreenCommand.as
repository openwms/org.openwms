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
	import org.openwms.client.model.ModelLocator;

	/**
	 * A ShowStartscreenCommand.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class ShowStartscreenCommand implements ICommand
	{
		[Bindable]
		private var modelLocator:ModelLocator = ModelLocator.getInstance();
		
		public function ShowStartscreenCommand()
		{
			super();
		}

		public function execute(event:CairngormEvent):void
		{
			modelLocator.mainViewStackIndex = ModelLocator.MAIN_VIEW_STACK_START_SCREEN;
		}
		
	}
}