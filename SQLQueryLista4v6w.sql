create table TypTransakcji(
ID_Typ INT PRIMARY KEY IDENTITY ( 1, 1),
NazwaTypu nchar(20) unique)
--------------------------------------------------------------------------------------------------------------------------------------
create table RodzajTransakcji(
ID_Rodzaj INT PRIMARY KEY IDENTITY ( 1, 1),
Nazwa nchar(20) unique)
--------------------------------------------------------------------------------------------------------------------------------------
create table Artykul(
ID_Artykulu INT PRIMARY KEY IDENTITY ( 1, 1),
NazwaArtykulu nchar(20) unique)
---------------------------------------------------------------------------------------------------------------------------------------------
create table Lokalizacja(
ID_Lokalizacji INT PRIMARY KEY IDENTITY ( 1, 1),
Ulica nchar(50),
NrBudynku nchar(20),
Miasto nchar(20),
KodPocztowy nchar(20))
--------------------------------------------------------------------------------------------------------------------------------------
create table Podmiot(
ID_Podmiotu INT PRIMARY KEY IDENTITY ( 1, 1),
NazwaPodmiotu nchar(20),
ID_Lokalizacja int references dbo.Lokalizacja(ID_Lokalizacji))
--------------------------------------------------------------------------------------------------------------------------------------
create table Transakcje(
ID_Transakcji INT PRIMARY KEY IDENTITY ( 1, 1),
Data datetime,
ID_TypTransakcji int references dbo.TypTransakcji(ID_Typ), 
ID_RodzajTransakcji int references dbo.RodzajTransakcji(ID_Rodzaj),
ID_Artykul int references dbo.Artykul(ID_Artykulu),
ID_Podmiot int references dbo.Podmiot(ID_Podmiotu),
Kwota smallmoney,
Uwagi nchar(200))
--------------------------------------------------------------------------------------------------------------------------------------
create table Budzet(
ID_Budzet INT PRIMARY KEY IDENTITY ( 1, 1),
Data datetime,
Transakcja int references dbo.Transakcje(ID_Transakcji),
Saldo smallmoney)
--------------------------------------------------------------------------------------------------------------------------------------
/*
DROP TABLE Budzet
DROP TABLE Transakcje
DROP TABLE TypTransakcji
DROP TABLE RodzajTransakcji
DROP TABLE Artykul
DROP TABLE Podmiot
DROP TABLE Lokalizacja
*/
/*--------------------------------------------------------------------------------------------------------------------------------------
Zalozenia:
typ transakcji jest podawany z pola wybieralnego innaczej mowiac uzytkownik sam go nie wpisuje
*/--------------------------------------------------------------------------------------------------------------------------------------
CREATE PROCEDURE dodawanieRachunku @data datetime , @typTransakcji nchar(20) , @rodzajTransakcji nchar(20), @artykuly nchar(20), @podmiot nchar(20) ,@podmiotUlica nchar(50), @podmiotBudynek nchar(20), @podmiotMiasto nchar(20) , @podmiotKod nchar(20) , @kwota smallmoney, @uwagi nchar(200)
AS
BEGIN
	declare @idTyp int
	declare @idRodzaj int
	declare @idArtyklu int
	declare @idLokalizacja int
	declare @idPodmiot int
	declare @idTransakcja int
	declare @Saldo smallmoney
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	SET @idTyp = (SELECT ID_Typ FROM TypTransakcji WHERE NazwaTypu = @typTransakcji) 
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	IF (SELECT COUNT(*) FROM RodzajTransakcji WHERE Nazwa = @rodzajTransakcji) > 0
	BEGIN
		 SET @idRodzaj = (SELECT ID_Rodzaj FROM RodzajTransakcji WHERE Nazwa = @rodzajTransakcji)
	END 
	ELSE 
	BEGIN
		INSERT INTO RodzajTransakcji VALUES (@rodzajTransakcji)
		SET @idRodzaj = (SELECT ID_Rodzaj FROM RodzajTransakcji WHERE Nazwa =  @rodzajTransakcji)
	END
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	IF (SELECT COUNT(*) FROM Artykul WHERE NazwaArtykulu = @artykuly) > 0
	BEGIN
		 SET @idArtyklu = (SELECT ID_Artykulu FROM Artykul WHERE NazwaArtykulu = @artykuly)
	END 
	ELSE 
	BEGIN
		INSERT INTO Artykul VALUES (@artykuly)
		SET @idArtyklu = (SELECT ID_Artykulu FROM Artykul WHERE NazwaArtykulu = @artykuly)
	END
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	IF (SELECT COUNT(*) FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod) > 0
	BEGIN
		 SET @idLokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)
	END 
	ELSE 
	BEGIN
		INSERT INTO Lokalizacja VALUES (@podmiotUlica , @podmiotBudynek , @podmiotMiasto , @podmiotKod)
		SET @idLokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)
	END
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
IF (SELECT COUNT(*) FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = @idLokalizacja) > 0
	BEGIN
		 SET @idPodmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = @idLokalizacja)
	END 
	ELSE 
	BEGIN
		INSERT INTO Podmiot VALUES (@podmiot , @idLokalizacja)
		SET @idPodmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = @idLokalizacja)
	END
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
INSERT INTO Transakcje VALUES (@data , @idTyp , @idRodzaj , @idArtyklu , @idPodmiot , @kwota , @uwagi)
SET @idTransakcja = (SELECT TOP 1 ID_Transakcji FROM Transakcje ORDER BY  ID_Transakcji DESC)
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
IF (SELECT COUNT(*) FROM Budzet) > 0
BEGIN 
	IF (SELECT COUNT(*) FROM Budzet WHERE Data > @data) > 0
	BEGIN
		IF (SELECT COUNT(*) FROM Budzet WHERE Data <= @data) > 0
		BEGIN
			SET @Saldo = (SELECT TOP 1 Saldo FROM Budzet WHERE Data <= @data ORDER BY Data DESC)
		END 
		ELSE
		BEGIN
			SET @Saldo = 0
		END
		SET @Saldo = @Saldo + @kwota
		UPDATE Budzet SET Saldo = Saldo + @kwota WHERE Data >= @data
		INSERT INTO Budzet VALUES (@data , @idTransakcja , @Saldo)	 
	END
	ELSE 
	BEGIN
		SET @Saldo = (SELECT TOP 1 Saldo FROM Budzet ORDER BY  Data DESC)	
		SET @Saldo = @Saldo + @kwota
		INSERT INTO Budzet VALUES (@data , @idTransakcja , @Saldo)
	END	
