package EmojiMod.patches.com.megacrit.cardcrawl;

import EmojiMod.EmojiMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class RenderDescriptionExprEditor extends ExprEditor {

    private boolean firstPatch;
    private final String replacementString;
    private final boolean skip;

    public RenderDescriptionExprEditor(String replacementString, boolean skipFirst) {
        this.firstPatch = true;
        this.replacementString = replacementString;
        this.skip = skipFirst;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") &&
                m.getMethodName().equals("renderRotatedText")) {
            if (skip && firstPatch) {
                firstPatch = false;
                return;
            }
            m.replace(replacementString);
        }
    }
}
