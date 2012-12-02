package net.vicp.madz.framework.mediators
{
	import mx.containers.Canvas;
	
	import net.vicp.madz.framework.facades.FrameworkFacade;
	import net.vicp.madz.framework.views.Background;
	import net.vicp.madz.framework.views.navigators.TreeNavigator;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;

	public class NavigatorMediator extends Mediator 
	{
		public static const NAME:String = "net.vicp.madz.framework.mediators.NavigatorMediator";
		private static var instance:NavigatorMediator;
		private	var background:Background = new Background();
		private	var navigator:TreeNavigator = new TreeNavigator();
		public function NavigatorMediator(enforcer:NavigatorMediatorSingletonEnforcer,viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			initialize();
		}
		
		public static function getInstance(viewComponent:Object):NavigatorMediator{
			if(instance == null){
				instance = new NavigatorMediator(new NavigatorMediatorSingletonEnforcer(),viewComponent);
			}
			return instance;
		}
		
		
		
		public override function listNotificationInterests():Array
		{
			return [ 
            		FrameworkFacade.MENU_FETCHED_RESULT
                   ];
		}
		
		public override function handleNotification(notification:INotification):void
		{
			switch(notification.getName()){
				case FrameworkFacade.MENU_FETCHED_RESULT:
				onMenuFetched(notification);
				break;
			}
		}
		
		private function initialize():void{
//			
		}
		
		private function onMenuFetched(notification:INotification):void{
			trace("NavigatorMediator: On Menu Fetched");
			var result:XMLList = notification.getBody() as XMLList;
			trace(result);
			navigator.treeNavData = result;
			var box:Canvas = viewComponent as Canvas;
			box.addChild(background);
			box.addChild(navigator);
			navigator.addEventListener(Event.CHANGE,onChange);
		}
		
		[Bindable]
        public var selectedNode:XML;
            
		private function onChange(event:Event):void{
			 selectedNode=(event.target as TreeNavigator).myTreeNav.selectedItem as XML;
             var currentView:String = selectedNode.@data;
             this.sendNotification(FrameworkFacade.SHOW_VIEW,currentView);
		}
		
	}
}

class NavigatorMediatorSingletonEnforcer{}