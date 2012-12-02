package net.vicp.madz.framework.mediators
{
	import mx.containers.Canvas;
	
	import net.vicp.madz.bootstrap.facades.BootStrapFacade;
	import net.vicp.madz.framework.facades.FrameworkFacade;
	import net.vicp.madz.framework.views.Framework;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.mediator.Mediator;
	
	
	public class FrameworkMediator extends Mediator
	{
		public static const NAME:String = "net.vicp.madz.framework.mediators.FrameworkMediator";

		
		public function FrameworkMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
		}
		
		override public function listNotificationInterests():Array{
            return [ 
            		BootStrapFacade.LOGOUT,
            		BootStrapFacade.AUTHENTICATED_SUCCESS,
            		BootStrapFacade.RESIZE
                   ];
        }

        override public function handleNotification( note:INotification ):void{
            switch ( note.getName() ){
                case BootStrapFacade.LOGOUT:
                	processLogout();
                  	break;
                 case BootStrapFacade.AUTHENTICATED_SUCCESS:
                 	processAuthSuccess();
                 	break;
                 case BootStrapFacade.RESIZE:
                 	processResize();
                 	break;
            }
        }
        
        private function processLogout():void{
        	var frameworkBox:Framework= viewComponent as Framework;
//        	frameworkBox.navigatorBox.removeAllChildren();
//        	frameworkBox.viewBox.removeAllChildren();
			frameworkBox.removeAllChildren();
        }
        
        private function processAuthSuccess():void{
        	this.sendNotification(FrameworkFacade.LOAD_FRAMEWORK_COMPONENT,viewComponent);
        }
        
        private function processResize():void{
			trace("FrameworkMediator process Resize");
        }
        

	}
		
}