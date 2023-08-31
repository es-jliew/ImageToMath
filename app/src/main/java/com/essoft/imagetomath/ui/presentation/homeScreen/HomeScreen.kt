package com.essoft.imagetomath.ui.presentation.homeScreen

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.essoft.imagetomath.R
import com.google.mlkit.vision.common.InputImage
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val context = LocalContext.current
    val extractedText by viewModel.extractedText.collectAsState()
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    //Photo picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        photoUri = uri
        val inputImage = photoUri?.let { InputImage.fromFilePath(context, it) }
        if (inputImage != null) {
            viewModel.getTextFromLocalImage(inputImage)
        }
    }

    //Camera
    val cameraLauncherIntent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),

    ) { imageBitMap ->
        if (imageBitMap != null) {
            viewModel.getTextFromCapturedImage(imageBitMap)
        }
    }

    Scaffold(modifier = Modifier ) { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                Column(modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround) {
                    Card(modifier = Modifier.fillMaxHeight(0.7f),
                        ) {
                        if (photoUri != null) {
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = photoUri)
                                    .build(),
                                contentDescription = "Math Image",
                                contentScale = ContentScale.FillWidth
                            )
                        } else {
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = R.drawable.ic_launcher_foreground)
                                    .build(),
                                contentDescription = "Math Image",
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier.height(8.dp),
                        color = Color.Transparent
                    )

                    Text("Math Expression:")

                    Divider(
                        modifier = Modifier.height(8.dp),
                        color = Color.Transparent
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        readOnly = true,
                        value = "",
                        onValueChange = {  },
                        label = { Text(extractedText.mathExpression.toString()) },
                    )

                    Divider(
                        modifier = Modifier.height(8.dp),
                        color = Color.Transparent
                    )

                    Text("Result:")

                    Divider(
                        modifier = Modifier.height(8.dp),
                        color = Color.Transparent
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        readOnly = true,
                        value = "",
                        onValueChange = { },
                        label = { Text(text = extractedText.mathExpressionResult?.toString() ?: "") },
                    )

                    Divider(
                        modifier = Modifier.height(8.dp),
                        color = Color.Transparent
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { galleryLauncher.launch(PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                    ) {
                        Row() {
                            Icon(
                                Icons.Filled.Upload,
                                contentDescription = "Upload")

                            Text(text = "Select Image")
                        }
                    }
                }
            }
        }
}