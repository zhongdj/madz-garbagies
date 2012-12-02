package net.vicp.madz.auth.events
{
	import flash.events.Event;

	public class AuthenticateEvent extends Event
	{
		public static const AUTHENTICATE:String = "Authenticate";
		public function AuthenticateEvent(userName:String,password:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			this.userName = userName;
			this.password = password;
			super(AUTHENTICATE, bubbles, cancelable);
		}
		
		public var userName:String;
		public var password:String;
		
	}
}