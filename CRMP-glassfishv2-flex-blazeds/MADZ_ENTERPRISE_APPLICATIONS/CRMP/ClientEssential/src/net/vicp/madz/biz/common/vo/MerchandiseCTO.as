package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.create.MerchandiseCTO")]
	public class MerchandiseCTO
	{
		public var code:String;
		public var name:String;
		public var suggestPrice:Number;
		public var description:String;
		public var category:String;
		public var unitOfMeasureId:String;
				
		public function MerchandiseCTO()
		{
		}

	}
}