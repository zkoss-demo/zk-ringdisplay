package org.potix.zk_ringdisplay.step2;

public class ChairBean {
	private String imageUrl;
	private String name;
	private String description;
	private String commandData;
	
	public ChairBean(String imageUrl, String name, String description, String commandData) {
		this.imageUrl = imageUrl;
		this.name = name;
		this.description = description;
		this.commandData = commandData;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getCommandData() {
		return commandData;
	}
}
