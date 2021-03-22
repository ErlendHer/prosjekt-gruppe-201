package app.state.handlers;

import app.Store;
import app.Store.StoreObject;

public class PostStateHandler extends AbstractStateHandler {

	@Override
	public boolean readInput() {
		System.out.println("Kem e du:");
		readLine(false);
		System.out.println(Store.getStoreValue(StoreObject.CurrentUser));
		return false;
	}

}
