package com.mvn.virtualneighbor.interfaces;

public interface ImportantMethods {

	/**
	 * This method is used for initialize all important objects which will be
	 * use in this class.
	 */
	public void initialization();

	/**
	 * This method is used for find all ids on this activity.
	 */
	public void findAllIds();

	/**
	 * To set Listeners on the views.
	 */
	public void setListeners();

	/**
	 * To set adapters on the views.
	 */
	public void setAdapters();
}
