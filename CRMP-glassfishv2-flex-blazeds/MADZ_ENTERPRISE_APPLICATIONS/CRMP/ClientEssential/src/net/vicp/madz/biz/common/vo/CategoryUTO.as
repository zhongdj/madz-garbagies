package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.update.CategoryUTO")]
	public class CategoryUTO
	{
		public var id:String;
    	public var name:String;
    	public var description:String;
    	
		public function CategoryUTO()
		{
		}
		
		public function toString():String{
			return "CategoryUTO:[id = " + id + ", name = " + name + ", description = " + description;
		}
	}
}