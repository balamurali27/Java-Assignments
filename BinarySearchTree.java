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
    public void delete(int key) {
        Node dest = root.left, p = root;
        while(true) {
            if (dest.data < key) {
                if (dest.rightThread) return; // key not found
                p = dest;
                dest = dest.right;
            } else if (dest.data > key) {
                if (dest.leftThread) return; // key not found
                p = dest;
                dest = dest.left;
            } else {
                break; // found the key now we need to replace it
            }
        }
        Node target = dest;
        if (!dest.rightThread && !dest.leftThread) { // key node has two children
            p = dest;
            // find largest node at left child
            target = dest.left;
            while (!target.rightThread) {
                p = target;
                target = target.right;
            }
            dest.data = target.data; // replace key
        }
        if (p.data >= target.data) {
            if (target.rightThread && target.leftThread) {
                p.left = target.left;
                p.leftThread = true;
            } else if (target.rightThread) {
                Node largest = target.left;
                while (!largest.rightThread) {
                    largest = largest.right;
                }
                largest.right = p;
                p.left = target.left;
            } else {
                Node smallest = target.right;
                while (!smallest.leftThread) {
                    smallest = smallest.left;
                }
                smallest.left = target.left;
                p.left = target.right;
            }
        } else {
            if (target.rightThread && target.leftThread) {
                p.right = target.right;
                p.rightThread = true;
            } else if (target.rightThread) {
                Node largest = target.left;
                while (!largest.rightThread) {
                    largest = largest.right;
                }
                largest.right =  target.right;
                p.right = target.left;
            } else {
                Node smallest = target.right;
                while (!smallest.leftThread) {
                    smallest = smallest.left;
                }
                smallest.left = p;
                p.right = target.right;
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
            if(current.rightThread){
                current=current.right;
            }
            else{
                current=leftMostChild(current.right);
            }
        }
        System.out.println();
    }
    public void reverseinorder(Node root){
    }
    public void postorder(Node root){
    }
    //function to find left most child
    public Node leftMostChild(Node node){
        if(node != null)
            while(node.left!=null && !node.leftThread ){
               node = node.left;
            }
        return node;
    }
       
    public Node rightMostChild(Node node){
            while(node.right!=null){
                node = node.right;
            }
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
        int ch = 4;
        do{
            System.out.println("Binary Tree Menu");
            System.out.println("1.Insert \n2.Delete \n3.Traversal\n4.Exit\nEnter your choice:");
            ch = sc.nextInt();
            switch(ch){
                case 1://insert 
                         System.out.print("Enter the data for node:");
                         data=sc.nextInt();
                         bt.insert(data);
                        break;
                case 2://delete 
                        break;
                case 3://traversal
                        System.out.print("Traversal menu\n1.Inorder\n2.Postorder\n3.Reverse Inorder\nYour choice : ");
                        int tc =sc.nextInt();
                        switch(tc){
                            case 1: bt.inorder(bt.root);
                                    break;
                            case 2: bt.postorder(bt.root);
                                    System.out.println();
                                    break;
                            case 3: bt.reverseinorder(bt.root);
                                    break;
                            default:
                        }
                        break;        
                case 4: break;//return;        
                default: System.out.println("Invalid operation!");
            }
        }while(ch<4);
        sc.close();
    }
}
