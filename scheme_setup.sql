
/*Autoren: Tim Hartig (MatrNr: 2171296), Louisa Spahl (MatrNr: 2170984) */

/*
Relationenmodell

Vogelart: {[Va_ID: integer,  Artentyp: varchar, Name_DE: varchar, Name_ENG: varchar, Name_LAT: varchar]}
Birdwatcher: {[Bw_ID: integer, Name: varchar, Vorname: varchar ,Email: varchar]}																							NEEDS UPDATE
Beobachtungsgebiet: {[Ort_ID: integer, Level_1: varchar, Level_2: varchar ,Level_3: varchar]}
beobachtet: {[Va_ID: integer, Bw_ID: integer, Ort_ID: integer, DatumVon: date, DatumBis: date, Bemerkung: varchar]}
kommtVor: {[Va_ID: integer, Ort_ID: integer]}
*/

/*############################################################################*/ 

/* L�schen von Tabellen */

DROP TABLE kommtVor;
DROP TABLE beobachtet;
DROP TABLE Beobachtunsgebiet;
DROP TABLE Birdwatcher;
DROP TABLE Vogelart;

/* Implementierung des Merlin-Schemata */
CREATE TABLE Vogelart 

       (Va_ID         INTEGER PRIMARY KEY, 
        Artentyp		  VARCHAR(50) NOT NULL,
        Name_DE       VARCHAR(150), 
        Name_ENG      VARCHAR(150), 
        Name_LAT      VARCHAR(150) NOT NULL);
         
CREATE TABLE Birdwatcher 
       (Bw_ID         INTEGER PRIMARY KEY, 
        Name          VARCHAR(150) NOT NULL, 
        Vorname       VARCHAR(150) NOT NULL,
				Benutzername  VARCHAR(150) NOT NULL,
				Passwort			VARCHAR(150) NOT NULL,
        Email         VARCHAR(255) NOT NULL,
				Rolle         VARCHAR(150) NOT NULL);
        
CREATE TABLE Beobachtunsgebiet 
       (Ort_ID        INTEGER PRIMARY KEY, 
        LEVEL_1       VARCHAR(3) NOT NULL, 
        LEVEL_2       VARCHAR(3), 
        LEVEL_3       VARCHAR(150));
        
CREATE TABLE beobachtet   
       (Va_ID         INTEGER REFERENCES Vogelart ON DELETE CASCADE, 
        Bw_ID         INTEGER REFERENCES Birdwatcher ON DELETE CASCADE,
        Ort_ID        INTEGER REFERENCES Beobachtunsgebiet ON DELETE CASCADE,
        DatumVon      DATE NOT NULL, 
        DatumBis      DATE,
        Bemerkung     VARCHAR(200))/*,
        PRIMARY KEY   (Va_ID, Bw_ID, Ort_ID, DatumVon, DatumBis));*/;
				/* Prim�rschl�ssel vorr�bergehend entfernt. Keine null-Werte in einem Attribut erlaubt, das ein Teilschl�ssel darstellt.
				 * Evtl. Modellierungsfehler?
				 */
        
CREATE TABLE kommtVor 
       (Va_ID         INTEGER REFERENCES Vogelart ON DELETE CASCADE, 
        Ort_ID        INTEGER REFERENCES Beobachtunsgebiet ON DELETE CASCADE,
        PRIMARY KEY   (Va_ID, Ort_ID));

/*############################################################################*/ 

/* Import aller Stammdaten:

externen Attribute      eigene Attribute
B_ID               ==   ID,
B_CATEGORY         ==   Artentyp,	
B_SCIENTIFIC_NAME  ==   Name_LAT, 
B_ENGLISH_NAME     ==   Name_ENG,
DE_DEUTSCH         ==   Name_DE.
*/

INSERT INTO Vogelart
  (Va_ID, Artentyp, Name_DE, Name_ENG, Name_LAT)
  (SELECT b.B_ID, b.B_CATEGORY, bde.IOC_GERMAN_NAME, b.B_ENGLISH_NAME, b.B_SCIENTIFIC_NAME
  FROM MERLIN.BIRDS b LEFT JOIN MERLIN.BIRDS_IOC bde ON b.B_SCIENTIFIC_NAME = bde.IOC_SCIENTIFIC_NAME);
  
  
