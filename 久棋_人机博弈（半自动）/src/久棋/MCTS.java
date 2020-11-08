package 久棋;

import 久棋.MCTS.Node;

public class MCTS {
	public static final int BUDGET = 100000;
	public static final int BLACKWIN = 2;
	public static final int WHITEWIN = 1;
	public static final int NOTWIN = 0;
	public static final int BLACK = 2;
	public static final int NOCHESS = 0;
	public static final int WHITE = 1;
	public static final int ERROR = -999;
	public static final double UCTCP = Math.sqrt(2);
	public static final int NOTFILLED = -1;
	public int rows;
	public Node rootNode;
	public MCTS(int[][] chessmans) {
		this.rows = chessmans.length;
		this.rootNode = new Node(null, chessmans);
		while (rootNode.accessNumber<BUDGET) {
			Node node = treePolicy(rootNode);
			int reward = defaultPolicy(node);
			backup(node, reward);
		}
	}
	class Node {
		public int[][] chessmans;//当前结点棋盘状态
		public double[][] rewardMap;//当前棋盘状态结点，落子的概率图
		public int[][] accessNumberMap;//当前棋盘状态结点，访问数量
		public int accessNumber;//当前节点总访问数
		public double reward;
		//下一节点集合
		public Node[][] nextNode;
		public Node lastNode;
		//本节点的最后一个棋子
		public int[] chessXY;
		public int flag=0;
		//下一节点颜色
		int nextColor;
		public Node(Node lastNode, int[][] chessmans) {
			this.chessmans = chessmans;
			this.lastNode = lastNode;
			this.nextColor = nextChessColor(chessmans);
			nextNode = new Node[rows][rows];
			accessNumber = 0;
			reward = 0;
			rewardMap = new double[rows][rows];
			accessNumberMap = new int[rows][rows];
			chessXY = new int[2];
		}
		public void setChessXY(int X, int Y) {
			this.chessXY[0] = X;
			this.chessXY[1] = Y;
		}
	}
	public double[][] getProbabilityMap() {
		return rootNode.rewardMap;
	}
	
