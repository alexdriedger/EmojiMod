package EmojiMod.patches.com.megacrit.cardcrawl;

import EmojiMod.EmojiMod;
import EmojiMod.util.EmojiMappingUtils;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.stream.Collectors;

public class ReplaceDynamicVariableExprEditor extends ExprEditor {

    private int callCount;

    public ReplaceDynamicVariableExprEditor() {
        callCount = 0;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        if (m.getClassName().equals("java.lang.StringBuilder") && m.getMethodName().equals("append")) {
            if (callCount != 3) {
                callCount++;
                return;
            }

            m.replace(
        "{" +
                    "$_ = stringBuilder.append(" + EmojiMappingUtils.class.getName() + ".replaceIntWithEmoji(num));" +
                "}"
            );

            callCount++;
        }

    }

}
