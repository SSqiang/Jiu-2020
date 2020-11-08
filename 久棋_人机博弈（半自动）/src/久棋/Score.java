package 久棋;

public class Score {
	private int chessboard[][];
	public int rows;
	public static final int SQUARE=600;//成方棋形分值
	public static final int TRIANGLE=1200;//三角棋形分值
	public static final int CONTRAST=800;//对角棋形分值
	public static final int TRIBE=800; //三连子棋形分值
	public static final int DOUBE=500; //二连子棋形分值
	
	public static final int PreTriangle=2000; //阻止对方三角成方棋形分值
	public static final int PreTribe=1800; //阻止对方三连子成双三角棋形分值
	public static final int PreContrast=800; //阻止对方对角成三角棋形分值
	public static final int PreDoube1=1400; //阻止对方二连子成三角棋形分值
	public static final int PreDoube2=1500; //阻止对方二连子成三连子棋形分值
	
	private int totalScore=0;
	public int color=0;
	public int anotherColor=0;
	public Score(int chess[][],int color) {
		rows=chess.length;
		chessboard=new int[rows][rows];
		for(int i=0;i<chess.length;i++) {
			for(int j=0;j<chess[i].length;j++) {
				chessboard[i][j]=chess[i][j];
			}
		}
		this.color=color;
		this.anotherColor=getAnotherColor();
	}
	
	private int getAnotherColor() {
		if(this.color==1) {
			return 2;
		}else
			return 1;
	}
	
	public int squareTotal() {
		int totalSquare = 0;
		for(int i=0; i<rows-1; i++) {
			for(int j=0;j<rows-1; j++) {
				if(chessboard[i][j] ==this.color) {
					int firstColor = chessboard[i][j];
					if(chessboard[i][j+1]==firstColor&&chessboard[i+1][j+1]==firstColor&&chessboard[i+1][j]==firstColor) {
						totalSquare++;
					}
				}
			}
		}
		return totalSquare;
	}
	
	public int getTotalScore() {
		getScore();
		totalScore+=tribeNum()*PreTribe;
		//totalScore= squareTotal()*SQUARE+triangleTotal()*TRIANGLE+tribeTotal()*TRIBE+doubeTotal()*DOUBE+CONTRAST*contrastTotal()
		//+trinum()*PreTriangle+tribeNum()*PreTribe+preContrast()*PreContrast+doubenum1()*PreDoube1+doubenum2()*PreDoube2;
		return totalScore;
	}
	
