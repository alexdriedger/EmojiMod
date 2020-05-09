package EmojiMod.patches.com.megacrit.cardcrawl.screen.SingleCardViewPopup;

import EmojiMod.EmojiMod;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionExprEditor;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionSpacingVariableLocator;
import EmojiMod.util.EmojiMappingUtils;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class SingleCardViewPopupPatches {

    public static final String RENDER_DESCRIPTION_REPLACEMENT =
        "{ " +
            "$7 = i * 2.50F * -font.getCapHeight() + draw_y - this.current_y + -9.0F;" +
            "$_ = $proceed($$);" +
        " }";

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class LineSpacingPatch {
        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT, true, false);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class SpacingVariablePatch {
        @SpireInsertPatch(locator = RenderDescriptionSpacingVariableLocator.class, localvars = { "spacing "})
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef float[] spacing) {
            spacing[0] = spacing[0] / 1.53F * 2.50F;
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class DescriptionWidthPatch {
        @SpireInsertPatch(rloc = 20, localvars = {"start_x", "i", "font"})
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef float[] start_x, int i, BitmapFont font) {
            AbstractCard c = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            String[] tokens = c.description.get(i).getCachedTokenizedText();
            float descWidth = EmojiMappingUtils.getStringWidthWithEmoji(c, tokens, font);
            float current_x = (float) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "current_x");
            float drawScale = (float) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "drawScale");
            start_x[0] = current_x - descWidth * drawScale / 4.0F;
        }
    }

}
