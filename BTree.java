import java.util.Random;

/* Ejercicios arboles B
 * Buscar cual es el empleado que tiene más pendientes por hace
 * Código de inserción del arbol B desde cero: https://github.com/csanjuanc/estructuras-de-datos/blob/master/B_Tree/ArbolB.java, 02/09/22
 * 
 */

// Inserting a key on a B-tree in Java 
public class BTree {

  private int T;// t es el grado mínimo del arbol

  // creación del nodo para BTree
  public class Node {
    int n; // los espacios del array key utilizados
    int key[] = new int[2 * T - 1]; // array de las claves
    Node child[] = new Node[2 * T]; // array de los hijos (punteros)
    boolean leaf = true; // indica si el nodo es de tipo hoja o es nodo interno

    // imprime la info que hay en dicho nodo
    public void imprimir() {
      System.out.print("[");
      for (int i = 0; i < n; i++) {
        if (i < n - 1) {
          System.out.print(key[i] + " | ");
        } else {
          System.out.print(key[i]);
        }
      }
      System.out.print("]");
    }

  }

  // constructor para el arbol
  public BTree(int t) {
    T = t;
    // crea una raiz
    root = new Node();
    root.n = 0;
    root.leaf = true;
  }

  private Node root;

  // cuando un nodo se llena lo splitea para repartir los valores con otro creado
  private void split(Node x, int pos, Node y) {
    // crea un nodo temporal
    Node z = new Node();

    z.leaf = y.leaf;
    z.n = T - 1;// tendrá en este caso solo 1 valor pq t = 2

    // se agg los ultimos valores del nodo y al principio de node z
    for (int j = 0; j < T - 1; j++) {

      z.key[j] = y.key[j + T];
    }
    // si no es una hoja, entonces se reasignan los hijes
    if (!y.leaf) {
      // se crean los hijos de z y se agg los ultimos valores del array child de y
      for (int j = 0; j < T; j++) {
        z.child[j] = y.child[j + T];
      }
    }
    // nuevo tamaño de y
    y.n = T - 1;// los valores utilizados en y ahora se reducen al valores minimo menos 1

    // se hace un desplazamiento de los hijos de x para add el nodo z
    for (int j = x.n; j >= pos + 1; j--) {
      x.child[j + 1] = x.child[j];
    }
    // es el pos+1 hijo de nodo x
    x.child[pos + 1] = z;
    // reacomodamos las claves, ya que reacomodamos los hijos
    for (int j = x.n - 1; j >= pos; j--) {
      x.key[j + 1] = x.key[j];
    }

    // agrega la clave situada en la mediana
    // darle el valor de la mediana de nuestro split
    x.key[pos] = y.key[T - 1];
    // aumentar los valores ocupados en el array key
    x.n = x.n + 1;

    /*
     * cuandos e splitea el array de key va a quedar con espacios
     * vacios que se van a llenar en una proxima inserción si así
     * es el caso
     */
  }

  // insert key
  public void insert(final int key) {
    Node r = root; // copia de root
    // n es la cantidad de valores almacenados en el nodo
    if (r.n == 2 * T - 1) {// si el nodo está lleno
      Node s = new Node();// se crea un nuevo nodo
      root = s;
      s.leaf = false;
      s.n = 0;
      s.child[0] = r;// el primer hijo corresponde a todos lo que hiciste en r, o sea la raíz
      split(s, 0, r);// se hace una división, ya que está lleno
      _insert(s, key); // se inserta luego del split y verificar que sea una hoja
    } else {// sino solo se inserta con la private _insert
      _insert(r, key);
    }
  }