	public int[] getBestChild() {
		int[] xy= {-1,-1};
		double maxUCT = 0;
		int maxUCTi = -1;
		int maxUCTj = -1;
		double minmum=Double.MAX_VALUE;
		for(int col_i=0; col_i<rows; col_i++) {
			for(int row_j=0; row_j<rows; row_j++) {
				if(this.rootNode.chessmans[col_i][row_j] == NOCHESS ) {
					double tempMaxUCT = (double)(rootNode.rewardMap[col_i][row_j])/(double)(rootNode.accessNumberMap[col_i][row_j]);
				//	System.out.println(rootNode.rewardMap[col_i][row_j]+"/"+rootNode.accessNumberMap[col_i][row_j]);
					if(tempMaxUCT > maxUCT) {
						double minmum1=Math.abs((double)(col_i)-6.5)+Math.abs((double)(row_j)-6.5);
						maxUCT = tempMaxUCT;
						maxUCTi = col_i;
						maxUCTj = row_j;
						minmum=minmum1;
					}else if(tempMaxUCT == maxUCT) {
						double minmum1=Math.abs((double)(col_i)-6.5)+Math.abs((double)(row_j)-6.5);
						if(minmum1<minmum) {
							minmum=minmum1;
							maxUCT = tempMaxUCT;
							maxUCTi = col_i;
							maxUCTj = row_j;
						}	
					}
				}
			}
		}
		if(maxUCTi == -1 || maxUCTj == -1) {
			System.out.println("error：没找到极大值UCT");
			for(int col_i=0; col_i<rows; col_i++) {
				for(int row_j=0; row_j<rows; row_j++) {
					if(rootNode.chessmans[col_i][row_j] == NOCHESS) {
						double tempMaxUCT =  (double)(rootNode.rewardMap[col_i][row_j])/(double)(rootNode.accessNumberMap[col_i][row_j]);
						System.out.println("error:"+tempMaxUCT+"="+rootNode.rewardMap[col_i][row_j]+"/"+rootNode.accessNumberMap[col_i][row_j]);
					}
				}
			}
		}
		xy[0]=maxUCTi;
		xy[1]=maxUCTj;
		return xy;
	}
	public Node treePolicy(Node node) {
		while (node != null) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < rows; j++) {
					if (null == node.nextNode[i][j] && NOCHESS == node.chessmans[i][j]) {
						return expand(node, i, j);
					}
				}
			}
			node = bestChild(node);
			if (node.flag == 1)
				return node;
		}
		return node;

	}
	//扩张下一个为扩展的动作
	public Node expand(Node node, int X, int Y) {
		int[][] tempChessmans = new int[rows][rows];
		for(int i=0; i<rows; i++) {
			for(int j=0; j<rows; j++) {
				tempChessmans[i][j] = node.chessmans[i][j];
			}
		}
		int tempNextColor = node.nextColor;
		tempChessmans[X][Y] = tempNextColor;
		node.nextNode[X][Y] = new Node(node, tempChessmans);
		node.nextNode[X][Y].setChessXY(X, Y);
		return node.nextNode[X][Y];
	}
	
	//UCT部分精髓
	public Node bestChild(Node node) {
		double maxUCT = 0;
		int maxUCTi = -1;
		int maxUCTj = -1;
		int k = 0;
		double minmum = Double.MAX_VALUE;
		for (int col_i = 0; col_i < rows; col_i++) {
			for (int row_j = 0; row_j < rows; row_j++) {
				if (NOCHESS == node.chessmans[col_i][row_j]) {
					k++;
					double tempMaxUCT = (double) ((double) (node.rewardMap[col_i][row_j])
							/ (double) (node.accessNumberMap[col_i][row_j])
							+ UCTCP * (double) (Math.sqrt(2 * (double) (Math.log(node.accessNumber))
									/ (double) (node.accessNumberMap[col_i][row_j]))));
					if (tempMaxUCT > maxUCT) {
						double minmum1 = Math.abs((double) (col_i) - 6.5) + Math.abs((double) (row_j) - 6.5);
						maxUCT = tempMaxUCT;
						maxUCTi = col_i;
						maxUCTj = row_j;
						minmum = minmum1;
					} else if (tempMaxUCT == maxUCT) {
						double minmum1 = Math.abs((double) (col_i) - 6.5) + Math.abs((double) (row_j) - 6.5);
						if (minmum1 < minmum) {
							minmum = minmum1;
							maxUCT = tempMaxUCT;
							maxUCTi = col_i;
							maxUCTj = row_j;
						}
					}
				}
			}
		}
		if (maxUCTi == -1 && maxUCTj == -1 && k == 0) {
			node.flag = 1;
			return node;
		}
		if (maxUCTi == -1 || maxUCTj == -1) {
			System.out.println("error：没找到极大值UCT");
			for (int col_i = 0; col_i < rows; col_i++) {
				for (int row_j = 0; row_j < rows; row_j++) {
					if (NOCHESS == node.chessmans[col_i][row_j]) {
						double tempMaxUCT = (double) ((double) (node.rewardMap[col_i][row_j])
								/ (double) (node.accessNumberMap[col_i][row_j])
								+ UCTCP * (double) (Math.sqrt(2 * (double) (Math.log(node.accessNumber))
										/ (double) (node.accessNumberMap[col_i][row_j]))));
						System.out.println("error:" + tempMaxUCT + "=" + node.rewardMap[col_i][row_j] + "/"
								+ node.accessNumberMap[col_i][row_j] + "+" + UCTCP + "*" + "Math.sqrt(2*"
								+ Math.log(node.accessNumber) + "/" + node.accessNumberMap[col_i][row_j] + ")");
					}
				}
			}
		}

		return node.nextNode[maxUCTi][maxUCTj];
	}
	//默认策略
	public int defaultPolicy(Node node) {
		int nextColor = node.nextColor;
		int[][] tempChessmans = new int[rows][rows];
		for(int col_i=0; col_i<rows; col_i++) {
			for(int row_j=0; row_j<rows; row_j++) {
				tempChessmans[col_i][row_j] = node.chessmans[col_i][row_j];
			}
		}
		while (NOTFILLED == isCheckTerminal(tempChessmans)) {
			int[] nextXY = new int[2];
			nextXY = randomNext(tempChessmans);
			tempChessmans[nextXY[0]][nextXY[1]] = nextColor;
			if (nextColor == BLACK) {
				nextColor = WHITE;
			} else {
				nextColor = BLACK;
			}
		}
		int color1=node.nextColor;
		return stateReward(tempChessmans, color1);

	}

	//对于该棋盘状态的奖励
	public int stateReward(int[][] chessmans,int nextColor) {
		final int win = 2;
		final int loss = 0;
		final int flat = 1;
		if(isCheckTerminal(chessmans) == BLACKWIN) {
			if(nextColor==BLACK) {
				return win;
			}else {
				return loss;
			}
		}else if(isCheckTerminal(chessmans) == WHITEWIN){
			if(nextColor==WHITE) {
				return win;
			}else {
				return loss;
			}
		}else {
			return flat;
		}
	}
	//奖励回传
	public void backup(Node lastNode, int reward) {
		//最后一个结点不需要记录其子节点的访问
		lastNode.accessNumber++;
		lastNode.reward +=reward;
		int[] chessXY = lastNode.chessXY;
		lastNode = lastNode.lastNode;
		while(lastNode != null) {
			//最后一个结点之前的所有节点都要记录其子节点的访问
			double relative_position=1-(Math.abs((double)(chessXY[0])-6.5)*Math.abs((double)(chessXY[0])-6.5)+
					Math.abs((double)(chessXY[1])-6.5)*Math.abs((double)(chessXY[1])-6.5))/(6.5*6.5*2);
			lastNode.accessNumberMap[chessXY[0]][chessXY[1]]++;
			lastNode.accessNumber++;
			if(reward==2) {
				reward=0;
			}else if(reward==1) {
				reward=1;
			}else {
				reward=2;
			}
			lastNode.rewardMap[chessXY[0]][chessXY[1]] += 0.7*reward*+0.3*reward*relative_position;
			lastNode.reward += reward*relative_position;
			chessXY = lastNode.chessXY;
			lastNode = lastNode.lastNode;
		}
	}
	//返回当前棋盘的下一个棋子的颜色
	public int nextChessColor(int[][] chessmans) {
		int whiteNumber = 0;
		int blackNumber = 0;
		for(int col_i=0; col_i<rows; col_i++) {
			for(int row_j=0; row_j<rows; row_j++) {
				if(chessmans[col_i][row_j] == BLACK) {
					blackNumber++;
				}
				if(chessmans[col_i][row_j] == WHITE) {
					whiteNumber++;
				}
			}
		}
		if(whiteNumber-blackNumber == 1) {
			return BLACK;
		}else if(whiteNumber-blackNumber == 0) {
			return WHITE;
		}else {
			System.out.println("error：棋盘棋子不同颜色数量差值不为1或0，为 黑"+blackNumber+" 白"+whiteNumber);
			return ERROR;
		}
	}

	//按照均等概率随机返回一个当前棋盘的落子位置的XY坐标

	public int[] randomNext(int[][] chessmans) {
		int[] nextXY = new int[2];
		int totalNumber1 = 0;
		for (int col_i = 0; col_i < 14; col_i++) {
			for (int row_j = 0; row_j < 14; row_j++) {
				if (chessmans[col_i][row_j] != NOCHESS) {
					totalNumber1++;
				}
			}
		}
		int remainNumber = rows * rows - totalNumber1;
		int randomNumber = (int) (Math.random() * remainNumber);
		int nowNumber = 0;
		for (int col_i = 0; col_i < rows; col_i++) {
			for (int row_j = 0; row_j < rows; row_j++) {
				if (chessmans[col_i][row_j] == NOCHESS) {
					if (nowNumber == randomNumber) {
						nextXY[0] = col_i;
						nextXY[1] = row_j;
						return nextXY;
					}
					nowNumber++;
				}
			}
		}
		return nextXY;
	}

	//检查当前结点是否为终端结点
	public int isCheckTerminal(int[][] chessmans) {
		int totalChessNumber1=0;
		for(int i=0;i<14;i++) {
			for(int j=0;j<14;j++) {
				if(chessmans[i][j]!=0) {
					totalChessNumber1++;
				}
			}
		}
		if(totalChessNumber1<rows*rows) {
			return NOTFILLED;
		}else{
			Score score1 = new Score(chessmans, nextChessColor(chessmans));
			if (nextChessColor(chessmans) == WHITE) {
				Score score2 = new Score(chessmans, BLACK);
				if (score1.getTotalScore() > score2.getTotalScore()) {
					return WHITEWIN;
				} else if (score1.getTotalScore() == score2.getTotalScore()) {
					return NOTWIN;
				} else {
					return BLACKWIN;
				}
			} else {
				Score score2 = new Score(chessmans, WHITE);
				if (score1.getTotalScore() > score2.getTotalScore()) {
					return BLACKWIN;
				} else if (score1.getTotalScore() == score2.getTotalScore()) {
					return NOTWIN;
				} else {
					return WHITEWIN;
				}
			}
		}
	}

}

