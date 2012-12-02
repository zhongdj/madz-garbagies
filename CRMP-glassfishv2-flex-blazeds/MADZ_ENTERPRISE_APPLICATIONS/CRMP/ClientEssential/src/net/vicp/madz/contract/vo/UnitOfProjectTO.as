package net.vicp.madz.contract.vo
{
	import net.vicp.madz.account.vo.ContactTO;

	[Bindable]
	[RemoteClass(alias="net.madz.module.contract.UnitOfProjectTO")]
	public class UnitOfProjectTO
	{
		public var id:String;
		public var name:String;
		public var updatedBy:String;
		public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		public var projectId:String;
		public var projectName:String;
		public var startDate:Date;
		public var endDate:Date;
		public var billToContact:ContactTO;
		public var shipToContact:ContactTO;
		public var payerContact:ContactTO;
		public var soldToContact:ContactTO;
		public var contractId:String;
		
		public function UnitOfProjectTO()
		{
		}
	}
}