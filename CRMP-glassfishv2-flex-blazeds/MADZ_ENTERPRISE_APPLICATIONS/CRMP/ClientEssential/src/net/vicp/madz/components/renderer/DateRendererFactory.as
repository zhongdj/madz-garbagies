package net.vicp.madz.components.renderer
{
	import mx.core.IFactory;
	
	public class DateRendererFactory implements IFactory
	{
		private var columnName:String;
		
		public function DateRendererFactory(columnName:String)
		{
			this.columnName = columnName;
		}
		
		public function newInstance():*
		{
			var render:SimpleDateRenderer =  new SimpleDateRenderer();
			render.dateFieldName = columnName;
			return render;
		}
	}
}