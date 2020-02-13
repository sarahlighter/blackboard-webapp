package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Lecture;

public class LecturesService {
	private static HashMap<String, Lecture> lect_Map = new HashMap<>();
	
	public LecturesService() {
	}
	
	// Getting a list of all Lecture 
	// GET "..webapi/Lectures"
	public List<Lecture> getAllLectures() {	
		//Getting the list
		ArrayList<Lecture> list = new ArrayList<>();
		for (Lecture lect : lect_Map.values()) {
			list.add(lect);
		}
		return list ;
	}

	
	public Lecture addLecture(Lecture lect) {
		long nextAvailableId = InMemoryDatabase.getNextLectureId();
		String id=String.valueOf(nextAvailableId);
		lect.setLectureId(id);
		lect_Map.put(id, lect);
		return lect;
	}
	// Getting One Lecture
	public Lecture getLecture(String lectId) {
		 //Simple HashKey Load
		 Lecture lect2 = lect_Map.get(lectId);
	     System.out.println("Item retrieved:");
	     System.out.println(lect2.toString());
		
		return lect2;
	}
	
	// Deleting a Lecture
	public Lecture deleteLecture(String lectId) {
		Lecture deletedlectDetails = lect_Map.get(lectId);
		lect_Map.remove(lectId);
		return deletedlectDetails;
	}
	
	// Updating Lecture Info
	public Lecture updateLecture(String lectId, Lecture lect) {	
		Lecture oldlectObject = lect_Map.get(lectId);
		if (oldlectObject == null)
        {
            return null;
        }
		String LectureId = oldlectObject.getLectureId();
		lect.setLectureId(LectureId);
		lect_Map.put(lectId, lect);
		return lect;
	}
	
	

}
