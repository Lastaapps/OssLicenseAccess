package cz.lastaapps.osslicenseaccess.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cz.lastaapps.osslicenseaccess.ArtifactLicense
import cz.lastaapps.osslicenseaccess.LicenseLoader
import cz.lastaapps.osslicenseaccess.sample.ui.theme.OssLicenseAccessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OssLicenseAccessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        var selectedArtifact by remember {
                            mutableStateOf<ArtifactLicense?>(null)
                        }

                        if (selectedArtifact == null) {
                            ArtifactList(
                                onArtifactSelected = { selectedArtifact = it },
                                modifier = Modifier.fillMaxSize(),
                            )
                        } else {
                            BackHandler(true) {
                                selectedArtifact = null
                            }
                            ArtifactLicense(
                                license = selectedArtifact!!,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ArtifactList(
    onArtifactSelected: (ArtifactLicense) -> Unit,
    modifier: Modifier = Modifier,
) {
    //Blocks the Main thread, use ViewModel or something
    val context = LocalContext.current
    val data = remember(context) {
        LicenseLoader.loadLicenses(context)
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(data) { license ->
            Surface(
                onClick = { onArtifactSelected(license) },
                color = MaterialTheme.colors.primarySurface,
                modifier = modifier,
            ) {
                Box(
                    Modifier
                        .defaultMinSize(minHeight = 48.dp)
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(license.name, style = MaterialTheme.typography.h6)
                }
            }
        }
    }
}

@Composable
private fun ArtifactLicense(
    license: ArtifactLicense,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val text = remember(context, license) {
        LicenseLoader.loadLicenseText(context, license)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Surface(
            color = MaterialTheme.colors.primarySurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = license.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp),
            )
        }
        Surface(
            color = MaterialTheme.colors.primarySurface,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            LinkifyText(
                text,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