END
ELSE
BEGIN
	SET @Saldo = 0
	SET @Saldo = @Saldo + @kwota
	INSERT INTO Budzet VALUES (@data , @idTransakcja , @Saldo)

END
--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
END
--------------------------------------------------------------------------------------------------------------------------------------
insert into TypTransakcji  values ('dochody')
insert into TypTransakcji  values ('wydatki')

exec dodawanieRachunku '2016-12-22' , 'wydatki' , 'transport' , 'bilet' , 'pks' , 'slezna' , '18' , 'wroclaw' , '10-100' , -12 , 'brak'

--DROP PROCEDURE dodawanieRachunku
--------------------------------------------------------------------------------------------------------------------------------------

SELECT * FROM Budzet
SELECT * FROM Transakcje
SELECT * FROM Podmiot
SELECT * FROM Artykul
SELECT * FROM TypTransakcji
SELECT * FROM RodzajTransakcji
SELECT * FROM Lokalizacja
--------------------------------------------------------------------------------------------------------------------------------------
SELECT Transakcje.ID_Transakcji , Transakcje.Data , TypTransakcji.NazwaTypu , RodzajTransakcji.Nazwa , Artykul.NazwaArtykulu , 
Podmiot.NazwaPodmiotu , Lokalizacja.Ulica ,Lokalizacja.NrBudynku , Lokalizacja.Miasto , Lokalizacja.KodPocztowy , 
Transakcje.Kwota , Transakcje.Uwagi , Budzet.Saldo FROM Budzet 
					INNER JOIN Transakcje ON Budzet.Transakcja = Transakcje.ID_Transakcji 
					INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu
					INNER JOIN Artykul ON Transakcje.ID_Artykul = Artykul.ID_Artykulu
					INNER JOIN TypTransakcji ON Transakcje.ID_TypTransakcji = TypTransakcji.ID_Typ
					INNER JOIN RodzajTransakcji ON Transakcje.ID_RodzajTransakcji = RodzajTransakcji.ID_Rodzaj
					INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji ORDER BY Transakcje.Data
