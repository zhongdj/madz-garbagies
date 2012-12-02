package net.vicp.madz.auth.commands
{
	
	import org.puremvc.as3.interfaces.ICommand;
    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.patterns.command.SimpleCommand;

	import net.vicp.madz.auth.events.AuthenticateEvent;
	import net.vicp.madz.auth.proxies.UserProxy;
	
	public class AuthenticateCommand extends SimpleCommand implements ICommand {
		private function get proxy():UserProxy {
			return this.facade.retrieveProxy(UserProxy.NAME) as UserProxy;
		}
		
		public override function execute(notification:INotification):void {
			var credentials:AuthenticateEvent = notification.getBody() as AuthenticateEvent;
			this.proxy.authenticate(credentials.userName, credentials.password);
		}
	}
}