package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.query.UnitOfMeasureQTO")]
	public class UnitOfMeasureQTO
	{
		public var id:String;
		public var name:String;
    	public var description:String;
		public var updatedBy:String;
	    public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function UnitOfMeasureQTO()
		{
		}
	}
}