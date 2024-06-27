package com.ataglance.walletglance.ui.theme.uielements.accounts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ataglance.walletglance.data.accounts.Account
import com.ataglance.walletglance.data.accounts.color.AccountColors
import com.ataglance.walletglance.data.app.AppTheme
import com.ataglance.walletglance.data.color.LighterDarkerColors
import com.ataglance.walletglance.ui.theme.GlanceTheme
import com.ataglance.walletglance.ui.theme.WalletGlanceTheme
import com.ataglance.walletglance.ui.theme.animation.bounceClickEffect

@Composable
fun SmallAccount(
    account: Account?,
    appTheme: AppTheme?,
    fontSize: TextUnit = 21.sp,
    roundedCornerSize: Dp = 14.dp,
    horizontalPadding: Dp = 9.dp,
    verticalPadding: Dp = 4.dp,
    outerPadding: PaddingValues = PaddingValues(0.dp),
    adjustStyleByActiveStatus: Boolean = false,
    showBalance: Boolean = true,
    onClick: () -> Unit = {}
) {
    val accountAndOnAccountColor = account?.let {
        account.color.getColorAndColorOnByTheme(appTheme)
    } ?: Pair(LighterDarkerColors(), Color.White)
    val transparency by animateFloatAsState(
        targetValue =
            if (adjustStyleByActiveStatus && account != null && !account.isActive) 0.6f else 1f,
        label = "account transparency"
    )
    val accountGradientColor = accountAndOnAccountColor.first
    val onAccountColor = accountAndOnAccountColor.second
    val showBalanceBlock = account != null && !account.withoutBalance && showBalance

    Box(
        modifier = Modifier
            .padding(outerPadding)
            .alpha(transparency)
    ) {
        Box(
            modifier = Modifier
                .bounceClickEffect(.97f, onClick = onClick)
                .clip(RoundedCornerShape(roundedCornerSize + 1.dp))
                .background(GlanceTheme.onSurface.copy(.15f))
                .padding(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(roundedCornerSize))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                accountGradientColor.darker,
                                accountGradientColor.lighter
                            ),
                            start = Offset(0f, 200f),
                            end = Offset(100f, 0f)
                        )
                    )
            ) {
                Text(
                    text = account?.name ?: "???",
                    color = onAccountColor,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(
                            top = verticalPadding - 1.dp, bottom = verticalPadding,
                            start = horizontalPadding,
                            end = horizontalPadding - if (showBalanceBlock) 1.dp else 0.dp
                        )
                )
                if (account != null && showBalanceBlock) {
                    Text(
                        text = account.getFormattedBalanceWithSpaces(),
                        color = onAccountColor,
                        textAlign = TextAlign.Center,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Light,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clip(RoundedCornerShape(roundedCornerSize))
                            .background(
                                if (
                                    appTheme != null &&
                                    account.color.name == AccountColors.Default.name
                                ) {
                                    GlanceTheme.background.copy(.07f)
                                } else {
                                    Color.White.copy(.09f)
                                }
                            )
                            .padding(
                                top = verticalPadding - 1.dp, bottom = verticalPadding,
                                start = horizontalPadding, end = horizontalPadding
                            )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
private fun SmallAccountPreview() {
    BoxWithConstraints {
        WalletGlanceTheme(
            useDeviceTheme = false,
            lastChosenTheme = AppTheme.LightDefault.name,
            boxWithConstraintsScope = this
        ) {
            SmallAccount(Account(balance = 516.41), appTheme = AppTheme.LightDefault)
        }
    }
}