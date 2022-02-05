package cz.lastaapps.osslicenseaccess

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Load data stored the apk file by OSS plugin
 */
object LicenseLoader {

    //both files are stored in build/generated/third_party_licenses/res/raw
    /** Hold notices names */
    const val metadataFile = "third_party_license_metadata"

    /** Hold licenses contents / links */
    const val licensesFile = "third_party_licenses"

    /**
     * Loads all the artifacts with a license stored
     */
    fun loadLicenses(context: Context, fileName: String = metadataFile): List<ArtifactLicense> {
        return loadLicenses(openRawStream(context, fileName))
    }

    /**
     * Loads all the artifacts with a license stored
     */
    fun loadLicenses(stream: InputStream): List<ArtifactLicense> {
        val buffered = BufferedReader(InputStreamReader(stream))
        return buffered.readLines().map { line ->
            // start:len name
            val (numbers, name) = line.split(" ", limit = 2)
            val (start, len) = numbers.split(":", limit = 2)
            ArtifactLicense(name, start.toLong(), len.toInt())
        }
    }

    /**
     * Loads license text for artifact given
     */
    fun loadLicenseText(
        context: Context,
        license: ArtifactLicense,
        fileName: String = licensesFile,
    ): String {
        return loadLicenseText(openRawStream(context, fileName), license)
    }

    /**
     * Loads license text for artifact given
     */
    fun loadLicenseText(stream: InputStream, license: ArtifactLicense): String {
        stream.skip(license.start)
        val buffered = BufferedReader(InputStreamReader(stream))
        val array = CharArray(license.length)
        buffered.read(array)
        return array.concatToString()
    }

    /**
     * Open stream from a file from the raw folder
     */
    private fun openRawStream(context: Context, name: String): InputStream {
        return context.resources.openRawResource(
            context.resources.getIdentifier(name, "raw", context.packageName)
        )
    }
}