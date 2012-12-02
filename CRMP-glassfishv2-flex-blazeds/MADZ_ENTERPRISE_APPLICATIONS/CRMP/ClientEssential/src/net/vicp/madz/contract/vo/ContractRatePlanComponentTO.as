package net.vicp.madz.contract.vo
{
	import net.vicp.madz.biz.common.vo.MerchandiseQTO;
	import net.vicp.madz.biz.common.vo.UnitOfMeasureQTO;
	
	[Bindable]
	[RemoteClass(alias="net.madz.module.contract.ContractRatePlanComponentTO")]
	public class ContractRatePlanComponentTO
	{
		public var id:String;
		public var updatedBy:String;
		public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public var chargeType:String;
		public var chargeModel:String;
		public var chargeRate:Number;
		public var active:Boolean;
		public var contract:ContractTO;
		public var merchandise:MerchandiseQTO;
		public var unitOfMeasure:UnitOfMeasureQTO;
		
		public function ContractRatePlanComponentTO()
		{
		}
	}
}