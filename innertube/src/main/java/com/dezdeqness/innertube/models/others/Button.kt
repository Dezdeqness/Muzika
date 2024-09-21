package com.dezdeqness.innertube.models.others

import kotlinx.serialization.Serializable

@Serializable
data class Button(
    val buttonRenderer: ButtonRenderer,
) {
    @Serializable
    data class ButtonRenderer(
        val text: Runs,
        val navigationEndpoint: NavigationEndpoint?,
        val command: NavigationEndpoint?,
        val icon: Icon?,
    )
}