--------------------------------------------------------------------------------------------------------------------------------------
CREATE PROCEDURE modyfikowanieRachunku @idtransakcji int , @data datetime , @typTransakcji nchar(20) , @rodzajTransakcji nchar(20), @artykuly nchar(20), @podmiot nchar(20) ,@podmiotUlica nchar(50), @podmiotBudynek nchar(20), @podmiotMiasto nchar(20) , @podmiotKod nchar(20) , @kwota smallmoney, @uwagi nchar(200)
AS
BEGIN
	IF @data != (SELECT Data FROM Transakcje WHERE ID_Transakcji = @idtransakcji)
	BEGIN
		DECLARE @dataWczesniej DATETIME
		DECLARE @dataWczesniejid INT
		DECLARE @dataPozniejsza DATETIME
		DECLARE @dataPozniejszaid INT 
		IF @data < (SELECT Data FROM Transakcje WHERE ID_Transakcji = @idtransakcji)				--podroz do przeszlosci
		BEGIN
			SET @dataWczesniej = @data
			IF (SELECT COUNT(*) FROM Budzet WHERE Data = @dataWczesniej AND Budzet.Transakcja > @idtransakcji) > 0 
			BEGIN
				SET @dataWczesniejid = (SELECT TOP 1 ID_Budzet FROM Budzet WHERE Data = @dataWczesniej AND Budzet.Transakcja > @idtransakcji ORDER BY Budzet.Transakcja DESC)
			END
			ELSE
			BEGIN
				SET @dataWczesniejid = (SELECT MAX(ID_Budzet) FROM Budzet)
			END
			SET @dataPozniejsza = (SELECT Data FROM Transakcje WHERE ID_Transakcji = @idtransakcji) 
			IF (SELECT cOUNT(*) FROM Budzet WHERE Data = @dataPozniejsza AND Budzet.Transakcja < @idtransakcji) > 0
			BEGIN
				SET @dataPozniejszaid = (SELECT TOP 1 ID_Budzet FROM Budzet WHERE Data = @dataPozniejsza AND Budzet.Transakcja < @idtransakcji ORDER BY Budzet.Transakcja DESC)
			END
			ELSE
			BEGIN
				SET @dataPozniejszaid = (SELECT MAX(ID_Budzet) FROM Budzet)
			END
			UPDATE Transakcje SET Data = @data WHERE ID_Transakcji = @idtransakcji
			/*SELECT * INTO #budzetKopia FROM (SELECT ID_Budzet FROM Budzet WHERE Data < @dataPozniejsza AND Data > @dataWczesniej) AS KOPIA
			WHILE (SELECT COUNT(*) FROM #budzetKopia) > 0
			BEGIN 
				UPDATE Budzet SET Saldo = Saldo + @kwota WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia)
				DELETE FROM #budzetKopia WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia)
			END
			DROP TABLE #budzetKopia*/
			UPDATE Budzet SET Saldo = Saldo + @kwota WHERE DATA <= @dataPozniejsza AND ID_Budzet <= @dataPozniejszaid AND Data >= @dataWczesniej AND ID_Budzet >= @dataWczesniejid
			UPDATE Budzet SET Saldo = (SELECT SUM(Kwota) FROM Transakcje WHERE Data < @data AND ID_Transakcji <= @dataPozniejszaid) + @kwota WHERE Budzet.Transakcja = @idtransakcji
		END
		ELSE																						--podroz do przyszlosci
		BEGIN
			SET @dataWczesniej = (SELECT Data FROM Transakcje WHERE ID_Transakcji = @idtransakcji) 
			IF (SELECT COUNT(*) FROM Budzet WHERE Data = @dataWczesniej AND Budzet.Transakcja > @idtransakcji) > 0 
			BEGIN
				SET @dataWczesniejid = (SELECT TOP 1 ID_Budzet FROM Budzet WHERE Data = @dataWczesniej AND Budzet.Transakcja > @idtransakcji ORDER BY Budzet.Transakcja DESC)
			END
			ELSE
			BEGIN
				SET @dataWczesniejid = (SELECT MAX(ID_Budzet) FROM Budzet)
			END
			SET @dataPozniejsza = @data
			IF (SELECT cOUNT(*) FROM Budzet WHERE Data = @dataPozniejsza AND Budzet.Transakcja < @idtransakcji) > 0
			BEGIN
				SET @dataPozniejszaid = (SELECT TOP 1 ID_Budzet FROM Budzet WHERE Data = @dataPozniejsza AND Budzet.Transakcja < @idtransakcji ORDER BY Budzet.Transakcja DESC)
			END
			ELSE
			BEGIN
				SET @dataPozniejszaid = (SELECT MAX(ID_Budzet) FROM Budzet)
			END
			UPDATE Transakcje SET Data = @data WHERE ID_Transakcji = @idtransakcji
			/*SELECT * INTO #budzetKopia2 FROM (SELECT ID_Budzet FROM Budzet WHERE Data < @dataPozniejsza AND Data > @dataWczesniej) AS KOPIA
			WHILE (SELECT COUNT(*) FROM #budzetKopia2) > 0
			BEGIN 
				UPDATE Budzet SET Saldo = Saldo - @kwota WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia2)
				DELETE FROM #budzetKopia2 WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia2)
			END
			DROP TABLE #budzetKopia2
			*/
			UPDATE Budzet SET Saldo = Saldo - @kwota WHERE DATA <= @dataPozniejsza AND ID_Budzet <= @dataPozniejszaid AND Data >= @dataWczesniej AND ID_Budzet >= @dataWczesniejid
			UPDATE Budzet SET Saldo = (SELECT SUM(Kwota) FROM Transakcje WHERE Data < @data AND ID_Transakcji <= @dataPozniejszaid) + @kwota WHERE Budzet.Transakcja = @idtransakcji
		END
	END 
	ELSE
	BEGIN
		IF (SELECT TypTransakcji.NazwaTypu FROM Transakcje INNER JOIN TypTransakcji ON Transakcje.ID_TypTransakcji = TypTransakcji.ID_Typ WHERE Transakcje.ID_Transakcji = @idtransakcji) != @typTransakcji 
		BEGIN 
			IF @typTransakcji = 'dochody'				--chcemy zmienic na wydatki
			BEGIN
				UPDATE Transakcje SET ID_TypTransakcji = 1 WHERE ID_Transakcji = @idtransakcji
				UPDATE Budzet SET Saldo = Saldo - @kwota - @kwota WHERE Budzet.Transakcja = @idtransakcji
				SELECT * INTO #budzetKopia3 FROM (SELECT ID_Budzet FROM BUDZET WHERE Data > @data) AS KOPIA
				WHILE (SELECT COUNT(*) FROM #budzetKopia3) > 0
				BEGIN
					UPDATE Budzet SET Saldo = Saldo - @kwota - @kwota WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia3) 
					DELETE FROM #budzetKopia3 WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia3)
				END
				DROP TABLE #budzetKopia3 
			END
			ELSE
			BEGIN
				BEGIN 
					UPDATE Transakcje SET ID_TypTransakcji = 2 WHERE ID_Transakcji = @idtransakcji
					UPDATE Budzet SET Saldo = Saldo + @kwota + @kwota WHERE Budzet.Transakcja = @idtransakcji
					SELECT * INTO #budzetKopia4 FROM (SELECT ID_Budzet FROM BUDZET WHERE Data > @data) AS KOPIA
					WHILE (SELECT COUNT(*) FROM #budzetKopia4) > 0
					BEGIN
						UPDATE Budzet SET Saldo = Saldo + @kwota + @kwota WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia4)
						DELETE FROM #budzetKopia4 WHERE ID_Budzet = (SELECT TOP 1 ID_Budzet FROM #budzetKopia4)
					END
					DROP TABLE #budzetKopia4  
				END
			END
		END
		ELSE
		BEGIN
			IF (SELECT RodzajTransakcji.Nazwa FROM Transakcje INNER JOIN RodzajTransakcji ON Transakcje.ID_RodzajTransakcji = RodzajTransakcji.ID_Rodzaj WHERE Transakcje.ID_Transakcji = @idtransakcji) != @rodzajTransakcji  
			BEGIN
				DECLARE @rodzajStary nchar(20)
				SET @rodzajStary = (SELECT RodzajTransakcji.Nazwa FROM Transakcje INNER JOIN RodzajTransakcji ON Transakcje.ID_RodzajTransakcji = RodzajTransakcji.ID_Rodzaj WHERE Transakcje.ID_Transakcji = @idtransakcji)
				IF (SELECT COUNT(*) FROM RodzajTransakcji WHERE Nazwa = @rodzajTransakcji) = 0
				BEGIN
					INSERT INTO RodzajTransakcji VALUES (@rodzajTransakcji) 
				END  
				UPDATE Transakcje SET ID_RodzajTransakcji = (SELECT ID_Rodzaj FROM RodzajTransakcji WHERE Nazwa = @rodzajTransakcji) WHERE ID_Transakcji = @idtransakcji
				IF (SELECT COUNT(*) FROM Transakcje WHERE ID_RodzajTransakcji = (SELECT ID_Rodzaj FROM RodzajTransakcji WHERE Nazwa = @rodzajStary)) = 0 
				BEGIN
					DELETE FROM RodzajTransakcji WHERE Nazwa = @rodzajStary
				END 
			END
			ELSE
			BEGIN
				IF (SELECT Artykul.NazwaArtykulu FROM Transakcje INNER JOIN Artykul ON Transakcje.ID_Artykul = Artykul.ID_Artykulu WHERE Transakcje.ID_Transakcji = @idtransakcji) != @artykuly  
				BEGIN
					DECLARE @artykulStary nchar(20)
					SET @artykulStary = (SELECT Artykul.NazwaArtykulu FROM Transakcje INNER JOIN Artykul ON Transakcje.ID_Artykul = Artykul.ID_Artykulu WHERE Transakcje.ID_Transakcji = @idtransakcji)
					IF (SELECT COUNT(*) FROM Artykul WHERE NazwaArtykulu = @artykuly) = 0
					BEGIN
						INSERT INTO Artykul VALUES (@artykuly) 
					END  
					UPDATE Transakcje SET ID_Artykul = (SELECT ID_ArtykulU FROM Artykul WHERE NazwaArtykulu = @artykuly) WHERE ID_Transakcji = @idtransakcji
					IF (SELECT COUNT(*) FROM Transakcje WHERE ID_Artykul = (SELECT ID_Artykulu FROM Artykul WHERE NazwaArtykulu = @artykulStary)) = 0 
					BEGIN
						DELETE FROM Artykul WHERE NazwaArtykulu = @artykulStary
					END 
				END
				ELSE
				BEGIN
					IF (SELECT Podmiot.NazwaPodmiotu FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu WHERE Transakcje.ID_Transakcji = @idtransakcji) != @podmiot  
					BEGIN
						DECLARE @podmiotStary nchar(20)
						SET @podmiotStary = (SELECT Podmiot.NazwaPodmiotu FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu WHERE Transakcje.ID_Transakcji = @idtransakcji)
						IF (SELECT COUNT(*) FROM Podmiot WHERE NazwaPodmiotu = @podmiot) = 0
						BEGIN
							INSERT INTO Podmiot VALUES (@podmiot , (SELECT ID_Lokalizacja FROM Podmiot WHERE NazwaPodmiotu = @podmiotStary)) 
						END   
						UPDATE Transakcje SET ID_Podmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot) WHERE ID_Transakcji =@idtransakcji
						IF (SELECT COUNT(*) FROM Transakcje WHERE ID_Podmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiotStary)) = 0 
						BEGIN
							DELETE FROM Podmiot WHERE NazwaPodmiotu = @podmiotStary
						END 
					END
					ELSE
					BEGIN
						IF (SELECT Lokalizacja.Ulica FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji) != @podmiotUlica
						BEGIN
							DECLARE @PodmiotPrzemieszczany int
							DECLARE @lokalizacjaStara int
							SET @PodmiotPrzemieszczany = (SELECT Podmiot.ID_Podmiotu FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
							SET @lokalizacjaStara = (SELECT Lokalizacja.ID_Lokalizacji FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
							IF (SELECT COUNT(*) FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod) = 0
							BEGIN
								INSERT INTO Lokalizacja VALUES (@podmiotUlica , @podmiotBudynek , @podmiotMiasto , @podmiotKod) 
							END  
							UPDATE Transakcje SET ID_Podmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)) WHERE ID_Transakcji = @idtransakcji
							IF (SELECT COUNT(*) FROM Transakcje WHERE ID_Podmiot = @PodmiotPrzemieszczany) = 0 
							BEGIN
								DELETE FROM Podmiot WHERE ID_Podmiotu = @PodmiotPrzemieszczany
							END 
							IF (SELECT COUNT(*) FROM Podmiot WHERE ID_Lokalizacja = @lokalizacjaStara) = 0 
							BEGIN
								DELETE FROM Lokalizacja WHERE ID_Lokalizacji = @lokalizacjaStara
							END 
						END
						ELSE
						BEGIN
							IF (SELECT Lokalizacja.NrBudynku FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji) != @podmiotBudynek
							BEGIN
								DECLARE @PodmiotPrzemieszczany2 int
								DECLARE @lokalizacjaStara2 int
								SET @PodmiotPrzemieszczany2 = (SELECT Podmiot.ID_Podmiotu FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
								SET @lokalizacjaStara2 = (SELECT Lokalizacja.ID_Lokalizacji FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
								IF (SELECT COUNT(*) FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod) = 0								BEGIN
									INSERT INTO Lokalizacja VALUES (@podmiotUlica , @podmiotBudynek , @podmiotMiasto , @podmiotKod) 
								END  
								UPDATE Transakcje SET ID_Podmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)) WHERE ID_Transakcji = @idtransakcji
								IF (SELECT COUNT(*) FROM Transakcje WHERE ID_Podmiot = @PodmiotPrzemieszczany2) = 0 
								BEGIN
									DELETE FROM Podmiot WHERE ID_Podmiotu = @PodmiotPrzemieszczany2
								END 
								IF (SELECT COUNT(*) FROM Podmiot WHERE ID_Lokalizacja = @lokalizacjaStara2) = 0 
								BEGIN
									DELETE FROM Lokalizacja WHERE ID_Lokalizacji = @lokalizacjaStara2
								END
							END
							ELSE
							BEGIN
								IF (SELECT Lokalizacja.Miasto FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji) != @podmiotMiasto
								BEGIN
									DECLARE @PodmiotPrzemieszczany3 int
									DECLARE @lokalizacjaStara3 int
									SET @PodmiotPrzemieszczany3 = (SELECT Podmiot.ID_Podmiotu FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
									SET @lokalizacjaStara3 = (SELECT Lokalizacja.ID_Lokalizacji FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
									IF (SELECT COUNT(*) FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod) = 0								
									BEGIN
										INSERT INTO Lokalizacja VALUES (@podmiotUlica , @podmiotBudynek , @podmiotMiasto , @podmiotKod) 
									END  
									IF (SELECT COUNT(*) FROM PODMIOT WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)) = 0
									BEGIN 
										INSERT INTO Podmiot VALUES (@podmiot , (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod))
									END 
									UPDATE Transakcje SET ID_Podmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)) WHERE ID_Transakcji = @idtransakcji
									IF (SELECT COUNT(*) FROM Transakcje WHERE ID_Podmiot = @PodmiotPrzemieszczany3) = 0 
									BEGIN
										DELETE FROM Podmiot WHERE ID_Podmiotu = @PodmiotPrzemieszczany3
									END 
									IF (SELECT COUNT(*) FROM Podmiot WHERE ID_Lokalizacja = @lokalizacjaStara3) = 0 
									BEGIN
										DELETE FROM Lokalizacja WHERE ID_Lokalizacji = @lokalizacjaStara3
									END
								END
								ELSE
								BEGIN
									IF (SELECT Lokalizacja.KodPocztowy FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji) != @podmiotKod
									BEGIN
										DECLARE @PodmiotPrzemieszczany4 int
										DECLARE @lokalizacjaStara4 int
										SET @PodmiotPrzemieszczany4 = (SELECT Podmiot.ID_Podmiotu FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
										SET @lokalizacjaStara4 = (SELECT Lokalizacja.ID_Lokalizacji FROM Transakcje INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji WHERE Transakcje.ID_Transakcji = @idtransakcji)
										IF (SELECT COUNT(*) FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod) = 0								
										BEGIN
											INSERT INTO Lokalizacja VALUES (@podmiotUlica , @podmiotBudynek , @podmiotMiasto , @podmiotKod) 
										END  
										UPDATE Transakcje SET ID_Podmiot = (SELECT ID_Podmiotu FROM Podmiot WHERE NazwaPodmiotu = @podmiot AND ID_Lokalizacja = (SELECT ID_Lokalizacji FROM Lokalizacja WHERE Ulica = @podmiotUlica AND NrBudynku = @podmiotBudynek AND Miasto = @podmiotMiasto AND KodPocztowy = @podmiotKod)) WHERE ID_Transakcji = @idtransakcji
										IF (SELECT COUNT(*) FROM Transakcje WHERE ID_Podmiot = @PodmiotPrzemieszczany4) = 0 
										BEGIN
											DELETE FROM Podmiot WHERE ID_Podmiotu = @PodmiotPrzemieszczany4
										END 
										IF (SELECT COUNT(*) FROM Podmiot WHERE ID_Lokalizacja = @lokalizacjaStara4) = 0 
										BEGIN
											DELETE FROM Lokalizacja WHERE ID_Lokalizacji = @lokalizacjaStara4
										END
									END
									ELSE
									BEGIN
										IF (SELECT Kwota FROM Transakcje WHERE ID_Transakcji = @idtransakcji) != @kwota
										BEGIN
											DECLARE @roznica smallmoney
											SET @roznica = @kwota - (SELECT Kwota FROM Transakcje WHERE ID_Transakcji = @idtransakcji)
											UPDATE Transakcje SET Kwota = @kwota WHERE ID_Transakcji = @idtransakcji
											UPDATE Budzet SET Saldo = Saldo + @roznica WHERE Transakcja = @idtransakcji	
											SELECT * INTO #kopiaBudzetKwota FROM (SELECT ID_Budzet FROM Budzet WHERE Data > (SELECT Data FROM Transakcje WHERE ID_Transakcji = @idtransakcji)) AS Kopia
											UPDATE Budzet SET Saldo = Saldo + @roznica WHERE Data > (SELECT Data FROM Transakcje WHERE ID_Transakcji = @idtransakcji)
										END
										ELSE 
										BEGIN
											IF (SELECT Uwagi FROM Transakcje WHERE ID_Transakcji = @idtransakcji) != @uwagi
											BEGIN
												UPDATE Transakcje SET Uwagi = @uwagi WHERE ID_Transakcji = @idtransakcji
											END
										END
									END
								END
							END
						END  
					END
				END
			END
		END
	END
END
--------------------------------------------------------------------------------------------------------------------------------------
SELECT * INTO #Kopiakompleksowa FROM (SELECT Transakcje.ID_Transakcji , Transakcje.Data , TypTransakcji.NazwaTypu , RodzajTransakcji.Nazwa , Artykul.NazwaArtykulu , 
Podmiot.NazwaPodmiotu , Lokalizacja.Ulica ,Lokalizacja.NrBudynku , Lokalizacja.Miasto , Lokalizacja.KodPocztowy , 
Transakcje.Kwota , Transakcje.Uwagi , Budzet.Saldo FROM Budzet 
					INNER JOIN Transakcje ON Budzet.Transakcja = Transakcje.ID_Transakcji 
					INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu
					INNER JOIN Artykul ON Transakcje.ID_Artykul = Artykul.ID_Artykulu
					INNER JOIN TypTransakcji ON Transakcje.ID_TypTransakcji = TypTransakcji.ID_Typ
					INNER JOIN RodzajTransakcji ON Transakcje.ID_RodzajTransakcji = RodzajTransakcji.ID_Rodzaj
					INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji /*ORDER BY Transakcje.Data*/) AS KOPIA
