package net.vicp.madz.account.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.account.AccountTO")]
	public class AccountTO
	{
		public var name:String;
		public var shortName:String;
		
		public var id:String;
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function AccountTO()
		{
		}
	}
}