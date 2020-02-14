package com.csye6225.spring2020.courseservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2020.courseservice.datamodel.InMemoryDatabase;
import com.csye6225.spring2020.courseservice.datamodel.Board;

public class BoardsService {
	private static HashMap<String, Board> brd_Map = new HashMap<>();
	
	public BoardsService() {}

	// Getting a list of all Board 
	// GET "..webapi/boards"
	public List<Board> getAllBoards() {	
		//Getting the list
		ArrayList<Board> list = new ArrayList<>();
		for (Board brd : brd_Map.values()) {
			list.add(brd);
		}
		return list ;
	}
	public List<Board> getBoardsByCourse(String corsId){
		ArrayList<Board> list = new ArrayList<>();
		for (Board brd : brd_Map.values()) {
			if(brd.getCourseId().equals(corsId))
				list.add(brd);
		}
		return list ;
	}
	public Board addBoard(Board brd) {
		long nextAvailableId = InMemoryDatabase.getNextBoardId();
		String id=String.valueOf(nextAvailableId);
		brd.setBoardId(id);
		brd.setPostDate(new Date().toString());
		brd_Map.put(id, brd);
		if( !isValid(brd)) {
			return null;
		}
		return brd;
	}

	// Getting One Board
	public Board getBoard(String brdId) {
		if(!isExist(brdId)) {
			return null;
		}
		Board brd = brd_Map.get(brdId);
		return brd;
	}

	// Deleting a Board
	public Board deleteBoard(String brdId) {
		if(!isExist(brdId)) {
			return null;
		}
		Board oldbrdObject = brd_Map.get(brdId);
		brd_Map.remove(brdId);
		return oldbrdObject;
	}

	// Updating Board Info
	public Board updateBoard(String brdId, Board brd) {	
		if(!isExist(brdId)) {
			return null;
		}
		Board oldbrdObject = brd_Map.get(brdId);
		brd.setBoardId(brdId);
		brd.setPostDate(oldbrdObject.getPostDate());
		brd_Map.put(brdId, brd);
		return brd;
	}

	public boolean isExist(String brdId) {
		return brd_Map.containsKey(brdId);
	}
	public boolean isValid(Board brd) {
		if(!new CoursesService().isExist(brd.getCourseId())) {
			return false;
		}
		return true;
	}
}
