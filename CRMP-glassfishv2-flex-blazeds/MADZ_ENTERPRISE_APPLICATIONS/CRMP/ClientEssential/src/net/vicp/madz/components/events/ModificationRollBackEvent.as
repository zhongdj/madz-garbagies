package net.vicp.madz.components.events
{
	import flash.events.Event;

	public class ModificationRollBackEvent extends Event
	{
		private const NAME:String = "ModificationRollBackEvent";
		private var tasks:Array;
		
		public function ModificationRollBackEvent(tasks:Array,bubbles:Boolean=false, cancelable:Boolean=false)
		{
			this.tasks = tasks;
			super(NAME, bubbles, cancelable);
		}
		
		public function get tasks():Array{
			return tasks;
		}
		
	}
}