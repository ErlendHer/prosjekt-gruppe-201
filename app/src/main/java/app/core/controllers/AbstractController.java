package app.core.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import app.core.state.State;

public abstract class AbstractController {

	private final List<String> cmdFlags = List.of("enter", "back", "quit", "search", "help");
	protected Scanner scanner;
	protected ArrayList<String> inputs;
	protected State nextState;
	
	public AbstractController() {
		this.scanner = new Scanner(System.in);
		this.inputs = new ArrayList<String>();
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
	
	protected void awaitConsoleInput() {
		List<String> cmd = Arrays.asList(scanner.nextLine().toLowerCase().split(" "));
		if(cmd.size() < 0) {
			System.err.println("Vennligst skriv inn en kommando");
		} else if(!cmdFlags.contains(cmd.get(0))) {
			System.err.println("Ugyldig kommando, "
					+ "skriv 'help' for å få en oversikt "
					+ "over tilgjengelige kommandoer");
		} else if(cmd.get(0).equalsIgnoreCase("quit")){
			System.out.println("Applikasjonen er avsluttet");
			System.exit(-1);
		}else {
			inputs.addAll(cmd);
		}
	}
	
	protected void clearInput() {
		this.inputs.clear();
	}
	
	protected void setNextState(State state) {
		this.nextState = state;
	}
	
	public void closeScanner() {
		this.scanner.close();
	}
	
	public State getNextState() {
		return this.nextState;
	}
	
	public abstract boolean readInput();
	
	
	
}
