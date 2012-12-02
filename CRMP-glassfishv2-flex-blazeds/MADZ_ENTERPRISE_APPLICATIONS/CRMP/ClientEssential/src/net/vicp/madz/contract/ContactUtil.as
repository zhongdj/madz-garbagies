package net.vicp.madz.contract
{
	import flash.utils.Dictionary;
	
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;

	public class ContactUtil
	{
		
		private const contactCache : Dictionary = new Dictionary(); 
		
		private var accountProxy:RemoteObject = new RemoteObject("Account");
		
		
		public function ContactUtil()
		{
		}
		
		public function findContacts(accountId : String, contactType : String, listener : Function) : void {
			
			if (null == contactCache[accountId]) {
				accountProxy.addEventListener(ResultEvent.RESULT, function(event:ResultEvent) : void {
					contactCache[accountId] = event.result;
					listener.call(null, event.result);
				});
				accountProxy.findContacts(accountId, contactType);

			} else {
				listener.call(null, contactCache[accountId]);
			}
		}
	}
	
}