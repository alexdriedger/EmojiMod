package EmojiMod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import EmojiMod.EmojiMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.lwjgl.Sys;

public class AbstractCardPatches {

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
        public static boolean firstPatch = true;

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException
                {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") &&
                            m.getMethodName().equals("renderRotatedText")) {
                        if (firstPatch) {
                            firstPatch = false;
                            return;
                        }
                        EmojiMod.logger.info("Replacing renderRotatedText");
                        m.replace(
                    "{ " +
                                "$7 = i * 2.50F * -font.getCapHeight() + draw_y - this.current_y + -6.0F;" +
                                "$_ = $proceed($$);" +
                            " }"
                        );
                    }
                }
            };
        }

    }


}