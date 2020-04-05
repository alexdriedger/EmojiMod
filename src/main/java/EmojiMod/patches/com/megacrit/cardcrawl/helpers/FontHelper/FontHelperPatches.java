package EmojiMod.patches.com.megacrit.cardcrawl.helpers.FontHelper;

import EmojiMod.EmojiMod;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.FontHelper;

//@SpirePatch(clz = FontHelper.class, method = "identifyOrb")
//public class FontHelperPatches {
//
//    public static TextureAtlas.AtlasRegion Postfix(TextureAtlas.AtlasRegion result, String word) {
//        // Result is not null if the word was an energy icon
//        if (result != null) {
//            return result;
//        }
//
////        String path128 = EmojiMod.getModID() + "Resources/images/noto-emoji-master/png/128/emoji_u1f6a3_1f3fc_200d_2642.png";
//
////        TextureAtlas.AtlasRegion region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 128, 128);
////        if (EmojiMod.testRegion == null) {
////            EmojiMod.logger.info("testRegion is null!!!!!!!!!!");
////        }
//        if (word.startsWith("[U+")) {
//            return findTexture(word);
//        }
//        return null;
//    }
//
//    public static TextureAtlas.AtlasRegion findTexture(String word) {
//        Pattern p = Pattern.compile("\\[U\\+(.*?)\\]");
//        Matcher m = p.matcher(word);
//        if (!m.find()) {
//            String errorMsg = "Find texture used on a expression that is not supported by EmojiMod";
//            EmojiMod.logger.error(errorMsg);
//            throw new RuntimeException(errorMsg);
//        }
//        String s = "emoji-u" + m.group(1);
//        s = s.toLowerCase();
////        EmojiMod.logger.info("findTexture string to look for:\t" + s);
//        if (EmojiMod.cachedEmojis.containsKey(s)) {
//            return EmojiMod.cachedEmojis.get(s);
//        } else {
//            EmojiMod.logger.info("Loading emoji:\t" + s);
//            TextureAtlas.AtlasRegion region = EmojiMod.emojiAtlas.findRegion(s);
//            if (region == null) {
//                EmojiMod.logger.error("Emoji:\t" + s + " not loaded correctly!");
//            }
//            EmojiMod.cachedEmojis.put(s, region);
//            return region;
//        }
//    }
//}

//@SpirePatch(clz = FontHelper.class, method = "initialize")
//public class FontHelperPatches {
//
////    @SpireInsertPatch(rloc = 424, localvars = "fontFile")
////    public static void Insert(@ByRef FileHandle[] fontFile) {
////        System.out.println("Postfixing FontHelper initialize");
////        properPatch(fontFile);
//////        otherPatch();
////    }
//
//    private static void otherPatch() {
//        FileHandle fileHandle = Gdx.files.internal(EmojiMod.getModID() + "Resources/font/emo/TwitterColorEmoji-SVGinOT.ttf");
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fileHandle);
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
////        FreeTypeFontGenerator.FreeTypeBitmapFontData data = new FreeTypeFontGenerator.FreeTypeBitmapFontData();
////        data.xChars = new char[] { '动' };
////        data.capChars = new char[] { '动' };
//        parameter.characters = "❤\uD83D\uDC49";
//        parameter.size = 12;
//        EmojiFontHelper.EMOJI_FONT = generator.generateFont(parameter); // font size 12 pixels
//        generator.dispose();
//    }
//
//    private static void properPatch(FileHandle[] fontFile) {
//        fontFile[0] = Gdx.files.internal("font/emo/TwitterColorEmoji-SVGinOT.ttf");
//
//        try {
//            Method method = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
//            method.setAccessible(true);
//            EmojiFontHelper.EMOJI_FONT = (BitmapFont) method.invoke(FontHelper.class, 24.0F, true);
//            System.out.println("Set Emoji Font");
//        } catch (Exception e) {
//            System.out.println("Tried to use reflection on FontHelper");
//        }
//    }
//}


public class FontHelperPatches {

    @SpirePatch(
        clz = FontHelper.class,
        method = "prepFont",
        paramtypez = {
                FreeTypeFontGenerator.class,
                float.class,
                boolean.class
        }
    )
    public static class AddEmojiToFontPatch {
        public static BitmapFont Postfix(BitmapFont __result, FreeTypeFontGenerator g, float size, boolean isLinearFiltering) {
            EmojiMod.logger.info("Postfixing prepFont");
            EmojiMod.emojiSupport.AddEmojisToFont(__result);
            return __result;
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderRotatedText")
    public static class RenderRotatedTextPatch {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float offsetX, float offsetY, float angle, boolean roundY, com.badlogic.gdx.graphics.Color c) {
            String s = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
            msg[0] = s;
        }
    }
}
