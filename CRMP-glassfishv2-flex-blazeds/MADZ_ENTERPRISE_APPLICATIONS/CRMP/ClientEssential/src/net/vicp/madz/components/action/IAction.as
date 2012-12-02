package net.vicp.madz.components.action
{
	public interface IAction
	{
		function get id():String;
		
		function get label():String;
		
		function get toolTip():String;
		
		function get icon():Class;
		
		function get enableIcon():Class;
		
		function get disableIcon():Class;
		
		function get action():Function;
		
		function set action(handler:Function):void;
		
		function get context():Object;
		
		function set context(ctx:Object):void;
	}
}