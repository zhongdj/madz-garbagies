package net.vicp.madz.admin.vo
{
	import mx.collections.ArrayCollection;

	[Bindable]
	[RemoteClass(alias="net.madz.infra.security.to.RoleTO")]
	public class RoleTO
	{
		public var id:String;
		public var name:String;
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public var menuItems:ArrayCollection;
		
		
		public function RoleTO()
		{
		}
	}
}