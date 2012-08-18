package com.hanghang.codestore.algo;


/**
 * 蛇形数组
 * 
 * n=3
 * 1 2 3 
 * 8 9 4
 * 7 6 5
 * 最基本的直观爬行方式实现
 * @author junfeng.chen
 *
 */
public class SnakeArray 
{

	private int array[][] = null;
	private int k = 0;
	public SnakeArray(int n)
	{
		array = new int[n][n];
		k = n*n;
		init();
	}
	
	public SnakeArray(int m,int n)
	{
		array = new int[m][n];
		k=m*n;
		init();
	}
	
	/**
	 * print array
	 * 
	 * for i = 
	 * 
	 */
	public void print()
	{
		
		
		
		int i=0,j=0;
		for(i=0;i<array.length;i++)
		{
			for(j=0;j<array[i].length;j++)
			{
				System.out.print(array[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	private void init()
	{
		int i=0,j=0,n=1;//i 行 j列
		while(n<=k)
		{
			
			while(j<array[0].length&&array[i][j]==0&&n<=k)//move right
			{
				array[i][j++]=n++;
			}
			
			i++;
			j--;
			while(i<array.length&&array[i][j]==0&&n<=k)//move down
			{
				array[i++][j]=n++;
			}
			
			i--;
			j--;
			while(j>=0&&array[i][j]==0&&n<=k)//move left
			{
				array[i][j--]=n++;
			}
			
			i--;
			j++;
			while(i>0&&array[i][j]==0&&n<=k)//move up
			{
				array[i--][j] = n++;
			} 
			i++;
			j++;
		}
			
	}
	
	
	public static void main(String args[])
	{
		SnakeArray  snake = new SnakeArray(10);
		snake.print();
		
		System.out.println();
		 snake = new SnakeArray(8,5);
		snake.print();
	}
	
}

