@file:OptIn(ExperimentalMaterial3Api::class)

package ph.edu.comteq.daryllaboratoryexercise3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.daryllaboratoryexercise3.ui.theme.DarylLaboratoryExercise3Theme
import java.time.Duration
import java.time.Instant


class TicketingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DarylLaboratoryExercise3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Ticketing(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Ticketing(modifier: Modifier = Modifier) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now()
            .plus(Duration.ofDays(2)).toEpochMilli(),
        selectableDates = object: SelectableDates{
            override fun isSelectableDate(utcTimeMillis: Long): Boolean{
                return utcTimeMillis >= Instant.now()
                .plus(Duration.ofDays(1)).toEpochMilli()
            }
        }
    )
    val goldColor = Color(0xFFd29f1b)
    val lightTextColor = Color.White.copy(alpha = 0.9f)

    val datePickerColors = DatePickerDefaults.colors(
        containerColor = Color.Black,
        titleContentColor = goldColor,
        headlineContentColor = goldColor,
        subheadContentColor = lightTextColor,
        weekdayContentColor = lightTextColor,
        dayContentColor = lightTextColor,
        disabledDayContentColor = Color.Gray.copy(alpha = 0.5f),


        selectedDayContainerColor = goldColor,
        selectedDayContentColor = Color.Black,

        todayDateBorderColor = goldColor,
        todayContentColor = goldColor
    )
    var generalAdmissionCount by remember { mutableStateOf(0) }
    var freeTicketCount by remember { mutableStateOf(0) }
    val generalAdmissionPrice = 500
    val totalAmount = generalAdmissionCount * generalAdmissionPrice

    Column(
        modifier = Modifier.background(Color.Black).fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(230.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lab4),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                )
                Text(
                    text = "Official\nTicketing Service",
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )
                )
            }
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                DatePicker(
                    modifier = Modifier.padding(10.dp),
                    state = datePickerState,
                    title = null,
                    showModeToggle = false,
                    colors = datePickerColors,
                    headline = {
                        Text(
                            text = "1. Date to Visit"
                            )
                      }
                )
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                        Text(
                            text = "2. Number of Tickets",
                            color = goldColor,
                            fontSize = 30.sp,
                        )
                        Spacer(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 7.dp)
                                .height(1.dp)
                                .background(Color(0xFF999999))

                        )


                    // General Admission Counter
                    TicketCounter(
                        title = "General Admission",
                        subtitle = "P$generalAdmissionPrice",
                        count = generalAdmissionCount,
                        onDecrement = { if (generalAdmissionCount > 0) generalAdmissionCount-- },
                        onIncrement = { generalAdmissionCount++ }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Free Tickets Counter
                    TicketCounter(
                        title = "",
                        subtitle = "Under 18s, Under 26s\nresidents of the EEA,\nMuseum members,\nProfessionals",
                        count = freeTicketCount,
                        onDecrement = { if (freeTicketCount > 0) freeTicketCount-- },
                        onIncrement = { freeTicketCount++ }
                    )
                    Text(
                        text = "FREE",
                        style = TextStyle(
                            fontSize = 25.sp,
                            color = Color(color=0xFFd29f1b)
                        )
                    )
                }
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth().height(80.dp)
                .background(Color(0xFFd29f1b))
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total: P$totalAmount",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color.Black,
                )
            )
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {/*TODO*/},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Checkout",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = playfairdisplayregular,
                        color = Color(color=0xFFd29f1b)
                    )
                )
            }
        }
    }
}
@Composable
fun TicketCounter(
    title: String,
    subtitle: String,
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    val lightTextColor = Color.White.copy(alpha = 0.9f)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = lightTextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = lightTextColor.copy(alpha = 0.7f),
                fontSize = 14.sp,
                lineHeight = 16.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CounterButton(text = "-", onClick = onDecrement)
            Text(
                text = "$count",
                color = lightTextColor,
                fontSize = 22.sp
            )
            CounterButton(text = "+", onClick = onIncrement)
        }
    }
}

@Composable
fun CounterButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color(0xFFd29f1b)),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFd29f1b))
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}
@Preview(showBackground = true)
@Composable
fun TicketingPreview() {
    DarylLaboratoryExercise3Theme {
        Ticketing()
    }
}