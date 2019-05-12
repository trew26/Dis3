package de.dis2011;

import de.dis2011.data.*;

/**
 * Hauptklasse
 */
public class Main {
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}
	
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//print classpath for debugging purposes
		System.out.println(System.getProperty("java.class.path"));
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int QUIT = 1;
		final int MENU_ESTATES = 2;
		final int SHOW_CONTRACT_MENU = 3;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Estate-Verwaltung", MENU_ESTATES);
		mainMenu.addEntry("Vertragsmenü", SHOW_CONTRACT_MENU);
		mainMenu.addEntry("Beenden", QUIT);
		
		//Verarbeite Eingabe
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_MAKLER:
					String password = "temppw123";
					String user_pw = FormUtil.readString("Bitte geben Sie das Passwort ein");
					if (!password.equals(user_pw)) {
						System.out.println("Falsches Passwort!");
						break;
					} else {
						showMaklerMenu();
						break;
					}
				case MENU_ESTATES:
					showEstateMenu();
					break;
				case SHOW_CONTRACT_MENU:
					// check login from makler
					String agent_login = FormUtil.readString("Bitte geben Sie den Makler Login ein");
					String agent_pw = FormUtil.readString("Bitte geben Sie das Makler Passwort ein");

				//	Boolean correct_login = Makler.check_login(agent_login, agent_pw);
