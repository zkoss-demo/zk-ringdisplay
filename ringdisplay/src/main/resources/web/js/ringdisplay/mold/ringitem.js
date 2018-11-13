function (out) {

	//Here you call the "this" means the widget instance. (@see Ringdisplay.js)

	var zcls = this.getZclass(),
		uuid = this.uuid;

	//The this.domAttrs_() means it will prepare some dom attributes,
	//like the pseudo code below
	/*
		class="${zcls} ${this.getSclass()}" id="${uuid}"
	*/
	out.push('<div ', this.domAttrs_(), '>');
	for (var w = this.firstChild; w; w = w.nextSibling)
		w.redraw(out);
	out.push('</div>');

}