  // insert node //////////////////////
  final private void _insert(Node x, int k) {

    if (x.leaf) {// si el nodo q recibimos es una hoja
      // si es una hoja, se hace la inserción
      int i = 0;
      // la posición donde se debe ingresar es i
      for (i = x.n - 1; i >= 0 && k < x.key[i]; i--) {// se recorre de atras hacia adelante hasta
        // 0 y el k es menor al valor por donde vaya en el array para saber dónde se
        // inserta
        x.key[i + 1] = x.key[i];
      }
      // posterior a esto lo ingresa
      x.key[i + 1] = k;
      // y se le suma uno a los valores ocupados en el array
      x.n = x.n + 1;
    } else {// si no es una hoja
      int i = 0;
      // como no es hoja se busca que la clave no sea mayor y para saber la posición
      // del hijo para saber dónde insertar
      for (i = x.n - 1; i >= 0 && k < x.key[i]; i--) {

      }
      ;
      i++;
      // se crea un temporal para recorrer los hijes por donde se vaya, ya que ahí lo
      // vamos a ingresar
      Node tmp = x.child[i];
      if (tmp.n == 2 * T - 1) {// veo si los hijos están llenos
        split(x, i, tmp);// si es así se hace una division de nodo
        // como está lleno puede que haya un desplazamiento i
        if (k > x.key[i]) {// se comprueba la clave
          i++; // incrementar la posición de i
        }
      }
      _insert(x.child[i], k);// se hace una llamada recursiva
      // solo se inserta cuando el nodo es una hoja
    }

  }

  public int buscarClaveMayor() {
    int claveMaxima = getClaveMayor(this.root);

    return claveMaxima;
  }

  private int getClaveMayor(Node current) {
    if (current == null) {
      return 0;// Si es cero no existe
    }

    // Mientras no sea una hoja
    while (!current.leaf) {
      /*
       * Se accede al hijo mas a la derecha
       * como está ordenado de izquierda a derecha se sabe que en la derecha
       * están los de mayor valor
       */

      current = current.child[current.n];
    }

    return claveMayorPorNodo(current);
  }

  private int claveMayorPorNodo(Node current) {
    // Devuelve el valor mayor es decir el que se encuentre más a la derecha
    return current.key[current.n - 1];
  }

  // Busca el valor ingresado y muestra el contenido del nodo que contiene el
  // valor
  public void buscarNodoPorClave(int num) {
    Node temp = search(root, num);

    if (temp == null) {
      System.out.println("No se ha encontrado un nodo con el valor ingresado");
    } else {
      print(temp);
    }
  }

  // Search
  private Node search(Node actual, int key) {
    int i = 0;// se empieza a buscar siempre en la primera posicion

    // Incrementa el indice mientras el valor de la clave del nodo sea menor
    while (i < actual.n && key > actual.key[i]) {
      i++;
    }

    // Si la clave es igual, entonces retornamos el nodo
    if (i < actual.n && key == actual.key[i]) {
      return actual;
    }

    // Si llegamos hasta aqui, entonces hay que buscar los hijos
    // Se revisa primero si tiene hijos
    if (actual.leaf) {
      return null;
    } else {
      // Si tiene hijos, hace una llamada recursiva
      return search(actual.child[i], key);
    }
  }

  // copiando de la otra para buscar el mayor numero

  private void print(Node n) {
    n.imprimir();

    // Si no es hoja
    if (!n.leaf) {
      // recorre los nodos para saber si tiene hijos e imprime los hijes
      for (int j = 0; j <= n.n; j++) { // llega hasta n.n que significa las veces que hay claves

        if (n.child[j] != null) {
          System.out.println();
          // por cada clave imprime los hijes
          System.out.println("hijes de " + j + " del node " + n.key[j]);
          print(n.child[j]);
        }
      }
    }
  }

  public static void main(String[] args) {
    Random r = new Random();
    BTree b = new BTree(2); // se crea el arbol con un random
    for (int index = 0; index < 63; index++) {
      b.insert(r.nextInt(1000));
    }


    // IMPLEMENTAR
    System.out.println("\nEl empleado que más tareas tiene es : " + b.buscarClaveMayor());
  }
}