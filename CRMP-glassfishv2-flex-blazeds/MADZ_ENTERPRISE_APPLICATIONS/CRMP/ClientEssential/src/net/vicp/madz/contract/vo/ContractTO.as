package net.vicp.madz.contract.vo
{
	
	import mx.collections.ArrayCollection;
	
	import net.vicp.madz.account.vo.AccountTO;
	
	[Bindable]
	[RemoteClass(alias="net.madz.module.contract.ContractTO")]
	public class ContractTO
	{
		public var id:String;
		public var name:String;
		public var updatedBy:String;
		public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		
		public var state:String;
		public var effectiveStartDate:Date;
		public var effectiveEndDate:Date;
		public var paymentTerm:String;
		public var billVolumnBaseline:int;
		public var billPeriodBaseline:int;
		public var billPeriodBaselineUnit:String;
		public var active:Boolean;
		
		public var payerAccount:AccountTO;
		public var billToAccount:AccountTO;
		public var soldToAccount:AccountTO;
		public var shipToAccount:AccountTO;
		public var billVolumnBaselineUnitId:String;
		public var billVolumnBaselineUnitName:String;
		public var originalContractId:String;
		public var originalContractName:String;
		
		public var unitOfProjects :ArrayCollection;
		public var ratePlanComponents :ArrayCollection;
		
		public function ContractTO()
		{
		}
	}
}