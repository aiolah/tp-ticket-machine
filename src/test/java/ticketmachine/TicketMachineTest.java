package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void noTicketIfNotEnoughMoney() {
		machine.insertMoney(PRICE - 1);
        assertFalse(machine.printTicket(), "Il n'y a pas assez d'argent");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void printTicketIfEnoughMoney() {
		machine.insertMoney(PRICE);
        assertTrue(machine.printTicket(), "Ticket non imprimé");
	}

	@Test
	// S5 : Quand on imprime un ticket, la balance est décrémentée du prix du ticket
	void lessBalanceIfTicketPrinted() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0, machine.getBalance(), "Le montant n'a pas été débité");
	}

	@Test
	// S6 :  le montant collecté est mis à jour quand on imprime un ticket (pasavant)
	void totalAugmenteAfterPrintedTicket() {
		machine.insertMoney(50);
		machine.printTicket();
		machine.insertMoney(50);
		machine.printTicket();

		assertEquals(100, machine.getTotal(), "L'argent n'est pas bien ajouté");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void returnRefund() {
		machine.insertMoney(PRICE);
		machine.insertMoney(PRICE);
		assertEquals(100, machine.refund(), "Le montant rendu n'est pas le bon");
	}

	@Test
	// S8 : refund()remet la balance à zéro
	void balanceEqualsZeroAfterRefund() {
		machine.insertMoney(PRICE);
		machine.insertMoney(PRICE);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La machine n'a pas rendu tout l'argent");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void noNegativeAmountMustReturnException() {
		try {
			machine.insertMoney(-1);

			fail("Cet appel doit lancer une exception !");
		}
		catch(IllegalArgumentException e)
		{

		}
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void noMachineCreatedIfNegativePrice() {
		try {
			TicketMachine machine = new TicketMachine(-1);

			fail("Cet appel doit lancer une exception !");
		}
		catch(IllegalArgumentException e)
		{

		}
	}
}
