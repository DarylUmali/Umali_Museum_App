package ph.edu.comteq.daryllaboratoryexercise3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme
// Make sure to add R.font.playfairdisplayregular and R.font.optima to your res/font directory
// and R.drawable.logo, R.drawable.louvre to your res/drawable directory.

// Assuming R.font.playfairdisplayregular and R.font.optima exist in your project
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

    // Gold color defined once
    val gold = Color(0xFFD4AF37)

    // Total duration for all sequenced animations
    val animationDuration = 10000 // 10 seconds (Total duration)
    val totalCoverHeight = 400.dp

    // 💡 UPDATED: Set a large distance to ensure the button starts off-screen.
    val buttonSlideDistance: Dp = 400.dp

    // Define the Arch Shape for the image and border
    // Large top radius creates the arch effect, zero bottom radius keeps the bottom flat
    val archShape = RoundedCornerShape(
        topStart = 150.dp,
        topEnd = 150.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    // 1. Define state and animate the main progress value (0f to 1f)
    val coverProgress = remember { mutableStateOf(0f) }
    // Starts the animation as soon as the composable is created
    LaunchedEffect(Unit) { coverProgress.value = 1f }
    val animatedProgress: Float by animateFloatAsState(
        targetValue = coverProgress.value,
        animationSpec = tween(durationMillis = animationDuration),
        label = "OverallProgress"
    )

    // --- Animation Timing Parameters ---
    val coverDurationFraction = 0.20f // 0% to 20%
    val textDurationFraction = 0.70f  // 20% to 90%
    val buttonDurationFraction = 0.10f // 90% to 100%

    // 2. Calculate Cover Progress (0% to 20%)
    val coverAnimationProgress = (animatedProgress / coverDurationFraction).coerceIn(0f, 1f)
    // Cover starts at totalCoverHeight (fully covering) and shrinks to 0.dp
    val animatedCoverHeight = totalCoverHeight * (1f - coverAnimationProgress)

    // 3. Calculate Text Progress (Starts at 20%, ends at 90%)
    val textStartProgress = coverDurationFraction
    val textEndProgress = textStartProgress + textDurationFraction
    val textAnimationProgress = ((animatedProgress - textStartProgress) / textDurationFraction).coerceIn(0f, 1f)

    // 4. Calculate Button Slide Progress (Starts at 90%, ends at 100%)
    val buttonStartProgress = textEndProgress
    val buttonAnimationProgress = ((animatedProgress - buttonStartProgress) / buttonDurationFraction).coerceIn(0f, 1f)
    // Offset starts at buttonSlideDistance (off-screen) and ends at 0.dp (final position)
    val animatedButtonOffset = buttonSlideDistance * (1f - buttonAnimationProgress)

    // Define Text Content
    val titleText = "Experience Art"
    val bodyText = "We are thrilled to invite you to join us for an extraordinary event that will immerse us in the world of art."

    // --- Typewriter Animation Calculations ---
    val titleCharCount = (titleText.length * textAnimationProgress).toInt().coerceIn(0, titleText.length)
    val animatedTitleText = titleText.substring(0, titleCharCount)

    val bodyStartDelay = 0.30f
    val bodyTextProgress = ((textAnimationProgress - bodyStartDelay) / (1f - bodyStartDelay)).coerceIn(0f, 1f)
    val bodyCharCount = (bodyText.length * bodyTextProgress).toInt().coerceIn(0, bodyText.length)
    val animatedBodyText = bodyText.substring(0, bodyCharCount)
    // ----------------------------------------


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        // Static Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Gattery Logo",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .padding(bottom = 32.dp)
        )

        // START OF IMAGE AND COVER BLOCK
        Box(
            modifier = Modifier
                .width(250.dp)
                .height(totalCoverHeight)
        ) {
            // Base Image with Arch Shape and Border
            Image(
                painter = painterResource(id = R.drawable.louvre),
                contentDescription = "Louvre Museum",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    // KEY CHANGE 1: Apply the custom arch shape clip
                    .clip(archShape)
                    // KEY CHANGE 2: Apply the border using the custom arch shape
                    .border(2.dp, gold, archShape)
            )

            // Animated Cover Layer (Shrinks Upward)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(animatedCoverHeight)
                    .align(Alignment.BottomCenter)
                    // Ensure the cover also adheres to the arch shape
                    .clip(archShape)
                    .background(Color.Black)
            )

            // Animated Title Text
            Text(
                text = animatedTitleText,
                color = Color.White.copy(alpha = 0.8f),
                fontFamily = playfairdisplayregular,
                fontSize = 33.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-5).dp)
            )
        }
        // END OF IMAGE AND COVER BLOCK

        Spacer(modifier = Modifier.height(10.dp))

        // Animated Body Text
        Text(
            text = animatedBodyText,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            fontFamily = optima,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Explore Button with Slide Up Animation
        Button(
            onClick = {
                val intent = Intent(context, ExploreActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = gold,
                contentColor = Color.Black
            ),
            modifier = Modifier
                // The large initial offset (400.dp) keeps it off-screen until its animation phase.
                .offset(y = animatedButtonOffset)
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