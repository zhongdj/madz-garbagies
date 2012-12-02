package net.vicp.madz.contract.vo
{
	import net.vicp.madz.account.vo.ContactTO;
	
	[Bindable]
	[RemoteClass(alias="net.madz.module.contract.ProjectTO")]
	public class ProjectTO
	{
		public var id:String;
		public var name:String;
		public var updatedBy:String;
		public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		private var billToContact:ContactTO;
		private var shipToContact:ContactTO;
		private var payerContact:ContactTO;
		private var soldToContact:ContactTO;
		
		public function ProjectTO()
		{
		}
	}
}