package TemporaryData

// Временный класс для имитации БД

class UserBD(val login: String, val password: String) {
}

object TemporaryUserStorage {
    val all_user_data = listOf(
        UserBD("st_10001", "121"),
        UserBD("st_10002", "122"),
        UserBD("st_10003", "123"),
        UserBD("st_10004", "124"),
        UserBD("te_101", "4321"),
        UserBD("ht_101", "11223344")
    )
}