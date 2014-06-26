package merlin.data;



import static merlin.base.PreparedStatementKeyEnum.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.base.PreparedStatementKeyEnum;
import merlin.utils.ConstantElems;


//Daten holen zur Checkliste
public class SpeciesRepository {
	
	public static DefaultComboBoxModel<String> getLevel1Data() {
		try {
			DbWrapper database = Application.getInstance().database();
			DefaultComboBoxModel<String> resultModel = new DefaultComboBoxModel<String>();
			List<String> resultList = database.getListOfQuery("SELECT DISTINCT Level_1 FROM Beobachtunsgebiet");
			
			resultModel.addElement("");
			for (String str : resultList)
				resultModel.addElement(str);
			
			return resultModel;
			
		} catch (Exception e) {
			e.printStackTrace();
			DefaultComboBoxModel<String> errModel = new DefaultComboBoxModel<String>();
			errModel.addElement("Fehler");
			return errModel;
		} 
	}
	
	public static DefaultComboBoxModel<String> getLevel1Data_Checklist() {
		try {
			DbWrapper database = Application.getInstance().database();
			DefaultComboBoxModel<String> resultModel = new DefaultComboBoxModel<String>();
			List<String> resultList = database.getListOfQuery("SELECT DISTINCT Level_1 FROM Beobachtunsgebiet");
			
			for (String str : resultList)
				resultModel.addElement(str);
			
			return resultModel;
			
		} catch (Exception e) {
			e.printStackTrace();
			DefaultComboBoxModel<String> errModel = new DefaultComboBoxModel<String>();
			errModel.addElement("Fehler");
			return errModel;
		} 
	}
	
