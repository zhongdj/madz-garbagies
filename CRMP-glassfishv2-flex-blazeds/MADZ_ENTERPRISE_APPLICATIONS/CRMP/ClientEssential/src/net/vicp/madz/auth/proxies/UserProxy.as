package net.vicp.madz.auth.proxies
{
	import mx.controls.Alert;
	import mx.messaging.ChannelSet;
	import mx.messaging.config.ServerConfig;
	import mx.rpc.AsyncResponder;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import net.vicp.madz.auth.facades.AuthenticateFacade;
	import net.vicp.madz.auth.vo.CompanyCTO;
	
	import org.puremvc.as3.interfaces.IProxy;
	import org.puremvc.as3.patterns.proxy.Proxy;
	
	public class UserProxy extends Proxy implements IProxy {
		public static const NAME:String = "UserProxy";
		
		private var remoteObject:RemoteObject;
		
		// Define a ChannelSet object.
		private var channelSet:ChannelSet;
		
		// Define an AsyncToken object.
		public var token:AsyncToken;
		
		private var userName:String;
		
		public function UserProxy() {
			super(NAME);
			initialize();
		}
		
		private function initialize():void{
			if(remoteObject == null){
				remoteObject = new RemoteObject();
				remoteObject.destination = "CommonObjectQuery";
				remoteObject.addEventListener(ResultEvent.RESULT,handleResultEvent);
				remoteObject.addEventListener(FaultEvent.FAULT,handleFaultEvent);
			}
			if(channelSet == null){
				channelSet = ServerConfig.getChannelSet(remoteObject.destination);
			}
		}
		
		private function handleResultEvent(event:ResultEvent):void{
			Alert.show(event.result as String);
		}
		
		private function handleFaultEvent(event:FaultEvent):void{
			Alert.show(event.fault as String);
		}
		
		//Login Method
		public function authenticate(username:String, password:String):void {
			
			//TODO For testing purposees, lets just accept all users
			if (channelSet.authenticated == false) {
				token = channelSet.login(username, password);
				this.userName = username;
				// Add result and fault handlers.
				token.addResponder(new AsyncResponder(handleLoginSuccess, handleLoginFailed));
			}
		}
		
		//Logout Method
		public function logout():void {
			trace(userName + " is logging out ...");
			userName = "";
			//For testing purposees, lets just unauthenticate all users
			token = channelSet.logout();
			token.addResponder(new AsyncResponder(handleLogoutResult,handleLogoutFault));
		}
		
		private function handleLoginSuccess(event:ResultEvent, token:Object=null):void{
			trace(event.result);
			sendNotification(AuthenticateFacade.AUTHENTICATED_SUCCESS, userName);
		}
		
		private function handleLoginFailed(event:FaultEvent, token:Object=null):void{
			trace(event.fault);
			switch(event.fault.faultCode) {
				case "Client.Authentication":
				default:
					Alert.show("Login failure: " + event.fault.faultString);
			}
			sendNotification(AuthenticateFacade.AUTHENTICATED_FAILED);
		}
		
		
		
		private function handleLogoutResult(event:ResultEvent, token:Object=null):void{
			this.sendNotification(AuthenticateFacade.UNAUTHENTICATED_SUCCESS);
			Alert.show("Logout Successfully");
		}
		
		private function handleLogoutFault(event:FaultEvent, token:Object=null):void{
			Alert.show("Logout failure: " + event.fault.faultString);
		}
		
		public function register(companyCTO:CompanyCTO):void{
			//TODO call remote object to create companyCTO
			trace(companyCTO.adminName);
			trace(companyCTO.adminPass);
			trace(companyCTO.name);
			trace(companyCTO.artificialPersonName);
			trace(companyCTO.address);
			
			var registerRemote:RemoteObject = new RemoteObject("Register");
			registerRemote.addEventListener(ResultEvent.RESULT,handleRegisterSuccess);
			registerRemote.addEventListener(FaultEvent.FAULT,handleRegisterFailed);
			registerRemote.registerCompany(companyCTO);
		}
		
		private function handleRegisterSuccess(event:ResultEvent):void{
			trace("Register Result:" +event.result);
			sendNotification(AuthenticateFacade.REGISTER_SUCCESS);
		}
		
		private function handleRegisterFailed(event:FaultEvent):void{
			trace("Register Failed:" + event.fault);
			sendNotification(AuthenticateFacade.REGISTER_FAILED);
		}
		
	}
}