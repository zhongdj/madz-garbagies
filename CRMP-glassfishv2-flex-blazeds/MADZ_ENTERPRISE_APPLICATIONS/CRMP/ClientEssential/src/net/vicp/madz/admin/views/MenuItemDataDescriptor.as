package net.vicp.madz.admin.views
{
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.List;
	import mx.controls.Menu;
	import mx.controls.treeClasses.ITreeDataDescriptor;
	
	import net.vicp.madz.admin.vo.MenuItemTO;
	
	public class MenuItemDataDescriptor implements ITreeDataDescriptor
	{
		private var menuItems:ArrayCollection;
		
		public function MenuItemDataDescriptor(menuItemArray:ArrayCollection)
		{
			this.menuItems = menuItemArray;
		}
		
		public function getChildren(node:Object, model:Object=null):ICollectionView
		{
			var itemTO:MenuItemTO = node as MenuItemTO;
			var children : ArrayCollection = new ArrayCollection();
			for (var i:int = 0; i < menuItems.length; i ++) {
				if (null == (menuItems[i] as MenuItemTO).parentMenuItemId) {
					continue;
				}
				
				if (itemTO.id == (menuItems[i] as MenuItemTO).parentMenuItemId) {
					children.addItem(menuItems[i]);
					continue;
				}
			}
			return children;
		}
		
		public function hasChildren(node:Object, model:Object=null):Boolean
		{
			var itemTO:MenuItemTO = node as MenuItemTO;
			var children : ArrayCollection = new ArrayCollection();
			for (var i:int = 0; i < menuItems.length; i ++) {
				if (null == (menuItems[i] as MenuItemTO).parentMenuItemId) {
					continue;
				}
				
				if (itemTO.id == (menuItems[i] as MenuItemTO).parentMenuItemId) {
					return true;
				}
			}
			return false;
		}
		
		public function isBranch(node:Object, model:Object=null):Boolean
		{
			return hasChildren(node, model);
		}
		
		public function getData(node:Object, model:Object=null):Object
		{
			return node;
		}
		
		public function addChildAt(parent:Object, newChild:Object, index:int, model:Object=null):Boolean
		{
			return false;
		}
		
		public function removeChildAt(parent:Object, child:Object, index:int, model:Object=null):Boolean
		{
			return false;
		}
	}
}