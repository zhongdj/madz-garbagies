package net.vicp.madz.admin.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.infra.security.to.MenuItemTO")]
	public class MenuItemTO
	{
		public var id:String;
		public var name:String;
		public var icon:String;
		public var viewId:String;
		public var parentMenuItemId:String;
		public var parentMenuItemName:String;
	
		public var updatedByName:String;
		public var updatedOn:Date;
		public var createdByName:String;
		public var createdOn:Date;
		public var deleted:Boolean;
		
		public function MenuItemTO()
		{
		}
	}
}