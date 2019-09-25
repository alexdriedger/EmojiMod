package EmojiMod.patches.com.megacrit.cardcrawl.screen.options.OptionsPanel;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;

import static com.megacrit.cardcrawl.screens.options.OptionsPanel.LOCALIZED_LANGUAGE_LABELS;

@SuppressWarnings("unused")
@SpirePatch(clz = OptionsPanel.class, method = "languageLabels")
public class LanguageDropdownPatches {

    public static SpireReturn<String[]> Prefix(OptionsPanel __instance) {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("EmojiMod:LanguageDropdown");
        String[] emojiLabel = uiStrings.TEXT;
        return SpireReturn.Return(new String[] { emojiLabel[0], LOCALIZED_LANGUAGE_LABELS[3], LOCALIZED_LANGUAGE_LABELS[0], LOCALIZED_LANGUAGE_LABELS[1], LOCALIZED_LANGUAGE_LABELS[2], LOCALIZED_LANGUAGE_LABELS[26], LOCALIZED_LANGUAGE_LABELS[25], LOCALIZED_LANGUAGE_LABELS[4], LOCALIZED_LANGUAGE_LABELS[5], LOCALIZED_LANGUAGE_LABELS[23], LOCALIZED_LANGUAGE_LABELS[24], LOCALIZED_LANGUAGE_LABELS[6], LOCALIZED_LANGUAGE_LABELS[7], LOCALIZED_LANGUAGE_LABELS[8], LOCALIZED_LANGUAGE_LABELS[15], LOCALIZED_LANGUAGE_LABELS[9], LOCALIZED_LANGUAGE_LABELS[10], LOCALIZED_LANGUAGE_LABELS[17], LOCALIZED_LANGUAGE_LABELS[21], LOCALIZED_LANGUAGE_LABELS[11], LOCALIZED_LANGUAGE_LABELS[16], LOCALIZED_LANGUAGE_LABELS[13], LOCALIZED_LANGUAGE_LABELS[18], LOCALIZED_LANGUAGE_LABELS[19] });
    }
}
