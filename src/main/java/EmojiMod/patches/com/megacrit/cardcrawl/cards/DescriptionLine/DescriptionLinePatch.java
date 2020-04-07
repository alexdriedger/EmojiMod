package EmojiMod.patches.com.megacrit.cardcrawl.cards.DescriptionLine;

import EmojiMod.EmojiMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DescriptionLine;

@SpirePatch(clz = DescriptionLine.class, method = SpirePatch.CONSTRUCTOR)
public class DescriptionLinePatch {
    public static void Prefix(DescriptionLine __instance, @ByRef String[] text, float width) {
        text[0] = EmojiMod.emojiSupport.FilterEmojis(text[0]);
    }
}
