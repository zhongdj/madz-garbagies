package net.vicp.madz.bootstrap.commands
{
    import net.vicp.madz.auth.facades.AuthenticateFacade;
    import net.vicp.madz.auth.mediators.*;
    import net.vicp.madz.bootstrap.mediators.*;
    import net.vicp.madz.framework.facades.FrameworkFacade;
    import net.vicp.madz.framework.mediators.*;
    
    import org.puremvc.as3.interfaces.ICommand;
    import org.puremvc.as3.interfaces.INotification;
    import org.puremvc.as3.patterns.command.SimpleCommand;
    
    public class StartupCommand extends SimpleCommand implements ICommand
    {	
        override public function execute( note:INotification ):void{
        	var clientWorkbench:ClientEssential = note.getBody() as ClientEssential;
     		
     		var authenticateModule:AuthenticateFacade = AuthenticateFacade.getInstance();
     		authenticateModule.startUp(clientWorkbench);
	       	
	       	var framework:FrameworkFacade = FrameworkFacade.getInstance();
	       	framework.startUp(clientWorkbench);
	       	
         	
         	facade.registerMediator(new ApplicationMediator(clientWorkbench));
         	facade.registerMediator(new HeaderMediator(clientWorkbench.header));
        }
    }
}