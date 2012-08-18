package com.hanghang.codestore.algo.calc24;


import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class OperationUtil 
{

	/**
	 * 中缀表达式转为后缀表达式
	 * (1)获取下一个输入标记
	 * (2)如果标记是:
	 * 左括号:将其压入表达式栈
	 * 右括号:连续弹出并显示栈中的元素，直到遇到一个左括号
	 * 一个运算符:如果栈为空，或者标记具有比栈顶元素更高的优先级，则将其压入栈中，否则弹出并显示
	 *           栈顶元素；接着继续比较标记和新的栈顶元素
	 * 一个操作数:显示它
	 * (3)当到到中缀表达式结尾时，弹出并显示栈中的元素直到栈空
	 * @param middleFixString
	 * @param fraction
	 * @return
	 */
	public static List<String> toPostfixExp(String middleFixString,boolean fraction)
	{
		
		Stack<String> opSt = new Stack<String>();
		List<String> postExpList = new ArrayList<String>();
		List<String> eleList = getElementList(middleFixString,fraction);
		for(String ele:eleList)
		{
			char c = ele.charAt(0);
			if(ele.length()==1 && !(c>='0' && c<='9'))
			{
				switch(c)
				{
				case '(' :
					opSt.push(ele);
					break ;
				case ')' :
					for(;;)
					{
						if(opSt.isEmpty())
							break;
						String op = opSt.pop();
						if(op .equals("("))
							break;
							postExpList.add(op);
						
					}
					break ;
				default:
			
					if(opSt.isEmpty())
						opSt.push(ele);
					else
					{//比较优先级
						while(!opSt.isEmpty())
						{
							char opTop = opSt.peek().charAt(0);
							if(opSt.isEmpty()||opTop == '(')
							{
								opSt.push(ele);
								break;
							}
							
							if(getPriority(c)>getPriority(opTop))
							{
								opSt.push(ele);
								break;
							}
							else
							{
								postExpList.add(opSt.pop());
								opSt.push(ele);
								break;
							}
						}
													
					}
				}
			}
			else
			{
				postExpList.add(ele);
			}
		}
		
		while(!opSt.isEmpty())
		{
			postExpList.add(opSt.pop());
		}
		return postExpList;
	}
	
	
	

	private static List<String> getElementList(String middleFixExp,boolean fraction)
	{
		
		List<String> list = new ArrayList<String>();
		char [] seprator = null;
		if(fraction)
			seprator = new char[]{'+','-','*','(',')'};
		else
			seprator = new char[]{'+','-','/','*','(',')'};
		int opBeginIndx = -1;
		boolean isOperator = false;
		
		for(int i=0;i<middleFixExp.length();i++)
		{
			char curChar  = middleFixExp.charAt(i);
			isOperator = false;
			for(int j=0;j<seprator.length;j++)
			{
				if(seprator[j] == curChar)
				{   
					if(opBeginIndx != -1)
						list.add(middleFixExp.substring(opBeginIndx, i));
					list.add(curChar+"");
					isOperator  = true;
					opBeginIndx = -1;
					break;
				}
			}
			if(isOperator)
				continue;
			if(opBeginIndx == -1)
				opBeginIndx = i;
		}
		if(opBeginIndx != -1)
			list.add(middleFixExp.substring(opBeginIndx));
		return list;
	}
	
	public static String evaluate(String exp,boolean fraction) throws Exception
	{
	
		Stack<String> sta = new Stack<String>();
		List<String> list = toPostfixExp(exp,fraction);
		String s ="";
		while(list.size()>0)
		{
			s = list.remove(0);
			if(s.length()==1 && !(s.charAt(0)>='0'&&s.charAt(0)<='9'))
			{
				String y = sta.pop();
				String x = sta.pop();
				
				if("+-*/".indexOf(s)>=0)
				{
					String result = "";
					if(fraction)
						result =caculateFraction(x,y,s.charAt(0));
					else
						result =caculateInteger(x,y,s.charAt(0));
					sta.push(result);
						
				}
				else throw new RuntimeException("not support operator:"+s); 
					
			}
			else
			{
				sta.push(s);
			}
		}
		
		return sta.pop();
	}
	
	public static String evaluate(List<String> postfix_exp,boolean fraction) throws Exception
	{
		Stack<String> sta = new Stack<String>();
		String s ="";
		while(postfix_exp.size()>0)
		{
			s = postfix_exp.remove(0);
			if(s.length()==1 && !(s.charAt(0)>='0'&&s.charAt(0)<='9'))
			{
				String y = sta.pop();
				String x = sta.pop();
				
				if("+-*/".indexOf(s)>=0)
				{
					String result = "";
					if(fraction)
						result =caculateFraction(x,y,s.charAt(0));
					else
						result =caculateInteger(x,y,s.charAt(0));
					sta.push(result);
						
				}
				else throw new RuntimeException("not support operator:"+s); 
					
			}
			else
			{
				sta.push(s);
			}
		}
		
		return sta.pop();
	}
	
	private static String caculateInteger(String x,String y,char operator)throws Exception
	{
		int px = Integer.parseInt(x);
		int py = Integer.parseInt(y);
		int result = 0;
		switch(operator)
		{
		case '+':
			result = px + py;
	        break;
		case '-' :
			result = px - py ;
			break;
		case '*' :
			result = px * py;
			break;
		case '/' :
			result = px / py;
			break;
		}
		return result+"";
	}
	
	private static String caculateFraction(String x,String y,char operator)
	{
		int dx = 1, dy = 1,nx = 1, ny = 1;
		if(x.indexOf("/")>0)
		{
			dx = Integer.parseInt(x.substring(x.indexOf("/")+1));
			nx = Integer.parseInt(x.substring(0,x.indexOf("/")));
		}else
		{
			nx = Integer.parseInt(x);
		}
		
		if(y.indexOf("/")>0)
		{
			dy = Integer.parseInt(y.substring(y.indexOf("/")+1));
			ny = Integer.parseInt(y.substring(0,y.indexOf("/")));
		}else
		{
			ny = Integer.parseInt(y);
		}
		
		String s = "";
		int sn = 0;
		int sd = 1;
		switch(operator)
		{
		
		 case '+':
			  sn = nx*dy+ny*dx;
			  sd = dx*dy;
			
			 break;
		 case '-':
			  sn = nx*dy-ny*dx;
			  sd = dx*dy;
			 if(sn%sd==0)
				 s = (sn/sd)+"";
			 else
				 s =  sn+"/"+sd;
			 break;
		 case '*':
			 sn = nx*ny;
			 sd = dx*dy;
			 break;
		 case '/':	 
			 sn = nx*dy;
			 sd = ny*dx;
			 break;
		}
		
		 if(sn%sd==0)
			 s = (sn/sd)+"";
		 else
			 s =  sn+"/"+sd;
		 return s;
	}
	
	private static int getPriority(char operator)
	{
		switch(operator)
		{
			case '+':
				return 0;
			case '-':
				return 0;
			case '*' :
				return 1;
			case '/' :
				return 1;
			case '%' :
				return 1;
			default :
				return -1;
		}
	}



	/**
	 * 
	 * @param source 操作数集合
	 * @return
	 */
	public static List<List<String>> getAllPostfixExp(List<String> source)
	{
		List<List<String>> container = new ArrayList<List<String>>();
		Stack<String> st = new Stack<String>();
		st.push(source.get(0));
		fn2(source,1,true,source.size()-1,st,container);
//		fn(source,1,st,container);
		return container;
	}
	
	/**
	 * 
	 * @param source
	 * @param k
	 * @param st
	 * @param container
	 */
	private static void fn(List<String> source,int k,Stack<String> st,List<List<String>> container)
	{
		if(container == null)
			throw new RuntimeException("container is null");
		String []operators = new String[]{"+","-","/","*"};
		if(k>=source.size())
			return;
		st.push(source.get(k));
		for(String op:operators)
		{
			st.push(op);
			if(k==source.size()-1)
			{
				List<String> sta = new ArrayList<String>();
				sta.addAll(st);
				container.add(sta);
			}else
			{
				fn(source,k+1,st,container);
			}
			st.pop();
		}
		st.pop();
	}
	
	
	
	/**
	 * 
	 * @param source
	 * @param k 当前插入序号
	 * @param st
	 * @param rest_operator_num 还剩几个操作符没有插入
	 * @param container
	 */
	private static void fn2(List<String> source,int k,boolean push,
			int rest_operator_num,Stack<String> st,List<List<String>> container)
	{
		if(container == null)
			throw new RuntimeException("container is null");
		String []operators = new String[]{"+","-","/","*"};
		if(k>=source.size())
			return;
		if(push)
			st.push(source.get(k));
		//本次最多能插入的操作符个数
		int max_num = rest_operator_num-(source.size()-1-k);
		if(k == source.size()-1)//将剩余的所有操作符推入
		{
			for(int j=0;j<max_num;j++)
			{
				for(String op:operators)
				{
					st.push(op);
					if(j == max_num-1)
					{
						List<String> sta = new ArrayList<String>();
						if(st.size()==source.size()*2-1)//有一bug 计算出来的表达式比实际的多 尚未找到bug出在哪里 尚且在此判断 去除不合法的表达式
						{
							sta.addAll(st);
							container.add(sta);
						}
						
					}else
					{
						fn2(source,k,false,max_num-j-1,st,container);//?
					}
					st.pop();
				}
			}
			
		}else
		{
				for(int i=0;i<=max_num;i++)
				{
					if(i!=0)//push i 个操作符
						pushOperator(i,source,k,false,rest_operator_num-i,st,container);
					else if(i ==0)
					{
						fn2(source,k+1,true,rest_operator_num,st,container);
					}
				}
		}
		if(push)
			st.pop();
	}
	
	
	private static void pushOperator(int num,
			List<String> source,int k,boolean push,
			int rest_operator_num,Stack<String> st,List<List<String>> container)
	{
		String []operators = new String[]{"+","-","/","*"};

		if(num<1)
			return;
			for(String op:operators)
			{
				st.push(op);
				if(num == 1)
				{
					fn2(source,k+1,true,rest_operator_num,st,container);
				}
				else
				{
					pushOperator(num-1,source,k,push,rest_operator_num,st,container);
				}
				st.pop();
			}
	}
	
	public static void main(String args[])
	{
//		System.out.println(Integer.parseInt("4/5"));
		List<String> source = new ArrayList<String>();
		source.add("2");
		source.add("2");
		source.add("4");
		source.add("8");
//		List<List<String>> container = getAllPostfixExp(source);
//		System.out.println(container.size());
		
//		String s ="+";
//		System.out.println(s.length()==0 && !(s.charAt(0)>='0'&&s.charAt(0)<='9'));
//		List<String> list = toPostfixExp("12+((3*(2-4/5))+5)*3",true);
//		System.out.println(OperationUtil.evaluate("3+2-9/4", true));
//		List<List<String>> all = getAllPostfixExp(source);
//		for(int i=0;i<all.size();i++)
//		{
//			
//			for(String s:all.get(i))
//			{
//				System.out.print(s+" ");
//			}
//			System.out.println();
//		}
//		System.out.println(all.size());
		long begin = System.currentTimeMillis();
//		find24(new String[]{"4","5","5","1","6"});
		find24(new String[]{"8","3","8","3"});
		long end = System.currentTimeMillis();
		System.out.println(end-begin);
//		test8383();
	}
	
	public static void find24(String [] ops)
	{
		List<String[]> list  = PermUtil.perm(ops, 0, ops.length, null);
		for(String ss[]:list)
		{
			List<String> list1 = new ArrayList<String>();
			for(String s:ss)
			{
				list1.add(s);
			}
			List<List<String>> expList = getAllPostfixExp(list1);
//			System.out.println("exp list size = "+expList.size());
			for(List<String> exp:expList)
			{
				StringBuffer sbu = new StringBuffer();
				for(String str:exp)
				{
					sbu.append(str).append(" ");
				}
//				System.out.println(sbu.toString());
				String result = "";
				try {
					result = evaluate(exp,true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					continue;
				}
				if("24".equals(result))
				{
					System.out.println("find success:"+sbu.toString());
					
//					System.out.println();
				}
			}
		}
	}
	
	static void test8383()
	{
		List<String> source = new ArrayList<String>();
		source.add("8");
		source.add("3");
		source.add("8");
		source.add("3");
	List<List<String>> all = 	getAllPostfixExp(source);
	for(int i=0;i<all.size();i++)
	{
		
		for(String s:all.get(i))
		{
			System.out.print(s+" ");
		}
		System.out.println();
	}
	System.out.println(all.size());
	}
}

