package EmojiMod.patches.com.megacrit.cardcrawl;

import EmojiMod.EmojiMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class RenderDescriptionExprEditor extends ExprEditor {

    private boolean firstPatch;
    private final String replacementString;
    private final boolean skip;
    private final boolean patchAll;

    public RenderDescriptionExprEditor(String replacementString, boolean skipFirst, boolean patchAll) {
        this.firstPatch = true;
        this.replacementString = replacementString;
        if (skipFirst && patchAll) {
            throw new RuntimeException("Error: Trying to render descriptions of cards incorrectly");
        }
        this.skip = skipFirst;
        this.patchAll = patchAll;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") &&
                m.getMethodName().equals("renderRotatedText")) {
            if (skip && firstPatch && !patchAll) {
                firstPatch = false;
                return;
            }
            m.replace(replacementString);
        }
    }
}
