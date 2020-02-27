
import com.google.gson.annotations.SerializedName

data class ResulSearch(
    @SerializedName("search_api")
    val searchApi: SearchApi
)

data class SearchApi(
    val result: List<Result>
)