package EmojiMod.patches.com.megacrit.cardcrawl.sceens.mainMenu.MenuButton;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;

public class TestMenuEmojiPatch {

    @SpirePatch(clz = MenuButton.class, method = "setLabel")
    public static class LabelFix {
        public static void Postfix(MenuButton __instance) {
            // New emoji support method
//            String s = EmojiMod.emojiSupport.FilterEmojis("Test Scaled text 0.75x \ud83d\ude0e \ud83d\ude00 \ud83d\ude00. test4 [E] test 5");
//            ReflectionHacks.setPrivate(__instance, MenuButton.class, "label", s);
        }
    }
}
