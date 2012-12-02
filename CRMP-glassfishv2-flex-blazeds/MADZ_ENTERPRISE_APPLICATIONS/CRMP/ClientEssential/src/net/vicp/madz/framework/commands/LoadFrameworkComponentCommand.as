package net.vicp.madz.framework.commands
{
	import net.vicp.madz.framework.facades.FrameworkFacade;
	import net.vicp.madz.framework.mediators.*;
	import net.vicp.madz.framework.proxies.*;
	import net.vicp.madz.framework.views.*;
	
	import org.puremvc.as3.interfaces.ICommand;
	import org.puremvc.as3.interfaces.INotification;
	import org.puremvc.as3.patterns.command.SimpleCommand;
	
	public class LoadFrameworkComponentCommand extends SimpleCommand implements ICommand
	{
		
		public function LoadFrameworkComponentCommand()
		{
			super();
		}
		
		public override function execute(notification:INotification):void
		{
			var framework:Framework = notification.getBody() as Framework;
			framework.initializeInside();
			//create & register navigator
			facade.registerProxy(new MenuProxy());
			facade.registerCommand(FrameworkFacade.FETCH_MENU,MenuFetchCommand);
			facade.registerMediator(NavigatorMediator.getInstance(framework.navigatorBox));
			
			//create & register view
			facade.registerProxy(new ViewConfigMapProxy());
			facade.registerCommand(FrameworkFacade.FETCH_VIEW_CONFIG_MAP,ViewConfigMapFetchCommand);
			facade.registerMediator(ViewContainerMediator.getInstance(framework.viewBox));
			
			sendNotification(FrameworkFacade.FETCH_MENU);
			sendNotification(FrameworkFacade.FETCH_VIEW_CONFIG_MAP);
		}
		
	}
}