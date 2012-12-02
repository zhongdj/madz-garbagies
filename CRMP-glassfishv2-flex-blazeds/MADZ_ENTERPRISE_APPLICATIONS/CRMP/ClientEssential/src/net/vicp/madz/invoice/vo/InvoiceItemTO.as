package net.vicp.madz.invoice.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.billing.InvoiceItemTO")]
	public class InvoiceItemTO
	{
		public var state:Boolean;
		public var quantity:Number;
		public var amount:Number;
		public var merchandiseId:String;
		public var merchandiseName:String;
		public var invoiceId:String;
		public var unitOfMeasureId:String;
		public var unitOfMeasureName:String;
		
		public var id:String;
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function InvoiceItemTO()
		{
		}
	}
}