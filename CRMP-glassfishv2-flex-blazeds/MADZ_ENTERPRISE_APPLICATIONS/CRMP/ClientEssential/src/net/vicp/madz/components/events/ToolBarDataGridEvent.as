package net.vicp.madz.components.events
{
	import flash.events.Event;

	public class ToolBarDataGridEvent extends Event
	{
		public static const PAGE_SIZE_CHANGE:String = "pageSizeChange";
		public static const END_PAGE:String = "endPage";
		public static const HOME_PAGE:String = "homePage";
		public static const NEXT_PAGE:String = "nextPage";
		public static const PREVIOUS_PAGE:String = "previousPage";
		
		public static const DATA_QUERY:String = "dataQuery";
		public static const DATA_REFRESH:String = "dataRefresh";
		public static const DATA_CREATE:String = "dataCreate";
		public static const DATA_DELETE:String = "dataDelete";
		public static const DATA_MODIFY:String = "dataModify";
		public static const DATA_MODIFY_SUBMIT:String = "dataModifySubmit";
		public static const DATA_MODIFY_ROLLBACK:String = "dataModifyRollback";
		public static const MODEL_CHANGED:String = "modelChanged";
		public static const VISABLE_MODEL_CHANGED:String = "visableModelChanged";
		
		public function ToolBarDataGridEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		private var submitingData:Array;
		private var createdData:Object;
		private var modifiedData:Object;
		private var deletedData:Array;
		
		private var start:int;
		private var end:int;
		
		public function getSubmitingData():Array{
			return submitingData;
		}
		
		public function setSubmitingData(data:Array):void{
			this.submitingData = data;
		}
		
		public function getCreatedData():Object{
			return createdData;
		}
		
		public function setCreatedData(data:Object):void{
			createdData = data;
		}
		
		public function getModifiedData():Object{
			return modifiedData;
		}
		
		public function setModifiedData(data:Object):void{
			modifiedData = data;
		}
		
		public function getDeletedData():Array{
			return deletedData;
		}
		
		public function setDeletedData(data:Array):void{
			deletedData = data;
		}
		
		public function getStartIndex():int{
			return start;
		}
		
		public function setStartIndex(startIndex:int):void{
			start=startIndex;
		}
		
		public function getEndIndex():int{
			return end;
		}
		
		public function setEndIndex(endIndex:int):void{
			end = endIndex;
		}
	}
}