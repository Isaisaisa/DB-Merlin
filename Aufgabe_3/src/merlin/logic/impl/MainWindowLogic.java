package merlin.logic.impl;

import java.util.Vector;

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
	public static Vector<String> loadRegion(){
		return SpeziesRepository.getRegion();
	
	}
	public static Vector<String> loadLand(String level1){
		return SpeziesRepository.getLand(level1);
	}
	
	public static Vector<String> loadArea(String level1, String level2){
		return SpeziesRepository.getArea(level1, level2);
	}
	
	
	public static void addObservation(String level1, String level2, String level3, String dateFrom, String dateUntil, String notice){
		addObservation(level1, level2, level3, dateFrom, dateUntil, notice);
	}
	
}
