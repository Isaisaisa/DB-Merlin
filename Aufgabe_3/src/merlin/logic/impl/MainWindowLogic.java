package merlin.logic.impl;

import merlin.data.SpeziesRepository;

public class MainWindowLogic {

	public static void selectLocation(String region, String land, String area){
		SpeziesRepository.selectLocation(region, land, area);
	}
	public static void selectLocation(String region, String land){
		selectLocation(region, land, "");
	}
	public static void selectLocation(String region){
		selectLocation(region, "", "");
	}
	
	
	public static void loadRegion(String string){
//		MerlinMainWindow.loadLevel1(string);
//		
//
//        jCombobox.addItem(rs.getString(1));
	}
	
}
