import java.util.*;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// to implement a numerical order comparator
class NumOrderComp implements Comparator<Integer> {
    public int compare(Integer t1, Integer t2) {
        if (t1 <  t2) {
            return -1;
        }
        else {
            return 1;
        }
    }
}

//to implement an increasing edge weight order comparator
class EdgeWeightOrderComp implements Comparator<Edge> {
    public int compare(Edge e1, Edge e2) {
        if (e1.weight <  e2.weight) {
            return -1;
        }
        else if (e1.weight == e2.weight) {
            return 0;
        }
        else {
            return 1;
        }
    }
}

// to represent a node of the maze
class Node {
    Posn p;      // top left of cell graph position, not pixel position
    Node top;
    Node bot;
    Node left;
    Node right;
    
    // Constructor
    Node(Posn p, Node top, Node bot, Node left, Node right) {
        this.p = p;
        this.top = top;
        this.bot = bot;
        this.left = left;
        this.right = right;
    }
    
    // Convenience Constructor
    Node(Posn p) {
        this.p = p;
        this.top = null;
        this.bot = null;
        this.left = null;
        this.right = null;
    }
    
    // sets neighbors unless already set
    // EFFECT: modifies 
    void setNeighbors(Node top, Node bot, Node left, Node right) {
        if (this.top == null) {
            this.top = top;
        }
        if (this.bot == null) {
            this.bot = bot;
        }
        if (this.left == null) {
            this.left = left;
        }
        if (this.right == null) {
            this.right = right;
        }
    }
}

// to represent an edge
class Edge {
    int weight;
    Node from;
    Node to;
    
    // Constructor
    Edge(int weight, Node from, Node to) {
        this.to = to;
        this.from = from;
        this.weight = weight;
    }
    
    // Constructor
    Edge(Node from, Node to) {
        this.to = to;
        this.from = from;
        this.weight = randomWeight(50);
    }
    
    // gets a random weight from 1 to the given integer
    int randomWeight(int r) {
        return (int) ((r - 1) * Math.random() + 1);
    }
}

// to represent a graph
class Graph {
    ArrayList<ArrayList<Node>> nodes;
    
    // Constructor
    Graph(ArrayList<ArrayList<Node>> nodes) {
        this.nodes = nodes;
    }
    
    // gets list of edges sorted by weight
    ArrayList<Edge> getEdges() {
        ArrayList<Edge> elist = new ArrayList<Edge>();
        
        // adds vertical edges
        for (int r = 0; r < this.nodes.size() - 1; r += 1) {
            for (int c = 0; c < this.nodes.get(0).size(); c += 1) {
                elist.add(new Edge(nodes.get(r).get(c), nodes.get(r + 1).get(c)));
            }
        }   
        
        // adds horizontal edges
        for (int r = 0; r < this.nodes.size(); r += 1) {
            for (int c = 0; c < this.nodes.get(0).size() - 1; c += 1) {
                elist.add(new Edge(nodes.get(r).get(c), nodes.get(r).get(c + 1)));
            }
        }
   
        // sorts the list of edges
        Collections.sort(elist, new EdgeWeightOrderComp());
        return elist;
    }
    
    // generate the spanning tree of this graph using kruskal's algorithm
    // EFFECT: initialize neighbors
    ArrayList<Edge> span() {
        HashMap<Posn, Posn> representatives = new HashMap<Posn, Posn>();
        ArrayList<Edge> edgesInTree = new ArrayList<Edge>();
        ArrayList<Edge> worklist = getEdges(); // all edges in the graph, sorted by edge weights
        for (ArrayList<Node> aRow : this.nodes) {
            for (Node n : aRow) {
                representatives.put(n.p, n.p);
            }
        }
        while (edgesInTree.size() < this.nodes.size() * this.nodes.get(0).size() - 1) {
            Edge cheapest = worklist.get(0);
            if (find(representatives, cheapest.from.p).equals(find(representatives,
                    cheapest.to.p))) {
                worklist.remove(0);
            }
            else {
                updateNeighbors(worklist.get(0));
                edgesInTree.add(worklist.get(0));
                representatives = union(representatives,
                        find(representatives, cheapest.from.p),
                        find(representatives, cheapest.to.p));
            }
        }
        return edgesInTree;
    }
    
