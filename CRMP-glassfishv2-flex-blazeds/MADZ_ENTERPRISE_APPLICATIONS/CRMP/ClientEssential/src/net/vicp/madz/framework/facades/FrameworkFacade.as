package net.vicp.madz.framework.facades
{
	import mx.core.UIComponent;
	
	import net.vicp.madz.framework.commands.*;
	import net.vicp.madz.framework.views.Framework;
	
	import org.puremvc.as3.interfaces.IFacade;
	import org.puremvc.as3.patterns.facade.Facade;
	
	public class FrameworkFacade
	{
		
		private static var instance:FrameworkFacade;
		 
		public static const INITIALIZE_FRAMEWORK_MODULE:String	 	= "InitializeFramework";
		public static const LOAD_FRAMEWORK_COMPONENT:String 		= "LoadFrameworkComponent";
		public static const FETCH_MENU:String						= "FetchMenu";
		public static const FETCH_VIEW_CONFIG_MAP:String			= "FetchViewConfigMap";
		public static const MENU_FETCHED_RESULT:String				= "MenuFetchedResult";
		public static const VIEW_CONFIG_MAP_FETCHED_RESULT:String	= "ViewConfigMapFetchedResult";
		public static const SHOW_VIEW:String						= "ShowView";
		public static const LOGOUT:String 							= "logout";
		private var framework:Framework;
		private var facade:IFacade;
		
		public static function getInstance():FrameworkFacade{
			if(instance == null){
				instance = new FrameworkFacade(new FrameworkFacadeSingletonEnforcer());
			}
			return instance;
		}
		
		function FrameworkFacade(enforcer:FrameworkFacadeSingletonEnforcer)
		{
			facade = Facade.getInstance();
			initializeFacade();	
			framework = new Framework();
		}
		
		public function startUp(viewComponent:UIComponent):void{
			var app:ClientEssential = viewComponent as ClientEssential;
			app.frameworkBox.removeAllChildren();
			app.frameworkBox.addChild(framework);
			facade.sendNotification(INITIALIZE_FRAMEWORK_MODULE,framework);
		}
		
		protected function initializeFacade():void {
			facade.registerCommand(INITIALIZE_FRAMEWORK_MODULE,InitializeFrameworkCommand);
			facade.registerCommand(LOAD_FRAMEWORK_COMPONENT,LoadFrameworkComponentCommand);
		}
	}
}

class FrameworkFacadeSingletonEnforcer{}