import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
public class world {

	public int m,n;
	public int scale = 20;
	public int TOP = 30;

	public world(){

	}

	// type 0 void, 1 snake, 2 point
	public int[] sx;
	public int[] sy;
	public int ls;

	public int npx,npy;

	int map[][];

	public void init(int nm, int nn,int ns){
		scale = ns;
		m = nm;
		n = nn;
		sx = new int[m*n+1];
		sy = new int[m*n+1];
		ls = 1;
		sx[0] = 0;
		sy[0] = 0;
		map = new int[m][n];
		for (int i=0;i<m;i++)
			for (int j=0;j<n;j++)
				map[i][j]=0;
		map[0][0] = 1;
		genPoint();
		map[npx][npy] = 2;
		createSol(m,n);
		System.out.println("Init ready"+npx+" "+npy+" "+sx[0]+" "+sy[0]);
	}

	// dir = 1 2 3 4 up down left right
	int dx[] = {0,0,-1,1};
	int dy[] = {-1,1,0,0};


	public void genPoint(){
		int nx,ny;

		nx = (int)(Math.random()*m);
		ny = (int)(Math.random()*n);
		while(!(isValid(nx,ny)&&map[nx][ny]==0)){
			nx = (int)(Math.random()*m);
			ny = (int)(Math.random()*n);
		}			
		npx = nx;
		npy = ny;
		map[npx][npy]=2;
	}

	public int sol[][];
	public void createSol(int m,int n){
		sol = new int[m][n];


		if (m<2||n<2){
			System.err.println("No soultion!!");
		}

		if (m%2==0){
			for(int i =0;i<m;i++) sol[i][0]=2;
			for(int i =0;i<n;i++) sol[0][i]=1;
			for(int i =0;i<m;i++) {
				if (i%2==0) sol[i][n-1]=3;
				else
					sol[i][n-1]=0;
			}
			for(int i=1;i<=m-2;i++) {
				if (i%2==0) sol[i][1]=1;
				else
					sol[i][1]=3;
			}
			for(int i=2;i<=n-2;i++)
				for (int j=0;j<m;j++)
				{
					if (j%2==0)
						sol[j][i]=1;
					else
						sol[j][i]=0;
				}

		}


	}

	public int refreshMap(int nx, int ny){
		if (map[nx][ny]==0){			
			map[nx][ny]=1;
			map[sx[ls-1]][sy[ls-1]]=0;
			for(int i =ls-1;i>0;i--)
			{
				sx[i]=sx[i-1];
				sy[i]=sy[i-1];
			}
			sx[0]=nx;
			sy[0]=ny;
			return 1;
		}
		else if(map[nx][ny]==2){
			map[nx][ny]=1;
			ls = ls+1;
			for(int i =ls-1;i>0;i--)
			{
				sx[i]=sx[i-1];
				sy[i]=sy[i-1];
			}
			sx[0]=nx;
			sy[0]=ny;


			if (ls==n*m) return 3;
			else
				genPoint();

			return 1;
		}
		else{
			return 0;
		}
	}

	public int move(int dir){
		boolean useAI = true;
		int nx,ny;
		if (useAI){
			dir =sol[sx[0]][sy[0]]; 
		}
		nx = sx[0]+dx[dir];
		ny = sy[0]+dy[dir];		

		if (isValid(nx,ny)){
			int ans = refreshMap(nx,ny);
			return ans;
		}


		return 0;

	}

	Color color[] = {Color.black,Color.white,Color.yellow};
	public void draw(Graphics g, JFrame f){
		for (int i = 0; i<m;i++) for(int j=0;j<n;j++)
		{			
			g.setColor(color[map[i][j]]);
			g.fillRect(i*scale, j*scale+TOP, scale, scale);
			g.setColor(Color.black);
			//			g.drawRoundRect(x, y, width, height, arcWidth, arcHeight)
			g.drawRoundRect(i*scale, j*scale+TOP, scale, scale,scale,scale);
		}

	}

	public boolean isValid(int x, int y){
		if (x>=0&&x<m&&y>=0&&y<n){				
			return true;
		}
		return false;
	}
	public static void main(String args[])
	{
		world w = new world();
		int m=4;
		int n=6;
		w.createSol(m, n);
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++)
			{
				System.out.print(w.sol[j][i]+" ");
			}
			System.out.println();
		}
	}

}
