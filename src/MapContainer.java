public class MapContainer {

	public Octree root;
	public MapContainer(int level){
		root = new Octree(level,0L,0L);
	}
	
	public int[][] retrieve(Long[] range)
	{
		int xr = (int)(range[2]-range[1]+1);
		int yr = (int)(range[4]-range[3]+1);
		
		int ret[][] = new int[xr][yr];
		
		for(Long i = range[1];i<=range[2];i++)
			for(Long j = range[3];j<=range[4];j++) {
				int x = (int)(i - range[1]);
				int y = (int)(j - range[3]);
				ret[x][y] = root.getValue(i, j);
				
			}
		return ret;		
	}

	public static void main(String[]args)	{
		MapContainer m = new MapContainer(20);
		System.out.println(m.root.getValue(5L, 10L));
		m.root.traverse();
	}
}

class Octree {

	public int value;
	public int level;
	public boolean spanned = false;
	public Octree[] subTree = new Octree[5];
	public Long[] range = new Long[5];
	public Long midx;
	public Long midy;


	public Octree(int nlevel, Long sx, Long sy){
		if (nlevel<0)
			System.err.println("Level is too low: "+nlevel);
		level = nlevel;
		range[1] = sx;
		range[2] = sx+(1<<level);
		range[3] = sy;
		range[4] = sy+(1<<level);
		midx = (range[2]+range[1])/2;
		midy = (range[3]+range[4])/2;
	}

	public int locate(Long x, Long y){
		if (x<range[1] || x>range[2] || y<range[3] || y>range[4]){
			return -1;
		}
		if (level==0)
			return 0;

		int state = 1;
		if (x>midx) state = state + 1;
		if (y>midy) state = state + 2;

		return state;
	}




	public void setValue(Long x, Long y, int newValue){
		if (!spanned){
			if (level==0)
				spanned = true;
			else{
				spanned = true;
				Long[] lx = {0L,range[1],midx+1,range[1],midx+1};
				Long[] ly = {0L,range[3],range[3],midy+1,midy+1};
				for (int i = 1;i<=4;i++)
					subTree[i] = new Octree(level-1,lx[i],ly[i]);
			}	
		}

		int pos = locate(x,y);
		switch(pos){
		case -1: System.err.println("find the wrong place!"); break;
		case 0: value = newValue; break;
		default: subTree[pos].setValue(x, y, newValue);
		}
		return;
	}

	
	public int getValue(int i, int j) {
		Long x = (long) i;
		Long y = (long) j;
				
		return getValue(x,y);
	}
	public int getValue(Long x, Long y){
		if (!spanned){
			if (level==0){
				spanned = true;
				value = randGen();
			}
			else{
				spanned = true;
				Long[] lx = {0L,range[1],midx+1,range[1],midx+1};
				Long[] ly = {0L,range[3],range[3],midy+1,midy+1};
				subTree[0] = null;
				for (int i = 1;i<=4;i++)
					subTree[i] = new Octree(level-1,lx[i],ly[i]);
			}	
		}

		int pos = locate(x,y);
		switch(pos){
		case -1: 
			System.out.println("find the wrong place! Get value "+x+","+y+" returned -1, Level="+level);
			for(int i = 1;i<=4;i++)
			System.out.print(range[i]+",");
			System.out.println();
			return -1;
		case 0: return value; 
		default: return subTree[pos].getValue(x, y);
		}

	}

	
	public void traverse(){
//		String s = "";
//		s = s+level+" ";
		System.out.print(level+" ");
		if (level>0 && spanned)
			for (int i = 1;i<=4;i++)
				subTree[i].traverse();
		
	}

	public int randGen(){
		return (int)(Math.round(Math.random()));
	}


}