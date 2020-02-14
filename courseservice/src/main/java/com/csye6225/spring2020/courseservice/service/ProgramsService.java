package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2020.courseservice.datamodel.Program;
import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;

public class ProgramsService {
	private static HashMap<String, Program> pgm_Map = InMemoryDatabase.getProgramsDB();

	public ProgramsService() {}

	public List<Program> getAllPrograms(){
		ArrayList<Program> allPrograms = new ArrayList<>();
		for(Program p:pgm_Map .values()) {
			allPrograms.add(p);
		}
		return allPrograms;
	}
 	public Program getProgram(String ProgramId) {
		Program program=pgm_Map.get(ProgramId);
		return program;
	}
	public Program addProgram(Program pgm) {
		long nextAvailableId = InMemoryDatabase.getNextProgramId();
		String id=String.valueOf(nextAvailableId);
		pgm.setProgramId(id);
		pgm_Map.put(id, pgm);
		return pgm;
	}

	public Program deleteProgram(String ProgramId) {
		Program pgm=getProgram(ProgramId);
		pgm_Map.remove(ProgramId);
		return pgm;
	}

	public Program updateProgram(String ProgramId, Program pgm) {
		Program oldProgram=pgm_Map.get(ProgramId);
		pgm.setProgramId(oldProgram.getProgramId());
		pgm_Map .put(ProgramId,pgm);
		return pgm;
	}
	
	public boolean isExist(String pgmId) {
		return pgm_Map.containsKey(pgmId);
	}
}
