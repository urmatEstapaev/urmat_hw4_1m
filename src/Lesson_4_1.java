import java.util.Random;

public class Lesson_4_1 {
    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 300, 340, 230, 320, 280};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 0, 0, 5};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber;
    public static int medicHeal = 100;
    public static boolean medicAive = true;
    public static boolean golemAlive = true;
    public static boolean luckyAlive = true;
    public static boolean witcherAlive = true;
    public static boolean thorAlive = true;
    public static boolean bossStunned = false;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        if (!bossStunned) {
            bossAttack();
        }
        bossAttack();
        heroesAttack();
        printStatistics();
        medicHeal();
        golemDefender();
        witcherRevive();
        luckyChance();
        thorStunner();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                int damage = bossDamage;
                if (heroesAttackType[i] == "Golem") {
                    damage = damage / 5;
                }
                heroesHealth[i] = heroesHealth[i] - damage;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHeal() {
        int medic = 3;
        if (!medicAive || heroesHealth[medic] <= 0) {
            medicAive = false;
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i != medic && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                heroesHealth[i] += medicHeal;
                System.out.println("medic healed " + heroesAttackType[i] + " for" + medicHeal + "health");
                break;
            }
        }
    }

    public static void golemDefender() {
        if (golemAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem") {
                    heroesHealth[i] = heroesHealth[i] - bossDamage / 5;
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                }
            }
        }
    }

    public static void witcherRevive() {
        if (witcherAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0 && i != 3) {
                    heroesHealth[i] = 150;
                    witcherAlive = false;
                    System.out.println("witcher revive " + heroesAttackType[i] + " but died");
                    break;
                }
            }
        }
    }

    public static void luckyChance() {
        if (luckyAlive && bossDamage > 0) {
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky) {
                System.out.println("lucky dodged the boss's blows");
            }
        }
    }

    public static void thorStunner() {
        if (thorAlive && !bossStunned) {
            Random random = new Random();
            boolean isStunned = random.nextBoolean();
            if (isStunned) {
                bossStunned = true;
                System.out.println("Thor stunned the boss for 1 round");
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}