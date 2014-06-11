package merlin.logic.impl;

import merlin.data.SpeziesRepository;
import merlin.gui.MerlinMainWindow;

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
	
	
//	gibt Name (String) von Level1 an GUI weiter
	public static void loadRegion(){
		
		for (String region : SpeziesRepository.getRegion()){
			MerlinMainWindow.loadLevel1(region);
			
		}
	}
	public static void loadLand(String string){
		
		for (String region : SpeziesRepository.getLand(string)){
			MerlinMainWindow.loadLevel2(region);
			
		}
	}
	
}
