package ph.edu.comteq.daryllaboratoryexercise3

import ads_mobile_sdk.h4
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme
import kotlin.jvm.java

val playfairdisplayregular = FontFamily(
    Font(R.font.playfairdisplayregular, FontWeight.Normal)
)

val optima = FontFamily(
    Font(R.font.optima, FontWeight.Normal)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DarylLaboratoryExercise3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GalleryScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Gattery Logo",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .padding(bottom = 32.dp)
        )

        Box(
            modifier = Modifier
                .width(250.dp)
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.louvre),
                contentDescription = "Louvre Museum",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Experience Art",
                color = Color.White.copy(alpha = 0.8f),
                fontFamily = playfairdisplayregular,
                fontSize = 33.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Align text to the bottom center of the Box
                    .offset(y = (-5).dp) // Adjust this value to control the overlap amount
            )
        }

        Spacer(modifier = Modifier.height(10.dp)) // Space after the overlapped section

        Text(
            text = "We are thrilled to invite you to join us for an extraordinary event that will immerse you in the world of art.",
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            fontFamily = optima,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp) // Added bottom padding for the description
        )

        Button(
            onClick = {
                val intent = Intent(context, ExploreActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD4AF37),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Explore Now",
                fontFamily = optima,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenPreview() {
    DarylLaboratoryExercise3Theme {
        GalleryScreen()
    }
}