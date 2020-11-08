package ����;
public class dalian {
	private int[][] a = new int[3][4];
	private int type = 0;//��������
	private int num = 0;
	private int color = 0;//��ɫ
	private int othercolor = 0;
	private int standpointX = 0;//���ϽǱ�־������
	private int standpointY = 0;
	private int eatpointX = 0;//�Ƽ����ֵ������
	private int eatpointY = 0;
	private int endpointX = 0;
	private int endpointY = 0;
	private int direction = 0;//���ƻ�����
	public static final int DAN = 1;
	public static final int SHUANG = 2;
	private boolean move = true;
	

	public dalian(int[][] a , int color) {
		super();
		this.a = a;
		this.color = color;
	}
	
	public int getOthercolor() {
		if(color == 1) {
			othercolor = 2;
		}else if(color == 2) {
			othercolor = 1;
		}
		return othercolor;
	}

	public void count() {
		if(a[0][0]== color &&a[0][1]==color&&a[0][2]==color&&a[0][3]==color&&a[1][0]==color&&a[1][1]==color&&a[1][3]==color&&a[1][2]!=color) {
			type = type+1;
			num++;
			if(a[1][2] == getOthercolor()) {
				move = false;
			}
		}
		if(a[0][0]==color&&a[0][1]==color&&a[0][2]!=color&&a[0][3]==color&&a[1][0]==color&&a[1][1]==color&&a[1][3]==color&&a[1][2]==color) {
			type = type+2;
			num++;
			if(a[0][2] == getOthercolor()) {
				move = false;
			}
		}
		if(a[0][0]==color&&a[0][1]==color&&a[1][0]==color&&a[1][1]==color&&a[1][3]==color&&a[2][2]==color&&a[2][3]==color&&a[1][2]!=color) {
			type = type+4;
			num++;
			if(a[1][2] == getOthercolor()) {
				move = false;
			}
		}
		if(a[0][0]==color&&a[0][1]==color&&a[0][2]==color&&a[0][3]==color&&a[1][0]==color&&a[1][1]!=color&&a[1][3]==color&&a[1][2]==color) {
			type = type+7;
			num++;
			if(a[1][1] == getOthercolor()) {
				move = false;
			}
		}
		if(a[0][0]==color&&a[0][1]!=color&&a[0][2]==color&&a[0][3]==color&&a[1][0]==color&&a[1][1]==color&&a[1][3]==color&&a[1][2]==color) {
			type = type+12;
			num++;
			if(a[0][1] == getOthercolor()) {
				move = false;
			}
		}
		if(a[1][0]==color&&a[1][1]!=color&&a[0][2]==color&&a[0][3]==color&&a[2][0]==color&&a[2][1]==color&&a[1][3]==color&&a[1][2]==color) {
			type = type+20;
			num++;
			if(a[1][1] == getOthercolor()) {
				move = false;
			}
		}
		if(a[1][0]==color&&a[1][1]==color&&a[1][2]==color&&a[1][3]==color&&a[2][0]==color&&a[2][1]==color&&a[2][2]!=color&&a[2][3]==color) {
			type = type+33;
			num++;
			if(a[2][2] == getOthercolor()) {
				move = false;
			}
		}
		if(a[1][0]==color&&a[1][1]==color&&a[1][2]!=color&&a[1][3]==color&&a[2][0]==color&&a[2][1]==color&&a[2][2]==color&&a[2][3]==color) {
			type = type+54;
			num++;
			if(a[1][2] == getOthercolor()) {
				move = false;
			}
		}
		if(a[1][0]==color&&a[1][1]==color&&a[0][2]==color&&a[0][3]==color&&a[2][0]==color&&a[2][1]==color&&a[1][3]==color&&a[1][2]!=color) {
			type = type+88;
			num++;
			if(a[1][2] == getOthercolor()) {
				move = false;
			}
		}
		if(a[1][0]==color&&a[1][1]==color&&a[1][2]==color&&a[1][3]==color&&a[2][0]==color&&a[2][1]!=color&&a[2][2]==color&&a[2][3]==color) {
			type = type+143;
			num++;
			if(a[2][1] == getOthercolor()) {
				move = false;
			}
		}
		if(a[1][0]==color&&a[1][1]!=color&&a[1][2]==color&&a[1][3]==color&&a[2][0]==color&&a[2][1]==color&&a[2][2]==color&&a[2][3]==color) {
			type = type+232;
			num++;
			if(a[1][1] == getOthercolor()) {
				move = false;
			}
		}
		if(a[0][0]==color&&a[0][1]==color&&a[1][0]==color&&a[1][1]!=color&&a[1][3]==color&&a[2][2]==color&&a[2][3]==color&&a[1][2]==color) {
			type = type+376;
			num++;
			if(a[1][1] == getOthercolor()) {
				move = false;
			}
		}
    }

