package 久棋;



import java.util.LinkedList;

public class Chess {
	public int[][] chessboard = new int[14][14];// 棋盘大小以后修改
	public int color;//本回合所下棋子的颜色
	public Array1[] moveList=new Array1[2000]; 
	public int value=0; //总价值
	public int kill=0; //跳子得分
	public int move=0; //走子得分
	public int fly=0;//飞子得分
	public int index=0; //记录走子和跳子的分界
	public int index1=0; //记录飞子阶段跳子和飞子的分界
	public int pretri[]=new int[169];
	public int gameOverScore=0;
	//测试1(黑子)
	//走子
	public final static int MOVEDALIANSCORE=350;//褡裢最好走子点得分
	public final static int MOVEDESTRORYDALIAN=250;//走子破坏对方褡裢得分
	public final static int MOVEDALIANFORM=200;//走子形成褡裢得分
	public final static int MOVESQUARE=80;//走子普通成方得分
	public final static int MOVEPRETRI=150;//走子防止对方成方得分
	public final static int MOVETRIANGLE=60;//走子己方三角得分
	public final static int MOVETRIBE=15;//走子己方三连子得分
	public final static int MOVEDOUBE=10;//走子己方二连子得分
	public final static int MOVECOMMON=1;//普通走子得分
	//跳子
	public static final int JUMPDESTRORYDALIAN1=200;//跳子破坏褡裢得分1,表示跳子跳入对方褡裢的最好行棋点，但并没有减少对方褡裢的数量
	public static final int JUMPDESTRORYDALIAN2=350;//跳子破坏褡裢得分2，表示破坏对方褡裢，使敌方褡裢数量-1
	public final static int JUMPDALIANFORM=150;//跳子己方形成褡裢得分
	public final static int JUMPSQUARE=200;//跳子己方普通成方
	public final static int JUMPPRETRI=150;//跳子防止对方成方得分
	public final static int JUMPTRIANGLE=100;//跳子己方成三角
	public final static int JUMPTRIBE=30;//跳子己方三连子得分
	public final static int JUMPDOUBE=15;//跳子己方2连子得分
	public final static int JUMPCOMMON=10;//普通跳子得分
	//吃子
	public final static int EATSQUARE=50;//吃子吃掉对方成方得分
	public final static int EATTRIANGLE=75;//吃子吃掉对方三角得分
	public final static int EATCOMMON=5;//普通吃子得分
	public final static int EATDALIANBEST=100;//吃对方最好褡裢点的得分 
	public final static int EATDESTROYDALIAN=60;//吃子使其破坏对方褡裢，但并不是最好的点
	//飞子
	public final static int FLYDALIANSCORE=200;//褡裢最好飞子点得分(在飞子阶段特殊的走子点)
	public final static int FLYDESTRORYDALIAN=300;//飞子破坏褡裢得分
	public final static int FLYDALIANFORM=50;//飞子形成褡裢得分
	public final static int FLYSQUARE=150;//飞子成方得分
	public final static int FLYPRETRI=100;//飞子防止对方成方得分
	public final static int FLYTRIANGLE=80;//飞子三角得分
	public final static int FLYTRIBE=20;//飞子三连子得分
	public final static int FLYDOUBE=10;//飞子二连子得分
	public final static int FLYCOMMON=5;//普通飞子得分
	
	
	public LinkedList<int[]> bestMove=new LinkedList<int[]>();
	public LinkedList<int[]> bestMoveChi=new LinkedList<int[]>();
	public LinkedList<int[]> moveChi=new LinkedList<int[]>();
	
	
	public void setValue(int value) {
		this.value = value;
	}

	public Chess(int[][] chessboard) {
		super();
		for(int i=0;i<this.chessboard.length;i++) {
			for(int j= 0;j<this.chessboard[i].length;j++) {
				this.chessboard[i][j]=chessboard[i][j];
			}
		}
	}
	
