package merlin.logic.impl;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import merlin.data.SpeciesRepository;
import merlin.data.enums.SpeciesCategoryEnum;

public class MainWindowLogic {

	public static DefaultTableModel selectLocation(String region, String land, String area){
		return SpeciesRepository.selectLocation(region, land, area);
	}
	
	public static DefaultComboBoxModel<String> getLevel1Data() {
		return SpeciesRepository.getLevel1Data();
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
	

	public static void addObservation(String birdId, String level1, String level2, String level3, String dateFrom, String dateUntil, String notice) throws Exception{
		SpeciesRepository.addDataObservation(birdId, level1, level2, level3, dateFrom, dateUntil, notice);
	}
	
	public static void addLocation(String l1, String l2, String l3) {
		SpeciesRepository.addLocation(l1, l2, l3);
	}
	
	public static void addBird(String name_lat, String name_de, String name_eng, String spec) throws Exception {
		SpeciesRepository.addBird(name_lat, name_de, name_eng, spec);
	}
	
	public static DefaultTableModel getLocations() throws Exception {
		return SpeciesRepository.getLocations();
	}
	
	public static DefaultTableModel getCoreData(String filter, String spec) throws Exception {
		return SpeciesRepository.getCoreData(filter, spec);
	}
	
	// leitet an die Datenbankebene weiter, um Tablle Beobachtet zu holen
	public static DefaultTableModel getDataObservation() {
		try {
			return SpeciesRepository.getDataObservation();
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultTableModel();
		}
	}
	
	
	public static void deleteDataObservation(String beoId){
		SpeciesRepository.deleteDataObservation(beoId);
	}
	
	
	
	public static DefaultTableModel showLiferTicks(String level1, String level2, String level3, String filter, boolean ticks, boolean lifer){
		return SpeciesRepository.showLiferTicks(level1, level2, level3, filter, ticks, lifer);
	}
	
}

