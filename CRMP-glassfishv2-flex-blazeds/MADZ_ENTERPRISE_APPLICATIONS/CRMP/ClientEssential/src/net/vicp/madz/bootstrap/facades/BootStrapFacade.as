package net.vicp.madz.bootstrap.facades
{	
	import org.puremvc.as3.interfaces.IFacade;
	import org.puremvc.as3.patterns.facade.Facade; 
    
    import net.vicp.madz.bootstrap.mediators.*;
    import net.vicp.madz.auth.commands.*;
	import net.vicp.madz.bootstrap.commands.*;
    
	public class BootStrapFacade extends Facade implements IFacade {
		
		public static const STARTUP:String                		= "startup";
		public static const INITIALIZE_SITE:String        		= "initializeSite";
		public static const RESIZE:String 				  		= "resize";
		public static const APPLICATION_RESIZE:String      		= "applicationResize";
		public static const AUTHENTICATED_SUCCESS:String  	 	= "AuthenticatedSuccess";

		public static const LOGIN:String = "login";
		public static const LOGOUT:String = "logout";
		public static const REGISTER:String = "register";
		public static const CLOSE_REGISTER:String = "close_register";
		
		private var clientWorkbench:ClientEssential;
		
		public function startup(app:Object):void {
			clientWorkbench = app as ClientEssential;
			this.sendNotification(BootStrapFacade.STARTUP, app);
		}

		public static function getInstance():BootStrapFacade{
            if (instance == null) {
				instance = new BootStrapFacade();
			}
            return instance as BootStrapFacade;
        }
		
		protected override function initializeController():void {
			super.initializeController();
			registerCommand( STARTUP, StartupCommand );
		}
		
		protected override function initializeView():void {
			super.initializeView();
		}
	}
}