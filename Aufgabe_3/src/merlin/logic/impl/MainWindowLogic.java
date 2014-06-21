package merlin.logic.impl;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import merlin.data.SpeciesRepository;
import merlin.data.enums.SpeciesCategoryEnum;

public class MainWindowLogic {

	public static DefaultTableModel selectLocation(String region, String land, String area){
			return SpeciesRepository.selectLocation(region, land, area);
	}
	
	
//	public static void selectLocation(String region, String land){
//		selectLocation(region, land, "");
//	}
//	public static void selectLocation(String region){
//		selectLocation(region, "", "");
//	}
	
	
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
	

	public static void addObservation(String birdId, String level1, String level2, String level3, String dateFrom, String dateUntil, String notice){
		SpeciesRepository.addDataObservation(birdId, level1, level2, level3, dateFrom, dateUntil, notice);

		
	}
	
	public static DefaultTableModel loadTableDataIntoGui() {
		try {
			return SpeciesRepository.getCoreData("xa", SpeciesCategoryEnum.SPECIES, 0);
		} catch (Exception e) {
			e.printStackTrace();
//			showMsgBox(e); //TODO im auge behalten --> macht ärger beim Windowbuilder
			return new DefaultTableModel();
		}
	}
	
	// leitet an die Datenbankebene weiter, um Tablle Beobachtet zu holen
	public static DefaultTableModel getDataObservation(){
		DefaultTableModel table = null;
		try {
			table = SpeciesRepository.getDataObservation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}

}
