package net.vicp.madz.biz.common.vo
{
	[Bindable]
	[RemoteClass(alias="net.madz.module.common.to.create.CategoryCTO")]
	public class CategoryCTO
	{
		public var name:String ;
	    public var description:String;
	    
		public function CategoryCTO()
		{
		}

	}
}