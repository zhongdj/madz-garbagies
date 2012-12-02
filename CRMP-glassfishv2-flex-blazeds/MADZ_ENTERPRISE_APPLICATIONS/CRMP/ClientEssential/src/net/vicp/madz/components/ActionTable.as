package net.vicp.madz.components
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextLineMetrics;
	import flash.utils.ByteArray;
	
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.controls.ButtonBar;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.controls.buttonBarClasses.ButtonBarButton;
	import mx.controls.dataGridClasses.DataGridItemRenderer;
	import mx.core.Container;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.events.DataGridEvent;
	import mx.events.FlexEvent;
	import mx.events.ItemClickEvent;
	import mx.events.ListEvent;
	import mx.events.ResizeEvent;
	
	import net.vicp.madz.components.action.Action;
	import net.vicp.madz.components.events.ActionTableEvent;
	
	//////////////////////////////////////////////////////
	////
	//// Metadata : Events
	////
	//////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////
	//// Events : Data Operation Events
	//////////////////////////////////////////////////////
	
	/**
	 *  Dispatched when the 'Query' Button on the built-in button bar was pressed down  
	 *	Usually application shall add event listeners to handle this event, and on dataQuery
	 * 	event, a dialog will be popped up on which user submit their query conditions. 
	 *  
	 *  @eventType net.vicp.madz.components.events.ActionTableEvent.REQUEST_DATA_QUERY
	 */
	[Event(name="requestDataQuery", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 *  Dispatched when the item in the table was double clicked  
	 *	Usually application shall add event listeners to handle this event, and on requestDataModify
	 * 	event, a dialog may be popped up to let user edit data. 
	 *  
	 *  @eventType net.vicp.madz.components.events.ActionTableEvent.REQUEST_DATA_MODIFY
	 */
	[Event(name="requestDataModify", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 *  Dispatched when application call submitQueryCondition method.
	 *  Application shall handle this event to execute query and communicate with data source.
	 * 
	 *  NOTE:
	 *  	The conditions will be save in cache for following Data Refresh Event.
	 *  	When query result returns, application shall call model setter to render the result. 
	 *  
	 *  @eventType net.vicp.madz.components.events.ActionTableEvent.SUBMIT_QUERY_CONDITION
	 */
	[Event(name="submitQueryCondition", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 *  Dispatched when the 'Refresh' Button on the built-in button bar was pressed down.  
	 *	The component registered a listener to this event, and will dispatch another submitQueryCondition event.
	 * 	The query condition submitted by submitQueryCondition event is the condition of the previous submitQueryCondition event.
	 *  
	 *  @eventType net.vicp.madz.components.events.ActionTableEvent.DATA_REFRESH
	 */
	[Event(name="dataRefresh", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 *  Dispatched when the 'Create' button in the built-in button bar was pressed down.
	 *  Usually application shall add event listeners to handle this event. 
	 *  When handling this event, application maybe pop up a dialog, and then user fill in a data form in the dialog, 
	 *  and push a 'Confirm' button and then call submitCreateData method to dispatch a submitCreateData Event
	 *  
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.DATA_CREATE
	 */ 
	[Event(name="dataCreate", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 *  Dispatched when the submitCreateData method was called.
	 *  Application shall handle this event, and communicate with datasource or server side application
	 *  to submit created data.
	 *  
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.SUBMIT_CREATE_DATA
	 */ 
	[Event(name="submitCreateData", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 *  Dispatched when the 'Delete' button in the built-in button bar was pressed down.
	 *  Usually application shall add event listeners to handle this event. 
	 *  When handling this event, application usually communicate with data source or server side application 
	 *  to execute delete operation.
	 *  
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.DATA_DELETE
	 */ 
	[Event(name="dataDelete", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when application call updateItem method.
	 * Component will handle this event, such as update the 'Submit','Rollback' buttons' state.
	 * For some advanced use case, application may design its own item modify use case, not to use column editors,
	 * but use custom dialog pop-up instead, make sure to call udpateItem method to notify ActionTable component
	 * there was an item changed.   
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.DATA_MODIFY
	 */ 
	[Event(name="dataModify", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when 'Submit' button in the built-in button bar was pressed down.
	 * Usually application shall add event listeners to handle this event. 
	 * When handling this event, application usually communicate with data source or server side application 
	 * to execute items modified operation.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.DATA_MODIFY_SUBMIT
	 */ 
	[Event(name="dataModifySubmit", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when 'Rollback' button in the built-in button bar was pressed down.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.DATA_MODIFY_ROLLBACK
	 */ 
	[Event(name="dataModifyRollback", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	[Event(name="dataCreateResult", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	[Event(name="dataModifiedSubmitResult", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	[Event(name="dataDeleteResult", type="net.vicp.madz.components.events.ActionTableEvent")]
	//////////////////////////////////////////////////////
	//// Events : Model Events
	//////////////////////////////////////////////////////
	
	/**
	 * Dispatched when model setter was called
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.MODEL_CHANGED
	 */ 
	[Event(name="modelChanged", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when visableModel setter was called
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.VISABLE_MODEL_CHANGED
	 */ 
	[Event(name="visableModelChanged", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	//////////////////////////////////////////////////////
	//// Events : Page Operation Events
	//////////////////////////////////////////////////////
	/**
	 * Dispatched when enter was pressed in pageSizeInput box.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.PAGE_SIZE_CHANGE
	 */ 
	[Event(name="pageSizeChange", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when enter was pressed in pageSizeInput box.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.PAGE_SIZE_CHANGE
	 */ 
	[Event(name="pageSizeChange", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when 'Home' button in the built-in button bar was pressed down.
	 * Usually Page Operations do not communicate with datasource or server side applications,
	 * unless the quantity is so huge.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.HOME_PAGE
	 */ 
	[Event(name="homePage", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when 'Home' button in the built-in button bar was pressed down.
	 * Usually Page Operations do not communicate with datasource or server side applications,
	 * unless the quantity is so huge.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.PREVIOUS_PAGE
	 */ 
	[Event(name="previousPage", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when 'Home' button in the built-in button bar was pressed down.
	 * Usually Page Operations do not communicate with datasource or server side applications,
	 * unless the quantity is so huge.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.NEXT_PAGE
	 */ 
	[Event(name="nextPage", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	
	/**
	 * Dispatched when 'Home' button in the built-in button bar was pressed down.
	 * Usually Page Operations do not communicate with datasource or server side applications,
	 * unless the quantity is so huge.
	 * 
	 * @eventType net.vicp.madz.components.events.ActionTableEvent.END_PAGE
	 */ 
	[Event(name="endPage", type="net.vicp.madz.components.events.ActionTableEvent")]
	
	/**
	 * Dispatched when following method invoked:
	 * 	(1)onDataDelete
	 * 	(2)onEndPage
	 * 	(3)onHome
	 * 	(4)onNext
	 * 	(5)onPageSizeChange
	 * 	(6)onPrevious
	 * 	(7)model setter
	 * 
	 */ 
	[Event(name="visableStateChange")]
	//////////////////////////////////////////////////////
	////
	//// Metadata : Styles
	////
	//////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////
	//// Styles : Button Skins
	//////////////////////////////////////////////////////
	/**
	 *  Skin for query button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/query_enable.PNG")]
	 */
	[Style(name="queryButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for query button disabled state
	 *
	 *  @default null
	 */
	[Style(name="queryButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for create button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/create_enable.PNG")]
	 */
	[Style(name="createButtonEnableSkin", type="Class", inherit="no")]
	/**
	 *  Skin for create button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/create_disable.PNG")]
	 */
	[Style(name="createButtonDisableSkin", type="Class", inherit="no")]
	
	/**
	 *  Skin for delete button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/delete_enable.PNG")]
	 */
	[Style(name="deleteButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for delete button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/delete_disable.PNG")]
	 */
	[Style(name="deleteButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for submit button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/submit_enable.PNG")]
	 */
	[Style(name="submitButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for submit button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/submit_disable.PNG")]
	 */
	[Style(name="submitButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for rollback button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/cancel_enable.PNG")]
	 */
	[Style(name="rollbackButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for rollback button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/cancel_disable.PNG")]
	 */
	[Style(name="rollbackButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for refresh button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/refresh_enable.PNG")]
	 */
	[Style(name="refreshButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for refresh button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/refresh_disable.PNG")]
	 */
	[Style(name="refreshButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for home button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/home_enable.PNG")]
	 */
	[Style(name="homeButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for home button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/home_disable.PNG")]
	 */
	[Style(name="homeButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for previous button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/previous_enable.PNG")]
	 */
	[Style(name="previousButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for previous button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/previous_disable.PNG")]
	 */
	[Style(name="previousButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for next button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/next_enable.PNG")]
	 */
	[Style(name="nextButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for next button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/next_disable.PNG")]
	 */
	[Style(name="nextButtonDisableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for end button enabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/end_enable.PNG")]
	 */
	[Style(name="endButtonEnableSkin", type="Class", inherit="no")]

	/**
	 *  Skin for end button disabled state
	 *
	 *  @default [Embed(source="net/vicp/madz/assets/TableIcons/end_disable.PNG")]
	 */
	[Style(name="endButtonDisableSkin", type="Class", inherit="no")]

    /*** a) Extend UIComponent. ***/
	public class ActionTable extends UIComponent
	{
		
		//////////////////////////////////////////////////////
		////
		//// Variables
		////
		//////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////
		//// Variables : Compositions of ActionTable
		//////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////
		//// Compositions of ActionTable : Toolbar Variables
		//////////////////////////////////////////////////////
		private var toolbarBox:HBox;
		private var toolbar:ButtonBar;
		private var pageLabel:Label;
		private var pageSizeInput:TextInput;
		private var pageTip:Label;
		
		//////////////////////////////////////////////////////
		//// Toolbar Variables : Actions of Toolbar
		//////////////////////////////////////////////////////
		private var homeAction:Action;
		private var previousAction:Action;
		private var nextAction:Action;
		private var endAction:Action;
		private var queryAction:Action;
		private var createAction:Action;
		private var submitAction:Action;
		private var rollbackAction:Action;
		private var deleteAction:Action;
		private var refreshAction:Action;
		
		//////////////////////////////////////////////////////
		//// Toolbar Variables : Button Provider of Toolbar
		//////////////////////////////////////////////////////
		[Bindable]
		private var buttonProvider:Array;
		
		//////////////////////////////////////////////////////
		//// Toolbar Variables : used for page operations handlers
		//// When data modification occurs before page operation
		//////////////////////////////////////////////////////		
		private var lastPageEvent:ActionTableEvent;
   		
   		//////////////////////////////////////////////////////
		//// Toolbar Variables : used for refresh operation
		//// When data query occurs before refresh.
		//// Component will cache latest query condition
		//////////////////////////////////////////////////////		
		private var queryCondition:Object;
		
		//////////////////////////////////////////////////////
		//// Compositions of ActionTable : Table Variables
		//////////////////////////////////////////////////////
		private var table:DataGrid;
		
		//////////////////////////////////////////////////////
		//// Table Variables : Core Data Provider Model of ActionTable 
		//////////////////////////////////////////////////////		
		[Bindable]
		private var modelProvider:Array;
		
		//////////////////////////////////////////////////////
		//// Table Variables : Modified Data Model
		//// When to submit model modification
		//////////////////////////////////////////////////////	
		private var modifiedModel:Array;
		
		//////////////////////////////////////////////////////
		//// Table Variables : Data Provider Model of Table 
		//////////////////////////////////////////////////////	
		[Bindable("collectionChange")]
		private var _visableModel:Array;
		//////////////////////////////////////////////////////
		//// Table Variables : Visable Model Navigators of Table
		//////////////////////////////////////////////////////	
		private var rows:int;
		private var currentPage:int;
		private var pages:int;
		private var start:int;
		private var end:int;
		private var pageSize:int;
		
		//////////////////////////////////////////////////////
		//// Table Variables : Columns of Table
		//////////////////////////////////////////////////////		
		[Bindable]
		private var _columnProvider:Array;
		
		//////////////////////////////////////////////////////
		//// Table Variables : Restore Navigators of Table
		//////////////////////////////////////////////////////		
		private var originalStartIndex:int;
		private var originalEndIndex:int; 
		private var originalModel:Array;
		
		
		//////////////////////////////////////////////////////
		//// Variables : Default Skins
		//////////////////////////////////////////////////////
		/*** d) Embed new skins used by the Button component. ***/
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/query_enable.PNG")]
		public static var queryEnableIcon:Class;
	
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/create_enable.PNG")]
		public static var createEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/delete_enable.PNG")]
		public static var deleteEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/delete_disable.PNG")]
		public static var deleteDisableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/submit_enable.PNG")]
		public static var submitEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/submit_disable.PNG")]
		public static var submitDisableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/cancel_enable.PNG")]
		public static var cancelEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/cancel_disable.PNG")]
		public static var cancelDisableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/refresh_enable.PNG")]
		public static var refreshEnableIcon:Class;
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/home_enable.PNG")]
		public static var homeEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/home_disable.PNG")]
		public static var homeDisableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/previous_enable.PNG")]
		public static var previousEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/previous_disable.PNG")]
		public static var previousDisableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/next_enable.PNG")]
		public static var nextEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/next_disable.PNG")]
		public static var nextDisableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/end_enable.PNG")]
		public static var endEnableIcon:Class;
		
		[Bindable]
		[Embed(source="net/vicp/madz/assets/TableIcons/end_disable.PNG")]
		public static var endDisableIcon:Class;
		
		//////////////////////////////////////////////////////
		////
		//// Constructor
		////
		//////////////////////////////////////////////////////
		/*** b) Implement the class constructor. ***/
		public function ActionTable()
		{
			super();
			setGridEventListeners();
		}
		
		//////////////////////////////////////////////////////
		////
		//// Methods
		////
		//////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////
		//// Methods : Add Event Handlers
		//////////////////////////////////////////////////////
		
		private function setGridEventListeners():void{
			addEventListener(DataGridEvent.ITEM_EDIT_BEGIN,onEditBegin,true);
			addEventListener(ItemClickEvent.ITEM_CLICK,onItemSelect,true);
			addEventListener(FlexEvent.CREATION_COMPLETE,onCreationComplete);
			addEventListener(ResizeEvent.RESIZE,onResize);
			
//			addEventListener(ActionTableEvent.DATA_CREATE,onDataCreate);
			addEventListener(ActionTableEvent.SUBMIT_CREATE_DATA,onDataCreated);
			addEventListener(ActionTableEvent.DATA_DELETE,onDataDelete);
			addEventListener(ActionTableEvent.BEFORE_DATA_MODIFY,onBeforeDataModify);
			
			addEventListener(ActionTableEvent.DATA_MODIFY,onDataItemModify);
			addEventListener(ActionTableEvent.DATA_MODIFY_ROLLBACK,onDataModifyRollback);
			addEventListener(ActionTableEvent.DATA_MODIFY_SUBMIT,onDataModifySubmit);
			addEventListener(ActionTableEvent.MODEL_CHANGED,onModelChanged);
			addEventListener(ActionTableEvent.VISABLE_MODEL_CHANGED,onVisableModelChanged);
			
			addEventListener(ActionTableEvent.DATA_CREATE_RESULT,onDataCreateResult);
			addEventListener(ActionTableEvent.DATA_MODIFY_SUBMIT_RESULT,onDataModifiedSubmitResult);
			addEventListener(ActionTableEvent.DATA_DELETE_RESULT,onDataDeleteResult);
			addEventListener(ActionTableEvent.DATA_REFRESH,onDataRefresh);
			
			addEventListener(ActionTableEvent.END_PAGE,onEndPage);
			addEventListener(ActionTableEvent.HOME_PAGE,onHomePage);
			addEventListener(ActionTableEvent.NEXT_PAGE,onNextPage);
			addEventListener(ActionTableEvent.PAGE_SIZE_CHANGE,onPageSizeChanged);
			addEventListener(ActionTableEvent.PREVIOUS_PAGE,onPreviousPage);
			
			addEventListener("visableStateChange",onVisableStateChange);
		}
		
		private function addParentEventListeners():void{
			parent.addEventListener(Event.RESIZE,onResizeEvent);
		}
		        
		//////////////////////////////////////////////////////
		//// Methods : Initialize
		//////////////////////////////////////////////////////
		private function initActions():void{
			{
				var queryButtonEnableSkin:Class = getStyle("queryButtonEnableSkin");
				var queryButtonDisableSkin:Class = getStyle("queryButtonDisableSkin");
				queryAction = new Action("查询","查询"
						,queryButtonEnableSkin	?	queryButtonEnableSkin	:	queryEnableIcon
						,queryButtonDisableSkin	?	queryButtonDisableSkin	:	null,true,onQuery);
			}
			
			{
				var createButtonEnableSkin:Class = getStyle("createButtonEnableSkin");
				var createButtonDisableSkin:Class = getStyle("createButtonDisableSkin");
				createAction = new Action("新建","新建"
						,createButtonEnableSkin		?	createButtonEnableSkin		:	createEnableIcon
						,createButtonDisableSkin	?	createButtonDisableSkin		:	null,true,onCreate);
			}
			
			{
				var submitButtonEnableSkin:Class = getStyle("submitButtonEnableSkin");
				var submitButtonDisableSkin:Class = getStyle("submitButtonDisableSkin");
				submitAction = new Action("提交","提交"
						,submitButtonEnableSkin		?	submitButtonEnableSkin		:	submitEnableIcon
						,submitButtonDisableSkin	?	submitButtonDisableSkin		:	submitDisableIcon,false,onSubmit);
			}
			
			{
				var rollbackButtonEnableSkin:Class = getStyle("rollbackButtonEnableSkin");
				var rollbackButtonDisableSkin:Class = getStyle("rollbackButtonDisableSkin");
				rollbackAction = new Action("撤销","撤销更改"
						,rollbackButtonEnableSkin	?	rollbackButtonEnableSkin	:	cancelEnableIcon
						,rollbackButtonDisableSkin	?	rollbackButtonDisableSkin	:	cancelDisableIcon,false,onRollback);
			}
			
			{
				var deleteButtonEnableSkin:Class = getStyle("deleteButtonEnableSkin");
				var deleteButtonDisableSkin:Class = getStyle("deleteButtonDisableSkin");
				deleteAction = new Action("删除","删除"
						,deleteButtonEnableSkin		?	deleteButtonEnableSkin		:	deleteEnableIcon
						,deleteButtonDisableSkin	?	deleteButtonDisableSkin		:	deleteDisableIcon,false,onDelete);
			}
			
			{
				var refreshButtonEnableSkin:Class = getStyle("refreshButtonEnableSkin");
				var refreshButtonDisableSkin:Class = getStyle("refreshButtonDisableSkin");
				refreshAction = new Action("刷新","刷新"
						,refreshButtonEnableSkin	?	refreshButtonEnableSkin		:	refreshEnableIcon
						,refreshButtonDisableSkin	?	refreshButtonDisableSkin	:	null,true,onRefresh);
			}

			{
				var homeButtonEnableSkin:Class = getStyle("homeButtonEnableSkin");
				var homeButtonDisableSkin:Class = getStyle("homeButtonDisableSkin");
				homeAction = new Action("首页","首页"
						,homeButtonEnableSkin	?	homeButtonEnableSkin	:	homeEnableIcon
						,homeButtonDisableSkin	?	homeButtonDisableSkin	:	homeDisableIcon,false,onHome);
			}

			{
				var previousButtonEnableSkin:Class = getStyle("previousButtonEnableSkin");
				var previousButtonDisableSkin:Class = getStyle("previousButtonDisableSkin");
				previousAction = new Action("上一页","上一页"
						,previousButtonEnableSkin	?	previousButtonEnableSkin	:	previousEnableIcon
						,previousButtonDisableSkin	?	previousButtonDisableSkin	:	previousDisableIcon,false,onPrevious);
			}

			{
				var nextButtonEnableSkin:Class = getStyle("nextButtonEnableSkin");
				var nextButtonDisableSkin:Class = getStyle("nextButtonDisableSkin");
				nextAction = new Action("下一页","下一页"
						,nextButtonEnableSkin	?	nextButtonEnableSkin	:	nextEnableIcon
						,nextButtonDisableSkin	?	nextButtonDisableSkin	:	nextDisableIcon,false,onNext);
			}

			{
				var endButtonEnableSkin:Class = getStyle("endButtonEnableSkin");
				var endButtonDisableSkin:Class = getStyle("endButtonDisableSkin");
				endAction = new Action("末页","末页"
						,endButtonEnableSkin	?	endButtonEnableSkin		:	endEnableIcon
						,endButtonDisableSkin	?	endButtonDisableSkin	:	endDisableIcon,false,onEnd);
			}
		}
		
		private function initButtons():void{
			buttonProvider = new Array();
			
			buttonProvider.push(queryAction);
			buttonProvider.push(createAction);
			buttonProvider.push(submitAction);
			buttonProvider.push(rollbackAction);
			buttonProvider.push(deleteAction);
			buttonProvider.push(refreshAction);
			buttonProvider.push(homeAction);
			buttonProvider.push(previousAction);
			buttonProvider.push(nextAction);
			buttonProvider.push(endAction);
		}
		
		protected function initializeToolbar():void{
			if(toolbar){
				trace("toolbar is constructed on initializeTable");
			}else{
				trace("toolbar is not constructed on initializeTable");
			}
			callLater(syncButtonEnabledState);
		}
		
		protected function initializeTable():void{
			if(table){
				trace("table is constructed on initializeTable");
			}else{
				trace("table is not constructed on initializeTable");
			}
		}
		
		protected function onToolbarCreateComplete():void{
				setToolbarStyle();
				setToolbarListeners();
		}
			
		protected function setToolbarStyle():void{
			
		}
		
		protected function setToolbarListeners():void{
			toolbar.addEventListener(ItemClickEvent.ITEM_CLICK,onToolbarButtonClick);
			pageSizeInput.addEventListener(FlexEvent.ENTER,onPageSizeChange);
		}
		
		protected function onTableCreateComplete():void{
			setTableStyle();
			setTableListeners();
		}
		
		protected function setTableStyle():void{
			table.doubleClickEnabled = true;
			table.allowMultipleSelection = true;
			table.editable = true;
		}
		
		protected function setTableListeners():void{
			table.addEventListener(MouseEvent.DOUBLE_CLICK,onTableDoubleClick);
			table.addEventListener(DataGridEvent.ITEM_EDIT_BEGIN,onTableItemEditBegin);
			table.addEventListener(FlexEvent.DATA_CHANGE,onTableDataChange,true);
			table.addEventListener(DataGridEvent.ITEM_EDIT_END,onItemEditEnd);
		}
		
		//////////////////////////////////////////////////////
		//// Methods : Data Action Listeners
		//////////////////////////////////////////////////////
		private function onDataCreated(event:ActionTableEvent):void{
			var data:Object = event.getCreatedData();
			addItemToModel(data);
			if(visableModel.length < pageSize){
				addItemToOriginalModel(clone(data));
				addItemToVisableModel(data);
			}
			dispatchEvent(new Event("visableStateChange"));
		}
		
		private function onDataDelete(event:ActionTableEvent):void{
			deleteAction.enabled = false;
			//check if selectedObjects still exist when deleted from array
			var selectedObjects:Array = event.getDeletedData();
			for(var i:int = 0; i < selectedObjects.length; i ++){
				var index:int = model.indexOf(selectedObjects[i]);
				if(index >= 0){
					if(end >= model.length  ){
						end = model.length - 1 ;
					}
					removeAtFromModel(index);
					removeAtFromModifiedModel(index);
				}
			}

			table.selectedItems = new Array();
			dispatchEvent(new Event("visableStateChange"));
		}
		
		private function onBeforeDataModify(event:ActionTableEvent):void{
			copyOriginalModel();
		}
		
		private function onDataModifyRollback(event:ActionTableEvent):void{
			submitAction.enabled = false;
			rollbackAction.enabled = false;
			restoreFromOriginalModel();
			callLater(syncButtonEnabledState);
			modifiedModel = null;
		}
		
		private function onDataModifySubmit(event:ActionTableEvent):void{
			submitAction.enabled = false;
			rollbackAction.enabled = false;
			callLater(syncButtonEnabledState);
			originalModel = null;
			syncOriginalModel();
			modifiedModel = null;
		}
		//////////////////////////////////////////////////////
		//// Methods : Page Operation Listeners
		//////////////////////////////////////////////////////
		private function onEndPage(event:ActionTableEvent):void{
			currentPage = pages;
			dispatchEvent(new Event("visableStateChange"));
		}
		
		private function onHomePage(event:ActionTableEvent):void{			
			currentPage = 1;
			
			dispatchEvent(new Event("visableStateChange"));
		}
		
		private function onNextPage(event:ActionTableEvent):void{
			if(currentPage < pages){
					currentPage ++;
			}
			dispatchEvent(new Event("visableStateChange"));
		}
		
		private function onPageSizeChanged(event:ActionTableEvent):void{
			if(pageSizeInput.text == null ||pageSizeInput.text.length == 0){
				pageSize = 20;
			}else{
				if(pageSizeInput.text.match( /[0-9]+/) ){
					pageSize = parseInt(pageSizeInput.text);
				}
			}
			dispatchEvent(new Event("visableStateChange"));
			
			if(modifiedModel && modifiedModel.length >0 ){ 
				var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.DATA_MODIFY_ROLLBACK);
				event.setSubmitingData(modifiedModel);
				dispatchEvent(event);
			}
		}
		
		private function onPreviousPage(event:ActionTableEvent):void{
			if(currentPage > 1){
				currentPage --;
			}
			dispatchEvent(new Event("visableStateChange"));
		}
		//////////////////////////////////////////////////////
		//// Methods : Model Change Listeners
		//////////////////////////////////////////////////////
		private function onModelChanged(event:ActionTableEvent):void{
			if(end - start < pageSize){
				syncVisableModel();
			}
		}
		
		private function onVisableModelChanged(event:ActionTableEvent):void{
			syncOriginalModel();
		}
		
		//////////////////////////////////////////////////////
		//// Methods : Core Models Behaviors
		//// Core Models:
		//// 	(1)model		:accomodate all data 
		////	(2)visableModel	:accomodate visable data
		////	(3)originalModel:accomodate original data before modification
		//// 	(4)modifiedModel:accomodate modified data
		//////////////////////////////////////////////////////

		//////////////////////////////////////////////////////
		//// Core Models Behaviors : Model Behaviours
		//////////////////////////////////////////////////////

		private function addItemToModel(data:Object):int{
			if(model == null){
				model = new Array();
			}
			model[model.length] = data;
			dispatchEvent(new ActionTableEvent(ActionTableEvent.MODEL_CHANGED));
			return model.length;
		}
		
		private function removeAtFromModel(index:int):void{
			if(index >= 0 && index < model.length){
				model.splice(index,1);
				dispatchEvent(new ActionTableEvent(ActionTableEvent.MODEL_CHANGED));
			}
		}

		//////////////////////////////////////////////////////
		//// Core Models Behaviors : Modified Model Behaviours
		//////////////////////////////////////////////////////
		private function removeAtFromModifiedModel(index:int):void{
			if(index >= 0 && index < model.length){
				if(modifiedModel){
					modifiedModel.splice(index,1);
				}
			}
			
			if(modifiedModel == null || modifiedModel.length <= 0){
				submitAction.enabled = false;
				rollbackAction.enabled = false;
			}
		}
		
		//////////////////////////////////////////////////////
		//// Core Models Behaviors : Original Model Behaviours
		//////////////////////////////////////////////////////
		private function addItemToOriginalModel(data:Object):int{
			originalModel[originalModel.length] = data;
			return originalModel.length;
		}
		
		private function restoreFromOriginalModel():void{
			trace("_originalModel" + originalModel);
			trace("_originalModel size" + originalModel.length);
			
			visableModel = originalModel;
			for(var i:int = start; i < end; i ++){
				modelProvider[i] = originalModel[i - start];
			}
			resetOriginalStates();	
			copyOriginalModel();
			modifiedModel = null;
		}
		
		private function syncOriginalModel():void{
			if(originalModel == null){
				originalModel = new Array();
			}
			if(originalModel.length == visableModel.length){
				if(originalStartIndex == start && originalEndIndex == end){
					return;
				}else{
					copyOriginalModel();
				}
			}else if(originalModel.length < visableModel.length){
				if(originalStartIndex == start){
					for(var i:int = originalModel.length; i < visableModel.length; i ++){
						originalModel.push(clone(visableModel[i]));
					}
					originalStartIndex = start;
					originalEndIndex = end;
				}else{
					copyOriginalModel();
					originalStartIndex = start;
					originalEndIndex = end;
				}
			}else{
			}
		}
		
		private function copyOriginalModel():void{
			if ( originalModel == null ) {
				originalModel = new Array();
			}
			
			if ( visableModel == null ) {
				visableModel = new Array();
			}
			
			if(visableModel.length > 0){
				if(originalStartIndex != start || originalEndIndex != end){
					originalModel = clone(visableModel);
					originalStartIndex = start;
					originalEndIndex = end;
				}
			}
		}
		
		private function resetOriginalStates():void{
			originalEndIndex = 0;
			originalStartIndex = 0;
			originalModel = null;
		}
		
		private function clone(source:Object):*{
			var targetCopy:ByteArray = new ByteArray(); 
		    targetCopy.writeObject(source); 
		    targetCopy.position = 0; 
		    return(targetCopy.readObject()); 
		}
		
		//////////////////////////////////////////////////////
		//// Core Models Behaviors : Visable Model Behaviours
		//////////////////////////////////////////////////////
		private function addItemToVisableModel(data:Object):int{
			if(visableModel.length < pageSize){
				visableModel[visableModel.length] = data;
				end ++;
				return visableModel.length;
			}else{
				return -1;
			}
		}
		
		private function syncVisableModel():void{
			visableModel = model.slice(start,end);
			dispatchEvent(new ActionTableEvent(ActionTableEvent.VISABLE_MODEL_CHANGED));
		}

		//////////////////////////////////////////////////////
		//// Methods : components behaviors of toolbar 
		//////////////////////////////////////////////////////
		public function calculateVisableCounter():void{
			recalculatePageCounters();
			resetPageTip();
			invalidateSize();
		}
		
		private function resetPageTip():void{
			pageTip.text = "总行数：" + rows + " ( 第 " + currentPage + " 页 / 共 " + pages + " 页 )";
		}
		
		private function recalculatePageCounters():void{
			rows = modelProvider.length;
			if(pageSize <= 0){
				pageSize = 20;
			}
			if(rows % pageSize == 0){
				pages = rows / pageSize;
			}else{
				pages = rows / pageSize + 1;
			}
			
			start = pageSize * (currentPage - 1);
			if(pageSize * currentPage <= rows){
				end = pageSize * currentPage ;
			}else{
				end = rows ;
			}
		}
		private function decideNaviButtonState():void{
			if(currentPage <= 1){
				homeAction.enabled = false;
				previousAction.enabled = false;
				if(pages > 1){
					nextAction.enabled = true;
					endAction.enabled = true;
				}else{
					nextAction.enabled = false;
					endAction.enabled = false;
				}
			}else{
				if(currentPage > 1){
					homeAction.enabled = true;
					previousAction.enabled = true;
				}else{
					homeAction.enabled = false;
					previousAction.enabled = false;
				}
				
				if(currentPage < pages){
					nextAction.enabled = true;
					endAction.enabled = true;
				}else{
					nextAction.enabled = false;
					endAction.enabled = false;
				}
			}
			callLater(syncButtonEnabledState);
		}

		protected function syncButtonEnabledState():void{
			if(table.selectedItems.length <= 0){
				deleteAction.enabled = false;
			}
			if(buttonProvider != null && buttonProvider.length > 0 && buttonProvider.length == toolbar.getChildren().length){
				for(var i:int = 0; i < buttonProvider.length; i++){
					ButtonBarButton(toolbar.getChildAt(i)).enabled = Action(buttonProvider[i]).enabled;
				}
			}
			toolbar.invalidateProperties();
			toolbar.invalidateSize();
			toolbar.invalidateDisplayList();
		}

		//////////////////////////////////////////////////////
		////
		//// Methods : Data Action Methods
		////
		//////////////////////////////////////////////////////
		public function submitQueryCondition(condition:Object):void{
			queryCondition = condition;
			var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.SUBMIT_QUERY_CONDITION);	
			event.setQueryCondition(condition);
			dispatchEvent(event);
		}

		public function submitCreateData(data:Object):void{
			var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.SUBMIT_CREATE_DATA);
			event.setCreatedData(data);
			dispatchEvent(event);
		}
		
		public function updateItem(item:Object,index:int):void{
			var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.DATA_MODIFY);
			event.setModifiedIndex(index);
			event.setModifiedData(item);
			dispatchEvent(event);
		}
		
		public function reset():void {
			this.pageSizeInput.text = "";
			this.pageSize = 20;
			this.model = new Array();
		}
		
		//////////////////////////////////////////////////////
		////
		//// Methods : Overriden Methods
		////
		//////////////////////////////////////////////////////
		 /*** e) Implement the createChildren() method. ***/
		override protected function createChildren():void{
			super.createChildren();
			
			//Prepare children of Toolbar
			{
				initActions();
				initButtons();
			}
			
			
			addParentEventListeners();
			
			if(!toolbarBox){
				toolbarBox = new HBox();
				toolbarBox.setStyle("verticalAlign","middle");
			}
			
			if(!toolbar){
				toolbar = new ButtonBar();
//				toolbar.styleName = "myButtonBar";
				toolbar.dataProvider = buttonProvider;
			}
			
			if(!pageLabel){
				pageLabel = new Label();
				pageLabel.setStyle("font-Family","宋体");
				pageLabel.setStyle("fontSize","12");
				pageLabel.text = "页面大小：";
			}
			
			if(!pageSizeInput){
				pageSizeInput = new TextInput();
				pageSizeInput.width = 40;
			}
			
			if(!pageTip){
				pageTip = new Label();
				pageTip.setStyle("font-Family","宋体");
				pageTip.setStyle("fontSize","12");
				pageTip.text = "总行数：" + rows + " ( 第 " + currentPage + " 页 / 共 " + pages + " 页 )";
			}
			
			toolbarBox.addChild(toolbar);
			toolbarBox.addChild(pageLabel);
			toolbarBox.addChild(pageSizeInput);
			toolbarBox.addChild(pageTip);
			
			if(!table){
				table = new DataGrid();
				if(!columnProvider){
					columnProvider = new Array();
				}
				table.columns = columnProvider;
				table.dataProvider = visableModel;
			}
			
			addChild(toolbarBox);
			addChild(table);
			
			{
				initializeToolbar();
				initializeTable();
			}
			
		}
		
		/*** f) Implement the commitProperties() method. ***/
		override protected function commitProperties():void {
            super.commitProperties();
   		}
   		
   		/*** g) Implement the measure() method. ***/
        // The default width is the size of the text plus the button.
        // The height is dictated by the button.
        override protected function measure():void {
            super.measure();
            var totalWidth:Number = 0;
            var totalHeight:Number = 0;
			if(parent is Container){
            	var left:Number = Container(parent).viewMetricsAndPadding.left;
            	var right:Number = Container(parent).viewMetricsAndPadding.right;
            	var top:Number = Container(parent).viewMetricsAndPadding.top;
            	var bottom:Number = Container(parent).viewMetricsAndPadding.bottom;
            	totalWidth = parent.width - left - right;
            	totalHeight = parent.height - top - bottom;
            	measuredMinWidth = measuredWidth = totalWidth;
				measuredMinHeight = measuredHeight = totalHeight;
				
				toolbarBox.width = measuredWidth;
				table.width = measuredWidth;
				
            }else{
            }
      		
            for(var i:int = 0; i < toolbar.numChildren; i ++){
            	toolbar.height = Math.max(toolbar.height,toolbar.getChildAt(i).height);
            }
            if(toolbar.height <= 0){
            	toolbar.height = 25;
            }
            
            for(var j:int = 0; j < toolbarBox.numChildren; j ++){
            	toolbarBox.height = Math.max(toolbarBox.height,toolbarBox.getChildAt(j).height);
            }
            
            var metrics:TextLineMetrics = measureText(pageLabel.text);
            pageLabel.width = metrics.width + 2 * metrics.ascent + 2 * metrics.leading;
			
			metrics = measureText(pageTip.text)
			pageTip.width = metrics.width + 2 * metrics.ascent + 2 * metrics.leading + pageSizeInput.width;          
			table.height = measuredHeight - toolbarBox.height;
        }
        
        
        /*** h) Implement the updateDisplayList() method. ***/
        override protected function updateDisplayList(unscaledWidth:Number,
                unscaledHeight:Number):void {
            super.updateDisplayList(unscaledWidth, unscaledHeight);
            toolbarBox.setActualSize(toolbarBox.width,toolbarBox.height);
            table.move(0,toolbarBox.height);
            table.setActualSize(table.width,table.height);
        }

		//////////////////////////////////////////////////////
		////
		//// Event Handlers : ToolBar Event Handlers
		////
		//////////////////////////////////////////////////////
		private function onQuery(context:Object):void{
			dispatchEvent(new ActionTableEvent(ActionTableEvent.REQUEST_DATA_QUERY));
		} 

		private function onCreate(context:Object):void{
			dispatchEvent(new ActionTableEvent(ActionTableEvent.DATA_CREATE));
		}		
			 
		private function onSubmit(context:Object):void{
			if(submitAction.enabled){
				//Send submit event
				var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.DATA_MODIFY_SUBMIT);
				event.setSubmitingData(modifiedModel);
				dispatchEvent(event);
			}
		}
		
		private function onRollback(context:Object):void{
			if(rollbackAction.enabled){
				//Send rollback event
				var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.DATA_MODIFY_ROLLBACK);
				event.setSubmitingData(modifiedModel);
				dispatchEvent(event);
			}
		}
		
		private function onDelete(context:Object):void{
			if(deleteAction.enabled){
				//Send delete event
				var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.DATA_DELETE);
				var selectedObjects:Array = table.selectedItems;
				event.setDeletedData(selectedObjects);
				dispatchEvent(event);
			}
		}
		
		private function onRefresh(context:Object):void{
			if(refreshAction.enabled){
				setupCloseAlertHandler(new ActionTableEvent(ActionTableEvent.DATA_REFRESH));
			}
		}
		
		private function onHome(ctx:Object):void{
			trace("onHome...");
			if(homeAction.enabled){
				setupCloseAlertHandler(new ActionTableEvent(ActionTableEvent.HOME_PAGE));
			}
		}
		
		private function onPrevious(ctx:Object):void{
			trace("onPrevious...");
			if(previousAction.enabled){
				setupCloseAlertHandler(new ActionTableEvent(ActionTableEvent.PREVIOUS_PAGE));
			}
		}
		
		private function onNext(ctx:Object):void{
			trace("onNext...");
			if(nextAction.enabled){
				setupCloseAlertHandler(new ActionTableEvent(ActionTableEvent.NEXT_PAGE));
			}
		}
		
		private function onEnd(ctx:Object):void{
			trace("onEnd...");
			if(endAction.enabled){
				setupCloseAlertHandler(new ActionTableEvent(ActionTableEvent.END_PAGE));
			}
		}
					
		private function onPageSizeChange(event:FlexEvent):void{
			trace("on pagesize changed");
			setupCloseAlertHandler(new ActionTableEvent(ActionTableEvent.PAGE_SIZE_CHANGE));
		}
		
		private function setupCloseAlertHandler(event:ActionTableEvent):void{
			lastPageEvent = event;
			if(modifiedModel && modifiedModel.length > 0){
				Alert.yesLabel = "是";
				Alert.noLabel = "否";
				Alert.show("数据已经更改,选择‘是’撤销更改继续翻页/刷新操作，选择‘否’撤销翻页操作",'',Alert.YES|Alert.NO,this,closeHandler);
			}else{
				lastPageEvent = null;
				dispatchEvent(event);
			}
		}
		
		private function closeHandler(event:CloseEvent):void{
			trace(event.detail);
			if (event.detail==Alert.YES){
				submitAction.enabled = false;
				rollbackAction.enabled = false;
				restoreFromOriginalModel();
				callLater(syncButtonEnabledState);
				if(lastPageEvent != null){
  	              dispatchEvent(lastPageEvent);
  	              lastPageEvent = null;
 				}
 			}
            else
               trace("No");
		}
		
		private function onResizeEvent(event:Event):void{
			trace(event);
			trace(DisplayObject(event.target).width);
			trace(DisplayObject(event.target).height);
			invalidateSize();
			invalidateDisplayList();
		}

		private function onToolbarButtonClick(event:ItemClickEvent):void  {
			Action(event.item).context = this;
            Action(event.item).onAction();
        }
            
		//////////////////////////////////////////////////////
		////
		//// Event Handlers : Table Event Handlers
		////
		//////////////////////////////////////////////////////
		private function onTableDoubleClick(event:MouseEvent):void{
			trace(event.currentTarget);
			dispatchEvent(new ActionTableEvent(ActionTableEvent.BEFORE_DATA_MODIFY));
			var grid:DataGrid = event.currentTarget as DataGrid;
			var itemRenderer:DataGridItemRenderer = grid.editedItemRenderer as DataGridItemRenderer;
			if(itemRenderer){
				var columnIndex:int = itemRenderer.listData.columnIndex;
				var rowIndex:int = itemRenderer.listData.rowIndex;
				var data:Object = itemRenderer.data;
				var actionEvent:ActionTableEvent = new ActionTableEvent(ActionTableEvent.REQUEST_DATA_MODIFY);
				actionEvent.setRequestModifyColumnIndex(columnIndex);
				actionEvent.setRequestModifyRowIndex(rowIndex);
				actionEvent.setRequestModifyData(data);
				dispatchEvent(actionEvent);
			} else {
				var actionEvent:ActionTableEvent = new ActionTableEvent(ActionTableEvent.REQUEST_DATA_MODIFY);
				actionEvent.setRequestModifyColumnIndex(-1);
				actionEvent.setRequestModifyRowIndex(grid.selectedIndex);
				actionEvent.setRequestModifyData(grid.selectedItem);
				dispatchEvent(actionEvent);
			}
		}
		
		private function onTableItemEditBegin(event:DataGridEvent):void{
		}
		
		private function onTableDataChange(event:FlexEvent):void{
			trace(event);
//			trace(event.target);
		}
		
		private function onItemEditEnd(event:DataGridEvent):void{
			var row:int = event.rowIndex;
			if(row >=0 && row < visableModel.length){
				updateItem(visableModel[row],row);
			}
		}

		private function onItemSelect(event:Event):void{
			if(event as ListEvent){
				if(table != null){
					if(table.selectedItems.length >= 1){
						deleteAction.enabled = true;
					}else{
						deleteAction.enabled = false;
					}
				}else{
					deleteAction.enabled = false;
				}
				callLater(syncButtonEnabledState);
			}else if(event as ItemClickEvent){
				
			}
		}
		
		private function onEditBegin(dgEvent:DataGridEvent):void{
			var event:ActionTableEvent = new ActionTableEvent(ActionTableEvent.BEFORE_DATA_MODIFY);
			var row:int = dgEvent.rowIndex;
			var globalIndex:int = start + row;
			event.setModifiedData(model[globalIndex]);
			dispatchEvent(event);
		}
		
		private function onResize(event:ResizeEvent):void{
			trace(event);
			trace("oldHeight:" + event.oldHeight);
			trace("oldWidth:" + event.oldWidth);
		}
		
		private function onDataCreateResult(event:ActionTableEvent):void{
			/**
			 * @todo: implement data create result handler
			 */ 
			trace("onDataCreateResult");	
		}
		
		private function onDataModifiedSubmitResult(event:ActionTableEvent):void{
			/**
			 * @todo: implement Modified Submit result handler
			 */ 
			trace("onDataModifiedSubmitResult");	
		}
		
		private function onDataDeleteResult(event:ActionTableEvent):void{
			/**
			 * @todo: implement data delete result handler
			 */ 
			trace("onDataDeleteResult");
		}
		
		private function onDataRefresh(event:ActionTableEvent):void {
			var eventObject:ActionTableEvent = new ActionTableEvent(ActionTableEvent.SUBMIT_QUERY_CONDITION);
			eventObject.setQueryCondition(queryCondition);
			dispatchEvent(eventObject);
		}
		
		private function onDataItemModify(event:ActionTableEvent):void{
			trace("onDataItemModify");
			var data:Object = event.getModifiedData();	
			var row:int = event.getModifiedIndex();
			
			if(isNaN(row) || row < 0){
				if(data){
					row = visableModel.indexOf(data);
					if(isNaN(row) || row < 0 || row >= visableModel.length){
						trace("Row And Data are both invalid");
						return;
					}
				}else{
					trace("Row And Data are both invalid");
					return;
				}
			}
			
			submitAction.enabled = true;
			rollbackAction.enabled = true;
			callLater(syncButtonEnabledState);
			
			if(modifiedModel == null){
				modifiedModel = new Array();
			}
			var globalIndex:int = start + row ;
			modifiedModel[globalIndex] = model[globalIndex];
			
		}
		
		private function onVisableStateChange(event:Event):void {
			calculateVisableCounter();
			syncVisableModel();
			decideNaviButtonState();
		}
		
		//////////////////////////////////////////////////////
		////
		//// Event Handlers : Component Life Cycle Handlers
		////
		//////////////////////////////////////////////////////
		/**
		 * After initialized, createComplete event is invoked.
		 * 
		 * On create Complete:
		 * 		1. On toolbar create complete:
		 * 			1.1 Add event listener  ItemClickEvent.ITEM_CLICK,onButtonClick
		 * 		2. On table create complete:
		 * 			2.1 Set table style
		 * 			2.2 Add event listener  DataGridEvent.ITEM_EDIT_BEGIN,onTableItemEditBegin
		 * 									FlexEvent.DATA_CHANGE,onTableDataChange
		 * 									DataGridEvent.ITEM_EDIT_END,onItemEditEnd
		 **/
		 
		protected function onCreationComplete(event:FlexEvent):void{
			onToolbarCreateComplete();
			onTableCreateComplete();
		}
				
		//////////////////////////////////////////////////////
		////
		//// Properties 
		////
		//////////////////////////////////////////////////////	
		
		//////////////////////////////////////////////////////
		//// Properties : Toolbar Properties 
		//////////////////////////////////////////////////////	
		[Bindable]
   		public function get columnProvider():Array{
   			return _columnProvider;
   		}
   		
   		public function set columnProvider(columns:Array):void{
   			if(!columns){
   				columns = new Array();
   				_columnProvider = columns;
   			}else if(columns.length > 0){
				for(var i:int = 0;i < columns.length;i++){
					_columnProvider.push(columns[i]);
				}
			}else{
				_columnProvider = columns;
			}
			table.columns = _columnProvider;
   			invalidateProperties();
   			invalidateSize();
   			invalidateDisplayList();
   		}
		
   		//////////////////////////////////////////////////////
		//// Properties : Table Properties (Core Data Model) 
		//////////////////////////////////////////////////////	
   		[Bindable]		
		public function get model():Array{
			return modelProvider;
		}
		
		public function set model(data:Array):void{
			visableModel = null;
			modifiedModel = null;
			originalModel = null;
			modelProvider = data;
			currentPage = 1;			
			dispatchEvent(new Event("visableStateChange"));
		}
		
		public function get selectedItems():Array{
			return table.selectedItems;
		}
		
		private function get visableModel():Array{
			return _visableModel;
		}
		
		private function set visableModel(arr:Array):void{
			_visableModel = arr;
			table.dataProvider = _visableModel;
		}
	}
}