	public int getNum() {
		return num;
	}
	
	public int getType() {//������������
		return type;
	}
	
	public int getColor() {//������ɫ
		return color;
	}
	
	public int getmold() {//�жϵ�˫����
		if(type==1 || type==2 ||type==4 || type==7 || type==12 || type==20 || type==33 || type==54 || type==88 || type==143 || type==232 || type==376) {
			return DAN;
		}else {
			return SHUANG;
		}
	}

	public void setStandpointX(int standpointX) {
		this.standpointX = standpointX;
	}

	public void setStandpointY(int standpointY) {
		this.standpointY = standpointY;
	}

	public int getStandpointX() {
		return standpointX;
	}

	public int getStandpointY() {
		return standpointY;
	}
	
	public void couteatPoint() {
		if(type==1 || type==5 || type==4 || type ==89 || type==58 || type==142 || type==147 || type==54 || type==88) {
			eatpointX = standpointX + 1;
			eatpointY = standpointY + 1;
			endpointX = standpointX + 1;
			endpointY = standpointY + 2;
		}else if(type==2 ) {
			eatpointX = standpointX ;
			eatpointY = standpointY + 1;
			endpointX = standpointX ;
			endpointY = standpointY + 2;
			
		}else if(type==7 || type==20 || type==376 || type==383 || type==27 || type==608 ||type==252 || type==635 || type==232) {
			eatpointX = standpointX + 1;
			eatpointY = standpointY + 2;
			endpointX = standpointX + 1;
			endpointY = standpointY + 1;
		}else if(type==12) {
			eatpointX = standpointX ;
			eatpointY = standpointY + 2;
			endpointX = standpointX  ;
			endpointY = standpointY + 1;
		}else if(type==33) {
			eatpointX = standpointX + 2;
			eatpointY = standpointY + 1;
			endpointX = standpointX + 2;
			endpointY = standpointY + 2;
		}else if(type==143) {
			eatpointX = standpointX + 2;
			eatpointY = standpointY + 2;
			endpointX = standpointX + 2;
			endpointY = standpointY + 1;
		}
	}

	public int getEatpointX() {
		return eatpointX;
	}

	public int getEatpointY() {
		return eatpointY;
	}
	
	public int getStartpointX() {
		return eatpointX;
	}
	
	public int getStartpointY() {
		return eatpointY;
	}
	
	public int getEndpointX() {
		return endpointX;
	}

	public int getEndpointY() {
		return endpointY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	
	public boolean getMove() {
		return move;
	}

	public void countXY() {
		if(getDirection()==2) {
			int m,n,p;
			m=standpointX;
			standpointX=standpointY;
			standpointY=m;
			n=eatpointX;
			eatpointX=eatpointY;
			eatpointY=n;
			p=endpointX;
			endpointX=endpointY;
			endpointY=p;
		}
	}
	
	public void getInformation() {
		System.out.println("���ϽǱ�׼��λ�ã�("+getStandpointX()+","+getStandpointY()+")");
		System.out.println("���ƻ�����(�ᣨ1��������2��)��"+getDirection());
    	System.out.println("��������:"+getType());
    	System.out.println("��������(����1����˫��2��)��"+getmold());
    	System.out.println("�Ƽ�����λ�ã�("+getEatpointX()+","+getEatpointY()+")");
    	System.out.println("�ҷ����������ʼ�㣺("+getStartpointX()+","+getStartpointY()+")");
    	System.out.println("�ҷ�����������ֹ�㣺("+getEndpointX()+","+getEndpointY()+")");
    	System.out.println("�����Ƿ���ƶ���"+getMove());
    	System.out.println();
	}
}
