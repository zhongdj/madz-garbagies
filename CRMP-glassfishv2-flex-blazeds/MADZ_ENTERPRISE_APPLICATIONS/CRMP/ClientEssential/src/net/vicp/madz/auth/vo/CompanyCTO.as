package net.vicp.madz.auth.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.infra.security.to.create.CompanyCTO")]
	public class CompanyCTO
	{
		public function CompanyCTO()
		{
		}

		public var adminName:String;
		public var adminPass:String;
		public var name:String;
		public var artificialPersonName:String;
		public var address:String;
	}
}