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
		for(Program c:pgm_Map .values()) {
			allPrograms.add(c);
		}
		return allPrograms;
	}
 	public Program getProgram(String ProgramId) {
		Program cors=pgm_Map .get(ProgramId);
		System.out.println("Item retrieved:");
		System.out.println(cors.toString());
		return cors;
	}
	public Program addProgram(Program pgm) {
		pgm_Map .put(pgm.getProgramName(),pgm);
		return pgm;
	}

	public Program deleteProgram(String ProgramId) {
		Program pgm=getProgram(ProgramId);
		pgm_Map.remove(ProgramId);
		return pgm;
	}

	public Program updateProgram(String ProgramId, Program pgm) {
		Program oldProgram=pgm_Map .get(ProgramId);
		pgm.setProgramName(oldProgram.getProgramName());
		pgm_Map .put(ProgramId,pgm);
		return pgm;
	}
}
