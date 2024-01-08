package fr.aboin.cockteirb.core.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/**
 * A object representing a cocktail with all its details (when getting a cocktail by id)
 */
class Cocktail : CocktailSummary {
    var alternateTitle: String? = null
    var tags: List<String>? = null
    var videoURL: String? = null
    var category: String? = null
    var iba: String? = null
    var alcoholic: String? = null
    var glass: String? = null
    var instructions: HashMap<String, String>? = null
    var ingredients: List<String>? = null
    var measures: List<String>? = null
    var imageSource: String? = null
    var imageAttribution: String? = null
    var creativeCommonsConfirmed: String? = null
    var dateModified: String? = null

    constructor(
        id: String?,
        title: String?,
        alternateTitle: String?,
        tags: List<String>?,
        videoURL: String?,
        category: String?,
        iba: String?,
        alcoholic: String?,
        glass: String?,
        instructions: HashMap<String, String>?,
        imageURL: String?,
        ingredients: List<String>?,
        measures: List<String>?,
        imageSource: String?,
        imageAttribution: String?,
        creativeCommonsConfirmed: String?,
        dateModified: String?
    ) {
        this.id = id
        this.title = title
        this.alternateTitle = alternateTitle
        this.tags = tags
        this.videoURL = videoURL
        this.category = category
        this.iba = iba
        this.alcoholic = alcoholic
        this.glass = glass
        this.instructions = instructions
        this.imageURL = imageURL
        this.ingredients = ingredients
        this.measures = measures
        this.imageSource = imageSource
        this.imageAttribution = imageAttribution
        this.creativeCommonsConfirmed = creativeCommonsConfirmed
        this.dateModified = dateModified
    }

    override fun toString(): String {
        return "Cocktail(\n\tid = $id,\n\ttitle = $title,\n\talternateTitle = $alternateTitle,\n\ttags = $tags,\n\tvideoURL = $videoURL,\n\tcategory = $category,\n\tiba = $iba,\n\talcoholic = $alcoholic,\n\tglass = $glass,\n\tinstructions = $instructions,\n\timageURL = $imageURL,\n\tingredients = $ingredients,\n\tmeasures = $measures,\n\timageSource = $imageSource,\n\timageAttribution = $imageAttribution,\n\tcreativeCommonsConfirmed = $creativeCommonsConfirmed,\n\tdateModified = $dateModified\n)"
    }
}

class CocktailDeserializer : JsonDeserializer<Cocktail> {
    override fun deserialize(
        body: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Cocktail {
        body as JsonObject
        if (body.get("drinks").isJsonNull) {
            throw Exception("No cocktail was found with this id")
        }
        val json = body.getAsJsonArray("drinks")[0].asJsonObject

        val id = json.get("idDrink").asString
        val title = json.get("strDrink").asString
        val alternateTitle = if (json.get("strDrinkAlternate").isJsonNull) null else json.get("strDrinkAlternate").asString
        val tagsRaw = if (json.get("strTags").isJsonNull) "" else json.get("strTags").asString
        val tags = tagsRaw.split(",")
        val videoURL = if (json.get("strVideo").isJsonNull) null else json.get("strVideo").asString
        val category = if (json.get("strCategory").isJsonNull) null else json.get("strCategory").asString
        val iba = if (json.get("strIBA").isJsonNull) null else json.get("strIBA").asString
        val alcoholic = if (json.get("strAlcoholic").isJsonNull) null else json.get("strAlcoholic").asString
        val glass = if (json.get("strGlass").isJsonNull) null else json.get("strGlass").asString

        val instructions = HashMap<String, String>()
        val instructionsMapping: HashMap<String, String> = hashMapOf(
            "strInstructions" to "EN",
            "strInstructionsES" to "ES",
            "strInstructionsDE" to "DE",
            "strInstructionsFR" to "FR",
            "strInstructionsIT" to "IT",
            "strInstructionsZH-HANS" to "ZH-HANS",
            "strInstructionsZH-HANT" to "ZH-HANT"
        )

        for ((key, value) in instructionsMapping) {
            if (!json.get(key).isJsonNull) {
                instructions[value] = json.get(key).asString.trim()
            }
        }

        val imageURL = json.get("strDrinkThumb").asString

        val ingredients = ArrayList<String>()
        val measures = ArrayList<String>()

        // For loop to add ingredients and measures to the lists
        for (i in 1..15) {
            if (!json.get("strIngredient$i").isJsonNull) {
                ingredients.add(json.get("strIngredient$i").asString.trim())
            }
            if (!json.get("strMeasure$i").isJsonNull) {
                measures.add(json.get("strMeasure$i").asString.trim())
            }
        }

        val imageSource = if (json.get("strImageSource").isJsonNull) null else json.get("strImageSource").asString
        val imageAttribution = if (json.get("strImageAttribution").isJsonNull) null else json.get("strImageAttribution").asString
        val creativeCommonsConfirmed = if (json.get("strCreativeCommonsConfirmed").isJsonNull) null else json.get("strCreativeCommonsConfirmed").asString
        val dateModified = if (json.get("dateModified").isJsonNull) null else json.get("dateModified").asString

        return Cocktail(
            id,
            title,
            alternateTitle,
            tags,
            videoURL,
            category,
            iba,
            alcoholic,
            glass,
            instructions,
            imageURL,
            ingredients,
            measures,
            imageSource,
            imageAttribution,
            creativeCommonsConfirmed,
            dateModified
        )
    }
}