boolean correct_login = true;
					if (correct_login){
						System.out.println("Login erfolgreich!");
						System.out.println();

					} else {
						System.out.println("Login war nicht erfolgreich!");
						System.out.println();
						return;
					}
					showContractMenu(agent_login);
				case QUIT:
					return;
			}
		}
	}
	
	/**
	 * Zeigt die Maklerverwaltung
	 */
	public static void showMaklerMenu() {
		//Menüoptionen
		final int NEW_MAKLER = 0;
		final int BACK = 1;
		final int EDIT_MAKLER = 2;
		final int DELETE_MAKLER = 3;
		final int SHOW_MAKLERS = 4;
		final int TEST_HIB_GET = 5;
		
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
		maklerMenu.addEntry("Zeige alle Makler", SHOW_MAKLERS);
		maklerMenu.addEntry("Makler bearbeiten", EDIT_MAKLER);
		maklerMenu.addEntry("Makler löschen", DELETE_MAKLER);
		maklerMenu.addEntry("Test HIB Get", TEST_HIB_GET);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		//Verarbeite Eingabe
		while(true) {
			int response = maklerMenu.show();
			switch(response) {
				case NEW_MAKLER:
					newMakler();
					break;
				case BACK:
					return;
				case EDIT_MAKLER:
					editMakler();
					break;
				case DELETE_MAKLER:
					deleteMakler();
					break;
				case SHOW_MAKLERS:
					showAllMaklers();
					break;
				case TEST_HIB_GET:
					restHibGetMakler();
			}
		}
	}
	
	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newMakler() {
		Makler m = new Makler();
		
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();
		
		System.out.println("Makler mit der ID "+m.getId()+" wurde erzeugt.");
	}

	/**
	 * Löschen einen existierenden Makler, nachdem der Benutzer die ID eingegeben hat
	 */
	public static void deleteMakler() {
		// just testing here...
		int makler_id = FormUtil.readInt("ID des zu löschenden Maklers");
		Makler m =  new Makler();
		m = m.load(makler_id);

		Makler direkt_m = Makler.load(makler_id);

		// actual delete call
		Makler.delete(makler_id);
	}

	/**
	 * Bearbeitet eines existierenden Maklers andhand der angegebenen ID
	 */
	public static void editMakler() {
		int makler_id = FormUtil.readInt("ID des zu bearbeitenden Maklers");
		Makler m = Makler.load(makler_id);
		m.setName(FormUtil.readString("Neuer Name"));
		m.setAddress(FormUtil.readString("Neue Adresse"));
		m.setLogin(FormUtil.readString("Neuer Login"));
		m.setPassword(FormUtil.readString("Neues Passwort"));
		m.save();

		System.out.println("Makler mit der ID "+m.getId()+" wurde gespeichert.");
	}

	/**
	 * Gibt alle existierenden Makler aus
	 */
	public static void showAllMaklers(){
		Makler.showAll();
	}

	/**
	 * Öffnet die Estate Verwaltung. Dazu muss sich ein Makler mit seinem Passwort und Login anmelden.
	 */
	public static void showEstateMenu() {
		// check login from makler
		String agent_login = FormUtil.readString("Bitte geben Sie den Makler Login ein");
		String agent_pw = FormUtil.readString("Bitte geben Sie das Makler Passwort ein");

		Boolean correct_login = Makler.check_login(agent_login, agent_pw);

		if (correct_login){
			System.out.println("Login erfolgreich!");
			System.out.println();

		} else {
			System.out.println("Login war nicht erfolgreich!");
			System.out.println();
			return;
		}

		//Menüoptionen
		final int NEW_HOUSE = 0;
		final int NEW_APARTMENT = 1;
		final int BACK = 2;
		final int EDIT_ESTATE = 3;
		final int DELETE_ESTATE = 4;
		final int SHOW_ESTATES = 5;
		final int SHOW_CONTRACT_MENU = 6;
		final int TEST_HIBERNATE_ESTATE_BY_ID = 7;
		final int HIB_SHOW_ALL_ESTATES = 8;
		final int HIB_DELETE_ESTATE = 9;
		final int HIB_TEST_CREATE_ESTATE = 10;
		final int HIB_CREATE_APARTMENT = 11;
		final int HIB_CREATE_HOUSE = 12;

		//Estateverwaltungsmenü
		Menu estateMenu = new Menu("Estate-Verwaltung");
		estateMenu.addEntry("Estate anlegen (Test, Hibernate)", HIB_TEST_CREATE_ESTATE);
		estateMenu.addEntry("Haus anlegen", NEW_HOUSE);
		estateMenu.addEntry("Haus anlegen (Hibernate)", HIB_CREATE_HOUSE);
		estateMenu.addEntry("Apartment anlegen", NEW_APARTMENT);
		estateMenu.addEntry("Apartment anlegen (Hibernate)", HIB_CREATE_APARTMENT);
		estateMenu.addEntry("Zeige alle Estates", SHOW_ESTATES);
		estateMenu.addEntry("Zeige alle Estates (Hibernate)", HIB_SHOW_ALL_ESTATES);
		estateMenu.addEntry("Estate bearbeiten", EDIT_ESTATE);
		estateMenu.addEntry("Estate löschen", DELETE_ESTATE);
		estateMenu.addEntry("Estate löschen (Hibernate)", HIB_DELETE_ESTATE);
		estateMenu.addEntry("Test Hibernate Estate by ID", TEST_HIBERNATE_ESTATE_BY_ID);
		estateMenu.addEntry("Zurück zum Hauptmenü", BACK);

		//Verarbeite Eingabe
		while(true) {
			int response = estateMenu.show();
			switch(response) {
				case NEW_HOUSE:
					createHouse(agent_login);
					break;
				case NEW_APARTMENT:
					createApartment(agent_login);
					break;
				case BACK:
					return;
				case EDIT_ESTATE:
					editEstate(agent_login);
					break;
				case DELETE_ESTATE:
					deleteEstate(agent_login);
					break;
				case SHOW_ESTATES:
					showEstates(agent_login);
					break;
				case SHOW_CONTRACT_MENU:
					showContractMenu(agent_login);
					break;
				case TEST_HIBERNATE_ESTATE_BY_ID:
					testHibernateById();
					break;
				case HIB_SHOW_ALL_ESTATES:
					showAllEstatesHib();
					break;
				case HIB_DELETE_ESTATE:
					deleteEstateHib();
					break;
				case HIB_TEST_CREATE_ESTATE:
					createEstateTestHib(agent_login);
					break;
				case HIB_CREATE_APARTMENT:
					createApartmentHib(agent_login);
					break;
				case HIB_CREATE_HOUSE:
					createHouseHib(agent_login);
			}
		}

	}

	/**
	 * Erzeugt ein neues Haus Objekt in der Datenbank
	 * @param makler_login der angemeldete Makler
	 */
	public static void createHouse(String makler_login) {
		Estate estate = new Estate();
		//Estate attribute abfragen
		estate.setZip(FormUtil.readInt("ZIP (int)"));
		estate.setNumber(FormUtil.readInt("Number (int)"));
		estate.setCity(FormUtil.readString("City (string)"));
		estate.setStreet(FormUtil.readString("Street (string)"));
		estate.setArea(FormUtil.readString("Area (string)"));
		estate.setFloors(FormUtil.readInt("Floors (int)"));
		estate.setPrice(FormUtil.readInt("Price (int)"));
		estate.setGarden(FormUtil.readInt("Garden (1 if present)"));
		//den makler benutzen welcher sich eingeloggt hat
		Makler m = Makler.getByLogin(makler_login);

		estate.setFk_agent(m);

		estate.save();
		estate.saveHouse();
	}

	public static void createHouseHib(String makler_login) {
		Estate estate = new Estate();
		//Estate attribute abfragen
		estate.setZip(FormUtil.readInt("ZIP (int)"));
		estate.setNumber(FormUtil.readInt("Number (int)"));
		estate.setCity(FormUtil.readString("City (string)"));
		estate.setStreet(FormUtil.readString("Street (string)"));
		estate.setArea(FormUtil.readString("Area (string)"));
		estate.setFloors(FormUtil.readInt("Floors (int)"));
		estate.setPrice(FormUtil.readInt("Price (int)"));
		estate.setGarden(FormUtil.readInt("Garden (1 if present)"));
		//den makler benutzen welcher sich eingeloggt hat
		Makler m = Makler.getByLogin(makler_login);

		estate.setFk_agent(m);

		estate.saveHib();
		estate.saveHouseHib();
	}

	/**
	 * Just for testing, inserts a new estate into the db using hibernate
	 * @param makler_login the logged in agent
	 */
	public static void createEstateTestHib(String makler_login) {
		Estate estate = new Estate();
		//Estate attribute abfragen
		estate.setZip(FormUtil.readInt("ZIP (int)"));
		estate.setNumber(FormUtil.readInt("Number (int)"));
		estate.setCity(FormUtil.readString("City (string)"));
		estate.setStreet(FormUtil.readString("Street (string)"));
		estate.setArea(FormUtil.readString("Area (string)"));
		estate.setFloors(FormUtil.readInt("Floors (int)"));
		estate.setPrice(FormUtil.readInt("Price (int)"));
		estate.setGarden(FormUtil.readInt("Garden (1 if present)"));
		//den makler benutzen welcher sich eingeloggt hat
		Makler m = Makler.getByLogin(makler_login);
		estate.setFk_agent(m);

		estate.saveHib();
	}

	public static void createApartmentHib(String makler_login) {
		Estate estate = new Estate();

		//Estate attribute abfragen
		estate.setZip(FormUtil.readInt("ZIP (int)"));
		estate.setNumber(FormUtil.readInt("Number (int)"));
		estate.setCity(FormUtil.readString("City (string)"));
		estate.setStreet(FormUtil.readString("Street (string)"));
		estate.setArea(FormUtil.readString("Area (Stadtteil)"));
		estate.setFloor(FormUtil.readInt("Floor"));
		estate.setRent(FormUtil.readInt("Rent"));
		estate.setRooms(FormUtil.readInt("Rooms"));
		estate.setBalcony(FormUtil.readInt("Balcony (1 if present)"));
		estate.setKitchen(FormUtil.readInt("Kitchen (1 if present)"));
		//den makler benutzen welcher sich eingeloggt hat
		Makler m = Makler.getByLogin(makler_login);
		estate.setFk_agent(m);

		estate.saveHib();
		estate.saveApartmentHib();
	}

	/**
	 * Erzeugt ein neues Estate Objekt in der Datenbank
	 * @param makler_login der angemeldete Makler
	 */
	public static void createApartment(String makler_login) {
		Estate estate = new Estate();
		//Estate attribute abfragen
		estate.setZip(FormUtil.readInt("ZIP (int)"));
		estate.setNumber(FormUtil.readInt("Number (int)"));
		estate.setCity(FormUtil.readString("City (string)"));
		estate.setStreet(FormUtil.readString("Street (string)"));
		estate.setArea(FormUtil.readString("Area (Stadtteil)"));
		estate.setFloor(FormUtil.readInt("Floor"));
		estate.setRent(FormUtil.readInt("Rent"));
		estate.setRooms(FormUtil.readInt("Rooms"));
		estate.setBalcony(FormUtil.readInt("Balcony (1 if present)"));
		estate.setKitchen(FormUtil.readInt("Kitchen (1 if present)"));
		//den makler benutzen welcher sich eingeloggt hat
		Makler m = Makler.getByLogin(makler_login);

		estate.setFk_agent(m);

		estate.save();
		estate.saveApartment();

	}

	/**
	 * Gibt alle Estates aus vom eingeloggten Makler
	 * @param makler_login der angemeldete Makler
	 */
	public static void showEstates(String makler_login) {
		Estate.showEstates(makler_login);
	}

	/**
	 * löscht eine Estate. Der angemeldete Makler kann nur Estates löschen, welche von Ihm verwaltet werden
	 * @param agent_login der angemeldete Makler
	 */
	public static void deleteEstate(String agent_login) {
		int estate_id = FormUtil.readInt("ID der zu löschenden Estate");
		Estate.deleteEstate(estate_id, agent_login);
	}

	/**
	 * Bearbeiten einer Estate. Der Angemeldete Makler kann nur Estate bearbeiten, welche von Ihm verwaltet werden
	 * @param agent_login der angemeldete Makler
	 */
	public static void editEstate(String agent_login) {
		int estate_id = FormUtil.readInt("ID der zu bearbeitenden Estate");

		System.out.println("Geben Sie nun die neuen Werte für die Estate an");

		Estate estate = new Estate();
		//Estate attribute abfragen
		estate.setId(estate_id);
		estate.setZip(FormUtil.readInt("Neuer ZIP-Code"));
		estate.setNumber(FormUtil.readInt("Neue Nummer"));
		estate.setCity(FormUtil.readString("Neue City"));
		estate.setStreet(FormUtil.readString("Neue Straße"));
		estate.setArea(FormUtil.readString("Neue Area (Stadtteil)"));

		Makler m = Makler.getByLogin(agent_login);
		estate.setFk_agent(m);

		estate.setFloor(FormUtil.readInt("Floor"));
		estate.setRent(FormUtil.readInt("Rent"));
		estate.setRooms(FormUtil.readInt("Rooms"));
		estate.setBalcony(FormUtil.readInt("Balcony (1 if present)"));
		estate.setKitchen(FormUtil.readInt("Kitchen (1 if present)"));
		estate.setFloors(FormUtil.readInt("Floors"));
		estate.setPrice(FormUtil.readInt("Price"));
		estate.setGarden(FormUtil.readInt("Garden (1 if present)"));

		estate.save();
		estate.updateApartment(agent_login);
		estate.updateHouse(agent_login);
	}
