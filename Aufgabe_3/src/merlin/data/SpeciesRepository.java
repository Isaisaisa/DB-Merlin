package merlin.data;

import static merlin.utils.ConstantElems.showMsgBox;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import merlin.base.Application;
import merlin.data.enums.SpeciesCategoryEnum;
import static merlin.data.enums.SpeciesCategoryEnum.*;



public class SpeciesRepository {

	public static void selectLocation(String region, String land, String area){
		if (land.isEmpty() && area.isEmpty()){
			
		}
		if (area.isEmpty()){
			
		}
		
	}
	
//	public static void loadRegion(){
//		ResultSet rs;
//		try {
//			rs = Application.getInstance().database().sendQuery("SELECT Level1 FROM Beobachtungsgebiet");
//			 while (rs.next()) {
//		        	MainWindowLogic.loadRegion(rs.getString(1));
//		     }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//       
//	}
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
		
		// Quelle: stackoverflow.com/question --> (iterating over ResultSet and adding its value in an Arraylist)
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
			
		
		

//		INSERT INTO beobachtet
//		  VALUES (VA_ID, BW_ID, ORT_ID, TO_DATE('08-MAI-2014 11:02', 'DD-MONTH-YYYY HH24:MI'), TO_DATE('09-MAI-2014 14:15', 'DD-MONTH-YYYY HH24:MI'), '2 m. 5 w. 3 juv.');

		
		public static void addDataObservation(int birdId, String level1, String level2, String level3, String dateFrom, String dateUntil, String notice){
			String str = getLocationId(level1, level2, level3);
			
			//TODO birdwatcherid holen
		}
			
		
		private static String getLocationId(String level1, String level2, String level3) {
			 String template1 = level1, template2 = "null", template3 = "null";
			
			if ( level1.isEmpty()) { return "-1"; } // fehler
			if (!level2.isEmpty()) { template2 = level2; }
			if (!level3.isEmpty()) { template3 = level3; }
			
			String query = "SELECT Ort_ID FROM Beobachtunsgebiete WHERE Level_1 = '" + template1 + "' AND Level_2 = '" + template2 + "' AND Level_3 = '" + template3 + "'";
			try {
				return Application.getInstance().database().getSingleValue(query);
			} catch (Exception e) {
				e.printStackTrace();
				showMsgBox(e); // TODO
				return "";
			}
			
		} 
		
		// Stammdaten holen
//		public static DefaultTableModel getCoreData() throws Exception {
//			return Application.getInstance().database().getTableModelOfQuery("SELECT * FROM VOGELART WHERE NAME_LAT LIKE 'X%' ORDER BY NAME_LAT ASC");
//			return Application.getInstance().database().getTableModelOfQuery("SELECT * FROM VOGELART ORDER BY NAME_LAT ASC");
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
}
