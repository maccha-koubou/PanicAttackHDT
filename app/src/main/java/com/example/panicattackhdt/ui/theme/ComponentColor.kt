package com.example.panicattackhdt.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.graphics.Color

val IconButtonColors = IconButtonColors(Color.Transparent, Gray, White, White)
val AddButtonColors = IconButtonColors(LightPurple, Purple, LightPurple, White)
val largeMainButtonColors = ButtonColors(DarkPurple, White, Gray, White)
val emergencyButtonColors = ButtonColors(Color(0xFFE40000), White, Gray, White)
val largeSecondaryButtonColors = ButtonColors(Color.Transparent, Gray, Color.Transparent, BackgroundGray)
val largeInputButtonColors = ButtonColors(LightPurple, DarkPurple, White, Gray)

val CheckBoxUncheckedColors = IconButtonColors(White, Gray, White, BackgroundGray)
val CheckBoxUncheckedMedicationColors = IconButtonColors(White, Color.Transparent, White, BackgroundGray)
val CheckBoxCheckedColors = IconButtonColors(Gray, White, BackgroundGray, White)
val CheckBoxDuringCycleColors = IconButtonColors(DarkPurple, White, LightPurple, White)

val cardColors = CardColors(White, Black, Gray, Gray)

val tabActiveIndicatorColors = CardColors(DarkPurple, Color.Transparent, Color.Transparent, Color.Transparent)
val tabInactiveIndicatorColors = CardColors(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent)

val navigationBarColors = NavigationBarItemColors(DarkPurple, DarkPurple, Color.Transparent, Gray, Gray, Gray, Gray)
@OptIn(ExperimentalMaterial3Api::class)
val titleBarColors = TopAppBarColors(White, White, Gray, Black, DarkPurple)

val menuItemColors = ListItemColors(White, Black, DarkPurple, Gray, Gray, Gray, Gray, Gray, Gray)

// The colors of the handle and selected text's background in textboxes
val textSelectionColors = TextSelectionColors(Purple, LightPurple)
// The colors of the text in textboxes
val textFieldColors = TextFieldColors(DarkPurple, Black, Gray, Pink, White, LightPurple, White, LightPink, Gray, Gray, textSelectionColors, DarkPurple, Color.Transparent, Gray, Pink, DarkPurple, Gray, Gray, Gray, DarkPurple, Gray, Gray, Gray, DarkPurple, DarkPurple, Gray, Gray, Gray, Gray, Gray, Gray, Gray, Gray, Gray, Pink, Gray, Gray, Gray, Gray, Gray, Gray, Gray, Gray)
@OptIn(ExperimentalMaterial3Api::class)
val datePickerColors = DatePickerColors(White, Gray, Black, Black, Black, DarkPurple, Black, Gray, DarkPurple, White, White, DarkPurple, Gray, Black, Gray, White, White, DarkPurple, Gray, DarkPurple, DarkPurple, LightPurple, DarkPurple, LightPurple, textFieldColors)