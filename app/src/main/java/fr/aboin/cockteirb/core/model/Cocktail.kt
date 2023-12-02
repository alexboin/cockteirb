package fr.aboin.cockteirb.core.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/*
{
  "drinks": [
    {
      "idDrink": "11007",
      "strDrink": "Margarita",
      "strDrinkAlternate": null,
      "strTags": "IBA,ContemporaryClassic",
      "strVideo": null,
      "strCategory": "Ordinary Drink",
      "strIBA": "Contemporary Classics",
      "strAlcoholic": "Alcoholic",
      "strGlass": "Cocktail glass",
      "strInstructions": "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten only the outer rim and sprinkle the salt on it. The salt should present to the lips of the imbiber and never mix into the cocktail. Shake the other ingredients with ice, then carefully pour into the glass.",
      "strInstructionsES": null,
      "strInstructionsDE": "Reiben Sie den Rand des Glases mit der Limettenscheibe, damit das Salz daran haftet. Achten Sie darauf, dass nur der äußere Rand angefeuchtet wird und streuen Sie das Salz darauf. Das Salz sollte sich auf den Lippen des Genießers befinden und niemals in den Cocktail einmischen. Die anderen Zutaten mit Eis schütteln und vorsichtig in das Glas geben.",
      "strInstructionsFR": null,
      "strInstructionsIT": "Strofina il bordo del bicchiere con la fetta di lime per far aderire il sale.\r\nAvere cura di inumidire solo il bordo esterno e cospargere di sale.\r\nIl sale dovrebbe presentarsi alle labbra del bevitore e non mescolarsi mai al cocktail.\r\nShakerare gli altri ingredienti con ghiaccio, quindi versarli delicatamente nel bicchiere.",
      "strInstructionsZH-HANS": null,
      "strInstructionsZH-HANT": null,
      "strDrinkThumb": "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
      "strIngredient1": "Tequila",
      "strIngredient2": "Triple sec",
      "strIngredient3": "Lime juice",
      "strIngredient4": "Salt",
      "strIngredient5": null,
      "strIngredient6": null,
      "strIngredient7": null,
      "strIngredient8": null,
      "strIngredient9": null,
      "strIngredient10": null,
      "strIngredient11": null,
      "strIngredient12": null,
      "strIngredient13": null,
      "strIngredient14": null,
      "strIngredient15": null,
      "strMeasure1": "1 1/2 oz ",
      "strMeasure2": "1/2 oz ",
      "strMeasure3": "1 oz ",
      "strMeasure4": null,
      "strMeasure5": null,
      "strMeasure6": null,
      "strMeasure7": null,
      "strMeasure8": null,
      "strMeasure9": null,
      "strMeasure10": null,
      "strMeasure11": null,
      "strMeasure12": null,
      "strMeasure13": null,
      "strMeasure14": null,
      "strMeasure15": null,
      "strImageSource": "https://commons.wikimedia.org/wiki/File:Klassiche_Margarita.jpg",
      "strImageAttribution": "Cocktailmarler",
      "strCreativeCommonsConfirmed": "Yes",
      "dateModified": "2015-08-18 14:42:59"
    }
  ]
}
 */

class Cocktail {
    var id: String? = null
    var title: String? = null
    var alternateTitle: String? = null
    var tags: List<String>? = null
    var videoURL: String? = null
    var category: String? = null
    var iba: String? = null
    var alcoholic: String? = null
    var glass: String? = null
    var instructions: HashMap<String, String>? = null
    var imageURL: String? = null
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

class SimpleCocktailDeserializer : JsonDeserializer<Cocktail> {
    override fun deserialize(
        body: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Cocktail {
        body as JsonObject

        println(body)

        val id = body.getAsJsonPrimitive("idDrink")?.asString ?: ""
        print(id)
        val title = body.getAsJsonPrimitive("strDrink")?.asString ?: ""
        print(title)
        val imageURL = body.getAsJsonPrimitive("strDrinkThumb")?.asString ?: ""
        print(imageURL)


        val alternateTitle: String? = null
        val tags: List<String>? = null
        val videoURL: String? = null
        val category: String? = null
        val iba: String? = null
        val alcoholic: String? = null
        val glass: String? = null
        val instructions: HashMap<String, String>? = null
        val ingredients: List<String>? = null
        val measures: List<String>? = null
        val imageSource: String? = null
        val imageAttribution: String? = null
        val creativeCommonsConfirmed: String? = null
        val dateModified: String? = null

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


class CocktailDeserializer : JsonDeserializer<Cocktail> {
    override fun deserialize(
        body: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Cocktail {
        body as JsonObject
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
                instructions[value] = json.get(key).asString
            }
        }

        val imageURL = json.get("strDrinkThumb").asString

        val ingredients = ArrayList<String>()
        val measures = ArrayList<String>()

        // For loop to add ingredients and measures to the lists
        for (i in 1..15) {
            if (!json.get("strIngredient$i").isJsonNull) {
                ingredients.add(json.get("strIngredient$i").asString)
            }
            if (!json.get("strMeasure$i").isJsonNull) {
                measures.add(json.get("strMeasure$i").asString)
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