    // updates neighbors of this graph based on the given edge
    void updateNeighbors(Edge e) {
        // horizontal neighbors
        for (int r = 0; r < this.nodes.size(); r += 1) {
            for (int c = 0; c < this.nodes.get(0).size() - 1; c += 1) {
                if (e.to.equals(this.nodes.get(r).get(c + 1)) && e.from.equals(this.nodes.get(r)
                        .get(c))) {
                    this.nodes.get(r).get(c).right = this.nodes.get(r).get(c + 1);
                    this.nodes.get(r).get(c + 1).left = this.nodes.get(r).get(c);
                }
            }
        }
        
        // vertical neighbors
        for (int r = 0; r < this.nodes.size() - 1; r += 1) {
            for (int c = 0; c < this.nodes.get(0).size(); c += 1) {
                if (e.to.equals(this.nodes.get(r + 1).get(c)) && e.from.equals(this.nodes.get(r)
                        .get(c))) {
                    this.nodes.get(r).get(c).bot = this.nodes.get(r + 1).get(c);
                    this.nodes.get(r + 1).get(c).top = this.nodes.get(r).get(c);
                }
            }
        }
        
    }
    
    // finds the representative of Node n in representatives
    Posn find(HashMap<Posn, Posn> representatives, Posn p) {
        if (representatives.get(p).equals(p)) {
            return p;
        }
        else {
            return find(representatives, representatives.get(p));
        }
    }
    
    // sets the representative of child to the representative of parent
    HashMap<Posn, Posn> union(HashMap<Posn, Posn> representatives, Posn child, Posn parent) {
        representatives.put(child, find(representatives, parent));
        return representatives;
    }
}

// to represent a maze game world
class MazeWorld extends World {
    
    // fields
    Graph g;
    Posn player;
    ArrayList<Edge> spanningTree;
    ArrayList<Node> visited;
    ArrayList<Node> solution;
    boolean displaySpanningTree;
    boolean isComplete;
    
    // class constants 
    static final int BOARD_WIDTH_PX = 1000;                         // width of the board in pixels
    static final int BOARD_HEIGHT_PX = (int) (.6 * BOARD_WIDTH_PX); // height of the board in pixels
    static final int BOARD_WIDTH = 10;                              // height of the board in tiles
    static final int BOARD_HEIGHT = (int) (.6 * BOARD_WIDTH);       // width of the board in tiles
    static final int TILE_SIZE = BOARD_WIDTH_PX / BOARD_WIDTH;      // size of each cell in pixels
    static final int CENTER_Y = BOARD_HEIGHT * TILE_SIZE / 2;       // y center of the board in pix
    static final int CENTER_X = BOARD_WIDTH * TILE_SIZE / 2;        // x center of the board in pix
    
    // constructor
    MazeWorld() {
        this.g = new Graph(makeNodes());
        this.spanningTree = g.span();
        this.displaySpanningTree = false;
        this.visited = new ArrayList<Node>();
        this.solution = new ArrayList<Node>();
        this.player = new Posn(0, 0);
        this.isComplete = false;
    }
    
    // on-key events
    public void onKeyEvent(String key) {
        if (key.equals("left") && goodMove(key) && !isComplete) {
            this.player = new Posn(this.player.x - 1, this.player.y);
            addVisited(this.player);
        }
        else if (key.equals("right") && goodMove(key) && !isComplete) {
            this.player = new Posn(this.player.x + 1, this.player.y);
            addVisited(this.player);
        }
        else if (key.equals("up") && goodMove(key) && !isComplete) {
            this.player = new Posn(this.player.x, this.player.y - 1);
            addVisited(this.player);
        }
        else if (key.equals("down") && goodMove(key) && !isComplete) {
            this.player = new Posn(this.player.x, this.player.y + 1);
            addVisited(this.player);
        }
        else if (key.equals("d")) {
            visited = new ArrayList<Node>();
            this.solution = dfs();
        }
        else if (key.equals("b")) {
            visited = new ArrayList<Node>();
            this.solution = bfs();
        }
        else if (key.equals("s")) {
            this.displaySpanningTree = !(this.displaySpanningTree);
        }
        if (this.player.x == BOARD_WIDTH - 1 && this.player.y == BOARD_HEIGHT - 1) {
            this.isComplete = true;
        }
        if (key.equals("n")) {
            this.g = new Graph(makeNodes());
            this.spanningTree = g.span();
            this.solution = new ArrayList<Node>();
            this.displaySpanningTree = false;
            this.player = new Posn(0, 0);
            this.visited = new ArrayList<Node>();
            this.isComplete = false;
        }
    }
    