	public Chess(int[][] chessboard,int nextColor) {
		super();
		for(int i=0;i<this.chessboard.length;i++) {
			for(int j= 0;j<this.chessboard[i].length;j++) {
				this.chessboard[i][j]=chessboard[i][j];
			}
		}
		this.color=nextColor;
	}
	
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
 		this.color = color;
 	}

	public void showBoard() {
		for(int i=0;i<this.chessboard.length;i++) {
			for(int j= 0;j<this.chessboard[0].length;j++) {
				System.out.print(String.format("%3d", this.chessboard[i][j]));
			}
			System.out.println();
		}
	}
	public int doubeTotal(int color1) {
		int totalDoube=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(chessboard[i][j]==color1) {
					int firstColor=chessboard[i][j];
					if(i+1>13) {
						if(j+1<14) {
							if(chessboard[i][j+1]==firstColor) {
								totalDoube++;
							}
						}
					}else {
						if(j+1>13) {
							if(chessboard[i+1][j]==firstColor) {
								totalDoube++;
							}
						}else {
							if(chessboard[i][j+1]==firstColor) {
								totalDoube++;
							}
							if(chessboard[i+1][j]==firstColor) {
								totalDoube++;
							}
						}
					}
				}
			}
		}
		return totalDoube;
	}
	
	public int trinum1(int color1) {
		int n = 0;
		int m = 0;
		int k = 0;
		int color2=0;
		if(color1==1) {
			color2=2;
		}else if(color1==2) {
			color2=1;
		}
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				if (chessboard[i][j] == color1)
					n++;
				if (chessboard[i][j + 1] == color1)
					n++;
				if (chessboard[i + 1][j] == color1)
					n++;
				if (chessboard[i + 1][j + 1] == color1)
					n++;
				if (chessboard[i][j] == color2)
					k++;
				if (chessboard[i][j + 1] == color2)
					k++;
				if (chessboard[i + 1][j] == color2)
					k++;
				if (chessboard[i + 1][j + 1] == color2)
					k++;
				if(n==3&&k==0) {
					m++;
				}
				n=0;
				k=0;
			}
		}
		return m;
	}
	public int[] trinum(int color1) {
		int n = 0;
		int m = 0;
		int color2=0;
		int k = 0;
		if(color1==1) {
			color2=2;
		}else if(color1==2) {
			color2=1;
		}
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				if (chessboard[i][j] == color1)
					n++;
				if (chessboard[i][j + 1] == color1)
					n++;
				if (chessboard[i + 1][j] == color1)
					n++;
				if (chessboard[i + 1][j + 1] == color1)
					n++;
				if (chessboard[i][j] == color2)
					k++;
				if (chessboard[i][j + 1] == color2)
					k++;
				if (chessboard[i + 1][j] == color2)
					k++;
				if (chessboard[i + 1][j + 1] == color2)
					k++;
				if(n==3) {
					pretri[m]=1;
					if(k==1) {
						if(color2!=0) {
							pretri[m]=2;
						}
					}
					m++;
				}else{
					pretri[m]=0;
					m++;
				}
				n=0;
				k=0;
			}
		}
		return pretri;
	}
	public int[] Squarenum(int color){//计算当前状态下棋盘中成方的个数
		int[] num = new int[169];
		int m1=0;
		for(int i=0;i<13;i++) {
			for(int j=0;j<13;j++) {
				if(chessboard[i][j]==color) {
					if((chessboard[i][j]==chessboard[i][j+1] && chessboard[i][j+1]==chessboard[i+1][j] &&chessboard[i+1][j]==chessboard[i+1][j+1])&&chessboard[i][j]!=0) {
						num[m1]=1;
						m1++;
					}else {
						num[m1]=0;
						m1++;
					}
				}else {
					num[m1]=0;
					m1++;
				}
			}
		}
		return num;
	}
	
	
	public void move(int x1, int y1, int x2, int y2) {//行棋方法
		if (x1 >= 0 && x1 < 14 && x2 >= 0 && x2 < 14 && y1 >= 0 && y1 < 14 && y2 >= 0 && y2 < 14) {
			if (this.chessboard[x1][y1] == this.color) {
				int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
				if(this.isLessFourteen()) {
					if (Dvalue == 4) {
						if (chessboard[x2][y2] == 0
								&& (chessboard[x1][y1] + chessboard[(x1 + x2) / 2][(y1 + y2) / 2]) == 3) {
							chessboard[x2][y2] = this.color;
							chessboard[x1][y1] = 0;
							chessboard[(x1 + x2) / 2][(y1 + y2) / 2] = 0;
						}
					}else {
						chessboard[x1][y1] = 0;
						chessboard[x2][y2] = this.color;
					}
				}else {
					if (Dvalue == 1) {
						chessboard[x1][y1] = 0;
						chessboard[x2][y2] = this.color;
					} else if (Dvalue == 4) {
						if (chessboard[x2][y2] == 0
								&& (chessboard[x1][y1] + chessboard[(x1 + x2) / 2][(y1 + y2) / 2]) == 3) {
							chessboard[x2][y2] = this.color;
							chessboard[x1][y1] = 0;
							chessboard[(x1 + x2) / 2][(y1 + y2) / 2] = 0;
						}
					}
				}
			}
		}else {
			System.out.println("行棋不规范");
		}

 	}
	
	public void moveBack(int x1, int y1, int x2, int y2) {//回溯方法
		if (x1 >= 0 && x1 < 14 && x2 >= 0 && x2 < 14 && y1 >= 0 && y1 < 14 && y2 >= 0 && y2 < 14) {
				int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
				if (Dvalue == 1) {
					if (chessboard[x1][y1] == 1) {
						chessboard[x1][y1] = 0;
						chessboard[x2][y2] = 1;
					} else if (chessboard[x1][y1] == 2) {
						chessboard[x1][y1] = 0;
						chessboard[x2][y2] = 2;
					}
				} else if (Dvalue == 4) {
					if (chessboard[x2][y2] == 0
							&& chessboard[x1][y1]==1) {
						chessboard[x2][y2] = 1;
						chessboard[x1][y1] = 0;
						chessboard[(x1 + x2) / 2][(y1 + y2) / 2] = 2;
					}else if (chessboard[x2][y2] == 0
							&& chessboard[x1][y1]==2) {
						chessboard[x2][y2] = 2;
						chessboard[x1][y1] = 0;
						chessboard[(x1 + x2) / 2][(y1 + y2) / 2] = 1;
					}
				}
			
		}else {
			System.out.println("行棋不规范");
		}

	}
	public void moveBack1(int x1, int y1, int x2, int y2) {//回溯方法
		if (x1 >= 0 && x1 < 14 && x2 >= 0 && x2 < 14 && y1 >= 0 && y1 < 14 && y2 >= 0 && y2 < 14) {
			this.chessboard[x2][y2]=this.chessboard[x1][y1];
			this.chessboard[x1][y1]=0;
		}else {
			System.out.println("行棋不规范");
		}

	}
	public int getAnotherColor() {
		if(this.color==1) {
 			return 2;
		}else {
			return 1;
		}
	}
	public boolean hop(int x2,int y2) {//在一次跳子行棋过后,判断是否可以继续连续跳子
		int m1=x2-2;
        int n1=y2;
        int m2=x2;
        int n2=y2-2;
        int m3=x2+2;
        int n3=y2;
        int m4=x2;
        int n4=y2+2;
        boolean state = false;
        int len = chessboard.length;
        if(m1>=0 && m1<len && n1>=0 && n1<len && chessboard[m1][n1]==0 && (chessboard[x2][y2]+chessboard[(x2+m1)/2][(y2+n1)/2]==3)) {
        	state = true;
        }
        if(m2>=0 && m2<len && n2>=0 && n2<len && chessboard[m2][n2]==0 && (chessboard[x2][y2]+chessboard[(x2+m2)/2][(y2+n2)/2]==3)) {
        	state = true;
        }
        if(m3>=0 && m3<len && n3>=0 && n3<len && chessboard[m3][n3]==0 && (chessboard[x2][y2]+chessboard[(x2+m3)/2][(y2+n3)/2]==3)) {
        	state = true;
        }
        if(m4>=0 && m4<len && n4>=0 && n4<len && chessboard[m4][n4]==0 && (chessboard[x2][y2]+chessboard[(x2+m4)/2][(y2+n4)/2]==3)) {
        	state = true;
        }
        return state;
	}
	
	public  boolean checkIsEmpty() {
		for(int i=0;i<this.chessboard.length;i++) {
			for(int j=0;j<this.chessboard[i].length;j++) {
				if(this.chessboard[i][j]==0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Array1[] getMoveList() {//得到除飞子阶段的可行点
		MoveList();
		jumpMoveList();
		return moveList;
	}
	
	public Array1[] getMoveList1() {
		jumpMoveList();
		index1=index;
		index++;
		if(isLessFourteen()) {
			for(int i=0;i<14;i++) {
				for(int j=0;j<14;j++) {
					if(this.color==this.chessboard[i][j]) {
						LinkedList<int[]> li=new LinkedList<int[]>();
						int xy1[]= {i,j};
						li.add(xy1);
						moveList[index]=new Array1(li);
						moveList[index].setType(Array1.FLY);
						index++;
					}
				}
			}
		}
		index--;
		return moveList;
	}
	public void MoveList() {
		int m=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(chessboard[i][j]==this.color) {
					if(i-1<0) {//即i=0
						if(j-1<0) {//即j=0
							if(chessboard[i][j+1]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if(chessboard[i+1][j]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						}else if(j+1>13) {//即j=13
							if(chessboard[i+1][j]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if(chessboard[i][j-1]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						}else  {//即j=1~12
							if(chessboard[i+1][j]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if(chessboard[i][j-1]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if(chessboard[i][j+1]==0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						}
					}else if(i + 1 > 13){//即i=13
						if (j - 1 < 0) {//即j=0
							if (chessboard[i][j + 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i - 1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}

						} else if (j + 1 > 13) {//即j=13
							if (chessboard[i -1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i][j - 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						} else {
							if (chessboard[i - 1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i][j - 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i][j + 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						}
					}else {//即i=1~12
						if (j - 1 < 0) {//即j=0
							if (chessboard[i][j + 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i - 1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i + 1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}

						} else if (j + 1 > 13) {//即j=13
							if (chessboard[i -1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i +1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i][j - 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						} else {
							if (chessboard[i - 1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i][j - 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i][j + 1] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+1};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
							if (chessboard[i+1][j] == 0) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+1,j};
								li.add(xy1);
								li.add(xy2);
								moveList[m]=new Array1(li);
								moveList[m].setType(Array1.MOVE);
								m++;
							}
						}
					}
				}
			}
		}
		index=m;
	}

	public void jumpMoveList() {
		index--;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(chessboard[i][j]==this.color&&hop(i,j)) {
					if(i-2<0) {//i=0,1
						if(j-2<0) {//j=0,1
							if(chessboard[i][j+2]==0&&(chessboard[i][j+1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+2};
								li.add(xy1);
								li.add(xy2);
								moveList[++index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i,j+2);
								if(hop(i,j+2)) {
									jump(i,j+2,index1);
								}
								jumpBackUp(i,j+2,i,j);
							}
							if(this.chessboard[i+2][j]==0&&(chessboard[i+1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i+2,j);
								if(hop(i+2,j)) {
									jump(i+2,j,index1);
								}
								jumpBackUp(i+2,j,i,j);
							}
						}else if(j+2>13) {//j=12,13
							if(this.chessboard[i][j-2]==0&&(chessboard[i][j-1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i,j-2);
								if(hop(i,j-2)) {
									jump(i,j-2,index1);
								}
								jumpBackUp(i,j-2,i,j);
							}
							if(this.chessboard[i+2][j]==0&&(chessboard[i+1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i+2,j);
								if(hop(i+2,j)) {
									jump(i+2,j,index1);
								}
								jumpBackUp(i+2,j,i,j);
							}
						}else {//j=2~11
							if(this.chessboard[i][j-2]==0&&(chessboard[i][j-1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j-2);
								int index1=index;
								if(hop(i,j-2)) {
									jump(i,j-2,index1);
								}
								jumpBackUp(i,j-2,i,j);
							}
							if(this.chessboard[i+2][j]==0&&(chessboard[i+1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i+2,j);
								int index1=index;
								if(hop(i+2,j)) {
									jump(i+2,j,index1);
								}
								jumpBackUp(i+2,j,i,j);
							}
							if(this.chessboard[i][j+2]==0&&(chessboard[i][j+1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j+2);
								int index1=index;
								if(hop(i,j+2)) {
									jump(i,j+2,index1);
								}
								jumpBackUp(i,j+2,i,j);
							}
						}
					}else if(i+2>13) {//i=12,13
						if(j-2<0) {//j=0,1
							if(this.chessboard[i][j+2]==0&&(chessboard[i][j+1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j+2);
								int index1=index;
								if(hop(i,j+2)) {
									jump(i,j+2,index1);
								}
								jumpBackUp(i,j+2,i,j);
							}
							if(this.chessboard[i-2][j]==0&&(chessboard[i-1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i-2,j);
								if(hop(i-2,j)) {
									jump(i-2,j,index1);
								}
								jumpBackUp(i-2,j,i,j);
							}
						}else if(j+2>13) {//j=12,13
							if(this.chessboard[i][j-2]==0&&(chessboard[i][j-1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j-2);
								int index1=index;
								if(hop(i,j-2)) {
									jump(i,j-2,index1);
								}
								jumpBackUp(i,j-2,i,j);
							}
							if(this.chessboard[i-2][j]==0&&(chessboard[i-1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i-2,j);
								int index1=index;
								if(hop(i-2,j)) {
									jump(i-2,j,index1);
								}
								jumpBackUp(i-2,j,i,j);
							}
						}else {//j=2~11
							if(this.chessboard[i][j-2]==0&&(chessboard[i][j-1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i,j-2);
								if(hop(i,j-2)) {
									jump(i,j-2,index1);
								}
								jumpBackUp(i,j-2,i,j);
							}
							if(this.chessboard[i-2][j]==0&&(chessboard[i-1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i-2,j);
								if(hop(i-2,j)) {
									jump(i-2,j,index1);
								}
								jumpBackUp(i-2,j,i,j);
							}
							if(this.chessboard[i][j+2]==0&&(chessboard[i][j+1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j+2);
								int index1=index;
								if(hop(i,j+2)) {
									jump(i,j+2,index1);
								}
								jumpBackUp(i,j+2,i,j);
							}
						}
					}else {//i=2~11
						if(j-2<0) {//j=0,1
							if(this.chessboard[i][j+2]==0&&(chessboard[i][j+1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j+2);
								if(hop(i,j+2)) {
									jump(i,j+2,index);
								}
								jumpBackUp(i,j+2,i,j);
							}
							if(this.chessboard[i-2][j]==0&&(chessboard[i-1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i-2,j);
								int index1=index;
								if(hop(i-2,j)) {
									jump(i-2,j,index1);
								}
								jumpBackUp(i-2,j,i,j);
							}
							if(this.chessboard[i+2][j]==0&&(chessboard[i+1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i+2,j);
								if(hop(i+2,j)) {
									jump(i+2,j,index1);
								}
								jumpBackUp(i+2,j,i,j);
							}
						}else if(j+2>13) {//j=12,13
							if(this.chessboard[i][j-2]==0&&(chessboard[i][j-1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-2};
								li.add(xy1);
								li.add(xy2);
								
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j-2);
								int index1=index;
								if(hop(i,j-2)) {
									jump(i,j-2,index1);
								}
								jumpBackUp(i,j-2,i,j);
							}
							if(this.chessboard[i-2][j]==0&&(chessboard[i-1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i-2,j);
								if(hop(i-2,j)) {
									jump(i-2,j,index1);
								}
								jumpBackUp(i-2,j,i,j);
							}
							if(this.chessboard[i+2][j]==0&&(chessboard[i+1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i+2,j);
								int index1=index;
								if(hop(i+2,j)) {
									jump(i+2,j,index1);
								}
								jumpBackUp(i+2,j,i,j);
							}
						}else {//j=2~11
							if(this.chessboard[i][j-2]==0&&(chessboard[i][j-1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j-2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								int index1=index;
								this.move(i,j,i,j-2);
								if(hop(i,j-2)) {
									jump(i,j-2,index1);
								}
								jumpBackUp(i,j-2,i,j);
							}
							if(this.chessboard[i-2][j]==0&&(chessboard[i-1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i-2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i-2,j);
								int index1=index;
								if(hop(i-2,j)) {
									jump(i-2,j,index1);
								}
								jumpBackUp(i-2,j,i,j);
							}
							if(this.chessboard[i][j+2]==0&&(chessboard[i][j+1]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i,j+2};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i,j+2);
								int index1=index;
								if(hop(i,j+2)) {
									jump(i,j+2,index1);
								}
								jumpBackUp(i,j+2,i,j);
							}
							if(this.chessboard[i+2][j]==0&&(chessboard[i+1][j]+chessboard[i][j]==3)) {
								LinkedList<int[]> li=new LinkedList<int[]>();
								int xy1[]= {i,j};
								int xy2[]= {i+2,j};
								li.add(xy1);
								li.add(xy2);
								index++;
								moveList[index]=new Array1(li);
								moveList[index].setType(Array1.JUMP);
								this.move(i,j,i+2,j);
								int index1=index;
								if(hop(i+2,j)) {
									jump(i+2,j,index1);
								}
								jumpBackUp(i+2,j,i,j);
							}
						}
					}
				}
			}
		}
	}
	
	private void jumpBackUp(int i, int j, int i2, int j2) {
		this.chessboard[i][j]=0;
		this.chessboard[i2][j2]=this.color;
		this.chessboard[(i+i2)/2][(j+j2)/2]=this.getAnotherColor();
	}

	public void jump(int x1,int y1,int n) {
		if(x1-2<0) {
			if(y1-2<0) {
				if(this.chessboard[x1][y1+2]==0&&(chessboard[x1][y1+1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1+2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1+2);
					if(hop(x1,y1+2)) {
						jump(x1,y1+2,index1);
					}
					jumpBackUp(x1,y1+2,x1,y1);
				}
				if(this.chessboard[x1+2][y1]==0&&(chessboard[x1+1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1+2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1+2,y1);
					if(hop(x1+2,y1)) {
						jump(x1+2,y1,index1);
					}
					jumpBackUp(x1+2,y1,x1,y1);
				}
			}else if(y1+2>13) {
				if(this.chessboard[x1][y1-2]==0&&(chessboard[x1][y1-1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1-2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1-2);
					if(hop(x1,y1-2)) {
						jump(x1,y1-2,index1);
					}
					jumpBackUp(x1,y1-2,x1,y1);
				}
				if(this.chessboard[x1+2][y1]==0&&(chessboard[x1+1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1+2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1+2,y1);
					if(hop(x1+2,y1)) {
						jump(x1+2,y1,index1);
					}
					jumpBackUp(x1+2,y1,x1,y1);
				}
			}else {
				if(this.chessboard[x1][y1-2]==0&&(chessboard[x1][y1-1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1-2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1-2);
					if(hop(x1,y1-2)) {
						jump(x1,y1-2,index1);
					}
					jumpBackUp(x1,y1-2,x1,y1);
				}
				if(this.chessboard[x1+2][y1]==0&&(chessboard[x1+1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1+2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1+2,y1);
					if(hop(x1+2,y1)) {
						jump(x1+2,y1,index1);
					}
					jumpBackUp(x1+2,y1,x1,y1);
				}
				if(this.chessboard[x1][y1+2]==0&&(chessboard[x1][y1+1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1+2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1= index;
					this.move(x1,y1,x1,y1+2);
					if(hop(x1,y1+2)) {
						jump(x1,y1+2,index1);
					}
					jumpBackUp(x1,y1+2,x1,y1);
				}
			}
		}else if(x1+2>13) {
			if(y1-2<0) {
				if(this.chessboard[x1][y1+2]==0&&(chessboard[x1][y1+1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1+2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					this.move(x1,y1,x1,y1+2);
					int index1=index;
					if(hop(x1,y1+2)) {
						jump(x1,y1+2,index1);
					}
					jumpBackUp(x1,y1+2,x1,y1);
				}
				if(this.chessboard[x1-2][y1]==0&&(chessboard[x1-1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1-2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1-2,y1);
					if(hop(x1-2,y1)) {
						jump(x1-2,y1,index1);
					}
					jumpBackUp(x1-2,y1,x1,y1);
				}
			}else if(y1+2>13) {
				if(this.chessboard[x1][y1-2]==0&&(chessboard[x1][y1-1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1-2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1-2);
					if(hop(x1,y1-2)) {
						jump(x1,y1-2,index1);
					}
					jumpBackUp(x1,y1-2,x1,y1);
				}
				if(this.chessboard[x1-2][y1]==0&&(chessboard[x1-1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1-2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1-2,y1);
					if(hop(x1-2,y1)) {
						jump(x1-2,y1,index1);
					}
					jumpBackUp(x1-2,y1,x1,y1);
				}
			}else {
				if(this.chessboard[x1][y1-2]==0&&(chessboard[x1][y1-1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1-2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1-2);
					if(hop(x1,y1-2)) {
						jump(x1,y1-2,index1);
					}
					jumpBackUp(x1,y1-2,x1,y1);
				}
				if(this.chessboard[x1-2][y1]==0&&(chessboard[x1-1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1-2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1-2,y1);
					if(hop(x1-2,y1)) {
						jump(x1-2,y1,index1);
					}
					jumpBackUp(x1-2,y1,x1,y1);
				}
				if(this.chessboard[x1][y1+2]==0&&(chessboard[x1][y1+1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1+2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1+2);
					if(hop(x1,y1+2)) {
						jump(x1,y1+2,index1);
					}
					jumpBackUp(x1,y1+2,x1,y1);
				}
			}
		}else {
			if(y1-2<0) {
				if(this.chessboard[x1][y1+2]==0&&(chessboard[x1][y1+1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1+2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					this.move(x1,y1,x1,y1+2);
					int index1=index;
					if(hop(x1,y1+2)) {
						jump(x1,y1+2,index1);
					}
					jumpBackUp(x1,y1+2,x1,y1);
				}
				if(this.chessboard[x1-2][y1]==0&&(chessboard[x1-1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1-2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1-2,y1);
					if(hop(x1-2,y1)) {
						jump(x1-2,y1,index1);
					}
					jumpBackUp(x1-2,y1,x1,y1);
				}
				if(this.chessboard[x1+2][y1]==0&&(chessboard[x1+1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1+2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1+2,y1);
					if(hop(x1+2,y1)) {
						jump(x1+2,y1,index1);
					}
					jumpBackUp(x1+2,y1,x1,y1);
				}
			}else if(y1+2>13) {
				if(this.chessboard[x1][y1-2]==0&&(chessboard[x1][y1-1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1-2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1-2);
					if(hop(x1,y1-2)) {
						jump(x1,y1-2,index1);
					}
					jumpBackUp(x1,y1-2,x1,y1);
				}
				if(this.chessboard[x1-2][y1]==0&&(chessboard[x1-1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1-2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					this.move(x1,y1,x1-2,y1);
					int index1=index;
					if(hop(x1-2,y1)) {
						jump(x1-2,y1,index1);
					}
					jumpBackUp(x1-2,y1,x1,y1);
				}
				if(this.chessboard[x1+2][y1]==0&&(chessboard[x1+1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1+2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1+2,y1);
					if(hop(x1+2,y1)) {
						jump(x1+2,y1,index1);
					}
					jumpBackUp(x1+2,y1,x1,y1);
				}
			}else {
				if(this.chessboard[x1][y1-2]==0&&(chessboard[x1][y1-1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1-2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1-2);
					if(hop(x1,y1-2)) {
						jump(x1,y1-2,index1);
					}
					jumpBackUp(x1,y1-2,x1,y1);
				}
				if(this.chessboard[x1-2][y1]==0&&(chessboard[x1-1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1-2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1-2,y1);
					if(hop(x1-2,y1)) {
						jump(x1-2,y1,index1);
					}
					jumpBackUp(x1-2,y1,x1,y1);
				}
				if(this.chessboard[x1][y1+2]==0&&(chessboard[x1][y1+1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1,y1+2};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1,y1+2);
					if(hop(x1,y1+2)) {
						jump(x1,y1+2,index1);
					}
					jumpBackUp(x1,y1+2,x1,y1);
				}
				if(this.chessboard[x1+2][y1]==0&&(chessboard[x1+1][y1]+chessboard[x1][y1]==3)) {
					index++;
					moveList[index]=new Array1(moveList[n].li);
					int xy1[]= {x1+2,y1};
					moveList[index].li.add(xy1);
					moveList[index].setType(Array1.JUMP);
					int index1=index;
					this.move(x1,y1,x1+2,y1);
					if(hop(x1+2,y1)) {
						jump(x1+2,y1,index1);
					}
					jumpBackUp(x1+2,y1,x1,y1);
				}
			}
		}
	}
	
	public boolean game_over() {
		int n=0;
		int m=0;
		for(int i=0;i<chessboard.length;i++) {
			for(int j=0;j<chessboard[i].length;j++) {
				if(chessboard[i][j]==2)
					n++;
				if(chessboard[i][j]==1)
					m++;
			}
		}
		if(n==0) {
			System.out.println("白方胜利");
			return true;
		}else if(m==0) {
			System.out.println("黑方胜利");
			return true;
		}else {
			return false;
		}
	}

	public int randomChizi(LinkedList<int[]> chizi,int color) {
		int value1=0;
		int n=0;
		int m=0;
		for(int i=0;i<chizi.size();i++) {
			int num1[]=this.Squarenum(this.color);
			LinkedList<dalian> l1=Girdle(this.chessboard, this.color);
			int num2[]=this.trinum(this.color);
			this.chessboard[chizi.get(i)[0]][chizi.get(i)[1]]=0;
			int num3[]=this.Squarenum(this.color);
			int num4[]=this.trinum(this.color);
			LinkedList<dalian> l2=Girdle(this.chessboard, this.color);
			for(int j=0;j<num1.length;j++) {
				if(num1[j]==1&&num3[j]==0) {
					n++;
				}
				if(num2[j]==1&&num4[j]==0) {
					m++;
				}
			}
			if(this.color==color) {
				if(l1.size()>l2.size()) {
					int j=0;
					int t=0;
					for(j=0;j<l1.size();j++) {
						if(chizi.get(i)[0]==l1.get(j).getEatpointX()&&chizi.get(i)[1]==l1.get(j).getEatpointY()){
							value1-=EATDALIANBEST;
							t++;
						}
					}
					if(j==l1.size()&&t==0) {
						value1-=EATDESTROYDALIAN*(l1.size()-l2.size());
					}
				}else if(m!=0) {
					value1-=EATTRIANGLE*m;
				}else if(n!=0){
					value1-=EATSQUARE*n;
				}else {
					value1-=EATCOMMON;
				}
			}else {
				if(l1.size()>l2.size()) {
					int j=0;
					int t=0;
					for(j=0;j<l1.size();j++) {
						if(chizi.get(i)[0]==l1.get(j).getEatpointX()&&chizi.get(i)[1]==l1.get(j).getEatpointY()){
							value1+=EATDALIANBEST;
							t++;
						}
					}
					if(j==l1.size()&&t==0) {
						value1+=EATDESTROYDALIAN*(l1.size()-l2.size());
					}
				}else if(m!=0) {
					value1+=EATTRIANGLE*m;
				}else if(n!=0){
					value1+=EATSQUARE*n;
				}else {
					value1+=EATCOMMON;
				}
			}
		}
		return value1;
	}
	
	public boolean isExistChiziList(int []xy) {
		for(int i=0;i<moveChi.size();i++) {
			if(xy[0]==moveChi.get(i)[0]&&xy[1]==moveChi.get(i)[1]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLessFourteen() {
		int k=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(this.color==this.chessboard[i][j]) {
					k++;
				}
			}
		}
		if(k<=14)
			return true;
		return false;
	}
	
	public boolean isLessThree(int color1) {
		int k=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(color1==this.chessboard[i][j]) {
					k++;
				}
			}
		}
		if(k<=3)
			return true;
		return false;
	}
	
	public boolean isLessFourteen(int color1) {
		int k=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(color1==this.chessboard[i][j]) {
					k++;
				}
			}
		}
		if(k<=14)
			return true;
		return false;
	}
	
	 
	public int evaluate(Array1 arr2,int color1) {
		if(arr2.type==Array1.MOVE) {
			LinkedList<int[]> li=arr2.li;
			Chess c1=new Chess(this.chessboard,this.getAnotherColor());
			c1.moveBack(li.get(1)[0],li.get(1)[1],li.get(0)[0],li.get(0)[1]);
			int n=0;// 记录新成方的个数
			int k=0;
			if(color1==c1.color) {
				LinkedList<dalian> l1=Girdle(c1.chessboard, c1.color);
				LinkedList<dalian> l2=Girdle(this.chessboard, c1.color);
				LinkedList<dalian> l3=Girdle(c1.chessboard, this.color);
				LinkedList<dalian> l4=Girdle(this.chessboard, this.color);
				if(l3.size()==l4.size()&&l3.size()!=0) {
					int i=0;
					for(;i<l3.size();i++) {
						if(l3.get(i).getMove()&&!l4.get(i).getMove()) {
							move+=MOVEDESTRORYDALIAN;
						}
					}
				}else if(l2.size()>l1.size()) {
					move+=MOVEDALIANFORM*(l2.size()-l1.size());
				}else if(l1.size()==l2.size()) {
					int i = 0;
					int t = 0;
					for (i = 0; i < l1.size(); i++) {
						if (li.get(0)[0] == l1.get(i).getEatpointX() && li.get(0)[1] == l1.get(i).getEatpointY()
								&& li.get(1)[0] == l1.get(i).getEndpointX()
								&& li.get(1)[1] == l1.get(i).getEndpointY()) {
							move += MOVEDALIANSCORE;
							t++;
						}
					}
					if (i == l1.size()&&t==0) {
						int[] m1 = c1.Squarenum(c1.color);
						int[] m2 = this.Squarenum(c1.color);
						int[] m3 = this.trinum(this.color);
						int[] m4 = c1.trinum(this.color);
						int m5 = c1.tribeTotal(c1.getColor());
						int m6 = this.tribeTotal(c1.getColor());
						int m7 = c1.trinum1(c1.color);
						int m8 = this.trinum1(c1.color);
						int m9 = c1.doubeTotal(c1.color);
						int m10 = this.doubeTotal(c1.color);
						for (int j = 0; j < m1.length; j++) {
							if (m1[j] == 0 && m2[j] == 1) {
								n++;
							}
						}
						for (int j = 0; j < m3.length; j++) {
							if (m4[i] == 1 && m3[i] == 2) {
								k++;
							}
						}
						if (n != 0) {
							move += n * MOVESQUARE;
						} else if (k != 0) {
							move += k * MOVEPRETRI;
						} else if (m8 - m7 > 0) {
							move += (m8 - m7) * MOVETRIANGLE;
						} else if (m6 - m5 > 0) {
							move += (m6 - m5) * MOVETRIBE;
						} else if (m10 - m9 > 0) {
							move += (m10 - m9) * MOVEDOUBE;
						}
					}

				}
				move+=MOVECOMMON;
				/* 判断褡裢的变化
				 * for i 0 to len
				 *     if(change)
				 *         m++
				 * if(m!=0)
				 *     move+=m*MOVEDALIAN
				 */
				
			}else {
				LinkedList<dalian> l1=Girdle(c1.chessboard, c1.color);
				LinkedList<dalian> l2=Girdle(this.chessboard, c1.color);
				LinkedList<dalian> l3=Girdle(c1.chessboard, this.color);
				LinkedList<dalian> l4=Girdle(this.chessboard, this.color);
				if(l3.size()==l4.size()&&l3.size()!=0) {
					int i=0;
					for(;i<l3.size();i++) {
						if(l3.get(i).getMove()&&!l4.get(i).getMove()) {
							move-=MOVEDESTRORYDALIAN;
						}
					}
				}else if(l2.size()>l1.size()) {
					move-=MOVEDALIANFORM*(l2.size()-l1.size());
				}else if(l1.size()==l2.size()) {
					int i = 0;
					int t = 0;
					for (i = 0; i < l1.size(); i++) {
						if (li.get(0)[0] == l1.get(i).getEatpointX() && li.get(0)[1] == l1.get(i).getEatpointY()
								&& li.get(1)[0] == l1.get(i).getEndpointX()
								&& li.get(1)[1] == l1.get(i).getEndpointY()) {
							move -= MOVEDALIANSCORE;
							t++;
						}
					}
					if (i == l1.size()&&t==0) {
						int[] m1 = c1.Squarenum(c1.color);
						int[] m2 = this.Squarenum(c1.color);
						int[] m3 = this.trinum(this.color);
						int[] m4 = c1.trinum(this.color);
						int m5 = c1.tribeTotal(c1.getColor());
						int m6 = this.tribeTotal(c1.getColor());
						int m7 = c1.trinum1(c1.color);
						int m8 = this.trinum1(c1.color);
						int m9 = c1.doubeTotal(c1.color);
						int m10 = this.doubeTotal(c1.color);
						for (int j = 0; j < m1.length; j++) {
							if (m1[j] == 0 && m2[j] == 1) {
								n++;
							}
						}
						for (int j = 0; j < m3.length; j++) {
							if (m4[i] == 1 && m3[i] == 2) {
								k++;
							}
						}
						if (n != 0) {
							move -= n * MOVESQUARE;
						} else if (k != 0) {
							move -= k * MOVEPRETRI;
						} else if (m8 - m7 > 0) {
							move -= (m8 - m7) * MOVETRIANGLE;
						} else if (m6 - m5 > 0) {
							move -= (m6 - m5) * MOVETRIBE;
						} else if (m10 - m9 > 0) {
							move -= (m10 - m9) * MOVEDOUBE;
						}
					}

				}
				move-=MOVECOMMON;
			}
  		}else if(arr2.type==Array1.JUMP){
			LinkedList<int[]>li=arr2.li;
			Chess c1=new Chess(this.chessboard,this.getAnotherColor());
			for(int i=li.size()-1;i>0;i--) {
				c1.moveBack(li.get(i)[0],li.get(i)[1],li.get(i-1)[0],li.get(i-1)[1]);
			}
			int n=0;
			int k=0;
			if(color1==c1.color) {
				LinkedList<dalian> l1=Girdle(c1.chessboard, c1.color);
				LinkedList<dalian> l2=Girdle(this.chessboard, c1.color);
				LinkedList<dalian> l3=Girdle(c1.chessboard, this.color);
				LinkedList<dalian> l4=Girdle(this.chessboard, this.color);
				if (l3.size() > l4.size()) {
					kill += JUMPDESTRORYDALIAN2*(l3.size()-l4.size());
				}else if(l3.size()==l4.size()&&l3.size()!=0) {
					int i=0;
					for(;i<l3.size();i++) {
						if (l3.get(i).getMove() && !l4.get(i).getMove()) {
							kill += JUMPDESTRORYDALIAN1;
						}
					}
				}else if (l2.size() > l1.size()) {
					kill += JUMPDALIANFORM*(l2.size()-l1.size());
				}else if (l1.size()==l2.size()) {
					int[] m1 = c1.Squarenum(c1.color);
					int[] m2 = this.Squarenum(c1.color);
					int[] m3 = this.trinum(this.color);
					int[] m4 = c1.trinum(this.color);
					int m5 = c1.tribeTotal(c1.getColor());
					int m6 = this.tribeTotal(c1.getColor());
					int m7 = c1.trinum1(c1.color);
					int m8 = this.trinum1(c1.color);
					int m9 = c1.doubeTotal(c1.color);
					int m10 = this.doubeTotal(c1.color);
					for (int j = 0; j < m1.length; j++) {
						if (m1[j] == 0 && m2[j] == 1) {
							n++;
						}
					}
					for (int j = 0; j < m3.length; j++) {
						if (m4[j] == 1 && m3[j] == 2) {
							k++;
						}
					}
					if (n != 0) {
						kill += n * JUMPSQUARE;
					} else if (k != 0) {
						kill += k * JUMPPRETRI;
					} else if (m8 - m7 > 0) {
						kill += (m8 - m7) * JUMPTRIANGLE;
					} else if (m6 - m5 > 0) {
						kill += (m6 - m5) * JUMPTRIBE;
					} else if (m10 - m9 > 0) {
						kill += (m10 - m9) * JUMPDOUBE;
					}
				}
				kill += JUMPCOMMON*(li.size()-1);
			}else {
				LinkedList<dalian> l1=Girdle(c1.chessboard, c1.color);
				LinkedList<dalian> l2=Girdle(this.chessboard, c1.color);
				LinkedList<dalian> l3=Girdle(c1.chessboard, this.color);
				LinkedList<dalian> l4=Girdle(this.chessboard, this.color);
				if (l3.size() > l4.size()) {
					kill -= JUMPDESTRORYDALIAN2*(l3.size()-l4.size());
				}else if(l3.size()==l4.size()&&l3.size()!=0) {
					int i=0;
					for(;i<l3.size();i++) {
						if (l3.get(i).getMove() && !l4.get(i).getMove()) {
							kill -= JUMPDESTRORYDALIAN1;
						}
					}
				}else if (l2.size() > l1.size()) {
					kill -= JUMPDALIANFORM*(l2.size()-l1.size());
				}else if (l1.size()==l2.size()) {
					int[] m1 = c1.Squarenum(c1.color);
					int[] m2 = this.Squarenum(c1.color);
					int[] m3 = this.trinum(this.color);
					int[] m4 = c1.trinum(this.color);
					int m5 = c1.tribeTotal(c1.getColor());
					int m6 = this.tribeTotal(c1.getColor());
					int m7 = c1.trinum1(c1.color);
					int m8 = this.trinum1(c1.color);
					int m9 = c1.doubeTotal(c1.color);
					int m10 = this.doubeTotal(c1.color);
					for (int j = 0; j < m1.length; j++) {
						if (m1[j] == 0 && m2[j] == 1) {
							n++;
						}
					}
					for (int j = 0; j < m3.length; j++) {
						if (m4[j] == 1 && m3[j] == 2) {
							k++;
						}
					}
					if (n != 0) {
						kill -= n * JUMPSQUARE;
					} else if (k != 0) {
						kill -= k * JUMPPRETRI;
					} else if (m8 - m7 > 0) {
						kill -= (m8 - m7) * JUMPTRIANGLE;
					} else if (m6 - m5 > 0) {
						kill -= (m6 - m5) * JUMPTRIBE;
					} else if (m10 - m9 > 0) {
						kill -= (m10 - m9) * JUMPDOUBE;
					}
				}
				kill -= JUMPCOMMON*(li.size()-1);
			}
		}else if(arr2.type==Array1.FLY){
			LinkedList<int[]> li=arr2.li;
			Chess c1=new Chess(this.chessboard,this.getAnotherColor());
			c1.moveBack1(li.get(1)[0],li.get(1)[1],li.get(0)[0],li.get(0)[1]);
			int n=0;// 记录新成方的个数
			int k=0;
			if(color1==c1.color) {
				LinkedList<dalian> l1=Girdle(c1.chessboard, c1.color);
				LinkedList<dalian> l2=Girdle(this.chessboard, c1.color);
				LinkedList<dalian> l3=Girdle(c1.chessboard, this.color);
				LinkedList<dalian> l4=Girdle(this.chessboard, this.color);
				if(l3.size()==l4.size()&&l3.size()!=0) {
					int i=0;
					for(;i<l3.size();i++) {
						if(l3.get(i).getMove()&&!l4.get(i).getMove()) {
							fly+=FLYDESTRORYDALIAN;
						}
					}
				}else if(l2.size()>l1.size()) {
					fly+=FLYDALIANFORM*(l2.size()-l1.size());
				}else if(l1.size()==l2.size()) {
					int i = 0;
					int t = 0;
					for (i = 0; i < l1.size(); i++) {
						if (li.get(0)[0] == l1.get(i).getEatpointX() && li.get(0)[1] == l1.get(i).getEatpointY()
								&& li.get(1)[0] == l1.get(i).getEndpointX()
								&& li.get(1)[1] == l1.get(i).getEndpointY()) {
							fly += FLYDALIANSCORE;
							t++;
						}
					}
					if (i == l1.size()&&t==0) {
						int[] m1 = c1.Squarenum(c1.color);
						int[] m2 = this.Squarenum(c1.color);
						int[] m3 = this.trinum(this.color);
						int[] m4 = c1.trinum(this.color);
						int m5 = c1.tribeTotal(c1.getColor());
						int m6 = this.tribeTotal(c1.getColor());
						int m7 = c1.trinum1(c1.color);
						int m8 = this.trinum1(c1.color);
						int m9 = c1.doubeTotal(c1.color);
						int m10 = this.doubeTotal(c1.color);
						for (int j = 0; j < m1.length; j++) {
							if (m1[j] == 0 && m2[j] == 1) {
								n++;
							}
						}
						for (int j = 0; j < m3.length; j++) {
							if (m4[i] == 1 && m3[i] == 2) {
								k++;
							}
						}
						if (n != 0) {
							fly += n * FLYSQUARE;
						} else if (k != 0) {
							fly += k * FLYPRETRI;
						} else if (m8 - m7 > 0) {
							fly += (m8 - m7) * FLYTRIANGLE;
						} else if (m6 - m5 > 0) {
							fly += (m6 - m5) * FLYTRIBE;
						} else if (m10 - m9 > 0) {
							fly += (m10 - m9) * FLYDOUBE;
						}
					}
				}
				fly+=FLYCOMMON;
			}else {
				LinkedList<dalian> l1=Girdle(c1.chessboard, c1.color);
				LinkedList<dalian> l2=Girdle(this.chessboard, c1.color);
				LinkedList<dalian> l3=Girdle(c1.chessboard, this.color);
				LinkedList<dalian> l4=Girdle(this.chessboard, this.color);
				if(l3.size()==l4.size()&&l3.size()!=0) {
					int i=0;
					for(;i<l3.size();i++) {
						if(l3.get(i).getMove()&&!l4.get(i).getMove()) {
							fly-=FLYDESTRORYDALIAN;
						}
					}
				}else if(l2.size()>l1.size()) {
					fly-=FLYDALIANFORM*(l2.size()-l1.size());
				}else if(l1.size()==l2.size()) {
					int i = 0;
					int t = 0;
					for (i = 0; i < l1.size(); i++) {
						if (li.get(0)[0] == l1.get(i).getEatpointX() && li.get(0)[1] == l1.get(i).getEatpointY()
								&& li.get(1)[0] == l1.get(i).getEndpointX()
								&& li.get(1)[1] == l1.get(i).getEndpointY()) {
							fly -= FLYDALIANSCORE;
							t++;
						}
					}
					if (i == l1.size()&&t==0) {
						int[] m1 = c1.Squarenum(c1.color);
						int[] m2 = this.Squarenum(c1.color);
						int[] m3 = this.trinum(this.color);
						int[] m4 = c1.trinum(this.color);
						int m5 = c1.tribeTotal(c1.getColor());
						int m6 = this.tribeTotal(c1.getColor());
						int m7 = c1.trinum1(c1.color);
						int m8 = this.trinum1(c1.color);
						int m9 = c1.doubeTotal(c1.color);
						int m10 = this.doubeTotal(c1.color);
						for (int j = 0; j < m1.length; j++) {
							if (m1[j] == 0 && m2[j] == 1) {
								n++;
							}
						}
						for (int j = 0; j < m3.length; j++) {
							if (m4[i] == 1 && m3[i] == 2) {
								k++;
							}
						}
						if (n != 0) {
							fly -= n * FLYSQUARE;
						} else if (k != 0) {
							fly -= k * FLYPRETRI;
						} else if (m8 - m7 > 0) {
							fly -= (m8 - m7) * FLYTRIANGLE;
						} else if (m6 - m5 > 0) {
							fly -= (m6 - m5) * FLYTRIBE;
						} else if (m10 - m9 > 0) {
							fly -= (m10 - m9) * FLYDOUBE;
						}
					}
				}
				fly-=FLYCOMMON;
			}
		}
		value=move+kill+fly;
		return value;
	}
    public int getValue() {
		return value;
	}
    
    private static int[][] swap(int[][] a) {
		int len1 = a.length;
		int len2 = a[0].length;
		int[][] b = new int[len2][len1];
		for(int i = 0;i<len2;i++) {
			for(int j=0;j<len1;j++) {
				b[i][j]=a[j][i];
			}
		}
		return b;
	} 
	
	private static LinkedList<dalian> Girdle(int[][] a, int color) {
		LinkedList<dalian> list = new LinkedList<dalian>();
		int count = 0;
		for(int i = 0;i<a.length-2;i++) {
			for(int j = 0 ;j<a.length-3;j++) {
				int[][] dl = new int[3][4] ;
				int p = i;
				for(int m = 0;m<=2;m++,p++) {
					int q = j;
					for(int n = 0;n<=3;n++,q++) {
						dl[m][n]=a[p][q];
					}
				}
				dalian d = new dalian(dl,color);
                d.count(); 
                d.setStandpointX(i);
                d.setStandpointY(j);
                d.couteatPoint();
                d.setDirection(1);
                if(d.getNum()>0) {
                	list.add(count, d);
                	count++;
                }
			}
		}
		
		int[][] a1 = swap(a);
		for(int i = 0;i<a1.length-2;i++) {
			for(int j = 0 ;j<a1.length-3;j++) {
				int[][] dl = new int[3][4] ;
				int p = i;
				for(int m = 0;m<=2;m++,p++) {
					int q = j;
					for(int n = 0;n<=3;n++,q++) {
						dl[m][n]=a1[p][q];
					}
				}
				dalian d = new dalian(dl,color);
                d.count(); 
                d.setStandpointX(i);
                d.setStandpointY(j);
                d.setDirection(2);
                d.couteatPoint();
                d.countXY();
                if(d.getNum()>0) {
                	list.add(count, d);
                	count++;
                }
			}
		}
		return list;
	}  
	
	public int tribeTotal(int color1) {
		int totalTribe=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(chessboard[i][j]==color1) {
					int firstColor=chessboard[i][j];
					if(i+1>13||i+2>13) {
						if(j+2<14) {
							if((chessboard[i][j+1]==firstColor&&chessboard[i][j+2]==firstColor)) {
								totalTribe++;
							}
						}
					}else {
						if(j+2>13||j+1>13) {
							if(chessboard[i+1][j]==firstColor&&chessboard[i+2][j]==firstColor) {
								totalTribe++;
							}
						}else {
							if(chessboard[i][j+1]==firstColor&&chessboard[i][j+2]==firstColor) {
								totalTribe++;
							}
							if(chessboard[i+1][j]==firstColor&&chessboard[i+2][j]==firstColor) {
								totalTribe++;
							}
						}
					}
				}
			}
		}
		return totalTribe;
	}

	public boolean game_over2(int color) {
		int n=0;
		int m=0;
		for(int i=0;i<chessboard.length;i++) {
			for(int j=0;j<chessboard[i].length;j++) {
				if(chessboard[i][j]==2)
					n++;
				if(chessboard[i][j]==1)
					m++;
			}
		}
		if(n==0) {
			if(color==2) {
				gameOverScore=-9999;
				return true;
			}else {
				gameOverScore=9999;
				return true;
			}
		}else if(m==0) {
			if(color==1) {
				gameOverScore=-9999;
				return true;
			}else {
				gameOverScore=9999;
				return true;
			}
		}else {
			gameOverScore=0;
			return false;
		}
	}
}
