package EmojiMod.patches.com.megacrit.cardcrawl;

import EmojiMod.EmojiMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class RenderDescriptionExprEditor extends ExprEditor {

    private boolean firstPatch;
    private String replacementString;

    public RenderDescriptionExprEditor(String replacementString) {
        this.firstPatch = true;
        this.replacementString = replacementString;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") &&
                m.getMethodName().equals("renderRotatedText")) {
            if (firstPatch) {
                firstPatch = false;
                return;
            }
            EmojiMod.logger.info("Replacing renderRotatedText in new expr");
            m.replace(replacementString);
        }
    }
}