    // add the players current position to visited
    void addVisited(Posn player) {
        this.visited.add(new Node(player));
    }
    
    // can the player execute the move given by the key
    public boolean goodMove(String key) {
        if (key.equals("up")) {
            return this.g.nodes.get(this.player.y).get(this.player.x).top != null;
        }
        else if (key.equals("down")) {
            return this.g.nodes.get(this.player.y).get(this.player.x).bot != null;
        }
        else if (key.equals("left")) {
            return this.g.nodes.get(this.player.y).get(this.player.x).left != null;
        }
        else if (key.equals("right")) {
            return this.g.nodes.get(this.player.y).get(this.player.x).right != null;
        }
        else {
            return false;
        }
    }

    // displays world
    public WorldScene makeScene() {
        int shift = TILE_SIZE / 2;
        
        WorldScene scene = new WorldScene(BOARD_WIDTH_PX, BOARD_HEIGHT_PX);
        scene.placeImageXY(new RectangleImage(BOARD_WIDTH_PX + TILE_SIZE * 2, BOARD_HEIGHT_PX +
                TILE_SIZE * 2, "solid", Color.LIGHT_GRAY), CENTER_X, CENTER_Y);
        
        //draws the visited cells
        for (Node n : this.visited) {
            scene.placeImageXY(new RectangleImage(TILE_SIZE, TILE_SIZE, OutlineMode.SOLID, 
                    new Color(135, 206, 250)), n.p.x * TILE_SIZE + shift, n.p.y
                    * TILE_SIZE + shift);
        }
        
        // draws the solution
        for (Node n : this.solution) {
            if (n == null) {
                throw new RuntimeException("node is null");
            }
            scene.placeImageXY(new RectangleImage(TILE_SIZE, TILE_SIZE, OutlineMode.SOLID, 
                    Color.cyan), 
                    n.p.x * TILE_SIZE + shift, 
                    n.p.y * TILE_SIZE + shift);
        }
        
        //draws the player
        scene.placeImageXY(new RectangleImage(TILE_SIZE, TILE_SIZE, OutlineMode.SOLID, 
                Color.BLUE), this.player.x * TILE_SIZE + shift, this.player.y * 
                TILE_SIZE + shift);
        
        //draws the top left and bottom right corners
        scene.placeImageXY(new RectangleImage(TILE_SIZE, TILE_SIZE, OutlineMode.SOLID, 
                new Color(51, 168, 30)), shift, shift);
        scene.placeImageXY(new RectangleImage(TILE_SIZE, TILE_SIZE, OutlineMode.SOLID, 
                new Color(136, 67, 222)), BOARD_WIDTH_PX - shift, BOARD_HEIGHT_PX -
                shift);
        
        //draws the walls
        for (ArrayList<Node> arr : g.nodes) {
            for (Node n : arr) {
                if (n.top == null) {
                    scene.placeImageXY(new RectangleImage(MazeWorld.TILE_SIZE, 2, 
                            OutlineMode.SOLID, Color.black), n.p.x * 
                            TILE_SIZE + shift, n.p.y * TILE_SIZE);
                }
                if (n.bot == null) {
                    scene.placeImageXY(new RectangleImage(MazeWorld.TILE_SIZE, 2, 
                            OutlineMode.SOLID, Color.black), n.p.x * 
                            TILE_SIZE + shift, n.p.y * TILE_SIZE + shift + shift);
                }
                if (n.left == null) {
                    scene.placeImageXY(new RectangleImage(2, MazeWorld.TILE_SIZE, 
                            OutlineMode.SOLID, Color.black), n.p.x * 
                            TILE_SIZE, n.p.y * TILE_SIZE + shift);
                }
                if (n.right == null) {
                    scene.placeImageXY(new RectangleImage(2, MazeWorld.TILE_SIZE, 
                            OutlineMode.SOLID, Color.black), n.p.x * 
                            TILE_SIZE + shift + shift, n.p.y * TILE_SIZE + shift);
                }
            }
        }
        
        //draws the spanning tree
        if (displaySpanningTree) {
            for (ArrayList<Node> arr : g.nodes) {
                for (Node n : arr) {
                    if (n.top != null) {
                        scene.placeImageXY(new RectangleImage(2, MazeWorld.TILE_SIZE, 
                                OutlineMode.SOLID, Color.red), n.p.x * 
                                TILE_SIZE + shift, n.p.y * TILE_SIZE);
                    }
                    if (n.bot != null) {
                        scene.placeImageXY(new RectangleImage(2, MazeWorld.TILE_SIZE, 
                                OutlineMode.SOLID, Color.red), n.p.x * 
                                TILE_SIZE + shift, n.p.y * TILE_SIZE + shift + shift);
                    }
                    if (n.left != null) {
                        scene.placeImageXY(new RectangleImage(MazeWorld.TILE_SIZE, 2, 
                                OutlineMode.SOLID, Color.red), n.p.x * 
                                TILE_SIZE, n.p.y * TILE_SIZE + shift);
                    }
                    if (n.right != null) {
                        scene.placeImageXY(new RectangleImage(MazeWorld.TILE_SIZE, 2, 
                                OutlineMode.SOLID, Color.red), n.p.x * 
                                TILE_SIZE + shift + shift, n.p.y * TILE_SIZE + shift);
                    }
                }
            }
        }

        //renders when game complete
        if (isComplete) {
            scene.placeImageXY(new RectangleImage(300, 75, OutlineMode.SOLID, Color.BLACK),
                    CENTER_X, CENTER_Y);
            scene.placeImageXY(new TextImage("Congrats! You win.", 30, Color.RED), CENTER_X,
                    CENTER_Y - 15);
            scene.placeImageXY(new TextImage("Press 'n' to start a new game.", 18, Color.RED),
                    CENTER_X, CENTER_Y + 15);
        }
        
        return scene;
    }
    
