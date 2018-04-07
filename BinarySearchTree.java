import java.util.Scanner;
import java.util.concurrent.TimeUnit; //for testing purposes
class Node{
    Node left,right;
    int data;
    boolean leftThread,rightThread;
    public Node(int data){
        this.data = data;
        this.rightThread = false;
        this.leftThread = false;
        this.left = null;
        this.right = null;
    }
}

class ThreadedBinaryTree {
    public Node root;
   
    public void insert(int id){
        Node newNode = new Node(id);
        Node current = root;

        while(true){
            //new value smaller than current node
            if(id<current.data){
                //check if left child exists
                if(current.leftThread || current.left==null){//thread means no child
                    //if left child doesn't exist, add as left child
                    //add threads to newNode as it has no child
                    newNode.right=current;
                    newNode.rightThread=true;
                    //thread of parent is now thread of child
                    newNode.left=current.left;
                    newNode.leftThread=current.leftThread;
                    //add as child
                    current.left=newNode;
                    //set the thread value as false since it now has child 
                    current.leftThread=false;
                    break;
                }
                else{
                    //keep comparing with childs
                    current=current.left;
                }
            }
            //new value greater than current node
            else if(id>current.data){
                //check if right child exists
                if(current.rightThread || current.right==null){//thread means no child
                    //if right child doesn't exist, add as right child
                    //add threads to newNode as it has no child
                    newNode.left=current;
                    newNode.leftThread=true;
                    //thread of parent is now thread of child
                    newNode.right=current.right;
                    newNode.rightThread=current.rightThread;
                    //add as child
                    current.right=newNode;
                    //set the thread value as false since it now has child
                    current.rightThread=false;
                    break;
                }
                else{
                    //keep comparing with childs
                    current=current.right;
                }
            }
            //value already exists (id==current.data)
            else{
                System.out.println("\nValue already exists");
                break;
            }
        }
    }
}
class BinaryTree extends ThreadedBinaryTree{
    public void inorder(Node root){
        //start at left most node (smallest)
        Node current=leftMostChild(root);
        while(current!=null){
            System.out.print(current.data + " ");
            if(current.rightThread)
                current=current.right;
            else
                current=leftMostChild(current.right);
        }
        System.out.println();
    }
    public void reverseInorder(Node root){
        Node current=rightMostChild(root);
        while(current!=null){
            System.out.print(current.data + " ");
            if(current.leftThread)
                current=current.left;
            else
                current=rightMostChild(current.left);
        }
        System.out.println();
    }
    //recursive function for preorder traversal
    public void preorder(Node root){
        if(root != null){
            System.out.println(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }
    public void postorder(Node root){
        if(root != null){
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.data + " ");
        }
    }
    //function to find left most child
    public Node leftMostChild(Node node){
        if(node != null)
            while(node.left!=null && !node.leftThread)
               node = node.left;
        return node;
    }
    //function to find right most child   
    public Node rightMostChild(Node node){
        if(node != null)
            while(node.right!=null && !node.rightThread)
                node = node.right;
        return node;
    }
}

class BinarySearchTree{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BinaryTree bt = new BinaryTree();
        System.out.print("Enter the data for root node:");
        int data=sc.nextInt();
        bt.root = new Node(data);
        int ch = 6;
        do{
            System.out.println("Binary Tree Menu");
            System.out.println("1.Insert \n2.Inorder Traversal\n3.Reverse Inorder Traversal\n4.Preorder Traversal\n5.Postorder Traversal\n6.Exit\nEnter your choice:");
            ch = sc.nextInt();
            switch(ch){
                case 1://insert 
                    System.out.print("Enter the data for node:");
                    data=sc.nextInt();
                    bt.insert(data);
                    break;
                case 2://inorder traversal
                    System.out.println("*******Inorder traversal*******");
                    bt.inorder(bt.root);
                    break;
                case 3://reverse inorder traversal
                    System.out.println("*******Reverse inorder traversal*******");
                    bt.reverseInorder(bt.root);
                    break;
                case 4://preorder traversal
                    System.out.println("*******Preorder Traversal*******");
                    bt.preorder(bt.root);
                    break;
                case 5://postorder traversal
                    System.out.println("*******Postorder Traversal*******");
                    bt.postorder(bt.root);
                    break;
                case 6://Exit
                    break;
                default: System.out.println("Invalid operation!");
            }
        }while(ch<6);
        sc.close();
    }
}
