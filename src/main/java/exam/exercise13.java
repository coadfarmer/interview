package exam;

public class exercise13 {

	public static void main(String[] args) {
		char[] m= {'a','b','c'};
		char[] n= {'x','y','z'};
		StringBuilder r = new StringBuilder();
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if((m[i]=='a' && n[j]=='x')||(m[i]=='a' && n[j]=='y')) {
					continue;
				}else if((m[i]=='c'&&n[j]=='x')||(m[i]=='c'&&n[j]=='z'))
				{
					continue;
				}else if((m[i]=='b'&&n[j]=='y')||(m[i]=='b'&&n[j]=='z'))
				{
					continue;
				}else {
					String s = m[i]+":"+n[j];
					r.append(s).append(",");
				}
			}
		}
		String substring = r.substring(0, r.length() - 1);
		System.out.println(substring);
	}

}
