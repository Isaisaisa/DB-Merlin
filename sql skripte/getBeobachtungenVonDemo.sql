/* Alle Beobachtungen von Benutzer 'demo' ermitteln und
 * den zur Vogel ID zugehörigen lat. Namen
 */

/* Alle Beobachtungen von bwatcher.BW_ID */
SELECT beob.*, va.NAME_LAT
FROM (
      /* ID des Users 'demo' ermitteln */
      SELECT bw.BW_ID
      FROM BIRDWATCHER bw
      WHERE bw.BENUTZERNAME = 'demo'
      ) bwatcher,
      BEOBACHTET beob, VOGELART va
WHERE beob.BW_ID = bwatcher.BW_ID AND beob.VA_ID = va.VA_ID;