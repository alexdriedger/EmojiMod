package EmojiMod.patches.com.megacrit.cardcrawl;

import EmojiMod.EmojiMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.stream.Collectors;

public class ReplaceDynamicVariableExprEditor extends ExprEditor {

    private int callCount;
    private static String[] emojiNumberMappings = { "\uDB80\uDE65", "\uDB80\uDE60", "\uDB80\uDE64", "\uDB80\uDE63", "\uDB80\uDE5E", "\uDB80\uDE5D", "\uDB80\uDE62", "\uDB80\uDE61", "\uDB80\uDE5C", "\uDB80\uDE5F" };

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
                    "$_ = stringBuilder.append(" + ReplaceDynamicVariableExprEditor.class.getName() + ".replaceIntWithEmoji(num));" +
                "}"
            );

            callCount++;
        }

    }

    public static String replaceIntWithEmoji(int num) {
        String delim = "  ";
        if (num >= 999) {
            return "☠️" + delim;
        }
        String str = String.valueOf(num)
                .chars()
                .map(Character::getNumericValue)
                .mapToObj(x -> emojiNumberMappings[x])
                .collect(Collectors.joining(delim, "", delim));
        return str + delim;
    }
}
