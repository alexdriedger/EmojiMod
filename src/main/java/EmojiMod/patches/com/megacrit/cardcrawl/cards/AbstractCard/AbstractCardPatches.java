//package EmojiMod.patches.com.megacrit.cardcrawl.cards.AbstractCard;
//
//import EmojiMod.EmojiMod;
//import EmojiMod.patches.com.megacrit.cardcrawl.helpers.FontHelper.FontHelperPatches;
//import basemod.ReflectionHacks;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.helpers.FontHelper;
//import javassist.CtBehavior;
//
//import static com.megacrit.cardcrawl.cards.AbstractCard.orb_red;
//
//
//public class AbstractCardPatches {
//
//    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
//    public static class descriptionPatch {
//
//        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp", "start_x", "i", "spacing"})
//        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] tmp, @ByRef float[] start_x, int i, float spacing) {
////            EmojiMod.logger.info("Tokenized tmp:\t" + tmp);
////            if (!tmp.startsWith("[U+")) {
////                return;
////            }
//
////            EmojiMod.logger.info("Red orb packed height:\t" + orb_red.packedHeight);
////            EmojiMod.logger.info("Red orb packed width:\t" + orb_red.packedWidth);
//
////            EmojiMod.logger.info("Emoji  packed height:\t" + FontHelperPatches.findTexture(tmp.trim()).packedHeight);
////            EmojiMod.logger.info("Emoji packed width:\t" + FontHelperPatches.findTexture(tmp.trim()).packedWidth);
//
//            String token = tmp[0];
//            if (token.length() > 0 && token.startsWith("[U+")) {
//                float CARD_ENERGY_IMG_WIDTH = (float) ReflectionHacks.getPrivateStatic(AbstractCard.class, "CARD_ENERGY_IMG_WIDTH");
//                GlyphLayout gl = (GlyphLayout) ReflectionHacks.getPrivateStatic(AbstractCard.class, "gl");
//                gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
//
//
//                renderSmallEmoji(
//                        __instance,
//                        sb,
//                        FontHelperPatches.findTexture(token.trim()),
//                        (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
//                        -100.0F - ((__instance.description.size() - 4.0F) / 2.0F - i + 1.0F) * spacing
//                );
//
//                start_x[0] += gl.width;
//                tmp[0] = "";
//            }
//        }
//
//        private static void renderSmallEmoji(AbstractCard __instance, SpriteBatch sb, TextureAtlas.AtlasRegion region, float x, float y) {
//            sb.setColor((Color) ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor"));
//            sb.draw(
//                    region.getTexture(),
//                    __instance.current_x + x * Settings.scale * __instance.drawScale + region.offsetX * Settings.scale,
//                    __instance.current_y + y * Settings.scale * __instance.drawScale + region.offsetY * Settings.scale,
//                    0.0F,
//                    0.0F,
//                    orb_red.packedWidth,
//                    orb_red.packedHeight,
//                    __instance.drawScale * Settings.scale,
//                    __instance.drawScale * Settings.scale,
//                    0.0F,
//                    region.getRegionX(),
//                    region.getRegionY(),
//                    region.getRegionWidth(),
//                    region.getRegionHeight(),
//                    false,
//                    false
//            );
//        }
//
//        private static class Locator extends SpireInsertLocator
//        {
//            @Override
//            public int[] Locate(CtBehavior ctBehavior) throws Exception
//            {
//                Matcher matcher = new Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
//                int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
//                return new int[]{lines[lines.length-1]}; // Only last occurrence
//            }
//        }
//
////        @SpireInsertPatch(locator = )
////        public static void Insert(AbstractCard __instance, SpriteBatch sb) {
////            float CARD_ENERGY_IMG_WIDTH = (float) ReflectionHacks.getPrivateStatic(AbstractCard.class, "CARD_ENERGY_IMG_WIDTH");
////            GlyphLayout gl = (GlyphLayout) ReflectionHacks.getPrivateStatic(AbstractCard.class, "gl");
////            gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
////            __instance.renderSmallEnergy(sb, );
////            renderSmallEnergy(sb, orb_red, (start_x - this.current_x) / Settings.scale / this.drawScale, -100.0F - ((this.description.size() - 4.0F) / 2.0F - i + 1.0F) * spacing);
////            start_x += gl.width;
////        }
//    }
//
//}
