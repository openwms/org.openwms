
package org.openwms.client.control
{
  import com.adobe.cairngorm.control.FrontController;
  
  import org.openwms.client.command.*;
  import org.openwms.client.event.*;
  public class MainController extends FrontController
  {
    public function MainController():void {
      super();
      setupEventHandler();
    }
    private function setupEventHandler():void {
      this.addCommand( SwitchScreenEvent.SHOW_STARTSCREEN, ShowStartscreenCommand );
      this.addCommand( SwitchScreenEvent.SHOW_LOCATION_VIEW, ShowLocationViewCommand);
      this.addCommand( SwitchScreenEvent.SHOW_LOCATIONGROUP_VIEW, ShowLocationGroupCommand );
      this.addCommand( LoadLocationGroupsEvent.LOAD_ALL_LOCATION_GROUPS, LoadLocationGroupsCommand );
    }
  }
} 
