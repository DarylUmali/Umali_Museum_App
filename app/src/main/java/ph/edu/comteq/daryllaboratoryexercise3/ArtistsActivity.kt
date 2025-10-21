package ph.edu.comteq.daryllaboratoryexercise3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme

class ArtistsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                DarylLaboratoryExercise3Theme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Artist(
                            modifier = Modifier.padding(innerPadding)

                        )
                    }
                }
            }
        }
    }
}

val Gold = Color(0xFFFFD700)
data class Artwork(val id: Int, val description: String)


data class ArtistData(
    val id: Int,
    val name: String,
    val years: String,
    val profileImage: Int,
    val artworks: List<Artwork> 
)


val sampleArtists = listOf(
    ArtistData(
        id = 1,
        name = "Leonardo da Vinci",
        years = "1452 - 1519",
        profileImage = R.drawable.leonardo_da_vinci,
        artworks = listOf(
            Artwork(R.drawable.mona_lisa, "Mona Lisa"),
            Artwork(R.drawable.lady_ermine, "Lady with an Ermine"),
            Artwork(R.drawable.litta_madonna, "litta Madonna")
        )
    ),
    ArtistData(
        id = 2,
        name = "Michelangelo",
        years = "1475 - 1564",
        profileImage = R.drawable.michelangelo,
        artworks = listOf(
            Artwork(R.drawable.david, "David"),
            Artwork(R.drawable.delphic_sibyl, "Delphic sibyl"),
            Artwork(R.drawable.torment_of_saint_anthony, "Torment of saint anthony")
        )
    ),
    ArtistData(
        id = 2,
        name = "Gustav klimt",
        years = "1862 - 1918",
        profileImage = R.drawable.gustav_klimt,
        artworks = listOf(
            Artwork(R.drawable.adele_bloch_bauer, "Adele bloch bauer"),
            Artwork(R.drawable.lady_with_fan, "Lady with fan"),
            Artwork(R.drawable.the_kiss, "The kiss")
        )
    ),
)
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Artist(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Artists", "Artworks")
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Explore the Art of",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 31.sp
            )
        )


        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Renaissance",
            style = androidx.compose.ui.text.TextStyle(
                color = Gold,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Type to search") },
leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.scan),
                    contentDescription = "scan Icon",
                    modifier = Modifier.size(24.dp)
                )
            },

            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Gold,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    height = 3.dp,
                    color = Gold
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTabIndex == index
                Tab(
                    selected = selected,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            title,
                            color = if (selected) Gold else Color.Gray,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(sampleArtists) { artist ->
                ArtistCard(artist = artist)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
@Composable
fun ArtistCard(artist: ArtistData) {
    val context = LocalContext.current


    val isLeonardo = artist.name == "Leonardo da Vinci"
    val isMichelangelo = artist.name == "Michelangelo"


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                if (isLeonardo) {
                    val intent = Intent(context, ExhibitActivity::class.java)
                    intent.putExtra("artistName", artist.name)
                    context.startActivity(intent)
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isMichelangelo) {
                ArtistTextInfo(
                    artist = artist,
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false),
                    horizontalAlignment = Alignment.End
                )
                Spacer(modifier = Modifier.width(16.dp))
                ArtistProfileImage(artist = artist)
            } else {
                ArtistProfileImage(artist = artist)
                Spacer(modifier = Modifier.width(16.dp))
                ArtistTextInfo(
                    artist = artist,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(artist.artworks) { artwork ->
                Image(
                    painter = painterResource(id = artwork.id),
                    contentDescription = artwork.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Composable
private fun ArtistProfileImage(artist: ArtistData) {
    Image(
        painter = painterResource(id = artist.profileImage),
        contentDescription = "${artist.name} Profile",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    )
}


@Composable
private fun ArtistTextInfo(
    artist: ArtistData,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = artist.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = artist.years,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ArtistPreview() {
    DarylLaboratoryExercise3Theme {
        Artist()
    }
}