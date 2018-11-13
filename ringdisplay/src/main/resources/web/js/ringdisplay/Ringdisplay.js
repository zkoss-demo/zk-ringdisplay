ringdisplay.Ringdisplay = zk.$extends(zul.wgt.Div, {
	_selectedIndex : 0,
	_itemWidth : "200",
	_perspective : "1000px",
	_depth : "0px",
	
	getRadius : function(){
		return Math.round( (this.$n().offsetWidth - this.getItemWidth()) / 2 );
	},
	
	getTheta : function(){
		return 360 / this.getItemCount();
	},
	
	getFrameAngle : function(){
		return this.getTheta() * this._selectedIndex * -1;
	},
	
	getItemCount : function(){
		var count = 0;
		this.getChildren().forEach(function(child){
			if(child.isRealVisible)
				count++;
		});
		return count;
	},
	getChildren: function(){
		var childWidgets = [];
		var childNodes = jq(this.$n('ring')).children(".z-ringitem");
		childNodes.each(function(index,item){
			childWidgets.push(zk.$(item));
		})
		return childWidgets;
	},
	updatePosition : function() {
		if(!this.isRealVisible())
			return false;
	  var wgt = this,
	  	frameAngle = this.getFrameAngle(),
	  	radius = this.getRadius(),
	  	theta = this.getTheta();
	  this.getChildren().forEach(function(item){
		  var itemAngle = theta * item.getIndex();
		  var itemDepth = Math.cos(Math.PI * 2 * (itemAngle+frameAngle) / 360);
		  item.$n().style.transform = 'rotateY(' + itemAngle + 'deg) translateZ(' + radius + 'px) rotateY('+ (-(frameAngle + itemAngle)) +'deg)';
		  item.$n().style.opacity = Math.min(1,(itemDepth+1)/2 + 0.2);
	  });
	  this.$n('ring').style.transform = 'translateZ( -' + this.getDepth() + ')  rotateY(' + this.getFrameAngle() + 'deg)';
	},
	updateIndex: function(index){
		this.setSelectedIndex(index);
		this.fireSelectedIndex();
	},
	getSelectedIndex:function(){ 
		return this._selectedIndex; 
	},
	setSelectedIndex: function(targetIndex){
		if(this.getItemCount() == 0)
			return false;
		var itemCount = this.getItemCount(),
		realIndex = (this._selectedIndex % itemCount),
		realTarget = (targetIndex % itemCount);
		if(realIndex < 0)
			realIndex += itemCount;
		
		var stepsUp, stepsDown;
		stepsUp = realTarget - realIndex;
		if(stepsUp < 0)
			stepsUp += itemCount;
		stepsDown = realIndex - realTarget;
		if(stepsDown < 0)
			stepsDown += itemCount;
		if(stepsUp <= stepsDown){
			this._selectedIndex += stepsUp;
		}else{
			this._selectedIndex -= stepsDown; 
		}
		this.updatePosition();
	},
	fireSelectedIndex: function(){
		this.fire('onSelectedIndex', {selectedIndex: this._selectedIndex});
	},
	fixMinFlex_: function (n, orient) {
		return zFlex.fixMinFlex(this, n, orient);
	},
	onAfterSize: function () {
		this.$supers(ringdisplay.Ringdisplay,'onAfterSize', arguments);
		this.updatePosition();
	},
	$define: {
		depth: function(){
			if(this.desktop) {
				updatePosition();
			}
		},
		perspective: function(){
			if(this.desktop) {
				updatePosition();
			}
		},
		itemWidth: function(){
			if(this.desktop) {
				updatePosition();
			}
		},
		clickToTurn: function(){
			
		}
	},
	/**
	 * If you don't like the way in $define ,
	 * you could do the setter/getter by yourself here.
	 *
	 * Like the example below, they are the same as we mentioned in $define section.
	 */
	bind_: function () {
		/**
		 * For widget lifecycle , the super bind_ should be called
		 * as FIRST STATEMENT in the function.
		 * DONT'T forget to call supers in bind_ , or you will get error.
		 */
		this.$supers(ringdisplay.Ringdisplay,'bind_', arguments);
		this.$n().style.perspective = this.getPerspective();
		this.$n('ring').style.transform = 'translateZ('+ this.getDepth() +')'
		this.updatePosition();
	
	}
});