package merlin.logic.impl;

import static merlin.utils.ConstantElems.showMsgBox;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import merlin.data.SpeciesRepository;
import merlin.data.enums.SpeciesCategoryEnum;

public class MainWindowLogic {

	public static void selectLocation(String region, String land, String area){
		SpeciesRepository.selectLocation(region, land, area);
	}
	public static void selectLocation(String region, String land){
		selectLocation(region, land, "");
	}
	public static void selectLocation(String region){
		selectLocation(region, "", "");
	}
	
	
//	gibt Name (String) von Level1 an GUI weiter
	public static Vector<String> loadRegion(){
		return SpeciesRepository.getRegion();
	
	}
	public static Vector<String> loadLand(String level1){
		return SpeciesRepository.getLand(level1);
	}
	
	public static Vector<String> loadArea(String level1, String level2){
		return SpeciesRepository.getArea(level1, level2);
	}
	
	
	public static void addObservation(int birdId, String level1, String level2, String level3, String dateFrom, String dateUntil, String notice){
		SpeciesRepository.addDataObservation(birdId, level1, level2, level3, dateFrom, dateUntil, notice);
		
	}
	
	public static DefaultTableModel loadTableDataIntoGui() {
		try {
			return SpeciesRepository.getCoreData("x", SpeciesCategoryEnum.ALL, 0);
		} catch (Exception e) {
			e.printStackTrace();
			showMsgBox(e); //TODO im auge behalten
			return new DefaultTableModel();
		}
	}
}
