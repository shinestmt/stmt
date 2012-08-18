package com.hanghang.codestore.algo.calc24;


import java.util.List;
import java.util.ArrayList;

public class PermUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		List<String[]> list = perm(new String[]{"a","b","c","d"},0,4,null);
		List<String> source = new ArrayList<String>();
		String ss [] = list.get(0);
		for(String s:ss)
		{
			source.add(s);
		}
		List<List<String>> container = new ArrayList<List<String>>();
		System.out.println(container.size());
//		for(String[] ss:list)
//		{
//			printArray(ss);
//		}
		System.out.println(list.size());
	}
	
	/**
	 * 
	 * void perm (char *a, const int k，const int n) {  
// n 是数组a的元素个数，生成a[k],…,a[n-1]的全排列
    int i;
    if (k = = n-1) {     // 终止条件，输出排列
        for ( i=0; i<n; i++)  cout << a[i] << “ ”;   // 输出包括前
                                                   // 缀，以构成整个问题的解
        cout << endl;
}
    else {    //  a[k],…,a[n-1] 的排列大于1，递归生成
        for ( i = k; i < n; i++) {
            char temp = a[k]; a[k] = a[i]; a[i] = temp;  	// 交换a[k] 
							            // 和 a[i]
        perm(a,k+1,n);  // 生成 a[k+1],…,a[n-1]的全排列
        temp = a[k]; a[k] = a[i]; a[i] = temp; 	// 再次交换 a[k] 和
						            // a[i] , 恢复原顺序
     }
  } // else结束
}   // perm结束
	 * 求全排列
	 * @param s
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String[]> perm(String []s,int k,int n,List<String[]> result)
	{
		List<String[]> result1= result;
		if (k == n-1)
		{
			String ss [] = new String[s.length];
			for( int i = 0;i<=n-1; i++)
			{
				ss[i] = s[i];
			}
			if(result1==null)
				result1 = new ArrayList<String[]>();
				result1.add(ss);	
				
		}else
		{
			for(int i = k;i<n;i++)
			{
				String tmp = s[k];
				s[k] = s[i];
				s[i] = tmp;
				result1 = perm(s,k+1,n,result1);
				tmp = s[k];
				s[k] = s[i];
				s[i] = tmp;
			}
		}
		
		return result1;
	}

	static void printArray(String[] a)
	{
		for(String s:a)
		{
			System.out.print(s+" ");
		}
		System.out.println();
	}
}

