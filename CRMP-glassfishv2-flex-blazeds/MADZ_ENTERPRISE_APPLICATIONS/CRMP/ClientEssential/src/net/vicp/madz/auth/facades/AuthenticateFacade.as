package net.vicp.madz.auth.facades
{
	import mx.core.UIComponent;
	
	import net.vicp.madz.auth.commands.*;
	
	import org.puremvc.as3.interfaces.IFacade;
	import org.puremvc.as3.patterns.facade.Facade;

	public class AuthenticateFacade //extends Facade
	{
		public static const INITIALIZE_AUTHENTICATE_MODULE:String 	= "AuthenticateModuleStartup";
		public static const AUTHENTICATE:String            		= "Authenticate";
		public static const AUTHENTICATED_SUCCESS:String  	 	= "AuthenticatedSuccess";
		public static const AUTHENTICATED_FAILED:String   		= "AuthenticatedFailed";
		public static const LOGOUT:String         				= "logout";
		public static const UNAUTHENTICATED_SUCCESS:String 		= "UnauthenticatedSuccess";
		public static const SUBMIT_REGISTER_INFO:String			= "SubmitRegisterInfo";
		public static const REGISTER_SUCCESS:String 			= "register_success";
		public static const REGISTER_FAILED:String 				= "register_failed";
		
		private static var instance:AuthenticateFacade;
		private var facade:IFacade;
		
		public static function getInstance():AuthenticateFacade{
            if (instance == null) {
				instance = new AuthenticateFacade(new AuthenticateFacadeSingtonEnforcer());
			}
            return instance as AuthenticateFacade;
        }
        
		public function startUp(viewComponent:UIComponent):void{
			facade.sendNotification(INITIALIZE_AUTHENTICATE_MODULE,viewComponent);
		}
		
		public function AuthenticateFacade(enforcer:AuthenticateFacadeSingtonEnforcer){
			facade = Facade.getInstance();
			initializeFacade();	
		}

        
        public  function initializeFacade():void {
			facade.registerCommand(AUTHENTICATE,AuthenticateCommand);
			facade.registerCommand(LOGOUT,UnauthenticateCommand);
			facade.registerCommand(INITIALIZE_AUTHENTICATE_MODULE,InitializeAuthModuleCommand);
			facade.registerCommand(SUBMIT_REGISTER_INFO,RegisterCommand);
		}
	}
}

class AuthenticateFacadeSingtonEnforcer{
}