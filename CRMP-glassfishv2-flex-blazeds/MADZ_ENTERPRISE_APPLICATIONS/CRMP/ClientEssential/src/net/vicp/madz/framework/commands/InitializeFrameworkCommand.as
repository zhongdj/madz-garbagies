package net.vicp.madz.framework.commands
{
	import org.puremvc.as3.interfaces.ICommand;
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;

	import net.vicp.madz.framework.mediators.FrameworkMediator;
	import net.vicp.madz.framework.views.Framework;
	
	//Respond for InitializeFramework notification
	public class InitializeFrameworkCommand extends SimpleCommand implements ICommand
	{
		public function InitializeFrameworkCommand()
		{
			super();
		}
		
		public override function execute(notification:INotification):void
		{
			var framework:Framework = notification.getBody() as Framework;
         	facade.registerMediator(new FrameworkMediator(framework));
		}
	}
}