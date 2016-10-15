package cn.tedu.note.entity;

public enum Color {
	RED("红色",1),GREEN("绿色",2),YELLOW("黄色",3),WHITE("白色",4);
	private String name;
	private int index;
	
	private Color(String name,int index){
		this.name = name;
		this.index = index;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static String getName(int index){
		for(Color c :Color.values()){
			if(c.getIndex()==index){
				return c.name;
			}
		}
		return null;
		
	}
	public static void main(String[] args) {
		String name = getName(5);
		System.out.println(name);
	}
}

	
	
	