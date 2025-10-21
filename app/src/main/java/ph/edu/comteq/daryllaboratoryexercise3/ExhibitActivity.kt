package ph.edu.comteq.daryllaboratoryexercise3

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme
import java.io.BufferedReader

data class ArtworkInfo(
    val title: String,
    val years: String,
    val born_at: String,
    val comment: String
)
class ExhibitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DarylLaboratoryExercise3Theme {
                    Exhibit(context = this)
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exhibit(context: Context) {
    val artworks = remember { loadArtworksFromAssets(context) }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF121212)),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(artworks) { artwork ->
                ArtworkCard(artwork)
            }
        }
    }
}

@Composable
fun ArtworkCard(artwork: ArtworkInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            val imageRes = when (artwork.title) {
                "Mona Lisa" -> R.drawable.mona_lisa
                "Lady Ermine" -> R.drawable.lady_ermine
                "Litta Madonna" -> R.drawable.litta_madonna
                else -> R.drawable.leonardo_da_vinci
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = artwork.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 150.dp,
                            topEnd = 150.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFD700), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = artwork.title,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${artwork.years}, ${artwork.born_at}",
                            color = Color(0xFF3B3B3B),
                            fontSize = 14.sp
                        )
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.quote),
                    contentDescription = "Quote Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = artwork.comment,
                    color = Color(0xFFDADADA),
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}


fun loadArtworksFromAssets(context: Context): List<ArtworkInfo> {
    val jsonString = context.assets.open("artworks.json").bufferedReader().use(BufferedReader::readText)
    val listType = object : TypeToken<List<ArtworkInfo>>() {}.type
    return Gson().fromJson(jsonString, listType)
}

@Preview(showBackground = true)
@Composable
fun ExhibitPreview() {
    val context = LocalContext.current
    DarylLaboratoryExercise3Theme {
        Exhibit(context = context)
    }
}