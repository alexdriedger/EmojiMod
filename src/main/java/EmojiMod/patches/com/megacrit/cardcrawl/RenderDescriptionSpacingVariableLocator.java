package EmojiMod.patches.com.megacrit.cardcrawl;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class RenderDescriptionSpacingVariableLocator extends SpireInsertLocator {
    public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        Matcher initialMatcher = new Matcher.MethodCallMatcher(BitmapFont.class, "getCapHeight");
        int[] allMatches = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), initialMatcher);
        int[] singleMatch = new int[1];
        singleMatch[0] = allMatches[2] + 1;
        return singleMatch;
    }
}
