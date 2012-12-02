package net.vicp.madz.auth.mediators
{
	import flash.display.DisplayObject;
	import mx.core.IFlexDisplayObject;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	
	import net.vicp.madz.auth.facades.AuthenticateFacade;
	import net.vicp.madz.auth.views.RegisterCompany;
	import net.vicp.madz.auth.vo.CompanyCTO;
	import net.vicp.madz.bootstrap.facades.BootStrapFacade;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	public class RegisterMediator extends Mediator
	{
		public static const NAME:String = "net.vicp.madz.auth.mediators.RegisterMediator";
		private var registerForm:RegisterCompany;
		
		public function RegisterMediator(viewComponent:Object=null){
			super(NAME, viewComponent);
		}
		
		override public function listNotificationInterests():Array{
            return [ 
            		BootStrapFacade.REGISTER,
            		AuthenticateFacade.REGISTER_SUCCESS,
            		AuthenticateFacade.REGISTER_FAILED            		
                   ];
        }

        override public function handleNotification( note:INotification ):void{
            switch ( note.getName() ){
                case BootStrapFacade.REGISTER:
                	processRegister();
                  	break;
                case AuthenticateFacade.REGISTER_SUCCESS:
                	processRegisterSuccess();
                	break;
                case AuthenticateFacade.REGISTER_FAILED:
                	processRegisterFailed();
                	break;				                  	
            }
        }
        
        private function processRegister():void{
        	if(registerForm == null){
        		registerForm = new RegisterCompany;
        	}else{
        		registerForm.reset();
        	}
        	PopUpManager.addPopUp(registerForm as IFlexDisplayObject,viewComponent as DisplayObject,true);
        	PopUpManager.centerPopUp(registerForm);
			registerForm.submitButton.addEventListener(FlexEvent.BUTTON_DOWN,onSubmit);
			registerForm.closeBtn.addEventListener(FlexEvent.BUTTON_DOWN,onClose);
        }
        
        private function processRegisterSuccess():void{
        	PopUpManager.removePopUp(registerForm);
//        	sendNotification(BootStrapFacade.LOGIN);
        }
        
        private function processRegisterFailed():void{
        		registerForm.reset();
        }
        
        
        private function onSubmit(event:FlexEvent):void{
        	var companyCTO:CompanyCTO = new CompanyCTO();
        	companyCTO.adminName = registerForm.userName; 
        	companyCTO.adminPass = registerForm.password;
        	companyCTO.name = registerForm.companyName;
        	companyCTO.artificialPersonName = registerForm.ownerName;
        	companyCTO.address = registerForm.address;
        	sendNotification(AuthenticateFacade.SUBMIT_REGISTER_INFO,companyCTO);
        }
        
        private function onClose(event:FlexEvent):void{
        	PopUpManager.removePopUp(registerForm);
        }
        
	}
}