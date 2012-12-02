package net.vicp.madz.components.action
{
	public class Action implements IAction
	{
		private var _id:String;
		private var _label:String;
		private var _toolTip:String;
		private var _enableIcon:Class;
		private var _disableIcon:Class;
		private var _action:Function;
		private var _context:Object;
		private var _enabled:Boolean;
		
		public function Action(label:String,toolTip:String,enableIcon:Class=null,disableIcon:Class = null,enable:Boolean = true,action:Function=null,id:String=null)
		{
			this._label = label;
			this._toolTip = toolTip;
			this._enableIcon = enableIcon;
			this._disableIcon = disableIcon;
			this._enabled = enable;
			this._action = action;
			if(_enabled){
				_icon = _enableIcon;
			}else{
				_icon = _disableIcon;
			}
		}

		public function get id():String{
			return _id;
		}
		
		public function set id(id:String):void{
			_id = id;
		}

		public function get label():String
		{
			return "";//_label;
		}
		
		public function get toolTip():String
		{
			return _toolTip;
		}
		
//		[Bindable]
		private var _icon:Class;
		
		[Bindable]
		public function get icon():Class{
			return _icon;
		}
		
		public function set icon(clz:Class):void{
			_icon = clz;
		}
		
		[Bindable]
		public function get enabled():Boolean{
			return _enabled;
		}
		
		public function set enabled(enabled:Boolean):void{
			_enabled = enabled;
			if(_enabled){
				_icon = _enableIcon;
			}else{
				_icon = _disableIcon;
			}
		}
		
		public function get enableIcon():Class
		{
			return _enableIcon;
		}
		
		public function get disableIcon():Class{
			return _disableIcon;
		}
		
		public function get action():Function
		{
			return _action;
		}
		
		public function set action(handler:Function):void
		{
			_action = handler;
		}
		
		public function get context():Object{
			return _context;
		}
		
		public function set context(ctx:Object):void{
			_context = ctx;
		}
		
		public function onAction():void{
			if(_enabled){
				if(_action != null){
					trace("on action...");
					var objects:Array = new Array();
					objects[0] = context;
					_action.apply(this,objects);
				}else{
					trace("action on:" + _label + " not set yet");
				}
			}
		}
	}
}