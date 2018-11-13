package org.potix.zk_ringdisplay.step2;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

public class RingExampleVm {

	private ListModelList<SelectableChairBean> chairModel;
	private SelectableChairBean selectedItem;
	
	@Init
	public void init(){
		chairModel = new ListModelList<SelectableChairBean>();
		chairModel.add(new SelectableChairBean("/resources/img/1.png", "Chair 1", "Description 1","Command 1"));
		chairModel.add(new SelectableChairBean("/resources/img/2.png", "Chair 2", "Description 2","Command 2"));
		chairModel.add(new SelectableChairBean("/resources/img/3.png", "Chair 3", "Description 3","Command 3"));
		chairModel.add(new SelectableChairBean("/resources/img/4.png", "Chair 4", "Description 4","Command 4"));
		chairModel.add(new SelectableChairBean("/resources/img/5.png", "Chair 5", "Description 5","Command 5"));
		chairModel.add(new SelectableChairBean("/resources/img/6.png", "Chair 6", "Description 6","Command 6"));
		chairModel.add(new SelectableChairBean("/resources/img/7.png", "Chair 7", "Description 7","Command 7"));
		chairModel.add(new SelectableChairBean("/resources/img/8.png", "Chair 8", "Description 8","Command 8"));
		chairModel.add(new SelectableChairBean("/resources/img/9.png", "Chair 9", "Description 9","Command 9"));
	
		selectedItem = chairModel.get(0);
	}
	
	@NotifyChange({"selectedItem","selectedIndex"})
	@Command
	public void previous(){
		SelectableChairBean previousItem = selectedItem;
		if(chairModel.indexOf(selectedItem) == 0){
			selectedItem = chairModel.get(chairModel.size() -1);
		}else{
			selectedItem = chairModel.get(chairModel.indexOf(selectedItem) - 1);
		}
		BindUtils.postNotifyChange(null, null, selectedItem, "selected");
		BindUtils.postNotifyChange(null, null, previousItem, "selected");
	}
	
	@NotifyChange({"selectedItem","selectedIndex"})
	@Command
	public void next(){
		SelectableChairBean previousItem = selectedItem;
		if(chairModel.indexOf(selectedItem) == chairModel.size() - 1){
			selectedItem = chairModel.get(0);
		}else{
			selectedItem = chairModel.get(chairModel.indexOf(selectedItem) + 1);
		}
		BindUtils.postNotifyChange(null, null, selectedItem, "selected");
		BindUtils.postNotifyChange(null, null, previousItem, "selected");
	}
	
	@Command
	public void doItemClick(@BindingParam("data") String data){
		Clients.showNotification("Clicked on "+ data);
	}
	
	public int getSelectedIndex(){
		return chairModel.indexOf(selectedItem);
	}

	public ListModelList<SelectableChairBean> getChairModel() {
		return chairModel;
	}

	public ChairBean getSelectedItem() {
		return selectedItem;
	}
	
	public class SelectableChairBean extends ChairBean{
		public SelectableChairBean(String imageUrl, String name, String description, String commandData) {
			super(imageUrl, name, description, commandData);
		}
		
		public boolean isSelected(){
			return selectedItem.equals(this);
		}
	}
	
}
