package org.openwms.web.flex.client.components
{
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;

	public interface IApplicationModule extends IEventDispatcher
	{
		
		function getMainMenuItems():ArrayCollection;
		
		
	}
}