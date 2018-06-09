package bdfh.gui.controller;


import javafx.scene.input.KeyCode;

import javax.management.openmbean.CompositeType;
import java.util.ArrayDeque;
import java.util.Deque;


import static javafx.scene.input.KeyCode.*;

/**
 * @author Bryan Curchod
 * @version 1.0
 */
public class Konami {
	
	private static final KeyCode[] sequence = { UP, UP, DOWN, DOWN, LEFT, RIGHT, LEFT, RIGHT, B, A};
	
	private static Deque<KeyCode> typed = new ArrayDeque<>();
	
	
	public boolean typed(KeyCode key){
		typed.addLast(key);
		//System.out.println("t'es dans typed poto");
		if(typed.size() > sequence.length){
			typed.pop();
		}
		
		if(typed.size() == sequence.length){
			return sequenceComplete();
		}
		
		return false;
	}
	
	
	private boolean sequenceComplete(){
		int pos = 0;
		
		boolean complete = true;
		for(KeyCode key : typed){
			if(key != sequence[pos++]){
				complete = false;
				break;
			}
		}
		
		return complete;
	}
}
