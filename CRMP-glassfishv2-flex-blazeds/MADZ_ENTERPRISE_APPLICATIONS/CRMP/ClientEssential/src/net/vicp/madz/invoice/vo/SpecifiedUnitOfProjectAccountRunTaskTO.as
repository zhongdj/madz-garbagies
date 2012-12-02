package net.vicp.madz.invoice.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.service.pr2invoice.SpecifiedUnitOfProjectAccountRunTaskTO")]
	public class SpecifiedUnitOfProjectAccountRunTaskTO extends AccountRunTaskTO
	{
		public var specifiedUnitOfProjectId : String;
		public var specifiedUnitOfProjectName : String;
		
		public function SpecifiedUnitOfProjectAccountRunTaskTO()
		{
		}
	}
}