package EmojiMod.patches.com.badlogic.gdx.graphics.g2d;

import EmojiMod.EmojiMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = BitmapFont.class, method = "dispose")
public class BitmapFontDisposePatch {
    @SpireInstrumentPatch
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals(Texture.class.getName()) && m.getMethodName().equals("dispose")) {
                    m.replace(String.format("if (%s.check($0)) { $_ = $proceed($$); }", BitmapFontDisposePatch.class.getName()));
                }
            }
        };
    }

    public static boolean check(Texture texture) {
        return !EmojiMod.emojiSupport.textures.contains(texture);
    }
}
