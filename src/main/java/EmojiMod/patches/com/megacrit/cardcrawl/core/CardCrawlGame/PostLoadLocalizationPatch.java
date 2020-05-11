package EmojiMod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import EmojiMod.EmojiMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import javassist.CtBehavior;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="create")
public class PostLoadLocalizationPatch {
    //get hecked I hardcoded it and didn't make a subscriber
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void PostLocalization(CardCrawlGame __instance)
    {
        EmojiMod.PostLoadLocalizationStrings();
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            //Matcher finalMatcher = new Matcher.ConstructorCallMatcher(SingleCardViewPopup.class);
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "IS_FULLSCREEN");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}