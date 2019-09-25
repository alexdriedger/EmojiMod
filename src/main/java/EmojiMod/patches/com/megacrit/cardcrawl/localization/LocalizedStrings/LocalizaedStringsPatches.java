package EmojiMod.patches.com.megacrit.cardcrawl.localization.LocalizedStrings;

import EmojiMod.EmojiMod;
import EmojiMod.patches.com.megacrit.cardcrawl.core.Settings.SettingsPatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

import java.io.File;

@SuppressWarnings("unused")
public class LocalizaedStringsPatches {

    @SpirePatch(clz = LocalizedStrings.class, method = SpirePatch.CONSTRUCTOR)
    public static class LoadEmojiLocFiles {
        @SpireInsertPatch(rloc = 86, localvars = "langPackDir")
        public static void Insert(LocalizedStrings __instance, @ByRef(type="java.lang.String") String[] langPackDir) {
            if (Settings.language == SettingsPatch.EMO) {
                langPackDir[0] = EmojiMod.getModID() + "Resources/localization" + File.separator + "emo";
            }
        }
    }
}
