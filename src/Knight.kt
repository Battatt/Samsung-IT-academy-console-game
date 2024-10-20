import java.util.*

class Knight(health: Int = 100, power: Int = 25, var defence: Int = 60, var koef: Float = 0.5f, desc: String = "Рыцарь"): Entity(health, power, desc) {

    override fun attack(): Int {
        return power
    }

    override fun heal(): Int {
        health += 15
        if (health > 100) health = 100
        return 1
    }

    override fun toString(): String {
        return "$desc. Здоровье: $health. Броня: $defence."
    }

    override fun damage(decrease: Int) {
        defenceCheck()
        if (defence > 0) {
            val effectiveDamage = Math.min(decrease, defence)
            defence -= effectiveDamage
            health -= ((decrease - effectiveDamage) * koef).toInt()
        } else {
            health -= (decrease * koef).toInt()
        }
        if (health < 0) health = 0
    }

    private fun defenceCheck() {
        koef = if (defence <= 0) 1.0f else 0.5f
    }

    override fun pass(): Int {
        defence += 10 // Уменьшено количество восстанавливаемой защиты
        if (defence > 50) defence = 50
        return 0
    }

    override fun ai(): Int {
        return when {
            defence < 10 -> pass()
            health < 30 -> heal()
            Random().nextInt(200) < 60 -> pass() // Увеличен шанс атаки до 60%
            else -> attack()
        }
    }

    override fun full() {
        health = 100
        defence = 50
    }

    override fun getInfo(): Array<Any> {
        return arrayOf(health, power, defence, desc)
    }
}