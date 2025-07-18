package com.dezdeqness.muzika.navigation

import android.content.Context
import com.dezdeqness.auth.navigation.AuthNavigation
import com.dezdeqness.muzika.presentation.MainActivity
import org.koin.core.annotation.Single

@Single
class ApplicationNavigation : AuthNavigation {

    override fun navigateToMainScreen(context: Context) {
        context.startActivity(MainActivity.newIntent(context))
    }
}
