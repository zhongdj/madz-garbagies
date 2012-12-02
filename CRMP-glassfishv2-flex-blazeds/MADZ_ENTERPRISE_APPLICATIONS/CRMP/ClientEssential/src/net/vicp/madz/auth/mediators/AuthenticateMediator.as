package net.vicp.madz.auth.mediators
{
	import flash.display.DisplayObject;
	
	import mx.core.IFlexDisplayObject;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	
	import net.vicp.madz.auth.events.AuthenticateEvent;
	import net.vicp.madz.auth.facades.AuthenticateFacade;
	import net.vicp.madz.auth.views.LoginDialog;
	import net.vicp.madz.bootstrap.facades.BootStrapFacade;
	
	import org.puremvc.as3.interfaces.*;
	import org.puremvc.as3.patterns.mediator.Mediator;
		
	public class AuthenticateMediator extends Mediator
	{
		public static const NAME:String = "net.vicp.madz.auth.mediators.AuthenticateMediator";
		private var loginForm:LoginDialog;
		
		public function AuthenticateMediator(viewComponent:Object=null){
			super(NAME, viewComponent);
		}
		
		override public function listNotificationInterests():Array{
            return [ 
            		BootStrapFacade.LOGIN,
            		BootStrapFacade.LOGOUT,
            		
            		AuthenticateFacade.AUTHENTICATED_SUCCESS,
            		AuthenticateFacade.AUTHENTICATED_FAILED
                   ];
        }

        override public function handleNotification( note:INotification ):void{
            switch ( note.getName() ){
                 case BootStrapFacade.LOGIN:
                	processLogin();
                  	break;
                 case AuthenticateFacade.AUTHENTICATED_SUCCESS:
                 	processAuthSuccess(note);
                 	break;
                 case AuthenticateFacade.AUTHENTICATED_FAILED:
                 	processAuthFailed();
                 break;
            }
        }
        
		private function processLogin():void{
			if(loginForm == null){
				loginForm = new LoginDialog();
			}else{
				loginForm.reset();
			}
			PopUpManager.addPopUp(loginForm as IFlexDisplayObject, viewComponent as DisplayObject, true);
			PopUpManager.centerPopUp(loginForm);
			loginForm.loginButton.addEventListener(FlexEvent.BUTTON_DOWN,onAuthenticate);
			loginForm.closeBtn.addEventListener(FlexEvent.BUTTON_DOWN,onUnauth);

		}
		
		private function onAuthenticate(event:FlexEvent):void{
			var authEvent:AuthenticateEvent = new AuthenticateEvent(loginForm.userNameInput.text,loginForm.passwordInput.text);
			this.facade.sendNotification(AuthenticateEvent.AUTHENTICATE,authEvent);
		}
		
		private function onUnauth(event:FlexEvent):void{
			PopUpManager.removePopUp(loginForm);
		}
		
		private function processAuthFailed():void{
			loginForm.reset();
		}
		
		private function processAuthSuccess(note:INotification):void{
			PopUpManager.removePopUp(loginForm);
		}
	}
}