/* dt. Beobachtungsgebiete eintragen */
	
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (1,  'WP', null, null);  
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (2,  'WP', 'GER', null);
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (3,  'WP', 'GER', 'Hamburg');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (4,  'WP', 'GER', 'Schleswig-Holstein');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (5,  'WP', 'GER', 'Bremen');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (6,  'WP', 'GER', 'Mecklenburg-Vorpommern');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (7,  'WP', 'GER', 'Niedersachsen');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (8,  'WP', 'GER', 'Berlin');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (9,  'WP', 'GER', 'Brandenburg');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (10, 'WP', 'GER', 'Sachsen-Anhalt');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (11, 'WP', 'GER', 'Nordrhein-Westfalen');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (12, 'WP', 'GER', 'Hessen');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (13, 'WP', 'GER', 'Th�ringen');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (14, 'WP', 'GER', 'Sachsen');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (15, 'WP', 'GER', 'Rheinland-Pfalz');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (16, 'WP', 'GER', 'Saarland');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (17, 'WP', 'GER', 'Baden-W�rttemberg');
INSERT INTO Beobachtunsgebiet (Ort_ID, Level_1, Level_2, Level_3) Values (18, 'WP', 'GER', 'Bayern');


/* wie wird ein Statement formuliert, mit dem man Anhand Level 3 auf Level 2 schlie�en kann?
 * Sprich, Man hat die ID, bswp. 3, und m�chte den dazugeh�rigen Level 2 ermitteln und anhand dessen, alle zu Level 2 zugeh�rigen Level-3-Gebiete ausgeben
 * 
 * Beispielfall: Man macht eine Beobachtung in Hamburg (Deutschland), entspricht das einem Tupel beobachtet(va_id, bw_id, 3, ...)
 * Will man nun wissen, welche V�gel man in Deutschland gesehen hat, m�sste ebenfalls diese Eintragung ausgegeben werden,
 * da alle Level-3-Gebiete Teilmenge von Level 2 sind.
 * Es m�ssten demnach f�r alle Level-3 Gebiete, die zu Level-2 passen (ORT_ID = 2 (Deutschland) => Beobachtungsgebiet.Level_2 = 'GER'),
 * die passenden Eintr�ge selektiert werden.
 */ 

  
/* Checklist f�r Deutschland (ID = 2) */

INSERT INTO kommtVor 
  (Va_ID, Ort_ID)
  (SELECT v.Va_ID, b.Ort_ID 
  FROM Vogelart v, MERLIN.BIRDS_DE bd, Beobachtunsgebiet b 
  WHERE v.Name_DE = bd.DE_DEUTSCH AND b.Ort_ID = 2);
	
/*############################################################################*/

/* Teil 1: Beobachtung f�r den deutschen Raum anlegen */  
 
 /* Standart-Birdwatcher erstellen: */
 
 INSERT INTO Birdwatcher
    Values (1, 'Byteschubser', 'Armin', 'admin', 'ichchefdunix', 'admin@merlin.de', 'R01');

 INSERT INTO Birdwatcher
    Values (2, 'Inhalt', 'Carmen', 'cadmin', 'ichauchchef', 'content_admin@merlin.de', 'R02');

 INSERT INTO Birdwatcher
    Values (3, 'Watcher', 'Birdy', 'demo', 'merlindemo', 'demo@merlin.de', 'R03');

/*############################################################################*/		

/* Beispiel-Beobachtungen des Demo-Nutzers in seine Beobachtungsliste (pers. Checkliste) eintragen */

INSERT INTO beobachtet
  VALUES (284, 3, 2, TO_DATE('08-MAI-2014 11:02', 'DD-MONTH-YYYY HH24:MI'), TO_DATE('09-MAI-2014 14:15', 'DD-MONTH-YYYY HH24:MI'), '2 m. 5 w. 3 juv.');
	
INSERT INTO beobachtet
  VALUES (300, 3, 3, TO_DATE('28-MAI-2014 21:02', 'DD-MONTH-YYYY HH24:MI'), null, '...');
	
INSERT INTO beobachtet
  VALUES (397, 3, 2, TO_DATE('14-MAI-2014 13:14', 'DD-MONTH-YYYY HH24:MI'), null, 'St�rmisches Wetter');
	
INSERT INTO beobachtet
  VALUES (256, 3, 2, TO_DATE('08-MAI-2014 11:27', 'DD-MONTH-YYYY HH24:MI'), null, '3 m. 5 w.');
	
INSERT INTO beobachtet
  VALUES (284, 3, 2, TO_DATE('08-MAI-2014 11:02', 'DD-MONTH-YYYY HH24:MI'), null, '2 m. im Kampf');
	
	
	