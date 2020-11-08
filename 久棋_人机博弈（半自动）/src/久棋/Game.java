package 久棋;
import java.util.*;
public class Game {
	private static int a[][]=new int[14][14]; //0无子，1为白子，2为黑子
	public static Chess[] chessState=new Chess[197];
	public static Chess[] chessState1=new Chess[300];
	public static final int BLACK = 2;
	public static final int NOCHESS = 0;
	public static final int WHITE = 1;
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		int first=1;
		int first1=1;
		int flag=0;
		int n=0;
		int player=0;
		chessState[n]=new Chess(a);
		System.out.println("现棋盘棋子分布为：");
		chessState[n].showBoard();
		System.out.println("请输入你是黑子还是白子（1表示白子，-1表示黑子）");
		flag= sc.nextInt();
		player=flag;
		while(true) {
			if(flag==1) {
				if(first==1) {
					System.out.println("目前为第"+(n+1)+"步,白棋下一步棋子为:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState[n].chessboard[x1][y1]==0) {
						a[x1][y1]=1;
						chessState[++n]=new Chess(a);
						first=2;
						flag=-1;
					}else {
						System.out.println("白棋下子出现错误");
					    continue;
					}
				}else {
					System.out.println("目前为第"+(n+1)+"步,白棋下一步棋子为:");
					if (player == WHITE) {
						MCTS m2 = new MCTS(chessState[n].chessboard);
						int xy[] = m2.getBestChild();
						System.out.println(xy[0] + "  " + xy[1]);
						int x1=xy[0];
						int y1=xy[1];
						a[x1][y1]=1;
					    chessState[++n]=new Chess(a);
					    flag=-1;
					}else {
						int x1=sc.nextInt();
						int y1=sc.nextInt();
						if(x1==-1&&y1==-1) {
							System.out.print("对方请求悔棋,悔棋的步数为:");
							int m=sc.nextInt();
							n-=m;
							if(n<0) {
								System.out.println("悔棋出现错误");
								n+=m;
								continue;
							}
							a=chessState[n].chessboard;
							if(n==1){
								flag=-1;
								first1=1;
								first=2;
								continue;
							}else if(n==0) {
								flag=1;
								first=1;
								continue;
							}
							if(n%2==0) {
								flag=1;
								continue;
							}else {
								flag=-1;
								continue;
							}
						}else if((x1<0||x1>13)||(y1<0||y1>13)) {
							System.out.println("白棋下子出现错误");
							continue;
						}else if(chessState[n].chessboard[x1][y1]!=0){
							System.out.println("白棋下子出现错误");
							continue;
						}else {
							a[x1][y1]=1;
						    chessState[++n]=new Chess(a);
						    flag=-1;
					    }
					}
				}	
			}else {
				if(first1==1&&first==1) {
					System.out.println("目前为第"+(n+1)+"步,对方下一步棋子为:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState[n].chessboard[x1][y1]==0) {
						a[x1][y1]=1;
						chessState[++n]=new Chess(a);
						first1=2;
					}else {
						System.out.println("白棋下子出现错误");
						continue;
					}
				}else if(first==2||(first==1&&first1==2)){
					System.out.println("目前为第"+(n+1)+"步,黑棋下一步棋子为:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState[n].chessboard[x1][y1]==0) {
						a[x1][y1]=2;
						chessState[++n]=new Chess(a);
					    first=3;
					    flag=1;
					}else {
						System.out.println("黑棋下子出现错误");
						continue;
					}
				}else {
					System.out.println("目前为第"+(n+1)+"步,黑棋下一步棋子为:");
					if(player==-1) {
						MCTS m1=new MCTS(chessState[n].chessboard);
						int xy[]=m1.getBestChild();
						System.out.println(xy[0]+"  "+xy[1]);
						int x1=xy[0];
						int y1=xy[1];
						a[x1][y1]=2;
					    chessState[++n]=new Chess(a);
					    flag=1;
					}else {
						int x1=sc.nextInt();
						int y1=sc.nextInt();
						if(x1==-1&&y1==-1) {
							System.out.print("对方请求悔棋,悔棋的步数为:");
							int m=sc.nextInt();
							n-=m;
							if(n<0) {
								System.out.println("悔棋出现错误");
								n+=m;
								continue;
							}
							a=chessState[n].chessboard;
							if(n==1){
								flag=-1;
								first1=1;
								first=2;
								continue;
							}else if(n==0) {
								flag=1;
								first=1;
								continue;
							}
							if(n%2==0) {
								flag=1;
								continue;
							}else {
								flag=-1;
								continue;
							}
						}else if((x1<0||x1>13)||(y1<0||y1>13)) {
							System.out.println("黑棋下子出现错误");
							continue;
						}else if(chessState[n].chessboard[x1][y1]!=0){
							System.out.println("黑棋下子出现错误");
							continue;
						}else {
							a[x1][y1]=2;
						    chessState[++n]=new Chess(a);
						    flag=1;
					    }
					}
				}
			}
			chessState[n].showBoard();
			showChessNumber(chessState[n]);
			//System.out.println();
			if(chessState[n].checkIsEmpty())
				break;
		}
		int m=0;
		flag=-1;
		first=1;
		first1=1;
		chessState1[m]=new Chess(chessState[n].chessboard);
		while(true) {
			if(flag==1) {
				if(first==1) {
					System.out.println("目前为第"+(m+1)+"步,白棋第一步行棋应为去掉中央对角线的白子:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState1[m].chessboard[x1][y1]==1) {
						a[x1][y1]=0;
						chessState1[++m]=new Chess(a,WHITE);
						chessState1[m].showBoard();
						showChessNumber(chessState1[m]);
						first=2;
						flag=-1;
					}else {
						System.out.println("白棋去子出现错误");
					    continue;
					}
				}else {
					System.out.println("目前为第"+(m+1)+"步,白棋下一步行棋为:");
					if(player==WHITE) {
						Chess c1=new Chess(a,WHITE);
						Alpha_beta AI =new Alpha_beta(c1);
						System.out.println("最佳可行的行棋方法为:");
						for(int i=0;i<AI.bestMove.size()-1;i++) {
							System.out.print("("+AI.bestMove.get(i)[0]+","+AI.bestMove.get(i)[1]+") -> ");
						}
						System.out.println("("+AI.bestMove.getLast()[0]+","+AI.bestMove.getLast()[1]+")");
						System.out.print("可吃的点为:");
						if(AI.bestChi.size()!=0) {
							for(int i=0;i<AI.bestChi.size();i++) {
								System.out.print("("+AI.bestChi.get(i)[0]+","+AI.bestChi.get(i)[1]+")   ");
							}
							System.out.println();
						}else {
							System.out.println("该步行棋无可吃点");
						}
						
						chessState1[++m]=new Chess(a,WHITE);
						if(lessThirty_FiveNumber(chessState1[m])) {
							int x1=sc.nextInt();
							int y1=sc.nextInt();
							int x2=sc.nextInt();
							int y2=sc.nextInt();
							int[] num1 = chessState1[m].Squarenum(WHITE);// 移动前的方格数目
						    chessState1[m].move(x1, y1, x2, y2);
						    int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							int oldx=x2;
						    int oldy=y2;//记录行棋后的横纵坐标，用于连续跳子的情况中
							if(Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) == 3&&chessState1[m].hop(x2, y2) == true) {//满足连续跳子的情况
								a=chessState1[m].chessboard;
								for(;;) {
									System.out.println("白棋可以继续跳子");
								    System.out.print("是否要继续跳子（1表示继续跳子,0表示终止跳子）");
								    int c;
								    c = sc.nextInt();//1表示继续，0表示终止
									if (c == 1) {
										int x3, y3, x4, y4;
										System.out.println("请继续输入黑子跳子行棋");
										x3 = sc.nextInt();
										y3 = sc.nextInt();
										x4 = sc.nextInt();
										y4 = sc.nextInt();
										int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
										if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//要满足跳子规则，同时每次连续跳子要移动的棋子只能是上一次跳子的目的位置的棋子
											chessState1[m].move(x3, y3, x4, y4);	
											a=chessState1[m].chessboard;
											oldx=x4;
											oldy=y4;//更新目的位置棋子的坐标，用于下一次连续跳子
											if (chessState1[m].hop(x4, y4) == true) {//如果可以连续跳子就继续循环
												continue;
											} else {
												break;
											}
										} else {
											System.out.println("白棋跳子行棋不规范");
											continue;
										}
									}else {
										break;
									}
								}
								System.out.println("跳子结束后棋盘为");
								chessState1[m].showBoard();
							}
							a=chessState1[m].chessboard;
							int[] num2 = chessState1[m].Squarenum(WHITE);// 移动后的方格数目
							int deletenum = 0;
							for (int i = 0; i < num1.length; i++) {
								if (num1[i] == 0 && num2[i] == 1) {
									deletenum++;// 新增加的方格数
								}
							}
							if (deletenum == 0) {
								flag=-1;
								System.out.println("该步行棋没有形成棋门,不可吃子");
								chessState1[m].showBoard();
								showChessNumber(chessState1[m]);
							} else {
								System.out.println("一共可以删除" + deletenum + "颗黑子");
								int black1, black2;
								for (int i = 0; i < deletenum;) {
									System.out.println("请选择要删除的第" + (i + 1) + "颗黑子");
									black1 = sc.nextInt();
									black2 = sc.nextInt();
									if ((black1 < 0 || black1 >= 14) || (black2 < 0 || black2 >= 14)//删除的棋子要在方格中且是黑子
											|| a[black1][black2] != 2) {
										System.out.println("去除黑子格式错误,请选择正确的棋子去删除");
										continue;
									} else {
										chessState1[m].chessboard[black1][black2]=0;
										a=chessState1[m].chessboard;
										i++;
									}
								}
								System.out.println("成方吃掉黑子后,现棋盘为:");
								chessState1[m].showBoard();
								showChessNumber(chessState1[m]);
								flag=-1;
							}
						}else {
							for (int i = 0; i < AI.bestMove.size() - 1; i++) {
								chessState1[m].move(AI.bestMove.get(i)[0], AI.bestMove.get(i)[1], AI.bestMove.get(i + 1)[0],
										AI.bestMove.get(i + 1)[1]);
							}
							if (AI.bestChi.size() != 0) {
								for (int i = 0; i < AI.bestChi.size(); i++) {
									chessState1[m].chessboard[AI.bestChi.get(i)[0]][AI.bestChi.get(i)[1]] = 0;
								}
							}
							a=chessState1[m].chessboard;
							chessState1[m].showBoard();
							showChessNumber(chessState1[m]);
							flag=-1;
						}
					}else {
						int x1=sc.nextInt();
						int y1=sc.nextInt();
						int x2=sc.nextInt();
						int y2=sc.nextInt();
						if(x1==-1&&y1==-1&&x2==-1&&y2==-1) {
							System.out.print("对方请求悔棋,悔棋的步数为:");
							int m1=sc.nextInt();
							m-=m1;
							if(m<0) {
								System.out.println("悔棋出现错误");
								m+=m1;
								continue;
							}
							a=chessState1[m].chessboard;
							if(m==1){
								flag=1;
								first=1;
								continue;
							}else if(m==0) {
								flag=-1;
								first=1;
								first1=1;
								continue;
							}
							if(m%2==0) {
								flag=-1;
								continue;
							}else {
								flag=1;
								continue;
							}
						}else if((x1<0||x1>13)||(y1<0||y1>13)||(x2<0||x2>13)||(y2<0||y2>13)) {
							System.out.println("白棋行棋出现错误");
							continue;
						}else {
							int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							if(chessState1[m].isLessFourteen(WHITE)) {
								if ((a[x1][y1] != 1)) {// 判断是否错执行了对面的棋子
									System.out.println("现在应该为白子行棋,你错执行了对方的棋子");
									continue;
								} else {
									int[] num1 = chessState1[m].Squarenum(WHITE);// 移动前的方格数目
								    chessState1[++m]=new Chess(a,WHITE);
								    chessState1[m].move(x1, y1, x2, y2);
									int oldx=x2;
								    int oldy=y2;//记录行棋后的横纵坐标，用于连续跳子的情况中
									if(Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) == 3&&chessState1[m].hop(x2, y2) == true) {//满足连续跳子的情况
										a=chessState1[m].chessboard;
										for(;;) {
											System.out.println("白棋可以继续跳子");
										    System.out.print("是否要继续跳子（1表示继续跳子,0表示终止跳子）");
										    int c;
										    c = sc.nextInt();//1表示继续，0表示终止
											if (c == 1) {
												int x3, y3, x4, y4;
												System.out.println("请继续输入黑子跳子行棋");
												x3 = sc.nextInt();
												y3 = sc.nextInt();
												x4 = sc.nextInt();
												y4 = sc.nextInt();
												int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
												if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//要满足跳子规则，同时每次连续跳子要移动的棋子只能是上一次跳子的目的位置的棋子
													chessState1[m].move(x3, y3, x4, y4);	
													a=chessState1[m].chessboard;
													oldx=x4;
													oldy=y4;//更新目的位置棋子的坐标，用于下一次连续跳子
													if (chessState1[m].hop(x4, y4) == true) {//如果可以连续跳子就继续循环
														continue;
													} else {
														break;
													}
												} else {
													System.out.println("白棋跳子行棋不规范");
													continue;
												}
											}else {
												break;
											}
										}
										System.out.println("跳子结束后棋盘为");
										chessState1[m].showBoard();
									}
									a=chessState1[m].chessboard;
									int[] num2 = chessState1[m].Squarenum(WHITE);// 移动后的方格数目
									int deletenum = 0;
									for (int i = 0; i < num1.length; i++) {
										if (num1[i] == 0 && num2[i] == 1) {
											deletenum++;// 新增加的方格数
										}
									}
									if (deletenum == 0) {
										flag=-1;
										System.out.println("该步行棋没有形成棋门,不可吃子");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
									} else {
										System.out.println("一共可以删除" + deletenum + "颗黑子");
										int black1, black2;
										for (int i = 0; i < deletenum;) {
											System.out.println("请选择要删除的第" + (i + 1) + "颗黑子");
											black1 = sc.nextInt();
											black2 = sc.nextInt();
											if ((black1 < 0 || black1 >= 14) || (black2 < 0 || black2 >= 14)//删除的棋子要在方格中且是黑子
													|| a[black1][black2] != 2) {
												System.out.println("去除黑子格式错误,请选择正确的棋子去删除");
												continue;
											} else {
												chessState1[m].chessboard[black1][black2]=0;
												a=chessState1[m].chessboard;
												i++;
											}
										}
										System.out.println("成方吃掉黑子后,现棋盘为:");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
										flag=-1;
									}
								}
							}else {
								if (Dvalue > 4 || Dvalue==2) { // 判断未进入非子阶段时是否有类似非子的行棋,根2为对角线行棋
									System.out.println("白棋行棋不规范");
									continue;
								} else {
									if ((a[x1][y1] != 1)) {// 判断是否错执行了对面的棋子
										System.out.println("现在应该为白子行棋,你错执行了对方的棋子");
										continue;
									} else if (Dvalue == 1 && a[x1][y1] == 1 && a[x2][y2] != 0) {// 判断普通邻格行棋是否规范
										System.out.println("白棋行棋不规范");
										continue;
									} else if (Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) != 3) {// 判断跳子行棋是否规范
										System.out.println("白棋跳子行棋不规范");
										continue;
									} else {
										int[] num1 = chessState1[m].Squarenum(WHITE);// 移动前的方格数目
									    chessState1[++m]=new Chess(a,WHITE);
									    chessState1[m].move(x1, y1, x2, y2);
									    a=chessState1[m].chessboard;
										int oldx=x2;
									    int oldy=y2;//记录行棋后的横纵坐标，用于连续跳子的情况中
										if(Dvalue == 4 && chessState1[m].hop(x2, y2) == true) {//满足连续跳子的情况
											for(;;) {
												System.out.println("白棋可以继续跳子");
											    System.out.print("是否要继续跳子（1表示继续跳子,0表示终止跳子）");
											    int c;
											    c = sc.nextInt();//1表示继续，0表示终止
												if (c == 1) {
													int x3, y3, x4, y4;
													System.out.println("请继续输入白棋跳子行棋");
													x3 = sc.nextInt();
													y3 = sc.nextInt();
													x4 = sc.nextInt();
													y4 = sc.nextInt();
													int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
													if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//要满足跳子规则，同时每次连续跳子要移动的棋子只能是上一次跳子的目的位置的棋子
														chessState1[m].move(x3, y3, x4, y4);	
														a=chessState1[m].chessboard;
														oldx=x4;
														oldy=y4;//更新目的位置棋子的坐标，用于下一次连续跳子
														if (chessState1[m].hop(x4, y4) == true) {//如果可以连续跳子就继续循环
															continue;
														} else {
															break;
														}
													} else {
														System.out.println("白棋跳子行棋不规范");
														continue;
													}
												}else {
													break;
												}
											}
											System.out.println("跳子结束后棋盘为");
											chessState1[m].showBoard();
										}
										int[] num2 = chessState1[m].Squarenum(WHITE);// 移动后的方格数目
										int deletenum = 0;
										for (int i = 0; i < num1.length; i++) {
											if (num1[i] == 0 && num2[i] == 1) {
												deletenum++;// 新增加的方格数
											}
										}
										if (deletenum == 0) {
											flag=-1;
											System.out.println("该步行棋没有形成棋门,不可吃子");
											chessState1[m].showBoard();
											showChessNumber(chessState1[m]);
										} else {
											System.out.println("一共可以删除" + deletenum + "颗黑子");
											int black1, black2;
											for (int i = 0; i < deletenum;) {
												System.out.println("请选择要删除的第" + (i + 1) + "颗黑子");
												black1 = sc.nextInt();
												black2 = sc.nextInt();
												if ((black1 < 0 || black1 >= 14) || (black2 < 0 || black2 >= 14)//删除的棋子要在方格中且是黑子
														|| a[black1][black2] != 2) {
													System.out.println("去除黑子格式错误，请选择正确的棋子去删除");
													continue;
												} else {
													chessState1[m].chessboard[black1][black2]=0;
													a=chessState1[m].chessboard;
													i++;
												}
											}
											System.out.println("成方吃掉黑子后,现棋盘为:");
											chessState1[m].showBoard();
											showChessNumber(chessState1[m]);
											flag=-1;
										}
									}
								}
							}	
						}
					}	
				}
			}else {
				if(first1==1) {
					System.out.println("目前为第"+(m+1)+"步,黑棋第一步行棋应为去掉中央对角线的黑子,即去除点:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState1[m].chessboard[x1][y1]==2) {
						a[x1][y1] = 0;
						chessState1[++m] = new Chess(a, BLACK);
						chessState1[m].showBoard();
						showChessNumber(chessState1[m]);
						first1 = 2;
						flag = 1;
					} else {
						System.out.println("黑棋去子出现错误");
						continue;
					}
				} else {
					System.out.println("目前为第" + (m + 1) + "步,黑棋下一步行棋为:");
					if(player==-1) {
						Chess c1=new Chess(chessState1[m].chessboard,BLACK);
						Alpha_beta AI =new Alpha_beta(c1);
						System.out.println("最佳可行的行棋方法为:");
						for(int i=0;i<AI.bestMove.size()-1;i++) {
							System.out.print("("+AI.bestMove.get(i)[0]+","+AI.bestMove.get(i)[1]+") -> ");
						}
						System.out.println("("+AI.bestMove.getLast()[0]+","+AI.bestMove.getLast()[1]+")");
						System.out.print("可吃的点为:");
						if(AI.bestChi.size()!=0) {
							for(int i=0;i<AI.bestChi.size();i++) {
								System.out.print("("+AI.bestChi.get(i)[0]+","+AI.bestChi.get(i)[1]+")   ");
							}
							System.out.println();
						}else {
							System.out.println("该步行棋无可吃点");
						}
						
						chessState1[++m]=new Chess(a,BLACK);
						if(lessThirty_FiveNumber(chessState1[m])) {
							int x1=sc.nextInt();
							int y1=sc.nextInt();
							int x2=sc.nextInt();
							int y2=sc.nextInt();
							int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							int[] num1 = chessState1[m].Squarenum(BLACK);// 移动前的方格数目
						    chessState1[m].move(x1, y1, x2, y2);
							int oldx=x2;
						    int oldy=y2;//记录行棋后的横纵坐标，用于连续跳子的情况中
							if(Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) == 3&&chessState1[m].hop(x2, y2) == true) {//满足连续跳子的情况
								a=chessState1[m].chessboard;
								for(;;) {
									System.out.println("黑棋可以继续跳子");
								    System.out.print("是否要继续跳子（1表示继续跳子,0表示终止跳子）");
								    int c;
								    c = sc.nextInt();//1表示继续，0表示终止
									if (c == 1) {
										int x3, y3, x4, y4;
										System.out.println("请继续输入黑子跳子行棋");
										x3 = sc.nextInt();
										y3 = sc.nextInt();
										x4 = sc.nextInt();
										y4 = sc.nextInt();
										int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
										if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//要满足跳子规则，同时每次连续跳子要移动的棋子只能是上一次跳子的目的位置的棋子
											chessState1[m].move(x3, y3, x4, y4);	
											a=chessState1[m].chessboard;
											oldx=x4;
											oldy=y4;//更新目的位置棋子的坐标，用于下一次连续跳子
											if (chessState1[m].hop(x4, y4) == true) {//如果可以连续跳子就继续循环
												continue;
											} else {
												break;
											}
										} else {
											System.out.println("黑棋跳子行棋不规范");
											continue;
										}
									}else {
										break;
									}
								}
								System.out.println("跳子结束后棋盘为");
							    chessState1[m].showBoard();
							}
							a=chessState1[m].chessboard;
							int[] num2 = chessState1[m].Squarenum(BLACK);// 移动后的方格数目
							int deletenum = 0;
							for (int i = 0; i < num1.length; i++) {
								if (num1[i] == 0 && num2[i] == 1) {
									deletenum++;// 新增加的方格数
								}
							}
							if (deletenum == 0) {
								flag=1;
								System.out.println("该步行棋没有形成棋门,不可吃子");
								chessState1[m].showBoard();
								showChessNumber(chessState1[m]);
							} else {
								System.out.println("一共可以删除" + deletenum + "颗白子");
								int white1, white2;
								for (int i = 0; i < deletenum;) {
									System.out.println("请选择要删除的第" + (i + 1) + "颗白子");
									white1 = sc.nextInt();
									white2 = sc.nextInt();
									if ((white1 < 0 || white1 >= 14) || (white2 < 0 || white2 >= 14)//删除的棋子要在方格中且是黑子
											|| a[white1][white2] != 1) {
										System.out.println("去除白子格式错误,请选择正确的棋子去删除");
										continue;
									} else {
										chessState1[m].chessboard[white1][white2]=0;
										a=chessState1[m].chessboard;
										i++;
									}
								}
								System.out.println("成方吃掉黑子后,现棋盘为:");
								chessState1[m].showBoard();
								showChessNumber(chessState1[m]);
								flag=1;
							}
						}else {
							for (int i = 0; i < AI.bestMove.size() - 1; i++) {
								chessState1[m].move(AI.bestMove.get(i)[0], AI.bestMove.get(i)[1], AI.bestMove.get(i + 1)[0],
										AI.bestMove.get(i + 1)[1]);
							}
							if (AI.bestChi.size() != 0) {
								for (int i = 0; i < AI.bestChi.size(); i++) {
									chessState1[m].chessboard[AI.bestChi.get(i)[0]][AI.bestChi.get(i)[1]] = 0;
								}
							}
							a=chessState1[m].chessboard;
							chessState1[m].showBoard();
							showChessNumber(chessState1[m]);
							flag=1;
						}
					}else {
						int x1=sc.nextInt();
						int y1=sc.nextInt();
						int x2=sc.nextInt();
						int y2=sc.nextInt();
						if(x1==-1&&y1==-1&&x2==-1&&y2==-1) {
							System.out.print("对方请求悔棋,悔棋的步数为:");
							int m1=sc.nextInt();
							m-=m1;
							if(m<0) {
								System.out.println("悔棋出现错误");
								m+=m1;
								continue;
							}
							a=chessState1[m].chessboard;
							if(m==1){
								flag=1;
								first=1;
								continue;
							}else if(m==0) {
								flag=-1;
								first=1;
								first1=1;
								continue;
							}
							if(m%2==0) {
								flag=-1;
								continue;
							}else {
								flag=1;
								continue;
							}
						}else if((x1<0||x1>13)||(y1<0||y1>13)||(x2<0||x2>13)||(y2<0||y2>13)) {
							System.out.println("黑棋行棋出现错误");
							continue;
						}else {
							int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							if(chessState1[m].isLessFourteen(BLACK)) {
								if ((a[x1][y1] != 2)) {// 判断是否错执行了对面的棋子
									System.out.println("现在应该为黑子行棋,你错执行了对方的棋子");
									continue;
								} else {
									int[] num1 = chessState1[m].Squarenum(BLACK);// 移动前的方格数目
								    chessState1[++m]=new Chess(a,BLACK);
								    int color1=a[x1][y1];
								    int color2=a[(x1 + x2) / 2][(y1 + y2) / 2];
								    chessState1[m].move(x1, y1, x2, y2);
								    a=chessState1[m].chessboard;
									int oldx=x2;
								    int oldy=y2;//记录行棋后的横纵坐标，用于连续跳子的情况中
									if(Dvalue == 4 && color1+color2 == 3&&chessState1[m].hop(x2, y2) == true) {//满足连续跳子的情况
										for(;;) {
											System.out.println("黑棋可以继续跳子");
										    System.out.print("是否要继续跳子（1表示继续跳子,0表示终止跳子）");
										    int c;
										    c = sc.nextInt();//1表示继续，0表示终止
											if (c == 1) {
												int x3, y3, x4, y4;
												System.out.println("请继续输入黑子跳子行棋");
												x3 = sc.nextInt();
												y3 = sc.nextInt();
												x4 = sc.nextInt();
												y4 = sc.nextInt();
												int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
												if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//要满足跳子规则，同时每次连续跳子要移动的棋子只能是上一次跳子的目的位置的棋子
													chessState1[m].move(x3, y3, x4, y4);	
													a=chessState1[m].chessboard;
													oldx=x4;
													oldy=y4;//更新目的位置棋子的坐标，用于下一次连续跳子
													if (chessState1[m].hop(x4, y4) == true) {//如果可以连续跳子就继续循环
														continue;
													} else {
														break;
													}
												} else {
													System.out.println("黑棋跳子行棋不规范");
													continue;
												}
											}else {
												break;
											}
										}
										System.out.println("跳子结束后棋盘为");
										chessState1[m].showBoard();
									}
									int[] num2 = chessState1[m].Squarenum(BLACK);// 移动后的方格数目
									int deletenum = 0;
									for (int i = 0; i < num1.length; i++) {
										if (num1[i] == 0 && num2[i] == 1) {
											deletenum++;// 新增加的方格数
										}
									}
									if (deletenum == 0) {
										flag=1;
										System.out.println("该步行棋没有形成棋门,不可吃子");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
									} else {
										System.out.println("一共可以删除" + deletenum + "颗白子");
										int white1, white2;
										for (int i = 0; i < deletenum;) {
											System.out.println("请选择要删除的第" + (i + 1) + "颗白子");
											white1 = sc.nextInt();
											white2 = sc.nextInt();
											if ((white1 < 0 || white1 >= 14) || (white2 < 0 || white2 >= 14)//删除的棋子要在方格中且是白子
													|| a[white1][white2] != 1) {
												System.out.println("去除白子格式错误,请选择正确的棋子去删除");
												continue;
											} else {
												chessState1[m].chessboard[white1][white2]=0;
												a=chessState1[m].chessboard;
												i++;
											}
										}
										System.out.println("成方吃掉黑子后,现棋盘为:");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
										flag=1;
									}
								}
							}else {
								if (Dvalue > 4 || Dvalue==2) { // 判断未进入非子阶段时是否有类似非子的行棋,根2为对角线行棋
									System.out.println("黑棋行棋不规范");
									continue;
								} else {
									if ((a[x1][y1] != 2)) {// 判断是否错执行了对面的棋子
										System.out.println("现在应该为黑子行棋,你错执行了对方的棋子");
										continue;
									} else if (Dvalue == 1 && a[x1][y1] == 2 && a[x2][y2] != 0) {// 判断普通邻格行棋是否规范
										System.out.println("黑棋行棋不规范");
										continue;
									} else if (Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) != 3) {// 判断跳子行棋是否规范
										System.out.println("黑棋跳子行棋不规范");
										continue;
									} else {
										int[] num1 = chessState1[m].Squarenum(BLACK);// 移动前的方格数目
									    chessState1[++m]=new Chess(a,BLACK);
									    chessState1[m].move(x1, y1, x2, y2);
									    a=chessState1[m].chessboard;
										int oldx=x2;
									    int oldy=y2;//记录行棋后的横纵坐标，用于连续跳子的情况中
										if(Dvalue == 4 && chessState1[m].hop(x2, y2) == true) {//满足连续跳子的情况
											for(;;) {
												System.out.println("黑棋可以继续跳子");
											    System.out.print("是否要继续跳子（1表示继续跳子,0表示终止跳子）");
											    int c;
											    c = sc.nextInt();//1表示继续，0表示终止
												if (c == 1) {
													int x3, y3, x4, y4;
													System.out.println("请继续输入黑子跳子行棋");
													x3 = sc.nextInt();
													y3 = sc.nextInt();
													x4 = sc.nextInt();
													y4 = sc.nextInt();
													int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
													if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//要满足跳子规则，同时每次连续跳子要移动的棋子只能是上一次跳子的目的位置的棋子
														chessState1[m].move(x3, y3, x4, y4);	
														a=chessState1[m].chessboard;
														oldx=x4;
														oldy=y4;//更新目的位置棋子的坐标，用于下一次连续跳子
														if (chessState1[m].hop(x4, y4) == true) {//如果可以连续跳子就继续循环
															continue;
														} else {
															break;
														}
													} else {
														System.out.println("黑棋跳子行棋不规范");
														continue;
													}
												}else {
													break;
												}
											}
											System.out.println("跳子结束后棋盘为");
										    chessState1[m].showBoard();
										}
										int[] num2 = chessState1[m].Squarenum(BLACK);// 移动后的方格数目
										int deletenum = 0;
										for (int i = 0; i < num1.length; i++) {
											if (num1[i] == 0 && num2[i] == 1) {
												deletenum++;// 新增加的方格数
											}
										}
										if (deletenum == 0) {
											flag=1;
											System.out.println("该步行棋没有形成棋门,不可吃子");
											chessState1[m].showBoard();
											showChessNumber(chessState1[m]);
										} else {
											System.out.println("一共可以删除" + deletenum + "颗白子");
											int white1, white2;
											for (int i = 0; i < deletenum;) {
												System.out.println("请选择要删除的第" + (i + 1) + "颗白子");
												white1 = sc.nextInt();
												white2 = sc.nextInt();
												if ((white1 < 0 || white1 >= 14) || (white2 < 0 || white2 >= 14)//删除的棋子要在方格中且是黑子
														|| a[white1][white2] != 1) {
													System.out.println("去除白子格式错误,请选择正确的棋子去删除");
													continue;
												} else {
													chessState1[m].chessboard[white1][white2]=0;
													a=chessState1[m].chessboard;
													i++;
												}
											}
											System.out.println("成方吃掉黑子后,现棋盘为:");
											chessState1[m].showBoard();
											showChessNumber(chessState1[m]);
											flag=1;
										}
									}
								}
							}
						}
					}	
				}
			}
			if (chessState1[m].game_over()) {
				break;
			}
		}
	}
	
	public static void showChessNumber(Chess c) {
		int blackNumber=0;
		int whiteNumber=0;
		for(int i=0;i<c.chessboard.length;i++) {
			for(int j=0;j<c.chessboard[i].length;j++) {
				if(c.chessboard[i][j]==BLACK) {
					blackNumber++;
				}
				if(c.chessboard[i][j]==WHITE) {
					whiteNumber++;
				}
			}
		}
		System.out.println("黑子:"+blackNumber+"颗\n白子:"+whiteNumber+"颗");
	}
	public static boolean lessThirty_FiveNumber(Chess c) {
		int m=0;
		for(int i=0;i<c.chessboard.length;i++) {
			for(int j=0;j<c.chessboard[i].length;j++) {
				if(c.chessboard[i][j]==c.color)
					m++;
			}
		}
		if(m<=35)
			return true;
		else
			return false;
	}
}