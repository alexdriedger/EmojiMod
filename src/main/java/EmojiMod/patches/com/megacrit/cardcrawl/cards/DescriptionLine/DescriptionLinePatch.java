package EmojiMod.patches.com.megacrit.cardcrawl.cards.DescriptionLine;

import EmojiMod.EmojiMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DescriptionLine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DescriptionLinePatch {

    @SpirePatch(clz = DescriptionLine.class, method = SpirePatch.CONSTRUCTOR)
    public static class FilterEmojiRawDescriptionPatch {
        public static void Prefix(DescriptionLine __instance, @ByRef String[] text, float width) {
            text[0] = EmojiMod.emojiSupport.FilterEmojis(text[0]);
        }
    }
    @SpirePatch(clz = DescriptionLine.class, method = "tokenize")
    public static class DoubleSpacePatch {
        public static String[] Postfix(String desc) {
            Pattern p = Pattern.compile("(\\s+)");
            Matcher m = p.matcher(desc);
            String[] tokenized = desc.split("(\\s+)");
            for (int i = 0; m.find(); i++) {
                tokenized[i] = tokenized[i] + m.group(1);
            }
            return tokenized;
        }
    }
}
