package tree;

public class Node {//节点类
	 public int data;//数据域
	 public Node lnode;//左节点
	 public Node rnode;//右节点

	 public void addNode(Node n) {
		 if(n.data < this.data) {//左节点
			 if(lnode == null) {
				 lnode = n;
			 }else lnode.addNode(n);
		 }else {
			 if(rnode == null) {
				 rnode = n;
			 }else rnode.addNode(n);
		 }
	 }

	 public void xianxu() {//先序遍历
        System.out.print(this.data);
		if(lnode != null) lnode.xianxu();
		if(rnode != null) rnode.xianxu();
	}
	public void zhongxu() {//中序遍历
		if(lnode != null) lnode.zhongxu();
		System.out.print(this.data);
		if(rnode != null) rnode.zhongxu();
	}
    public void houxu() {//后序遍历
		if(lnode != null) lnode.houxu();
		if(rnode != null) rnode.houxu();
        System.out.print(this.data);
	}

}
