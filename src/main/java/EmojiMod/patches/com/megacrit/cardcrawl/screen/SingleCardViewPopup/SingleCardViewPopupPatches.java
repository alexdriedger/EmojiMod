package EmojiMod.patches.com.megacrit.cardcrawl.screen.SingleCardViewPopup;

import EmojiMod.EmojiMod;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionExprEditor;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionSpacingVariableLocator;
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
            "$7 = i * 2.50F * -font.getCapHeight() + draw_y - this.current_y + -12.0F;" +
            "$_ = $proceed($$);" +
        " }";

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class LineSpacingPatch {
        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT, true);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDynamicVariable")
    public static class LineSpacingDynamicVarPatch {
        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT, false);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class SpacingVariablePatch {
        @SpireInsertPatch(locator = RenderDescriptionSpacingVariableLocator.class, localvars = { "spacing "})
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef float[] spacing) {
            spacing[0] = spacing[0] / 1.53F * 2.50F;
        }
    }

}
