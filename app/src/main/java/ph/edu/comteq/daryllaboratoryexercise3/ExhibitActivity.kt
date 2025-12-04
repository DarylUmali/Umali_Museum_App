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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.common.reflect.TypeToken
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme
import java.io.BufferedReader
import kotlin.math.absoluteValue

// --- Data Model ---

data class ArtworkInfo(
    val title: String,
    val years: String,
    val born_at: String,
    val comment: String
)

// --- Activity Class ---

class ExhibitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // You need to ensure you have the required drawable resources (mona_lisa, lady_ermine, litta_madonna, leonardo_da_vinci, arrow, quote)
        // and an 'artworks.json' file in your assets folder.
        enableEdgeToEdge()
        setContent {
            DarylLaboratoryExercise3Theme {
                Exhibit(context = this)
            }
        }
    }
}

// --- Composable Functions ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exhibit(context: Context) {
    // Load data from assets
    val artworks = remember { loadArtworksFromAssets(context) }
    val lazyListState = rememberLazyListState()

    Scaffold { innerPadding ->
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF121212)), // Dark background for contrast
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            itemsIndexed(artworks) { index, artwork ->

                val visibleItemsInfo = lazyListState.layoutInfo.visibleItemsInfo
                val currentItem = visibleItemsInfo.firstOrNull { it.index == index }

                // Calculate the pixel offset of the item from the viewport center
                val offsetPxFromCenter by remember(currentItem) {
                    derivedStateOf {
                        if (currentItem == null) return@derivedStateOf 0f
                        val center = lazyListState.layoutInfo.viewportSize.width / 2f
                        val itemCenter = currentItem.offset + currentItem.size / 2f
                        itemCenter - center
                    }
                }

                // Normalize the offset (0 at center, 1 at the edge)
                val offsetFromCenterNormalized by remember(currentItem) {
                    derivedStateOf {
                        val center = lazyListState.layoutInfo.viewportSize.width / 2f
                        (offsetPxFromCenter.absoluteValue / center).coerceIn(0f, 1f)
                    }
                }

                // --- CURVE/FAN EFFECT PARAMETERS ---
                // Scale down items away from the center
                val scale = 1f - (offsetFromCenterNormalized * 0.30f)
                // Fade out items away from the center
                val alpha = 1f - (offsetFromCenterNormalized * 0.6f)

                // Define the maximum rotation angle for the 3D curve effect
                val maxRotation = 15f
                // Calculate rotation based on offset: negative on the left, positive on the right
                val rotationY = (offsetPxFromCenter / (lazyListState.layoutInfo.viewportSize.width / 2f)) * maxRotation
                val rotationYClamped = rotationY.coerceIn(-maxRotation, maxRotation)

                Box(
                    modifier = Modifier
                        .fillParentMaxWidth(1f)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                            // Apply rotation on Y-axis for the curve/fan effect
                            this.rotationY = rotationYClamped
                        }
                ) {
                    ArtworkCard(artwork)
                }
            }
        }
    }
}

@Composable
fun ArtworkCard(artwork: ArtworkInfo) {
    // Define the Arch Shape for the card container
    val archShape = RoundedCornerShape(
        topStart = 150.dp,
        topEnd = 150.dp,
        bottomStart = 12.dp,
        bottomEnd = 12.dp
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        shape = archShape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)) // Very dark gray container
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            // Map the title to the correct drawable resource
            val imageRes = when (artwork.title) {
                "Mona Lisa" -> R.drawable.mona_lisa
                "Lady Ermine" -> R.drawable.lady_ermine
                "Litta Madonna" -> R.drawable.litta_madonna
                else -> R.drawable.leonardo_da_vinci // Default image
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = artwork.title,
                contentScale = ContentScale.Crop, // Use Crop to maintain aspect ratio and fill area
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Info Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFFFFD700), RoundedCornerShape(8.dp)) // Gold background
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
                            color = Color(0xFF3B3B3B), // Darker gray text
                            fontSize = 14.sp
                        )
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.arrow), // Ensure you have this drawable
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Comment/Quote Section
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.quote), // Ensure you have this drawable
                    contentDescription = "Quote Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = artwork.comment,
                    color = Color(0xFFDADADA), // Light gray text
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// --- Utility Function (Data Loading) ---

fun loadArtworksFromAssets(context: Context): List<ArtworkInfo> {
    // This function requires an 'artworks.json' file in the 'assets' folder of your Android project
    return try {
        val jsonString = context.assets.open("artworks.json").bufferedReader().use(BufferedReader::readText)
        val listType = object : TypeToken<List<ArtworkInfo>>() {}.type
        Gson().fromJson(jsonString, listType) ?: emptyList()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

// --- Preview Function ---

@Preview(showBackground = true)
@Composable
fun ExhibitPreview() {
    val context = LocalContext.current
    DarylLaboratoryExercise3Theme {
        // Note: For a proper preview, you would usually mock the data instead of loading from real assets.
        // This preview will likely fail unless the assets are available during the preview process.
        // It's included for structural completeness.
        Exhibit(context = context)
    }
}