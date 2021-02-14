
Hogyan kell elindítani:
	Backend: 
		String boot
		A backend mappában a következő parancsot kiadva indul el a localhost:8080-as porton
		"./mvnw spring-boot:run" vagy "mvnw spring-boot:run"
		Swagger elérése: http://localhost:8080/api/swagger-ui.html
		
	Frontend: 
		Angular
		A Frontend mappába belépve először instalálni kell az npm package-eket
		"npm install"
		Utána ugyan ebben a mappában elindítani
		"npm run start" 
		
Fejlesztés neki kezdése:
	Összegyújtöttem az információkat, miket kell/fogok elkészíteni, ezek alapján egy egyszerű user story-t összeállítottam, a szükséges backend endpointokat összeírtam.
	Következő lépésben elkészítettem a backend és a frontend alap vázát (Backend: spring initializr, Frontend: ng cli ), alap kommunikáció összeállítása. CORS elkerüléséhez Angulárnál beállítottam webpack-ben egy proxy-t (/frontend/proxy.conf.json)
	
A játék reprezentációja a játékban

	Backend:
	
		Egy játék entitás a következőket tartalmazza: a pályát, hogy melyik játékos következik, hány lépés tettek meg és a játék állapotát
		
		A játék mezőtjét egy 9 elemű tömbben repezentálom 0-tól 8-ig
		0 1 2
		3 4 5
		6 7 8
		Egy mező 3 értéket vehet fel: "X", "O" és null
		
		Következő játékos: Ezzel a mezővel ellenőrizzük le, hogy melyik játékos lépését várja a backend
		
		Játékban megtett lépések száma: Ebből a számból következtetünk arra, hogy mikor lett a játék döntetlen
		
		Játék állapota: Ez 4 féle lehet: Eldöntetlen (még zajlik a játék), nyert X, nyert O és döntetlen
		
	A Frontend hasonló felépítéssel rendelkezik
	
Backend Endpointok:

	/create-game
		létrehoz egy játékot és a játék azonosítóját visszaküldi
		
	/next-step
		Vár egy játék azonosítót, hogy melyik mezőt állítja, és hogy melyik játékos szeretné
		Végrehajtja a lépést, ellenörzi, hogy váűltozott-e a játék állása (lett-e győztes) és visszaküldi a játék jelenlegi állását
		A következő ellenörzéseket végzi:
			- Létezik-e a játék az id alapján
			- Véget ért-e a játék
			- Az aktuális játékos szeretne-e lépni
			- Helyes mezőre hivatkozik-e
			- Foglalt-e a mező
			
További le nem fejlesztett endpointok:
	/surrender
		lehetséges legyen az adott játékot feladni
	
	/game-status
		Egy adott játék aktuális állását le lehessen kérni.
		Erre akkor lenne szükség, ha valami hiba folytán félbeszakadt a játék, de még is szeretnék folytatni
			
	/ai-next-step
		TESZT, egy külön endpoint a programozott játékos számára. Ez biztosítja azt, hogy akár tudna egymás ellen játszani 2 AI is.
			
			

