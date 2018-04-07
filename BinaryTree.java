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
        Node newNode;
        Node current;
        int parentData;
        int ch;
        boolean success=false;

        Scanner sc = new Scanner(System.in);
        
        //to display all nodes
        inorder(root);
        System.out.print("\nEnter which node's child you want the new node to be : ");
        parentData = sc.nextInt();

        //inorder traversal to find parent
        current = leftMostChild(root);
        while(current != null && current.data != parentData){
            if(current.rightThread)
                current=current.right;
            else
                current=leftMostChild(current.right);
        }
        if(current == null){
            //parent doesnt' exist
            System.out.println("The node you entered doesn't exist!");
            sc.close();
            return;
        }
        //now, current is parent
        //prompt for left or right child 
        ch=2;
        do{
            System.out.print("\n Insert as \n1) Left child\n2) Right child\nEnter your choice : ");
            ch=sc.nextInt();
            switch(ch){
                case 1 : //left child
                    if(current.leftThread || current.left==null){//thread means no child
                        //if left child doesn't exist,create new node add as left child
                        newNode=new Node(id);
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
                    }
                    else{
                        //replace left child
                        newNode=current.left;
                        newNode.data=id;
                    }
                    success=true;
                    break;
                case 2 : //right child
                    if(current.rightThread || current.right==null){//thread means no child
                        //if right child doesn't exist,create new node and add as right child
                        newNode=new Node(id);
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
                    }
                    else{
                        //replace right child
                        newNode=current.right;
                        newNode.data=id;
                    }
                    success=true;
                    break;
                case 3 : //exit
                    break;
                default : System.out.println("Invalid option!");
            }
            if(success) break;
        }while(ch<2);
        sc.close();
        return;
    }
    public void inorder(Node root){
        Node current=leftMostChild(root);
        while(current!=null){
            System.out.print(current.data + " ");
            if(current.rightThread)
                current=current.right;
            else
                current=leftMostChild(current.right);
        }
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
    }
    //recursive function for preorder traversal
    public void preorder(Node root){
        if(root != null){
            System.out.println(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }
    //recursive function for postorder traversal
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

class BinaryTree extends ThreadedBinaryTree{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //create new binary tree
        BinaryTree bt = new BinaryTree();
        System.out.print("Enter the data for root node:");
        int data=sc.nextInt();
        //insert root directly
        bt.root = new Node(data);

        //Main Menu
        boolean success=false;
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
                    success=true;
                    break;
                case 2://inorder traversal
                    System.out.println("*******Inorder traversal*******");
                    bt.inorder(bt.root);
                    success=true;
                    break;
                case 3://reverse inorder traversal
                    System.out.println("*******Reverse inorder traversal*******");
                    bt.reverseInorder(bt.root);
                    success=true;
                    break;
                case 4://preorder traversal
                    System.out.println("*******Preorder Traversal*******");
                    bt.preorder(bt.root);
                    success=true;
                    break;
                case 5://postorder traversal
                    System.out.println("*******Postorder Traversal*******");
                    bt.postorder(bt.root);
                    success=true;
                    break;
                case 6://Exit
                    break;
                default: System.out.println("Invalid operation!");
            }
            System.out.println("\n");
            if(!success) break;
        }while(ch<6);
        sc.close();
    }
}