public static void showContractMenu(String agent_login){
	//Menüoptionen
	final int NEW_PERSON = 0;
	final int NEW_SELL = 1;
	final int BACK = 2;
	final int NEW_RENT = 3;
	final int SHOW_CONTRACTS = 4;

	//Estateverwaltungsmenü
	Menu estateMenu = new Menu("Vertrag-Verwaltung");
	estateMenu.addEntry("Person anlegen", NEW_PERSON);
	estateMenu.addEntry("Kaufvertrag", NEW_SELL);
	estateMenu.addEntry("Mietvertrag", NEW_RENT);
	estateMenu.addEntry("Verträge anzeigen", SHOW_CONTRACTS);
	estateMenu.addEntry("Zurück zum Hauptmenü", BACK);

	//Verarbeite Eingabe
	while(true) {
		int response = estateMenu.show();
		switch(response) {
			case NEW_PERSON:
				createPerson();
				break;
			case NEW_RENT:
				createTenancy();
				break;
			case BACK:
				showMainMenu();
			case NEW_SELL:
				createPurchase();
				break;
			case SHOW_CONTRACTS:
				showContracts();
				break;
		}
	}
}
	/**
	 * Erzeugt eine neue Person in der Datenbank
	 */
	public static void createPerson() {
		Person person = new Person();

		person.setFirst_name(FormUtil.readString("First Name"));
		person.setLast_name(FormUtil.readString("Last Name"));
		person.setAddress(FormUtil.readString("Address"));

		person.savePerson();
	}

	/**
	 * Erzeugt ein neues Estate Objekt in der Datenbank
	 */
	public static void createTenancy() {
		Tenancy tenancy = new Tenancy();


		tenancy.setPersonId(FormUtil.readInt("Person (ID)"));
		tenancy.setAppartmentId(FormUtil.readInt("Appartment (ID)"));
		tenancy.setContractdate(FormUtil.readString("Datum des Vertragsvereinbarung (YYYY-MM-DD)"));
		tenancy.setStartdate(FormUtil.readString("Startdatum des Vertrags (YYYY-MM-DD)"));
		tenancy.setPlace(FormUtil.readString("Place"));
		tenancy.setDuration(FormUtil.readInt("Duration"));
		tenancy.setAdditional_cost(FormUtil.readInt("Additional Cost"));

		tenancy.saveTenancy();
	}

	public static void createPurchase() {
		Purchase purchase = new Purchase();

		Integer personID = FormUtil.readInt("Person (ID)");
		Person p = Person.getByID(personID);
		purchase.setFk_person(p);

		Integer houseID = FormUtil.readInt("Haus (ID)");
		House h = House.getByID(houseID);
		purchase.setHouse(h);

		purchase.setHouseId(houseID);
		purchase.setPlace(FormUtil.readString("Place"));
		purchase.setContractdate(FormUtil.readString("Datum des Vertragsvereinbarung (YYYY-MM-DD)"));
		purchase.setInstallment(FormUtil.readInt("Installments"));
		purchase.setRate(FormUtil.readInt("Rate"));

		purchase.savePurchase();
	}

	public static void showContracts() {

		Contract contract = new Contract();

		contract.showContracts();
	}

	/**
	 * Hibernate: Gibt eine Estate anhand der ID aus
	 */
	public static void testHibernateById() {
		int id = FormUtil.readInt("Estate ID");
		Estate test_estate = new Estate();

		test_estate.testHibEstateGet(id);
	}

	/**
	 * Hibernate: Gibt einen Makler anhand der ID aus
	 */
	public static void restHibGetMakler() {
		int id = FormUtil.readInt("Makler ID");
		Makler test_makler = new Makler();

		test_makler.testHibMaklerGet(id);

	}

	/**
	 * Hibernate: Zeigt alle Estates an
	 */
	public static void showAllEstatesHib() {
		Estate.showAllEstatesHib();
	}

	public static void deleteEstateHib() {
		int id  = FormUtil.readInt("ID der zu löschenden Estate");
		Estate.deleteEstateHib(id);
	}

}
