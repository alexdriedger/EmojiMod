package EmojiMod.patches.com.megacrit.cardcrawl.screen.SingleCardViewPopup;

import EmojiMod.EmojiMod;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionExprEditor;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class SingleCardViewPopupPatches {

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class LineSpacingPatch {
        public static final String RENDER_DESCRIPTION_REPLACEMENT =
            "{ " +
                "$7 = i * 2.50F * -font.getCapHeight() + draw_y - this.current_y + -6.0F;" +
                "$_ = $proceed($$);" +
            " }";

        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT);
        }
    }
}
