package net.vicp.madz.framework.proxies
{
	import org.puremvc.as3.interfaces.IProxy;
	import org.puremvc.as3.patterns.proxy.Proxy;

	import net.vicp.madz.framework.facades.FrameworkFacade;
	
	public class ViewConfigMapProxy extends Proxy implements IProxy
	{
		public static const NAME:String = "net.vicp.madz.framework.proxies.ViewConfigMapProxy";
		
		public function ViewConfigMapProxy()
		{
			super(NAME);
		}
		
		public function getViewConfigMap():void{
			trace("ViewConfigMapProxy: getViewConfigMap");
			this.sendNotification(FrameworkFacade.VIEW_CONFIG_MAP_FETCHED_RESULT);
		}
	}
}