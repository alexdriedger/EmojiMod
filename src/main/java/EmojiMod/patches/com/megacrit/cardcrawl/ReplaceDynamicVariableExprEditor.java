package EmojiMod.patches.com.megacrit.cardcrawl;

import EmojiMod.EmojiMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class ReplaceDynamicVariableExprEditor extends ExprEditor {

    private int callCount;

    public ReplaceDynamicVariableExprEditor() {
        callCount = 0;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        if (m.getClassName().equals("java.lang.StringBuilder") && m.getMethodName().equals("append")) {
            EmojiMod.logger.info("ReplaceDynamicVariable count: " + callCount);
            if (callCount != 3) {
                callCount++;
                return;
            }

            EmojiMod.logger.info("Replacing BaseMod RenderDV");

            m.replace(
        "{" +
                    "$_ = stringBuilder.append(" + ReplaceDynamicVariableExprEditor.class.getName() + ".replaceIntWithEmoji(num));" +
                "}"
            );

//            m.replace(
//        "{" +
//                    "$1 = " + ReplaceDynamicVariableExprEditor.class.getName() + ".replaceIntWithEmoji($1);" +
//                    "$_ = $proceed($$);" +
//                "}"
//            );

            callCount++;
        }

    }

    public static String replaceIntWithEmoji(int num) {
        return "\uDB80\uDE60 ";
    }
}
