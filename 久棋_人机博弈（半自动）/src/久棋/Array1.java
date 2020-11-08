package ¾ÃÆå;

import java.util.LinkedList;

public class Array1 {
	public LinkedList<int[]> li=new LinkedList<int[]>();
	public int type=0;
	final static int MOVE=1;
	final static int JUMP=2;
	final static int FLY=3;
	public Array1(LinkedList<int[]> l) {
		for(int i=0;i<l.size();i++) {
			li.add(l.get(i));
		}
	}

	public void setType(int t) {
		this.type=t;
	}
	
	public void showList() {
		if(type==MOVE) {
			System.out.println("("+li.get(0)[0]+","+li.get(0)[1]+") -> ("+li.get(1)[0]+","+li.get(1)[1]+")");
		}else {
			for(int i=0;i<li.size()-1;i++) {
				System.out.print("("+li.get(i)[0]+","+li.get(i)[1]+") -> ");
			}
			System.out.println("("+li.getLast()[0]+","+li.getLast()[1]+")");
		}
	}
	
}