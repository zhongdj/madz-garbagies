package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.query.MerchandiseQTO")]
	public class MerchandiseQTO
	{
	    public var id:String;
	    public var code:String;
	    public var name:String;
	    public var description:String;
	    public var suggestPrice:Number;
	    public var category:String;
	    public var categoryName:String;
	    public var unitOfMeasureId:String;
	    public var unitOfMeasureName:String;
	    public var updatedBy:String;
	    public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;

		public function MerchandiseQTO()
		{
		}

	}
}