	public int doubenum1() {
		int totalDoube=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.anotherColor) {
					int firstColor=chessboard[i][j];
					if(i+1>13) {
						if(j+1<14) {
							if(chessboard[i][j+1]==firstColor) {
								if(chessboard[i-1][j]==this.color)
									totalDoube++;
								if(chessboard[i-1][j+1]==this.color)
									totalDoube++;
							}
						}
					}else {
						if(j+1>13) {
							if(chessboard[i+1][j]==firstColor) {
								if(chessboard[i][j-1]==this.color)
									totalDoube++;
								if(chessboard[i+1][j-1]==this.color)
									totalDoube++;
							}
						}else {
							if(chessboard[i][j+1]==firstColor) {
								if(i-1>=0) {
									if(chessboard[i-1][j]==this.color)
										totalDoube++;
									if(chessboard[i-1][j+1]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==this.color)
									totalDoube++;
								if(chessboard[i+1][j+1]==this.color)
									totalDoube++;
							}
							if(chessboard[i+1][j]==firstColor) {
								if(j-1>=0) {
									if(chessboard[i+1][j-1]==this.color)
										totalDoube++;
									if(chessboard[i][j-1]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j+1]==this.color)
									totalDoube++;
								if(chessboard[i][j+1]==this.color)
									totalDoube++;
							}
						}
					}
				}
			}
		}
		return totalDoube;
	}
	
	public int doubenum2() {
		int totalDoube=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.anotherColor) {
					if(chessboard[i][j]==this.anotherColor) {
						int firstColor=chessboard[i][j];
						if(i==13) {
							if(j+1==13) {
								if(chessboard[i][j+1]==firstColor) {
									if(chessboard[i][j-1]==this.color)
										totalDoube++;
								}
							}else if(j+1<13){
								if(chessboard[i][j+1]==firstColor) {
									if(j-1>=0) {
										if(chessboard[i][j-1]==this.color)
											totalDoube++;
									}
									if(chessboard[i][j+2]==this.color)
										totalDoube++;
								}
							}
						}else if(i==0) {
							if(j+1==13) {
								if(chessboard[i][j+1]==firstColor) {
									if(chessboard[i][j-1]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==firstColor) {
									if(chessboard[i+2][j]==this.color)
										totalDoube++;
								}
							}else if(j+1<13){
								if(chessboard[i][j+1]==firstColor) {
									if(j-1>=0) {
										if(chessboard[i][j-1]==this.color)
											totalDoube++;
									}
									if(chessboard[i][j+2]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==firstColor) {
									if(chessboard[i+2][j]==this.color)
										totalDoube++;
								}
							}
						}else if(i==12) {
							if(j+1==13) {//j==12
								if(chessboard[i][j+1]==firstColor) {
									if(chessboard[i][j-1]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==firstColor) {
									if(chessboard[i-1][j]==this.color)
										totalDoube++;
								}
							}else if(j+1<13){//0~11
								if(chessboard[i][j+1]==firstColor) {
									if(j-1>=0) {
										if(chessboard[i][j-1]==this.color)
											totalDoube++;
									}
									if(chessboard[i][j+2]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==firstColor) {
									if(chessboard[i-1][j]==this.color)
										totalDoube++;
								}
							}
						}else{
							if(j+1==13) {//j==12
								if(chessboard[i][j+1]==firstColor) {
									if(chessboard[i][j-1]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==firstColor) {
									if(chessboard[i-1][j]==this.color)
										totalDoube++;
									if(chessboard[i+2][j]==this.color)
										totalDoube++;
								}
							}else if(j+1<13){//0~11
								if(chessboard[i][j+1]==firstColor) {
									if(j-1>=0) {
										if(chessboard[i][j-1]==this.color)
											totalDoube++;
									}
									if(chessboard[i][j+2]==this.color)
										totalDoube++;
								}
								if(chessboard[i+1][j]==firstColor) {
									if(chessboard[i+2][j]==this.color)
										totalDoube++;
									if(chessboard[i-1][j]==this.color)
										totalDoube++;
								}
							}
						}
					}
				}
			}
		}
		return totalDoube;
	}
	
	public int triangleTotal() {
		int totalTriangle=0;
		for(int i=0;i<rows-1;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.color) {
					int firstColor = chessboard[i][j];
					if(j-1<0) {
						if(chessboard[i+1][j+1]==firstColor) {
							if (chessboard[i][j + 1] == firstColor)
								totalTriangle++;
							if (chessboard[i + 1][j] == firstColor)
								totalTriangle++;
						}
					}else if(j+1>13) {
						if(chessboard[i+1][j-1]==firstColor) {
							if (chessboard[i][j - 1] == firstColor)
								totalTriangle++;
							if (chessboard[i + 1][j] == firstColor)
								totalTriangle++;
						}
					}else {
						if(chessboard[i+1][j-1]==firstColor) {
							if (chessboard[i][j - 1] == firstColor)
								totalTriangle++;
							if (chessboard[i + 1][j] == firstColor)
								totalTriangle++;
						}
						if(chessboard[i+1][j+1]==firstColor) {
							if (chessboard[i + 1][j] == firstColor)
								totalTriangle++;
							if (chessboard[i][j + 1] == firstColor)
								totalTriangle++;
						}
					}
				}
			}
		}
		return totalTriangle;
	}
	
	public int trinum() {
		int pretri[]=new int[169];
		int n = 0;
		int m = 0;
		int k = 0;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				if (chessboard[i][j] == this.anotherColor)
					n++;
				if (chessboard[i][j + 1] == this.anotherColor)
					n++;
				if (chessboard[i + 1][j] == this.anotherColor)
					n++;
				if (chessboard[i + 1][j + 1] == this.anotherColor)
					n++;
				if (chessboard[i][j] == this.color)
					k++;
				if (chessboard[i][j + 1] == this.color)
					k++;
				if (chessboard[i + 1][j] == this.color)
					k++;
				if (chessboard[i + 1][j + 1] == this.color)
					k++;
				if(n==3&&k==1) {
					pretri[m]=1;
					m++;
				}else{
					pretri[m]=0;
					m++;
				}
				n=0;
				k=0;
			}
		}
		int q=0;
		for(int i=0;i<pretri.length;i++) {
			if(pretri[i]==1) {
				q++;
			}
		}
		return q;
	}
	
	public int tribeNum() {
		int totalTribe=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.anotherColor) {
					int firstColor=chessboard[i][j];
					if(i+1>13) {
						if(j+2<14) {
							if(chessboard[i][j+1]==firstColor&&chessboard[i][j+2]==firstColor) {
								if(chessboard[i-1][j+1]==this.color)
									totalTribe++;
							}
						}
					}else if(i+2>13){
						if(j+2<14) {
							if(chessboard[i][j+1]==firstColor&&chessboard[i][j+2]==firstColor) {
								if(chessboard[i-1][j+1]==this.color)
									totalTribe++;
								if(chessboard[i+1][j+1]==this.color)
									totalTribe++;
							}
						}
					}else {
						if(j+2>13) {
							if(chessboard[i+1][j]==firstColor&&chessboard[i+2][j]==firstColor) {
								if(chessboard[i+1][j-1]==this.color)
									totalTribe++;
							}
						}else if(j+1>13) {
							if(chessboard[i+1][j]==firstColor&&chessboard[i+2][j]==firstColor) {
								if(chessboard[i+1][j-1]==this.color)
									totalTribe++;
								if(chessboard[i+1][j+1]==this.color)
									totalTribe++;
							}
						}else {
							if(chessboard[i][j+1]==firstColor&&chessboard[i][j+2]==firstColor) {
								if(chessboard[i+1][j+1]==this.color)
									totalTribe++;
								if(i-1>=0) {
									if(chessboard[i-1][j+1]==this.color)
										totalTribe++;
								}
							}
							if(chessboard[i+1][j]==firstColor&&chessboard[i+2][j]==firstColor) {
								if(j-1>=0) {
									if(chessboard[i+1][j-1]==this.color)
										totalTribe++;
								}
								if(chessboard[i+1][j+1]==this.color)
									totalTribe++;
							}
						}
					}
				}
			}
		}
		return totalTribe;
	}
	
	public int preContrast() {
		int totalContrast=0;
		for(int i=0;i<rows-1;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.anotherColor) {
					int firstColor=chessboard[i][j];
					if(j-1<0) {
						if(chessboard[i+1][j+1]==firstColor) {
							
						}
					}else if(j+1>13) {
						if(chessboard[i+1][j-1]==firstColor) {
							if(chessboard[i+1][j]==this.color)
								totalContrast++;
							if(chessboard[i][j-1]==this.color)
								totalContrast++;
						}
					}else {
						if(chessboard[i+1][j-1]==firstColor) {
							if(chessboard[i+1][j]==this.color)
								totalContrast++;
							if(chessboard[i][j+1]==this.color)
								totalContrast++;
						}
						if(chessboard[i+1][j+1]==firstColor) {
							if(chessboard[i+1][j]==this.color)
								totalContrast++;
							if(chessboard[i][j+1]==this.color)
								totalContrast++;
						}
					}
				}
			}
		}
		return totalContrast;
	}
	
	public int tribeTotal() {
		int totalTribe=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.color) {
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
	public int doubeTotal() {
		int totalDoube=0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.color) {
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
	
	public int contrastTotal() {
		int totalContrast=0;
		for(int i=0;i<rows-1;i++) {
			for(int j=0;j<rows;j++) {
				if(chessboard[i][j]==this.color) {
					int firstColor=chessboard[i][j];
					if(j-1<0) {
						if(chessboard[i+1][j+1]==firstColor) {
							totalContrast++;
						}
					}else if(j+1>13) {
						if(chessboard[i+1][j-1]==firstColor) {
							totalContrast++;
						}
					}else {
						if(chessboard[i+1][j-1]==firstColor) {
							totalContrast++;
						}
						if(chessboard[i+1][j+1]==firstColor) {
							totalContrast++;
						}
					}
				}
			}
		}
		return totalContrast;
	}
	
	public void getScore() {
		int n=0;
		int m=0;
		for(int i=0;i<13;i++) {
			for(int j=0;j<13;j++) {
				if (chessboard[i][j] == this.anotherColor)
					n++;
				if (chessboard[i][j + 1] == this.anotherColor)
					n++;
				if (chessboard[i + 1][j] == this.anotherColor)
					n++;
				if (chessboard[i + 1][j + 1] == this.anotherColor)
					n++;
				if (chessboard[i][j] == this.color)
					m++;
				if (chessboard[i][j + 1] == this.color)
					m++;
				if (chessboard[i + 1][j] == this.color)
					m++;
				if (chessboard[i + 1][j + 1] == this.color)
					m++;
				if(m==2&&n==2) {
					if(this.chessboard[i][j+1]==this.color&&this.chessboard[i][j]==this.color) {
						if(j+2<14) {
							if(this.chessboard[i][j+2]==this.color) {
								totalScore+=TRIBE;
							}else {
								totalScore+=DOUBE;
							}
						}else {
							totalScore+=DOUBE;
						}
					}else if(this.chessboard[i+1][j]==this.color&&this.chessboard[i][j]==this.color) {
						if(i+2<14) {
							if(this.chessboard[i+2][j]==this.color) {
								totalScore+=TRIBE;
							}else {
								totalScore+=DOUBE;
							}
						}else {
							totalScore+=DOUBE;
						}
					}else if(this.chessboard[i+1][j+1]==this.color&&this.chessboard[i][j]==this.color) {
						totalScore+=CONTRAST;
						totalScore+=PreContrast;
					}else if(this.chessboard[i+1][j]==this.color&&this.chessboard[i][j+1]==this.color) {
						totalScore+=CONTRAST;
						totalScore+=PreContrast;
					}
	                if(j==12) {
	                	if(this.chessboard[i+1][j+1]==this.color&&this.chessboard[i][j+1]==this.color) {
	                		if(i+2<14) {
	                			if(this.chessboard[i+2][j]==this.color) {
									totalScore+=TRIBE;
								}else {
									totalScore+=DOUBE;
								}
	                		}else {
	                			totalScore+=DOUBE;
	                		}
	                	}
					}
	                if(i==12) {
	                	if(this.chessboard[i+1][j+1]==this.color&&this.chessboard[i+1][j]==this.color) {
	                		if(j+2<14) {
								if(this.chessboard[i][j+2]==this.color) {
									totalScore+=TRIBE;
								}else {
									totalScore+=DOUBE;
								}
							}else {
								totalScore+=DOUBE;
							}
	                	}
	                }
				}else if(m==3&&n==1) {
					totalScore+=TRIANGLE;
				}else if(m==4&&n==0) {
					totalScore+=SQUARE;
				}else if(n==3&&m==1) {
					totalScore+=PreTriangle;
				}
				n=0;
				m=0;
			}
		}
	}
}
