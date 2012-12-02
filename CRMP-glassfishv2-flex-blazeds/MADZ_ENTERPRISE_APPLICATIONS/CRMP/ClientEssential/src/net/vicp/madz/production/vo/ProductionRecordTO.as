package net.vicp.madz.production.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.production.ProductionRecordTO")]
	public class ProductionRecordTO
	{
		public var billed : Boolean;
		public var description : String;
		public var referenceNumber : String;
		public var uomId : String;
		public var uomName : String;
		public var quantity : Number;
		public var productionTime : Date;
		public var unitOfProjectId : String;
		public var unitOfProjectName : String;
		public var mixingPlantId : String;
		public var mixingPlantName : String;
		public var partId : String;
		public var partName : String;
		
		public var truckNumber : String;
		public var projectOwnerName : String;
		public var unitOfProjectOwnerName : String;
		
		
		public var id : String;
		public var updatedByName : String;
		public var updatedOn : Date;
		public var createdByName : String;
		public var createdOn : Date;
		public var deleted : Boolean;
		
		
		public function ProductionRecordTO()
		{
		}
	}
}