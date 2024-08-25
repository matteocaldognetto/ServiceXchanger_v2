package it.unibs.ing.elaborato.menu.configMenu;

import it.unibs.ing.elaborato.exception.FileWriterException;
import it.unibs.ing.elaborato.exception.LogoutException;
import it.unibs.ing.elaborato.model.district.DistrictHandler;
import it.unibs.ing.elaborato.model.conversionElement.ConversionElementHandler;
import it.unibs.ing.elaborato.model.hierarchy.HierarchyHandler;
import it.unibs.ing.elaborato.model.closedSet.ClosedSetHandler;
import it.unibs.ing.elaborato.menu.Executable;
import it.unibs.ing.elaborato.model.proposal.ExchangeProposalHandler;
import it.unibs.ing.elaborato.util.Constants;
import it.unibs.ing.elaborato.menu.LogoutController;
import it.unibs.ing.elaborato.menu.TerminationController;
import it.unibs.ing.elaborato.util.Utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe che si occupa della gestione del menu.
 * Presenta all'utente delle opzioni e ne supervisiona il comportamento attraverso opportuni controlli sull'input dei dati.
 */
public class MenuConfigContext {

	private final Map<Integer, Executable> actions;

	public MenuConfigContext(DistrictHandler districts, HierarchyHandler hierarchies, ConversionElementHandler conversionElements, ExchangeProposalHandler exchangeProposals, ClosedSetHandler closedSets, Scanner scanner) {
		actions = new HashMap<>();
		actions.put(Constants.NUMBER_1_MESSAGE, new InsertNewDistrictController(districts, scanner));
		actions.put(Constants.NUMBER_2_MESSAGE, new InsertNewHierarchyController(hierarchies, conversionElements, scanner));
		actions.put(Constants.NUMBER_3_MESSAGE, new ShowDistrictsController(districts, scanner));
		actions.put(Constants.NUMBER_4_MESSAGE, new ShowHierarchiesController(hierarchies, scanner));
		actions.put(Constants.NUMBER_5_MESSAGE, new ShowConvFactController(hierarchies, conversionElements, scanner));
		actions.put(Constants.NUMBER_6_MESSAGE, new ShowProposalsController(hierarchies, exchangeProposals, scanner));
		actions.put(Constants.NUMBER_7_MESSAGE, new ShowClosedSetsController(closedSets, scanner));
		actions.put(Constants.NUMBER_8_MESSAGE, new LogoutController());
		actions.put(Constants.NUMBER_0_MESSAGE, new TerminationController());
	}

	public void chooseOption(Scanner scanner) throws LogoutException, FileWriterException {
		int index;

		do {
			System.out.print(Constants.TITLE_MENU);
			System.out.println(Constants.CONFIG_OPTION);

			index = Integer.parseInt(Utility.checkCondition(Constants.SELECT_FROM_THE_OPTIONS_MESSAGE, Constants.INVALID_INPUT_MESSAGE, input -> !Utility.isInt(input) || !(Integer.parseInt(input) >= Constants.NUMBER_0_MESSAGE && Integer.parseInt(input) <= Constants.NUMBER_8_MESSAGE), scanner));
			Executable action = actions.get(index);

			Utility.clearConsole(Constants.TRANSACTION_TIME);
			action.execute();
			Utility.clearConsole(Constants.TRANSACTION_TIME);
		} while (index != Constants.NUMBER_0_MESSAGE && index != Constants.NUMBER_8_MESSAGE);
	}
}