    // reconstruct the path between the start and the finish of the maze
    // EFFECT: adds everything in the hashmap to visited
    public ArrayList<Node> reconstruct(HashMap<Node, Node> cameFromEdge, Node finish) {
        ArrayList<Node> solution = new ArrayList<Node>();
        Node prev = finish;
        solution.add(finish);
        //for (int i = 0; i < 20; i += 1) {
        while (!(prev.p.equals(new Posn(0, 0)))) {
            prev = cameFromEdge.get(prev);
            solution.add(prev);
        }
        return solution;
    }
    
    // Depth-First solver
    public ArrayList<Node> dfs() {
        // maps each node to the node it came from
        HashMap<Node, Node> cameFromEdge = new HashMap<Node, Node>(); 
        ArrayList<Node> worklist = new ArrayList<Node>(); // A stack (FILO)
        
        worklist.add(g.nodes.get(0).get(0));
        while (worklist.size() > 0) {
            Node next = worklist.get(worklist.size() - 1);
            if (visited.contains(next)) {
                worklist.remove(worklist.size() - 1);
            }
            else if (next.p.equals(new Posn(BOARD_WIDTH - 1, BOARD_HEIGHT - 1))) {
                return reconstruct(cameFromEdge, next);
            }
            else {
                visited.add(next);
                if (next.top != null) {
                    if (!cameFromEdge.containsKey(next.top)) {
                        cameFromEdge.put(next.top, next);
                    }
                    worklist.add(next.top);
                }
                if (next.bot != null) {
                    if (!cameFromEdge.containsKey(next.bot)) {
                        cameFromEdge.put(next.bot, next);
                    }
                    worklist.add(next.bot);
                }
                if (next.left != null) {
                    if (!cameFromEdge.containsKey(next.left)) {
                        cameFromEdge.put(next.left, next);
                    }
                    worklist.add(next.left);
                }
                if (next.right != null) {
                    if (!cameFromEdge.containsKey(next.right)) {
                        cameFromEdge.put(next.right, next);
                    }
                    worklist.add(next.right);
                }
            }
        }
        throw new RuntimeException("Maze is unsolvable by depth-first search");
    }

