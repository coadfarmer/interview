package tree;

public class MyTree {
	private Node root;//根节点

	public void add(int a) {
		Node n = new Node();
		n.data = a;
		if(root == null) {
			root = n;
		}else {
			root.addNode(n);
		}
	}

	public void sort() {
		if(root == null) return;
		else root.zhongxu();
	}

	public static void main(String[] args) {
		MyTree mt = new MyTree();
		mt.add(5);
		mt.add(4);
		mt.add(8);
		mt.add(6);
		mt.add(0);
		mt.sort();
		System.out.println();
		}
}
