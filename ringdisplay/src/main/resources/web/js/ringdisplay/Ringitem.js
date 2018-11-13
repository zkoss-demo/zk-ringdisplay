/**
 *
 * Base naming rule:
 * The stuff start with "_" means private , end with "_" means protect ,
 * others mean public.
 *
 * All the member field should be private.
 *
 * Life cycle: (It's very important to know when we bind the event)
 * A widget will do this by order :
 * 1. $init
 * 2. set attributes (setters)
 * 3. rendering mold (@see mold/ringdisplay.js )
 * 4. call bind_ to bind the event to dom .
 *
 * this.deskop will be assigned after super bind_ is called,
 * so we use it to determine whether we need to update view
 * manually in setter or not.
 * If this.desktop exist , means it's after mold rendering.
 *
 */
ringdisplay.Ringitem = zk.$extends(zul.wgt.Div, {

	getIndex: function(){
		index = 0,
		wgt = this;
		this.parent.getChildren().every(function(item){
			if(item == wgt)
				return false;
			if(item.isRealVisible())
				index++;
			return true;
		});
		return index;
	},
	setWidthDirectly: function(){
		 this.$supers('setWidth', arguments);
	},
	setWidth: function(){
		console.log('disabled, use parent itemWidth instead');
	},
	doClick: function(evt){
		this.parent.setSelectedIndex(this.getIndex());
	},
	bind_: function () {
		/**
		 * For widget lifecycle , the super bind_ should be called
		 * as FIRST STATEMENT in the function.
		 * DONT'T forget to call supers in bind_ , or you will get error.
		 */
		this.$supers(ringdisplay.Ringdisplay,'bind_', arguments);
		this.domListen_(this.$n(), 'onClick', 'doClick_');
		this.setWidthDirectly(this.parent.getItemWidth()+"px");
		jq(this).css({"left":"calc(50% - " + this.$n().offsetWidth / 2 + "px)"});
		jq(this).css({"top":"calc(50% - " + this.$n().offsetHeight / 2 + "px)"});
	
	},
	unbind_: function () {
	
		// A example for domUnlisten_ , should be paired with bind_
		// this.domUnlisten_(this.$n("cave"), "onClick", "_doItemsClick");
		
		/*
		* For widget lifecycle , the super unbind_ should be called
		* as LAST STATEMENT in the function.
		*/
		this.$supers(ringdisplay.Ringdisplay,'unbind_', arguments);
		this.domUnlisten_(this.$n(), 'onClick', 'doClick_');
	},
	/*
		widget event, more detail 
		please refer to http://books.zkoss.org/wiki/ZK%20Client-side%20Reference/Notifications
	 */
	doClick_: function (evt) {
		this.$super('doClick_', evt, true);//the super doClick_ should be called
		if(this.parent.getClickToTurn() && this.parent.getSelectedIndex != this.getIndex()){
			this.parent.setSelectedIndex(this.getIndex());
		}else{
			this.fire('onClick', {index: this.getIndex()});
		}
	}
});