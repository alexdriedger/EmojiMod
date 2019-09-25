package EmojiMod.patches.com.megacrit.cardcrawl.screen.options.OptionsPanel;

import EmojiMod.patches.com.megacrit.cardcrawl.core.Settings.SettingsPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;

import static com.megacrit.cardcrawl.screens.options.OptionsPanel.LOCALIZED_LANGUAGE_LABELS;

@SuppressWarnings("unused")
public class LanguageDropdownPatches {

    @SuppressWarnings("unused")
    @SpirePatch(clz = OptionsPanel.class, method = "languageLabels")
    public static class LanguageLabelsPatch {
        public static SpireReturn<String[]> Prefix(OptionsPanel __instance) {
            UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("EmojiMod:LanguageDropdown");
            String[] emojiLabel = uiStrings.TEXT;
            // From third return statement
            return SpireReturn.Return(new String[] { emojiLabel[0], LOCALIZED_LANGUAGE_LABELS[3], LOCALIZED_LANGUAGE_LABELS[0], LOCALIZED_LANGUAGE_LABELS[1], LOCALIZED_LANGUAGE_LABELS[2], LOCALIZED_LANGUAGE_LABELS[26], LOCALIZED_LANGUAGE_LABELS[25], LOCALIZED_LANGUAGE_LABELS[4], LOCALIZED_LANGUAGE_LABELS[5], LOCALIZED_LANGUAGE_LABELS[23], LOCALIZED_LANGUAGE_LABELS[24], LOCALIZED_LANGUAGE_LABELS[6], LOCALIZED_LANGUAGE_LABELS[7], LOCALIZED_LANGUAGE_LABELS[8], LOCALIZED_LANGUAGE_LABELS[15], LOCALIZED_LANGUAGE_LABELS[9], LOCALIZED_LANGUAGE_LABELS[10], LOCALIZED_LANGUAGE_LABELS[17], LOCALIZED_LANGUAGE_LABELS[21], LOCALIZED_LANGUAGE_LABELS[11], LOCALIZED_LANGUAGE_LABELS[16], LOCALIZED_LANGUAGE_LABELS[13], LOCALIZED_LANGUAGE_LABELS[18], LOCALIZED_LANGUAGE_LABELS[19] });
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = OptionsPanel.class, method = "LanguageOptions")
    public static class LanguageOptionsPatch {
        public static SpireReturn<Settings.GameLanguage[]> Prefix(OptionsPanel __instance) {
            // From third return statement
            return SpireReturn.Return(new Settings.GameLanguage[] {SettingsPatch.EMO, Settings.GameLanguage.ENG, Settings.GameLanguage.PTB, Settings.GameLanguage.ZHS, Settings.GameLanguage.ZHT, Settings.GameLanguage.DUT, Settings.GameLanguage.EPO, Settings.GameLanguage.FRA, Settings.GameLanguage.DEU, Settings.GameLanguage.GRE, Settings.GameLanguage.IND, Settings.GameLanguage.ITA, Settings.GameLanguage.JPN, Settings.GameLanguage.KOR, Settings.GameLanguage.NOR, Settings.GameLanguage.POL, Settings.GameLanguage.RUS, Settings.GameLanguage.SRP, Settings.GameLanguage.SRB, Settings.GameLanguage.SPA, Settings.GameLanguage.THA, Settings.GameLanguage.TUR, Settings.GameLanguage.UKR, Settings.GameLanguage.VIE });
        }
    }
}
