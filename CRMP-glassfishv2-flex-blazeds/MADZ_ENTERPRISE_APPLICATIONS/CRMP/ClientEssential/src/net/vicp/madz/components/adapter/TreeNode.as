package net.vicp.madz.components.adapter
{
	import mx.collections.ArrayCollection;
	
	public class TreeNode
	{
		
		private var data : Object;
		private var labelField : String;
		private const children :ArrayCollection = new ArrayCollection();
		
		public function TreeNode(data:Object, labelField : String) 
		{
			this.data = data;
			this.labelField = labelField;
		}
		
		public function get label() : String 
		{
			return data[labelField];
		}
		
		public function addChild(childData : TreeNode) : Boolean {
			var found : TreeNode = null; 
			for (var i:int = 0; i < children.length; i++) {
				if (children[i].getData()[labelField] == childData.getData()[labelField]) {
					found = children[i];
					break;
				}
			}
			if (null == found) {
			    children.addItem(childData);
				return true;
			} 
            return false;
		}
		
		public function removeChild(childData : TreeNode) : Boolean {
			var index :int = -1;
	        
			for (var i:int; children.length; i ++) 
			{
				if (childData == children[i]) 
				{
					index = i;
					break;
				}
			}
			
			if (-1 != index) {
				this.children.removeItemAt(index);
			}
			
			return index > -1;
		}
		
		public function hasChildren() : Boolean {
			return this.children.length > 0;
		}
		
		public function getChildren() : ArrayCollection {
			return this.children;
		}
		
		public function getData() : Object {
			return data;
		}
		
		
	}
}