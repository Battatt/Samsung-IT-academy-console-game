import java.util.*

class Robot(health: Int = 100, power: Int = 15, var battery: Int = 100, var k: Int = 3, desc: String = "Робот"): Entity(health, power, desc) {
    override fun damage(decrease: Int) {
        health -= decrease
        if (health < 0) health = 0
    }

    override fun getInfo(): Array<Any> {
        return arrayOf(health, power, battery, desc)
    }

    override fun toString(): String {
        return "$desc. Здоровье: $health. Батарея: $battery."
    }

    override fun attack(): Int {
        batteryChecker()
        return if (battery >= 20) {
            battery -= 20
            power * k
        } else {
            0
        }
    }

    override fun heal(): Int {
        batteryChecker()
        return if (battery >= 15) {
            health += 25
            battery -= 15
            if (health > 100) health = 100
            1
        } else {
            0
        }
    }

    private fun batteryChecker() {
        k = when {
            battery <= 0 -> {
                health = 0
                -1
            }
            battery <= 50 -> 1
            battery <= 75 -> 2
            else -> 3
        }
    }

    override fun pass(): Int {
        batteryChecker()
        battery += 20
        if (battery > 100) battery = 100
        return 0
    }

    override fun ai(): Int {
        batteryChecker()
        return when {
            health <= 40 && battery >= 15 -> heal()
            battery < 20 -> pass()
            Random().nextInt(200) < 60 -> pass()
            else -> attack()
        }
    }

    override fun full() {
        health = 100
        battery = 100
    }
}