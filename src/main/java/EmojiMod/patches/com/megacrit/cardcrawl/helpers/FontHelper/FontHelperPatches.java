package EmojiMod.patches.com.megacrit.cardcrawl.helpers.FontHelper;

import EmojiMod.EmojiMod;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.FontHelper;


// Maybe need to patch FontHelper.getWidth(...) & FontHelper.getHeight(...)???
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
    public static class RenderRotatedText {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float offsetX, float offsetY, float angle, boolean roundY, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderTipLeft")
    public static class RenderTipLeft {
        public static void Prefix(SpriteBatch sb, @ByRef String[] msg) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFont")
    public static class RenderFont {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderWrappedText",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class,
                    float.class
            }
    )
    public static class RenderWrappedText {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float width, com.badlogic.gdx.graphics.Color c, float scale) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFontLeftDownAligned")
    public static class RenderFontLeftDownAligned {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFontRightToLeft")
    public static class RenderFontRightToLeft {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontRightTopAligned",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderFontRightTopAligned {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFontRightAligned")
    public static class RenderFontRightAligned {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCentered",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderFontCentered1 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCentered",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class,
                    float.class
            }
    )
    public static class RenderFontCentered2 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c, float scale) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCentered",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class
            }
    )
    public static class RenderFontCentered3 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontRightTopAligned",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderFontRightTopAligned2 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float scale, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderSmartText",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderSmartText {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float lineWidth, float lineSpacing, com.badlogic.gdx.graphics.Color baseColor) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderDeckViewTip")
    public static class RenderDeckViewTip {
        public static void Prefix(SpriteBatch sb, @ByRef String[] msg, float y, com.badlogic.gdx.graphics.Color color) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFontLeftTopAligned")
    public static class RenderFontLeftTopAligned {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFontLeft")
    public static class RenderFontLeft {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "exampleNonWordWrappedText")
    public static class ExampleNonWordWrappedText {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c, float widthMax, float lineSpacing) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(clz = FontHelper.class, method = "renderFontCenteredTopAligned")
    public static class RenderFontCenteredTopAligned {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCenteredWidth",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderFontCenteredWidth {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCenteredWidth",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class
            }
    )
    public static class RenderFontCenteredWidth2 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCenteredHeight",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderFontCenteredHeight {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float lineWidth, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCenteredHeight",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class,
                    float.class
            }
    )
    public static class RenderFontCenteredHeight2 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, float lineWidth, com.badlogic.gdx.graphics.Color c, float scale) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCenteredHeight",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class,
                    com.badlogic.gdx.graphics.Color.class
            }
    )
    public static class RenderFontCenteredHeight3 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y, com.badlogic.gdx.graphics.Color c) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontCenteredHeight",
            paramtypez = {
                    SpriteBatch.class,
                    BitmapFont.class,
                    String.class,
                    float.class,
                    float.class
            }
    )
    public static class RenderFontCenteredHeight4 {
        public static void Prefix(SpriteBatch sb, BitmapFont font, @ByRef String[] msg, float x, float y) {
            msg[0] = EmojiMod.emojiSupport.FilterEmojis(msg[0]);
        }
    }

}
