fun main() {
    fun part1(input: List<String>): List<Int> {
        val heightMap = mutableListOf<List<Int>>()
        input.forEach { row ->
            heightMap.add(row.map { it.digitToInt() }) }

        val map = Graph1(heightMap[0].lastIndex, heightMap.lastIndex, heightMap)

        val lowPoints = map.lowPoints
        val basins = mutableListOf<Int>()

        for (lowPoint in map.lowPoints) {
            val basin = breadthFirstSearch(map, lowPoint).filter { it.value }
            basins.add(basin.size)
        }

        val part1 = lowPoints.fold(0) {acc, node -> acc + 1 + node.label }
        val part2 = basins.sortedDescending().subList(0, 3).reduce { acc, i -> acc * i }
        return listOf(part1, part2)
    }

    val testInput = readInput("src/week2", "Day09_test")
    val testAnswer = part1(testInput)
    println("Part 1 test " + if (testAnswer[0] == 15) "passed" else "failed")
    println("Part 2 test " + if (testAnswer[1] == 1134) "passed" else "failed")

    val input = readInput("src/week2", "Day09")
    val answer = part1(input)
    println("Part 1 answer: ${answer[0]}")
    println("Part 2 answer: ${answer[1]}")
}

data class Graph1(val length: Int, val height: Int, val labels: List<List<Int>>) {
    data class Node(val x: Int, val y: Int, var label: Int)

    private val allNodes = mutableListOf<Node>()
    private val edges = HashMap<Node, List<Node>>()
    val lowPoints = mutableListOf<Node>()

    init {
        for (x in  0..height) {
            for (y in 0..length) {
                val node = Node(x, y, labels[x][y])
                allNodes.add(node)
            }
        }
        for (node in allNodes) {
            edges[node] = findNeighbours(node)
        }
    }

    fun getNeighbours(node: Node): List<Node>? {
        return edges[node]
    }

    private fun findNeighbours(node: Node): List<Node> {
        val directions = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
        val neighbours = mutableListOf<Node>()
        for (direction in directions) {
            val location = node.x + direction.first to node.y + direction.second
            if (isInMap(location) && isInBasin(location)) {
                val neighbour = Node(location.first, location.second, labels[location.first][location.second])
                neighbours.add(neighbour)
            }
        }
        if (neighbours.isNotEmpty() && isLowPoint(node, neighbours)) lowPoints.add(node)
        return neighbours
    }

    private fun isLowPoint(node: Node, neighbours: List<Node>): Boolean {
        val lower = mutableListOf<Node>()
        for (neighbour in neighbours) {
            if (node.label > neighbour.label) lower.add(neighbour)
        }

        return lower.isEmpty()
    }

    private fun isInMap(location: Pair<Int, Int>): Boolean {
        if (location.first < 0 || location.second < 0) return false
        return allNodes.find { it.x == location.first && it.y == location.second } != null
    }

    private fun isInBasin(location: Pair<Int, Int>): Boolean {
        if (location.first < 0 || location.second < 0) return false
        return labels[location.first][location.second] < 9
    }
}

fun breadthFirstSearch(graph1: Graph1, start: Graph1.Node): HashMap<Graph1.Node, Boolean> {
    val queue = ArrayDeque<Graph1.Node>()
    queue.addLast(start)

    val reached = HashMap<Graph1.Node, Boolean>()
    reached[start] = true

    while (queue.isNotEmpty()) {
        val currentLocation = queue.removeFirst()
        val neighbours = graph1.getNeighbours(currentLocation) ?: emptyList()
        for (node in neighbours) {
            if (!reached.containsKey(node)) {
                queue.addLast(node)
                reached[node] = true
            }
        }
    }
    return reached
}