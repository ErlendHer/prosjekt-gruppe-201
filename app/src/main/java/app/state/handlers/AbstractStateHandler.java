package app.state.handlers;

import java.util.ArrayList;
import java.util.Scanner;

import app.state.State;

public abstract class AbstractStateHandler {

	protected Scanner scanner;
	protected ArrayList<String> inputs;
	protected State nextState;
	
	public AbstractStateHandler() {
		this.scanner = new Scanner(System.in);
		this.inputs = new ArrayList<String>();
	}
	
	protected boolean validate(String input, boolean isInteger) {
		
		return true;
	}
	
	protected void readLine(boolean isInteger) {
		if(isInteger) {
			while(true){
				String input = scanner.nextLine();
				try {
					Integer.parseInt(input);
					inputs.add(input);
					break;
				} catch(NumberFormatException e) {
					System.err.println("Ugyldig input, forventet heltall");
				}
			}
		} else {
			inputs.add(scanner.nextLine());
		}
	}
	
	protected void setNextState(State state) {
		this.nextState = state;
	}
	
	public State getNextState() {
		return this.nextState;
	}
	
	public abstract boolean readInput();
	
	
	
}
