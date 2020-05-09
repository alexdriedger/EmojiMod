package EmojiMod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import EmojiMod.EmojiMod;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionExprEditor;
import EmojiMod.patches.com.megacrit.cardcrawl.RenderDescriptionSpacingVariableLocator;
import EmojiMod.util.EmojiMappingUtils;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import javassist.expr.ExprEditor;

import java.util.ArrayList;
import java.util.List;

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
    public static class DescriptionWidthPatch {
        @SpireInsertPatch(rloc = 25, localvars = {"start_x", "i", "font"})
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef float[] start_x, int i, BitmapFont font) {
            String[] tokens = __instance.description.get(i).getCachedTokenizedText();
            float descWidth = EmojiMappingUtils.getStringWidthWithEmoji(__instance, tokens, font);
            start_x[0] = __instance.current_x - descWidth * __instance.drawScale / 2.0F;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
    public static class LineSpacingPatch {
        public static ExprEditor Instrument() {
            return new RenderDescriptionExprEditor(RENDER_DESCRIPTION_REPLACEMENT, true, false);
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