package com.ataglance.walletglance.ui.theme.screencontainers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ataglance.walletglance.R
import com.ataglance.walletglance.data.accounts.Account
import com.ataglance.walletglance.data.app.AppTheme
import com.ataglance.walletglance.data.date.DateRangeEnum
import com.ataglance.walletglance.ui.theme.GlanceTheme
import com.ataglance.walletglance.ui.theme.uielements.containers.DateFilterBar
import com.ataglance.walletglance.ui.theme.uielements.containers.GlassSurface
import com.ataglance.walletglance.ui.theme.uielements.containers.SmallAccountsContainer

@Composable
fun <S> ScreenDataContainer(
    scaffoldAppScreenPadding: PaddingValues,
    accountList: List<Account>,
    appTheme: AppTheme?,
    onAccountClick: (Int) -> Unit,
    currentDateRangeEnum: DateRangeEnum,
    isCustomDateRangeWindowOpened: Boolean,
    onDateRangeChange: (DateRangeEnum) -> Unit,
    onCustomDateRangeButtonClick: () -> Unit,
    animationContentLabel: String,
    animatedContentTargetState: S,
    visibleNoDataMessage: Boolean,
    noDataMessageResource: Int,
    typeFilterBar: @Composable () -> Unit,
    animatedContent: @Composable (S) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.button_bar_to_widget_gap)),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = scaffoldAppScreenPadding.calculateTopPadding() +
                        dimensionResource(R.dimen.button_bar_to_widget_gap),
                bottom = scaffoldAppScreenPadding.calculateBottomPadding() +
                        dimensionResource(R.dimen.screen_vertical_padding)
            )
    ) {
        if (accountList.size > 1) {
            SmallAccountsContainer(
                accountList = accountList,
                appTheme = appTheme,
                onAccountClick = onAccountClick
            )
        }
        DateFilterBar(
            currentDateRangeEnum = currentDateRangeEnum,
            isCustomDateRangeWindowOpened = isCustomDateRangeWindowOpened,
            onDateRangeChange = onDateRangeChange,
            onCustomDateRangeButtonClick = onCustomDateRangeButtonClick
        )
        typeFilterBar()
        Spacer(modifier = Modifier)
        GlassSurface(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = dimensionResource(R.dimen.screen_horizontal_padding)),
            filledWidth = 1f
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(R.dimen.screen_horizontal_padding))
            ) {
                AnimatedContent(
                    targetState = animatedContentTargetState,
                    label = animationContentLabel
                ) {
                    animatedContent(it)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AnimatedVisibility(
                        visible = visibleNoDataMessage,
                        enter = fadeIn(tween(220, delayMillis = 90)) +
                                scaleIn(tween(220, delayMillis = 90), .92f),
                        exit = fadeOut(animationSpec = tween(90)),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = stringResource(noDataMessageResource),
                                color = GlanceTheme.onSurface.copy(.6f),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }
        }
    }
}