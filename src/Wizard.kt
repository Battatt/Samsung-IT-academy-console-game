import java.util.*

class Wizard(health: Int = 100, power: Int = 20, var mana: Int = 100, desc: String = "Маг") : Entity(health, power, desc) {
    override fun damage(decrease: Int) {
        health -= decrease
        if (health < 0) health = 0
    }

    override fun getInfo(): Array<Any> {
        return arrayOf(health, power, mana, desc)
    }

    override fun toString(): String {
        return "$desc. Здоровье: $health. Мана: $mana."
    }

    override fun attack(): Int {
        return if (manaCheck(10)) {
            mana -= 10
            power
        } else {
            pass()
        }
    }

    override fun heal(): Int {
        return if (manaCheck(20)) {
            mana -= 20
            health = (health + 30).coerceAtMost(100)
            1
        } else {
            pass()
        }
    }

    private fun manaCheck(decrease: Int): Boolean {
        return decrease <= mana
    }

    override fun pass(): Int {
        mana += 10
        if (mana > 100) mana = 100
        return 0
    }

    override fun ai(): Int {
        return when {
            health <= 40 && mana >= 20 -> heal()
            mana < 10 -> pass()
            Random().nextInt(100) < 50 -> attack()
            else -> pass()
        }
    }

    override fun full() {
        health = 100
        mana = 100
    }
}
