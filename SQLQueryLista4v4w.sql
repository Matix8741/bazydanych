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

exec dodawanieRachunku '2017-01-01' , 'wydatki' , 'zakupy' , 'pomidor' , 'lidl' , 'slowackiego' , '51' , 'wroclaw' , '10-100' , -1 , 'brak'

--DROP PROCEDURE dodawanieRachunku
--------------------------------------------------------------------------------------------------------------------------------------

SELECT * FROM Budzet
SELECT * FROM Transakcje
SELECT * FROM Podmiot
SELECT * FROM Transakcje.dbo.Artykul
SELECT * FROM TypTransakcji
SELECT * FROM RodzajTransakcji
SELECT * FROM Lokalizacja

insert into Artykul (NazwaArtykulu)
values ('Dzem');

EXEC Transakcje.dbo.dodawanieRachunku '2016-12-22' , 'wydatki' , 'transport' , 'bilet' , 'pks' , 'slezna' , '18' , 'wroclaw' , '10-100' , -12 , 'brak'