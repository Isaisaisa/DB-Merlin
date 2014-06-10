package merlin.data;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public static void loadRegion(){
		ResultSet rs;
		try {
			rs = Application.getInstance().database().sendQuery("SELECT Level1 FROM Beobachtungsgebiet");
			 while (rs.next()) {
		        	MainWindowLogic.loadRegion(rs.getString(1));
		     }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
	}
		
}
