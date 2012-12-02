package net.vicp.madz.framework.proxies
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import net.vicp.madz.framework.facades.FrameworkFacade;
	
	import org.puremvc.as3.interfaces.IProxy;
	import org.puremvc.as3.patterns.proxy.Proxy;
	public class MenuProxy extends Proxy implements IProxy
	{
		public static const NAME:String = "net.vicp.madz.framework.proxies.MenuProxy";
		
		public function MenuProxy()
		{
			super(NAME);
		}
		
		public function getMenu():void{
			var remoteObject:RemoteObject = new RemoteObject();
			remoteObject.destination = "UIQuery";
			remoteObject.addEventListener(ResultEvent.RESULT,handleResultEvent);
			remoteObject.addEventListener(FaultEvent.FAULT,handleFaultEvent);
			remoteObject.getMenuXMLDescription();
		}
		
		private function handleResultEvent(event:ResultEvent):void{
			
			trace("MenuProxy: getMenu");
//			var result:String = "<node label=\"Application\" data=\"net.vicp.madz.framework.views.SystemSettings\" icon=\"application\"/>";
			var result:String = event.result.toString();
			trace(result);
			var menu:XMLList = new XMLList(result);
			trace(menu);
			sendNotification(FrameworkFacade.MENU_FETCHED_RESULT,menu);
		}
		
		private function handleFaultEvent(event:FaultEvent):void{
			Alert.show("Cannot get menu.");
		}
			
	}
}