package com.dezdeqness.muzika.navigation

import android.content.Context
import com.dezdeqness.auth.navigation.AuthNavigation
import com.dezdeqness.muzika.presentation.MainActivity

class ApplicationNavigation : AuthNavigation {

    override fun navigateToMainScreen(context: Context) {
        context.startActivity(MainActivity.newIntent(context))
    }
}
