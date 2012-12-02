package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.query.StandardQTO")]
	public class StandardQTO
	{
		public var updatedBy:String;
	    public var updatedOn:Date;
		public var createdBy:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function StandardQTO()
		{
		}

	}
}