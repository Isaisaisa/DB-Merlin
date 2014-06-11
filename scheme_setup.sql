

/*############################################################################*/ 

/* Löschen von Tabellen */

DROP TABLE kommtVor;
DROP TABLE beobachtet;
DROP TABLE Beobachtunsgebiet;
DROP TABLE Birdwatcher;
DROP TABLE Vogelart;
DROP SEQUENCE va_id_sequence;
DROP SEQUENCE ort_id_sequence;
DROP SEQUENCE bw_id_sequence;


 
/* Implementierung des Merlin-Schemata */
CREATE TABLE Vogelart 
       (Va_ID         INTEGER PRIMARY KEY, 
        Artentyp      VARCHAR(50) NOT NULL,
        Name_DE       VARCHAR(150), 
        Name_ENG      VARCHAR(150), 
        Name_LAT      VARCHAR(150) NOT NULL);
         
CREATE TABLE Birdwatcher 
       (Bw_ID         INTEGER PRIMARY KEY, 
        Name          VARCHAR(150) NOT NULL, 
        Vorname       VARCHAR(150) NOT NULL,
        Benutzername  VARCHAR(150) UNIQUE NOT NULL,
        Passwort      VARCHAR(150) NOT NULL,
        Email         VARCHAR(255) UNIQUE NOT NULL,
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
        Bemerkung     VARCHAR(200),
        PRIMARY KEY   (Va_ID, Bw_ID, Ort_ID, DatumVon));

        
CREATE TABLE kommtVor 
       (Va_ID         INTEGER REFERENCES Vogelart ON DELETE CASCADE, 
        Ort_ID        INTEGER REFERENCES Beobachtunsgebiet ON DELETE CASCADE,
        PRIMARY KEY   (Va_ID, Ort_ID));


/* Sequenz und Trigger für Beobachtungsgebiete */
CREATE SEQUENCE ort_id_sequence
  START WITH 1
  INCREMENT BY 1;
  
  
CREATE OR REPLACE TRIGGER ort_id_sequence 
BEFORE INSERT ON Beobachtunsgebiet
FOR EACH ROW
BEGIN
  SELECT ort_id_sequence.nextval into :new.Ort_ID from dual;
END;
/

/* Sequenz und Trigger für Birdwatcher */        
CREATE SEQUENCE bw_id_sequence
  START WITH 1
  INCREMENT BY 1;
  
  
CREATE OR REPLACE TRIGGER bw_id_trigger 
BEFORE INSERT ON Birdwatcher
FOR EACH ROW
BEGIN
  SELECT bw_id_sequence.nextval into :new.Bw_ID from dual;
END;
/

/*############################################################################*/ 

 /* Standart-Birdwatcher erstellen: */

/*SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = 'demo' and Passwort = 'merlin';*/

INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle)
  VALUES ('Byteschubser', 'Armin', 'admin', 'cheffe', 'admin@merlin.de', 'R01');
  
INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle)
  VALUES ('Inhalt', 'Carmen', 'cadmin', 'vize', 'content_admin@merlin.de', 'R02');
  
INSERT INTO Birdwatcher (Name, Vorname, Benutzername, Passwort, Email, Rolle)
  VALUES ('Watcher', 'Birdy', 'demo', 'merlin', 'demo@merlin.de', 'R03');


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
  
DELETE Vogelart
WHERE Artentyp LIKE 'group%';

/* Sequenz und Trigger für Vogelarten */
CREATE SEQUENCE va_id_sequence
  START WITH 40000
  INCREMENT BY 1;
  
  
CREATE OR REPLACE TRIGGER va_id_sequence 
BEFORE INSERT ON Vogelart
FOR EACH ROW
BEGIN
  SELECT va_id_sequence.nextval into :new.Va_ID from dual;
END;
/
  
/* dt. Beobachtungsgebiete eintragen */

INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', null, null);  
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', null); /* 2 */
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Hamburg'); /* 3 */
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Schleswig-Holstein');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Bremen');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Mecklenburg-Vorpommern');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Niedersachsen');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Berlin');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Brandenburg');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Sachsen-Anhalt');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Nordrhein-Westfalen');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Hessen');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Thüringen');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Sachsen');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Rheinland-Pfalz');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Saarland');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Baden-Württemberg');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('WPA', 'GER', 'Bayern');
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('AAA', null, null);
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('AFR', null, null);
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('AUS', null, null);
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('IND', null, null);
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('NEA', null, null);
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('NEO', null, null);
INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values ('OPA', null, null); 


/* wie wird ein Statement formuliert, mit dem man Anhand Level 3 auf Level 2 schließen kann?
 * Sprich, Man hat die ID, bswp. 3, und möchte den dazugehörigen Level 2 ermitteln und anhand dessen, alle zu Level 2 zugehörigen Level-3-Gebiete ausgeben
 * 
 * Beispielfall: Man macht eine Beobachtung in Hamburg (Deutschland), entspricht das einem Tupel beobachtet(va_id, bw_id, 3, ...)
 * Will man nun wissen, welche Vögel man in Deutschland gesehen hat, müsste ebenfalls diese Eintragung ausgegeben werden,
 * da alle Level-3-Gebiete Teilmenge von Level 2 sind.
 * Es müssten demnach für alle Level-3 Gebiete, die zu Level-2 passen (ORT_ID = 2 (Deutschland) => Beobachtungsgebiet.Level_2 = 'GER'),
 * die passenden Einträge selektiert werden.
 */ 

  
/* Checklist für Deutschland (ID = 2) */

INSERT INTO kommtVor 
  (Va_ID, Ort_ID)
  (SELECT v.Va_ID, b.Ort_ID 
  FROM Vogelart v, MERLIN.BIRDS_DE bd, Beobachtunsgebiet b 
  WHERE v.NAME_LAT = bd.DE_LATEIN AND b.Ort_ID = 2);
	
/*############################################################################*/

/* Teil 1: Beobachtung für den deutschen Raum anlegen */  	

/* Beispiel-Beobachtungen des Demo-Nutzers in seine Beobachtungsliste (pers. Checkliste) eintragen */
INSERT INTO beobachtet
  VALUES (284, 3, 2, TO_DATE('08-MAI-2014 11:02', 'DD-MONTH-YYYY HH24:MI'), TO_DATE('09-MAI-2014 14:15', 'DD-MONTH-YYYY HH24:MI'), '2 m. 5 w. 3 juv.');
	
INSERT INTO beobachtet
  VALUES (300, 3, 3, TO_DATE('28-MAI-2014 21:02', 'DD-MONTH-YYYY HH24:MI'), null, '...');
	
INSERT INTO beobachtet
  VALUES (397, 3, 2, TO_DATE('14-MAI-2014 13:14', 'DD-MONTH-YYYY HH24:MI'), null, 'Stürmisches Wetter');
	
INSERT INTO beobachtet
  VALUES (256, 3, 2, TO_DATE('08-MAI-2014 11:27', 'DD-MONTH-YYYY HH24:MI'), null, '3 m. 5 w.');
	
INSERT INTO beobachtet
  VALUES (284, 3, 2, TO_DATE('08-MAI-2014 11:03', 'DD-MONTH-YYYY HH24:MI'), null, '2 m. im Kampf');
    