--------------------------------------------------------------------------------------------------------------------------------------
exec modyfikowanieRachunku 1 , '2016-12-24' , 'wydatki' , 'zywnosc' , 'woda' , 'Biedronka' , 'przyjaciol dzieci' , '4b' , 'klodZko' , '57-300' , -19.99 , 'brak'
--------------------------------------------------------------------------------------------------------------------------------------
SELECT Transakcje.ID_Transakcji , Transakcje.Data , TypTransakcji.NazwaTypu , RodzajTransakcji.Nazwa , Artykul.NazwaArtykulu , 
Podmiot.NazwaPodmiotu , Lokalizacja.Ulica ,Lokalizacja.NrBudynku , Lokalizacja.Miasto , Lokalizacja.KodPocztowy , 
Transakcje.Kwota , Transakcje.Uwagi , Budzet.Saldo FROM Budzet 
					INNER JOIN Transakcje ON Budzet.Transakcja = Transakcje.ID_Transakcji 
					INNER JOIN Podmiot ON Transakcje.ID_Podmiot = Podmiot.ID_Podmiotu
					INNER JOIN Artykul ON Transakcje.ID_Artykul = Artykul.ID_Artykulu
					INNER JOIN TypTransakcji ON Transakcje.ID_TypTransakcji = TypTransakcji.ID_Typ
					INNER JOIN RodzajTransakcji ON Transakcje.ID_RodzajTransakcji = RodzajTransakcji.ID_Rodzaj
					INNER JOIN Lokalizacja ON Podmiot.ID_Lokalizacja = Lokalizacja.ID_Lokalizacji ORDER BY Transakcje.Data
--------------------------------------------------------------------------------------------------------------------------------------
SELECT * FROM #Kopiakompleksowa order by Data
--------------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE modyfikowanieRachunku
select * from Transakcje  inner join Artykul on Transakcje.ID_Artykul = Artykul.ID_Artykulu where  ID_Transakcji = 1
SELECT * FROM Budzet WHERE Transakcja < NULL
DROP TABLE #Kopiakompleksowa