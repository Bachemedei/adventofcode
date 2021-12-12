fun main() {
    fun part1(input: List<String>): Int {
        val energyMap = mutableListOf<List<Int>>()
        input.forEach { row ->
            energyMap.add(row.map { it.digitToInt() }) }
        val octopuses = Graph(energyMap)
        var stepCount = 0
        while (stepCount < 100) {
            step(octopuses, octopuses.firstOctopus)
            stepCount ++
        }
        return octopuses.getStepCount()
    }

    fun part2(input: List<String>): Int {
        val energyMap = mutableListOf<List<Int>>()
        input.forEach { row ->
            energyMap.add(row.map { it.digitToInt() }) }
        val octopuses = Graph(energyMap)
        var stepCount = 0
        while (!octopuses.isSynchronisedFlash()) {
            step(octopuses, octopuses.firstOctopus)
            stepCount ++
        }
        return stepCount
    }

    fun run() {
        val testInput = readInput("src/week2", "Day11_test")
        println("Part 1 test " + if (part1(testInput) == 1656) "passed" else "failed")
        println("Part 2 test " + if (part2(testInput) == 195) "passed" else "failed")

        val input = readInput("src/week2", "Day11")
        println("Part 1 answer: ${part1(input)}")
        println("Part 2 answer: ${part2(input)}")
    }
    run()
}

private fun step(graph: Graph, start: Graph.Octopus): HashMap<Graph.Octopus, Boolean> {
    graph.reset()
    graph.increaseEnergy()

    val queue = ArrayDeque<Graph.Octopus>()
    queue.addLast(start)

    val reached = HashMap<Graph.Octopus, Boolean>()
    reached[start] = true

    while (queue.isNotEmpty()) {
        val currentOctopus = queue.removeFirst()
        val neighbours = graph.getNeighbours(currentOctopus)
        for (octopus in neighbours) {
            val hasFlashed = octopus.hasFlashed
            if (currentOctopus.hasFlashed)
                octopus.increaseEnergy()

            if (reached.filter { it.key.x == octopus.x && it.key.y == octopus.y }.isEmpty()) {
                queue.addLast(octopus)
                reached[octopus] = true
            } else if (hasFlashed != octopus.hasFlashed && queue.none { it.x == octopus.x && it.y == octopus.y }) {
                queue.addLast(octopus)
            }
        }
    }
    return reached
}

private data class Graph(val energy: List<List<Int>>) {
    data class Octopus(val x: Int, val y: Int, var energy: Int) {
        var hasFlashed = false
        var flashCounter = 0

        fun increaseEnergy() {
            energy++
            if (energy > 9) flash()
        }

        fun reset() {
            if (hasFlashed) energy = 0
            hasFlashed = false
        }

        fun flash() {
            if (hasFlashed) return
            hasFlashed = true
            flashCounter++
        }

        override fun equals(other: Any?): Boolean {
            return if (other is Octopus)  (x == other.x && y == other.x) else false
        }
    }

    private val allOctopuses = mutableListOf<Octopus>()
    private val edges = HashMap<Octopus, List<Octopus>>()
    lateinit var firstOctopus: Octopus

    init {
        for (x in  0..9) {
            for (y in 0..9) {
                val octopus = Octopus(x, y, energy[x][y])
                allOctopuses.add(octopus)
            }
        }
        for (node in allOctopuses) {
            edges[node] = findNeighbours(node)
            firstOctopus = allOctopuses.find { it.x == 0 && it.y == 0 }!!
        }
    }

    fun reset() {
        allOctopuses.forEach { it.reset() }
    }

    fun increaseEnergy() {
        allOctopuses.forEach { it.increaseEnergy() }
    }

    fun getStepCount(): Int {
        return allOctopuses.sumOf { it.flashCounter }
    }

    fun getNeighbours(octopus: Octopus): List<Octopus> {
        val neighbours = edges.filterKeys { it.x == octopus.x && it.y == octopus.y }
        return neighbours.values.flatten()
    }

    fun isSynchronisedFlash(): Boolean {
        return allOctopuses.filter { it.hasFlashed }.size == allOctopuses.size
    }

    private fun findNeighbours(octopus: Octopus): List<Octopus> {
        val directions = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1, 1 to -1, -1 to -1, -1 to 1, 1 to 1)
        val neighbours = mutableListOf<Octopus>()
        for (direction in directions) {
            val location = octopus.x + direction.first to octopus.y + direction.second
            if (isInMap(location)) {
                val neighbour = allOctopuses.find { it.x == location.first && it.y == location.second }
                if (neighbour != null) neighbours.add(neighbour)
            }
        }
        return neighbours
    }

    private fun isInMap(location: Pair<Int, Int>): Boolean {
        if (location.first < 0 || location.second < 0) return false
        return allOctopuses.find { it.x == location.first && it.y == location.second } != null
    }
}
