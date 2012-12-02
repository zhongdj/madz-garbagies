package net.vicp.madz.invoice.vo
{
	import mx.collections.ArrayCollection;

	[Bindable]
	[RemoteClass(alias="net.madz.module.billing.InvoiceTO")]
	public class InvoiceTO
	{
		
		public var invoiceNumber:String;
		public var referenceNumber:String;
		public var state:String;
		public var dueDate:Date;
		public var postDate:Date;
		public var totalQuantity:Number;
		public var totalAmount:Number;
		public var unpaidAmount:Number;
		public var payerAccountId:String;
		public var payerAccountName:String;
		public var billToAccountId:String;
		public var billToAccountName:String;
		public var soldToAccountId:String;
		public var soldToAccountName:String;
		public var shipToAccountId:String;
		public var shipToAccountName:String;
		
		public var id:String;
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public var crmpInvoiceItemSet:ArrayCollection;
		
		public function InvoiceTO()
		{
		}
	}
}