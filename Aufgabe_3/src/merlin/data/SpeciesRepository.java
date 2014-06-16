package merlin.data;



import static merlin.utils.ConstantElems.showMsgBox;






import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import merlin.base.Application;
import merlin.base.DbWrapper;
import merlin.data.enums.SpeciesCategoryEnum;
import merlin.utils.ConstantElems;
import static merlin.data.enums.SpeciesCategoryEnum.*;


//Daten holen zur Checkliste
public class SpeciesRepository {

	public static DefaultTableModel selectLocation(String region, String land, String area){
		DbWrapper database;
		DefaultTableModel table = new DefaultTableModel();
		try {
			database = Application.getInstance().database();
			System.out.println("37 SpeciesRepository : "+database);
			if ((land == null && area == null) || (land.isEmpty() && area.isEmpty())){
				table = database.getTableModelOfQuery("SELECT DISTINCT v.Name_Lat, v.Name_DE , v.Name_ENG, v.Artentyp FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "'");
				
			}else if (area == null || area.isEmpty()){
				table = database.getTableModelOfQuery("SELECT DISTINCT v.Name_Lat, v.Name_DE , v.Name_ENG, v.Artentyp FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND level_2 = '" +  land  + "'");
			
			}else{
				table = database.getTableModelOfQuery("SELECT DISTINCT v.Name_Lat, v.Name_DE , v.Name_ENG, v.Artentyp FROM Beobachtunsgebiet b INNER JOIN kommtVor kv ON b.ort_ID = kv.ort_ID INNER JOIN "
						+ "Vogelart v ON  v.va_ID = kv.va_ID WHERE level_1 = '" + region + "' AND level_2 = '" +  land  + "' AND level_3 = '" + area + "'");
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConstantElems.showMsgBox(e);
		}
		return table;
	
	}
	
	//Holt alle Level1 aus Beobachtung
		public static Vector<String> getRegion(){
			ResultSet rs;
			Vector<String> list = new Vector<String>();
			list.add("");
			try {
				rs = Application.getInstance().database().sendQuery("SELECT Level_1 FROM Beobachtunsgebiet WHERE Level_2 is null and Level_3 is null"); 
				while (rs.next()) {
			        list.add(rs.getString(1));
			     }
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(list);
			list = new Vector<String>(new HashSet<String>(list));
			return list;
		}
			
		
		

		
		public static void addDataObservation(String birdId, String level1, String level2, String level3, String dateFrom, String dateUntil, String notice){
			System.out.println("120 SpeciesRepository : " + level1);
			System.out.println("121 SpeciesRepository : " + level2);
			System.out.println("122 SpeciesRepository : " + level3);
			String str = getLocationId(level1, level2, level3);
			//TODO birdwatcherid holen
			
			DbWrapper database;
			try {
				database = Application.getInstance().database();
				if (dateUntil.equals("null")){
					String tmp ="INSERT INTO beobachtet"
							+ "	VALUES( '" + birdId + "', '3', '" + str + "', TO_DATE('" + dateFrom + "', 'DD-MM-YYYY HH24:MI'), 'null', '" + notice + "')";
					System.out.println("132 SpeciesRepository : "+tmp);
					database.sendUpdate(tmp);
				}else{	
					String tmp = "INSERT INTO beobachtet"
							+ "	VALUES( '" + birdId + "', '3', '" + str + "', TO_DATE('" + dateFrom + "', 'DD-MM-YYYY HH24:MI'), TO_DATE('" + dateUntil + "', 'DD-MM-YYYY HH24:MI'), '" + notice + "')";
					System.out.println("137 SpeciesRepository : " + tmp);
					database.sendUpdate(tmp);
//				database.sendUpdate("INSERT INTO beobachtet"
//						+ "	VALUES ( '" + birdId + "', '" + bw_Id + "', '" + str + "', TO_DATE('" + dateFrom + "', 'DD-MM-YYYY HH24:MI'), TO_DATE('" + dateUntil + "', 'DD-MM-YYYY HH24:MI'), '" + notice + "')");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				//TODO warum es nicht funktioniert
				ConstantElems.showMsgBox(e);
			}
			
			
		}
			
		
		private static String getLocationId(String level1, String level2, String level3) {
//			 String template1 = level1, template2 = "null", template3 = "null";
//			
//			if ( level1.isEmpty()) { return "-1"; } // fehler
//			if (!level2.isEmpty()) { template2 = level2; }
//			if (!level3.isEmpty()) { template3 = level3; }
//			
//			String query = "SELECT Ort_ID FROM Beobachtunsgebiete WHERE Level_1 = '" + template1 + "' AND Level_2 = '" + template2 + "' AND Level_3 = '" + template3 + "'";
//			try {
//				return Application.getInstance().database().getSingleValue(query);
//			} catch (Exception e) {
//				e.printStackTrace();
//				showMsgBox(e); // TODO
//				return "";
//			}
			String query = "";
			String stmt = "SELECT ort_Id FROM Beobachtunsgebiet WHERE level_1 = '";
			
			try{
				if (level1 == null){
					return "Mindestens die Zooökologische Region muss ausgewählt werden";
				}else if ((level2 == null && level3 == null) || level2.isEmpty() && level3.isEmpty()){
					query =  stmt + level1 + "' AND level_2 IS NULL AND level_3 IS NULL";
						
				}else if (level3 == null || level3.isEmpty()){
					query =  stmt + level1 + "' AND level_2 ='" + level2 + "' AND level_3 IS NULL";
				}else{
					query =  stmt + level1 + "' AND level_2 ='" + level2 + "' AND level_3 = '" + level3 + "'";
				}
				return Application.getInstance().database().getSingleValue(query);
				
			}catch (Exception e){
				e.printStackTrace();
				showMsgBox(e); // TODO
				return "EXCEPTION IS THROWN";
			}
			
		} 
		
		// Stammdaten holen
//		public static DefaultTableModel getCoreData() throws Exception {
//			return Application.getInstance().database().getTableModelOfQuery("SELECT * FROM VOGELART WHERE NAME_LAT LIKE 'X%' ORDER BY NAME_LAT ASC");
////			return Application.getInstance().database().getTableModelOfQuery("SELECT * FROM VOGELART ORDER BY NAME_LAT ASC");
//		}
		
//		public static DefaultTableModel getCoreData(String filter) throws Exception {
//			
//		}
//		
//		public static DefaultTableModel getCoreData(String filter, String location) throws Exception {
//			
//		}
//		
	
		public static DefaultTableModel getCoreData(String filter, SpeciesCategoryEnum species, int orderBy) throws Exception {
			// TODO Gefilterte Stammdaten ausgeben
			/*
			 * String filter
			 * SpeciesCategoryEnum species
			 * int orderBy => 0 = none, 1 = ASC, 2 = DESC
			 * 
			 */
			boolean filtered = filter != null && !filter.isEmpty();
			boolean filterSpecies = !species.value().equals(ALL.value());
			boolean orderResult = orderBy > 0;
			boolean limitQuery = filtered || filterSpecies;
			String query = "SELECT * FROM VOGELART";
			
			
			// MEEEGA dreckig. Is nur zu testzwecken so gebaut
			if (limitQuery) {
				query+= " WHERE ";
				
				if (filtered) {
					query += "(NAME_LAT LIKE '%" + filter +
							 "%' OR NAME_DE LIKE '%" + filter +
							"%' OR NAME_ENG LIKE '%" + filter +
							"%' OR ARTENTYP LIKE '%" + filter + "%')";
				}
			}			
			
			return Application.getInstance().database().getTableModelOfQuery(query);
		}
		
		
		

		
//		Methode um "beobachtet" in Gui zu laden
//		public static DefaultTableModel getDataObservation() throws Exception{
//			return Application.getInstance().database().getTableModelOfQuery("SELECT * FROM beobachtet");
//		}
		
		

}
