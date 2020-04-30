package EmojiMod.patches.BaseMod;

import EmojiMod.patches.com.megacrit.cardcrawl.ReplaceDynamicVariableExprEditor;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCustomDynamicVariable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.expr.ExprEditor;

public class RenderCustomDynamicVariablePatches {

    @SpirePatch(clz = RenderCustomDynamicVariable.Inner.class, method = "myRenderDynamicVariable")
    public static class ReplaceNumberWithEmojiPatch {
        public static ExprEditor Instrument() {
            return new ReplaceDynamicVariableExprEditor();
        }
    }
}
