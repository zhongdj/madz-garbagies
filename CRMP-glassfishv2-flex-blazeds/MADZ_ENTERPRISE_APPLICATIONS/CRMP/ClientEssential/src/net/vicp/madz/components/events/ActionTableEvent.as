package net.vicp.madz.components.events
{
	import flash.events.Event;

	public class ActionTableEvent extends Event
	{
		//////////////////////////////////////////////////////
		//// Events : Data Operation Events
		//////////////////////////////////////////////////////
		public static const REQUEST_DATA_QUERY:String = "requestDataQuery";
		public static const SUBMIT_QUERY_CONDITION:String = "submitQueryCondition";
		public static const DATA_REFRESH:String = "dataRefresh";
		public static const DATA_CREATE:String = "dataCreate";
		public static const SUBMIT_CREATE_DATA:String = "submitCreateData";
		public static const DATA_DELETE:String = "dataDelete";
		public static const REQUEST_DATA_MODIFY:String = "requestDataModify";
		public static const BEFORE_DATA_MODIFY:String = "beforeDataModify";
		public static const DATA_MODIFY:String = "dataModify";
		public static const DATA_MODIFY_SUBMIT:String = "dataModifySubmit";
		public static const DATA_MODIFY_ROLLBACK:String = "dataModifyRollback";
		public static const DATA_CREATE_RESULT:String = "dataCreateResult";
		public static const DATA_MODIFY_SUBMIT_RESULT:String = "dataModifiedSubmitResult";
		public static const DATA_DELETE_RESULT:String = "dataDeleteResult";
		
		//////////////////////////////////////////////////////
		//// Events : Model Events
		//////////////////////////////////////////////////////
		public static const MODEL_CHANGED:String = "modelChanged";
		public static const VISABLE_MODEL_CHANGED:String = "visableModelChanged";
		
		//////////////////////////////////////////////////////
		//// Events : Page Operation Events
		//////////////////////////////////////////////////////
		public static const PAGE_SIZE_CHANGE:String = "pageSizeChange";
		public static const END_PAGE:String = "endPage";
		public static const HOME_PAGE:String = "homePage";
		public static const NEXT_PAGE:String = "nextPage";
		public static const PREVIOUS_PAGE:String = "previousPage";
		
		public function ActionTableEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		private var _queryCondition:Object;
		private var submitingData:Array;
		private var createdData:Object;
		private var modifiedData:Object;
		private var deletedData:Array;
		//For request to modify
		private var requestModifyRowIndex:int;
		private var requestModifyColumnIndex:int;
		private var requestMofifyData:Object;
		
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
		

		public function setQueryCondition(condition:Object):void{
			_queryCondition = condition;
		}
		
		public function getQueryCondition():Object{
			return _queryCondition;
		}
		
		private var modifiedIndex:int;
		
		public function getModifiedIndex():int{
			return modifiedIndex;
		}
		
		public function setModifiedIndex(index:int):void{
			modifiedIndex = index;
		}
		
		public function getRequestModifyColumnIndex():int{
			return requestModifyColumnIndex;
		}
		
		public function setRequestModifyColumnIndex(columnIndex:int):void{
			requestModifyColumnIndex = columnIndex;
		}
		
		public function getRequestModifyRowIndex():int{
			return requestModifyRowIndex;
		}
		
		public function setRequestModifyRowIndex(rowIndex:int):void{
			requestModifyRowIndex = rowIndex;
		}
		
		public function getRequestModifyData():Object{
			return requestMofifyData;
		}
		
		public function setRequestModifyData(data:Object):void{
			requestMofifyData = data;
		}
	}
}