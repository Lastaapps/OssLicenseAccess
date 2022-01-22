package cz.lastaapps.osslicenseaccess

/**
 * Represents a license notice generated by OSS stored in res/raw/third_party_licenses_metadata
 * name - name of the notice
 * start - position in res/raw/third_party_licenses
 * length - how many chars should be read from res/raw/third_party_licenses
 * files can be previewed in app/build/generated/third_party_licenses/res/raw/
 */
data class ArtifactLicense(
    val name: String,
    val start: Long,
    val length: Int,
)