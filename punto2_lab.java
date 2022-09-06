import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/*
Ejercicio Arbol binario
Empresa multinivel implementada en un arbol binario. Buscar la ruta más larga de un nodo y decir en qué nivel está el nodo
Codigo de rutas: https://www.techiedelight.com/es/print-all-paths-from-root-to-leaf-nodes-binary-tree/, 03/09/22

*/


// Una clase para almacenar un nodo de árbol binario
class Node{
    int data; // es el código del empleado 
    Node left = null, right = null;
 
    public Node(int data) {
        this.data = data;
    }
}
 
class Main{
    static ArrayList<Integer> ruta = new ArrayList<Integer>();
    // Función para verificar si un nodo dado es un nodo hoja o no
    public static boolean isLeaf(Node node) {
        return (node.left == null && node.right == null);
    }
 
    // Función recursivo para encontrar rutas desde el nodo raíz a cada nodo hoja
    public static void printRootToLeafPaths(Node node, Deque<Integer> path){
        // caso base
        if (node == null) {
            return;
        }
 
        // incluir el nodo actual en la ruta
        path.addLast(node.data);

        // si se encuentra un nodo hoja, se agrega el tamaño de esa ruta al array ruta para comparar el mayor 
        if (isLeaf(node)) {
            ruta.add(path.size()-1); // le restamos -1 para no incluir el nodo (o sea solo incluimos las personas que este nodo tiene como hijes)
        }
 
        // recurre para el subárbol izquierdo y derecho
        printRootToLeafPaths(node.left, path);
        printRootToLeafPaths(node.right, path);
 
        // retroceder: eliminar el nodo actual después de que el subárbol izquierdo y derecho hayan terminado
        path.removeLast();
    }
 
    // La función principal para imprimir rutas desde el nodo raíz a cada nodo hoja
    public static void printRootToLeafPaths(Node node){

        // lista para almacenar la ruta de la raíz a la hoja
        Deque<Integer> path = new ArrayDeque<>();
        printRootToLeafPaths(node, path);
    }

    // Algoritmo para buscar por nivel
    public static void findNode(Node root, int buscar){
        if (root == null) {
            return;
        }
        // utilizamos una cola para ir guardando los nodos por nivel 
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        int level = 0;
        while (q.size() > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                if(q.peek().data == buscar){ // comparamos el data que es el código del nodo en el que estamos y buscar que es el código que queremos encontrar
                    System.out.println("El empleado ingresado está en el nivel: "+ level);
                    Node r = q.peek();
                    printRootToLeafPaths(r); // utilizamos como parametro ese nodo y buscamos las rutas de ese nodo 
                } 
                Node temp = q.poll(); // temp para ir moviendonos en el arbol
                if (temp.left != null) {
                    q.add(temp.left);
                }
                if (temp.right != null) {
                    q.add(temp.right);
                }
            }      
            level++; // aumentamos el nivel cada que recorremos un nivel
        }
        }
 
    public static void main(String[] args){

        // Suponemos que el arbol ya está creado y es el siguiente: 
        /* Construimos el siguiente árbol
                  1
                /   \
               /     \
              2       3
             / \     / \
            4   5   6   7
                   /     \
                  8       9
                           \
                            10
        */ 
        // creamos el arbol manual 
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        root.right.left.left = new Node(8);
        root.right.right.right = new Node(9);
        root.right.right.right.right = new Node(10);
 
        
    
        findNode(root,3);// busca el empleado por codigo (estamos buscando el empleado con el codigo 3)
        Collections.sort(ruta);
        System.out.println("El nodo tiene: "+ ruta.size()+ " rutas");
        if(ruta.size()!=0){ // si tiene rutas>0
            System.out.println("");
            System.out.println("La ruta con más influencia tiene: "+ruta.get(ruta.size()-1)+" personas");
        }else{ //tiene 0 rutas 
            System.out.println("El vendedor tiene 0 rutas, por ende, no hay rutas influyentes");
        }
       
        
    }
}
