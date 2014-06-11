package merlin.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import merlin.base.Application;
import merlin.logic.impl.MainWindowLogic;



public class SpeziesRepository {

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
		public static List<String> getRegion(){
			ResultSet rs;
			List<String> list = new LinkedList<String>();
			try {
				rs = Application.getInstance().database().sendQuery("SELECT Level_1 FROM Beobachtunsgebiet WHERE Level_2 is null and Level_3 is null"); 
				while (rs.next()) {
			        list.add(rs.getString(1));
			        System.out.println("resultSet fromLevel_1 : " + list.toString());
			     }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
	       
		}
		public static List<String> getLand(String string){
			ResultSet rs;
			List<String> list = new LinkedList<String>();
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
			return list;
		}
			
		
}
