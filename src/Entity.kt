abstract class Entity(
    var health: Int,
    var power: Int,
    var desc: String = "NONE"
) {
    abstract fun attack(): Int
    abstract fun heal(): Int
    abstract fun getInfo(): Array<Any>
    override fun toString(): String {
        return "$desc. Здоровье: $health"
    }

    abstract fun damage(decrease: Int)

    abstract fun pass(): Int

    abstract fun ai(): Int

    fun isAlive(): Boolean{
        return health > 0
    }
    open fun full() {
        health = 100
    }
}