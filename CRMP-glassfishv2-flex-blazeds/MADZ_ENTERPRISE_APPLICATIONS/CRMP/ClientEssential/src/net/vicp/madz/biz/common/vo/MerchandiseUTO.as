package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.update.MerchandiseUTO")]
	public class MerchandiseUTO
	{
		public var id:String;
		public var code:String;
		public var name:String;
		public var suggestPrice:Number;
		public var description:String ;
		public var category:String;
		public var unitofMeasureId:String;
				
		public function MerchandiseUTO()
		{
		}

	}
}