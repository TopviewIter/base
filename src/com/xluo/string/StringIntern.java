package com.xluo.string;


/**
 * 1、字面量在编译期间就被解析出来并放到了字节码当中的constant pool当中,并在类加载完后，进入常量池
 * 2、调用intern返回的不一定就是在编译期间被确定的那个常量,而是通过谁最先调用了intern谁就被返回(
 * 	这里与文档的描述有点区别,但与jvm规范一致)
 * 3、当直接使用字面量的时候,实际上jvm帮我们隐式创建了一个对象,并调用intern方法
 * @author luozhangjie
 *
 */
public class StringIntern {

	public static void instance(){
		
		String param = "param";
//		String param = new String("param");
//		String param = new StringBuilder("param").toString();
//		String param = new StringBuilder("pa").append("ram").toString();
		String paramSame = param.intern();
//		
		System.out.println(param == paramSame);
		
//		String test = new StringBuilder("ra").append("m").toString();
//		String testSame = test.intern();
//		System.out.println(testSame == test);
		
	}
	
	public static void onceShow(){
		
		String part = "pa";
		String param = part + "ram";
//		String param = new StringBuilder("pa").append("ram").toString();
//		param.intern(); 这里注释与不注释可以验证在使用new StringBuilder().append的时候是不会顺便到池中生成一个"param"对象
		String paramSame = new String("param"); //这里放进池里面的是jvm隐式创建的对象的引用,而不是paramSame
		System.out.println(param == paramSame.intern());
		System.out.println(param.intern() == paramSame.intern());
		
		//我猜字符串常量池里面内置了一个java常量
		String param2 = new StringBuilder("ja").append("va").toString();
		String param2Same = param2.intern();
		System.out.println(param2 == param2Same);
		
	}
	
	//证明当调用intern方法的时候如果常量池没有与之字面量相同的引用,则把它放到池中
	public static void changeLocation(){
		String a = "aa";
		String param = "b" + a;
		String param2 = param; //param2指向param指向的内存
		param.intern(); //param迁入常量池
		System.out.println(param == "baa"); //true
		System.out.println(param2 == "baa"); //true
	}
	
	//当直接使用字面量时,jvm底层会使用它创建一个对象,并调用intern方法
	//而intern方法则是返回第一次调用它的引用,在下面例子中,虽然ab在编译期就进入了常量池,但执行的时候是
	//先执行了a.intern,所以以后调用intern方法返回的均为a的引用
	public static void checkDoc(){
		String a = new StringBuilder("a").append("b").toString();
		a.intern();
		String b = new String("ab");
		System.out.println(b.intern() == b);
	}
	
	//"ba"先出现,隐式调用了intern方法,所以下次返回的为"ba"的引用
	public static void pushPool(){
		String a = "a";
		String param = "b" + a;
		System.out.println("ba" == param.intern()); 
		System.out.println(param == "ba");
		String param2 = "b" + a;
		System.out.println(param2.intern() == param);
	}
	
	public static void main(String[] args) {
		
//		instance();
		onceShow();
//		pushPool();
//		check();
//		checkDoc();
		
	}
	
}
