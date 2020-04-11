package EmojiMod.patches.com.megacrit.cardcrawl.helpers.ImageMaster;

import EmojiMod.EmojiMod;
import EmojiMod.EmojiSupport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;

@SpirePatch(clz = ImageMaster.class, method = "initialize")
public class ImageMasterPatches {

    public static void Postfix() {
        FileHandle fileHandle = Gdx.files.internal(EmojiMod.getModID() + "Resources/images/noto-emoji-master/unnamed.atlas");
        if (fileHandle != null) {
            EmojiMod.logger.info("File handle for atlas loaded correctly");
        }

        FileHandle newFileHandle = Gdx.files.internal(EmojiMod.getModID() + "Resources/images/emoji-support-multiple/multiple.atlas");
        if (fileHandle != null) {
            EmojiMod.logger.info("File handle for atlas loaded correctly");
        }

        // New EmojiSupport
        EmojiMod.emojiSupport = new EmojiSupport();
        EmojiMod.emojiSupport.Load(newFileHandle);
        EmojiMod.logger.info("Loaded emojis using new support system");

        EmojiMod.emojiAtlas = new TextureAtlas(fileHandle);
        if (EmojiMod.emojiAtlas != null) {
            EmojiMod.logger.info("Emoji atlas loaded correctly");
        }
        EmojiMod.testRegion = EmojiMod.emojiAtlas.findRegion("emoji-u1f6a3-1f3fc-200d-2642");
        if (EmojiMod.testRegion == null) {
            EmojiMod.logger.info("testRegion is null!!!!!!!!!!");
        }
    }
}