    // Breadth-First solver
    public ArrayList<Node> bfs() {
        // maps each node to the node it came from
        HashMap<Node, Node> cameFromEdge = new HashMap<Node, Node>(); 
        ArrayList<Node> worklist = new ArrayList<Node>(); // A queue (LILO)
        
        worklist.add(g.nodes.get(0).get(0));
        while (worklist.size() > 0) {
            Node next = worklist.get(0);
            if (visited.contains(next)) {
                worklist.remove(0);
            }
            else if (next.p.equals(new Posn(BOARD_WIDTH - 1, BOARD_HEIGHT - 1))) {
                return reconstruct(cameFromEdge, next);
            }
            else {
                visited.add(next);
                if (next.top != null) {
                    if (!cameFromEdge.containsKey(next.top)) {
                        cameFromEdge.put(next.top, next);
                    }
                    worklist.add(next.top);
                }
                if (next.bot != null) {
                    if (!cameFromEdge.containsKey(next.bot)) {
                        cameFromEdge.put(next.bot, next);
                    }
                    worklist.add(next.bot);
                }
                if (next.left != null) {
                    if (!cameFromEdge.containsKey(next.left)) {
                        cameFromEdge.put(next.left, next);
                    }
                    worklist.add(next.left);
                }
                if (next.right != null) {
                    if (!cameFromEdge.containsKey(next.right)) {
                        cameFromEdge.put(next.right, next);
                    }
                    worklist.add(next.right);
                }
            }
        }
        // should never get here
        throw new RuntimeException("Maze is unsolvable by breadth-first search"); 
    }
    
    // create ArrayList of Nodes
    public ArrayList<ArrayList<Node>> makeNodes() {
        ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();
        ArrayList<Node> aRow = new ArrayList<Node>();

        // initializes board with unconnected nodes
        for (int r = 0; r < BOARD_HEIGHT; r += 1) {
            aRow = new ArrayList<Node>();
            for (int c = 0; c < BOARD_WIDTH; c += 1) {
                aRow.add(new Node(new Posn(c, r)));
            }
            nodes.add(aRow);
        }
        return nodes;
    }
    
}

// Maze Game Examples
class ExamplesMaze {
    // Maze World
    MazeWorld maze;
    MazeWorld testMaze;
    
    // Node Examples
    Node n1 = new Node(new Posn(0, 0));
    Node n2 = new Node(new Posn(0, 50));
    Node n3 = new Node(new Posn(50, 0));
    Node n4 = new Node(new Posn(50, 50));
    Node n5 = new Node(new Posn(25, 25));
    Node n6 = new Node(new Posn(0, 25));
    Node n7 = new Node(new Posn(25, 0));
    Node n8 = new Node(new Posn(50, 25));
    Node n9 = new Node(new Posn(25, 50));
    
    // Edge Examples
    Edge e1 = new Edge(1, n1, n6);
    Edge e2 = new Edge(1, n1, n7);
    Edge e3 = new Edge(2, n5, n6);
    Edge e4 = new Edge(2, n5, n7);

    // ArrayList Examples
    ArrayList<Integer> a1 = new ArrayList<Integer>();
    ArrayList<Integer> a2 = new ArrayList<Integer>();
    ArrayList<Integer> a3 = new ArrayList<Integer>();
    ArrayList<Integer> a4 = new ArrayList<Integer>();

    // comparators
    Comparator<Integer> numOrder = new NumOrderComp();
    Comparator<Edge> edgeWeightOrder = new EdgeWeightOrderComp();
    
    // maze initializer
    void initMaze() {
        maze = new MazeWorld();
        testMaze = new MazeWorld();
    }
    
    // ArrayList initializer
    void initArrays() {
        a1.add(1);
        a1.add(3);
        a1.add(-2);
        a1.add(0);
        a1.add(9);
        a1.add(6);
        a1.add(-7);
        a1.add(0);
        a1.add(1);
        a1.add(45);
        a2.add(-7);
        a2.add(-2);
        a2.add(0);
        a2.add(0);
        a2.add(1);
        a2.add(1);
        a2.add(3);
        a2.add(6);
        a2.add(9);
        a2.add(45);
        a3.add(0);
        a4.add(0);

    }
    
