fun main() {
    fun part1(input: List<String>): Int {
        val allLocations = input.map { it.split("-") }.flatten().toSet()

        val edges = input.map { it.split("-") }.groupBy { it[0] }
        val newEdges = hashMapOf<Graph.Node, List<Graph.Node>>()
        edges.map {
            newEdges[Graph.Node(it.key)] = it.value
                .flatten()
                .filterNot { location -> location == it.key }
                .map { neighbour -> Graph.Node(neighbour) }
        }
        val graph = Graph(allLocations, newEdges)
        val paths = partOneSearch(graph, graph.startNode, graph.endNode)
        return paths.size
    }

    fun part2(input: List<String>): Int {
        val allLocations = input.map { it.split("-") }.flatten().toSet()

        val edges = input.map { it.split("-") }.groupBy { it[0] }
        val newEdges = hashMapOf<Graph.Node, List<Graph.Node>>()
        edges.map {
            newEdges[Graph.Node(it.key)] = it.value
                .flatten()
                .filterNot { location -> location == it.key }
                .map { neighbour -> Graph.Node(neighbour) }
        }
        val graph = Graph(allLocations, newEdges)
        val paths = partTwoSearch(graph, graph.startNode, graph.endNode)
        return paths.size
    }

    val testInput = readInput("src/week2", "Day12_test")
    println("Part 1 test " + if (part1(testInput) == 10) "passed" else "failed")
    println("Part 2 test " + if (part2(testInput) == 36) "passed" else "failed")

    val input = readInput("src/week2", "Day12")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

data class Graph(val allLocations: Set<String>, val input: HashMap<Node, List<Node>>) {
    data class Node(val label: String) {
        val size: CaveSize = if (label.first().isLowerCase()) CaveSize.Small else CaveSize.Large
    }

    private val allNodes: List<Node> = allLocations.map { Node(it) }
    val endNode = allNodes.first { it.label == "end" }
    val startNode = allNodes.first { it.label == "start" }
    private val edges = HashMap<Node, List<Node>>()
    init {
        input.forEach { edges[it.key] = it.value }
        for (location in input) {
            location.value.forEach { neighbour ->
                if (edges[neighbour].isNullOrEmpty() || edges[neighbour]?.contains(location.key) == false) {
                    edges[neighbour] = (edges[neighbour] ?: emptyList()) + listOf(location.key)
                }
            }
        }

        edges[endNode] = emptyList()
    }

    fun getNeighbours(node: Node): List<Node>? {
        return edges[node]
    }
}

fun partOneSearch(Graph: Graph, start: Graph.Node, end: Graph.Node): List<List<Graph.Node>> {
    val queue = ArrayDeque<Pair<Graph.Node, List<Graph.Node>>>()
    queue.addLast(start to listOf(start))

    val paths = mutableListOf<List<Graph.Node>>()

    while (queue.isNotEmpty()) {
        val queued = queue.removeFirst()
        val currentLocation = queued.first
        val currentPath = queued.second

        val neighbours = Graph.getNeighbours(currentLocation) ?: break
        for (node in neighbours) {
            if (neighbours.isNotEmpty() && (currentPath.none { it == node } || node.size == CaveSize.Large)) {
                val newPath = currentPath + node
                if (node == end) paths.add(newPath)
                else queue.addLast(node to newPath)
            }
        }
    }
    return paths
}

fun partTwoSearch(Graph: Graph, start: Graph.Node, end: Graph.Node): List<List<Graph.Node>> {
    val queue = ArrayDeque<Pair<Graph.Node, List<Graph.Node>>>()
    queue.addLast(start to listOf(start))

    val paths = mutableListOf<List<Graph.Node>>()

    while (queue.isNotEmpty()) {
        val queued = queue.removeFirst()
        val currentLocation = queued.first
        val currentPath = queued.second

        val neighbours = Graph.getNeighbours(currentLocation) ?: break
        for (node in neighbours) {
            if (checkIfMovementPossible(node, currentPath)) {
                val newPath = currentPath + node
                queue.addLast(node to newPath)
            } else if (node == end) {
                paths.add(currentPath + node)
            }
        }
    }
    return paths
}

fun checkIfMovementPossible(node: Graph.Node, currentPath: List<Graph.Node>): Boolean {
    return when {
        node.size == CaveSize.Large -> true
        node.label == "start" || node.label == "end" -> false
        currentPath.contains(node) -> {
            val smallCaves = currentPath.filter { it.size == CaveSize.Small }
            return smallCaves.size == smallCaves.toSet().size
        }
        else -> true
    }
}

enum class CaveSize {
    Small,
    Large
}