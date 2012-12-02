package net.vicp.madz.framework.commands
{
	import org.puremvc.as3.interfaces.ICommand;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	import org.puremvc.as3.interfaces.INotification;

	import net.vicp.madz.framework.proxies.ViewConfigMapProxy;
	
	//Respond for ViewConfigMapFetch notification
	public class ViewConfigMapFetchCommand extends SimpleCommand implements ICommand
	{
		private function get proxy():ViewConfigMapProxy {
			return this.facade.retrieveProxy(ViewConfigMapProxy.NAME) as ViewConfigMapProxy;
		}
		
		public override function execute(notification:INotification):void {
			this.proxy.getViewConfigMap();
		}
	}
}