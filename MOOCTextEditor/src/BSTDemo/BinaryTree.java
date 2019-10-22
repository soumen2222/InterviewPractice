package BSTDemo;

public class BinaryTree<E extends Comparable<E>> {

	TreeNode<E> root;

	public BinaryTree(){
		this.root = null;
	}



	public boolean find(E id){
		TreeNode<E> current = root;

		while(current!=null){
			int cmp = current.getValue().compareTo(id);
			if(cmp==0){
				return true;
			}else if(cmp > 0){
				current = current.getLeft();
			}else{
				current = current.getRight();
			}
		}
		return false;
	}


	public void insert(E id){

		if(root==null){
			root = new TreeNode<E> (id,null );
			return;
		}
		TreeNode<E> current = root;
		TreeNode<E> parent = null;
		while(true){
			parent = current;
			int cmp = current.getValue().compareTo(id);
			if(cmp>0){
				current = current.getLeft();
				if(current==null){
					parent.addLeftChild(id);
					return;
				}
			}else{
				current = current.getRight();
				if(current==null){
					parent.addRightChild(id);
					return;
				}
			}
		}
	}
	public void display(TreeNode<E> root){
		if(root!=null){
			display(root.getLeft());
			System.out.print(" " + root.getValue());
			display(root.getRight());
		}
	}


	public static void main(String arg[]){
		BinaryTree b = new BinaryTree();
		b.insert(3);
		b.insert(8);
		b.insert(1);
		b.insert(4);b.insert(6);b.insert(2);b.insert(10);b.insert(9);
		b.insert(20);b.insert(25);b.insert(15);b.insert(16);
		System.out.println("Original Tree : ");
		b.display(b.root);
		System.out.println("");
		System.out.println("Check whether Node with value 4 exists : " + b.find(4));

	}
}
