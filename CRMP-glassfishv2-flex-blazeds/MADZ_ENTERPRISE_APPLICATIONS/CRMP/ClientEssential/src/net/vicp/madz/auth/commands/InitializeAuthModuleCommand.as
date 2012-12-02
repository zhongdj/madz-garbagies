package net.vicp.madz.auth.commands
{
	import net.vicp.madz.auth.mediators.AuthenticateMediator;
	import net.vicp.madz.auth.mediators.RegisterMediator;
	import net.vicp.madz.auth.proxies.UserProxy;
	
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	public class InitializeAuthModuleCommand extends SimpleCommand
	{
		override public function execute( note:INotification ):void{
		    var app:ClientEssential = note.getBody() as ClientEssential;        	
		   	facade.registerProxy(new UserProxy());
		    facade.registerMediator(new AuthenticateMediator(app));
		    facade.registerMediator(new RegisterMediator(app));
        }

	}
}