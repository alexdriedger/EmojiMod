package EmojiMod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import EmojiMod.EmojiMod;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionExprEditor;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionSpacingVariableLocator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class AbstractCardPatches {

    public static final String RENDER_DESCRIPTION_REPLACEMENT =
        "{ " +
            "$7 = i * 2.50F * -font.getCapHeight() + draw_y - this.current_y + -3.0F;" +
            "$_ = $proceed($$);" +
        " }";

    @SpirePatch(
            clz = AbstractCard.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    String.class,
                    String.class,
                    String.class,
                    int.class,
                    String.class,
                    AbstractCard.CardType.class,
                    AbstractCard.CardColor.class,
                    AbstractCard.CardRarity.class,
                    AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class
            }
    )
    public static class ConstructorPatch {
        public static void Prefix(AbstractCard __instance, String id, @ByRef String[] name, String imgUrl, int cost, @ByRef String[] rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, DamageInfo.DamageType dType) {
            name[0] = EmojiMod.emojiSupport.FilterEmojis(name[0]);
            rawDescription[0] = EmojiMod.emojiSupport.FilterEmojis(rawDescription[0]);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
    public static class LineSpacingPatch {
        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT, true);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDynamicVariable")
    public static class LineSpacingDynamicVarPatch {
        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT, false);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
    public static class SpacingVariablePatch {
        @SpireInsertPatch(locator = RenderDescriptionSpacingVariableLocator.class, localvars = { "spacing "})
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef float[] spacing) {
            spacing[0] = spacing[0] / 1.45F * 2.50F;
        }
    }

}