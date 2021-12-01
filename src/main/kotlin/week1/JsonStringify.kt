package week1

sealed class JsonElement
data class JsonObject(val fields: Map<String, JsonElement>) : JsonElement() {
  constructor(vararg fields: Pair<String, JsonElement>) : this(fields.toMap())
}

data class JsonArray(val elements: List<JsonElement>) : JsonElement() {
  constructor(vararg elements: JsonElement) : this(elements.toList())
}

data class JsonNumber(val value: Double) : JsonElement()
data class JsonString(val value: String) : JsonElement()
data class JsonBoolean(val value: Boolean) : JsonElement()
object JsonNull : JsonElement()

fun JsonElement.stringify(): String {
  when (this) {
    is JsonObject -> {
      return if (fields.isEmpty()) {
        "{}"
      } else {
        fields.map {
          "\"${it.key}\":${it.value.stringify()}"
        }.joinToString(",", "{", "}")
      }
    }
    is JsonNull -> {
      return "null"
    }
    is JsonNumber -> {
      return value.toString().replace(Regex("\\.0+$"), "")
    }
    is JsonBoolean -> {
      return value.toString()
    }
    is JsonString -> {
      return "\"${value.replace("\"", "\\\"")}\""
    }
    is JsonArray -> {
      return if (elements.isEmpty()) {
        "[]"
      } else {
        elements.joinToString(",", "[", "]") {
          it.stringify()
        }
      }
    }
    else -> throw IllegalStateException()
  }
}
