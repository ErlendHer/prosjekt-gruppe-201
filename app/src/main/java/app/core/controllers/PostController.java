package app.core.controllers;

import app.Store;
import app.core.models.Thread;

public class PostController extends AbstractController {
	
	private Thread thread;

	public PostController() {
		this.thread = Store.getCurrentThread();
	}
	@Override
	public boolean readInput() {
		if(thread == null) {
			thread = Store.getCurrentThread();
		}
		System.out.println(thread);
		return false;
	}

}
