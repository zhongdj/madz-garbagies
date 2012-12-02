package net.vicp.madz.framework.mediators
{
	import mx.containers.Box;
	import mx.core.UIComponent;
	import mx.core.FlexGlobals;
	import mx.modules.ModuleLoader;
	
	import net.vicp.madz.framework.facades.FrameworkFacade;
	import net.vicp.madz.framework.views.ManagedView;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	public class ViewContainerMediator extends Mediator
	{
		public static const NAME:String = "net.vicp.madz.framework.mediators.ViewContainerMediator";
		
		private static var instance:ViewContainerMediator;
		private var viewCache:Array;
		
		public function ViewContainerMediator(enforcer:ViewContainerMediatorSingletonEnforcer, viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			viewCache = new Array();
			initialize();
		}
		
		public static function getInstance(viewComponent:UIComponent):ViewContainerMediator{
			if(instance == null){
				instance = new ViewContainerMediator(new ViewContainerMediatorSingletonEnforcer(),viewComponent);
			}
			return instance;
		}
		
		private function initialize():void{
//			sendNotification(FrameworkFacade.FETCH_VIEW_CONFIG_MAP);
//			FlexGlobals.topLevelApplication.styleManager.loadStyleDeclarations("net/vicp/madz/system/css/flexStyleExplorer.swf");
		}
		
		public override function listNotificationInterests():Array
		{
			return [ 
            		FrameworkFacade.VIEW_CONFIG_MAP_FETCHED_RESULT,
            		FrameworkFacade.SHOW_VIEW,
            		FrameworkFacade.LOGOUT            		
                   ];
		}
		
		public override function handleNotification(notification:INotification):void
		{
			switch(notification.getName()){
				case FrameworkFacade.VIEW_CONFIG_MAP_FETCHED_RESULT:
					onViewConfigMapFetched(notification);
					break;
				case FrameworkFacade.SHOW_VIEW:
					onShowView(notification);
					break;
				case FrameworkFacade.LOGOUT:
					onLogout();
					break;
			}
		}
		
		private function onLogout():void {
			if(viewCache != null ){
				for( var index:String in viewCache){
					if(viewCache[index] is ModuleLoader){
						if(ModuleLoader(viewCache[index]).child is ManagedView){
							ManagedView(ModuleLoader(viewCache[index]).child).reset();
						}else{
							ModuleLoader(viewCache[index]).removeAllChildren();
							viewCache[index]= null;
						}
					}
				}
			}
		}
		
		private function onViewConfigMapFetched(notification:INotification):void{
			trace("ViewContainerMediator: on View Config Map Fetched");	
		}
		
		private function onShowView(notification:INotification):void{
			trace("ViewContainerMediator: on Show View");
			trace("View ID: " + notification.getBody());
			var viewId:String = notification.getBody() as String;
			var loader:ModuleLoader;
			if(viewCache[viewId] == null){
				loader = new ModuleLoader();
				var frameworkUrl:String = convertViewIdToModulePath(viewId);
				loader.loadModule(frameworkUrl);
				viewCache[viewId] = loader;
			}
			
			loader = viewCache[viewId] as ModuleLoader;
			
			if(loader != null){
	        	var box:Box = viewComponent as Box;
	        	box.removeAllChildren();
	        	loader.percentWidth = 100;
	        	loader.percentHeight = 100;
	        	box.addChild(loader);
   			}
		}
		
		private function convertViewIdToModulePath(viewId:String):String{
			var result:String =  viewId;
			result = result.replace(/\./g,"/");
			result = result + ".swf";
			return result;
		}
	}
}

class ViewContainerMediatorSingletonEnforcer{}