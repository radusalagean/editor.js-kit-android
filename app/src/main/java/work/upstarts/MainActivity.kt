package work.upstarts

import android.content.res.AssetManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import work.upstarts.editorjskit.EJDeserializer
import work.upstarts.editorjskit.models.EJBlock
import work.upstarts.editorjskit.models.HeadingLevel
import work.upstarts.editorjskit.ui.EditorJsAdapter
import work.upstarts.editorjskit.ui.theme.EJStyle

const val DATA_JSON_PATH = "dummy_data.json"

data class EJResponse(val blocks: List<EJBlock>)

class MainActivity : AppCompatActivity() {

    private val rvAdapter: EditorJsAdapter by lazy {
        EditorJsAdapter(
            EJStyle.builderWithDefaults(applicationContext)
                .linkColor(ContextCompat.getColor(this, R.color.link_color))
                .linkColor(ContextCompat.getColor(this, R.color.link_color))
                .headingMargin(
                    STANDARD_MARGIN,
                    ZERO_MARGIN,
                    ZERO_MARGIN,
                    ZERO_MARGIN,
                    HeadingLevel.h1
                )
                .headingMargin(
                    STANDARD_MARGIN,
                    ZERO_MARGIN,
                    ZERO_MARGIN,
                    ZERO_MARGIN,
                    HeadingLevel.h2
                )
                .linkColor(ContextCompat.getColor(this, R.color.link_color))
                .listTextItemTextSize(18f)
                .dividerBreakHeight(DIVIDER_HEIGHT)
                .dividerBreakHeight(DIVIDER_HEIGHT)
                .build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        val blocksType = object : TypeToken<MutableList<EJBlock>>() {}.type

        val gson = GsonBuilder()
            .registerTypeAdapter(blocksType, EJDeserializer())
            .create()

        val dummyData = readFileFromAssets(DATA_JSON_PATH, assets)
        val ejResponse = gson.fromJson(dummyData, EJResponse::class.java)

        rvAdapter.items = ejResponse.blocks

    }
}

fun readFileFromAssets(fname: String, assetsManager: AssetManager) =
    assetsManager.open(fname).readBytes().toString(Charsets.UTF_8)

const val ZERO_MARGIN = 0
const val STANDARD_MARGIN = 16
const val DIVIDER_HEIGHT = 3
const val BULLET_SIZE = 16





