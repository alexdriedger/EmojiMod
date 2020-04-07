package EmojiMod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import EmojiMod.EmojiMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

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


}