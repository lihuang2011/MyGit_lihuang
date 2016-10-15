package cn.tedu.note.entity;

public class TestCase {
	public static String reverse(String str){
		StringBuffer buf = 
				new StringBuffer(str.length());
		System.out.println(buf);
		for(int i=str.length()-1;i>=0;i--){
			buf.append(str.charAt(i));
			
		}
	
		return buf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(reverse("abc"));
	}
}
