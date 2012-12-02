package net.vicp.madz.biz.common.vo
{
	
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.query.CategoryQTO")]
	public class CategoryQTO extends StandardQTO
	{
		public var id:String;
    	public var name:String;
    	public var description:String;

    	
		public function CategoryQTO()
		{
		}
		
		public function toString():String{
			return "CategoryQTO:[id = " + id + ", name = " + name + ", description = " + description;
		}
	}
}