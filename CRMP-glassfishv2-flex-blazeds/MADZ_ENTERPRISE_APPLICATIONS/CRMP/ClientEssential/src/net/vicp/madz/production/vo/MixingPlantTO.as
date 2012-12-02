package net.vicp.madz.production.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.production.ConcreteMixingPlantTO")]
	public class MixingPlantTO
	{
		
		public var name : String;
		public var operatorId : String;
		public var operatorName : String;
		
		public var id : String;
		public var updatedByName : String;
		public var updatedOn : Date;
		public var createdByName : String;
		public var createdOn : Date;
		public var deleted : Boolean;
		
		
		public function MixingPlantTO()
		{
		}
	}
}