package net.vicp.madz.invoice.vo
{
	[Bindable]
	[RemoteClass(name = "net.madz.module.billing.InvoiceStatixTO")]
	public class SummationOfProductValueTO
	{
		
		var unitOfProjectName : String;
		var quantity : Number;
		var amount : Number;
		var count : int;
		var productCode : String;
		
		
		public function SummationOfProductValueTO()
		{
		}
	}
}