package EmojiMod.patches.com.megacrit.cardcrawl.sceens.mainMenu.MenuButton;

import EmojiMod.EmojiFontHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;

public class TestMenuEmojiPatch {

    @SpirePatch(clz = MenuButton.class, method = "setLabel")
    public static class LabelFix {
        public static void Postfix(MenuButton __instance) {
            ReflectionHacks.setPrivate(__instance, MenuButton.class, "label", "Test [U+2694] test1 test2 test3 test4 [E] test 5");
        }
    }

//    @SpirePatch(clz = MenuButton.class, method = "render")
//    public static class RenderFix {
////        @SpireInsertPatch(rloc = 43, localvars = "sliderX")
//        @SpireInsertPatch(rloc = 43, localvars = "sliderX")
//        public static void Insert(MenuButton __instance, SpriteBatch sb, float sliderX) {
//            float x = (float) ReflectionHacks.getPrivate(__instance, MenuButton.class, "x");
////            System.out.println(EmojiFontHelper.EMOJI_FONT);
//            String longEmojiTestString = "emoji ❤ \uD83D\uDC49 test \uD83D\uDC49 \uD83D\uDC4D test \uD83D\uDE03";
//            String justEmojiTestString = "❤\uD83D\uDC49";
////            FontHelper.renderFont(sb, EmojiFontHelper.EMOJI_FONT, longEmojiTestString, x + MenuButton.FONT_X + sliderX, __instance.hb.cY + MenuButton.FONT_OFFSET_Y, Settings.CREAM_COLOR);
////            System.out.println("Adjusted menu font");
////            FontHelper.renderSmartText(sb, EmojiFontHelper.EMOJI_FONT, "\uD83D\uDE03", x + MenuButton.FONT_X + sliderX, __instance.hb.cY + MenuButton.FONT_OFFSET_Y, 9999.0F, 1.0F, Settings.GOLD_COLOR);
//        }
//    }
}
