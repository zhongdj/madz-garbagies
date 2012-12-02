package net.vicp.madz.account.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.account.ContactTO")]
	public class ContactTO 
	{
		
		public var name:String;
		public var email:String;
		public var mobile:String;
		public var telephone:String;
		public var fax:String;
		public var sex:Boolean;
		public var contactType:String;
		public var accountId:String;
		public var accountName:String;
		
		public var id:String;
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function ContactTO()
		{
			super();
		}
	}
}