	public static void addLocation(String l1, String l2, String l3) {
		PreparedStatement psAddLoc = getPreparedStatement(ADD_LOCATION);
		
		try {
			psAddLoc.setString(1, l1);
			psAddLoc.setString(2, (l2.isEmpty())?(null):(l2));
			psAddLoc.setString(3, (l3.isEmpty())?(null):(l3));
			database().sendUpdate(psAddLoc);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Ort konnte nicht hinzugefügt werden");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Ort konnte nicht hinzugefügt werden");
		}
	}
	
	public static void addChecklistEntry(String va_id, String level_1_admin, String level_2_admin, String level_3_admin) throws Exception {
		String ort_id = getLocationId(level_1_admin, level_2_admin, level_3_admin);
		
		database().sendUpdate("INSERT INTO kommtVor VALUES("+va_id+", "+ort_id+")");
	}
	
	public static void deleteChecklistEntry(String va_id, String level_1_admin, String level_2_admin, String level_3_admin) throws Exception {
		String ort_id = getLocationId(level_1_admin, level_2_admin, level_3_admin);
		
		database().sendUpdate("DELETE kommtVor WHERE va_id = "+va_id+" AND ort_id = "+ort_id);
	}
	
	public static void updateLocation(String Ort_Id, String l1, String l2, String l3) {
		PreparedStatement psUpdateLoc = getPreparedStatement(UPDATE_LOCATION);
		
		try {
			psUpdateLoc.setString(1, l1);
			psUpdateLoc.setString(2, (l2.isEmpty())?(null):(l2));
			psUpdateLoc.setString(3, (l3.isEmpty())?(null):(l3));
			psUpdateLoc.setString(4, Ort_Id);
			database().sendUpdate(psUpdateLoc);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Ort konnte nicht aktualisiert werden");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Ort konnte nicht aktualisiert werden");
		}
	}
	
	public static DefaultTableModel getLocations() throws Exception {
		return database().getTableModelOfQuery("SELECT Ort_Id as Ort_Id, Level_1 as \"Zooökologische Region\", Level_2 as \"Land\", Level_3 as \"Lokation\" FROM Beobachtunsgebiet ORDER BY Level_1 ASC, Level_2 ASC, Level_3 ASC");
	}
	
	public static DefaultTableModel getCoreData(String filter, String spec) throws Exception {
		
		String specFilter = "WHERE (lower(Artentyp) = 'species' OR lower(Artentyp) = 'subspecies') ";
		
		if (spec == "Oberarten") {
			specFilter = "WHERE (lower(Artentyp) = 'species') ";
		} else if (spec == "Unterarten") {
			specFilter = "WHERE (lower(Artentyp) = 'subspecies') ";
		}
		System.out.println("specFilter = " + specFilter);
		
		String ordering   = "ORDER BY Name_LAT ASC";
		
		String query =    "SELECT Va_ID as \"Vogel-ID\", "
						+ "Name_LAT as \"Vogelart (lateinischer Name)\", "
						+ "Name_DE as \"(deutscher Name)\", "
						+ "Name_ENG as \"(englischer Name)\", "
						+ "Artentyp as \"Artentyp\" "
						+ "FROM Vogelart " + specFilter;
		
		if (filter.isEmpty()) {
			query += ordering;
		} else {
			String filterText = 
					  "AND (lower(Name_Lat) like lower('%" + filter + "%') OR "
					+ "lower(Name_DE)  like lower('%" + filter + "%') OR "
					+ "lower(Name_ENG) like lower('%" + filter + "%')) ";
			
			query += filterText + ordering;
		}
		
		System.out.println(query);
		
		return database().getTableModelOfQuery(query);
	}
	
	public static void addBird(String name_lat, String name_de, String name_eng, String spec) throws Exception {
		PreparedStatement psAddBird = getPreparedStatement(ADD_BIRD);
		psAddBird.setString(1, spec);
		psAddBird.setString(2, name_de);
		psAddBird.setString(3, name_eng);
		psAddBird.setString(4, name_lat);
		
		database().sendUpdate(psAddBird);
		System.out.println();
	}
	
	public static void updateBird(String va_id, String lat, String de, String eng, String spec) throws SQLException, Exception {
		PreparedStatement psUpdateBird = getPreparedStatement(UPDATE_BIRD);
		psUpdateBird.setString(1, spec);
		psUpdateBird.setString(2, lat);
		psUpdateBird.setString(3, de);
		psUpdateBird.setString(4, eng);
		psUpdateBird.setString(5, va_id);
		
		database().sendUpdate(psUpdateBird);
	}
	
	private static DbWrapper database() throws Exception {
		return Application.getInstance().database();
	}
	
	public static DefaultTableModel selectLocation(String region, String land, String area, String filter) {
		DbWrapper database;
		DefaultTableModel table = new DefaultTableModel();
		try {
			database = Application.getInstance().database();
			if ((region == null || region.equals("")) && (land == null || land.equals("")) && (area == null || area.equals(""))){
				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE lower(v.Name_lat) like '%"+filter+"%' OR lower(v.Name_de) like '%"+filter+"%' OR lower(v.Name_eng) like '%"+filter+"%' ORDER BY v.Name_Lat ASC");
				
		    }else if ((land == null || land.equals("")) && (area == null || area.equals(""))){
				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND (lower(v.Name_lat) like '%"+filter+"%' OR lower(v.Name_de) like '%"+filter+"%' OR lower(v.Name_eng) like '%"+filter+"%') ORDER BY v.Name_Lat ASC");
				
			}else if (area == null || area.equals("")){
				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND level_2 = '" +  land  + "' AND (lower(v.Name_lat) like '%"+filter+"%' OR lower(v.Name_de) like '%"+filter+"%' OR lower(v.Name_eng) like '%"+filter+"%') ORDER BY v.Name_Lat ASC");
			
			}else{
				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND level_2 = '" +  land  + "' AND level_3 = '" + area + "' AND (lower(v.Name_lat) like '%"+filter+"%' OR lower(v.Name_de) like '%"+filter+"%' OR lower(v.Name_eng) like '%"+filter+"%') ORDER BY v.Name_Lat ASC");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			ConstantElems.showMsgBox(e);
		}
		return table;
	
	}
//	public static DefaultTableModel selectLocation(String region, String land, String area, String filter) {
//		DbWrapper database;
//		DefaultTableModel table = new DefaultTableModel();
//		
//		try {
//			database = Application.getInstance().database();
//			System.out.println("37 SpeciesRepository : "+database);
//			if ((region == null || region.equals("")) && (land == null || land.equals("")) && (area == null || area.equals(""))){
//				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
//						+ "Vogelart v ON  v.va_ID = kv.va_ID");
//				
//			}else if ((land == null || land.equals("")) && (area == null || area.equals(""))){
//				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
//						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "'");
//				
//			}else if (area == null || area.equals("")){
//				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
//						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND level_2 = '" +  land  + "'");
//				
//			}else{
//				table = database.getTableModelOfQuery("SELECT DISTINCT v.va_id, v.Name_Lat as \"Vogelart (lateinischer Name)\", v.Name_DE as \"(deutscher Name)\" , v.Name_ENG as \"(englischer Name)\", v.Artentyp as \"Artentyp\" FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
//						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND level_2 = '" +  land  + "' AND level_3 = '" + area + "'");
//				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			ConstantElems.showMsgBox(e);
//		}
//		return table;
//		
//	}
	
	//Holt alle Level1 aus Beobachtung
		public static Vector<String> getRegion(){
			ResultSet rs;
			Vector<String> list = new Vector<String>();
			list.add("");
			try {
				rs = Application.getInstance().database().sendQuery("SELECT Level_1 FROM Beobachtunsgebiet WHERE Level_2 is null and Level_3 is null"); 
				System.out.println(rs);
				while (rs.next()) {
			        list.add(rs.getString(1));
			     }
			} catch (Exception e) {
				e.printStackTrace();
			}
			list = new Vector<String>(new HashSet<String>(list));
			return list;
	       
		}
		
		
		public static Vector<String> getLand(String string){
			ResultSet rs;
			Vector<String> list = new Vector<String>();
			list.add("");
			try {
				rs = Application.getInstance().database().sendQuery("SELECT Level_2 FROM Beobachtunsgebiet WHERE Level_1 = " + "'" + string + "'" + "AND Level_2 is not null AND Level_3 is null" );
				System.out.println(rs);
				while (rs.next()) {
			        	list.add(rs.getString(1));
			     }
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(list);
			list = new Vector<String>(new HashSet<String>(list));
			return list;
		}
		
		public static Vector<String> getArea(String string, String str){
			ResultSet rs;
			Vector<String> list = new Vector<String>();
			list.add("");
			try {
				rs = Application.getInstance().database().sendQuery("SELECT Level_3 FROM Beobachtunsgebiet WHERE Level_1 = " + "'" + string + "'" + "AND Level_2 = " +  "'" + str + "'" + " AND Level_3 is not null" );
				System.out.println(rs);
				while (rs.next()) {
			        	list.add(rs.getString(1));
			     }
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(list);
			list = new Vector<String>(new HashSet<String>(list));
			return list;
		}
			
		//Beobachtung eintragen in Datenbank
		public static void addDataObservation(String birdId, String level1, String level2, String level3, String dateFrom, String dateUntil, String notice) throws Exception{
//				System.out.println("120 SpeciesRepository : " + level1); // TODO syso's aufräumen
//				System.out.println("121 SpeciesRepository : " + level2);
//				System.out.println("122 SpeciesRepository : " + level3);
				String ort_id = getLocationId(level1, level2, level3);
//				System.out.println("215 SpeciesRepository#addDataObservation : " +  ort_id);
				String bw_id = BirdwatcherRepository.getActiveUser().id();
//				System.out.println("127 SpeciesRepository#addDataObservation : " +  bw_id);
			
			
			DbWrapper database;
			try {
				database = Application.getInstance().database();
				if (dateUntil == null) {

//					String tmp ="INSERT INTO beobachtet (Va_ID, Bw_ID, Ort_ID, DatumVon, DatumBis, Bemerkung)"
//					+ "	VALUES( '" + birdId + "', '" + bw_id + "', '" + ort_id + "', TO_DATE('" + dateFrom + "', 'DD-MM-YYYY HH24:MI'), null, '" + notice + "')";
//					System.out.println("136 SpeciesRepository#addDataObservation : "+tmp);
//					database.sendUpdate(tmp);
					
					
					PreparedStatement psObservation = getPreparedStatement(ADD_OBSERVATION_MOMENT);
					psObservation.setString(1, birdId);
					psObservation.setString(2, bw_id);
					psObservation.setString(3, ort_id);
					psObservation.setString(4, dateFrom);
					psObservation.setString(5, notice);
					
					database.sendUpdate(psObservation);
					
				} else {	
//					String tmp = "INSERT INTO beobachtet (Va_ID, Bw_ID, Ort_ID, DatumVon, DatumBis, Bemerkung)"
//							+ "	VALUES ( '" + birdId + "', '" + bw_id + "', '" + ort_id + "', TO_DATE('" + dateFrom + "', 'DD-MM-YYYY HH24:MI'), TO_DATE('" + dateUntil + "', 'DD-MM-YYYY HH24:MI'), '" + notice + "')";
//					System.out.println("141 SpeciesRepository#addDataObservation : " + tmp);
//					database.sendUpdate(tmp);
					
					
					PreparedStatement psObservation = getPreparedStatement(ADD_OBSERVATION_PERIOD);
					psObservation.setString(1, birdId);
					psObservation.setString(2, bw_id);
					psObservation.setString(3, ort_id);
					psObservation.setString(4, dateFrom);
					psObservation.setString(5, dateUntil);
					psObservation.setString(6, notice);
					
//					psObservation.setString(1, birdId);
//					psObservation.setString(2, bw_id);
//					psObservation.setString(3, ort_id);
//					psObservation.setString(4, dateFrom);
//					psObservation.setString(5, SQL_DATE_FORMAT);
//					psObservation.setString(6, dateUntil);
//					psObservation.setString(7, SQL_DATE_FORMAT);
//					psObservation.setString(8, notice);
					
					database.sendUpdate(psObservation);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				ConstantElems.showMsgBox(e);
			}
			
			
		}
			
		// Beobachtung löschen aus Datenbank
		public static void deleteDataObservation(String beoId){
//			System.out.println("SpeciesRepository#deleteDataObservasion : " + beoId);
			DbWrapper database;
			try{
				database = Application.getInstance().database();	
				String tmp = "DELETE beobachtet WHERE Beo_id = '" + beoId + "'";
					database.sendUpdate(tmp);
			}catch (Exception e) {
				e.printStackTrace();
				ConstantElems.showMsgBox(e);
			}
		}
		
		public static String getLocationId(String l1, String l2, String l3) throws Exception {
			String query = "SELECT Ort_Id FROM Beobachtunsgebiet WHERE level_1 = '%L1%' and level_2 %L2% and level_3 %L3%";
			if (l1 == null || l1.isEmpty()) {
				throw new Exception("Keine zooökologische Region gewählt!");
			} else {
				query = query.replace("%L1%", l1);
			}
			
			if (l2 == null || l2.isEmpty()) {
				query = query.replace("%L2%", "is null");
			} else {
				query = query.replace("%L2%", "= '" + l2 + "'");
			}
			
			if (l3 == null || l3.isEmpty()) {
				query = query.replace("%L3%", "is null");
			} else {
				query = query.replace("%L3%", "= '" + l3 + "'");
			}
			
			System.out.println("getLocationId() query: " + query);
			String result = Application.getInstance().database().getSingleValue(query);
			System.out.println("Location Id returned: " + result);
			return result;
			
		}
		
//		Methode um "beobachtet" in Gui zu laden
		public static DefaultTableModel getDataObservation() throws Exception{
			return Application.getInstance().database().getTableModelOfQuery(
					" SELECT b.beo_id, v.Name_Lat, v.Name_De, v.Name_Eng,b.Ort_Id, b.DatumVon, b.DatumBis, b.Bemerkung "
					+ "FROM  beobachtet b, Vogelart v WHERE b.bw_id = '" + BirdwatcherRepository.getActiveUser().id() + 
					"' AND b.va_Id = v.va_id ORDER BY DatumVon ASC");
		}
		
		private static Hashtable<PreparedStatementKeyEnum, PreparedStatement> preparedStatements = new Hashtable<PreparedStatementKeyEnum, PreparedStatement>();
		private static boolean isPreparedStatementsHashInitialized = false;
		private static String SQL_DATE_FORMAT = "DD-MM-YYYY HH24:MI";
		
		public static PreparedStatement getPreparedStatement(PreparedStatementKeyEnum key) {
			if (!isPreparedStatementsHashInitialized) { // Initialisierung der Prepared Statements sicherstellen
				prepareStatements();
				isPreparedStatementsHashInitialized = true;
			}
			return preparedStatements.get(key);
		}

		private static void prepareStatements() {
			System.out.println("Preparing statements...");
			DbWrapper database;
			try {
				database = Application.getInstance().database();
				
				preparedStatements.put(REGISTER_BIRDWATCHER, 
						database.prepareStatement("INSERT INTO Birdwatcher "
								+ "(Name, Vorname, Benutzername, Passwort, Email, Rolle) "
								+ "VALUES (?, ?, ?, ?, ?, ?)"
						)
				);
				
				
				
				preparedStatements.put(GET_BW_ID, 
						database.prepareStatement("SELECT Bw_ID FROM Birdwatcher WHERE Benutzername = ?")
				);
				
				preparedStatements.put(LOGIN_TO_MERLIN, 
						database.prepareStatement("SELECT Bw_ID, Name, Vorname, Benutzername, Passwort, Email, Rolle FROM Birdwatcher WHERE Benutzername = ? AND Passwort = ?")
				);
				
				preparedStatements.put(GET_USER_ROLE, 
						database.prepareStatement("SELECT Rolle FROM Birdwatcher WHERE Benutzername = ?")
				);
				
				preparedStatements.put(ADD_OBSERVATION_MOMENT, 
						database.prepareStatement("INSERT INTO beobachtet (Va_ID, Bw_ID, Ort_ID, DatumVon, DatumBis, Bemerkung) "
								+ "VALUES (?, ?, ?, TO_DATE(?, '"+SQL_DATE_FORMAT+"'), null, ?)")
				);
				
				preparedStatements.put(ADD_OBSERVATION_PERIOD, 
						database.prepareStatement("INSERT INTO beobachtet (Va_ID, Bw_ID, Ort_ID, DatumVon, DatumBis, Bemerkung) "
								+ "VALUES (?, ?, ?, TO_DATE(?, '"+SQL_DATE_FORMAT+"'), TO_DATE(?, '"+SQL_DATE_FORMAT+"'), ?)")
				);
				
				preparedStatements.put(ADD_LOCATION,
						database.prepareStatement("INSERT INTO Beobachtunsgebiet (Level_1, Level_2, Level_3) Values (?, ?, ?)")
				);
				
				preparedStatements.put(ADD_BIRD,
						database.prepareStatement("INSERT INTO Vogelart (Artentyp, Name_DE, Name_ENG, Name_LAT) Values (?, ?, ?, ?)")
				);
				
				preparedStatements.put(UPDATE_BIRD,
						database.prepareStatement("UPDATE Vogelart SET Artentyp = ?, Name_LAT = ?, NAME_DE = ?, NAME_ENG = ? WHERE Va_ID = ?")
				);
				
				preparedStatements.put(UPDATE_LOCATION,
						database.prepareStatement("UPDATE BEOBACHTUNSGEBIET SET LEVEL_1 = ?, LEVEL_2 = ?, LEVEL_3 = ? WHERE ORT_ID = ?")
				);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
			// Lifer / Ticks anzeigen, Volltextsuche
			public static DefaultTableModel showLiferTicks(String level1, String level2, String level3, String filter, boolean showTicks, boolean showLifer){
				DbWrapper database;
				String query;
				DefaultTableModel table = new DefaultTableModel();
				try{
					database = Application.getInstance().database();
					String ort_id = "SELECT Ort_ID FROM BEOBACHTUNSGEBIET";
					if(!(level1 == null) && !level1.equals("")){
						ort_id += " WHERE Level_1 = '" + level1 +"'";
						if (!(level2 == null) && !level2.equals("")){
							ort_id += " AND Level_2 = '" +  level2 + "'";
							if(!(level3 == null) && !level3.equals("")){
								ort_id += " AND Level_3 = '" +  level3 + "'";
							}
						}
					}
					query = "SELECT beo_id, DatumVon as \"Datum (vom)\", DatumBis as \"Datum (bis)\", Name_Lat as \"Vogelart (lat. Name)\", Name_De as \"(deutscher Name)\", Name_Eng as \"(englischer Name)\", Bemerkung as \"Bemerkung\", \"Lifer/Tick\" as \"Lifer / Tick\" FROM "
							+ "(SELECT b.beo_id, v.Name_Lat, v.Name_De, v.Name_Eng,b.Ort_Id, b.DatumVon, b.DatumBis, b.Bemerkung,"
							     + " CASE WHEN l.Lifer = b.datumVon  THEN 'Lifer'"
							     + "     WHEN t.Tick = b.datumVon   THEN 'Tick'"
							     + " END as \"Lifer/Tick\" "
							+ "FROM  beobachtet b, Vogelart v,"
							     + " (SELECT beo.va_id, MIN(beo.datumVon) AS Lifer FROM Beobachtet beo GROUP BY beo.va_id) l,"
							     + " (SELECT beob.va_id, MIN(beob.datumVon) AS Tick FROM Beobachtet beob WHERE beob.ort_id IN"
							          + " (" + ort_id + ") GROUP BY beob.va_id) t"
							     + " WHERE b.bw_id = " + BirdwatcherRepository.getActiveUser().id()  + " AND b.va_Id = v.va_id AND b.Ort_id IN ("
							        + "" + ort_id + ") AND l.va_id = b.va_id AND t.va_id = b.va_id ORDER BY DatumVon ASC)"
							+ " WHERE (lower(Name_Lat) LIKE '%" + filter.toLowerCase()  + "%' OR lower(Name_DE) LIKE '%" + filter.toLowerCase()  + "%' OR lower(Name_ENG) LIKE '%" + filter.toLowerCase()  + "%')";
					
					if (showTicks || showLifer){
						query += " AND";
						if (!showTicks && showLifer){
							query += " \"Lifer/Tick\" = 'Lifer'";
						}else if (showTicks && !showLifer){
							query += " \"Lifer/Tick\" = 'Tick'";
						}else if (showTicks && showLifer){
							query += " (\"Lifer/Tick\" = 'Lifer' OR \"Lifer/Tick\" = 'Tick')";
						}
						
					}	
					 System.out.println("SpeciesRepository#showLiferTicks : " + query);
					 table = database.getTableModelOfQuery(query);	
					 
					}catch(Exception e){
							e.printStackTrace();
							
					}
				
				System.out.println("SpeciesRepository#showLiferTicks : " + table.toString());
				return table;
			}
		

			
			public static DefaultTableModel selectedChecklistView(String level1, String level2, String level3) throws Exception{
				String query = "SELECT DISTINCT Vogelart.NAME_LAT as \"Vogelname (lateinisch)\", Vogelart.NAME_DE as \"(deutsch)\", Vogelart.NAME_ENG as \"(englisch)\", Vogelart.ARTENTYP as \"Artentyp\" "
								+ " FROM ( (beobachtunsgebiet INNER JOIN kommtVor ON beobachtunsgebiet.ort_id = kommtVor.ort_id) INNER JOIN Vogelart ON Vogelart.va_id = kommtVor.va_id)"
								+ "	WHERE level_1 = '%L1%' and level_2 %L2% and level_3 %L3% AND NOT EXISTS "
                                                                      + " ( SELECT DISTINCT * FROM beobachtet, BEOBACHTUNSGEBIET "                                                                         
                                                                      + " WHERE "
                                                                      + " beobachtet.ORT_ID = Beobachtunsgebiet.ort_id and "
                                                                      + "  level_1 = '%L1%' and "
                                                                      + "  level_2  %L2% and "
                                                                      + "  level_3  %L3% and "
                                                                      + "  beobachtet.bw_id = %bw_id% and "
                                                                      + "  Vogelart.va_id = beobachtet.va_id )";
			
				if (level1 == null || level1.isEmpty()) {
					throw new Exception("Keine zooökologische Region gewählt!");
				} else {
					query = query.replace("%L1%", level1);
				}
				
				if (level2 == null || level2.isEmpty()) {
					query = query.replace("%L2%", "is null");
				} else {
					query = query.replace("%L2%", "= '" + level2 + "'");
				}
				
				if (level3 == null || level3.isEmpty()) {
					query = query.replace("%L3%", "is null");
				} else {
					query = query.replace("%L3%", "= '" + level3 + "'");
				}
				query = query.replace("%bw_id%", BirdwatcherRepository.getActiveUser().id());
				
				DefaultTableModel result = Application.getInstance().database().getTableModelOfQuery(query);
				return result;
			
			
			}
}



