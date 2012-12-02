package net.vicp.madz.production.vo
{
	[Bindable]
	[RemoteClass(name = "net.madz.module.production.ProductionRecordStatixTO")]
	public class ProductionRecordStatixTO
	{
		
		var unitOfProjectName : String;
		var quantity : Number;
		var count : int;
		var productCode : String;
		
		public function ProductionRecordStatixTO()
		{
		}
	}
}