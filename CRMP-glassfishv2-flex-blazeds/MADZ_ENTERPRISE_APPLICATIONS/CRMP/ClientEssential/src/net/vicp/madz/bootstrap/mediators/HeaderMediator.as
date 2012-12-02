package net.vicp.madz.bootstrap.mediators
{

	
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	
	import mx.core.IFlexDisplayObject;
	import mx.managers.PopUpManager;
	
	import org.puremvc.as3.interfaces.*;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	import net.vicp.madz.bootstrap.views.Header;
	import net.vicp.madz.auth.events.AuthenticateEvent;
	
	import net.vicp.madz.bootstrap.facades.BootStrapFacade;
	
	public class HeaderMediator extends Mediator
	{
		public const NAME:String  = "HeaderMediator";
		private var header:Header; 
		
		public function HeaderMediator(viewComponent:Header=null)
		{
			header = viewComponent;
			super(NAME, viewComponent);
			initEventListeners();
		}
		
		override public function listNotificationInterests():Array{
            return [ 
            		BootStrapFacade.LOGOUT,
            		BootStrapFacade.AUTHENTICATED_SUCCESS
                   ];
        }

        override public function handleNotification( note:INotification ):void{
            switch ( note.getName() ){
                 case Header.LOGOUT:
                    processLogout();
                 	break;
                 case BootStrapFacade.AUTHENTICATED_SUCCESS:
                 	processAuthSuccess(note);
                 	break;
            }
        }

		private function processLogout():void{
			header.logout();
		}
		
		private function processAuthSuccess(note:INotification):void{
			var userName:String = note.getBody() as String;
			header.loginSuccess(userName);			
		}
		
		private function initEventListeners():void{
			header.addEventListener(Header.LOGIN,onLogin);
			header.addEventListener(Header.LOGOUT,onLogout);
			header.addEventListener(Header.REGISTER,onPerformRegister);
		}
		
		private function onLogin(event:Event):void{
			sendNotification(BootStrapFacade.LOGIN);
		}
		
		private function onLogout(event:Event):void{
			sendNotification(BootStrapFacade.LOGOUT);
		}
		
		private function onPerformRegister(event:Event):void{
			sendNotification(BootStrapFacade.REGISTER);
		}
	}
}