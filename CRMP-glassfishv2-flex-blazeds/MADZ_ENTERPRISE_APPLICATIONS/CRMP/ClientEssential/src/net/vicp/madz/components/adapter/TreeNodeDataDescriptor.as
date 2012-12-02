package net.vicp.madz.components.adapter
{
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.List;
	import mx.controls.treeClasses.ITreeDataDescriptor;
	
	public class TreeNodeDataDescriptor implements ITreeDataDescriptor
	{
		
		public function TreeNodeDataDescriptor()
		{
		}
		
		public function getChildren(node:Object, model:Object=null):ICollectionView
		{
			return (node as TreeNode).getChildren();
		}
		
		public function hasChildren(node:Object, model:Object=null):Boolean
		{
			return (node as TreeNode).hasChildren();
		}
		
		public function isBranch(node:Object, model:Object=null):Boolean
		{
			return hasChildren(node, model);
		}
		
		public function getData(node:Object, model:Object=null):Object
		{
			return (node as TreeNode).getData();
		}
		
		public function addChildAt(parent:Object, newChild:Object, index:int, model:Object=null):Boolean
		{
			if (!(parent instanceof TreeNode)) {
				return false;
			}
			
			if (!(newChild instanceof TreeNode)) {
				return false;
			}
			
			(parent as TreeNode).addChild(newChild as TreeNode);
			
			return true;
		}
		
		public function removeChildAt(parent:Object, child:Object, index:int, model:Object=null):Boolean
		{
			if (!(parent instanceof TreeNode)) {
				return false;
			}
			
			if (!(child instanceof TreeNode)) {
				return false;
			}
			
			(parent as TreeNode).removeChild(child as TreeNode);
			
			return true;
		}
	}
}