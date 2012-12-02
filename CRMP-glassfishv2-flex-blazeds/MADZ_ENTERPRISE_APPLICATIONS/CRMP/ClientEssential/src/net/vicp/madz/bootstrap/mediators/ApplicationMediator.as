package net.vicp.madz.bootstrap.mediators
{
	import mx.events.ResizeEvent;
	
	import org.puremvc.as3.patterns.mediator.Mediator;
	import org.puremvc.as3.interfaces.*;
	import net.vicp.madz.bootstrap.facades.BootStrapFacade;
	
	public class ApplicationMediator extends Mediator
	{
		public static const NAME:String = "ApplicationMediator";
		public function ApplicationMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			initializeEvents();
		}
		
		override public function listNotificationInterests():Array{
            return [ 
            		BootStrapFacade.RESIZE
                   ];
        }

        override public function handleNotification( note:INotification ):void{
            switch ( note.getName() ){
                 case BootStrapFacade.RESIZE:
                 	processResize();
                 	break;
            }
        }
        
        private function processResize():void{
        	trace("resize one time");
        	var client:ClientEssential = viewComponent as ClientEssential;
        	trace("stage width = " + client.stage.width + ", stage height = " + client.stage.height);
			trace("client width = " + client.width + ", client height = " + client.height);
			trace("===================================");
        }
		
		private function initializeEvents():void{
			var client:ClientEssential = viewComponent as ClientEssential;
			client.addEventListener(ResizeEvent.RESIZE,onResize);
		}
		
		private function onResize(event:ResizeEvent):void {
			this.sendNotification(BootStrapFacade.RESIZE);
		}
		
	}
}