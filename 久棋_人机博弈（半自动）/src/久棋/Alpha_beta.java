package 久棋;




import java.util.LinkedList;
import java.util.Vector;

public class Alpha_beta {
	int a=Integer.MIN_VALUE;
	int b=Integer.MAX_VALUE;
	int bestScore=0;
	int color=0;
	LinkedList<int[]> bestMove=new LinkedList<int[]>();
	LinkedList<int[]> bestChi=new LinkedList<int[]>();
	
	public Alpha_beta(Chess chessboard){
		color=chessboard.color;
		bestScore=maxmin(false,2,chessboard,a,b);
		bestMove=chessboard.bestMove;
		bestChi=chessboard.bestMoveChi;
	}
	
	public int maxmin(boolean isAI, int depth, Chess chessboard, int a, int b) {
		if (chessboard.game_over2(color) || depth == 0) {
			return chessboard.gameOverScore;
		}
		if (isAI) {
			if(chessboard.isLessFourteen()) {
				Array1[] arr3=chessboard.getMoveList1(); 
				for(int i=0;i<arr3.length;i++) {
					if(i>chessboard.index)
						break;
					Chess C1=new Chess(chessboard.chessboard,chessboard.color);
					Array1 arr1=arr3[i];
					LinkedList<int[]> li=arr1.li;
					if(li.size()>1) {
						if((li.size()==2&&!chessboard.isLessFourteen(chessboard.getAnotherColor()))||
								li.size()>2||(li.size()==2&&(chessboard.isLessThree(chessboard.getAnotherColor())||
										chessboard.isLessThree(chessboard.color)))) {
							for(int j=0;j<li.size()-1;j++) {
								C1.move(li.get(j)[0],li.get(j)[1],li.get(j+1)[0],li.get(j+1)[1]);
							}
							int n = change(chessboard, C1);
							if (n == 0) {
								C1.setColor(C1.getAnotherColor());
								int value1 = C1.evaluate(arr1, color);// 行棋得分
								int tmp = maxmin(false, depth - 1, C1, a, b) + value1;
								// beta表示从这个节点往下搜索最坏结局的上界
								if (tmp < b) {
									b = tmp;
									chessboard.bestMove = li;
									chessboard.bestMoveChi=bestChi;
								}
								// 剪枝:如果从当前格局搜索下去,不可能找到比已知最优解更好的解,则停止这个格局分支的搜索(剪枝)回溯到父节点继续搜索。
								if (b <= a) {
									// 此结点剩下的孩子结点被剪枝
									return b;
								}
							} else {
								C1.setColor(C1.getAnotherColor());
								int value1 = C1.evaluate(arr1, color);// 行棋得分
								Vector<LinkedList<int[]>> v1 = randomChizi(n, C1);
								for (int j = 0; j < v1.size(); j++) {
									LinkedList<int[]> chizi = v1.get(j);
									int value2 = C1.randomChizi(chizi,color);
									int tmp = maxmin(false, depth - 1, C1, a, b) + value1 + value2;
									backChizi(C1, chizi);
									// beta表示从这个节点往下搜索最坏结局的上界
									if (tmp < b) {
										b = tmp;
										chessboard.bestMove = li;
										chessboard.bestMoveChi = chizi;
									}
									// 剪枝:如果从当前格局搜索下去,不可能找到比已知最优解更好的解,则停止这个格局分支的搜索(剪枝)回溯到父节点继续搜索。
									if (b <= a) {
										// 此结点剩下的孩子结点被剪枝
										return b;
									}
								}
							}
						}else {
							continue;
						}				
					}else {
						for(int j=0;j<14;j++) {
							for(int k=0;k<14;k++) {
								if(C1.chessboard[j][k]==0) {
									int t=0;
									for(int m=0;m<=chessboard.index1;m++) {
										if(arr3[m].li.getFirst()[0]==li.get(0)[0]&&arr3[m].li.getFirst()[1]==li.get(0)[1]) {
											for(int n=1;n<arr3[m].li.size();n++) {
												if(t!=0)
													break;
												if(j==arr3[m].li.get(n)[0]&&k==arr3[m].li.get(n)[1]) {
													t++;
												}
											}
											if(t!=0)
												break;
										}
									}
									if(t==0) {
										C1.move(li.get(0)[0],li.get(0)[1],j,k);
										int n = change(chessboard, C1);
										if (n == 0) {
											Array1 arr2=new Array1(arr1.li);
											int xy2[]= {j,k};
											arr2.li.add(xy2);
											arr2.setType(Array1.FLY);
											C1.setColor(C1.getAnotherColor());
											int value1 = C1.evaluate(arr2, color);// 行棋得分
											int tmp = maxmin(false, depth - 1, C1, a, b) + value1;
											C1.moveBack1(j, k, li.get(0)[0],li.get(0)[1]);
											C1.setColor(C1.getAnotherColor());
											// beta表示从这个节点往下搜索最坏结局的上界
											if (tmp < b) {
												b = tmp;
												chessboard.bestMove = arr2.li;
												chessboard.bestMoveChi=bestChi;
											}
											// 剪枝:如果从当前格局搜索下去,不可能找到比已知最优解更好的解,则停止这个格局分支的搜索(剪枝)回溯到父节点继续搜索。
											if (b <= a) {
												// 此结点剩下的孩子结点被剪枝
												return b;
											}
										} else {
											Array1 arr2=new Array1(arr1.li);
											int xy2[]= {j,k};
											arr2.li.add(xy2);
											arr2.setType(Array1.FLY);
											C1.setColor(C1.getAnotherColor());
											int value1 = C1.evaluate(arr2, color);// 行棋得分
											Vector<LinkedList<int[]>> v1 = randomChizi(n, C1);
											for (int a1 = 0; a1 < v1.size(); a1++) {
												LinkedList<int[]> chizi = v1.get(a1);
												if(a1!=0) {
													C1.move(li.get(0)[0],li.get(0)[1],j,k);
												}
												int value2 = C1.randomChizi(chizi,color);
												int tmp = maxmin(false, depth - 1, C1, a, b) + value1 + value2;
												backChizi(C1, chizi);
												C1.moveBack1(j, k, li.get(0)[0],li.get(0)[1]);
												C1.setColor(C1.getAnotherColor());
												// beta表示从这个节点往下搜索最坏结局的上界
												if (tmp < b) {
													b = tmp;
													chessboard.bestMove = arr2.li;
													chessboard.bestMoveChi = chizi;
												}
												// 剪枝:如果从当前格局搜索下去,不可能找到比已知最优解更好的解,则停止这个格局分支的搜索(剪枝)回溯到父节点继续搜索。
												if (b <= a) {
													// 此结点剩下的孩子结点被剪枝
													return b;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				return b;	
			}else {
				Array1[] arr3=chessboard.getMoveList();
				for(int i=0;i<arr3.length;i++) {
					if (i > chessboard.index)
						break;
					Chess C1 = new Chess(chessboard.chessboard, chessboard.color);
					Array1 arr1 = arr3[i];
					LinkedList<int[]> li = arr1.li;
					if(arr1.type==2) {
						if(chessboard.isLessFourteen(chessboard.getAnotherColor())&&
								!chessboard.isLessThree(chessboard.getAnotherColor())) {
							if(li.size()==2) {
								continue;
							}
						}
					}
					for (int j = 0; j < li.size() - 1; j++) {
						C1.move(li.get(j)[0], li.get(j)[1], li.get(j + 1)[0], li.get(j + 1)[1]);
					}
					int n = change(chessboard, C1);
					if (n == 0) {
						C1.setColor(chessboard.getAnotherColor());
						int value1 = C1.evaluate(arr1, color);// 行棋得分
						int tmp = maxmin(false, depth - 1, C1, a, b) + value1;
						// beta表示从这个节点往下搜索最坏结局的上界
						if (tmp < b) {
							b = tmp;
							chessboard.bestMove = li;
							chessboard.bestMoveChi=bestChi;
						}
						// 剪枝:如果从当前格局搜索下去,不可能找到比已知最优解更好的解,则停止这个格局分支的搜索(剪枝)回溯到父节点继续搜索。
						if (b <= a) {
							// 此结点剩下的孩子结点被剪枝
							return b;
						}
					} else {
						C1.setColor(chessboard.getAnotherColor());
						int value1 = C1.evaluate(arr1, color);// 行棋得分
						Vector<LinkedList<int[]>> v1 = randomChizi(n, C1);
						for (int j = 0; j < v1.size(); j++) {
							LinkedList<int[]> chizi = v1.get(j);
							int value2 = C1.randomChizi(chizi,color);
							int tmp = maxmin(false, depth - 1, C1, a, b) + value1 + value2;
							backChizi(C1, chizi);
							// beta表示从这个节点往下搜索最坏结局的上界
							if (tmp < b) {
								b = tmp;
								chessboard.bestMove = li;
								chessboard.bestMoveChi = chizi;
							}
							// 剪枝:如果从当前格局搜索下去,不可能找到比已知最优解更好的解,则停止这个格局分支的搜索(剪枝)回溯到父节点继续搜索。
							if (b <= a) {
								// 此结点剩下的孩子结点被剪枝
								return b;
							}
						}
					}
				}
				return b;
			}
		} else {// alpha
			if(chessboard.isLessFourteen()) {
				Array1[] arr3=chessboard.getMoveList1();
				for(int i=0;i<arr3.length;i++) {
					if(i>chessboard.index)
						break;
					Chess C1=new Chess(chessboard.chessboard,chessboard.color);
					Array1 arr1=arr3[i];
					LinkedList<int[]> li=arr1.li;
					if(li.size()>1) {
						if((li.size()==2&&!chessboard.isLessFourteen(chessboard.getAnotherColor()))||
								li.size()>2||(li.size()==2&&(chessboard.isLessThree(chessboard.getAnotherColor())||
										chessboard.isLessThree(chessboard.color)))) {
							for(int j=0;j<li.size()-1;j++) {
								C1.move(li.get(j)[0],li.get(j)[1],li.get(j+1)[0],li.get(j+1)[1]);
							}
							int n = change(chessboard, C1);
							if (n == 0) {
								C1.setColor(C1.getAnotherColor());
								int value1 = C1.evaluate(arr1, color);// 行棋得分
								int tmp = maxmin(true, depth - 1, C1, a, b) + value1;
								// beta表示从这个节点往下搜索最坏结局的上界
								if (a < tmp) {
									a = tmp;
									chessboard.bestMove=li;
									chessboard.bestMoveChi=bestChi;
								}
								if (b <= a) {
									return a;
								}
							} else {
								C1.setColor(C1.getAnotherColor());
								int value1 = C1.evaluate(arr1, color);// 行棋得分
								Vector<LinkedList<int[]>> v1 = randomChizi(n, C1);
								for (int j = 0; j < v1.size(); j++) {
									LinkedList<int[]> chizi = v1.get(j);
									int value2 = C1.randomChizi(chizi,color);
									int tmp = maxmin(true, depth - 1, C1, a, b) + value1 + value2;
									backChizi(C1, chizi);
									// beta表示从这个节点往下搜索最坏结局的上界
									if (a < tmp) {
										a = tmp;
										chessboard.bestMove=li;
										chessboard.bestMoveChi=chizi;
									}
									if (b <= a) {
										return a;
									}
								}
							}
						}else {
							continue;
						}
					}else {
						for(int j=0;j<14;j++) {
							for(int k=0;k<14;k++) {
								if(C1.chessboard[j][k]==0) {
									int t=0;
									for(int m=0;m<=chessboard.index1;m++) {
										if(arr3[m].li.getFirst()[0]==li.get(0)[0]&&arr3[m].li.getFirst()[1]==li.get(0)[1]) {
											for(int n=1;n<arr3[m].li.size();n++) {
												if(t!=0)
													break;
												if(j==arr3[m].li.get(n)[0]&&k==arr3[m].li.get(n)[1]) {
													t++;
												}
											}
											if(t!=0)
												break;
										}
									}
									if(t==0) {
										C1.move(li.get(0)[0],li.get(0)[1],j,k);
										int n = change(chessboard, C1);
										if (n == 0) {
											Array1 arr2=new Array1(li);
											int xy2[]= {j,k};
											arr2.li.add(xy2);
											arr2.setType(Array1.FLY);
											C1.setColor(C1.getAnotherColor());
											int value1 = C1.evaluate(arr2, color);// 行棋得分
											int tmp = maxmin(true, depth - 1, C1, a, b) + value1;
											C1.moveBack1(j, k, li.get(0)[0],li.get(0)[1]);
											C1.setColor(C1.getAnotherColor());
											// beta表示从这个节点往下搜索最坏结局的上界
											if (a < tmp) {
												a = tmp;
												chessboard.bestMove=arr2.li;
												chessboard.bestMoveChi=bestChi;
											}
											if (b <= a) {
												return a;
											}
										} else {
											Array1 arr2=new Array1(li);
											int xy2[]= {j,k};
											arr2.li.add(xy2);
											arr2.setType(Array1.FLY);
											C1.setColor(C1.getAnotherColor());
											int value1 = C1.evaluate(arr2, color);// 行棋得分
											Vector<LinkedList<int[]>> v1 = randomChizi(n, C1);
											for (int a1 = 0; a1 < v1.size(); a1++) {
												LinkedList<int[]> chizi = v1.get(a1);
												if(a1!=0) {
													C1.move(li.get(0)[0],li.get(0)[1],j,k);
												}
												int value2 = C1.randomChizi(chizi,color);
												int tmp = maxmin(true, depth - 1, C1, a, b) + value1 + value2;
												backChizi(C1, chizi);
												C1.moveBack1(j, k, li.get(0)[0],li.get(0)[1]);
												C1.setColor(C1.getAnotherColor());
												// beta表示从这个节点往下搜索最坏结局的上界
												if (a < tmp) {
													a = tmp;
													chessboard.bestMove=arr2.li;
													chessboard.bestMoveChi=chizi;
												}
												if (b <= a) {
													return a;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				return a;
			}else {
				Array1[] arr3=chessboard.getMoveList();
				for (int i=0;i<arr3.length;i++) {
					// 标识此结点的第几个孩子结点，为剪枝做准备
					if(i>chessboard.index)
						break;
					Chess C1=new Chess(chessboard.chessboard,chessboard.color);
					Array1 arr1=arr3[i];
					LinkedList<int[]> li=arr1.li;
					if(arr1.type==2) {
						if(chessboard.isLessFourteen(chessboard.getAnotherColor())&&
								!chessboard.isLessThree(chessboard.getAnotherColor())) {
							if(li.size()==2) {
								continue;
							}
						}
					}
				    for(int j=0;j<li.size()-1;j++) {
						C1.move(li.get(j)[0],li.get(j)[1],li.get(j+1)[0],li.get(j+1)[1]);
					}
					int n=change(chessboard,C1);
					if(n==0) {
						C1.setColor(chessboard.getAnotherColor());
						int value1 = C1.evaluate(arr1, color);// 行棋得分
						int tmp = maxmin(true, depth - 1, C1, a, b) + value1;
						// beta表示从这个节点往下搜索最坏结局的上界
						if (a < tmp) {
							a = tmp;
							chessboard.bestMove=li;
							chessboard.bestMoveChi=bestChi;
						}
						// 剪枝 ：如果从当前格局搜索下去，不可能找到比已知最优解更好的解，则停止这个格局分支的搜索（剪枝），回溯到父节点继续搜索。
						if (b <= a) {
							return a;
						}
					} else {
						C1.setColor(C1.getAnotherColor());
						int value1 = C1.evaluate(arr1, color);// 行棋得分
						Vector<LinkedList<int[]>> v1 = randomChizi(n, C1);
						for (int j = 0; j < v1.size(); j++) {
							LinkedList<int[]> chizi = v1.get(j);
							int value2 = C1.randomChizi(chizi,color);
							int tmp = maxmin(true, depth - 1, C1, a, b) + value1 + value2;
							backChizi(C1, chizi);
							// beta表示从这个节点往下搜索最坏结局的上界
							if (a < tmp) {
								a = tmp;
								chessboard.bestMove=li;
								chessboard.bestMoveChi=chizi;
							}
							// 剪枝 ：如果从当前格局搜索下去，不可能找到比已知最优解更好的解，则停止这个格局分支的搜索（剪枝），回溯到父节点继续搜索。
							if (b <= a) {
								return a;
							}
						}
					}
				}
				return a;
			}
		}
	}

	public int change(Chess c1,Chess c2) {
		int n=0;
		int[] num1=c1.Squarenum(c1.color);
		int[] num2=c2.Squarenum(c1.color);
		for(int i=0;i<num1.length;i++) {
			if(num1[i]==0&&num2[i]==1) {
				n++;
			}
		}
		return n;
	}
	
	public void backChizi(Chess c1,LinkedList<int[]> li) {
		for(int i=0;i<li.size();i++) {
			c1.chessboard[li.get(i)[0]][li.get(i)[1]]=c1.color;
		}
	}
	
	public Vector<LinkedList<int[]>> randomChizi(int n,Chess c1) {
		Vector<LinkedList<int[]>> v1=new Vector();
		if(n==1) {
			for(int i=0;i<c1.chessboard.length;i++) {
				for(int j=0;j<c1.chessboard[i].length;j++) {
					if(c1.chessboard[i][j]==c1.color) {
						int[] xy= {i,j};
						LinkedList<int[]> li=new LinkedList<int[]>();
						li.add(xy);
						v1.add(li);
					}
				}
			}
		}else if(n==2){
			for(int i=0;i<c1.chessboard.length;i++) {
				for(int j=0;j<c1.chessboard[i].length;j++) {
					if(c1.chessboard[i][j]==c1.color) {
						int[] xy= {i,j};
						for(int k=i;k<c1.chessboard.length;k++) {
							for(int m=j+1;m<c1.chessboard.length;m++) {
								if(c1.chessboard[k][m]==c1.color) {
									LinkedList<int[]> li=new LinkedList<int[]>();
									int[] xy2= {k,m};
									li.add(xy);
									li.add(xy2);
									v1.add(li);
								}
							}
						}
					}
				}
			}
		}
		return v1;
	}
}



