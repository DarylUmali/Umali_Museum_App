package ph.edu.comteq.daryllaboratoryexercise3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme

class ExploreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DarylLaboratoryExercise3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ExploreScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
            .statusBarsPadding() // Handle status bar insets

    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Explore",
            color = Color.White.copy(alpha = 0.8f),
            fontFamily = playfairdisplayregular,
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(

            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD4AF37))

        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Upcoming Event",
                color = Color.White.copy(alpha = 0.8f),
                fontFamily = optima,
                fontSize = 30.sp

            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Tickets",
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = optima,
                    fontSize = 18.sp,
                    modifier = Modifier // Make it easy to tap
                        .clickable { // <<< TODO: NAVIGATION FOR THIS NEW TEXT
                            val intent = Intent(context, TicketingActivity::class.java)
                            context.startActivity(intent)
                        }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(id = R.drawable.chevron_right), // Replace with your arrow icon
                    contentDescription = "Forward Arrow",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.renaissance), // Replace with your image
                    contentDescription = "Renaissance Exhibition",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "10",
                            color = Color.White,
                            fontFamily = optima,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 25.dp)
                        )
                        Text(
                            text = "OCT",
                            color = Color.White.copy(alpha = 0.8f),
                            fontFamily = optima,
                            fontSize = 16.sp
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f) // Take remaining space
                    ) {
                        Text(
                            text = "Renaissance Exhibition",
                            color = Color.White,
                            fontFamily = playfairdisplayregular,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)

                        )
                        Text(
                            text = "9:00 AM - 6:00 PM",
                            color = Color.White.copy(alpha = 0.7f),
                            fontFamily = optima,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                        )
                        Text(
                            text = "Indulge in the rich tapestry of Renaissance art",
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = Color(0xFFD4AF37),
                            fontFamily = optima,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 16.dp,bottom = 8.dp)
                        )
                        Text(
                            text = "+33 (0)1 23 45 67 89",
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = Color.White.copy(alpha = 0.7f),
                            fontFamily = optima,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 16.dp)

                        )
                    }
                }

                Button(
                    onClick = { /* Handle visit gallery action */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4AF37),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp), // Rounded only at the bottom
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)

                ) {
                    Text(
                        text = "Visit Gallery",
                        fontFamily = optima,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold

                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    DarylLaboratoryExercise3Theme {
        ExploreScreen()
    }
}