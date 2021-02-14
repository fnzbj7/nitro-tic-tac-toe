
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
		Frontend elérése: http://localhost:4200
		
Technológiai stack:

	Backend: Spring boot, Rest, Swagger, Lombok, Jpa(-), h2(-), JUnit(-)
	(-): Elérhetőek, de nem lettek felhasználva
		
	Frontend: Angular, Scss
		
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
			
			
Backend lényegesebb fájlrendszeri felépítés

	hu.nitro.tictactoe.*
	
	hu.nitro.tictactoe.controller
		A rest endpointok belépő helye
		
	hu.nitro.tictactoe.service
		Az üzleti logika itt történik. Jelenleg az adatbázist helyettesítő "Map" is itt található, mely egy idhoz (Long) ad vissza egy játék megvalósítást (GameModel)
		
	hu.nitro.tictactoe.dto
		Minden olyan osztály, mely a rest endpointon keresztül ki és be közlekedik
		
	hu.nitro.tictactoe.model
		Bármely olyan összefogó java osztály, melyre belső használatra szükség van
		Megjegyzés: jelenleg létezik olyan model osztály is, melyet visszaküldünk Rest-en keresztül nem helyesen (hisz ez a Dto feladata), de idő hiányában nem készítettem Mapper osztályokat melyek elvégeznék a Dto/Model konvertálást

Frontend lényegesebb fájlrendszeri felépítése:

	\frontend\src\app\game\
	
		game.component.ts
			Itt található a megjelenítéshez szükséges logika

		game.component.html + game.component.scss
			A html és az scss

		game.service.ts
			Ez az osztály végzi a backenddel való kommunikációt, ha lett volna komolyabb (nem megjelenítéshez köthető) logika, akkor az ide került volna
		
	\frontend\src\app\app-routing.module.ts
		
		Nem került lefejlesztésre, de 2 route-al is rendelkezik a frontend: egy ahol a játék elkezdődik és egy (ez nem lett lefejlesztve) route, ahol képesek lehetnénk egy már elkezdett játékot folytatni egy játék id megadása után (Az url-ben paraméterként)







