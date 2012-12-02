package net.vicp.madz.invoice.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.service.pr2invoice.AccountRunTaskTO")]
	public class AccountRunTaskTO
	{
		
		public var startDate : Date;
		public var endDate : Date;
		public var executeDate : Date;
		
		public var executeStartTime : Date;
		public var executeEndTime : Date;
		
		public var state : String;
		public var generatedInvoiceQuantity : int;
		public var generatedInvoiceAmount : int;
		
		public var scannedProductionRecordQuantity : int;
		public var scannedProductivity : Number = 0;
		
		public function AccountRunTaskTO()
		{
		}
	}
}