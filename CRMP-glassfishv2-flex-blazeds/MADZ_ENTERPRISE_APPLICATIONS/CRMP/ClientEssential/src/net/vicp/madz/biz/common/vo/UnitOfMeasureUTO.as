package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.update.UnitOfMeasureUTO")]
	public class UnitOfMeasureUTO extends UnitOfMeasureCTO
	{
		public var id:String;
		public var deleted:Boolean;
		
		public function UnitOfMeasureUTO()
		{
		}
	}
}