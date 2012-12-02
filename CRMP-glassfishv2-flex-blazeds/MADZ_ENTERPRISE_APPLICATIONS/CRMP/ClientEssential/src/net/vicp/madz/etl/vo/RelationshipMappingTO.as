package net.vicp.madz.etl.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.service.etl.to.ETLColumnContentMappingDescriptorTO")]
	public class RelationshipMappingTO
	{

		public var rawData:String;
		public var mappingKey:String;
		public var mappedData:String;
		public var columnDescriptorId:String;
		
		public var id:String;
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function RelationshipMappingTO()
		{
		}
	}
}