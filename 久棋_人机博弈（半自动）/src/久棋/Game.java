package ����;
import java.util.*;
public class Game {
	private static int a[][]=new int[14][14]; //0���ӣ�1Ϊ���ӣ�2Ϊ����
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
		System.out.println("���������ӷֲ�Ϊ��");
		chessState[n].showBoard();
		System.out.println("���������Ǻ��ӻ��ǰ��ӣ�1��ʾ���ӣ�-1��ʾ���ӣ�");
		flag= sc.nextInt();
		player=flag;
		while(true) {
			if(flag==1) {
				if(first==1) {
					System.out.println("ĿǰΪ��"+(n+1)+"��,������һ������Ϊ:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState[n].chessboard[x1][y1]==0) {
						a[x1][y1]=1;
						chessState[++n]=new Chess(a);
						first=2;
						flag=-1;
					}else {
						System.out.println("�������ӳ��ִ���");
					    continue;
					}
				}else {
					System.out.println("ĿǰΪ��"+(n+1)+"��,������һ������Ϊ:");
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
							System.out.print("�Է��������,����Ĳ���Ϊ:");
							int m=sc.nextInt();
							n-=m;
							if(n<0) {
								System.out.println("������ִ���");
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
							System.out.println("�������ӳ��ִ���");
							continue;
						}else if(chessState[n].chessboard[x1][y1]!=0){
							System.out.println("�������ӳ��ִ���");
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
					System.out.println("ĿǰΪ��"+(n+1)+"��,�Է���һ������Ϊ:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState[n].chessboard[x1][y1]==0) {
						a[x1][y1]=1;
						chessState[++n]=new Chess(a);
						first1=2;
					}else {
						System.out.println("�������ӳ��ִ���");
						continue;
					}
				}else if(first==2||(first==1&&first1==2)){
					System.out.println("ĿǰΪ��"+(n+1)+"��,������һ������Ϊ:");
					int x1=sc.nextInt();
					int y1=sc.nextInt();
					if(((x1==6&&y1==6)||(x1==7&&y1==7))&& chessState[n].chessboard[x1][y1]==0) {
						a[x1][y1]=2;
						chessState[++n]=new Chess(a);
					    first=3;
					    flag=1;
					}else {
						System.out.println("�������ӳ��ִ���");
						continue;
					}
				}else {
					System.out.println("ĿǰΪ��"+(n+1)+"��,������һ������Ϊ:");
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
							System.out.print("�Է��������,����Ĳ���Ϊ:");
							int m=sc.nextInt();
							n-=m;
							if(n<0) {
								System.out.println("������ִ���");
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
							System.out.println("�������ӳ��ִ���");
							continue;
						}else if(chessState[n].chessboard[x1][y1]!=0){
							System.out.println("�������ӳ��ִ���");
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
					System.out.println("ĿǰΪ��"+(m+1)+"��,�����һ������ӦΪȥ������Խ��ߵİ���:");
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
						System.out.println("����ȥ�ӳ��ִ���");
					    continue;
					}
				}else {
					System.out.println("ĿǰΪ��"+(m+1)+"��,������һ������Ϊ:");
					if(player==WHITE) {
						Chess c1=new Chess(a,WHITE);
						Alpha_beta AI =new Alpha_beta(c1);
						System.out.println("��ѿ��е����巽��Ϊ:");
						for(int i=0;i<AI.bestMove.size()-1;i++) {
							System.out.print("("+AI.bestMove.get(i)[0]+","+AI.bestMove.get(i)[1]+") -> ");
						}
						System.out.println("("+AI.bestMove.getLast()[0]+","+AI.bestMove.getLast()[1]+")");
						System.out.print("�ɳԵĵ�Ϊ:");
						if(AI.bestChi.size()!=0) {
							for(int i=0;i<AI.bestChi.size();i++) {
								System.out.print("("+AI.bestChi.get(i)[0]+","+AI.bestChi.get(i)[1]+")   ");
							}
							System.out.println();
						}else {
							System.out.println("�ò������޿ɳԵ�");
						}
						
						chessState1[++m]=new Chess(a,WHITE);
						if(lessThirty_FiveNumber(chessState1[m])) {
							int x1=sc.nextInt();
							int y1=sc.nextInt();
							int x2=sc.nextInt();
							int y2=sc.nextInt();
							int[] num1 = chessState1[m].Squarenum(WHITE);// �ƶ�ǰ�ķ�����Ŀ
						    chessState1[m].move(x1, y1, x2, y2);
						    int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							int oldx=x2;
						    int oldy=y2;//��¼�����ĺ������꣬�����������ӵ������
							if(Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) == 3&&chessState1[m].hop(x2, y2) == true) {//�����������ӵ����
								a=chessState1[m].chessboard;
								for(;;) {
									System.out.println("������Լ�������");
								    System.out.print("�Ƿ�Ҫ�������ӣ�1��ʾ��������,0��ʾ��ֹ���ӣ�");
								    int c;
								    c = sc.nextInt();//1��ʾ������0��ʾ��ֹ
									if (c == 1) {
										int x3, y3, x4, y4;
										System.out.println("��������������������");
										x3 = sc.nextInt();
										y3 = sc.nextInt();
										x4 = sc.nextInt();
										y4 = sc.nextInt();
										int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
										if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//Ҫ�������ӹ���ͬʱÿ����������Ҫ�ƶ�������ֻ������һ�����ӵ�Ŀ��λ�õ�����
											chessState1[m].move(x3, y3, x4, y4);	
											a=chessState1[m].chessboard;
											oldx=x4;
											oldy=y4;//����Ŀ��λ�����ӵ����꣬������һ����������
											if (chessState1[m].hop(x4, y4) == true) {//��������������Ӿͼ���ѭ��
												continue;
											} else {
												break;
											}
										} else {
											System.out.println("�����������岻�淶");
											continue;
										}
									}else {
										break;
									}
								}
								System.out.println("���ӽ���������Ϊ");
								chessState1[m].showBoard();
							}
							a=chessState1[m].chessboard;
							int[] num2 = chessState1[m].Squarenum(WHITE);// �ƶ���ķ�����Ŀ
							int deletenum = 0;
							for (int i = 0; i < num1.length; i++) {
								if (num1[i] == 0 && num2[i] == 1) {
									deletenum++;// �����ӵķ�����
								}
							}
							if (deletenum == 0) {
								flag=-1;
								System.out.println("�ò�����û���γ�����,���ɳ���");
								chessState1[m].showBoard();
								showChessNumber(chessState1[m]);
							} else {
								System.out.println("һ������ɾ��" + deletenum + "�ź���");
								int black1, black2;
								for (int i = 0; i < deletenum;) {
									System.out.println("��ѡ��Ҫɾ���ĵ�" + (i + 1) + "�ź���");
									black1 = sc.nextInt();
									black2 = sc.nextInt();
									if ((black1 < 0 || black1 >= 14) || (black2 < 0 || black2 >= 14)//ɾ��������Ҫ�ڷ��������Ǻ���
											|| a[black1][black2] != 2) {
										System.out.println("ȥ�����Ӹ�ʽ����,��ѡ����ȷ������ȥɾ��");
										continue;
									} else {
										chessState1[m].chessboard[black1][black2]=0;
										a=chessState1[m].chessboard;
										i++;
									}
								}
								System.out.println("�ɷ��Ե����Ӻ�,������Ϊ:");
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
							System.out.print("�Է��������,����Ĳ���Ϊ:");
							int m1=sc.nextInt();
							m-=m1;
							if(m<0) {
								System.out.println("������ִ���");
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
							System.out.println("����������ִ���");
							continue;
						}else {
							int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							if(chessState1[m].isLessFourteen(WHITE)) {
								if ((a[x1][y1] != 1)) {// �ж��Ƿ��ִ���˶��������
									System.out.println("����Ӧ��Ϊ��������,���ִ���˶Է�������");
									continue;
								} else {
									int[] num1 = chessState1[m].Squarenum(WHITE);// �ƶ�ǰ�ķ�����Ŀ
								    chessState1[++m]=new Chess(a,WHITE);
								    chessState1[m].move(x1, y1, x2, y2);
									int oldx=x2;
								    int oldy=y2;//��¼�����ĺ������꣬�����������ӵ������
									if(Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) == 3&&chessState1[m].hop(x2, y2) == true) {//�����������ӵ����
										a=chessState1[m].chessboard;
										for(;;) {
											System.out.println("������Լ�������");
										    System.out.print("�Ƿ�Ҫ�������ӣ�1��ʾ��������,0��ʾ��ֹ���ӣ�");
										    int c;
										    c = sc.nextInt();//1��ʾ������0��ʾ��ֹ
											if (c == 1) {
												int x3, y3, x4, y4;
												System.out.println("��������������������");
												x3 = sc.nextInt();
												y3 = sc.nextInt();
												x4 = sc.nextInt();
												y4 = sc.nextInt();
												int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
												if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//Ҫ�������ӹ���ͬʱÿ����������Ҫ�ƶ�������ֻ������һ�����ӵ�Ŀ��λ�õ�����
													chessState1[m].move(x3, y3, x4, y4);	
													a=chessState1[m].chessboard;
													oldx=x4;
													oldy=y4;//����Ŀ��λ�����ӵ����꣬������һ����������
													if (chessState1[m].hop(x4, y4) == true) {//��������������Ӿͼ���ѭ��
														continue;
													} else {
														break;
													}
												} else {
													System.out.println("�����������岻�淶");
													continue;
												}
											}else {
												break;
											}
										}
										System.out.println("���ӽ���������Ϊ");
										chessState1[m].showBoard();
									}
									a=chessState1[m].chessboard;
									int[] num2 = chessState1[m].Squarenum(WHITE);// �ƶ���ķ�����Ŀ
									int deletenum = 0;
									for (int i = 0; i < num1.length; i++) {
										if (num1[i] == 0 && num2[i] == 1) {
											deletenum++;// �����ӵķ�����
										}
									}
									if (deletenum == 0) {
										flag=-1;
										System.out.println("�ò�����û���γ�����,���ɳ���");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
									} else {
										System.out.println("һ������ɾ��" + deletenum + "�ź���");
										int black1, black2;
										for (int i = 0; i < deletenum;) {
											System.out.println("��ѡ��Ҫɾ���ĵ�" + (i + 1) + "�ź���");
											black1 = sc.nextInt();
											black2 = sc.nextInt();
											if ((black1 < 0 || black1 >= 14) || (black2 < 0 || black2 >= 14)//ɾ��������Ҫ�ڷ��������Ǻ���
													|| a[black1][black2] != 2) {
												System.out.println("ȥ�����Ӹ�ʽ����,��ѡ����ȷ������ȥɾ��");
												continue;
											} else {
												chessState1[m].chessboard[black1][black2]=0;
												a=chessState1[m].chessboard;
												i++;
											}
										}
										System.out.println("�ɷ��Ե����Ӻ�,������Ϊ:");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
										flag=-1;
									}
								}
							}else {
								if (Dvalue > 4 || Dvalue==2) { // �ж�δ������ӽ׶�ʱ�Ƿ������Ʒ��ӵ�����,��2Ϊ�Խ�������
									System.out.println("�������岻�淶");
									continue;
								} else {
									if ((a[x1][y1] != 1)) {// �ж��Ƿ��ִ���˶��������
										System.out.println("����Ӧ��Ϊ��������,���ִ���˶Է�������");
										continue;
									} else if (Dvalue == 1 && a[x1][y1] == 1 && a[x2][y2] != 0) {// �ж���ͨ�ڸ������Ƿ�淶
										System.out.println("�������岻�淶");
										continue;
									} else if (Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) != 3) {// �ж����������Ƿ�淶
										System.out.println("�����������岻�淶");
										continue;
									} else {
										int[] num1 = chessState1[m].Squarenum(WHITE);// �ƶ�ǰ�ķ�����Ŀ
									    chessState1[++m]=new Chess(a,WHITE);
									    chessState1[m].move(x1, y1, x2, y2);
									    a=chessState1[m].chessboard;
										int oldx=x2;
									    int oldy=y2;//��¼�����ĺ������꣬�����������ӵ������
										if(Dvalue == 4 && chessState1[m].hop(x2, y2) == true) {//�����������ӵ����
											for(;;) {
												System.out.println("������Լ�������");
											    System.out.print("�Ƿ�Ҫ�������ӣ�1��ʾ��������,0��ʾ��ֹ���ӣ�");
											    int c;
											    c = sc.nextInt();//1��ʾ������0��ʾ��ֹ
												if (c == 1) {
													int x3, y3, x4, y4;
													System.out.println("��������������������");
													x3 = sc.nextInt();
													y3 = sc.nextInt();
													x4 = sc.nextInt();
													y4 = sc.nextInt();
													int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
													if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//Ҫ�������ӹ���ͬʱÿ����������Ҫ�ƶ�������ֻ������һ�����ӵ�Ŀ��λ�õ�����
														chessState1[m].move(x3, y3, x4, y4);	
														a=chessState1[m].chessboard;
														oldx=x4;
														oldy=y4;//����Ŀ��λ�����ӵ����꣬������һ����������
														if (chessState1[m].hop(x4, y4) == true) {//��������������Ӿͼ���ѭ��
															continue;
														} else {
															break;
														}
													} else {
														System.out.println("�����������岻�淶");
														continue;
													}
												}else {
													break;
												}
											}
											System.out.println("���ӽ���������Ϊ");
											chessState1[m].showBoard();
										}
										int[] num2 = chessState1[m].Squarenum(WHITE);// �ƶ���ķ�����Ŀ
										int deletenum = 0;
										for (int i = 0; i < num1.length; i++) {
											if (num1[i] == 0 && num2[i] == 1) {
												deletenum++;// �����ӵķ�����
											}
										}
										if (deletenum == 0) {
											flag=-1;
											System.out.println("�ò�����û���γ�����,���ɳ���");
											chessState1[m].showBoard();
											showChessNumber(chessState1[m]);
										} else {
											System.out.println("һ������ɾ��" + deletenum + "�ź���");
											int black1, black2;
											for (int i = 0; i < deletenum;) {
												System.out.println("��ѡ��Ҫɾ���ĵ�" + (i + 1) + "�ź���");
												black1 = sc.nextInt();
												black2 = sc.nextInt();
												if ((black1 < 0 || black1 >= 14) || (black2 < 0 || black2 >= 14)//ɾ��������Ҫ�ڷ��������Ǻ���
														|| a[black1][black2] != 2) {
													System.out.println("ȥ�����Ӹ�ʽ������ѡ����ȷ������ȥɾ��");
													continue;
												} else {
													chessState1[m].chessboard[black1][black2]=0;
													a=chessState1[m].chessboard;
													i++;
												}
											}
											System.out.println("�ɷ��Ե����Ӻ�,������Ϊ:");
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
					System.out.println("ĿǰΪ��"+(m+1)+"��,�����һ������ӦΪȥ������Խ��ߵĺ���,��ȥ����:");
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
						System.out.println("����ȥ�ӳ��ִ���");
						continue;
					}
				} else {
					System.out.println("ĿǰΪ��" + (m + 1) + "��,������һ������Ϊ:");
					if(player==-1) {
						Chess c1=new Chess(chessState1[m].chessboard,BLACK);
						Alpha_beta AI =new Alpha_beta(c1);
						System.out.println("��ѿ��е����巽��Ϊ:");
						for(int i=0;i<AI.bestMove.size()-1;i++) {
							System.out.print("("+AI.bestMove.get(i)[0]+","+AI.bestMove.get(i)[1]+") -> ");
						}
						System.out.println("("+AI.bestMove.getLast()[0]+","+AI.bestMove.getLast()[1]+")");
						System.out.print("�ɳԵĵ�Ϊ:");
						if(AI.bestChi.size()!=0) {
							for(int i=0;i<AI.bestChi.size();i++) {
								System.out.print("("+AI.bestChi.get(i)[0]+","+AI.bestChi.get(i)[1]+")   ");
							}
							System.out.println();
						}else {
							System.out.println("�ò������޿ɳԵ�");
						}
						
						chessState1[++m]=new Chess(a,BLACK);
						if(lessThirty_FiveNumber(chessState1[m])) {
							int x1=sc.nextInt();
							int y1=sc.nextInt();
							int x2=sc.nextInt();
							int y2=sc.nextInt();
							int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							int[] num1 = chessState1[m].Squarenum(BLACK);// �ƶ�ǰ�ķ�����Ŀ
						    chessState1[m].move(x1, y1, x2, y2);
							int oldx=x2;
						    int oldy=y2;//��¼�����ĺ������꣬�����������ӵ������
							if(Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) == 3&&chessState1[m].hop(x2, y2) == true) {//�����������ӵ����
								a=chessState1[m].chessboard;
								for(;;) {
									System.out.println("������Լ�������");
								    System.out.print("�Ƿ�Ҫ�������ӣ�1��ʾ��������,0��ʾ��ֹ���ӣ�");
								    int c;
								    c = sc.nextInt();//1��ʾ������0��ʾ��ֹ
									if (c == 1) {
										int x3, y3, x4, y4;
										System.out.println("��������������������");
										x3 = sc.nextInt();
										y3 = sc.nextInt();
										x4 = sc.nextInt();
										y4 = sc.nextInt();
										int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
										if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//Ҫ�������ӹ���ͬʱÿ����������Ҫ�ƶ�������ֻ������һ�����ӵ�Ŀ��λ�õ�����
											chessState1[m].move(x3, y3, x4, y4);	
											a=chessState1[m].chessboard;
											oldx=x4;
											oldy=y4;//����Ŀ��λ�����ӵ����꣬������һ����������
											if (chessState1[m].hop(x4, y4) == true) {//��������������Ӿͼ���ѭ��
												continue;
											} else {
												break;
											}
										} else {
											System.out.println("�����������岻�淶");
											continue;
										}
									}else {
										break;
									}
								}
								System.out.println("���ӽ���������Ϊ");
							    chessState1[m].showBoard();
							}
							a=chessState1[m].chessboard;
							int[] num2 = chessState1[m].Squarenum(BLACK);// �ƶ���ķ�����Ŀ
							int deletenum = 0;
							for (int i = 0; i < num1.length; i++) {
								if (num1[i] == 0 && num2[i] == 1) {
									deletenum++;// �����ӵķ�����
								}
							}
							if (deletenum == 0) {
								flag=1;
								System.out.println("�ò�����û���γ�����,���ɳ���");
								chessState1[m].showBoard();
								showChessNumber(chessState1[m]);
							} else {
								System.out.println("һ������ɾ��" + deletenum + "�Ű���");
								int white1, white2;
								for (int i = 0; i < deletenum;) {
									System.out.println("��ѡ��Ҫɾ���ĵ�" + (i + 1) + "�Ű���");
									white1 = sc.nextInt();
									white2 = sc.nextInt();
									if ((white1 < 0 || white1 >= 14) || (white2 < 0 || white2 >= 14)//ɾ��������Ҫ�ڷ��������Ǻ���
											|| a[white1][white2] != 1) {
										System.out.println("ȥ�����Ӹ�ʽ����,��ѡ����ȷ������ȥɾ��");
										continue;
									} else {
										chessState1[m].chessboard[white1][white2]=0;
										a=chessState1[m].chessboard;
										i++;
									}
								}
								System.out.println("�ɷ��Ե����Ӻ�,������Ϊ:");
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
							System.out.print("�Է��������,����Ĳ���Ϊ:");
							int m1=sc.nextInt();
							m-=m1;
							if(m<0) {
								System.out.println("������ִ���");
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
							System.out.println("����������ִ���");
							continue;
						}else {
							int Dvalue = Math.abs(x1 - x2)*Math.abs(x1 - x2) + Math.abs(y1 - y2)*Math.abs(y1 - y2);
							if(chessState1[m].isLessFourteen(BLACK)) {
								if ((a[x1][y1] != 2)) {// �ж��Ƿ��ִ���˶��������
									System.out.println("����Ӧ��Ϊ��������,���ִ���˶Է�������");
									continue;
								} else {
									int[] num1 = chessState1[m].Squarenum(BLACK);// �ƶ�ǰ�ķ�����Ŀ
								    chessState1[++m]=new Chess(a,BLACK);
								    int color1=a[x1][y1];
								    int color2=a[(x1 + x2) / 2][(y1 + y2) / 2];
								    chessState1[m].move(x1, y1, x2, y2);
								    a=chessState1[m].chessboard;
									int oldx=x2;
								    int oldy=y2;//��¼�����ĺ������꣬�����������ӵ������
									if(Dvalue == 4 && color1+color2 == 3&&chessState1[m].hop(x2, y2) == true) {//�����������ӵ����
										for(;;) {
											System.out.println("������Լ�������");
										    System.out.print("�Ƿ�Ҫ�������ӣ�1��ʾ��������,0��ʾ��ֹ���ӣ�");
										    int c;
										    c = sc.nextInt();//1��ʾ������0��ʾ��ֹ
											if (c == 1) {
												int x3, y3, x4, y4;
												System.out.println("��������������������");
												x3 = sc.nextInt();
												y3 = sc.nextInt();
												x4 = sc.nextInt();
												y4 = sc.nextInt();
												int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
												if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//Ҫ�������ӹ���ͬʱÿ����������Ҫ�ƶ�������ֻ������һ�����ӵ�Ŀ��λ�õ�����
													chessState1[m].move(x3, y3, x4, y4);	
													a=chessState1[m].chessboard;
													oldx=x4;
													oldy=y4;//����Ŀ��λ�����ӵ����꣬������һ����������
													if (chessState1[m].hop(x4, y4) == true) {//��������������Ӿͼ���ѭ��
														continue;
													} else {
														break;
													}
												} else {
													System.out.println("�����������岻�淶");
													continue;
												}
											}else {
												break;
											}
										}
										System.out.println("���ӽ���������Ϊ");
										chessState1[m].showBoard();
									}
									int[] num2 = chessState1[m].Squarenum(BLACK);// �ƶ���ķ�����Ŀ
									int deletenum = 0;
									for (int i = 0; i < num1.length; i++) {
										if (num1[i] == 0 && num2[i] == 1) {
											deletenum++;// �����ӵķ�����
										}
									}
									if (deletenum == 0) {
										flag=1;
										System.out.println("�ò�����û���γ�����,���ɳ���");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
									} else {
										System.out.println("һ������ɾ��" + deletenum + "�Ű���");
										int white1, white2;
										for (int i = 0; i < deletenum;) {
											System.out.println("��ѡ��Ҫɾ���ĵ�" + (i + 1) + "�Ű���");
											white1 = sc.nextInt();
											white2 = sc.nextInt();
											if ((white1 < 0 || white1 >= 14) || (white2 < 0 || white2 >= 14)//ɾ��������Ҫ�ڷ��������ǰ���
													|| a[white1][white2] != 1) {
												System.out.println("ȥ�����Ӹ�ʽ����,��ѡ����ȷ������ȥɾ��");
												continue;
											} else {
												chessState1[m].chessboard[white1][white2]=0;
												a=chessState1[m].chessboard;
												i++;
											}
										}
										System.out.println("�ɷ��Ե����Ӻ�,������Ϊ:");
										chessState1[m].showBoard();
										showChessNumber(chessState1[m]);
										flag=1;
									}
								}
							}else {
								if (Dvalue > 4 || Dvalue==2) { // �ж�δ������ӽ׶�ʱ�Ƿ������Ʒ��ӵ�����,��2Ϊ�Խ�������
									System.out.println("�������岻�淶");
									continue;
								} else {
									if ((a[x1][y1] != 2)) {// �ж��Ƿ��ִ���˶��������
										System.out.println("����Ӧ��Ϊ��������,���ִ���˶Է�������");
										continue;
									} else if (Dvalue == 1 && a[x1][y1] == 2 && a[x2][y2] != 0) {// �ж���ͨ�ڸ������Ƿ�淶
										System.out.println("�������岻�淶");
										continue;
									} else if (Dvalue == 4 && (a[x1][y1] + a[(x1 + x2) / 2][(y1 + y2) / 2]) != 3) {// �ж����������Ƿ�淶
										System.out.println("�����������岻�淶");
										continue;
									} else {
										int[] num1 = chessState1[m].Squarenum(BLACK);// �ƶ�ǰ�ķ�����Ŀ
									    chessState1[++m]=new Chess(a,BLACK);
									    chessState1[m].move(x1, y1, x2, y2);
									    a=chessState1[m].chessboard;
										int oldx=x2;
									    int oldy=y2;//��¼�����ĺ������꣬�����������ӵ������
										if(Dvalue == 4 && chessState1[m].hop(x2, y2) == true) {//�����������ӵ����
											for(;;) {
												System.out.println("������Լ�������");
											    System.out.print("�Ƿ�Ҫ�������ӣ�1��ʾ��������,0��ʾ��ֹ���ӣ�");
											    int c;
											    c = sc.nextInt();//1��ʾ������0��ʾ��ֹ
												if (c == 1) {
													int x3, y3, x4, y4;
													System.out.println("��������������������");
													x3 = sc.nextInt();
													y3 = sc.nextInt();
													x4 = sc.nextInt();
													y4 = sc.nextInt();
													int Dvalue1 = Math.abs(x3 - x4)*Math.abs(x3 - x4) + Math.abs(y3 - y4)*Math.abs(y3 - y4);
													if (Dvalue1 == 4 && (a[x3][y3] + a[(x3 + x4) / 2][(y3 + y4) / 2]) == 3 &&x3==oldx &&y3==oldy)  {//Ҫ�������ӹ���ͬʱÿ����������Ҫ�ƶ�������ֻ������һ�����ӵ�Ŀ��λ�õ�����
														chessState1[m].move(x3, y3, x4, y4);	
														a=chessState1[m].chessboard;
														oldx=x4;
														oldy=y4;//����Ŀ��λ�����ӵ����꣬������һ����������
														if (chessState1[m].hop(x4, y4) == true) {//��������������Ӿͼ���ѭ��
															continue;
														} else {
															break;
														}
													} else {
														System.out.println("�����������岻�淶");
														continue;
													}
												}else {
													break;
												}
											}
											System.out.println("���ӽ���������Ϊ");
										    chessState1[m].showBoard();
										}
										int[] num2 = chessState1[m].Squarenum(BLACK);// �ƶ���ķ�����Ŀ
										int deletenum = 0;
										for (int i = 0; i < num1.length; i++) {
											if (num1[i] == 0 && num2[i] == 1) {
												deletenum++;// �����ӵķ�����
											}
										}
										if (deletenum == 0) {
											flag=1;
											System.out.println("�ò�����û���γ�����,���ɳ���");
											chessState1[m].showBoard();
											showChessNumber(chessState1[m]);
										} else {
											System.out.println("һ������ɾ��" + deletenum + "�Ű���");
											int white1, white2;
											for (int i = 0; i < deletenum;) {
												System.out.println("��ѡ��Ҫɾ���ĵ�" + (i + 1) + "�Ű���");
												white1 = sc.nextInt();
												white2 = sc.nextInt();
												if ((white1 < 0 || white1 >= 14) || (white2 < 0 || white2 >= 14)//ɾ��������Ҫ�ڷ��������Ǻ���
														|| a[white1][white2] != 1) {
													System.out.println("ȥ�����Ӹ�ʽ����,��ѡ����ȷ������ȥɾ��");
													continue;
												} else {
													chessState1[m].chessboard[white1][white2]=0;
													a=chessState1[m].chessboard;
													i++;
												}
											}
											System.out.println("�ɷ��Ե����Ӻ�,������Ϊ:");
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
		System.out.println("����:"+blackNumber+"��\n����:"+whiteNumber+"��");
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