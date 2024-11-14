import java.sql.Statement

data class QgsForm(
    val statement: String,
    val answer: String
)

enum class QgsAnswer {
    TRUE, FALSE, NOT_GIVEN
}