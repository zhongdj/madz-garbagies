package net.vicp.madz.auth.commands
{
	import net.vicp.madz.auth.proxies.UserProxy;
	import net.vicp.madz.auth.vo.CompanyCTO;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	public class RegisterCommand extends SimpleCommand
	{
		private function get proxy():UserProxy {
			return this.facade.retrieveProxy(UserProxy.NAME) as UserProxy;
		}
		
		public override function execute(notification:INotification):void {
			var companyCTO:CompanyCTO = notification.getBody() as CompanyCTO;
			this.proxy.register(companyCTO);
		}
		
	}
}