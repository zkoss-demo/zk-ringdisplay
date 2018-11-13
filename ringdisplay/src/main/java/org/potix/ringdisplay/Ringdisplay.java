package org.potix.ringdisplay;

import java.util.Map;

import org.zkoss.lang.Objects;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;

public class Ringdisplay extends Div {
	private static final long serialVersionUID = 5800904620990240995L;

	/* static listeners will cause the client widget to automatically propagate events to
	 * the server, even if no listener is manually defined.
	 * This is useful for events updating internal properties of the component, such as selection index*/
	static {
		addClientEvent(Ringdisplay.class, "onItemSelect", 0);
	}

	/* properties of the component.
	 * Values declared here will act as default if the user doesn't specify it */
	private String _perspective ="1000px";
	private int _itemWidth = 200;
	private String _depth = "100px";
	private int _selectedIndex = 0;
	private boolean _clickToTurn = true;

	
	/* public behavior */
	public void next() {
		setSelectedIndex(getSelectedIndex() + 1);
	}

	public void previous() {
		setSelectedIndex(getSelectedIndex() - 1);
	}

	
	/* properties getters */
	public int getSelectedIndex() {
		return _selectedIndex;
	}
	
	public String getPerspective() {
		return _perspective;
	}

	public int getItemWidth() {
		return _itemWidth;
	}

	public String getDepth() {
		return _depth;
	}

	public boolean isClickToTurn() {
		return _clickToTurn;
	}

	/* properties setters
	 * smartUpdate() will be called to automatically propagate the value to the client
	 *  if the value is not equal to the previous value */
	public void setClickToTurn(boolean clickToTurn) {
		if (!Objects.equals(_clickToTurn, clickToTurn)) {
			_clickToTurn = clickToTurn;
			smartUpdate("selectedIndex", _clickToTurn);
		}
	}
	
	public void setSelectedIndex(int selectedIndex) {
		if (!Objects.equals(_selectedIndex, selectedIndex)) {
			_selectedIndex = selectedIndex;
			smartUpdate("selectedIndex", _selectedIndex);
		}
	}

	public void setPerspective(String perspective) {
		if (!Objects.equals(_perspective, perspective)) {
			_perspective = perspective;
			smartUpdate("perspective", _perspective);
		}
	}

	public void setItemWidth(int itemWidth) {
		if (!Objects.equals(_itemWidth, itemWidth)) {
			_itemWidth = itemWidth;
			smartUpdate("itemWidth", _itemWidth);
		}
	}

	public void setDepth(String depth) {
		if (!Objects.equals(_depth, depth)) {
			_depth = depth;
			smartUpdate("depth", _depth);
		}
	}

	/* Choose the properties to render during initialization
	 * In most case, should be every property that can be updated */
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer) throws java.io.IOException {
		super.renderProperties(renderer);

		render(renderer, "depth", _depth);
		render(renderer, "itemWidth", _itemWidth);
		render(renderer, "perspective", _perspective);
		render(renderer, "selectedIndex", _selectedIndex);
		render(renderer, "clickToTurn", _clickToTurn);
	}

	/* the service method receives events from the client and process them
	 * If no action is necessary, forwarding to the super implementation to dispatch
	 * the event to the default listeners if any */
	public void service(AuRequest request, boolean everError) {
		final String cmd = request.getCommand();
		final Map<String, Object> data = request.getData();

		if (cmd.equals("onItemSelect")) {
			Events.postEvent(Event.getEvent(request));
		} else if (cmd.equals("onSelectedIndex")) {
			this._selectedIndex = (int) data.get("selectedIndex");
			Events.postEvent(Event.getEvent(request));
		} else
			super.service(request, everError);
	}
}
