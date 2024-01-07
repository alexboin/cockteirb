package fr.aboin.cockteirb.core.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type


class CocktailInfo {
    @SerializedName("strDrink")
    var strDrink: String? = null

    @SerializedName("strDrinkThumb")
    var strDrinkThumb: String? = null

    @SerializedName("idDrink")
    var idDrink: String? = null


    override fun toString(): String {
        return "CocktailInfo(\n\tidDrink = $idDrink,\n\tstrDrink = $strDrink,\n\tstrDrinkThumb = $strDrinkThumb\n)"
    }

    fun toCocktailInfo(): CocktailInfo {
        return CocktailInfo().apply {
            idDrink = this@CocktailInfo.idDrink ?: ""
            strDrink = this@CocktailInfo.strDrink
            strDrinkThumb = this@CocktailInfo.strDrinkThumb
        }
    }
}


class CocktailInfoDeserializer : JsonDeserializer<CocktailInfo> {
    override fun deserialize(
        body: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CocktailInfo {
        body as JsonObject

        val id = body.getAsJsonPrimitive("idDrink")?.asString ?: ""
        val strDrink = body.getAsJsonPrimitive("strDrink")?.asString ?: ""
        val strDrinkThumb = body.getAsJsonPrimitive("strDrinkThumb")?.asString ?: ""

        return CocktailInfo().apply {
            this.idDrink = id
            this.strDrink = strDrink
            this.strDrinkThumb = strDrinkThumb
        }
    }
}
