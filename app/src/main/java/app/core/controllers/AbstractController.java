package app.core.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import app.core.models.AbstractModel;
import app.core.state.State;

public abstract class AbstractController {

	private final List<String> cmdFlags = List.of("enter", "back", "quit", "search", "help", "answer", "create",
			"comment", "like", "stat", "logout");

	protected Scanner scanner;
	protected ArrayList<String> inputs;
	protected State nextState;

	public AbstractController() {
		this.scanner = new Scanner(System.in);
		this.inputs = new ArrayList<String>();
	}

	protected void readLine(boolean isInteger) {
		if (isInteger) {
			while (true) {
				String input = scanner.nextLine();
				System.out.println(input);
				try {
					Integer.parseInt(input);
					inputs.add(input);
					break;
				} catch (NumberFormatException e) {
					System.err.println("Ugyldig input, forventet heltall");
				}
			}
		} else {
			inputs.add(scanner.nextLine());
		}
	}

	protected void awaitConsoleInput() throws Exception {
		List<String> cmd = Arrays.asList(scanner.nextLine().toLowerCase().split(" "));
		boolean valid = false;
		String errMessage = "";

		if (cmd.size() < 0) {
			errMessage = "Vennligst skriv inn en kommando";
		} else if (!cmdFlags.contains(cmd.get(0))) {
			errMessage = "Ugyldig kommando, " + "skriv 'help' for å få en oversikt " + "over tilgjengelige kommandoer";
		} else if (cmd.get(0).equalsIgnoreCase("quit")) {
			System.out.println("Applikasjonen er avsluttet");
			System.exit(-1);
		} else {
			valid = true;
			inputs.addAll(cmd);
		}

		if (!valid)
			throw new Exception(errMessage);
	}

	protected AbstractModel lookupIndex(ArrayList<? extends AbstractModel> list) {
		try {
			int index = Integer.parseInt(inputs.get(1)) - 1;

			return list.get(index);
		} catch (NumberFormatException e) {
			System.err.println("Ugyldig input, du må taste inn et heltall");
		} catch (IndexOutOfBoundsException e2) {
			System.err.println("Ugyldig valg");
		}
		return null;
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