    // tester method
    void test(Tester t) {
        
        // initialize maze
        initMaze();
        
        // initialize arrays
        initArrays();
        
        // runner
        maze.bigBang(MazeWorld.BOARD_WIDTH_PX, MazeWorld.BOARD_HEIGHT_PX, 1);
        
        // test quicksort
        Collections.sort(a1, numOrder);
        Collections.sort(a3, numOrder);
        t.checkExpect(a1, a2);
        t.checkExpect(a3, a4);
        
        // test makeNodes
        t.checkExpect(maze.g.nodes.size(), MazeWorld.BOARD_HEIGHT);
        t.checkExpect(maze.g.nodes.get(0).size(), MazeWorld.BOARD_WIDTH);
        String posnFlag;
        for (int r = 0; r < maze.g.nodes.size() - 1; r += 1) {
            for (int c = 0; c < maze.g.nodes.get(0).size() - 1; c += 1) {
                
                if (maze.g.nodes.get(r).get(c).top != null ||
                        maze.g.nodes.get(r).get(c).bot != null ||
                        maze.g.nodes.get(r).get(c).left != null ||
                        maze.g.nodes.get(r).get(c).right != null) {
                    posnFlag = "";
                }
                else {
                    posnFlag = c + ", " + r;
                }
                t.checkExpect(posnFlag, "");
            }
        }
        
        //test getEdges
        ArrayList<String> testGetEdges = new ArrayList<String>();
        for (Edge edge : maze.g.getEdges()) {
            testGetEdges.add(edge.weight + ": (" + edge.from.p.x + ", " + edge.from.p.y + ") => ("
                    + edge.to.p.x + ", " + edge.to.p.y + ")");
        }
        
        t.checkExpect(testGetEdges.size(), MazeWorld.BOARD_HEIGHT * MazeWorld.BOARD_WIDTH * 2 - 
                MazeWorld.BOARD_HEIGHT - MazeWorld.BOARD_WIDTH);
        
        //tests goodMove
        testMaze.player = new Posn(1, 1);
        testMaze.g.nodes.get(testMaze.player.y).get(testMaze.player.x).top = new 
                Node(new Posn(1, 0));
        t.checkExpect(testMaze.goodMove("up"), true);
        testMaze.g.nodes.get(testMaze.player.y).get(testMaze.player.x).top = null;
        t.checkExpect(testMaze.goodMove("up"), false);
        
        // test dfs
        boolean result3 = false;
        testMaze.visited = new ArrayList<Node>();
        Posn samplePosn2 = new Posn(MazeWorld.BOARD_WIDTH - 1, MazeWorld.BOARD_HEIGHT - 1);
        for (Node n : testMaze.dfs()) {
            result3 = result3 || (n.p.x == samplePosn2.x && n.p.y == samplePosn2.y);
        }
        t.checkExpect(result3, true);
        
        // test bfs
        boolean result4 = false;
        testMaze.visited = new ArrayList<Node>();
        Posn samplePosn3 = new Posn(MazeWorld.BOARD_WIDTH - 1, MazeWorld.BOARD_HEIGHT - 1);
        for (Node n : testMaze.dfs()) {
            result4 = result4 || (n.p.x == samplePosn3.x && n.p.y == samplePosn3.y);
        }
        t.checkExpect(result4, true);
        
        // test span
        t.checkExpect(maze.spanningTree.size(), maze.g.nodes.size() * 
                maze.g.nodes.get(0).size() - 1);
        
        //tests addVisited
        boolean result = false;
        Posn samplePosn = new Posn(800, 800);
        for (Node n : testMaze.visited) {
            result = result || (n.p.x == samplePosn.x && n.p.y == samplePosn.y);
        }
        t.checkExpect(result, false);
        boolean result2 = false;
        testMaze.addVisited(samplePosn);
        for (Node n : testMaze.visited) {
            result2 = result2 || (n.p.x == samplePosn.x && n.p.y == samplePosn.y);
        }
        t.checkExpect(result2, true);
        
        //test makeNodes
        t.checkExpect(testMaze.makeNodes().size(), MazeWorld.BOARD_HEIGHT);
        
    }
}
