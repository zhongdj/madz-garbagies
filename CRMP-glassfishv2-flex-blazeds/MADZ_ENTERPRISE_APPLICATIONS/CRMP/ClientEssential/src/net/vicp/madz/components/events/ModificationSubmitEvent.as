package net.vicp.madz.components.events
{
	import flash.events.Event;

	public class ModificationSubmitEvent extends Event
	{
		private const NAME:String = "ModificationSubmitEvent";
		private var tasks:Array;
		
		public function ModificationSubmitEvent(tasks:Array,bubbles:Boolean=false, cancelable:Boolean=false)
		{
			this.tasks = tasks;
			super(NAME, bubbles, cancelable);
		}
		
		public function get tasks():Array{
			return tasks;
		}
	}
}