package net.vicp.madz.utils
{
	import mx.collections.ArrayCollection;

	public class SelectUtils
	{
		public static function getSelectedItem(id : String, collection: Array) : Object {
			if (null == id || null == collection || 0 >= collection.length) {
				return null;
			}
			
			for (var i:int = 0; i <collection.length; i ++) {
				if (id == collection[i]["id"]) {
					return collection[i];
				}
			}
			return null;
		}
		
		public function SelectUtils()
		{
		}
	}
}