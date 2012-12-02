package net.vicp.madz.invoice.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.service.pr2invoice.AllAccountRunTaskTO")]
	public class AllAccountRunTaskTO extends AccountRunTaskTO
	{
		public var scannedAccountQuantity : int;
		
		public function AllAccountRunTaskTO()
		{
			super();
		}
	}
}