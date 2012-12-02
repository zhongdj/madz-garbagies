package net.vicp.madz.framework.commands
{
	import org.puremvc.as3.interfaces.ICommand;
    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.patterns.command.SimpleCommand;
    
	import net.vicp.madz.framework.proxies.MenuProxy;
	//Respond for FetchMenu Notifiaction
	public class MenuFetchCommand extends SimpleCommand implements ICommand
	{
		private function get proxy():MenuProxy {
			return this.facade.retrieveProxy(MenuProxy.NAME) as MenuProxy;
		}
		
		public override function execute(notification:INotification):void {
			this.proxy.getMenu();
		}
	}
}