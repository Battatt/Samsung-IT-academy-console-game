import java.util.Scanner
import java.util.Random

class GameClass(
    var player: Entity,
    var enemy: Entity,
    var errorCode: Int = 1,
    var playerAttack: Int = 0,
    var playerDamage: Int = 0,
    var wins: Int = 0,
    val input: Scanner = Scanner(System.`in`)
) {

    private val random: Random = Random()


    private fun createRandomEnemy() {

        val enemyType = random.nextInt(3)
        enemy = when (enemyType) {
            0 -> Knight()
            1 -> Wizard()
            else -> Robot()
        }
    }

    fun error(): Int {
        println("При ходе игры возникла ошибка. Завершение работы...")
        errorCode = -1
        return -1
    }

    private fun clearConsole() {
        for (i in 1..200) {
            println("")
        }
    }

    fun statistics() {
        clearConsole()
        print(player)
        for (i in 1..20)
            print(" ")
        println(enemy)
    }

    fun choosePlayer(): Int {
        println("Выбор персонажа")
        println("1 - Рыцарь, 2 - Маг, 3 - Робот")
        val playerChoice = input.nextInt()
        player = when (playerChoice) {
            1 -> Knight()
            2 -> Wizard()
            3 -> Robot()
            else -> {
                println("Неверный выбор!")
                return error()
            }
        }
        return 0
    }

    fun drawCharacters() {
        println("  O                                                               O  ")
        println(" /|\\                                                             /|\\ ")
        println(" / \\                                                             / \\ ")
    }

    fun endGameStatistics() {
        println("Побед игрока - $wins")
        println("Нанесено урона - $playerAttack")
        println("Получено урона - $playerDamage")
        println("                        O")
        println("                       /|\\")
        println("                       / \\ ")
        println("                    Конец игры")
    }



    fun play(check:Boolean = true){
        createRandomEnemy()
        if (check) {
            choosePlayer()
        }
        player.full()
        enemy.full()
        var playerTurn = 0
        var enemyTurn = 0

        while (player.isAlive() && enemy.isAlive() && errorCode != -1) {

            statistics()
            drawCharacters()
            for (i in 1..40)
                print("//")
            println("//")
            println("Ход игрока. 1 - атака, 2 - лечение, 3 - пропуск хода")


            playerTurn = when(input.nextInt()) {
                1 -> player.attack()
                2 -> player.heal()
                3 -> player.pass()
                else -> 0
            }
            when (playerTurn) {
                1 -> println("Игрок - лечение")
                0 -> println("Игрок - пропуск хода")
                else -> {
                    playerAttack += playerTurn
                    enemy.damage(playerTurn)
                    println("Игрок нанес $playerTurn урона противнику")
                }
            }
            if (!enemy.isAlive()) {
                println("Поздравляем! Вы победили противника!")
                wins++
                break
            }

            enemyTurn = enemy.ai()
            when (enemyTurn) {
                1 -> println("Противник - лечение")
                0 -> println("Противник - пропуск хода")
                else -> {
                    player.damage(enemyTurn)
                    playerDamage += enemyTurn
                    println("Противник нанес $enemyTurn урона игроку")
                }
            }
            if (!player.isAlive()) {
                println("Вы проиграли! Противник одержал победу.")
                return endGameStatistics()
            }
            Thread.sleep(800)
        }
        println("Продолжить игру? 1 - Да, 0 - нет")
        return when (input.nextInt()) {
            1 -> play(check=false)
            else -> endGameStatistics()
        }

    }

}
